package com.qentelli.automation.annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.simple.parser.JSONParser;
import com.qentelli.automation.utilities.AbstractResourceBundle;
import com.qentelli.automation.utilities.TDMUser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class AbstractTestDataStore implements ITestDataStore {
	private final static int LIMIT = 1000;

	static String databaseName = "automation";
	String env;
	Integer size = 0;
	JSONParser jsonParser;
	Object obj;
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;
	Logger logger = LogManager.getLogger(TDMUser.class);

	public AbstractTestDataStore(String collectionname) {
		initCollection(collectionname, "en_US");
	}

	public AbstractTestDataStore(String collectionname, String locale) {
		// make things specific for env and application
		if (locale == null) {
			initCollection(collectionname, "en_US");

		} else {
			initCollection(collectionname, locale);
		}
	}

	private void initCollection(String bucket, String locale) {
		String env = AbstractResourceBundle.getEnv() ; 
		if (env == null) {
			env = "test";
		}
		// String env = RuntimeSingleton.getInstance().scenarios.get(firstKey).env;

		bucket = bucket + "_" + locale + "_" + env;
		logger.info("Collection (" + bucket + ")");
		jsonParser = new JSONParser();
		mongo = new MongoClient("autoqe-dash.qa.dc2.qentelli.com", 27017);
		database = mongo.getDatabase(databaseName);
		// snelson: this should also create the collection if it doesn't exist
		collection = database.getCollection(bucket);
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public long getSize() {
		FindIterable<Document> cursor = collection.find();
		for (Document doc2 : cursor) {
			// logger.info("tdm\t" + doc2.getString("email"));
			size++;
		}

		if (size > LIMIT) {
			logger.info("generic tdm limit hit");
		}
		return size;

	}
}
