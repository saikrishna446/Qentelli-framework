package com.qentelli.automation.singletons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.influxdb.dto.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.qentelli.automation.annotations.IException.ErrorType;
import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.base.BaseException;
import com.qentelli.automation.factory.ExceptionFactory;
import com.qentelli.automation.listeners.ResultSender;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.TextUtils;
import cucumber.api.Result;
import cucumber.api.Result.Type;
import cucumber.api.Scenario;
import cucumber.api.TestCase;

public class ScenarioTestResultData extends TestResultData {
	public String failedRed;
	public String passedGreen;
	public int total = 0;
	public int failed = 0;
	public int passed = 0;
	public int skipped = 0;
	public int undefined = 0;
	public Type result;
	public String featureName;
	public byte[] screenshot = null;
	public String sauceHtml = null;
	public String sauceLink = null;
	public String sauceVideo = "";
	public String testError = "none";
	public String comment = "";
	// public boolean skipReport = false;
	public String serverInfo;
	public BaseException exception = null;
	public List<String> videos = new ArrayList<>();
	public Scenario s;
	public Collection<String> tags2;

	public ScenarioTestResultData(String id) {
		rid = id;
	}

	public ScenarioTestResultData(String n, String id) {
		name = n;
		rid = id;

	}

	public void renameScenario() {
		String newName = prefixTestrail(this.name);
		this.name = TextUtils.makeURLCompatible(newName);
	}

	public String prefixTestrail(String name) {
		String testrailPrefixPattern = "^\\s*[C|T]\\d{7,}.*";
		String hcTestrailPrefixPattern = "^\\s*HC\\d{4}.*"; // HealthCheck ID pattern

		if (!name.matches(testrailPrefixPattern) || !name.matches(hcTestrailPrefixPattern)) {
			name = this.testrail + "_" + name;
		}
		return name;
	}

	public void tabulateSteps(List<StepTestResultData> srs) {
		if (srs ==null) return ; 
		for (StepTestResultData data : srs) {
			logger.info(data.name);
			if (data.result == Type.FAILED) {
				failed++;
			}

			if (data.result == Type.PASSED) {
				passed++;
			}

			if (data.result == Type.SKIPPED || data.result == null) {
				skipped++;
			}
			if (data.result == Type.UNDEFINED) {
				undefined++;
			}
		}

		if (skipped == 0 && failed > 0 && (srs.size() > (passed + failed + undefined))) {
			skipped = srs.size() - (passed + failed + undefined);
		}
		String s = String.valueOf(failed);
		failedRed = "<font color=\"red\">" + s + "</font>";
		s = String.valueOf(failed);
		passedGreen = "<font color=\"green\">" + s + "</font>";
		printScenario();
	}

	public void printScenario() {

		logger.info("RunId@ " + rid);
		logger.info(TextUtils.center("-- scenario --"));

		cleanData();
		logger.info(TextUtils.centerClear(name));
		logger.info(TextUtils.centerClear("---------------------------------"));
		logger.info(TextUtils.format("runtime", rid));
		logger.info(TextUtils.format("Start", start));
		logger.info(TextUtils.format("End", end));
		logger.info(TextUtils.format("Duration", duration));

		logger.info(TextUtils.format("Application", application));
		logger.info(TextUtils.format("Project", project));
		logger.info(TextUtils.format("Set", suite));
		logger.info(TextUtils.format("env", env));
		logger.info(TextUtils.format("locale", locale));
		logger.info(TextUtils.format("Testrail", testrail));
		logger.info(TextUtils.format("browser", browser));

		logger.info(TextUtils.format("Steps total", total));
		logger.info(TextUtils.format("Steps passed", passed));
		logger.info(TextUtils.format("failed", failed));
		logger.info(TextUtils.format("skipped", skipped));
		logger.info(TextUtils.format("undefined", undefined));
		logger.info(TextUtils.format("status", result));
		logger.info(TextUtils.format("error", testError));
		logger.info(TextUtils.format("repository", bucket));
		logger.info("---------------------------------");
		logger.info("sauce:", sauceVideo);
		logger.info("---------------------------------");

	}

