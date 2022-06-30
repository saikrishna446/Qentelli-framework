package com.qentelli.automation.hookse2e;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.influxdb.InfluxDBException;
import org.influxdb.dto.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import com.qentelli.automation.annotations.IException.ErrorType;
import com.qentelli.automation.common.Constants;
import com.qentelli.automation.common.World;
import com.qentelli.automation.listeners.ResultSender;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.singletons.ScenarioTestResultData;
import com.qentelli.automation.singletons.SetTestResultData;
import com.qentelli.automation.singletons.StepTestResultData;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import com.qentelli.automation.testdatafactory.data.FactoryData;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.TextUtils;
import cucumber.api.PickleStepTestStep;
import cucumber.api.Result.Type;
import cucumber.api.Scenario;
import cucumber.api.TestCase;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Hooks {
	private final long ID = Thread.currentThread().getId();
	static Logger logger = LogManager.getLogger(Hooks.class);
	RuntimeProperties r = new RuntimeProperties(true); 
	private World world;
	int currentStepDefIndex = 0;
	Long startT = 0L ; 
	Long endT = 0L  ;
	private String scenDesc;
;

	private String SID = "";
	private String scenarioAppFail = "";
	private static int t = 0;

	public static List<String> tagNames;
	String comment;
	private String syndTime = "";
	static {
		Configurator.initialize(null, "log4j2.xml");
	}

	public Hooks(World world) {
		this.world = world;
	}

	@Before
	public synchronized void incTotal() {
		if (Objects.isNull(RuntimeSingleton.getInstance().setData)) {
			RuntimeSingleton.getInstance().setData = new SetTestResultData(RuntimeSingleton.getInstance().runid,
					RuntimeSingleton.getInstance().bucket);

		}
		RuntimeSingleton.getInstance().setData.total++;
	}

	@Before
	public synchronized void beforeScenario(Scenario s) {
		RuntimeSingleton.getInstance().threads.put(s.getName(), t++);
		startT = System.currentTimeMillis();
		try {
			// for E2E using JRUNID and SID.....
			if (System.getProperty("SID") != null) {
				logger.info("SID exists >>>>>> : " + System.getProperty("SID"));
				SID = System.getProperty("SID");

			}
			// let's store the number of scenarios in a set context
            logger.info(TextUtils
                .center(RuntimeSingleton.getInstance().whoami + "<> <before scenario> <>"));

			int stepsCnt = getStepCount(s);
			logger.info("Steps <" + stepsCnt + ">");
			// logger.info("INTAKE" + getStepss(s).size());

			ScenarioTestResultData d = new ScenarioTestResultData(s.getName(), RuntimeSingleton.getInstance().runid);
			d.total = getStepCount(s);

			d.tags.addAll(s.getSourceTagNames());
			d.setupTagFields();
			d.renameScenario();
			world.setScenarioName(d.name);

			d.featureName = s.getName() ; 

			Scenario scenario = s;
			world.setSauceTunnelId(System.getenv("TUNNEL_IDENTIFIER"));
			this.scenDesc = d.name;
			world.setScenario(scenario);

			RuntimeSingleton.getInstance().setTags(scenario.getSourceTagNames());
			RuntimeSingleton.getInstance().setScenario(s);
			worldInit();
			d.browser = world.getBrowser();
			d.env = world.getTestEnvironment();
			d.locale = world.getLocale();
			List<StepTestResultData> intake = new ArrayList<StepTestResultData>();
			for (PickleStepTestStep ps : getSteps(s)) {
				StepTestResultData data = new StepTestResultData(RuntimeSingleton.getInstance().runid);
				data.addRuntimeDetails(d);
				data.name = ps.getStepText();
				data.line = ps.getStepLine();
				data.result = null; // Type.UNDEFINED;
				data.scenarioName = d.name;
				data.env = d.env;
				data.testrail = d.testrail;
				data.bucket = d.bucket;
				intake.add(data);
			}
			r.writeProp("TESTRAIL",d.testrail) ;
			// RuntimeSingleton.getInstance().testcase = d.testrail;

			RuntimeSingleton.getInstance().browserPlatform = d.browser.name();
			RuntimeSingleton.getInstance().setData.env = d.env;
			RuntimeSingleton.getInstance().setData.browser = d.browser;

			// logger.info("Steps:\t" + intake.size());
			RuntimeSingleton.getInstance().scenarios.put(ID, d);
			RuntimeSingleton.getInstance().steps.put(ID, intake);

			world.getDriver();
			d.printScenario();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// this is to delete the oats related saved screenshot
			File screen = new File("OATS/DataBank/screenshot.png");
			screen.delete();
		}

	}

	@BeforeStep
	public void beforeStep(Scenario s) throws InterruptedException {
		logger.info(TextUtils.center("<> <before step> <>"));
		StepTestResultData stp = RuntimeSingleton.getInstance().steps.get(ID).get(currentStepDefIndex);
		stp.start = System.currentTimeMillis();

		RuntimeSingleton.getInstance().steps.get(ID).remove(currentStepDefIndex);
		RuntimeSingleton.getInstance().steps.get(ID).add(currentStepDefIndex, stp);
		logger.info(TextUtils.center(stp.name));
        logger.info(TextUtils.center("----- <> <> <> ------"));

	}

	@After
	public void updateJsonData() {
//		MongoDBUtils.testDataList.add(world.getTestDataJson());
//		MongoDBUtils.generatedTestDataList.add(world.getGeneratedDataJson());
//		MongoDBUtils.apiTestDataList.add(world.getApiTestDataJson());
//		MongoDBUtils.dataBaseInputList.add(world.getDataBaseInputTestDataJson());
//		MongoDBUtils.dataBaseOutputList.add(world.getDataBaseOutputJson());
	}

	@After
	public synchronized void after(Scenario scenario) {
		try {
			/// logger.info(scenario.getLines());
			logger.info("Pre after scenario pulling id:\t" + scenario.getId());

			for (Entry<Long, ScenarioTestResultData> test : RuntimeSingleton.getInstance().scenarios.entrySet()) {
				logger.info(TextUtils.format(test.getKey().toString(), test.getValue().testrail));

			}
			ScenarioTestResultData d = RuntimeSingleton.getInstance().scenarios.get(ID);
			d.end = System.currentTimeMillis();
			d.duration = d.end - d.start;


			tagNames = RuntimeSingleton.getInstance().getTags();


			Date date = new Date(d.duration);
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			String dateFormatted = formatter.format(date);
			logger.info("Clocked@" + dateFormatted);
			logger.info(TextUtils.center("<> <after scenario> <>"));
			logger.info(String.format("Completed step %s of %s", currentStepDefIndex, d.total));

			logger.info("Result queue check \t" + RuntimeSingleton.getInstance().steps.get(ID).size()
					+ " vs " + d.total);
            logger.info("stream --> steps <> <> <>");
			for (int i = 0; i < RuntimeSingleton.getInstance().steps.get(ID).size(); i++) {

				StepTestResultData stp = RuntimeSingleton.getInstance().steps.get(ID).get(i);
				RuntimeSingleton.getInstance().steps.get(ID).remove(i);

				if (stp.result == null) {
					stp.result = Type.SKIPPED;
				}

				RuntimeSingleton.getInstance().steps.get(ID).add(i, stp);
				sendStepStatus(stp);
			}
			// look for exception
			d.evaluateError(scenario);
			d.result = scenario.getStatus();
			d.tabulateSteps(RuntimeSingleton.getInstance().steps.get(ID));
			d = embedVideo(d);
			d.name = scenario.getName();
			this.scenDesc = d.name;
			logger.info("scenario id" + scenario.getId());
			logger.info("Sauce (" + d.sauceLink + ")");

			r.writeProp(scenDesc,scenario.getStatus().toString() );
			long dura = endT - startT  ; 
			r.writeProp(scenDesc+"_DURATION",Long.toString(dura)) ;


			embedScreenshot(scenario);
			try {
				if (this.world.driver != null) {
					logger.info("Closing " + world.getBrowser() + " browser");
					world.driver.quit();
				}
				if (this.world.ieDriver != null) {
					logger.info("Closing IE browser");
					world.ieDriver.quit();
				}
				Constants.world = world;
			} catch (Throwable e) {
				logger.info("Unable to quit the driver");
				e.printStackTrace();
			}
			// Trying to release the BYD user
			try {
				if (world.bydSessionUserDetails != null)
					world.bydSessionMonitor.releaseUser(world.bydSessionUserDetails.get("username"));
				else if (world.cooSessionMonitor != null)
					world.cooSessionMonitor.releaseUser(world.cooImpersonateSessionUserDetails.get("username"));
			} catch (Exception e) {
				logger.info("Exception: BYD - " + e.getMessage());
			}
			logger.info(TextUtils.center("<> <> <>"));
			RuntimeSingleton.getInstance().setData.addRuntimeDetails(d);
			scenario.getSourceTagNames().forEach(name -> {
                logger.info("Tag>\t" + name);

			});
			d.setupTagFields();
			d.suite = "E2E";
			sendScenarioStatus(d);
			System.out.println("Comments: " + comment);
			logger.info(TextUtils.center("----- <> <> <> ------"));
		} catch (InfluxDBException e) {
			logger.info("Influx exception occurred. Results may not have been pushed to the grafana db\n"
					+ e.getStackTrace());
		}

        // new RuntimeProperties().delete();
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		endT = System.currentTimeMillis(); 
		try {
			// logger.info(TextUtils.center("------- <> <after step> <> -------"));

			logger.info("Step Status:\t" + scenario.getStatus());
			if (world.getHost().equals("")) {
				setServerData();
			}
            RuntimeProperties props = new RuntimeProperties();
			comment = "Server Info: " + world.getHost() + " " + world.getPort() + " " + world.getOrderId() + " "
                + world.getProfileId() + " " + props.getEmail();

			// logger.info(TextUtils.centerClear("<> <> <>"));
			List<StepTestResultData> sr = RuntimeSingleton.getInstance().steps.get(ID);
			StepTestResultData r = sr.get(currentStepDefIndex);
			// update step results
			r.end = System.currentTimeMillis();
			r.result = scenario.getStatus();
			r.duration = r.end - r.start;
			sr.remove(currentStepDefIndex);
			sr.add(currentStepDefIndex++, r);
			// remove and add it back to the singleton for reporting at the end
			RuntimeSingleton.getInstance().steps.remove(ID);

			RuntimeSingleton.getInstance().steps.put(ID, sr);

		} catch (InfluxDBException e) {
			logger.info("Influx exception occurred. Results may not have been pushed to the grafana db\n"
					+ e.getStackTrace());
		}
		logger.info(TextUtils.center("----- <> <> <> ------"));
	}

	public void embedScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = null;
			List<String> scenarioFailed = world.getSauceWebLink();

			try {
				if (this.world.driver != null) {
					screenshot = takeFullPageScreenshot(scenario, this.world.driver);
				}
				if (this.world.ieDriver != null) {
					screenshot = ((TakesScreenshot) this.world.ieDriver).getScreenshotAs(OutputType.BYTES);
					scenario.embed(screenshot, "image/png");
					logger.info("Screen shot\t" + screenshot.length);
				}
				if (this.world.driver != null) {
					takeFullPageScreenshot(scenario, this.world.driver);
				}
				if (scenarioAppFail.equalsIgnoreCase("COO")) {
					try {
						if (this.world.driver != null) {
							screenshot = ((TakesScreenshot) this.world.driver).getScreenshotAs(OutputType.BYTES);
							scenario.embed(screenshot, "image/png");
							logger.info(">>>>>>>>> COO: Screen shot\t" + screenshot.length);
						} else {
							logger.info(">>>>>>>>> COO Failed: NO Screen shot from chrome driver >>>>>>>>>>>>>>");
						}
					} catch (Exception e1) {
						logger.info(
								"Exception : >>>>>>>>> COO Failed: NO Screen shot from chrome driver >>>>>>>>>>>>>>");
					}
				}
				RuntimeSingleton.getInstance().scenarios.get(ID).screenshot = screenshot;

				for (String links : scenarioFailed) {
					scenario.embed(links.getBytes(StandardCharsets.UTF_8), "text/html");
				}
				// Capture and embed screenshot for EBS if its available
				File screen = new File("OATS/DataBank/screenshot.png");
				if (screen.exists()) {
					byte[] fileContent = FileUtils.readFileToByteArray(screen);
					scenario.embed(fileContent, "image/png");

				}
				if (world.getSauceRest() != null)
					world.getSauceRest().jobFailed(world.getSessionId());
			} catch (Exception e) {
				logger.info("Exception thrown while attaching screenshot");
				e.printStackTrace();
			}
		}
		if (scenario.getStatus().toString().equalsIgnoreCase("PASSED") && world.getSauceRest() != null
				&& world.getSessionId() != null) {
			List<String> scenarioPassed = world.getSauceWebLink();
			for (String links : scenarioPassed) {
				scenario.embed(links.getBytes(StandardCharsets.UTF_8), "text/html");
			}
			world.getSauceRest().jobPassed(world.getSessionId());
		}
	}

	private byte[] takeFullPageScreenshot(Scenario scenario, WebDriver driver) throws IOException {
		byte[] output = null;
		ByteArrayOutputStream bi = new ByteArrayOutputStream();
		BufferedImage screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver).getImage();
		ImageIO.write(screenshot, "jpg", bi);
		bi.flush();
		output = bi.toByteArray();
		bi.close();
		scenario.embed(output, "image/png");
		return output;
	}

	private void sendStepStatus(StepTestResultData d) {
		Point point = null;

		tagNames.stream().filter(f -> f.matches("@\\w\\w_\\w\\w")).forEach(str -> {
          // logger.info("matched!!!!!" + str);
			d.locale = str.substring(1);
		});

		d.makeSureLocaleIsSetCorrectly();
        logger.info(d.getPointName());
        point = Point.measurement(d.getPointName())
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", RuntimeSingleton.getInstance().isMobile)
				.addField("platform", RuntimeSingleton.getInstance().platform)
				.addField("lid", RuntimeSingleton.getInstance().getId())
				.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", d.duration)
				.addField("start", d.start).addField("end", d.end).addField("line", d.line).addField("runid", d.rid)
				.addField("suite", d.suite).addField("result", d.result.toString()).addField("step", d.name)
				.addField("application", d.application).addField("env", d.env).addField("scenario", d.scenarioName)
				.addField("testrail", d.testrail).addField("bucket", d.bucket).addField("browser", d.browser.name())
            .tag("result", d.result.toString()).tag("env", d.env)
				.tag("browser", d.browser.name()).tag("application", d.application).tag("testrail", d.testrail)
				.tag("database", d.bucket).tag("suite", d.suite).tag("locale", d.locale).build();

		ResultSender.send(point, ResultSender.TABLE.STEPS);
		d.printStep(); 
		logger.info(TextUtils.center("<> <> step sent <> <>"));

		// Construct JSON's step array data
		JSONObject DataSentToPostgreSQL = RuntimeSingleton.getInstance().GetDataSentToPostgreSQL();

		JSONObject newStepObj = new JSONObject();
		newStepObj.put("time", System.currentTimeMillis());
		newStepObj.put("lid", RuntimeSingleton.getInstance().getId());
		newStepObj.put("runId", d.rid);
		newStepObj.put("step", d.name);
		newStepObj.put("featureName", d.featureName);
		newStepObj.put("scenarioName", d.scenarioName);
		newStepObj.put("testRail", d.testrail);
		newStepObj.put("duration", d.duration);
		newStepObj.put("start", d.start);
		newStepObj.put("end", d.end);
		newStepObj.put("line", d.line);
		newStepObj.put("result", d.result.toString());
		JSONArray array = (JSONArray) DataSentToPostgreSQL.get("step");
		array.add(newStepObj);
		RuntimeSingleton.getInstance().SetDataSentToPostgreSQL(DataSentToPostgreSQL);
	}

	private synchronized void sendScenarioStatus(ScenarioTestResultData d) {
		d.checkBrowser();
		String base64Encoded = "no image";
		if (RuntimeSingleton.getInstance().isMobile == true) {
			d.suite = "Mobile";
		}

		if (d.screenshot != null) {
			String encodedString = Base64.getEncoder().encodeToString(d.screenshot);
			base64Encoded = "data:image/png;base64," + new String(d.screenshot, StandardCharsets.UTF_8);
			base64Encoded = "  <img src=\"data:image/gif;base64," + encodedString + "\" />";
		}
        // logger.info(base64Encoded);
		d.setSauce();
		if (d.sauceLink == null)
			d.sauceLink = "none";
		// for HealthCheck E2E only - @HC tagname -- appending to if any error with data
		// i.e., orderNumber and emailid
		tagNames = RuntimeSingleton.getInstance().getTags();
		boolean sendScenarioReport = true;// for healthcheck
		String verifyTagName = "";
		System.out.println("<<<<< Verifying TagName: @healthCheck in tags" + tagNames);
		for (int i = 0; i < tagNames.size(); i++) {
			// System.out.println(tagNames.get(i));
			if (tagNames.get(i).equals("@healthCheck")) {
				sendScenarioReport = false;
			}
			if (tagNames.get(i).equals("@HC") || tagNames.get(i).equals("@HCE2EALL")) {
				verifyTagName = "E2E";
				d.application = "E2E";
			}
			if (tagNames.get(i).equals("@CTR")) {
				d.application = "CTR";
			}
		}
		if (world.getHost().equals("")) {
			comment = "Server Info: " + world.getHost() + " " + world.getPort() + " " + world.getOrderId() + " "
					+ world.getProfileId();
		}
		d.comment = comment;
		logger.info("comment:\t" + comment);
		logger.info("test error:\t" + d.testError);

		comment = comment.replaceAll("&", "");

		// for E2E to append error message to comment
		if (verifyTagName.equals("E2E") && !d.comment.equals("") && !d.testError.equals("none")) {
			comment = comment + " >>>> ERR: " + d.testError;
			d.comment = comment;
			logger.info("E2E Test Error:\t" + d.comment);
		} else if (verifyTagName.equals("E2E") && !d.comment.equals("")) {
			d.comment = comment;
			logger.info("E2E Test Error:\t" + d.comment);
		} else {
			d.flattenError();
		}
		d.printScenario();

		d.serverInfo = parseCommentServerInfo(comment);
		String et = null;
		if (d.result.equals(Type.PASSED)) {
			et = ErrorType.Validation.toString();
			comment = "the test passed its designated validation";
		} else {
			et = d.exception.getType().toString();
		}

		if (d.exception != null) {
			/*
			 * for (StackTraceElement e : d.exception.getStackTrace()) { comment +=
			 * e.toString(); }
			 */
//			comment = d.exception.getReason().replaceAll("com\\.qentelli\\.automation\\.exceptions\\.base\\.","");
			comment = TextUtils.cleanupExceptionNames(d.exception.toString());
		}

		if (sendScenarioReport) {

			Point point = null;
			d.makeSureLocaleIsSetCorrectly();

            point = Point.measurement(d.getPointName())
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
					.addField("mobile", RuntimeSingleton.getInstance().isMobile)
					.addField("platform", RuntimeSingleton.getInstance().platform)
					.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", d.duration)
					.addField("start", d.start).addField("end", d.end).addField("runid", d.rid)
					.addField("suite", d.suite).addField("lid", RuntimeSingleton.getInstance().getId())
					.addField("total", d.total).addField("env", d.env).addField("name", d.name)
					.addField("browser", d.browser.name()).addField("skipped", d.skipped).addField("failed", d.failed)
					.addField("feature", d.featureName).addField("passed", d.passed)
					.addField("application", d.application).addField("testrail", d.testrail)
					.addField("video", d.sauceVideo).addField("sauce", d.sauceLink).addField("sauceHtml", d.sauceHtml)
                .addField("result", d.result.toString())// .addField("shot", base64Encoded)
					.addField("testlink", d.testrailLink).addField("undefined", d.undefined)
					.addField("syndicationTime", scenarioAppFail + " >> " + syndTime).addField("comment", comment)
					.addField("serverinfo", d.serverInfo).addField("locale", d.locale).addField("reason", et)
					.addField("bucket", d.bucket)
                /* .addField("reason", d.exception.getLabel()) */.tag("scenario", d.name)
                .addField("JRUNID", d.rid)
                .addField("SID", SID).tag("result", d.result.toString())
					.tag("env", d.env).tag("browser", d.browser.name()).tag("suite", d.suite)
					.tag("application", scenarioAppFail).tag("testrail", d.testrail)
					.tag("locale", RuntimeSingleton.getInstance().locale).build();
			ResultSender.send(point, ResultSender.TABLE.SCENARIOS);

			JSONObject DataSentToPostgreSQL = RuntimeSingleton.getInstance().GetDataSentToPostgreSQL();
			JSONObject newScenarioObj = new JSONObject();
			newScenarioObj.put("time", System.currentTimeMillis());
			newScenarioObj.put("lid", RuntimeSingleton.getInstance().getId());
			newScenarioObj.put("runId", d.rid);
			newScenarioObj.put("featureName", d.featureName);
			newScenarioObj.put("scenarioName", d.name);
			newScenarioObj.put("testRail", d.testrail);
			newScenarioObj.put("locale", d.locale);
			newScenarioObj.put("duration", d.duration);
			newScenarioObj.put("start", d.start);
			newScenarioObj.put("end", d.end);
			newScenarioObj.put("totalSteps", d.total);
			newScenarioObj.put("passed", d.passed);
			newScenarioObj.put("skipped", d.skipped);
			newScenarioObj.put("failed", d.failed);
			newScenarioObj.put("result", d.result.toString());
			newScenarioObj.put("testRailLink", d.testrailLink);
			newScenarioObj.put("comment", comment);
			newScenarioObj.put("syndicationTime", syndTime);
			newScenarioObj.put("serverInfo", d.serverInfo);
			newScenarioObj.put("errorType", et);
			newScenarioObj.put("sauceVideo", d.sauceVideo);
			newScenarioObj.put("sauceLink", d.sauceLink);
			newScenarioObj.put("sauceHtml", d.sauceHtml);

			JSONArray array = (JSONArray) DataSentToPostgreSQL.get("scenario");
			array.add(newScenarioObj);
			RuntimeSingleton.getInstance().SetDataSentToPostgreSQL(DataSentToPostgreSQL);

		}

	}

	private static final String regex = "^[A-Z][0-9]"; // alpha-numeric uppercase

	public static boolean isUpperCase(String str) {
		return Pattern.compile(regex).matcher(str).find();
	}

	public int getStepCount(Scenario s) {
		Field f;
		TestCase r = null;
		try {
			f = s.getClass().getDeclaredField("testCase");
			f.setAccessible(true);
			r = (TestCase) f.get(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<PickleStepTestStep> stepDefs = r.getTestSteps().stream().filter(x -> x instanceof PickleStepTestStep)
				.map(x -> (PickleStepTestStep) x).collect(Collectors.toList());
		return stepDefs.size();
	}

	public List<PickleStepTestStep> getSteps(Scenario s) {
		Field f;
		TestCase r = null;

		try {
			f = s.getClass().getDeclaredField("testCase");
			f.setAccessible(true);
			r = (TestCase) f.get(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<PickleStepTestStep> stepDefs = r.getTestSteps().stream().filter(x -> x instanceof PickleStepTestStep)
				.map(x -> (PickleStepTestStep) x).collect(Collectors.toList());

		return stepDefs;
	}

	private synchronized void worldInit() {
		// For E2E to execute all scripts with respective locale
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		boolean langExecE2E = false;
		// locale(all small letters) is from jenkins... logic below steps for multi
		// locale execution if it is ALL
		System.out.println("Locale to execute is  " + System.getProperty("locale"));
        // }
//		if ((System.getProperty("locale") != null && System.getProperty("locale").equalsIgnoreCase("ALL"))
//				) {
//			tagNames = RuntimeSingleton.getInstance().getTags();
//			for (int i = 0; i < tagNames.size(); i++) {
//				logger.info(tagNames.get(i));
//
//				if (tagNames.get(i).equals("@HCE2EALL")) {
//					world.setE2ETrue();
//                    logger.info("Feature Name: " + RuntimeSingleton.getInstance().getFeatureName());
//                    logger.info("Scenario Name: " + RuntimeSingleton.getInstance().getScenario().getName());
//                    logger.info(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
//							.getParameter("locale"));
//                    logger.info(world.getLocale());
//                    logger.info(RuntimeSingleton.getInstance().locale);
//					langExecE2E = true;
//				}
//			}
//
//		} else {
//			System.setProperty("locale", System.getProperty("locale"));
//			tagNames = RuntimeSingleton.getInstance().getTags();
//			for (int i = 0; i < tagNames.size(); i++) {
//				logger.info(tagNames.get(i));
//
//				if (tagNames.get(i).equals("@HCE2E")) {
//					world.setE2ETrue();
//					break;
//				}
//			}
//		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// App Related properties
		String locale = System.getProperty("locale");
        ;
		String driverType = System.getProperty("driverType");
		if (locale != null && !locale.isEmpty()) {
			world.setLocale(locale);
		} else {
			world.setLocale(
					Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("locale"));
		}
		RuntimeSingleton.getInstance().locale = world.getLocale();

		switch (world.getLocale().toLowerCase()) {
		case "en_ca":
			world.setFormattedLocale(new Locale("en", "CA"));
			world.setLocaleResource(
					ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", new Locale("en", "CA")));
			break;
		case "en_gb":
			world.setFormattedLocale(new Locale("en", "GB"));
			world.setLocaleResource(
					ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", new Locale("en", "GB")));
			break;
		case "en_us":
			world.setFormattedLocale(Locale.US);
			world.setLocaleResource(ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", Locale.US));
			break;
		case "es_us":
			world.setFormattedLocale(new Locale("es", "US"));
			world.setLocaleResource(
					ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", new Locale("es", "US")));
			break;
		case "fr_ca":
			world.setFormattedLocale(Locale.CANADA_FRENCH);
			world.setLocaleResource(
					ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", Locale.CANADA_FRENCH));
			break;
		case "fr_fr":
			world.setFormattedLocale(Locale.FRANCE);
			world.setLocaleResource(ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", Locale.FRANCE));
			break;
		default:
			world.setFormattedLocale(Locale.US);
			world.setLocaleResource(ResourceBundle.getBundle("com.qentelli.automation.locales.Locale", Locale.US));
			break;
		}

		if (driverType != null && !driverType.isEmpty()) {
		} else {
			driverType = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("driverType");
		}

		switch (driverType.toLowerCase()) {
		case "local":
			world.setDriverType(Constants.DRIVERTYPE.LOCAL);
			break;
		case "sauce":
			world.setDriverType(Constants.DRIVERTYPE.SAUCE);
			break;
		case "grid":
			world.setDriverType(Constants.DRIVERTYPE.GRID);
			break;
		default:
			world.setDriverType(Constants.DRIVERTYPE.LOCAL);
			break;
		}

		// Sauce Properties
		String tunnelRequired = System.getProperty("tunnel");
		if (tunnelRequired != null && !tunnelRequired.isEmpty()) {
			world.setTunnelRequired(tunnelRequired);
		} else {
			world.setTunnelRequired(
					Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("tunnel"));
		}

		// Browser Related Properties
		String browser = System.getProperty("browser");
		String browserVersion = System.getProperty("browserVersion");
		String browserPlatform = System.getProperty("browserPlatform");
		String environment = System.getProperty("environment");

		if (browser != null && !browser.isEmpty()) {
			world.setBrowser(Constants.BROWSER.valueOf(browser));

		} else {
			browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
			world.setBrowser(Constants.BROWSER.valueOf(browser));
		}

		if (browserVersion != null && !browserVersion.isEmpty()) {
			world.setBrowserVersion(browserVersion);
		} else {
			world.setBrowserVersion(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("browserVersion"));
		}
		if (browserPlatform != null && !browserPlatform.isEmpty()) {
			world.setBrowserPlatform(browserPlatform);
		} else {
			world.setBrowserPlatform(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("browserPlatform"));
		}
		if (environment != null && !environment.isEmpty()) {
			try {
				world.setTestEnvironment(environment);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				world.setTestEnvironment(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
						.getParameter("environment"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("<><><><>ENV is set as: " + world.getTestEnvironment());
		world.factoryConfig = new FactoryConfig(world.getTestEnvironment(), world.getFormattedLocale());
		world.factoryData = new FactoryData();
		world.factoryData.setEnv(world.getTestEnvironment());
	}

	private void initMobile() {
		try {
			String mobilePlatform = System.getProperty("mobilePlatform");
			String mobileDeviceName = System.getProperty("mobileDeviceName");
			String mobileDeviceOrientation = System.getProperty("mobileDeviceOrientation");
			String mobilePlatformVersion = System.getProperty("mobilePlatformVersion");
			String mobilePlatformName = System.getProperty("mobilePlatformName");
			String mobileBrowser = System.getProperty("mobileBrowser");

			if (world.isMobile()) {
				if (mobilePlatform != null && !mobilePlatform.isEmpty()) {
					world.setMobilePlatform(mobilePlatform);
				} else {
					world.setMobilePlatform(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
							.getParameter("mobilePlatform"));
				}

				if (mobileDeviceName != null && !mobileDeviceName.isEmpty()) {
					world.setMobileDeviceName(mobileDeviceName);
				} else {
					world.setMobileDeviceName(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
							.getParameter("mobileDeviceName"));
				}

				if (mobileDeviceOrientation != null && !mobileDeviceOrientation.isEmpty()) {
					world.setMobileDeviceOrientation(mobileDeviceOrientation);
				} else {
					world.setMobileDeviceOrientation(Reporter.getCurrentTestResult().getTestContext()
							.getCurrentXmlTest().getParameter("mobileDeviceOrientation"));
				}

				if (mobilePlatformVersion != null && !mobilePlatformVersion.isEmpty()) {
					world.setMobilePlatformVersion(mobilePlatformVersion);
				} else {
					world.setMobilePlatformVersion(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
							.getParameter("mobilePlatformVersion"));
				}

				if (mobilePlatformName != null && !mobilePlatformName.isEmpty()) {
					world.setMobilePlatformName(mobilePlatformName);
				} else {
					world.setMobilePlatformName(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
							.getParameter("mobilePlatformName"));
				}

				if (mobileBrowser != null && !mobileBrowser.isEmpty()) {
					world.setMobileBrowser(mobileBrowser);
				} else {
					world.setMobileBrowser(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
							.getParameter("mobileBrowser"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setServerData() {
		String pageSource;
		try {
			pageSource = world.driver.getPageSource();
		} catch (Exception e) {
			pageSource = "";
		}
		Pattern pHost = Pattern.compile("Hostname: .* ");
		Matcher mHost = pHost.matcher(pageSource);

		Pattern pPort = Pattern.compile("Host Port: \\d+");
		Matcher mPort = pPort.matcher(pageSource);

		Pattern pProfileID = Pattern.compile("debug profileId: \\d+");
		Matcher mProfileID = pProfileID.matcher(pageSource);

		Pattern pOrderID = Pattern.compile("orderId: STORE_\\d+");
		Matcher mOrderID = pOrderID.matcher(pageSource);

		logger.info(TextUtils.center("------- <> <Server Info> <> -------"));
		;
		while (mHost.find()) {
			logger.info(mHost.group());
			world.setHost(mHost.group());
		}
		while (mPort.find()) {
			logger.info(mPort.group());
			world.setPort(mPort.group());
		}

		while (mProfileID.find()) {
			logger.info(mProfileID.group());
			world.setProfileId(mProfileID.group());
		}

		while (mOrderID.find()) {
			logger.info(mOrderID.group());
			world.setOrderId(mOrderID.group());
		}
		logger.info(TextUtils.center("------- <> <> <> -------"));
	}

	private ScenarioTestResultData embedVideo(ScenarioTestResultData s) {
		String sessionId = ((RemoteWebDriver) world.driver).getSessionId().toString();
		logger.info("Session:\t" + sessionId);
		if (world.getDriverType().equals(Constants.DRIVERTYPE.SAUCE)) {
			String tempWebLink = world.getSauceRest().getPublicJobLink(sessionId);
			logger.info("Session:\t" + tempWebLink);
			s.sauceLink = tempWebLink;
			String authToken = tempWebLink.substring(tempWebLink.indexOf("auth="));
			logger.info("Auth:\t" + authToken);
			// TODO: make the auth a property that comes from config.props (AUTH_TOKEN)
			s.sauceVideo = "https://assets.saucelabs.com/jobs/" + sessionId + "/video.mp4?" + authToken;
		}
		return s;
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

	private String getTimeDuration(long appDuration) {
		String retValue = String.format("%.2f", (double) (appDuration / 1000) / 60);
		return retValue;
	}

	private String syndicationTime() {
		StringBuilder strBuilder = new StringBuilder();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
          strBuilder.append("<div>" + "</div>");
			strBuilder.append("<div>ATG Creation: " + world.getAtgCreationDate() + "    Submittion Date: "
					+ world.getAtgSubmitDate() + "</div>");

          logger.info("ATG Order: " + " and Creation Date: " + world.getAtgCreationDate()
					+ " and its Submition Date: " + world.getAtgSubmitDate());
          logger.info("IDM Order: " + " and Creation Date: " + world.getIDMCreationDate());
			Date date1 = format.parse(world.getAtgSubmitDate());
			Date date2 = format.parse(world.getIDMCreationDate());
			Long date3 = date2.getTime() - date1.getTime();

			logger.info("IDM Date Difference after ATG Submit date :::: " + getTimeDuration(date3));
			strBuilder.append("<div>IDM Creation: " + world.getIDMCreationDate() + " Diff(IDM-ATG Submittion): "
					+ getTimeDuration(date3) + "</div>");
		} catch (Exception date1) {
			logger.info("IDM Date exception ???????? " + date1.getLocalizedMessage());
		}
        logger.info("EBS : ATG Order: " + " and its reached to EBS Creation Date: "
				+ world.getEBSCreationDate());
		try {
			Date date1 = format.parse(world.getAtgSubmitDate());
			Date date2 = format.parse(world.getEBSCreationDate());
			Long date3 = date2.getTime() - date1.getTime();

			logger.info("EBS Date Difference after ATG Submit date :::: " + getTimeDuration(date3));
			strBuilder.append("<div>EBS Creation: " + world.getEBSCreationDate() + " Diff(EBS-ATG Submittion): "
					+ getTimeDuration(date3) + "</div>");
		} catch (Exception date1) {
			logger.info("EBS Date exception ????????" + date1.getLocalizedMessage());
		}
        logger.info("EBS -> iCentris reach,  ATG Order: "
				+ " and its reached to iCentris Creation Date: " + world.getEBSiCentrisDate());
		try {
			Date date1 = format.parse(world.getAtgSubmitDate());
			Date date2 = format.parse(world.getEBSiCentrisDate());
			Long date3 = date2.getTime() - date1.getTime();

			logger.info("EBS -> iCentris ----- Date Difference after ATG Submit date :::: " + getTimeDuration(date3));
			strBuilder.append("<div>EBS -> iCentris Creation: " + world.getEBSiCentrisDate()
					+ " Diff(EBS-ATG Submittion): " + getTimeDuration(date3) + "</div>");
		} catch (Exception date1) {
			logger.info("EBS -> iCentris Date exception ????????" + date1.getLocalizedMessage());
		}
        logger.info("Coach Office reach,  ATG Order: " + " and its reached to COO Creation Date: "
				+ world.getCOOCreationDate());
		try {
			Date date1 = format.parse(world.getAtgSubmitDate());
			Date date2 = format.parse(world.getCOOCreationDate());
			Long date3 = date2.getTime() - date1.getTime();

			logger.info("COO ----- Date Difference after ATG Submit date :::: " + getTimeDuration(date3));
			strBuilder.append("<div>COO Creation: " + world.getEBSiCentrisDate() + " Diff(COO-ATG Submittion): "
					+ getTimeDuration(date3) + "</div>");
		} catch (Exception date1) {
			logger.info("COO ----- Date exception ????????" + date1.getLocalizedMessage());
		}
		return strBuilder.toString();
	}
}
