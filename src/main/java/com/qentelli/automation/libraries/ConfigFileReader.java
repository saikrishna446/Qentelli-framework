package com.qentelli.automation.libraries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.base.AutomationIssueException;

public class ConfigFileReader {
	private Properties properties;
	private Properties orProperties;
	private static Properties dbProperties;
	private final String propertyFilePath = System.getProperty("user.dir") + "/config/config.properties";
	private final String objectrepositoryFilePath = System.getProperty("user.dir")
			+ "/configs/objectrepository.properties";
	Logger logger = LogManager.getLogger(ConfigFileReader.class);

	// *************************************************
	private static ConfigFileReader configFileReader, objectRepositoryFileReader;
	private World world;

	public ConfigFileReader(World world) {
		this(ConfigType.CONFIG);
		this.world = world;
	}

	public static ConfigFileReader getConfigFileReader() {
		if (configFileReader != null) {
			return configFileReader;
		} else {
			configFileReader = new ConfigFileReader(ConfigType.CONFIG);
			return configFileReader;
		}

	}

	public static ConfigFileReader getObjectRepositoryFileReader() {
		if (objectRepositoryFileReader != null) {
			return objectRepositoryFileReader;
		} else {
			objectRepositoryFileReader = new ConfigFileReader(ConfigType.OR);
			return objectRepositoryFileReader;
		}

	}

	// *************************************************