	public void setSauce() {
		logger.info("Sauce (" + sauceLink + ")");
		logger.info("Sauce Video (" + sauceVideo + ")");

		// sauceLink = url;
		sauceHtml = "<html>\n" + "<style>\n" + "\n" + "p.ex1 {\n" + "  padding-top: 250px;\n" + "}\n" + "\n"
				+ "body {\n" + "  display: flex;\n" + "  flex-direction: column;\n" + "  height: 93vh;\n"
				+ "  justify-content: center;\n" + "  align-items: center;\n" + "  background: #222;\n"
				+ "  color: #eee;\n" + "  font-family: \"Dosis\", sans-serif;\n" + "}\n" + "\n" + ".underlined-a {\n"
				+ "  text-decoration: none;\n" + "  color: aqua;\n" + "  padding-bottom: 0.15em;\n"
				+ "  box-sizing: border-box;\n" + "  box-shadow: inset 0 -0.2em 0 aqua;\n" + "  transition: 0.2s;\n"
				+ "  &:hover {\n" + "    color: #222;\n" + "    box-shadow: inset 0 -2em 0 aqua;\n"
				+ "    transition: all 0.45s cubic-bezier(0.86, 0, 0.07, 1);\n" + "  }\n" + "}\n" + "\n"
				+ ".brk-btn {\n" + "  position: relative;\n" + "  background: none;\n" + "  color: aqua;\n"
				+ "  text-transform: uppercase;\n" + "  text-decoration: none;\n" + "  border: 0.2em solid aqua;\n"
				+ "  padding: 0.5em 1em;\n" + "  &::before {\n" + "    content: \"\";\n" + "    display: block;\n"
				+ "    position: absolute;\n" + "    width: 10%;\n" + "    background: #222;\n" + "    height: 0.3em;\n"
				+ "    right: 20%;\n" + "    top: 0.21em;\n" + "    transform: skewX(-45deg);\n"
				+ "    -webkit-transition: all 0.45s cubic-bezier(0.86, 0, 0.07, 1);\n"
				+ "    transition: all 0.45s cubic-bezier(0.86, 0, 0.07, 1);\n" + "  }\n" + "  &::after {\n"
				+ "    content: \"\";\n" + "    display: block;\n" + "    position: absolute;\n" + "    width: 10%;\n"
				+ "    background: #222;\n" + "    height: 0.3em;\n" + "    left: 20%;\n" + "    bottom: -0.25em;\n"
				+ "    transform: skewX(45deg);\n"
				+ "    -webkit-transition: all 0.45 cubic-bezier(0.86, 0, 0.07, 1);\n"
				+ "    transition: all 0.45s cubic-bezier(0.86, 0, 0.07, 1);\n" + "  }\n" + "  &:hover {\n"
				+ "    &::before {\n" + "      right: 80%;\n" + "    }\n" + "    &::after {\n" + "      left: 80%;\n"
				+ "    }\n" + "  }\n" + "}\n" + "</style>\n" + "<body>\n" + "<br>\n" + "<br>\n" + "<a href=\""
				+ sauceLink + "\" class=\"brk-btn\" target=\"_new\"+>\n" + "  Video\n" + "</a>\n" + "</body>\n"
				+ "</html>\n";

	}

	public void flattenError() {
		testError = testError.replaceAll("&", "");
		testError = testError.replaceAll(":", "");
		testError = testError.replaceAll(" ", "_");
		testError = testError.replaceAll("\\.", "_");
		testError = testError.replaceAll("\\>", "_");
		testError = testError.replaceAll("\\s+", "");
	}

	public void evaluateStepError() {

	}

