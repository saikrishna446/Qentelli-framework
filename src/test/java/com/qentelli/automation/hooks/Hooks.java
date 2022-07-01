package com.qentelli.automation.hooks;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.influxdb.dto.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.qentelli.automation.common.Constants;
import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.custom.SauceException;
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
import cucumber.api.Scenario;
import cucumber.api.TestCase;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Hooks {
	static Logger logger = LogManager.getLogger(Hooks.class);
	private final long ID = Thread.currentThread().getId();
	RuntimeProperties props = new RuntimeProperties(true);
	int currentStepDefIndex = 0;
	private String scenDesc;
	private World world;
	String comment;

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


		currentStepDefIndex = 0;
		try {
			// let's store the number of scenarios in a set context
			logger.info(TextUtils.center("<> <before scenario> <>"));

			String tags = "";
			for (String tag : s.getSourceTagNames()) {
				tags += TextUtils.ConsoleColors.WHITE_BRIGHT + "<>" + TextUtils.ConsoleColors.RESET + tag;
			}
			logger.info(tags);
			ScenarioTestResultData d = new ScenarioTestResultData(s.getName(), RuntimeSingleton.getInstance().runid);
			d.total = getStepCount(s);

			d.tags.addAll(s.getSourceTagNames());
			d.setupTagFields();
			d.renameScenario();
			world.setScenarioName(d.name);
			d.featureName = s.getId().substring(s.getId().lastIndexOf("/") + 1, s.getId().lastIndexOf(".feature"));

			Scenario scenario = s;
			this.scenDesc = d.name;
			world.setScenario(scenario);
			logger.info(this.scenDesc);
			RuntimeSingleton.getInstance().setTags(scenario.getSourceTagNames());
			RuntimeSingleton.getInstance().setScenario(s);
			worldInit(d);

			d.browser = world.getBrowser();
			d.env = world.getTestEnvironment();
			d.locale = world.getLocale();
			List<StepTestResultData> intake = new ArrayList<StepTestResultData>();
			for (PickleStepTestStep ps : getSteps(s)) {

				StepTestResultData data = new StepTestResultData(RuntimeSingleton.getInstance().runid);
				data.addRuntimeDetails(d);
				data.name = ps.getStepText();
				data.line = ps.getStepLine();
				data.result = null;
				data.scenarioName = s.getName();
				data.env = d.env;
				data.testrail = d.testrail;
				data.bucket = d.bucket;
				intake.add(data);
			}
//			logger.info(currentStepDefIndex +" SIZE:"+intake.size()) ; 
//			logger.info( " SIZE:"+intake.size()) ; 
			props.writeProp("TESTRAIL", d.testrail);
			RuntimeSingleton.getInstance().browserPlatform = d.browser.name();
			RuntimeSingleton.getInstance().setData.env = d.env;
			RuntimeSingleton.getInstance().setData.browser = d.browser;

			d.rid = RuntimeSingleton.getInstance().runid;
			d.s = s;
			d.tags2 = s.getSourceTagNames();
			RuntimeSingleton.getInstance().scenarios.put(ID, d);

			RuntimeSingleton.getInstance().steps.put(ID, intake);

			// Mobile
			String isMobile = System.getProperty("isMobile");
			if (isMobile != null && !isMobile.isEmpty()) {
				world.setMobile(Boolean.parseBoolean(isMobile));
			} else {
				world.setMobile(Boolean.parseBoolean(
						Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("isMobile")));
			}

			if (world.isMobile()) {
				initMobile();
			}

			world.getDriver();
			d.printScenario();
		} catch (SauceException e) {
			throw (e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// this is to delete the oats related saved screenshot
			File screen = new File("OATS/DataBank/screenshot.png");
			screen.delete();
		}
		logger.info(TextUtils.center("<> Execution Environment : " + world.getExecutionEnvironment() + " <>"));

	}

	@BeforeStep
	public void beforeStep(Scenario s) throws InterruptedException {
		logger.info(TextUtils.center("<> <before step> <>"));
		if (RuntimeSingleton.getInstance().steps.get(ID) == null)
			return;
		StepTestResultData stp = RuntimeSingleton.getInstance().steps.get(ID).get(currentStepDefIndex);
		stp.start = System.currentTimeMillis();
		RuntimeSingleton.getInstance().steps.get(ID).remove(currentStepDefIndex);
		RuntimeSingleton.getInstance().steps.get(ID).add(currentStepDefIndex, stp);
		logger.info(TextUtils.center(stp.name));
		logger.info(TextUtils.center("----- <> <> <> ------"));

	}

	@After
	public synchronized void after(Scenario scenario) {
		logger.info(ID+" <ID> ");
		logger.info("scenarios "+RuntimeSingleton.getInstance().scenarios.size());
		logger.info(RuntimeSingleton.getInstance().steps.get(ID).size()+" <steps> ");
		ScenarioTestResultData d = RuntimeSingleton.getInstance().scenarios.get(ID);
		d.end = System.currentTimeMillis();
		d.duration = d.end - d.start;

		Date date = new Date(d.duration);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateFormatted = formatter.format(date);
		logger.info("Clocked@" + dateFormatted);
		logger.info(TextUtils.center("<> <after scenario> <>"));
		logger.info(TextUtils.format(Long.toString(ID), d.testrail));

		logger.info(String.format("Completed steps (all should match) %s of %s of %s",
				RuntimeSingleton.getInstance().steps.get(ID).size(), currentStepDefIndex, d.total));

		d.evaluateError(scenario);
		d.result = scenario.getStatus();
		d.tabulateSteps(RuntimeSingleton.getInstance().steps.get(ID));
		d = embedVideo(d);
		d.name = scenario.getName();
		this.scenDesc = d.name;
		logger.info("scenario id" + scenario.getId());


		props.writeProp(scenDesc, scenario.getStatus().toString());
		props.writeProp(scenDesc + "_DURATION", Long.toString(d.duration));
		embedScreenshot(scenario);
		if (this.world.driver != null) {
			logger.info("Closing " + world.getBrowser() + " browser");
			world.driver.quit();
		} else if (this.world.ieDriver != null) {
			logger.info("Closing IE browser");
			world.ieDriver.quit();
		}
		// Trying to release the BYD user
		if (world.bydSessionUserDetails != null)
			world.bydSessionMonitor.releaseUser(world.bydSessionUserDetails.get("username"));
		else if (world.cooSessionMonitor != null)
			world.cooSessionMonitor.releaseUser(world.cooImpersonateSessionUserDetails.get("username"));

		RuntimeSingleton.getInstance().setData.addRuntimeDetails(d);
		d.result = scenario.getStatus();
		d.sendScenarioTestResults(world);
		logger.info("Sauce (" + d.sauceLink + ")");
		// logger.info(TextUtils.center("----- <> <> <> ------"));
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		logger.info(TextUtils.center(currentStepDefIndex + "------- <> <after step> <> -------" + currentStepDefIndex));

		logger.info("Step Status:\t" + scenario.getStatus());
		if (world.getHost().equals("")) {
			try {
				setServerData();
			} catch (Exception e) {
				logger.info("Not able to get host/node data");
			}
		}

		comment = "Server Info: " + world.getHost() + " " + world.getPort() + " " + world.getOrderId() + " "
				+ world.getProfileId();

		// logger.info(TextUtils.centerClear("<> <> <>"));
		List<StepTestResultData> sr = RuntimeSingleton.getInstance().steps.get(ID);
		if (sr == null)
			return;
		StepTestResultData r = sr.get(currentStepDefIndex);
		// update step results
		r.end = System.currentTimeMillis();
		r.result = scenario.getStatus();
		r.duration = r.end - r.start;
		sr.remove(currentStepDefIndex);
		sr.add(currentStepDefIndex++, r);
		logger.info("current id " + currentStepDefIndex);
		// remove and add it back to the singleton for reporting at the end
		RuntimeSingleton.getInstance().steps.remove(ID);
		RuntimeSingleton.getInstance().steps.put(ID, sr);
		int i = 1; // start at one

		sendStepStatus(r);

		logger.info(TextUtils.center("----- <> <> <> ------"));
	}

	public void embedScreenshot(Scenario scenario) {
		if (this.world.driver == null) {
			logger.error("Sauce is null");
			return;
		}
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

		d.checkBrowser();
		d.makeSureDataBaseIsSetCorrectly();
		d.makeSureLocaleIsSetCorrectly();
		if (d.getPointName().contains("_none_")) {
			d.locale = "none";
		}

		Point point = null;

		logger.info(TextUtils.center("<> <> step sent <> <>@"));
		if (d.name.contains("compassAPI"))
			return;

		point = Point.measurement(d.getPointName()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("mobile", RuntimeSingleton.getInstance().isMobile)
				.addField("platform", RuntimeSingleton.getInstance().platform)
				.addField("lid", RuntimeSingleton.getInstance().getId())
				.addField("user", RuntimeSingleton.getInstance().whoami).addField("duration", d.duration)
				.addField("start", d.start).addField("end", d.end).addField("line", d.line).addField("runid", d.rid)
				.addField("suite", d.suite).addField("result", d.result.toString()).addField("step", d.name)
				.addField("application", d.application).addField("env", d.env).addField("scenario", d.scenarioName)
				.addField("testrail", d.testrail).addField("locale", d.locale).addField("bucket", d.bucket)
				.addField("project", d.project).addField("browser", d.browser.name()).tag("result", d.result.toString())
				.tag("env", d.env).tag("project", d.project).tag("browser", d.browser.name())
				.tag("application", d.application).tag("testrail", d.testrail).tag("suite", d.suite)
				.tag("locale", d.locale).build();

		ResultSender.send(point, ResultSender.TABLE.STEPS);

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

		d.printStep();

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

	private synchronized void worldInit(ScenarioTestResultData scenarioTestResultData) {
		// App Related properties
		String locale = System.getProperty("locale");
		if (StringUtils.isEmpty(locale)) {
			locale = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("locale");
		}

		String driverType = System.getProperty("driverType");
		if (world.getLocale() == null) {
			if (scenarioTestResultData.taggedLocales.size() == 0
					|| scenarioTestResultData.taggedLocales.contains(locale)) {
				world.setLocale(locale);
				String[] localeParams = locale.split("_");
				Locale.setDefault(new Locale(localeParams[0], localeParams[1]));
			} else {
				logger.info("This test is specific to " + scenarioTestResultData.taggedLocales.get(0)
						+ " locale and not marked for " + locale + " locale. Hence running this particular test on "
						+ scenarioTestResultData.taggedLocales.get(0));
				world.setLocale(scenarioTestResultData.taggedLocales.get(0));
				String[] localeParams = scenarioTestResultData.taggedLocales.get(0).split("_");
				Locale.setDefault(new Locale(localeParams[0], localeParams[1]));
			}
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
			world.setFormattedLocale(new Locale("fr", "FR"));
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

		String tunnelID = System.getProperty("tunnel_id");
		if (tunnelID != null && !tunnelID.isEmpty()) {
			world.setSauceTunnelId(tunnelID);
		} else {
			world.setSauceTunnelId(
					Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("tunnel_id"));
		}

		// Browser Related Properties
		String browser = System.getProperty("browser");
		String browserVersion = System.getProperty("browserVersion");
		String browserPlatform = System.getProperty("browserPlatform");
		String environment = System.getProperty("environment");

		if (browser != null && !browser.isEmpty()) {
			world.setBrowser(Constants.BROWSER.valueOf(browser.toUpperCase()));
		} else {
			browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
			world.setBrowser(Constants.BROWSER.valueOf(browser.toUpperCase()));
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
		if (((RemoteWebDriver) world.driver) == null) {
			return s;
		}

		String sessionId = ((RemoteWebDriver) world.driver).getSessionId().toString();
		if (world.getDriverType().equals(Constants.DRIVERTYPE.SAUCE)) {
			String tempWebLink = world.getSauceRest().getPublicJobLink(sessionId);
			s.sauceLink = tempWebLink;
			String authToken = tempWebLink.substring(tempWebLink.indexOf("auth="));
			s.sauceVideo = "https://assets.saucelabs.com/jobs/" + sessionId + "/video.mp4?" + authToken;
		}
		return s;
	}
}
