package com.qentelli.automation.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.qentelli.automation.utilities.SHACTestDataObject;
import com.qentelli.automation.utilities.TBBTestDataObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import com.qentelli.automation.drivers.OATSDriver;
import com.qentelli.automation.drivers.SauceLabsDriver;
import com.qentelli.automation.drivers.SeleniumGridDriver;
import com.qentelli.automation.drivers.Waits;
import com.qentelli.automation.drivers.WebDriverManager;
import com.qentelli.automation.exceptions.custom.SauceException;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import com.qentelli.automation.testdatafactory.data.FactoryData;
import com.qentelli.automation.testdatafactory.data.Product;
import com.qentelli.automation.utilities.BYDSessionMonitor;
import com.qentelli.automation.utilities.COImpersonateUserSessionMonitor;
import com.saucelabs.saucerest.SauceREST;
import cucumber.api.Scenario;

public class World {
	private Constants.BROWSER browser;
	private String sauceTunnelId;
	private String tunnelRequired;
	private String locale;
	private String orderNum;
	private String email;
	private String password;
	private String customerId;
	public String emailSubject;
	private String browserVersion;
	private String browserPlatform;
	private String platform;
	private String platformVersion;
	private String sessionId;
	private List<String> sauceWebLink = new ArrayList<>();
	private String testEnvironment;
	private Boolean isCoachUser = false;
	private boolean isE2E = false;
	public Map<String, String> bydSessionUserDetails;
	public BYDSessionMonitor bydSessionMonitor;
	public Map<String, String> cooImpersonateSessionUserDetails;
	public COImpersonateUserSessionMonitor cooSessionMonitor;
	public FactoryConfig factoryConfig;
	public FactoryData factoryData;
	WebDriverManager webDriver = new WebDriverManager(this);
	SauceLabsDriver sauceDriver = new SauceLabsDriver(this);
	SeleniumGridDriver gridDriver = new SeleniumGridDriver(this);
	OATSDriver oatsDriver = new OATSDriver(this);
	public WebDriver driver;
	public WebDriver ieDriver;
	public Scenario scenario;
	private String scenarioName;

	private String atgCreationDate="NA";
	private String atgSubmitDate="NA";
    private String idmCreationDate="NA";
    private String ebsCreationDate="NA";
	private String ebsiCentrisDate="NA";
	private String cooCreationDate="NA";

	Map<String, String> customerDetails = new HashMap<String, String>();
	Map<String, Integer> businessAlertTotals = new HashMap<String, Integer>();
	Map<String, String> creditCardDetails = new HashMap<String, String>();
	Map<String, String> orderDetails = new HashMap<String, String>();
	Map<String, String> coachId = new HashMap<String, String>();
	Map<String, String> existingCustomerDetails = new HashMap<String, String>();
	Map<String, String> existingCoachDetails = new HashMap<String, String>();
	Map<String, String> updateAccountAddress = new HashMap<String, String>();
	Map<String, String> testDataJson = new HashMap<>();
	Map<String, String> generatedDataJson = new HashMap<>();
	Map<String, String> dataBaseInputTestDataJson = new HashMap<>();
	Map<String, String> coachDetailOfCurrentUser = new HashMap<>();
	List<Product> products = new ArrayList<>();
	public SHACTestDataObject shacTestDataObject = new SHACTestDataObject();
	public TBBTestDataObject tbbTestDataObject = new TBBTestDataObject();

	private boolean isMobile;
	private String mobilePlatform;
	private String mobileDeviceName;
	private String mobileDeviceOrientation;
	private String mobilePlatformVersion;
	private String mobilePlatformName;
	private String mobileBrowser;
	private Constants.DRIVERTYPE driverType;
	private SauceREST sauceRest;
	private Locale formattedLocale;
	private String userName;
	private boolean isUserTakenFromTDM = false;
	private String hostNumber = "";
	private String portNumber;
	private String profileId;
	private String orderId;
	private Locale userLocale;
	private boolean atgOrderFullFillerSubmitted=false;
	/*
	 * Method to get the driver based on the environment setup
	 */
    public synchronized WebDriver getDriver() throws Exception {
		switch (this.driverType) {
			case SAUCE:
				this.driver = sauceDriver.getDriver();
				break;
			case GRID:
				this.driver = gridDriver.getDriver();
				break;
			default:
				this.driver = webDriver.getDriver();
				break;
		}
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Waits.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		return this.driver;
	}

