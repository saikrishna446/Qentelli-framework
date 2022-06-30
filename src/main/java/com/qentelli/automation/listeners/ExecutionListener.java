package com.qentelli.automation.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.dto.Point;
import org.json.simple.JSONObject;
import org.testng.IAlterSuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.singletons.SetTestResultData;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.TextUtils;

import com.qentelli.automation.util.SendTestResultToPostgres;

public class ExecutionListener implements ITestListener, IAlterSuiteListener {
	private static final Logger log = LogManager.getLogger(ExecutionListener.class);
	Long id = System.currentTimeMillis();
	RuntimeProperties r = null ; 
	HashMap<String, Integer> results = new HashMap<String, Integer>();
	Long duration;

	/**
	 * Set thread count based on runtime parameter
	 */
	@Override
	public synchronized void alter(List<XmlSuite> suites) {
		int count = Integer.parseInt(System.getProperty("threadCount", "-1"));
		if (count <= 0) {
			return;
		}
		log.info("**Suite Alter is invoked**");
		for (XmlSuite suite : suites) {
			log.info("xml suite running is: " + suite.getFileName());
			log.info("setting thread count to be: " + count);
			suite.setThreadCount(count);
			suite.setDataProviderThreadCount(count);
		}
	}

	@Override
	public synchronized void onTestStart(ITestResult iTestResult) {

		log.info("on start |	" + iTestResult.getName());
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		log.info("hurray you | passed |");

	}

	@Override
	public synchronized void onTestFailure(ITestResult iTestResult) {

		String testMsg = iTestResult.getThrowable().toString();
		log.info("throwable |" + testMsg + "|");

	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		log.info("you | skipped" + "|");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

	}

	@Override
	public synchronized void onStart(ITestContext iTestContext) {

		////////////////////////////////////////////////////
		// let's get everything started w/ a unique ID //
		////////////////////////////////////////////////////

		log.info("runid: " + RuntimeSingleton
				.getInstance(System.getProperty("SID") == null ? Long.toString(id) : System.getProperty("SID"),
						RuntimeSingleton.getBucket())
				.getId());
		
		////////////////////////////////////////////////////
		r = new RuntimeProperties(true); 
		RuntimeSingleton.getInstance().platform = System.getProperty("browserPlatform");
		if (RuntimeSingleton.getInstance().platform == null) {
			RuntimeSingleton.getInstance().platform = System.getProperty("os.name");
		}

		syncExecution(iTestContext);
		log.info("Execution Thread: " + Thread.currentThread().getId());
		r.writeProp("RUID", RuntimeSingleton.getInstance().runid);
		r.writeProp("OS", System.getProperty("os.name"));
		r.writeProp("BROWSER", System.getProperty("browser"));

		r.writeProp("ENV", System.getProperty("environment"));
		r.writeProp("USER", RuntimeSingleton.getInstance().whoami);
		r.writeProp("REPO", System.getProperty("database"));
		r.writeProp("START", RuntimeSingleton.getInstance().runid);

		// clear the results map; set everything we want to 0 //
		results.put("PASSED", 0);
		results.put("FAILED", 0);
		results.put("SKIPPED", 0);
		results.put("UNDEFINED", 0);
		duration = id;
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		log.info("ON FINISH");
		RuntimeSingleton.getInstance().setData.end = System.currentTimeMillis();
		duration = RuntimeSingleton.getInstance().setData.end - duration;
		RuntimeSingleton.getInstance().setData.duration = duration;
		processEventFile();
		// fixes reporting issue w/ new thread implementation
		RuntimeSingleton.getInstance().setData.passed = results.get("PASSED");
		RuntimeSingleton.getInstance().setData.failed = results.get("FAILED");
		RuntimeSingleton.getInstance().setData.skipped = results.get("SKIPPED");
		RuntimeSingleton.getInstance().setData.undefined = results.get("UNDEFINED");
		RuntimeSingleton.getInstance().setData.total = results.get("PASSED") + results.get("FAILED")
				+ results.get("SKIPPED") + results.get("UNDEFINED");
		log.info("ON FINISH");
		log.info(TextUtils.format("Scenario Name", RuntimeSingleton.getInstance().getScenario().getName()));

		log.info("Total " + RuntimeSingleton.getInstance().setData.total);

		sendRunStats(RuntimeSingleton.getInstance().setData);
		log.info("END FINISH");
	}

