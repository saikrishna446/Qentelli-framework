package com.qentelli.automation.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.influxdb.dto.Point;
import org.w3c.dom.Document;

import com.qentelli.automation.listeners.ResultSender;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.singletons.ScenarioTestResultData;
import com.qentelli.automation.singletons.SetTestResultData;
import com.qentelli.automation.singletons.StepTestResultData;

import cucumber.api.Result.Type;

public class IngestAPI {
	static Logger logger = LogManager.getLogger(IngestAPI.class);
	String PATH = "/opt/workspace/workspace/SoapUI/output";
	static HashMap<String, File> response = new HashMap<String, File>();
	static List<HashMap<String, String>> listResults = new ArrayList<>();
	static Long lid = System.currentTimeMillis();
	static List<ScenarioTestResultData> lsScenario = new ArrayList<>();
	static List<String> lsName = new ArrayList<String>();

	static Map<String, String> argsMap = new HashMap<>();
	static Map<String, String> nameMap = new HashMap<>();

	static {
		RuntimeSingleton.getInstance(String.valueOf(lid), "Production");

	}
	static {
		Configurator.initialize(null, "log4j2.xml");
	}

	public static void main(String[] args) {
		String suite = "foo";
		for (String arg : args) {
			String[] parts = arg.split("=");
			argsMap.put(parts[0], parts[1]);
		}
		File dr = new File(argsMap.get("PATH"));

		if (dr.listFiles() == null) {
			logger.info("target PATH not found");
			return;
		}
		File[] files = dr.listFiles();
		List<Document> lsDocs = new ArrayList<Document>();
		for (File f : files) {

			if (f.getName().startsWith("TEST") && f.getName().endsWith("xml")) {
				suite = f.getName().substring(f.getName().indexOf("TEST") + 5, f.getName().indexOf("xml") - 1);
				lsName.add(suite);
			}

			if (f.isDirectory()) {
				String apiName = f.getName();
				File[] results = f.listFiles();
				for (File result : results) {
					apiName = apiName + ":" + result.getName();
					File[] fSteps = result.listFiles();
					for (File fStep : fSteps) {
						String testname = fStep.getName().split("-")[0];
						nameMap.put(testname, apiName);

					}
				}
			}
		}

		logger.info("You have tests: " + lsName.size() + "@" + dr.getAbsolutePath());
		logger.info(argsMap.get("ENDPOINT"));

		SetTestResultData setData = new SetTestResultData(RuntimeSingleton.getInstance().runid, "Production");
		setData.suite = argsMap.get("SUITE");
		setData.application = argsMap.get("APP");
		setData.locale = "none";
		setData.testrail = "n/a";
		setData.env = argsMap.get("ENV");
		setData.bucket = argsMap.get("BUCKET");
		readSteps(setData, argsMap.get("PATH"));

		logger.info("Results: http://10.1.70.107:3000/d/Orl_dtbZz/set-view?orgId=1&var-run=" + setData.rid);
		getResponses();
		logger.info("Responses: " + response.size());

		for (ScenarioTestResultData d : lsScenario) {

		}
	}

