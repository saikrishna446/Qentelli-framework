package com.qentelli.automation.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qentelli.automation.util.SendTestResultToPostgres;
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

public class ExecutionListenerE2E implements ITestListener, IAlterSuiteListener {
	Long id = System.currentTimeMillis();
	List<String> blacklist = new ArrayList<String>();
	HashMap<String, Integer> results = new HashMap<String, Integer>();
	Long duration;

	private static final Logger log = LogManager.getLogger(ExecutionListenerE2E.class);

	@Override
	public synchronized void onTestStart(ITestResult iTestResult) {

	}

	/**
	 * Set thread count based on runtime parameter
	 */
	@Override
	public synchronized void alter(List<XmlSuite> suites) {
		log.info(" --- onalter  >>>>>>>>>>>>>> ");
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
	public void onTestSuccess(ITestResult iTestResult) {
		this.sendTestMethodStatus(iTestResult, "PASS");
	}

	@Override
	public synchronized void onTestFailure(ITestResult iTestResult) {
		log.info(" --- on fail  >>>>>>>>>>>>>> ");

		log.info("test ng caught throwable |" + iTestResult.getThrowable().toString());

		String testMsg = iTestResult.getThrowable().toString();
		if (testMsg != null) {
			for (String s : blacklist) {
				String[] ss = s.split(",");
				String exceptionname = ss[1];
				String msg = ss[0];
				log.info("Msg (" + msg + ") " + exceptionname);
				if (testMsg.contains(exceptionname)) {
					log.warn("MATCHED turning off logging; you are forced to debug mode\n");
					// RuntimeSingleton.getInstance().debugMode = true;
				}
			}
		}

		this.sendTestMethodStatus(iTestResult, "FAIL");
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		this.sendTestMethodStatus(iTestResult, "SKIPPED");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

	}

	@Override
	public synchronized void onStart(ITestContext iTestContext) {
		duration = id;
		if (System.getProperty("RUNID") != null && System.getProperty("SID") == null)
			throw new RuntimeException("Use -DSDID= vs -DRUNID=");

		if (System.getProperty("SID") != null) {
			log.info("sid set by cmd: " + System.getProperty("SID"));
		}
		if (System.getProperty("SID").equals("gen"))
			System.setProperty("SID", Long.toString(duration));

		////////////////////////////////////////////////////
		// let's get everything started w/ a unique ID //
		////////////////////////////////////////////////////
		log.info("Start ---------->" + RuntimeSingleton
				.getInstance(System.getProperty("SID") == null ? Long.toString(id) : System.getProperty("SID"),
						RuntimeSingleton.getBucket())
				.getId());
		////////////////////////////////////////////////////
		// Specific to @HC tagname - SID comes from Jenkins at runtime
		RuntimeSingleton.getInstance().platform = System.getProperty("browserPlatform");
		if (RuntimeSingleton.getInstance().platform == null) {
			RuntimeSingleton.getInstance().platform = System.getProperty("os.name");
		}
		RuntimeProperties r = new RuntimeProperties(true);
		log.info("Execution Thread: " + Thread.currentThread().getId());
		r.writeProp("RUID", RuntimeSingleton.getInstance().runid);
		r.writeProp("OS", System.getProperty("os.name"));
		r.writeProp("ENV", System.getProperty("environment"));
		r.writeProp("USER", RuntimeSingleton.getInstance().whoami);
		r.writeProp("REPO", System.getProperty("database"));
		r.writeProp("BROWSER", System.getProperty("browser"));
		r.writeProp("START", RuntimeSingleton.getInstance().runid);
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		// gather set data and construct DataSentToPostgreSQL
		JSONObject DataSentToPostgreSQL = RuntimeSingleton.getInstance().GetDataSentToPostgreSQL();
		DataSentToPostgreSQL.put("time", System.currentTimeMillis());
		DataSentToPostgreSQL.put("mobile", RuntimeSingleton.getInstance().isMobile);
		DataSentToPostgreSQL.put("platform", System.getProperty("os.name"));
		DataSentToPostgreSQL.put("user", RuntimeSingleton.getInstance().whoami);
		DataSentToPostgreSQL.put("suite", "E2E");
		DataSentToPostgreSQL.put("env", System.getProperty("environment"));
		DataSentToPostgreSQL.put("application", "E2E");
		DataSentToPostgreSQL.put("project", "Ops");
		DataSentToPostgreSQL.put("locale", "ALL");
		DataSentToPostgreSQL.put("bucket", RuntimeSingleton.getInstance().bucket);
		DataSentToPostgreSQL.put("browser", System.getProperty("browser"));
		DataSentToPostgreSQL.put("testRail", "");
		DataSentToPostgreSQL.put("lid", System.getProperty("SID"));
		DataSentToPostgreSQL.put("start", 0);
		DataSentToPostgreSQL.put("end", 0);
		DataSentToPostgreSQL.put("duration", 0);
		DataSentToPostgreSQL.put("runId", System.getProperty("SID"));
		DataSentToPostgreSQL.put("total", 0);
		DataSentToPostgreSQL.put("passed", 0);
		DataSentToPostgreSQL.put("failed", 0);
		DataSentToPostgreSQL.put("skipped", '0');
		DataSentToPostgreSQL.put("logLink", "");

		RuntimeSingleton.getInstance().SetDataSentToPostgreSQL(DataSentToPostgreSQL);

		System.out.println("After set data point, JSON result = "
				+ RuntimeSingleton.getInstance().GetDataSentToPostgreSQL().toString());

		log.info("Before sending results to GetDataSentToPostgreSQL ");

		//SendTestResultToPostgres.send("insert", RuntimeSingleton.getInstance().GetDataSentToPostgreSQL().toString());
		SendTestResultToPostgres.send2(RuntimeSingleton.getInstance().GetDataSentToPostgreSQL());
		/*point = Point.measurement(getPointName()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("platform", System.getProperty("os.name"))
				.addField("user",
						System.getProperty("user.name") == "stick" ? System.getProperty("user.name")
								: "snelson[collection]")
				.addField("duration", duration).addField("lid", Long.valueOf(sid)).addField("start", start)
				.addField("end", end).addField("runid", sid).addField("suite", "E2E").addField("total", total)
				.addField("env", System.getProperty("environment")).addField("browser", System.getProperty("browser"))
				.addField("skipped", results.get("SKIPPED")).addField("failed", results.get("FAILED"))
				.addField("passed", results.get("PASSED")).addField("locale", "ALL").addField("application", "E2E")
				.addField("project", "Ops").addField("undefined", results.get("UNDEFINED"))
				.tag("env", System.getProperty("environment")).tag("browser", browser).tag("suite", "E2E")
				.tag("application", "E2E").tag("project", "Ops").tag("locale", "ALL").tag("suite", "E2E").build();
		ResultSender.send(point, ResultSender.TABLE.SET);*/

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
		try {
			RuntimeSingleton.getInstance().setData.end = System.currentTimeMillis();
			duration = RuntimeSingleton.getInstance().setData.end - duration;
			RuntimeSingleton.getInstance().setData.duration = duration;
			processEventFile();
//			// fixes reporting issue w/ new thread implementation
//			RuntimeSingleton.getInstance().setData.passed = results.get("PASSED");
//			RuntimeSingleton.getInstance().setData.failed = results.get("FAILED");
//			RuntimeSingleton.getInstance().setData.skipped = results.get("SKIPPED");
//			RuntimeSingleton.getInstance().setData.undefined = results.get("UNDEFINED");
//			RuntimeSingleton.getInstance().setData.total = results.get("PASSED") + results.get("FAILED")
//					+ results.get("SKIPPED") + results.get("UNDEFINED");
//			log.info("ON FINISH1");
//			log.info(TextUtils.format("Scenario Name", RuntimeSingleton.getInstance().getScenario().getName()));
//
//			log.info("Total " + RuntimeSingleton.getInstance().setData.total);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		RuntimeProperties r = new RuntimeProperties();
//		r.writeProp("START", String.valueOf(RuntimeSingleton.getInstance().setData.start));
//		r.writeProp("DURATION_SET", String.valueOf(duration));
//		sendRunStats(RuntimeSingleton.getInstance().setData);

	}

	private void sendTestMethodStatus(ITestResult iTestResult, String status) {

	}

	private void sendRunStats(SetTestResultData d) {

		d.checkBrowser();
		d.syncTagData();

		// let's default the suite/set to Mobile for mobile runs to make reporting
		// simpler in grafana
		Point point = null;
//		if (RuntimeSingleton.getInstance().isMobile) {
//			log.info("-----------");
//
//			log.info("mobile " + RuntimeSingleton.getInstance().isMobile);
//			log.info("mobileBrowser " + RuntimeSingleton.getInstance().mobileBrowser);
//			log.info("mobileDevice " + RuntimeSingleton.getInstance().mobileDevice);
//			log.info("mobilePlatform " + RuntimeSingleton.getInstance().mobilePlatform);
//			log.info("mobileVersion " + RuntimeSingleton.getInstance().mobileVersion);
//
//			log.info("-----------");
//		}
		// if (d.total)
//
		d.total = d.passed + d.failed;
//
//		// for E2E run only - total run count set...
//		List<String> tagNames = RuntimeSingleton.getInstance().getTags();
//		String appPrint = tagNames.toString();
//		log.info("<<<<>>>>Tags before push: " + appPrint + "<<<<>>>>");
//
//		for (int i = 0; i < tagNames.size(); i++) {
//			if (tagNames.get(i).equals("@HCE2EALL") && RuntimeSingleton.getInstance().setData.locale.contains("US")) {
//				System.out.println(i + " >>> Tag: " + tagNames.get(i) + " >>> Locale: "
//						+ RuntimeSingleton.getInstance().setData.locale + " and its count is : " + d.total);
//				// d.total = 4;
//			}
//			if (tagNames.get(i).equals("@HCE2EALL") && (RuntimeSingleton.getInstance().setData.locale.contains("GB")
//					|| RuntimeSingleton.getInstance().setData.locale.contains("CA"))) {
//				System.out.println(">>> Tag: " + tagNames.get(i) + " >>> Locale: "
//						+ RuntimeSingleton.getInstance().setData.locale + " and its count is : " + d.total);
//				// d.total = 3;
//			}
//			if (tagNames.get(i).equals("@HCE2EALL")) {
//				if (!localePrint.contains("ALL")) {
//					// RuntimeSingleton.getInstance().locale
//					System.out.println("==================  Locale to print in setv1 is : "
//							+ RuntimeSingleton.getInstance().locale + " ==================");
//					// RuntimeSingleton.getInstance().locale="en_"+localePrint;
//				} else {
//					System.out.println("================ ALL locales: Locale to print in setv1 is : "
//							+ RuntimeSingleton.getInstance().locale + " ====================");
//					RuntimeSingleton.getInstance().locale = localePrint;
//				}
//			}
//			if (appPrint.contains("@E2E") && appPrint.contains("@HCE2EALL"))
//				d.application = "E2E";
//			d.suite = "E2E";
//			if (appPrint.contains("@CTR") && appPrint.contains("@HCE2EALL"))
//				d.application = "CTR";
//		}

		log.info("APPLICATION Set is: " + d.application);

		log.info("APPLICATION suite is: " + d.suite);

//		if (RuntimeSingleton.getInstance().whoami.contains("stephen"))
//		point = Point.measurement(d.getPointName()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//				.addField("mobile", RuntimeSingleton.getInstance().isMobile)
//				.addField("platform", RuntimeSingleton.getInstance().platform)
//				.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", d.duration)
//				.addField("lid", RuntimeSingleton.getInstance().getId()).addField("start", d.start)
//				.addField("end", d.end).addField("runid", d.rid).addField("suite", d.suite).addField("total", d.total)
//				.addField("env", d.env).addField("browser", d.browser.name()).addField("skipped", d.skipped)
//				.addField("failed", d.failed).addField("logLink", d.logLink).addField("passed", d.passed)
//				.addField("locale", d.locale).addField("application", d.application).addField("testrail", d.testrail)
//				.addField("undefined", d.undefined).tag("env", d.env).tag("browser", d.browser.name())
//				.tag("suite", d.suite).tag("application", d.application).tag("locale", "ALL").build();
//		ResultSender.send(point, ResultSender.TABLE.SET);
		d.printSet();

	}

	public void processEventFile() {
		// RuntimeProperties r = new RuntimeProperties();
		String line;

		String s_id = String.valueOf(id);
		File input = new File(RuntimeProperties.tmp);
		for (String f : input.list()) {
			String[] split;
			/// log.info("file:" + f);
			if (f.contains(s_id))
				try (BufferedReader reader = new BufferedReader(
						new FileReader(RuntimeProperties.tmp + File.separator + f))) {
					log.info("split @ " + f);
					while ((line = reader.readLine()) != null) {

						if (line.contains("=")) {
							split = line.split("=");
							String res = split[1];
							log.info("here " + split[1]);

							if (res.equals("PASSED") || res.equals("FAILED") || res.equals("SKIPPED")
									|| res.equals("UNDEFINED")) {
								if (results.get(res) == null)
									results.put(res, 0);
								Integer value = results.get(res);
								log.info("value >< " + value);
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
