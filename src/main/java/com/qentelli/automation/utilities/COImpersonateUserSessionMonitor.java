package com.qentelli.automation.utilities;

import com.qentelli.automation.libraries.ConfigFileReader;
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

public class COImpersonateUserSessionMonitor {
    static String host = ConfigFileReader.getConfigFileReader().getMongoDBHostName();
    static String databaseName = "test_automation";
    static String collectionname = "";
    String env;
    JSONParser jsonParser;
    Object obj;
    MongoClient mongo;
    MongoDatabase database;
    MongoCollection<Document> collection;
    Logger logger = LogManager.getLogger(COImpersonateUserSessionMonitor.class);

    public COImpersonateUserSessionMonitor(String environment){
        collectionname = "COO_"+environment.toUpperCase()+"_IMPERSONATE_USER";
        jsonParser = new JSONParser();
        mongo = new MongoClient( "autoqe-dash.qa.dc2.qentelli.com" , 27017 );
        database = mongo.getDatabase(databaseName);
        collection = database.getCollection(collectionname);
    }


    private String _getFreeUser(MongoCollection<Document> col) throws ParseException {
        String usr = null, usr1 = null;
        int timeout = 15;
        // Performing a read operation on the collection.
        BasicDBObject criteria = new BasicDBObject();
        criteria.append("inuse", false);

        FindIterable<Document> fi = col.find(criteria);
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
                criteria.append("lastupdated", new BasicDBObject("$lte",b430mins.getTime()));
                fi = col.find(criteria);
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
            logger.info("Got exception in getting Free Impersonate User");
            throw (e);
        }
        finally {
            cursor.close();
        }

    }

    public synchronized Map<String,String> getFreeUser() throws Exception {
        try {
            Object jsonObj = jsonParser.parse(_getFreeUser(this.collection));
            String user=((JSONObject)jsonObj).get("name").toString();
            String pass=((JSONObject)jsonObj).get("password").toString();
            logger.info("COO Free Impersonate user:  "+user);
            blockUser(user);
            HashMap<String, String> cooUser = new HashMap<String, String>();
            cooUser.put("username",user);
            cooUser.put("password",pass);
            return cooUser;
        }catch (ParseException pe){
            logger.info("Got exception in getting Free Impersonate User");
            throw new Exception("Failed to parse", pe);
        }catch (NullPointerException npe){
            logger.info("CO Impersonate Free user does not exist");
            throw(npe);
        }
    }

    private boolean updateInuse(MongoCollection<Document> col,String username, boolean inuse){
        String task = inuse ? "Blocked" : "Released";
        try {
            Bson filter = new Document("name", username);
            Bson newValue = new Document("inuse", inuse) .append("lastupdated", new Date());
            Bson updateOperationDocument = new BasicDBObject("$set", newValue);
            collection.updateOne(filter, updateOperationDocument);
            logger.info("COO Impersonate User " + username + " is " + task + " successfully");
        }catch (Exception e){
            logger.info("Failed to update In Use  attribute for an user"+ username);
            return false;
        }
        return true;
    }

    public synchronized boolean blockUser(String username){
        logger.info("Blocking COO Impersonate user: "+username);
        return updateInuse(collection,username,true);
    }

    public synchronized boolean releaseUser(String username){
        logger.info("Releasing COO Impersonate user: "+username);
        return updateInuse(collection,username,false);
    }
}