    public String getExecutionEnvironment() {
      StringBuilder executionType = new StringBuilder(getDriverType().name());
      if (isMobile()) {
        executionType.append(" >> Mobile");
      } else {
        executionType.append(" >> DESKTOP");
      }
      return executionType.toString();
    }

	public Constants.BROWSER getBrowser() {
		return browser;
	}

	public String getBrowserPlatform() {
		return browserPlatform;
	}

	public String getChromeLog() {
		return this.driver.manage().logs().get(LogType.BROWSER).getAll().toString();
	}

	public SauceREST getSauceRest() {
		return sauceRest;
	}

//synchronized
	public synchronized void setSauceRest(SauceREST sauceRest) {
		this.sauceRest = sauceRest;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public synchronized Map<String, String> getCoachID() {
		return coachId;
	}

	public synchronized Map<String, String> getCreditCardDetails() {
		return creditCardDetails;
	}

	public synchronized Map<String, String> getCustomerDetails() {
		return customerDetails;
	}

	public synchronized Map<String, String> getExistingCustomerdetails() {
		return existingCustomerDetails;
	}

	public WebDriver getIEDriver() {
		WebDriver driver = null;
		if (this.driverType.equals(Constants.DRIVERTYPE.SAUCE)) {
			try {
				driver = sauceDriver.getSauceIEDriver();
			} catch (SauceException e) {
				e.printStackTrace();
			}
		} else {
			driver = this.ieDriver = webDriver.getIEDriver();
		}
		return driver;
	}
	public void setE2ETrue(){
		isE2E = true;
	}

	public boolean getIsE2E(){
		return isE2E;
	}

	public FactoryData getFactoryData() {
		return factoryData;
	}

	public void setFactoryData(FactoryData factoryData) {
		this.factoryData = factoryData;
	}

	public String getLocale() {
		return locale;
	}

	public OATSDriver getOatsDriver() {
		return oatsDriver;
	}

	public synchronized Map<String, String> getOrderDetails() {
		return orderDetails;
	}

    public String getOrderNum() {
      return orderNum;
    }

	public String getPlatform() {
		return platform;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public WebDriver getSauceIEDriver() throws SauceException {
		return this.ieDriver = sauceDriver.getSauceIEDriver();
	}

	public String getSauceTunnelId() {
		return sauceTunnelId;
	}

	public List<String> getSauceWebLink() {
		return sauceWebLink;
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getTunnelRequired() {
		return tunnelRequired;
	}

	public synchronized Map<String, String> getUpdateAccountDetails() {
		return updateAccountAddress;
	}

	public synchronized void setBrowser(Constants.BROWSER browser) {
		this.browser = browser;
	}

	public synchronized void setBrowserPlatform(String browserPlatform) {
		this.browserPlatform = browserPlatform;
	}

	public synchronized void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public synchronized void setCoachID(String key, String value) {

		this.coachId.put(key, value);
	}

	public synchronized void setCreditDetails(String key, String value) {

		this.creditCardDetails.put(key, value);
	}

	public synchronized void setCustomerDetails(String key, String value) {

		this.customerDetails.put(key, value);
	}

	public synchronized void setExistingCustomerdetails(String key, String value) {
		this.existingCustomerDetails.put(key, value);
	}

	public synchronized void setLocale(String locale) {
		this.locale = locale;
	}

	public synchronized void setOatsDriver(OATSDriver oatsDriver) {
		this.oatsDriver = oatsDriver;
	}

	public synchronized void setOrderDetails(String key, String value) {

		this.orderDetails.put(key, value);
	}

	public synchronized void setBusinessAlertTotals(String key, Integer value) {

		this.businessAlertTotals.put(key, value);
	}

	public synchronized void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public synchronized void setPlatform(String platform) {
		this.platform = platform;
	}

	public synchronized void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public synchronized void setSauceTunnelId(String sauceTunnelId) {
		this.sauceTunnelId = sauceTunnelId;
	}

	public synchronized void setSauceWebLink(String links) {
		sauceWebLink.add("<h2><a href=" + links + " target='_blank'>Please Click here for the Video</a><h2>");
	}

	public synchronized void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public synchronized void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public synchronized void setTunnelRequired(String tunnelRequired) {
		this.tunnelRequired = tunnelRequired;
	}

	public synchronized void setUpdateAccountDetails(String key, String value) {
		this.updateAccountAddress.put(key, value);
	}

	public synchronized void setExistingCoachDetails(String key, String value) {
		this.existingCoachDetails.put(key, value);
	}

	public String getMobilePlatform() {
		return mobilePlatform;
	}

	public synchronized void setMobilePlatform(String mobilePlatform) {
		this.mobilePlatform = mobilePlatform;
	}

	public String getMobileDeviceName() {
		return mobileDeviceName;
	}

	public synchronized void setMobileDeviceName(String mobileDeviceName) {
		this.mobileDeviceName = mobileDeviceName;
	}

	public String getMobileDeviceOrientation() {
		return mobileDeviceOrientation;
	}

	public synchronized void setMobileDeviceOrientation(String mobileDeviceOrientation) {
		this.mobileDeviceOrientation = mobileDeviceOrientation;
	}

	public String getMobilePlatformVersion() {
		return mobilePlatformVersion;
	}

	public synchronized void setMobilePlatformVersion(String mobilePlatformVersion) {
		this.mobilePlatformVersion = mobilePlatformVersion;
	}

	public String getMobilePlatformName() {
		return mobilePlatformName;
	}

	public synchronized Map<String, String> getExistingCoachDetails() {
		return existingCoachDetails;
	}

	public synchronized void setMobilePlatformName(String mobilePlatformName) {
		this.mobilePlatformName = mobilePlatformName;
	}

	public String getMobileBrowser() {
		return mobileBrowser;
	}

	public synchronized void setMobileBrowser(String mobileBrowser) {
		this.mobileBrowser = mobileBrowser;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public boolean isUserTakenFromTDM() {
		return isUserTakenFromTDM;
	}

	public synchronized void setIsUserTakenFromTDM(boolean userTakenFromTDM) {
		isUserTakenFromTDM = userTakenFromTDM;
	}

	public synchronized void setMobile(boolean mobile) {
		isMobile = mobile;
	}

	public Constants.DRIVERTYPE getDriverType() {
		return driverType;
	}

	public synchronized void setDriverType(Constants.DRIVERTYPE driverType) {
		this.driverType = driverType;
	}

    public String getEmail() {
      return email;
    }
	public String getCustomerId() {
		return customerId;
	}
	public String getPassword() {
		return password;
	}

	public synchronized void setEmail(String email) {
		this.email = email;
	}
	public synchronized void setCustomerId(String customer_Id) {
		this.customerId = customer_Id;
	}
	public synchronized void setPassword(String password) {
		this.password = password;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public synchronized void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public synchronized Map<String, String> getTestDataJson() {
		return testDataJson;
	}

	public synchronized void setTestDataJson(Map<String, String> testDataJson) {
		this.testDataJson = testDataJson;
	}

	public synchronized Map<String, String> getGeneratedDataJson() {
		return generatedDataJson;
	}

	public synchronized void setGeneratedDataJson(Map<String, String> generatedDataJson) {
		this.generatedDataJson = generatedDataJson;
	}

	public synchronized Map<String, Integer> getBusinessAlertTotals() {
		return businessAlertTotals;
	}

	public synchronized Map<String, String> getDataBaseInputTestDataJson() {
		return dataBaseInputTestDataJson;
	}

	public synchronized void setDataBaseInputTestDataJson(Map<String, String> dataBaseInputTestDataJson) {
		this.dataBaseInputTestDataJson = dataBaseInputTestDataJson;
	}

	public synchronized Map<String, String> getCoachDetailOfCurrentUser() {
		return coachDetailOfCurrentUser;
	}

	public synchronized void setCoachDetailOfCurrentUser(Map<String, String> coachDetailOfCurrentUser) {
		this.coachDetailOfCurrentUser = coachDetailOfCurrentUser;
	}

	public synchronized List<Product> getProducts() {
		return products;
	}

	public synchronized void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getTestEnvironment() {
		return testEnvironment;
	}

	public synchronized ResourceBundle getLocaleResource() {
		return localeResource;
	}

	private ResourceBundle localeResource;

	public synchronized void setLocaleResource(ResourceBundle localeResource) {
		this.localeResource = localeResource;
	}

	public synchronized void setTestEnvironment(String testEnvironment) throws IOException {
		// ********************************* WARNING ******************************
		// the code below causes issues w/ parallel execution
		// if you need the setProps call see snelson
		// *******************************************************************************
//		Properties prop = new Properties();
//		prop.load(new FileInputStream("config/config.properties"));
//		System.out.println("foo get\t" + prop.getProperty("EBS_" + testEnvironment + "_URL"));
//		prop.setProperty("EBS_URL", prop.getProperty("EBS_" + testEnvironment + "_URL"));
//		prop.setProperty("EBS_Username", prop.getProperty("EBS_" + testEnvironment + "_Username"));
//		prop.setProperty("EBS_Password", prop.getProperty("EBS_" + testEnvironment + "_Password"));
//		prop.store(new FileOutputStream("config/config.properties"), null);
		this.testEnvironment = testEnvironment;
	}

	public Locale getFormattedLocale() {
		return formattedLocale;
	}

	public void setFormattedLocale(Locale formattedLocale) {
		this.formattedLocale = formattedLocale;
	}

	public String getMyMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	public Boolean isACoachUser() {
		return isCoachUser;
	}

	public void setIsCoachUser(Boolean coachUser) {
		isCoachUser = coachUser;
	}

	public void setHost(String host) {
		hostNumber = host;
	}

	public void setPort(String port) {
		portNumber = port;
	}

	public String getHost() {
		return hostNumber;
	}

	public String getPort() {
		return portNumber;
	}

	public void setProfileId(String profile) {
		profileId = profile;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setOrderId(String order) {
		orderId = order;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

//	public String getAppE2E(String appName) {
//		String tempString = "";
//
//		if (appName.equalsIgnoreCase("TBB") || appName.equalsIgnoreCase("ATG")) {
//			tempString = "ATG";
//		}
//		if (appName.equalsIgnoreCase("EBS")) {
//			tempString = "EBS";
//		}
//		if (appName.equalsIgnoreCase("BYD")) {
//			tempString = "BYD";
//		}
//		if (appName.equalsIgnoreCase("COO")) {
//			tempString = "COO";
//		}
//		if (appName.equalsIgnoreCase("COM")) {
//			tempString = "COM";
//		}
//		if (appName.equalsIgnoreCase("OIM")) {
//			tempString = "OIM";
//		}
//		if (appName.equalsIgnoreCase("IDM")) {
//			tempString = "IDM";
//		}
//		return tempString;
//	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String setAtgCreationDate(String atgCreationDate) {
		return this.atgCreationDate=atgCreationDate;
	}

	public String getAtgCreationDate() {
		return atgCreationDate;
	}

	public String setAtgSubmitDate(String atgSubmitDate) {
		return this.atgSubmitDate=atgSubmitDate;
	}

	public String getAtgSubmitDate() {
		return atgSubmitDate;
	}

    public String setIDMCreationDate(String idmCreationDate) {
        return this.idmCreationDate=idmCreationDate;
    }

    public String getIDMCreationDate() {
        return idmCreationDate;
    }

	public String setEBSCreationDate(String ebsCreationDate) {
		return this.ebsCreationDate=ebsCreationDate;
	}

	public String getEBSCreationDate() {
		return ebsCreationDate;
	}

	public String setEBSiCentrisDate(String ebsiCentrisDate) {
		return this.ebsiCentrisDate=ebsiCentrisDate;
	}

	public String getEBSiCentrisDate() {
		return ebsiCentrisDate;
	}

	public String setCOOCreationDate(String cooCreationDate) {
		return this.cooCreationDate=cooCreationDate;
	}

	public String getCOOCreationDate() {
		return cooCreationDate;
	}

	public Locale getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}

	public boolean setATGOrderFullFillerSubmitted(boolean atgOrderFullFillerSubmitted) {
		return this.atgOrderFullFillerSubmitted=atgOrderFullFillerSubmitted;
	}

	public boolean getATGOrderFullFillerSubmitted() {
		return atgOrderFullFillerSubmitted;
	}
}
