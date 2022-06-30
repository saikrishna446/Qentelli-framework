package com.qentelli.automation.utilities;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BYDSessionMonitor {
    static String databaseName = "test_automation";
    static String collectionname = "bydesign_sessions";
    private String env;
    private JSONParser jsonParser;
    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private Logger logger = LogManager.getLogger(BYDSessionMonitor.class);

    public BYDSessionMonitor(){
        jsonParser = new JSONParser();
        mongo = new MongoClient( "autoqe-dash.qa.dc2.qentelli.com" , 27017 );
        database = mongo.getDatabase(databaseName);
        collection = database.getCollection(collectionname);
    }

    public BYDSessionMonitor(String environment){
        this.env = environment;
        this.jsonParser = new JSONParser();
        this.mongo = new MongoClient( "autoqe-dash.qa.dc2.qentelli.com" , 27017 );
        this.database = mongo.getDatabase(databaseName);
        this.collection = database.getCollection(collectionname);
    }

    //Runs the find query on the database
    private synchronized FindIterable<Document> runFindQuery(BasicDBObject criteria){
        return collection.find(criteria);
    }

    //Gets the user record from the collection
    private String getUser(String username) {

        // Performing a read operation on the collection.
        BasicDBObject criteria = new BasicDBObject();
        criteria.append("name", username);
        criteria.append("environment",env);
        FindIterable<Document> fi = runFindQuery(criteria);
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                return cursor.next().toJson();
            }
        } finally {
            cursor.close();
        }
        return null;

    }

    //Returns the inuse status for the given user
    public boolean inUse(String username){

        try {
            Object jsonObj = jsonParser.parse(getUser(username));
            return ((JSONObject)jsonObj).get("inuse").toString().equalsIgnoreCase("true");
        }catch (ParseException pe){
            pe.printStackTrace();
        }catch (NullPointerException npe){
            System.out.println("User: "+username + " does not exist");
        }
        return false;
    }

    //Gets the free user record
    private String _getFreeUser() {
        String usr = null, usr1 = null;
        int timeout = 15;
        // Performing a read operation on the collection.
        BasicDBObject criteria = new BasicDBObject();
        criteria.append("inuse", false);
        criteria.append("environment",env);

        FindIterable<Document> fi = runFindQuery(criteria);
        MongoCursor<Document> cursor = fi.iterator();
        try {
            if(cursor.hasNext()) {
                usr = cursor.next().toJson();
                return usr;
            }
            else{
                //Auto release users that are blocked more than 30mins
                Calendar b430mins = Calendar.getInstance();
                b430mins.add(Calendar.MINUTE, -timeout);
                criteria = new BasicDBObject();
                criteria.append("inuse", true);
                criteria.append("environment", env);
                criteria.append("lastupdated", new BasicDBObject("$lte",b430mins.getTime()));
                fi = runFindQuery(criteria);
                cursor = fi.iterator();
                while(cursor.hasNext()){
                    usr1 = cursor.next().toJson();
                    Object jsonObj = jsonParser.parse(usr1);
                    usr1=((JSONObject)jsonObj).get("name").toString();
                    logger.info("Auto releasing "+usr+ " based on timeout of "+timeout+" mins.");
                    releaseUser(usr1);
                    if(usr == null){
                        usr = usr1;
                    }
                }
                return usr;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return null;

    }

    //Returns the free user object
    public Map<String,String> getFreeUser(){
        try {
            Object jsonObj = jsonParser.parse(_getFreeUser());
            String user=((JSONObject)jsonObj).get("name").toString();
            String pass=((JSONObject)jsonObj).get("password").toString();
            logger.info("BYD Free user:  "+user);
            blockUser(user);
            HashMap<String, String> bydUser = new HashMap<String, String>();
            bydUser.put("username",user);
            bydUser.put("password",pass);
            return bydUser;
        }catch (ParseException pe){
            pe.printStackTrace();
        }catch (NullPointerException npe){
            System.out.println("BYD Free user does not exist");
        }
        return null;
    }

    //Updates the inuse status for the given user
    private synchronized boolean updateInUse(String username, boolean inuse){
        String task = inuse ? "Blocked" : "Released";
        try {
            Bson filter = new Document("name", username).append("environment",env);
            Bson newValue = new Document("inuse", inuse) .append("lastupdated", new Date());
            Bson updateOperationDocument = new BasicDBObject("$set", newValue);
            collection.updateOne(filter, updateOperationDocument);
            logger.info("BYD User " + username + " is " + task + " successfully");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Sets the inuse status to true for the given user
    public boolean blockUser(String username){
        logger.info("Blocking BYD user: "+username);
        return updateInUse(username,true);
    }

    //Sets the inuse status to false for the given user
    public boolean releaseUser(String username){
        logger.info("Releasing BYD user: "+username);
        return updateInUse(username,false);
    }

    //Sets the inuse status to false for all the users
    private synchronized void releaseAll() {
        logger.info("Releasing all BYD users");
        collection.updateMany(new Document(), new Document("$set", new Document("inuse", false).append("lastupdated", new Date())));
    }

    //Prints the current snapshot of the db to the console
    public void printSnapshot(){
        // Performing a read operation on the collection.
        String output = "Snapshot of all users in the current environment\n", jsonText;
        BasicDBObject criteria = new BasicDBObject();
        criteria.append("environment",env);
        FindIterable<Document> fi = runFindQuery(criteria);
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()){
                jsonText = cursor.next().toJson();
                JSONObject jsonObj = (JSONObject)jsonParser.parse(jsonText);
                output += jsonObj.get("name").toString() + " -> " + jsonObj.get("inuse").toString() + "  |  ";
            }
        } catch (Exception e){
            logger.error("Error occurred: " + e.getMessage());
        }
        finally {
            cursor.close();
        }
        logger.info(output);
    }

    public static void main (String[] a){
        BYDSessionMonitor sessions = new BYDSessionMonitor("UAT");
        sessions.releaseAll();
    }

}

//POJO for BYD user
class BYDUser{
    public String username;
    public String password;

    public BYDUser(String username, String password){
        this.username = username;
        this.password = password;
    }
}
