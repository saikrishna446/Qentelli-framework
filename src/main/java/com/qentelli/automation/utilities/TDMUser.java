package com.qentelli.automation.utilities;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.simple.parser.JSONParser;

import com.qentelli.automation.annotations.AbstractTestDataStore;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TDMUser extends AbstractTestDataStore {
	final static Logger logger = LogManager.getLogger(TDMUser.class);

	final static String collectionname = "_users_queue_";
	String env;
	String email = null;
	String[] emails = null;
	String[] passwords = null;
	Document[] docs = null;

	String[] orders = null;
	String[] continuityOrders = null;

	String name = null;
	String password = null;
	JSONParser jsonParser;
	Object obj;
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;
	String jsonObject = "{}";
	int size = 0;

	public TDMUser(String bucket, String locale) {
		super(bucket, locale);
		collection = getCollection();

		FindIterable<Document> cursor = collection.find();

		for (Document doc2 : cursor) {
			// logger.info("tdm\t" + doc2.getString("email"));
			size++;
		}
		emails = new String[size];
		passwords = new String[size];
		docs = new Document[size];
		size = 0;
		cursor = collection.find();

		for (Document doc2 : cursor) {
			String e = doc2.getString("email");
			String p = doc2.getString("password");
			docs[size] = doc2;
			passwords[size] = p;
			emails[size++] = e;
		}
		logger.info("Collection: " + size);

		size = 0;
		cursor = collection.find();

		for (Document doc2 : cursor) {
			// logger.info("tdm\t" + doc2.getString("store"));
			size++;
		}
		orders = new String[size];
		size = 0;
		cursor = collection.find();

		for (Document doc2 : cursor) {
			String e = doc2.getString("store");
			orders[size++] = e;
		}
		logger.info("Collection orders: " + size);

		continuityOrders = new String[size];
		size = 0;
		cursor = collection.find();

		for (Document doc2 : cursor) {
			String e = doc2.getString("orderNumber");
			continuityOrders[size++] = e;
		}

		for (int i = 0; i < emails.length; i++) {
//			System.out.println(emails[i]);
			if (emails[i] == null) {
				removeDocument(emails[i]);
			}
		}
	}

	public void insertDoc(String e, String p) {
		collection = getCollection();

		if (getSize() > 100) {
			return;
		}
		Document doc = Document.parse(getJson(e, p).toString());
		collection.insertOne(doc);
		int i = 0;
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("email", e);
		FindIterable<Document> cursor = collection.find(searchQuery);

		for (Document doc2 : cursor) {
			logger.info("tdm insert >>> " + doc2.getString("email"));
		}

	}

	public void insertDoc(String e, String p, String store) {

		collection = getCollection();
		if (getSize() > 100) {
			return;
		}
		Document doc = Document.parse(getJson(e, p, store).toString());
		collection.insertOne(doc);
		int i = 0;
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("email", e);
		FindIterable<Document> cursor = collection.find(searchQuery);

		for (Document doc2 : cursor) {
			// logger.info("tdm\t" + doc2.getString("email"));
		}

	}

	public void insertDoc(HashMap<String, String> in) {
		// super(collectionname);
		collection = getCollection();
		if (getSize() > 100) {
			return;
		}
		Document doc = Document.parse(getJson(in).toString());
		collection.insertOne(doc);
		int i = 0;
		FindIterable<Document> cursor = collection.find();

		for (Document doc2 : cursor) {
			logger.info("tdm insert >>> " + doc2.getString("email"));
		}

	}

	public TDMUser(String e) {
		super(collectionname);
		// snelson: this should also create the collection if it doesn't exist
		collection = getCollection();
		Document doc = Document.parse(getJson(e).toString());
		if (getSize() > 100) {
			return;
		}
		collection.insertOne(doc);

	}

	@Override
	public String getSchema() {
		return jsonObject;
	}

	@Override
	public String getCollectionName() {
		return collectionname;
	}

	private JsonObject getJson(String e) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("email", e);
		return jsonObject;
	}

	private JsonObject getJson(String e, String p) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("email", e);
		jsonObject.addProperty("password", p);
		return jsonObject;
	}

	private JsonObject getJson(String e, String p, String store) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("email", e);
		jsonObject.addProperty("password", p);
		jsonObject.addProperty("store", store);

		return jsonObject;
	}

	private JsonObject getJson(HashMap<String, String> in) {
		JsonObject jsonObject = new JsonObject();

		for (Entry<String, String> e : in.entrySet()) {
			jsonObject.addProperty(e.getKey(), e.getValue());

		}

		return jsonObject;
	}

	public String getEmail() {
		try {
			return emails[getThreadID()];
		} catch (Exception e) {
			logger.info("TDM user did not have an email address");
			return null;
		}
	}

	public String getPwd() {
		BasicDBObject searchQuery = new BasicDBObject();
		FindIterable<Document> cursor = collection.find(searchQuery);

		for (Document doc2 : cursor) {
			password = doc2.getString("password");
			// logger.info("pwd\t" + doc2.getString("password"));
		}
		return passwords[getThreadID()];
	}

	public String[] getEmails() {
		return emails;
	}

	public String[] getOrders() {
		return orders;
	}

	public int getThreadID() {
		String id = Thread.currentThread().getName().substring(Thread.currentThread().getName().lastIndexOf("-") + 1);
		return Integer.parseInt(id);
	}

	public Document getRequiredDoc() {
		return docs[getThreadID()];
	}

	public void removeDocument(String email) {
		BasicDBObject searchQuery = new BasicDBObject("email", email);
		logger.info("RemovinguUser with an email " + email + "from Bucket " + collection.getNamespace());
		collection.deleteOne(searchQuery);
	}

	public void removeDocument(String email, boolean logging) {
		BasicDBObject searchQuery = new BasicDBObject("email", email);
		if (logging) {
			logger.info("RemovinguUser with an email " + email + "from Bucket " + collection.getNamespace());
		}
		collection.deleteOne(searchQuery);
	}

	public String getOrder() {
		return continuityOrders[getThreadID()];
	}
}
