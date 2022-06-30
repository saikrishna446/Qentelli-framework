package com.qentelli.automation.utilities.healthcheck;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;

import com.qentelli.automation.common.World;
import com.qentelli.automation.libraries.ConfigFileReader;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.AbstractResourceBundle;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.api.RestRequest;
import com.qentelli.automation.utilities.db.OracleConnection;

public class ATG {
	private World world = null;
	private OracleConnection con;
	public String CheckATGServiceHealth = "";
	private ConfigFileReader configFileReader;
	Logger logger;
	// healthCheckServices;

	ResourceBundle healthCheck = ResourceBundle.getBundle("com.qentelli.automation.testdata.HealthCheck");
	private List<String> healthCheckATGURLS;
	private String instance = "";
	private List<String> healthCheckATGResponses;

	private List<String> goodATGURLS = new ArrayList<>();
	private Map<String, String> badATGURLS = new HashMap<String, String>();
	private String getEmailToCommon = healthCheck.getString("email.group.common");
	private String getEmailToATG = healthCheck.getString("email.group.atg");
	// private String tempURLS;
	private RestRequest restRequest;
	private RESTAPIService restapiService;

	private String hostname = "";
	private String port = "";
	private String databaseName = "";
	private String username = "";
	private String password = "";
	private String atgOrderQueueSize = "";

	public ATG(World world) {
		// super(world, world.driver);
		this.world = world;
		restRequest = new RestRequest(world);
		restapiService = new RESTAPIService(world);
		con = new OracleConnection(world);
		InitElements();
	}

	public void InitElements() {
		logger = LogManager.getLogger(ATG.class);
		logger.info("Environment=======" + world.getTestEnvironment().toLowerCase());
		instance = world.getTestEnvironment().toLowerCase();
		logger.info("Environment : " + instance);
		// tempURLS = healthCheck.getString(world.getTestEnvironment().toLowerCase() +
		// ".atg.healthcheck.urls");
		hostname = healthCheck.getString(world.getTestEnvironment().toLowerCase() + ".atgDB.host");
		port = healthCheck.getString(world.getTestEnvironment().toLowerCase() + ".atgDB.port");
		databaseName = healthCheck.getString(world.getTestEnvironment().toLowerCase() + ".atgDB.database");
		username = healthCheck.getString(world.getTestEnvironment().toLowerCase() + ".atgDB.username");
		password = healthCheck.getString(world.getTestEnvironment().toLowerCase() + ".atgDB.password");
	}

	public void getATGHealthURLS() {
		logger.debug("Retrieved ATG Health Check URL's from properties for Env " + instance + " : "
				+ ApplicationsEndpointObject.hce2e.atgHealthcheck);
		healthCheckATGURLS = Arrays.asList(ApplicationsEndpointObject.hce2e.atgHealthcheck);
		logger.debug("Retrieved ATG Health Check URL's from properties: ");
		healthCheckATGURLS.forEach(url -> logger.debug(url));
	}

	public void sendHealthCheckATGRequests() {
		HCTestcaseResourceBundle testdata = new HCTestcaseResourceBundle("ATG");
		healthCheckATGResponses = new ArrayList<>();
		logger.debug("ATG url count is : " + healthCheckATGURLS.size());
		String[] arry = healthCheckATGURLS.get(0).split(",");
		float total = 0;
		float passed = 0;
		for (String url : arry) {
			logger.debug(total + " ATG url is : " + url);
			total++;
			try {
				restRequest.tryGetRequest(url);
				String response = restRequest.getResponseAsStringNew();
				logger.debug("- This is the response: " + response);
				healthCheckATGResponses.add(response);
				if (response.contains("RUNNING") || response.contains("cat: Connected to"))
					passed++;
			} catch (Exception e) {
				logger.debug("- Exception for url : " + url + " " + e.getLocalizedMessage());
				logger.debug("- This is the BAD response for url : " + url);
				badATGURLS.put(url, e.getLocalizedMessage());
			}
		}
		logger.debug("Total BAD responses for url : " + badATGURLS);
		logger.debug("theshold ->" + testdata.threshold + "Total GOOD responses for url : " + healthCheckATGResponses);
		if (testdata.threshold != null) {
			if (passed == 0)
				Assert.assertTrue(passed != 0, "no end points passed");
			float f = passed / total;
			float threshold = Float.valueOf(testdata.threshold);
			logger.info(passed + " |||| " + total + "|||| " + f + "<thrreshold>" + threshold);
			if (f < threshold)
				sendEmail("snelson@qentelli.com;dcaruana@qentelli.com;dbuckholz@qentelli.com",
						"[" + AbstractResourceBundle.getEnv() + "] Not enough atg servers",
						"At least " + threshold * 100 + "% of the atg cluster is required for "
								+ AbstractResourceBundle.getEnv() + " (" + passed + " of " + total + " passed ["
								+ passed / total * 100 + "%])");

			Assert.assertTrue(f >= threshold,
					" Proves a threshold of atg servers are reached " + f + " >= " + threshold);
		}
	}

