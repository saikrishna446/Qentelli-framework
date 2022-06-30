package com.qentelli.automation.singletons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import cucumber.api.Scenario;

public class RuntimeSingleton {
	Logger logger = LogManager.getLogger(RuntimeSingleton.class);

	private static RuntimeSingleton parameters = null;
	public String whoami = System.getProperty("user.name");

	public SetTestResultData setData;
	List<String> tags = null;
	String featureName = null;
	String browser = null;
	public String locale = null;
	Scenario scenario = null;
	// public int events = 0;
	public Boolean isMobile = false;
	public String mobileDevice = null;
	public String mobilePlatform = null;
	public String browserPlatform = null;
	public String mobileVersion = null;
	public String mobileBrowser = null;
	public String platform = null;
	public String runid = null;
	public String pageTitle = null;
	public String bucket = null;

	private Long id = null;
	public HashMap<Long, ScenarioTestResultData> scenarios = new HashMap<Long, ScenarioTestResultData>();
	public HashMap<Long, List<StepTestResultData>> steps = new HashMap<Long, List<StepTestResultData>>();
	public HashMap<Long, HashMap<String, String>> healthCheckSteps = new HashMap<Long, HashMap<String, String>>();

	public HashMap<String, Integer> threads = new HashMap<String, Integer>();
	List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
	public ArrayList<HashMap<String, String>> foo = new ArrayList<HashMap<String, String>>();

	private JSONObject DataSentToPostgreSQL = new JSONObject();


//	public HashMap<Long, ScenarioTestResultData> scenarios2 = new HashMap<Long, ScenarioTestResultData>();;

	public RuntimeSingleton(String sid, String b) {
		runid = sid;
		id = Long.valueOf(sid);
		setData = new SetTestResultData(sid, b);
		whoami = whoami.equals("stick") ? "snelson" : whoami;
		bucket = b;
	}

	public synchronized static RuntimeSingleton getInstance(String sid, String b) {
		if (parameters == null) {
			parameters = new RuntimeSingleton(sid, b);
		}
		return parameters;
	}

	public synchronized static RuntimeSingleton getInstance() {
		if (parameters == null) {
			// parameters = new RuntimeSingleton();

			throw new RuntimeException(
                "parameters to Singletone cannot be null ");
		}

		return parameters;
	}

	public void setTags(Collection<String> t) {
		tags = new ArrayList<String>(t);
	}

	public void setFeatureName(String s) {
		featureName = s;
	}

	public void setScenario(Scenario s) {
		scenario = s;
	}

	public List<String> getTags() {
		return tags;
	}

	public String getFeatureName() {
		return featureName;
	}

	public Scenario getScenario() {
		return scenario;
	}

//	public synchronized void addSession(String name, String s) {
//		Long l = Thread.currentThread().getId();
//		RuntimeSingleton.getInstance().videos[l.intValue()] = new HashMap<String, String>();
//		RuntimeSingleton.getInstance().videos[l.intValue()].put(name, s);
//	}
//
//	public synchronized void deleteSessions(String name) {
//		Long l = Thread.currentThread().getId();
//		videos[l.intValue()] = null;
//	}
//
//	public synchronized Set<Entry<String, String>> getVideoEntries() {
//		Long l = Thread.currentThread().getId();
//
//		if (RuntimeSingleton.getInstance().videos[l.intValue()] == null) {
//			HashMap<String, String> foo = new HashMap<String, String>();
//			return foo.entrySet();
//		}
//
//		return RuntimeSingleton.getInstance().videos[l.intValue()].entrySet();
//
//	}

	public synchronized void setRid(String s) {
		runid = s;
	}

	public long getId() {
		return id;
	}

	public static String getBucket() {
		String bucket = "Test";
		if (System.getProperty("database") != null) {

			if (System.getProperty("database").equalsIgnoreCase("PROD")
					|| System.getProperty("database").equalsIgnoreCase("Production")) {
				bucket = "Production";

			}
			if (System.getProperty("database").equalsIgnoreCase("STABILITY")
					|| System.getProperty("database").equalsIgnoreCase("Stab")) {
				bucket = "Stability";
			}
		}
		return bucket;
	}

	public String getRunId() {
		return runid;
	}

    public String getLocaleByTag() {
      for (String s : tags) {
        if (s.toLowerCase().contains("en_us") || s.toLowerCase().contains("es_us")
            || s.toLowerCase().contains("en_gb") || s.toLowerCase().contains("en_ca")
            || s.toLowerCase().contains("fr_ca") || s.toLowerCase().contains("fr_fr"))
          return s;
      }
      // logger.error("no locale found for e2e");
      return "en_US";
    }

	public JSONObject GetDataSentToPostgreSQL() {
		// snelson to review, this can come from a props file so we dont have to carry around a payload in mem
		if ( (JSONArray) DataSentToPostgreSQL.get("scenario") == null) {
			DataSentToPostgreSQL.put("scenario", new JSONArray());
		}

		if ( (JSONArray) DataSentToPostgreSQL.get("step") == null) {
			DataSentToPostgreSQL.put("step", new JSONArray());
		}

		return DataSentToPostgreSQL;
	}

	public void SetDataSentToPostgreSQL(JSONObject jsonObj) {
		 DataSentToPostgreSQL = jsonObj;
	}
}