	public void evaluateError(Scenario scenario) {
		// look for exception
		Field f;
		Field rez;
		TestCase r = null;
		ArrayList<Result> res = null;
		try {
			f = scenario.getClass().getDeclaredField("testCase");
			rez = scenario.getClass().getDeclaredField("stepResults");
			f.setAccessible(true);
			rez.setAccessible(true);
			r = (TestCase) f.get(scenario);
			res = (ArrayList<Result>) rez.get(scenario);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (scenario.getStatus() == Result.Type.FAILED) {
			try {
				for (Result myResult : res) {
					if (myResult.getStatus() == Result.Type.FAILED) {
						logger.info("ERROR FOUND");
						exception = new ExceptionFactory(myResult.getError()).redirectException();
						logger.info(myResult.getError().toString());
						testError = myResult.getError().toString();
					}
				}
			} catch (Exception e1) {
				logger.info("<< Exception Server >>> " + e1.getLocalizedMessage());
				e1.printStackTrace();
			}
		}

	}

	public void sendScenarioTestResults(World world) {
		checkBrowser();

		if (RuntimeSingleton.getInstance().isMobile == true) {
			suite = "Mobile";
		}
		String base64Encoded = getEncodedScreenShotForDisplay();

		setSauce();
		if (sauceLink == null)
			sauceLink = "none";
		// for HealthCheck E2E only - @HC tagname -- appending to if any error with data
		// i.e., orderNumber and emailid
		List<String> tagNames = RuntimeSingleton.getInstance().getTags();
		makeSureDataBaseIsSetCorrectly();
		makeSureLocaleIsSetCorrectly();
		String verifyTagName = "";
		for (int i = 0; i < tagNames.size(); i++) {
			if (tagNames.get(i).equals("@healthCheck")) {
				healthCheckSteps();
			}
			if (tagNames.get(i).equals("@HC")) {
				verifyTagName = "E2E";
			}
		}

		if (verifyTagName.equals("E2E") && !comment.equals("") && (!testError.equals("none"))) {
			comment = testError + " >>>> " + comment;
		}
		comment = comment.replaceAll("&", "");
		flattenError();
		printScenario();
		logger.info("------------------");
		serverInfo = parseCommentServerInfo(comment);
		long l = Thread.currentThread().getId();

		String sl = Long.toString(l);
		logger.info("Thread id:" + sl);

		String et = null;
		if (result.equals(Type.PASSED)) {
			et = ErrorType.Validation.toString();
			comment = "the test passed its designated validation";
		} else {
			if (exception != null)
				et = exception.getType().toString();
		}
		if (exception == null)
			et = ErrorType.Validation.toString();
		if (exception != null) {
			comment = TextUtils.cleanupExceptionNames(exception.toString());
		}
		Point point = null;

		logger.info("Shot|>  " + (base64Encoded.length() > 0 ? "false" : "true"));
		logger.info("Video|> " + sauceVideo);
		if (testrailLink.equalsIgnoreCase("n/a")) testrailLink = new RuntimeProperties().getTestrail() ;
		point = Point.measurement(getPointName()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", RuntimeSingleton.getInstance().isMobile)
				.addField("platform", RuntimeSingleton.getInstance().platform)
				.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", duration)
				.addField("start", start).addField("end", end).addField("runid", rid).addField("suite", suite)
				.addField("lid", RuntimeSingleton.getInstance().getId()).addField("total", total).addField("env", env)
				.addField("name", name).addField("browser", browser.name()).addField("skipped", skipped)
				.addField("failed", failed).addField("feature", featureName).addField("passed", passed)
				.addField("application", application).addField("testrail", testrail).addField("bucket", bucket)
				.addField("video", sauceVideo).addField("sauce", sauceLink).addField("sauceHtml", sauceHtml)
				.addField("result", result.toString()).addField("testlink", testrailLink)
				.addField("undefined", undefined).addField("comment", comment).addField("serverinfo", serverInfo)
				.addField("reason", et).addField("locale", locale).addField("project", project)
				.tag("result", result.toString()).tag("env", env).tag("browser", browser.name()).tag("suite", suite)
				.tag("application", application).tag("testrail", testrail).tag("error", et).tag("locale", locale)
				.tag("project", project).build();
//		ResultSender.send(point, ResultSender.TABLE.SCENARIOS);

		JSONObject DataSentToPostgreSQL = RuntimeSingleton.getInstance().GetDataSentToPostgreSQL();


		JSONObject newScenarioObj = new JSONObject();
		newScenarioObj.put("time", System.currentTimeMillis());
		newScenarioObj.put("lid", RuntimeSingleton.getInstance().getId());
		newScenarioObj.put("runId", rid);
		newScenarioObj.put("featureName", featureName);
		newScenarioObj.put("scenarioName", name);
		newScenarioObj.put("testRail", testrail);
		newScenarioObj.put("locale", locale);
		newScenarioObj.put("duration", duration);
		newScenarioObj.put("start", start);
		newScenarioObj.put("end", end);
		newScenarioObj.put("totalSteps", total);
		newScenarioObj.put("passed", passed);
		newScenarioObj.put("skipped", skipped);
		newScenarioObj.put("failed", failed);
		newScenarioObj.put("result", result.toString());
		newScenarioObj.put("testRailLink", testrailLink);
		newScenarioObj.put("comment", comment);
		newScenarioObj.put("serverInfo", serverInfo);
		newScenarioObj.put("errorType", et);
		newScenarioObj.put("sauceVideo", sauceVideo);
		newScenarioObj.put("sauceLink", sauceLink);
		newScenarioObj.put("sauceHtml", sauceHtml);

		JSONArray array = (JSONArray) DataSentToPostgreSQL.get("scenario");
		array.add(newScenarioObj);
		RuntimeSingleton.getInstance().SetDataSentToPostgreSQL(DataSentToPostgreSQL);

	}

	private String getEncodedScreenShotForDisplay() {
		String base64Encoded = "none";
		// if (screenshot != null) {
		// String encodedString = Base64.getEncoder().encodeToString(screenshot);
		// logger.info("Screen shot detected!!!");
		// base64Encoded = "data:image/png;base64," + new String(screenshot,
		// StandardCharsets.UTF_8);
		// base64Encoded = " <img src=\"data:image/gif;base64," + encodedString + "\"
		// />";
		// }

		return base64Encoded;
	}

	private String parseCommentServerInfo(String comment) {
		StringBuilder strb = new StringBuilder();
		strb.append("<div style=\"height:2px;\">" + "<font size=\"-2\" color=\"grey\">");
		if (RuntimeSingleton.getInstance().pageTitle != null) {
			strb.append(
					"<font size=\"-2\" color=\"white\">" + RuntimeSingleton.getInstance().pageTitle + "</font><br>");
		}
		String info = "";
		String serverInfo = "";

		if (comment.contains("Server Info: null null null null")) {
			// strb.append("<i>not found</i>");

		} else if (comment.contains("Server Info:")) {
			serverInfo = comment.substring(comment.indexOf("Server Info: "));
			int i = 0;
			while (serverInfo.indexOf(":", i) > 0) {
				String key = serverInfo.substring(i, serverInfo.indexOf(":", i));
				i += key.length() + 1;
				logger.info("k:\t" + key);
				if (key.contains("orderId")) {
					strb.append(info + " (" + key.replace(" orderId", "").replaceAll(" ", "") + ")<br>");
					continue;
				}

				if (key.contains("profileId")) {
					strb.append("<i>orderId:</i> " + key.replace(" profileId", "") + "<br>");
					continue;
				}
				if (key.contains("Host Port")) {
					info = "<i>" + key.replace("Host Port", "").replaceAll(" ", "") + "</i>";
					continue;
				}

			}

			int last = serverInfo.lastIndexOf(" ");
			strb.append("<i>profileId:</i>" + serverInfo.substring(last));

			strb.append("<font></div>");

		} else {
			strb.append("<i>not applicable</i>");
		}
		return strb.toString();
	}

	synchronized private void healthCheckSteps() {
		HashMap<Long, HashMap<String, String>> hc_Scenario_Steps = RuntimeSingleton.getInstance().healthCheckSteps;

		int line = 5;
		if (hc_Scenario_Steps.containsKey(name)) {
			HashMap<String, String> hc_Steps = hc_Scenario_Steps.get(name);
			for (String hc_Step : hc_Steps.keySet()) {
				Point point = Point.measurement("steps").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
						.addField("mobile", RuntimeSingleton.getInstance().isMobile)
						.addField("platform", RuntimeSingleton.getInstance().platform)
						.addField("lid", RuntimeSingleton.getInstance().getId())
						.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", duration)
						.addField("start", start).addField("end", end).addField("line", line).addField("runid", rid)
						.addField("suite", suite).addField("result", hc_Steps.get(hc_Step)).addField("step", hc_Step)
						.addField("application", application).addField("env", env).addField("scenario", name)
						.addField("testrail", testrail).addField("browser", browser.name()).tag("step", hc_Step)
						.tag("scenario", name).tag("result", result.toString()).tag("env", env)
						.tag("browser", browser.name()).tag("application", application).tag("feature", featureName)
						.tag("testrail", testrail).tag("user", RuntimeSingleton.getInstance().whoami)
						.tag("line", String.valueOf(line)).tag("suite", suite)
						.tag("localeTag", RuntimeSingleton.getInstance().locale).build();

				ResultSender.send(point);
				logger.info(TextUtils.center("<> <> step sent <> <>"));
				// printStep();
				line++;
			}
		}
	}
}