	private static void sendRunStats(SetTestResultData d) {

		Point point = null;

		point = Point.measurement("setv1").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", false).addField("platform", "Linux").addField("user", "Jenkins")
				.addField("duration", d.duration).addField("lid", lid).addField("start", d.start).addField("end", d.end)
				.addField("runid", d.rid).addField("suite", d.suite).addField("total", d.total).addField("env", d.env)
				.addField("browser", "not applicable").addField("skipped", d.skipped).addField("failed", d.failed)
				.addField("logLink", d.logLink).addField("passed", d.passed).addField("locale", "none")
				.addField("application", d.application).addField("testrail", d.testrail).addField("bucket", d.bucket)
				.addField("undefined", d.undefined).tag("env", d.env).tag("browser", "not applicable")
				.tag("suite", d.suite).tag("application", d.application).tag("database", d.bucket).tag("locale", "none")
				.build();
		point = Point.measurement(d.application + "_" + d.suite + "_" + d.env + "_" + d.locale + "_" + d.bucket)
				.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).addField("mobile", false)
				.addField("platform", "Linux").addField("user", "Jenkins").addField("duration", d.duration)
				.addField("lid", lid).addField("start", d.start).addField("end", d.end).addField("runid", d.rid)
				.addField("suite", d.suite).addField("total", d.total).addField("env", d.env)
				.addField("browser", "not applicable").addField("skipped", d.skipped).addField("failed", d.failed)
				.addField("logLink", d.logLink).addField("passed", d.passed).addField("locale", "none")
				.addField("application", d.application).addField("testrail", d.testrail).addField("bucket", d.bucket)
				.addField("undefined", d.undefined).tag("env", d.env).tag("browser", "not applicable")
				.tag("suite", d.suite).tag("application", d.application).tag("database", d.bucket).tag("locale", "none")
				.build();
		ResultSender.send(point, ResultSender.TABLE.SET);

	}

	private static void sendScenario(ScenarioTestResultData d) {
		String et = d.comment.equals("none") ? "Unknown" : "Validation";
		if (d.result == Type.PASSED) {
			et = "Validation";
		}
		if (d.name == null) {
			d.name = "foo";
		}

		d.printScenario();
		Point point = Point.measurement("scenarios").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", false).addField("platform", "Linux").addField("user", "Jenkins")
				.addField("duration", d.duration).addField("start", d.start).addField("end", d.end)
				.addField("runid", d.rid).addField("suite", d.suite).addField("lid", lid).addField("total", d.total)
				.addField("env", d.env).addField("name", d.name).addField("browser", "none")
				.addField("skipped", d.skipped).addField("failed", d.failed).addField("feature", d.featureName)
				.addField("passed", d.passed).addField("application", d.application).addField("testrail", d.testrail)
				.addField("result", d.result.toString()).addField("testlink", d.testrail)
				.addField("undefined", d.undefined).addField("comment", d.comment).addField("reason", et)
				.addField("serverinfo", d.serverInfo).addField("locale", "none").addField("bucket", d.bucket)
				.tag("result", d.result.toString()).tag("env", d.env).tag("suite", d.suite)
				.tag("application", d.application).tag("database", d.bucket).tag("errorType", et).tag("locale", "none")
				.build();

		point = Point.measurement(d.application + "_" + d.suite + "_" + d.env + "_" + d.locale + "_" + d.bucket)
				.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).addField("mobile", false)
				.addField("platform", "Linux").addField("user", "Jenkins").addField("duration", d.duration)
				.addField("start", d.start).addField("end", d.end).addField("runid", d.rid).addField("suite", d.suite)
				.addField("lid", lid).addField("total", d.total).addField("env", d.env).addField("name", d.name)
				.addField("browser", "none").addField("skipped", d.skipped).addField("failed", d.failed)
				.addField("feature", d.featureName).addField("passed", d.passed).addField("application", d.application)
				.addField("testrail", d.testrail).addField("result", d.result.toString())
				.addField("testlink", d.testrail).addField("undefined", d.undefined).addField("comment", d.comment)
				.addField("reason", et).addField("serverinfo", d.serverInfo).addField("locale", "none")
				.addField("bucket", d.bucket).tag("result", d.result.toString()).tag("env", d.env).tag("suite", d.suite)
				.tag("application", d.application).tag("database", d.bucket).tag("errorType", et).tag("locale", "none")
				.build();
		ResultSender.send(point, ResultSender.TABLE.SCENARIOS);

		logger.info("scenario sent");

	}

	private static void sendStep(StepTestResultData d) {
		if (d.scenarioName == null) {
			d.scenarioName = "foo";
		}

		Point point = Point.measurement("steps").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", false).addField("platform", "Linux").addField("lid", lid)
				.addField("user", "Jenkins").addField("duration", d.duration).addField("start", d.start)
				.addField("end", d.end).addField("line", d.line).addField("runid", d.rid).addField("suite", d.suite)
				.addField("result", d.result.toString()).addField("step", d.name).addField("application", d.application)
				.addField("env", d.env).addField("scenario", d.scenarioName).addField("bucket", d.bucket)
				.addField("testrail", d.testrail).addField("browser", "none").tag("result", d.result.toString())
				.tag("env", d.env).tag("application", d.application).tag("line", String.valueOf(d.line))
				.tag("suite", d.suite).tag("locale", d.locale).build();

		point = Point.measurement(d.application + "_" + d.suite + "_" + d.env + "_" + d.locale + "_" + d.bucket)
				.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).addField("mobile", false)
				.addField("platform", "Linux").addField("lid", lid).addField("user", "Jenkins")
				.addField("duration", d.duration).addField("start", d.start).addField("end", d.end)
				.addField("line", d.line).addField("runid", d.rid).addField("suite", d.suite)
				.addField("result", d.result.toString()).addField("step", d.name).addField("application", d.application)
				.addField("env", d.env).addField("scenario", d.scenarioName).addField("bucket", d.bucket)
				.addField("testrail", d.testrail).addField("browser", "none").tag("result", d.result.toString())
				.tag("env", d.env).tag("application", d.application).tag("line", String.valueOf(d.line))
				.tag("suite", d.suite).tag("locale", d.locale).build();
		ResultSender.send(point, ResultSender.TABLE.STEPS);

		logger.info("step sent");

	}

	private static List<String> readSteps(SetTestResultData setData, String path) {
		Path filePath = Paths.get(path.substring(0, path.lastIndexOf("/")), "soapui.log");
		List<String> filteredLines = new ArrayList<String>();

		try (Stream<String> lines = Files.lines(filePath)) {

			Iterable<String> iterable = lines::iterator;
			for (String s : iterable) {
				filteredLines.add(s);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		int t = 0;

		HashMap<String, List<StepTestResultData>> stepsMap = new HashMap<String, List<StepTestResultData>>();
		HashMap<String, String> stepsNameMap = new HashMap<String, String>();
		List<StepTestResultData> lsSteps = new ArrayList<>();
		String newName = "none";
		String newComment = "none";

		Boolean bDidThisPass = true;
		int pullName = 0;
		int passed = 0;
		int failed = 0;
		int k = 4;
		String scName = "not found";

		for (String s : filteredLines) {
			ScenarioTestResultData scenarioData = null;

			if (s.contains("running step")) {
				s = s.split("running step")[1];
				newName = StringUtils.substringBetween(s, "[", "]");
				logger.info("step:\t" + newName);
				continue;
			}
			if (s.contains("Finished running SoapUI testcase")) {

				scenarioData = new ScenarioTestResultData(setData.rid);

				String duration = s.substring(s.lastIndexOf("time taken: "));
				duration = duration.split(",")[0];
				duration = duration.replace("time taken: ", "");

				duration = duration.replace("ms", "");

				s = s.split("testcase")[1];
				String name = StringUtils.substringBetween(s, "[", "]");

				scenarioData.name = name;
				scenarioData.bucket = setData.bucket;

				logger.info(scenarioData.name + " steps " + lsSteps.size());
				scenarioData.comment = newComment;
				scenarioData.duration = Long.parseLong(duration);
				scenarioData.total = lsSteps.size();
				scenarioData.passed = passed;
				scenarioData.failed = failed;
				scenarioData.application = setData.application;
				scenarioData.featureName = "API ENTERPRISE GATEWAY";
				scenarioData.result = bDidThisPass ? Type.PASSED : Type.FAILED;
				scenarioData.suite = setData.suite;
				scenarioData.comment = newComment;
				scenarioData.duration = Long.parseLong(duration);
				scenarioData.total = lsSteps.size();
				scenarioData.passed = passed;
				scenarioData.failed = failed;
				scenarioData.application = setData.application;
				scenarioData.featureName = "API ENTERPRISE GATEWAY";
				scenarioData.result = bDidThisPass ? Type.PASSED : Type.FAILED;
				scenarioData.suite = setData.suite;
				setData.skipped = 0;

				scenarioData.locale = "none";
				scenarioData.env = setData.env;
				scenarioData.serverInfo = argsMap.get("ENDPOINT");

				List<StepTestResultData> lsSteps2 = new ArrayList();

				Long myL = Math.floorDiv(scenarioData.duration, lsSteps.size());
				k = 4;
				Boolean bMatchTestCase = false;
				scenarioData.testrail = convertStringToHex(scenarioData.name);
				for (StepTestResultData fix : lsSteps) {

					fix.line = Integer.valueOf(k++);
					fix.duration = myL;
					fix.testrail = scenarioData.testrail;
					fix.scenarioName = scenarioData.name;
					fix.addRuntimeDetails(scenarioData);
					lsSteps2.add(fix);
					if (!bMatchTestCase) {
						newName = newName.replace("-", "");
						newName = newName.replace("=", "");
						newName = newName.replace(" ", "_");

						bMatchTestCase = true;
					}
				}
				lsScenario.add(scenarioData);
				stepsMap.put(RandomStringUtils.randomAlphanumeric(20).toUpperCase(), lsSteps2);
				lsSteps = new ArrayList<>();
				bDidThisPass = true;
				passed = 0;
				failed = 0;
				continue;
			}

			if ((s.contains("Assert") || s.contains("ASSERTION FAILED"))
					&& (!s.contains("Missing assertion for type"))) {

				StepTestResultData stepData = new StepTestResultData(setData.rid);
				String fullName = s;
				String ss[] = s.split(" Assertion");
				if (!s.contains("ASSERTION FAILED ")) {
					s = s.split(" Assertion")[1];
				} else {
					s = s.split("ASSERTION FAILED")[1];
					newComment = s;
				}
				String name = StringUtils.substringBetween(s, "[", "]");
				stepData.name = s;
				stepData.result = s.contains("has status VALID") || s.contains("has status UNKNOWN") ? Type.PASSED
						: Type.FAILED;
				stepData.application = setData.application;
				stepData.env = setData.env;
				if (stepData.result == Type.PASSED) {
					passed++;
				} else {
					failed++;
					bDidThisPass = false;
				}
				lsSteps.add(stepData);

				continue;
			}
		}

		logger.info("Scenarios:\t" + lsScenario.size());
		logger.info("Steps:\t" + stepsMap.size());

		int z = 0;
		for (Entry e : stepsMap.entrySet()) {
			String n = (String) e.getKey();
			setData.duration += lsScenario.get(z++).duration;
			for (StepTestResultData data : (List<StepTestResultData>) e.getValue()) {
				data.end = data.start + data.duration;
				for (ScenarioTestResultData scen : lsScenario) {
					if (scen.name.equals(data.scenarioName)) {
						logger.info("hit:\t" + data.testrail + "now" + scen.testrail);

						data.testrail = scen.testrail;
					}
				}
				sendStep(data);
			}

		}
		for (ScenarioTestResultData e : lsScenario) {
			setData.total++;
			sendScenario(e);
			if (e.result == Type.FAILED) {
				setData.failed++;
			}

			if (e.result == Type.PASSED) {
				setData.passed++;
			}

			if (e.result == Type.SKIPPED) {
				setData.skipped++;
			}
			if (e.result == Type.UNDEFINED) {
				setData.undefined++;
			}
			setData.duration += e.duration;
		}

		setData.end = setData.start + setData.duration;
		setData.printSet();
		sendRunStats(setData);

		return filteredLines;
	}

	private static void getResponses() {
		File dir = new File(argsMap.get("PATH"));
		Collection<File> files = FileUtils.listFiles(dir, new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
		String content;
		for (File f : files) {
			content = null;
			response.put(f.getName(), f);
		}

	}

	private static String getContent(File f) {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}

	public static String convertStringToHex(String str) {
		StringBuffer hex = new StringBuffer();

		if (str == null) {
			return "ABC123456";
		}
		for (char temp : str.toCharArray()) {

			int decimal = temp;

			hex.append(Integer.toHexString(decimal));
		}
		return hex.toString();

	}

	public static String fixName(String s) {
		return s.substring(s.lastIndexOf(":") + 1);

	}

}