	private void sendRunStats(SetTestResultData d) {
		d.checkBrowser();
		d.syncTagData();
		// let's default the suite/set to Mobile for mobile runs to make reporting
		// simpler in grafana

		d.printSet();
		Point point = null;
		if (RuntimeSingleton.getInstance().isMobile) {

			log.info("mobile " + RuntimeSingleton.getInstance().isMobile);
			log.info("mobileBrowser " + RuntimeSingleton.getInstance().mobileBrowser);
			log.info("mobileDevice " + RuntimeSingleton.getInstance().mobileDevice);
			log.info("mobilePlatform " + RuntimeSingleton.getInstance().mobilePlatform);
			log.info("mobileVersion " + RuntimeSingleton.getInstance().mobileVersion);

		}
		// if (d.total)

		d.total = d.passed + d.failed + d.skipped + d.undefined;
		r.writeProp("START", String.valueOf(d.start));
		r.writeProp("END", String.valueOf(d.start));
		log.info("sending point to " + d.getPointName());

		point = Point.measurement(d.getPointName()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", RuntimeSingleton.getInstance().isMobile)
				.addField("platform", RuntimeSingleton.getInstance().platform)
				.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", d.duration)
				.addField("lid", RuntimeSingleton.getInstance().getId()).addField("start", d.start)
				.addField("end", d.end).addField("runid", d.rid).addField("suite", d.suite).addField("total", d.total)
				.addField("env", d.env).addField("browser", d.browser.name()).addField("skipped", d.skipped)
				.addField("failed", d.failed).addField("logLink", d.logLink).addField("passed", d.passed)
				.addField("locale", d.locale).addField("application", d.application).addField("testrail", d.testrail)
				.addField("project", d.project).addField("undefined", d.undefined).tag("env", d.env)
				.tag("browser", d.browser.name()).tag("suite", d.suite).tag("application", d.application)
				.tag("project", d.project).tag("locale", d.locale).build();
		ResultSender.send(point, ResultSender.TABLE.SET);

		// gather set data and construct DataSentToPostgreSQL
		JSONObject DataSentToPostgreSQL = RuntimeSingleton.getInstance().GetDataSentToPostgreSQL();
		DataSentToPostgreSQL.put("time", System.currentTimeMillis());
		DataSentToPostgreSQL.put("mobile", RuntimeSingleton.getInstance().isMobile);
		DataSentToPostgreSQL.put("platform", RuntimeSingleton.getInstance().platform);
		DataSentToPostgreSQL.put("user", RuntimeSingleton.getInstance().whoami);
		DataSentToPostgreSQL.put("suite", d.suite);
		DataSentToPostgreSQL.put("env", d.env);
		DataSentToPostgreSQL.put("application", d.application);
		DataSentToPostgreSQL.put("project", d.project);
		DataSentToPostgreSQL.put("locale", d.locale);
		DataSentToPostgreSQL.put("bucket", d.bucket);
		DataSentToPostgreSQL.put("browser", d.browser.name());
		DataSentToPostgreSQL.put("testRail", d.testrail);
		DataSentToPostgreSQL.put("lid", RuntimeSingleton.getInstance().getId());
		DataSentToPostgreSQL.put("start", d.start);
		DataSentToPostgreSQL.put("end", d.end);
		DataSentToPostgreSQL.put("duration", d.duration);
		DataSentToPostgreSQL.put("runId", d.rid);
		DataSentToPostgreSQL.put("total", d.total);
		DataSentToPostgreSQL.put("passed", d.passed);
		DataSentToPostgreSQL.put("failed", d.failed);
		DataSentToPostgreSQL.put("skipped", d.skipped);
		DataSentToPostgreSQL.put("logLink", d.logLink);

		RuntimeSingleton.getInstance().SetDataSentToPostgreSQL(DataSentToPostgreSQL);
		System.out.println("After set data point, JSON result = "
				+ RuntimeSingleton.getInstance().GetDataSentToPostgreSQL().toString());

		log.info("Before sending results to GetDataSentToPostgreSQL ");

		// Option 2 - use service
//todo bhargav
		//SendTestResultToPostgres.send("insert", RuntimeSingleton.getInstance().GetDataSentToPostgreSQL().toString());
		SendTestResultToPostgres.send2(RuntimeSingleton.getInstance().GetDataSentToPostgreSQL());
		/*try {
			// Alternative RPC to send the same data to db.
			//URL url = new URL("http://automation.test.com:4999/insert");
			// Automation server endpoint:

			 URL url = new URL("http://localhost:5431/insert");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// Write the JSON to output stream for service
			OutputStream os = conn.getOutputStream();
			os.write(RuntimeSingleton.getInstance().GetDataSentToPostgreSQL().toString().getBytes("utf-8"));
			os.flush();

			if (conn.getResponseCode() != 200) {
				log.info("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			conn.disconnect();
		} catch (Exception e) {
			log.warn("Failed to insert results into PostgreSQL db, message is =  " + e.getMessage());
		}*/
	}

	public void syncExecution(ITestContext iTestContext) {
		// Identify if run is via Jenkins or local
		boolean runOnJenkins = (System.getenv("JENKINS_HOME") != null);
		log.info("Run on Jenkins : " + runOnJenkins);
		String s1 = System.getProperty("locale");

		if ((runOnJenkins) && s1 == null) {
			log.error("You did not provide a -Dlocale=${locale} param in your jenkins mvn build command. Locale is "
					+ s1);
			throw new RuntimeException(
					"You did not provide a -Dlocale=${locale} param in your jenkins mvn build command. Locale is "
							+ s1);
		}
		for (Entry<String, String> s : iTestContext.getCurrentXmlTest().getAllParameters().entrySet()) {
			log.info(String.format("%-25s<>%-20s", s.getKey(), s.getValue()));
			if (s.getKey().equalsIgnoreCase("locale")) {
				RuntimeSingleton.getInstance().locale = s.getValue();
			}
		}
	}

	public void processEventFile() {

		String line;

		String s_id = String.valueOf(id);
		File input = new File(r.tmp);
		for (String f : input.list()) {
			String[] split;

			if (f.contains(s_id))
				try (BufferedReader reader = new BufferedReader(new FileReader(r.tmp + File.separator + f))) {
					// log.info("split @ "+f);
					while ((line = reader.readLine()) != null) {

						if (line.contains("=")) {
							split = line.split("=");
							String res = split[1];
							// log.info("here " + split[1]);

							if (res.equals("PASSED") || res.equals("FAILED") || res.equals("SKIPED")
									|| res.equals("UNDEFINED")) {
								Integer value = results.get(res);
								value++;
								results.put(res, value);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