	public void sendEmail(String toEmail, String sub, String msg) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String datePrintInEmail = formatter.format(date).toString();
		System.out.println("Subject to print date: " + datePrintInEmail);
		// Get properties object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "mail.qentelli.com");
		Session session = Session.getDefaultInstance(properties);

		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply-qaautomation@qentelli.com"));
			// String to = System.getProperty("sendTo.email");
			String to = toEmail;
			for (String to_1 : to.split(";")) {
				logger.debug(to_1);
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_1));
			}
			message.setSubject(sub + " - " + datePrintInEmail);
			message.setContent(msg, "text/html; charset=utf-8");
			// send message
			Transport.send(message);
			logger.debug("message sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public Boolean checkATGHealthRequests() {
		Boolean pass = true;
		logger.debug("ATG url count is : " + healthCheckATGURLS.size());
		String[] arry = healthCheckATGURLS.get(0).split(",");

		logger.debug("ATG url response(which is not empty) count is  : " + healthCheckATGResponses.size());
		logger.debug("ATG all url response in array list   : " + healthCheckATGResponses);
		int index = 0;
		int goodURLSeq = 1;
		for (String response : healthCheckATGResponses) {
			logger.debug(index + " Checking this response: " + response);
			if (!response.isEmpty()) {
				if (response.contains("Ncat: Connected to "))
					return true;
				if (response.contains("Error 500")) {
					badATGURLS.put(healthCheckATGURLS.get(index), "status : Error 500--Internal Server Error");
					pass = false;
				} else if (response.contains("Error 404")) {
					badATGURLS.put(healthCheckATGURLS.get(index), "Error 404--Not Found");
					pass = false;
				} else {
					boolean flag = true;
					try {
						JSONObject serverResponse = new JSONObject(response);
						logger.info(index + " - JSONObject : " + serverResponse.toString());

						if (!serverResponse.getString("status").equalsIgnoreCase("RUNNING")) {
							pass = false;
							flag = false;
							badATGURLS.put(healthCheckATGURLS.get(index),
									"status : " + serverResponse.getString("status"));
						} else {
							for (String s : serverResponse.getJSONObject("details").keySet()) {

								logger.info(s + " : " + serverResponse.getJSONObject("details").getString(s));
								if (!serverResponse.getJSONObject("details").getString(s).equalsIgnoreCase("RUNNING")) {
									pass = false;
									flag = false;
									badATGURLS.put(healthCheckATGURLS.get(index), "details:" + s + " : "
											+ serverResponse.getJSONObject("details").getString(s));
									break;
								}
							}
						}
						if (flag) {
							goodATGURLS.add(goodURLSeq + ") " + arry[index]);
							goodURLSeq++;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.debug(e.getLocalizedMessage());
						badATGURLS.put(healthCheckATGURLS.get(index), "");
					}
				}
				/*
				 * if(! response.equals(testConfig.getHealthCheckATGResponse())){ pass = false;
				 * badATGURLS.add(healthCheckATGURLS.get(index)); }
				 */
//			} else {
//				badATGURLS.put(healthCheckATGURLS.get(index), "No Response");
//			}
				index++;
			}
		}
		String temp = "";
		if (!pass) {
			logger.debug("Bad ATG urls count is : " + badATGURLS.size());
			/*
			 * for (String s1 : badATGURLS.keySet()) { temp = temp + "<tr><td>" + s1.trim()
			 * + "</td><td>" + badATGURLS.get(s1).trim() + "</td></tr>"; }
			 */
		}
		logger.debug("ATGSERVICE urls are which are not RUNNING i.e., " + temp);
		logger.debug("Bad ATG urls count is : " + badATGURLS.size());
		logger.debug("Good ATG urls count is : " + goodATGURLS.size());
		if (goodATGURLS.size() == 0) {
			logger.debug("None of the ATGSERVICE urls are RUNNING");
			CheckATGServiceHealth = "fail";
			for (String s1 : badATGURLS.keySet()) {
				temp = temp + "<tr><td>" + s1.trim() + "</td><td>" + badATGURLS.get(s1).trim() + "</td></tr>";
			}
			restapiService.sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - ATG services are not RUNNING",
					"None of the ATGSERVICE urls are RUNNING, so E2E will not be executed.<br>Please find "
							+ world.getTestEnvironment().toUpperCase()
							+ " - ATG service details which are not RUNNING <br><br><table border='1'><tr><td><b>URLs</b></td><td><b>Details</b></td></tr>"
							+ temp + "</table><br><br>Please find URLs below which are RUNNING normal<br>"
							+ String.join("<br>", goodATGURLS));
			restapiService.updateHealthCheckPoint("ATGSERVICE", "FAIL");
			restapiService.updateHealthCheckPoint("E2E", "NOT READY");
			// restapiService.updateHealthCheckDB("ATGSERVICE", String.join("|",
			// badATGURLS.keySet()));
		} else if (badATGURLS.size() != 0) {
			CheckATGServiceHealth = "pass";
			for (String s1 : badATGURLS.keySet()) {

				temp = temp + "<tr><td>" + s1.trim() + "</td><td>" + badATGURLS.get(s1).trim() + "</td></tr>";
			}
			logger.debug("ATGSERVICE urls which are not RUNNING");
			restapiService.sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - ATG services are not RUNNING",
					"ATGSERVICE urls out of " + healthCheckATGURLS.size() + ", " + badATGURLS.size()
							+ " are not RUNNING.<br>Please find " + world.getTestEnvironment().toUpperCase()
							+ " - ATG service details which are not RUNNING <br><br><table border='1'><tr><td><b>URLs</b></td><td><b>Details</b></td></tr>"
							+ temp + "</table><br><br>Please find URLs below which are RUNNING normal<br>"
							+ String.join("<br>", goodATGURLS));
			// restapiService.updateHealthCheckDB("ATGSERVICE", "PASS");
			restapiService.updateHealthCheckPoint("ATGSERVICE", "PASS");
		} else {
			CheckATGServiceHealth = "pass";
			if (!pass)
				restapiService.sendEmail(getEmailToATG,
						world.getTestEnvironment().toUpperCase() + " - ATG services are not RUNNING",
						"Please find " + world.getTestEnvironment().toUpperCase()
								+ " - ATG service details which are not RUNNING <br><br><table border='1'><tr><td><b>URLs</b></td><td><b>Details</b></td></tr>"
								+ temp + "</table><br><br>Please find URLs below which are RUNNING normal<br>"
								+ String.join("<br>", goodATGURLS));
			restapiService.updateHealthCheckPoint("ATGSERVICE", "PASS");
			// restapiService.updateHealthCheckDB("ATGSERVICE", "PASS");
		}

		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		for (String goodUrl : goodATGURLS) {
			scenarioSteps.put(goodUrl, "PASSED");
		}
		for (String badUrl : badATGURLS.keySet()) {
			scenarioSteps.put(badUrl, "FAILED");
		}
		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		if (scenarioSteps.containsValue("FAILED"))
			pass = false;
		return pass;
	}

	public String getBadATGURLS() {
		String returnURLString = String.join(",", badATGURLS.keySet());
		return returnURLString;
	}

	public boolean getATGDBConnectionDetails() {
		if (world.getTestEnvironment() == null) {
			restapiService.sendEmail(getEmailToATG,
					world.getTestEnvironment().toUpperCase() + " - ATG order queue size failed",
					" In health check, ATG order queue size is only implemented for UAT and QA3. Hence E2E will not be executed for "
							+ world.getTestEnvironment().toUpperCase());
			return false;
		}
		/*
		 * Properties properties = new Properties(); String filePath =
		 * Objects.requireNonNull(getClass().getClassLoader().getResource(
		 * "application-common.properties")).getPath(); try { properties.load(new
		 * FileReader(filePath)); } catch (FileNotFoundException | NullPointerException
		 * e) { logger.error("The properties file " + filePath + " cannot be found"); }
		 * catch (IOException e) {
		 * logger.error("I/O exception during loading of properties file: " + filePath);
		 * }
		 */
		if (hostname != null) {
			/*
			 * hostname =
			 * properties.get("atgDB."+world.getTestEnvironment().toUpperCase()+".host").
			 * toString(); port =
			 * properties.get("atgDB."+world.getTestEnvironment().toUpperCase()+".port").
			 * toString(); databaseName =
			 * properties.get("atgDB."+world.getTestEnvironment().toUpperCase()+".database")
			 * .toString(); username =
			 * properties.get("atgDB."+world.getTestEnvironment().toUpperCase()+".username")
			 * .toString(); password =
			 * properties.get("atgDB."+world.getTestEnvironment().toUpperCase()+".password")
			 * .toString();
			 */
			System.out.println(hostname + "\n" + port + "\n" + databaseName + "\n" + username + "\n" + password);
		}
		/*
		 * if (world.getTestEnvironment().equalsIgnoreCase("uat")) { hostname =
		 * testConfig.getAtgDBUATHost(); port = testConfig.getAtgDBUATPort();
		 * databaseName = testConfig.getAtgDBUATDatabase(); username =
		 * testConfig.getAtgDBUATUsername(); password =
		 * testConfig.getAtgDBUATPassword(); } else if
		 * (world.getTestEnvironment().equalsIgnoreCase("qa3")) { hostname =
		 * testConfig.getAtgDBQA3Host(); port = testConfig.getAtgDBQA3Port();
		 * databaseName = testConfig.getAtgDBQA3Database(); username =
		 * testConfig.getAtgDBQA3Username(); password =
		 * testConfig.getAtgDBQA3Password(); }
		 */
		else {

			System.out.println(hostname + "\n" + port + "\n" + databaseName + "\n" + username + "\n" + password);
			restapiService.sendEmail(getEmailToATG,
					world.getTestEnvironment().toUpperCase() + " - ATG order queue size validation failed",
					" In health check, ATG order queue size is only implemented for UAT and QA3. Hence E2E will not be executed for "
							+ world.getTestEnvironment().toUpperCase());
			return false;
		}
		return true;
	}

	public String[] getATGOrderQueueSize() {

		try {
			if (world.getTestEnvironment() != null) {
				con.makeOracleConnectionUsingServiceName(hostname, port, databaseName, username, password);
				logger.debug("Connected to " + world.getTestEnvironment() + "-ATG-DB.");

				ResultSet rs = con.query("SELECT COUNT(1) FROM BB_CORE.DCSPP_ORDER WHERE STATE='SUBMITTED'");

				if (rs.next()) {
					atgOrderQueueSize = rs.getString("COUNT(1)");
					logger.info("ATG order queue size is : " + atgOrderQueueSize);
				} else {
					throw new RuntimeException(
							"Failed Query --SELECT COUNT(1) FROM BB_CORE.DCSPP_ORDER WHERE STATE='SUBMITTED'--");
				}
			} else {
				throw new RuntimeException("world.getTestEnvironment() returns null");
			}
			String[] result = { "pass" };
			return result;
		} catch (Exception e) {
			logger.info(e.getLocalizedMessage());
			/*
			 * restapiService.sendEmail(getEmailToATG,
			 * world.getTestEnvironment().toUpperCase() +
			 * " - ATG order queue size validation failed",
			 * "Failure occurred while getting ATG order queue size from DB <br> "+e.
			 * getLocalizedMessage());
			 */
			String[] result = { "fail", e.getLocalizedMessage() };
			return result;
		}

	}

	public boolean validateATGOrderQueueSize() {
		String scenarioName = "Check ATG Order Queue Size";
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		RuntimeProperties rp = new RuntimeProperties();
		logger.info("Validation for ATG order queue size is : " + atgOrderQueueSize);
		rp.writeProp("ATG", "0");
		if (atgOrderQueueSize != null) {
			if (Integer.parseInt(atgOrderQueueSize) < 120) {
				restapiService.updateHealthCheckPoint("ATGORDERCOUNT", "PASS");
				scenarioSteps.put("ATG order queue size is less than 120 actual " + atgOrderQueueSize, "PASSED");
				RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
				return true;
			} else {
				// restapiService.updateHealthCheckDB("ATGORDERCOUNT", atgOrderQueueSize);
				restapiService.updateHealthCheckPoint("ATGORDERCOUNT", "FAIL");
				restapiService.updateHealthCheckPoint("E2E", "NOT READY");
				restapiService.sendEmail(getEmailToATG,
						world.getTestEnvironment().toUpperCase() + " - ATG order queue size validation failed",
						"ATG order queue size is greater than or equal to 120, hence E2E will not be executed <br> Current queue size is : "
								+ atgOrderQueueSize);
				scenarioSteps.put("ATG order queue size is less than 120 actual" + atgOrderQueueSize, "FAILED");
				RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);

				return false;
			}

		} else {
			/*
			 * restapiService.sendEmail(getEmailToATG,
			 * world.getTestEnvironment().toUpperCase() + " - ATG order queue size failed",
			 * "Failure occurred while getting ATG order queue size from DB <br> ");
			 */
			restapiService.updateHealthCheckPoint("ATGORDERCOUNT", "PASS");
			scenarioSteps.put("ATG order queue size is less than 120", "PASSED");
			RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
			return false;
		}
	}
}
