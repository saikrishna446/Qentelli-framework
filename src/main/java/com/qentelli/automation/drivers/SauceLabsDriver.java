package com.qentelli.automation.drivers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.custom.SauceException;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.TextUtils.ConsoleColors;
import com.saucelabs.saucerest.SauceREST;

public class SauceLabsDriver {
  static Logger logger = LogManager.getLogger(SauceLabsDriver.class);
	private World world;
	private ResourceBundle configLib;
	private WebDriver driver;
	private String USERNAME, ACCESS_KEY;
	int profileCnt = 0;

	public SauceLabsDriver(World world) {
		this.world = world;
	}

	// To initialize Sauce ie driver
	public WebDriver getSauceIEDriver() throws SauceException {

		MutableCapabilities sauceOptions = new MutableCapabilities();
		MutableCapabilities browserOptions;

		SauceREST sauceRest = world.getSauceRest();
		try {
			// Reading config info from config file
			File file = new File("config");
			URL[] urls = { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			configLib = ResourceBundle.getBundle("config", Locale.getDefault(), loader);
			// Sauce user name
			USERNAME = configLib.getString("SAUCE_USERNAME");
			// Sauce key
			ACCESS_KEY = configLib.getString("SAUCE_ACCESS_KEY");

			sauceOptions.setCapability("username", USERNAME);
			sauceOptions.setCapability("accessKey", ACCESS_KEY);
			sauceOptions.setCapability("name", world.getScenarioName());
			sauceOptions.setCapability("commandTimeout", 600);
			sauceOptions.setCapability("idleTimeout", 10000);

			browserOptions = new InternetExplorerOptions();
			browserOptions.setCapability("platformName", configLib.getString("IE_PLATFORM"));
			browserOptions.setCapability("browserVersion", configLib.getString("IE_VERSION"));
			browserOptions.setCapability("name", world.getScenarioName());

			browserOptions.setCapability("sauce:options", sauceOptions);
			this.driver = new RemoteWebDriver(new URL("https://ondemand.saucelabs.com/wd/hub"), browserOptions);
		} catch (NullPointerException e) {
			throw new SauceException("SessionId was null");
		} catch (Exception e) {
			driver = null;
		}
		String sessionId = ((RemoteWebDriver) this.driver).getSessionId().toString();
		String tempWebLink = sauceRest.getPublicJobLink(sessionId);
		world.setSauceWebLink(tempWebLink);
		return driver;
	}

	// To initialize Sauce webdriver instances depending on parameters passed in
	// testng
	public synchronized WebDriver getDriver() throws Exception {
		MutableCapabilities sauceOptions = new MutableCapabilities();
		MutableCapabilities browserOptions;
		String PLATFORM = null;
		SauceREST sauceRest = null;
		try {
			// Reading config info from config file
			File file = new File("config");
			URL[] urls = { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			configLib = ResourceBundle.getBundle("config", Locale.getDefault(), loader);
			USERNAME = configLib.getString("SAUCE_USERNAME");
			ACCESS_KEY = configLib.getString("SAUCE_ACCESS_KEY");

			sauceOptions.setCapability("username", USERNAME);
			sauceOptions.setCapability("accessKey", ACCESS_KEY);
			sauceOptions.setCapability("name", world.getScenarioName());
			sauceOptions.setCapability("commandTimeout", 600);
			sauceOptions.setCapability("idleTimeout", 10000);
			sauceOptions.setCapability("maxDuration", 3600);
			sauceOptions.setCapability("screenResolution", "1280x800");
			sauceOptions.setCapability("extendedDebugging", true);

			sauceRest = new SauceREST(USERNAME, ACCESS_KEY);
			world.setSauceRest(sauceRest);
			RuntimeSingleton.getInstance().isMobile = world.isMobile();
			RuntimeSingleton.getInstance().mobileDevice = world.getMobileDeviceName();
			RuntimeSingleton.getInstance().mobilePlatform = world.getMobilePlatformName();
			RuntimeSingleton.getInstance().mobileVersion = world.getMobilePlatformVersion();
			RuntimeSingleton.getInstance().mobileBrowser = world.getMobileBrowser();
			RuntimeSingleton.getInstance().locale = world.getLocale();

			if (world.isMobile()) {
				// MOBILE
				sauceOptions.setCapability("appiumVersion", "1.18.1");
				browserOptions = new DesiredCapabilities();
				browserOptions.setCapability("platformName", world.getMobilePlatformName());
				browserOptions.setCapability("browserName", world.getMobileBrowser());
				browserOptions.setCapability("appium:deviceName", world.getMobileDeviceName());
				browserOptions.setCapability("appium:platformVersion", world.getMobilePlatformVersion());
				// browserOptions.setCapability("appium:app",""); // for an app test, add app
				// location, (remote or sauce storage)
				browserOptions.setCapability("appium:deviceOrientation", world.getMobileDeviceOrientation());
				browserOptions.setCapability("unicodeKeyboard", true);
				browserOptions.setCapability("resetKeyboard", true);
			} else {
				// DESKTOP
				sauceOptions.setCapability("seleniumVersion", "3.141.59");
                System.out.println("loading browser prefs for " + world.getBrowser());

				switch (world.getBrowser()) {
				case FIREFOX:
					browserOptions = new FirefoxOptions();
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("browser.download.dir", "C:\\Users\\Administrator\\Downloads");
					firefoxProfile.setPreference("browser.download.folderList", 2);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
							"text/plain, text/csv, application/octet-stream,image/jpeg, video/mp4,video/quicktime, application/binary");
					firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
							"text/plain, text/csv, application/octet-stream,image/jpeg, application/binary");
					firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
					firefoxProfile.setPreference("browser.download.manager.useWindow", false);
					firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
					firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
					firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
					firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);

					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setCapability("unhandledPromptBehavior", "accept");
					firefoxOptions.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
					browserOptions.merge(firefoxOptions);
					break;
				case CHROME:
					browserOptions = new ChromeOptions();
					LoggingPreferences logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					browserOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					((ChromeOptions) browserOptions).setAcceptInsecureCerts(true);
					((ChromeOptions) browserOptions).setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("profile.default_content_settings.popups", 0);

					String chromeProfile = System.getenv("TEMP");
					chromePrefs.put("--user-data-dir", chromeProfile);
					chromePrefs.put("--profile-directory",
							System.getenv("HOSTNAME") + "_" + Integer.valueOf(++profileCnt));
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("prefs", chromePrefs);
					browserOptions.merge(chromeOptions);
					break;
				case SAFARI:
					browserOptions = new SafariOptions();
					break;
				case IEXPLORER:
					browserOptions = new InternetExplorerOptions();
					break;
				case EDGE:
					browserOptions = new EdgeOptions();
					break;
				default:
					System.out.println("chrome2");
					browserOptions = new ChromeOptions();
					((ChromeOptions) browserOptions).setAcceptInsecureCerts(true);
					((ChromeOptions) browserOptions).setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
				}
				browserOptions.setCapability("platformName", world.getBrowserPlatform());
				browserOptions.setCapability("browserVersion", world.getBrowserVersion());
			}
			browserOptions.setCapability("unhandledPromptBehavior", "accept");
			browserOptions.setCapability("name", world.getScenarioName());
;
			/// ********************************************************
			// this sc proxy tunnel is needed for yopmail and jenkins
			// removing this will brake yopmail tests in ci/cd
			/// ********************************************************
			sauceOptions.setCapability("tunnelIdentifier", "sc-proxy-tunnel"); // also, if something is wrong here. You
																				// need to run a saucelabs connect
																				// tunnel
			// here's an example: bin/sc --pidfile /tmp/sc.pid1 -u "SAUCE_USER" -k
			// "SAUCEKEY" -i sc-proxy-tunnel
			// --no-remove-colliding-tunnels
			/// ********************************************************
			browserOptions.setCapability("sauce:options", sauceOptions);
            System.out.print("Loading browser ....");
			this.driver = new RemoteWebDriver(new URL("https://ondemand.saucelabs.com/wd/hub"), browserOptions);
			String sessionId = ((RemoteWebDriver) this.driver).getSessionId().toString();
			String tempWebLink = sauceRest.getPublicJobLink(sessionId);
			world.setSessionId(sessionId);
			world.setSauceWebLink(tempWebLink);
            System.out.print(" done! @ ");
            logger.info(
                "|" + ConsoleColors.BLUE + tempWebLink
                + ConsoleColors.RESET + "|");


		} catch (Exception e) {
			e.printStackTrace();
			throw new SauceException("Sauce Driver failed - " + e.getMessage());
		}
		return driver;
	}
}