	protected ConfigFileReader(ConfigType configType) {
		BufferedReader reader = null;
		try {
			if (configType.equals(ConfigType.CONFIG)) {
				properties = new Properties();
				reader = new BufferedReader(new FileReader(propertyFilePath));
				try {
					properties.load(reader);
					reader.close();
				} catch (IOException e) {
					logger.info("Here is the exception in config");
					e.printStackTrace();
				}

			} else if (configType.equals(ConfigType.OR)) {
				logger.info("Into OR Config ");
				orProperties = new Properties();
				reader = new BufferedReader(new FileReader(objectrepositoryFilePath));
				try {
					orProperties.load(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	// Type of config file reader
	// CONFIG = config properties file
	// OR = object repository
	// DB = database
	private enum ConfigType {
		CONFIG, OR, DB
	}

	public String getOIMUrl(String env) {

		String url = properties.getProperty("OIM_" + env + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	public int getdataSyncTime() {
		return Integer.parseInt(properties.getProperty("dataSyncWait"));
	}

	public int getMinDataSyncTime() {
		return Integer.parseInt(properties.getProperty("minDataSyncWait"));
	}

	public String getBYDUrl() {
		String url = properties.getProperty("BYD_" + world.getTestEnvironment() + "_URL");
		logger.info("Byd Url: " + url);
		if (url != null)
			return url;
		else
			throw new RuntimeException(
					"BYD_" + world.getTestEnvironment() + "_URL not specified in the Configuration.properties file.");
	}
	// snelson review here

/// do not use the use ApplicationsEndpointObject.coo.
///	public String getCOOUrl() {
//		String url = properties.getProperty("COO_" + world.getTestEnvironment() + "_URL");
//		if (url != null)
//			return url;
//		else
//			throw new RuntimeException(
//					"COO_" + world.getTestEnvironment() + "_URL not specified in the Configuration.properties file.");
//	}

	public String getCOOAdminUser(String env) {
		String user = properties.getProperty("COO_" + env + "_Admin_Username");
		if (user != null)
			return user;
		else
			throw new RuntimeException(user + " user not specified in the Configuration.properties file.");
	}

	public String getCOOAdminPassword(String env) {
		String pass = properties.getProperty("COO_" + env + "_Admin_Password");
		if (pass != null)
			return pass;
		else
			throw new RuntimeException(pass + " password not specified in the Configuration.properties file.");
	}

	public String getCOOImpersonateUrl() {
		String url = properties.getProperty("COO_" + world.getTestEnvironment() + "_Impersonate_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("COO_" + world.getTestEnvironment()
					+ "_Impersonate_URL not specified in the Configuration.properties file.");
	}

	// Get Username for COO app
	public String getCOOUserName() {
		String userName = "";
		String env = world.getTestEnvironment();
		logger.info(">>>>  Entering COO User for ENV: " + env);
		switch (env.toUpperCase()) {
		case "DEV3":
			userName = properties.getProperty("COO_" + env + "_E2E_Username");
			break;
		case "UAT":
			userName = properties.getProperty("Coach_" + env + "_Username");
			break;
		default:
			userName = properties.getProperty("COO_" + env + "_Username");
		}
		return userName;
	}

	public String getCOOUserNameE2E(String env) {
		String userName = properties.getProperty("COO_" + env + "_E2E_Username");
		logger.info(">>>>  Entering COO User for ENV: " + env);
		if (userName != null && !userName.isEmpty())
			return userName;
		else
			throw new RuntimeException("COO_Username not specified in the config.properties file.");
	}

	// Get Password for COO app
	public String getCOOPassword() {
		String password = "";
		String env = world.getTestEnvironment();
		logger.info(">>>>  Entering COO Password for ENV: " + env);
		if (env.toUpperCase().equals("DEV3")) {
			password = properties.getProperty("COO_" + env + "_E2E_Password");
		} else {
			password = properties.getProperty("COO_" + env + "_Password");
		}
		if (password != null && !password.isEmpty())
			return password;
		else
			throw new RuntimeException("COO_Password not specified in the config.properties file.");
	}

	/**
	 * Getting OMI service url from config file
	 *
	 * @return
	 */
	public String getTBBOIMServiceOneUrl(String env) {
		String url = properties.getProperty("OIM_SERVICE_URL_1");
		if (env.toUpperCase().trim().equalsIgnoreCase("UAT")) {
			url = properties.getProperty("OIM_SERVICE_URL_1");
		}
		if (env.toUpperCase().trim().equalsIgnoreCase("QA3") || env.toUpperCase().trim().equalsIgnoreCase("QA4")) {
			url = properties.getProperty("OIM_SERVICE_URL_2");
		}
		if (env.toUpperCase().trim().equalsIgnoreCase("DEV3")) {
			url = properties.getProperty("OIM_SERVICE_URL_3");
		}
		if (env.toUpperCase().trim().equalsIgnoreCase("DEV2")) {
			url = properties.getProperty("OIM_SERVICE_URL_4");
		}
		if (env.toUpperCase().trim().equalsIgnoreCase("QA2")) {
			url = properties.getProperty("OIM_SERVICE_URL_5");
		}
		if (env.toUpperCase().trim().equalsIgnoreCase("DEV4")) {
			url = properties.getProperty("OIM_SERVICE_URL_7");
		}
		logger.info(env + " : OIM URL for " + env.toUpperCase().trim() + " is : " + url);
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	public String getSearchIdentityAPIEndpoint(String env) {
		String url = "";
		logger.info("Env in function is : " + env);
		if (env.equalsIgnoreCase("DEV3")) {
			url = properties.getProperty("IDM_DEV3_API_USER_SEARCH");
		}
		return url;
	}

	/**
	 * Getting OMI service url from config file
	 *
	 * @return
	 */
	public String getTBBOMIServiceOneUrl() {
		String url = properties.getProperty("OIM_SERVICE_URL_1");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	/**
	 * Getting OMI service url from config file
	 *
	 * @return
	 */
	public String getTBBOMIServiceTwoeUrl(String env) {
		String url = properties.getProperty("BYD_" + env + "_SERVICE_URL_1");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	/**
	 * Getting OMI service url from config file
	 *
	 * @return
	 */
	public String getTBBOMIServiceThreeUrl(String env) {
		String url = properties.getProperty("BYD_" + env + "_SERVICE_URL_2");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	public String getBYDUserName(String env) {
		String username = properties.getProperty("BYD_" + env + "_Username");
		if (username != null)
			return username;
		else
			throw new RuntimeException(username + " Username not specified in the Configuration.properties file.");
	}

	public String getBYDPassword(String env) {
		String password = properties.getProperty("BYD_" + env + "_Password");
		if (password != null)
			return password;
		else
			throw new RuntimeException(password + " Password not specified in the Configuration.properties file.");
	}

	public String getTBBUrl() {
		String url = world.getLocaleResource().getString("TBB_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(
					"TBB_" + world.getTestEnvironment() + "_url not specified in the Configuration.properties file.");
	}

	public String getTBBEnrollmentUrl(String env) {
		String url = world.getLocaleResource().getString("TBB_" + env + "_ENROLLMENT_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	public String getTBBBypassCaptchaUrl() {
		if (world.driver.getCurrentUrl().contains("bypass")) {
			return world.driver.getCurrentUrl();
		}
		String url = world.getLocaleResource().getString("TBB_CAPTCHA_" + world.getTestEnvironment() + "_URL");
		url = url.contains("bypass")
				? url
				: (url.contains("?")
				? url.replace("?", world.tbbTestDataObject.bypassCaptchaRemoveV2 + "&")
				: url + world.tbbTestDataObject.bypassCaptchaRemoveV2);
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBB_CAPTCHA_" + world.getTestEnvironment()
					+ "_URL not specified in the Configuration.properties file.");
	}

	public String getBYDSearchUrl() {
		String url = properties.getProperty("BYD_" + world.getTestEnvironment() + "_SEARCH_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("BYD_" + world.getTestEnvironment()
					+ "_SEARCH_URL not specified in the Configuration.properties file.");
	}

	public String getElement(String elemntName) {
		String tempElement = orProperties.getProperty(elemntName);
		if (tempElement != null)
			return tempElement;
		else
			throw new RuntimeException(tempElement + " not specified in the ObjectRepository.properties file.");
	}

	public String getDriverPath() {
		String driverPath = System.getProperty("user.dir") + properties.getProperty("driverpath");
		if (driverPath != null)
			return driverPath;
		else
			throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
	}

	public long getExplicitlyWait() {
		String explicitlyWait = properties.getProperty("explicitlyWait");
		if (explicitlyWait != null)
			return Long.parseLong(explicitlyWait);
		else
			throw new RuntimeException("explicitlyWait not specified in the Configuration.properties file.");
	}

	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitlyWait");
		if (implicitlyWait != null)
			return Long.parseLong(implicitlyWait);
		else
			throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
	}

	public String getApplicationUrl() {

		String url = null;
		try {
			logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.info(System.getenv("APP_URL"));
			url = System.getenv("APP_URL");
			logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		} catch (Exception e) {
			url = null;
		}
		if (url == null) {
			url = properties.getProperty("url");
		}

		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

	public String getApiGatewayUrl() {
		String url = properties.getProperty("apigateway");
		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

	public String gettestOIMApplicationUrl(String env) {
		logger.info("#########################################" + properties.getProperty("OIM_" + env + "_URL"));
		String url = properties.getProperty("OIM_" + env + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	public String getKeyStatus() {
		String url = properties.getProperty("Jsonkeys");
		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

	public long getPause() {
		String pause = properties.getProperty("pause");
		if (pause != null)
			return Long.parseLong(pause);
		else
			throw new RuntimeException("pause not specified in the Configuration.properties file.");
	}

	public long getPageLoadPause() {
		String pageloadpause = properties.getProperty("pageloadpause");
		if (pageloadpause != null)
			return Long.parseLong(pageloadpause);
		else
			throw new RuntimeException("pageloadpause not specified in the Configuration.properties file.");
	}

	public String getTemplatePath() {
		String templatePath = System.getProperty("user.dir") + properties.getProperty("templateloc");
		if (templatePath != null)
			return templatePath;
		else
			throw new RuntimeException("templatePath not specified in the Configuration.properties file.");
	}

	public String getBrowser() {
		String browserName = properties.getProperty("browser");
		if (browserName != null)
			return browserName;
		else
			throw new RuntimeException(
					"Browser Name Key value in Configuration.properties is not matched : " + browserName);
	}

	public Boolean getBrowserWindowSize() {
		String windowSize = properties.getProperty("windowMaximize");
		if (windowSize != null)
			return Boolean.valueOf(windowSize);
		return true;
	}

	public String getReportConfigPath() {
		String reportConfigPath = properties.getProperty("reportConfigPath");
		if (reportConfigPath != null)
			return reportConfigPath;
		else
			throw new RuntimeException(
					"Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}

	public String getObjectRepoPath() {
		String objectConfigPath = properties.getProperty("ObjectRepoPath");
		if (objectConfigPath != null)
			return objectConfigPath;
		else
			throw new RuntimeException(
					"Object Repo Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}

	public String getDocumentType() {
		String template = System.getProperty("user.dir") + properties.getProperty("template");
		if (template != null)
			return template;
		else
			throw new RuntimeException("template is not specified in the Configuration.properties file.");
	}

	public String getOIMUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOIMPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getConnectionString(String dbEnv, String env) {
		configFileReader = new ConfigFileReader(ConfigType.DB);
		String connectionString = "";
		switch (dbEnv.toLowerCase()) {
		case "idm_qa":
			connectionString = String.format("@%s:%s/%s", dbProperties.getProperty("IDM_" + env + "_HOST_NAME"),
					dbProperties.getProperty("IDM_PORT"), dbProperties.getProperty("IDM_" + env + "_SERVICE_NAME"));
			break;
		case "idm_uat":
			connectionString = String.format("@%s:%s/%s",
					dbProperties.getProperty("IDM_HOST_NAME").replace("qa", "uat"),
					dbProperties.getProperty("IDM_PORT"), dbProperties.getProperty("IDM_" + env + "_SERVICE_NAME"));
			break;
		case "ebs_qa":
			connectionString = String.format("@%s:%s/%s", dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					dbProperties.getProperty("EBS_PORT"), dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			break;
		case "ebs_uat":
			connectionString = String.format("@%s:%s/%s", dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					dbProperties.getProperty("EBS_PORT"), dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			break;
		default:
			connectionString = String.format("@%s:%s/%s", dbProperties.getProperty("IDM_" + env + "_HOST_NAME"),
					dbProperties.getProperty("IDM_PORT"), dbProperties.getProperty("IDM_" + env + "_SERVICE_NAME"));
			break;
		}
		System.out.println("DATABASE=" + connectionString);
		return connectionString;
	}

	// Get URL for ShareACart app
	public String getShareACartUrl() {
		String env = world.getTestEnvironment();
        String url = properties.getProperty("SHAREACART_" + env + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(
					"SHAREACART_" + env + "_URL not specified in the Configuration.properties file.");
	}

	// Get Username for ShareACart app
	public String getShareACartUsername() {
		String env = world.getTestEnvironment();
		String url = "";
        try {
            url = world.getLocaleResource().getString("SHAREACART_" + world.getTestEnvironment() + "_Username");
        } catch (MissingResourceException e) {
            url = properties.getProperty("SHAREACART_" + env + "_Username");
        }
        if (!(url == null || url.isEmpty())) {
            return url;
        }else
			throw new RuntimeException(
					"SHAREACART_" + env + "_Username not specified in the Configuration.properties file.");
	}

	// Get Password for ShareACart app
	public String getShareACartPassword() {
		String env = world.getTestEnvironment();
		String url = properties.getProperty("SHAREACART_" + env + "_Password");
		if (url != null)
			return url;
		else
			throw new RuntimeException(
					"SHAREACART_" + env + "_Password not specified in the Configuration.properties file.");
	}

	public String getBAMIUrl() {
		String env = world.getTestEnvironment();
		String url = properties.getProperty("BAMI_" + env + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("BAMI" + env + "_URL not specified in the Configuration.properties file.");
	}

	public String getLogoutUrl(String env) {
		String url = properties.getProperty("COO_" + env + "_LOGOUT_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	/*
	 * Mongo DB Host Name
	 */
	public String getMongoDBHostName() {
		String mongoHostName = properties.getProperty("MONGO_DB_HOSTNAME");
		if (mongoHostName != null && !mongoHostName.isEmpty())
			return mongoHostName;
		else
			throw new RuntimeException("MONGO_DB_HOSTNAME not specified in the Configuration.properties file.");
	}

	/*
	 * Mongo DB port
	 */
	public int getMongoDBPort() {
		int mongoPort = Integer.parseInt(properties.getProperty("MODGO_DB_PORT"));
		if (mongoPort != 0)
			return mongoPort;
		else
			throw new RuntimeException("MODGO_DB_PORT not specified in the Configuration.properties file.");
	}

	/*
	 * Mongo DB User Name
	 */
	public String getMongoDBUserName() {
		String mongoUserName = properties.getProperty("MONGO_DB_USER_NAME");
		if (mongoUserName != null && !mongoUserName.isEmpty())
			return mongoUserName;
		else
			throw new RuntimeException("MONGO_DB_USER_NAME not specified in the Configuration.properties file.");
	}

	/*
	 * Mongo DB Password
	 */
	public String getMongoDBPassword() {
		String mongoPassword = properties.getProperty("MONOG_DB_PASSWORD");
		if (mongoPassword != null && !mongoPassword.isEmpty())
			return mongoPassword;
		else
			throw new RuntimeException("MONOG_DB_PASSWORD not specified in the Configuration.properties file.");
	}

	/*
	 * Mongo DB Collection
	 */
	public String getMongoDBName() {
		String mongoCollection = properties.getProperty("MONGO_DB_NAME");
		if (mongoCollection != null && !mongoCollection.isEmpty())
			return mongoCollection;
		else
			throw new RuntimeException("MONGO_DB_NAME not specified in the Configuration.properties file.");
	}

	/*
	 * Debug Mode
	 */
	public boolean isDebugModeOn() {
		String dbgMode = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("debugMode");
		if (dbgMode != null && !dbgMode.isEmpty()) {
			return Boolean.parseBoolean(dbgMode);
		} else {
			return true;
		}
	}

	/*
	 * Mongo DB Collection
	 */
	public String getMongoDBCollectionName() {
		String mongoCollection = properties.getProperty("MONGO_DB_COLLECTION");
		if (mongoCollection != null && !mongoCollection.isEmpty())
			return mongoCollection;
		else
			throw new RuntimeException("MONGO_DB_COLLECTION not specified in the Configuration.properties file.");
	}

	/*
	 * Mongo DB Debug Mode Collection
	 */
	public String getMongoDBDebugModeCollectionName() {
		String mongoDebugCollection = properties.getProperty("DEBUG_MODE_MONGO_DB_COLLECTION");
		if (mongoDebugCollection != null && !mongoDebugCollection.isEmpty())
			return mongoDebugCollection;
		else
			throw new RuntimeException(
					"DEBUG_MODE_MONGO_DB_COLLECTION not specified in the Configuration.properties file.");
	}

	public long getWaitTime(String wait) {
		String waitTime = properties.getProperty(wait);
		if (waitTime != null)
			return Long.parseLong(waitTime);
		else
			throw new RuntimeException("Wait not specified in the Configuration.properties file.");
	}

	/*
	 * To retrieve TDM endpoints from config file
	 */
	public String getTDMEndPoints(String property) {
		String endpoint = properties.getProperty(property);
		if (endpoint != null && !endpoint.isEmpty())
			return endpoint;
		else
			throw new RuntimeException("End point not specified in the config.properties file.");
	}

	/**
	 * @description: This method fetch the Ultimate Portion Fix performance pack
	 *               URLs from property files
	 * @return
	 */
	public String getTBBUltimatePortionPerformancePackUrl() {
		String url = world.getLocaleResource()
				.getString("TBB_UltimatePortionFixPP_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBB_UltimatePortionFixPP_" + world.getTestEnvironment()
					+ "_URL not specified in the Configuration.properties file.");
	}

	public String getTBB2BMindsetShakeologyChallengePackUrl() {
		String url = world.getLocaleResource()
				.getString("TBB_2BMindsetShakeologyChallengePack_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBB_2BMindsetShakeologyChallengePack_" + world.getTestEnvironment()
					+ "_URL not specified in the Configuration.properties file.");
	}

	public String getTBBMyAccountSettingUrl() {
		String url = world.getLocaleResource().getString("TBB_MyAccountSetting_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBB_MyAccountSetting_" + world.getTestEnvironment()
					+ "_url not specified in the Configuration.properties file.");
	}

	public String getCOOManageContatcsUrl() {
		String url = world.getLocaleResource()
				.getString("cooManageContactsUnderGrowMyBusiness_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

	public String getMySiteUrl() {
		String url = properties.getProperty("MYSITE_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("MYSITE_" + world.getTestEnvironment()
					+ "_URL not specified in the Configuration.properties file.");
	}

	public String getMyWebsitesAndProfilePageUrl() {
		String url = world.getLocaleResource()
				.getString("cooMyWebsitesAndProfilePageUnderGrowMyBusiness_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException(url + " url not specified in the Configuration.properties file.");
	}

//	public String getBODStandaloneMembershipPage() {
//		String url = world.getLocaleResource()
//				.getString("TBBBODStandaloneMembership_" + world.getTestEnvironment() + "_URL");
//		if (url != null)
//			return url;
//		else
//			throw new RuntimeException("TBBBODStandaloneMembership_" + world.getTestEnvironment()
//					+ "_url not specified in the Configuration.properties file.");
//	}

	public String getEnergizeGoutCitronPage() {
		String url = world.getLocaleResource()
				.getString("TBBEnergizeGoutCitron_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new AutomationIssueException("TBBEnergizeGoutCitron_" + world.getTestEnvironment()
					+ "_url not specified in the Configuration.properties file.");
	}

	public String getWeightedGlovesProductURL() {
		String url = world.getLocaleResource().getString("TBB_WeightedGloves_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBB_WeightedGloves_" + world.getTestEnvironment()
					+ "_url not specified in the Configuration.properties file.");
	}

	public String getAnnualBODPreLaunchPack() {
		String url = world.getLocaleResource()
				.getString("TBBAnnualBodPreLaunch_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBBAnnualBodPreLaunch_" + world.getTestEnvironment()
					+ "_url not specified in the Configuration.properties file.");
	}

	public String getLift4FitnessCP() {
		String url = world.getLocaleResource().getString("TBBLift4FitnessCP_" + world.getTestEnvironment() + "_URL");
		if (url != null)
			return url;
		else
			throw new RuntimeException("TBBLift4FitnessCP_" + world.getTestEnvironment()
					+ "_url not specified in the Configuration.properties file.");
	}
}
