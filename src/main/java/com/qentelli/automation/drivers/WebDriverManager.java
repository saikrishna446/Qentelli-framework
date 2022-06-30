package com.qentelli.automation.drivers;

import com.epam.healenium.SelfHealingDriver;
import com.qentelli.automation.common.World;
import org.openqa.selenium.PageLoadStrategy;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;

import java.util.logging.Level;

public class WebDriverManager {
	private World world;
	private WebDriver driver;

	public WebDriverManager(World world) {
		this.world = world;
	}

	public WebDriver getIEDriver() {
		String version = world.getBrowserVersion();
		if (version.equalsIgnoreCase("latest")) {
			io.github.bonigarcia.wdm.WebDriverManager.iedriver().arch32().setup();
		} else {
			io.github.bonigarcia.wdm.WebDriverManager.iedriver().arch32().browserVersion(version).setup();
		}

		InternetExplorerOptions options = new InternetExplorerOptions();
		options.setCapability("unhandledPromptBehavior", "accept");
		options.destructivelyEnsureCleanSession();
		this.driver = new InternetExplorerDriver(options);
		return this.driver;
	}

	/**
	 * Local Driver Initialization
	 *
	 * @return
	 * @throws Exception
	 */
	public WebDriver getDriver() throws Exception {
		String version = world.getBrowserVersion();
		try {

			switch (world.getBrowser()) {
				case FIREFOX:
					if (version.equalsIgnoreCase("latest")) {
						io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
					} else {
						io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().browserVersion(version).setup();
					}
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("browser.download.dir", System.getProperty("user.dir") + "//downloads");
					firefoxProfile.setPreference("browser.download.folderList",2);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
							"text/plain, text/csv, application/octet-stream,image/jpeg, video/mp4,video/quicktime, application/binary");
					firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
							"text/plain, text/csv, application/octet-stream, image/jpeg, application/binary");
					firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
					firefoxProfile.setPreference("browser.download.manager.useWindow", false);
					firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
					firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
					firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
					firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);

					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setCapability("unhandledPromptBehavior", "accept");
					firefoxOptions.setProfile(firefoxProfile);
					this.driver = new FirefoxDriver(firefoxOptions);
					SelfHealingDriver.create(this.driver);
					break;
				case CHROME:
					if (version.equalsIgnoreCase("latest")) {
						io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
					} else {
						io.github.bonigarcia.wdm.WebDriverManager.chromedriver().browserVersion(version).setup();
					}
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("profile.default_content_settings.popups", 0);
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("prefs", chromePrefs);
					LoggingPreferences logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

					chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
					chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
					this.driver = new ChromeDriver(chromeOptions);
					SelfHealingDriver.create(this.driver);
					break;
				case SAFARI:
					this.driver = new SafariDriver();
					SelfHealingDriver.create(this.driver);
					break;
				case IEXPLORER:
					SelfHealingDriver.create(this.driver);
					break;
				case EDGE:
					if (version.equalsIgnoreCase("latest")) {
						io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
						
						SelfHealingDriver.create(this.driver);
					} else {
						io.github.bonigarcia.wdm.WebDriverManager.edgedriver().browserVersion(version).setup();
						SelfHealingDriver.create(this.driver);
					}
					this.driver = new EdgeDriver();
					SelfHealingDriver.create(this.driver);
					break;
				default:
					io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
					this.driver = new ChromeDriver();
					SelfHealingDriver.create(this.driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Driver Initialization failed due to " + e.getMessage());

		}
		return driver;
	}
}
