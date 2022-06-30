package com.qentelli.automation.utilities.healthcheck;

import static io.restassured.RestAssured.given;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.dto.Point;
import org.json.JSONArray;
import org.json.JSONObject;

import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.base.AppIssueException;
import com.qentelli.automation.listeners.ResultSender;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;
import com.qentelli.automation.utilities.HCE2EndpointObject;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.api.RestRequest;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class RESTAPIService {
	private static int firstEntrytoE2E = 0;
	private World world = null;
	// private static final Logger logger =
	// LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Logger logger;
	ResourceBundle healthCheck = ResourceBundle.getBundle("com.qentelli.automation.testdata.HealthCheck");
	private boolean statusFound;
	private RestRequest restRequest;
	private String datePrintInEmail = "";
	HCE2EndpointObject hcEP = null;

	private String compassUrl;

	private Response response;
	private Map<String, String> pendingEBSJobs = new HashMap<String, String>();
	private Map<String, String> runningEBSJobs = new HashMap<String, String>();
	private Map<String, String> inactiveEBSJobs = new HashMap<String, String>();
	private Map<String, List<String>> mgServerSOA = new HashMap<String, List<String>>();
	private Map<String, List<String>> mgServerATG = new HashMap<String, List<String>>();
	private Map<String, List<String>> mgServerOSB = new HashMap<String, List<String>>();
	private Map<String, List<String>> soaServiceMap = new HashMap<String, List<String>>();

	private List<String> badServer = new ArrayList<>();
	private List<String> goodServer = new ArrayList<>();

	public RESTAPIService(World world) {

		// super(world, world.driver);
		this.world = world;
		restRequest = new RestRequest(world);
		InitElements();
	}

	public void InitElements() {
		logger = LogManager.getLogger(RESTAPIService.class);
		hcEP = new HCE2EndpointObject();
		logger.info(" syncIDURL " + hcEP.syncid);

		getEmailToCommon = healthCheck.getString("email.group.common");
		getEmailToATG = healthCheck.getString("email.group.atg");
		getEmailToSOA = healthCheck.getString("email.group.soa");
		getEmailToOSB = healthCheck.getString("email.group.osb");
		getEmailToEBS = healthCheck.getString("email.group.ebs");

		getStrGUID = healthCheck.getString(world.getTestEnvironment().toLowerCase() + ".order.import.guid");
		getCustomerNumber = healthCheck
				.getString(world.getTestEnvironment().toLowerCase() + ".order.import.customernumber");
		getGncCustomerID = healthCheck
				.getString(world.getTestEnvironment().toLowerCase() + ".order.import.gnccustomerid");
		getGncSponsorID = healthCheck
				.getString(world.getTestEnvironment().toLowerCase() + ".order.import.gncsponsorid");
		getCustomerEmailID = healthCheck
				.getString(world.getTestEnvironment().toLowerCase() + ".order.import.customeremailid");
		compassUrl = healthCheck.getString("compassapi");
	}

	private String syncId = "";
	// private String syncIDURL;
	private String syncInstanceName = "";
	private String getEmailToCommon;
	private String getEmailToATG;
	private String getEmailToSOA;
	private String getEmailToOSB;
	private String getEmailToEBS;

	private String getStrGUID;
	private String getCustomerNumber;
	private String getGncCustomerID;
	private String getGncSponsorID;
	private String getCustomerEmailID;

	public String getCompassAPI() {
		logger.debug("======================getCompassAPI=================================");
		logger.debug("Env: " + world.getTestEnvironment());
		logger.debug(compassUrl);
		return compassUrl;

	}

	public void getHealthCheckSyncIdURL() {
		logger.debug("======================getHealthCheckSyncIdURL=================================");
		logger.debug("Env: " + world.getTestEnvironment());
		// healthCheckSyncIdUrl = syncIDURL;
		logger.debug("Retrieved SyncId URL from properties: ");
		logger.debug(hcEP.syncid);

	}

	public void getHealthCheckIngestURL() {
		logger.debug("======================getHealthCheckIngestURL=================================");
		logger.debug("Env: " + world.getTestEnvironment());
		// healthCheckIngestURL = ; // getIngestStatusUrl;
//        if(world.getTestEnvironment().toUpperCase().equals("UAT")) {
		// healthCheckHeaderXAPIKey = getIngestHeaderXAPIKey;
		logger.debug("Retrieved co ingest URL from properties: ");
		logger.debug(ApplicationsEndpointObject.coo.cooHealthCheck);

	}
	/*
	 * public void getHealthCheckSOAServiceUrl() { logger.debug(
	 * "======================getHealthCheckSOAServiceUrl================================="
	 * );
	 * 
	 * healthCheckSOAServiceUrl = testConfig.getHealthCheckSOAServiceUrl();
	 * logger.debug("Retrieved SOA Health Check URL's from properties: ");
	 * logger.debug(healthCheckSOAServiceUrl); }
	 * 
	 * public void getHealthCheckManageServersUrl() { logger.debug(
	 * "======================getHealthCheckManageServerUrl================================="
	 * );
	 * 
	 * healthCheckManageServersUrl = testConfig.getHealthCheckManageServersUrl();
	 * logger.debug("Retrieved Managed Server Health Check URL's from properties: "
	 * ); logger.debug(healthCheckManageServersUrl); }
	 */

	public void sendRequestAndStoreSyncId() {
		logger.debug("======================sendRequestAndStoreSyncId=================================");
		logger.debug(hcEP.syncid);
		restRequest.tryGetRequest(hcEP.syncid);
		String response = restRequest.getResponseAsString();
		logger.debug("This is the response: " + response);
		if (response.contains("Internal Server Error")) {
			new RuntimeException("SyncID URL " + hcEP.syncid + " response return with Internal Server Error");
		}
		try {
			syncId = restRequest.getRestResponseValue("items");
			logger.debug("Sync Id is : " + syncId);

			syncId = syncId.substring(syncId.indexOf(":") + 1, syncId.indexOf(","));

			logger.debug("Sync Id is : " + syncId);

//			syncId = syncId.substring(syncId.indexOf("sync_id:"), syncId.indexOf(", sync_instance_name"))
//					.replace("sync_id:", "").trim();
			syncInstanceName = restRequest.getRestResponseValue("items");
			syncInstanceName = syncInstanceName
					.substring(syncInstanceName.indexOf("sync_instance_name:"), syncInstanceName.indexOf("]]"))
					.replace("sync_instance_name:", "").trim();
			logger.info("Sync Id: " + syncId + ", Instance Name is : " + syncInstanceName);
		} catch (Exception e) {
			logger.info("Failed due to SyncId not found");
			new RuntimeException("Failed due to SyncId not found");
		}
	}

	public String sendSOAServiceManageServerRequest(String requestService) {
		String response = "";
		// String [] appN={"SOA","ManageServers"};
		// for(int k=0;k<appN.length;k++) {
		// String appName = appN[k];
		if (requestService.equals("SOA Services")) {
			logger.debug("======================sendSOAServiceRequest=================================");
			restRequest.tryGetRequest(ApplicationsEndpointObject.hce2e.soaservices + syncId + "/SOA");
			response = restRequest.getResponseAsString();
			logger.debug("This is the request response: " + response);
			String temp = restRequest.getRestResponseValue("items");
			logger.debug("This is the REST response value: " + temp);
			response = response.substring(response.indexOf("{\"items\":"), response.lastIndexOf("}"))
					.replace("{\"items\":", "");
			logger.debug("This is for JSON response: " + response);
			// JSONObject obj = new JSONObject();
			JSONArray jsonArray = new JSONArray(response);
			logger.debug("JSON array: " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {

				String items = jsonArray.get(i).toString();
				logger.info(i + " : " + items);
				String[] temp1 = items.split(",");
				String servName = "";
				String servStatus = "";
				for (int j = 0; j < temp1.length; j++) {
					if (temp1[j].contains("service_name")) {
						logger.info("    " + j + " - " + temp1[j].replace("}", ""));
						servName = temp1[j].replace("\"service_name\":", "").replaceAll("[{}\"]+", "").trim();
					} else if (temp1[j].replace("\"", "").trim().startsWith("status")) {
						logger.info("    " + j + " - " + temp1[j].replace("}", ""));
						servStatus = temp1[j].replace("\"status\":", "").replaceAll("[{}\"]+", "").trim();
					}
				}
				if (soaServiceMap.containsKey(servStatus)) {
					soaServiceMap.get(servStatus).add(servName);

				} else {
					List<String> list = new ArrayList<String>();
					list.add(servName);
					soaServiceMap.put(servStatus, list);
				}
			}
		}
		// if (appName.equals("ManageServers")) {
		// String[] mgServers = {"SOA", "ATG", "OSB"};
		// for (int mg = 0; mg < mgServers.length; mg++) {
		else {
			logger.debug("======================sendManagedServerRequest=================================");
			String mgServer = "";
			if (requestService.equalsIgnoreCase("ATG Server"))
				mgServer = "ATG";
			else if (requestService.equalsIgnoreCase("SOA Server"))
				mgServer = "SOA";
			else if (requestService.equalsIgnoreCase("OSB Server"))
				mgServer = "OSB";
			// sendRequestAndStoreSyncId();
			logger.info(ApplicationsEndpointObject.hce2e.mgserver + syncId + "/" + mgServer);
			restRequest.tryGetRequest(ApplicationsEndpointObject.hce2e.mgserver + syncId + "/" + mgServer);
			response = restRequest.getResponseAsString();
			// logger.debug("This is the request response: " + response);
			String temp = restRequest.getRestResponseValue("items");
			logger.debug("This is the REST response value: " + temp);
			response = response.substring(response.indexOf("{\"items\":"), response.lastIndexOf("}"))
					.replace("{\"items\":", "");
			logger.debug("This is for JSON response: " + response);
			JSONObject obj = new JSONObject();
			JSONArray jsonArray = new JSONArray(response);
			logger.debug("JSON array: " + jsonArray.length());
			logger.debug("======Managed Servers - " + mgServer + "=========");

			for (int i = 0; i < jsonArray.length(); i++) {

				String abc = jsonArray.get(i).toString();
				// logger.info(i + " : " + abc);
				String[] temp1 = abc.split(",");
				String servName = "";
				String servStatus = "";
				for (int j = 0; j < temp1.length; j++) {

					if (temp1[j].contains("instance_name")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
					} else if (temp1[j].replace("\"", "").trim().startsWith("manage_server_name")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						servName = temp1[j].replace("\"manage_server_name\":", "").replaceAll("[{}\"]+", "").trim();
					} else if (temp1[j].replace("\"", "").trim().contains("manage_server_status")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						servStatus = temp1[j].replace("\"manage_server_status\":", "").replaceAll("[{}\"]+", "").trim();
					} else if (temp1[j].replace("\"", "").trim().contains("last_status")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
					} else if (temp1[j].replace("\"", "").trim().startsWith("manage_server_health_status")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
					}

				}
				Map<String, List<String>> mgMap = null;
				if (mgServer.equalsIgnoreCase("ATG"))
					mgMap = mgServerATG;
				else if (mgServer.equalsIgnoreCase("SOA"))
					mgMap = mgServerSOA;
				else if (mgServer.equalsIgnoreCase("OSB"))
					mgMap = mgServerOSB;

				if (mgMap.containsKey(servStatus.toUpperCase())) {
					mgMap.get(servStatus.toUpperCase()).add(servName);

				} else {
					List<String> list = new ArrayList<String>();
					list.add(servName);
					mgMap.put(servStatus.toUpperCase(), list);
				}
			}
		}
		return response;
	}

	public boolean ebsConcurrentJobRequest() {
		String[] stat = { "Pending", "Running", "Inactive" };
		String returnString = "";
		boolean testCaseStatus = true;
		List<String> badPgm = new ArrayList<String>();
		List<String> goodPgm = new ArrayList<String>();

		for (int k = 0; k < stat.length; k++) {

			String status = stat[k];
			logger.debug("======================sendConcurrentJobRequest=================================");

			restRequest
					.tryGetRequest(ApplicationsEndpointObject.hce2e.ebsccjob + syncId + "/" + status + "?limit=2000");
			String responseString = restRequest.getResponseAsString();
			// logger.debug("This is the request response: " + response);
			String temp = restRequest.getRestResponseValue("items");
			// logger.debug("This is the REST response value: " + temp);
			responseString = responseString
					.substring(responseString.indexOf("{\"items\":"), responseString.lastIndexOf("}"))
					.replace("{\"items\":", "");
			// logger.debug("This is for JSON response: " + response);
			JSONObject obj = new JSONObject();
			JSONArray jsonArray = new JSONArray(responseString);
			logger.debug("JSON array: " + jsonArray.length());
			logger.debug("======EBS Concurrent Job Requests - Status - " + status + "=========");
			returnString = returnString + "======EBS Concurrent Job Requests - Status - " + status + "=========\n";
			String reqId = "";
			String pgmName = "";
			String phase = "";
			String status1 = "";
			for (int i = 0; i < jsonArray.length(); i++) {

				String items = jsonArray.get(i).toString();
				// logger.info(i + " : " + abc);
				String[] temp1 = items.split(",");

				for (int j = 0; j < temp1.length; j++) {
					if (temp1[j].contains("instance_name")) {
						// logger.info(" ============= ");
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
					} else if (temp1[j].replace("\"", "").trim().startsWith("conc_prog_name")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						pgmName = temp1[j].replace("\"conc_prog_name\":", "").replaceAll("[{}\"]+", "").trim();
					} else if (temp1[j].replace("\"", "").trim().startsWith("request_id")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						reqId = temp1[j].replace("\"request_id\":", "").replaceAll("[{}\"]+", "").trim();
					} else if (temp1[j].replace("\"", "").trim().contains("program_current_phase")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						phase = "<i><u>Phase: "
								+ temp1[j].replace("\"program_current_phase\":", "").replaceAll("[{}\"]+", "").trim()
								+ "</u></i>";
					} else if (temp1[j].replace("\"", "").trim().contains("program_current_status")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						status1 = "<i>Status: "
								+ temp1[j].replace("\"program_current_status\":", "").replaceAll("[{}\"]+", "").trim()
								+ "</i>";
					} else if (temp1[j].replace("\"", "").trim().startsWith("responsibility_name")) {
						// logger.info(" " + j + " - " + temp1[j].replace("}", ""));
						pgmName = " <b> " + pgmName + " : "
								+ temp1[j].replace("\"responsibility_name\":", "").replaceAll("[{}\"]+", "").trim()
								+ " </b>";
					}
				}
				pgmName = pgmName + " - " + phase + " - " + status1;
				returnString = returnString + reqId + " : " + pgmName + "\n";
				// logger.debug("Return String: "+returnString);
				if (status.equalsIgnoreCase("Pending")) {
					pendingEBSJobs.put(reqId, pgmName);
				} else if (status.equalsIgnoreCase("Running")) {
					runningEBSJobs.put(reqId, pgmName);
				} else if (status.equalsIgnoreCase("Inactive")) {
					inactiveEBSJobs.put(reqId, pgmName);
				}
			}
		}

		String onHold = "";
		boolean inactivePPEBSCO = false;
		boolean inactiveOrderImport = false;
		for (String s : inactiveEBSJobs.keySet()) {
			inactiveEBSJobs.containsValue("PP EBS to Coach Office Program : PP US Job Scheduler");
			if (inactiveEBSJobs.get(s).contains("PP EBS to Coach Office Program")) {
				inactivePPEBSCO = true;
			}
			if (inactiveEBSJobs.get(s).contains("PP Batch Order Import")
					|| inactiveEBSJobs.get(s).contains("PP Order Import")) {
				inactiveOrderImport = true;
			}
			onHold = onHold + "<tr><td>" + s + "</td><td>" + inactiveEBSJobs.get(s) + "</td></tr>";
		}
		String containsFalse = "\n";
		if (inactivePPEBSCO || inactiveOrderImport) {
			badPgm.add("PP EBS to Coach Office Program : PP US Job Scheduler -  is not inactive");
			badPgm.add("PP Batch Order Import/PP Order Import - is not inactive");
			logger.debug("'PP EBS to Coach Office' or 'PP Order Import' is on HOLD ");
			sendEmail(getEmailToCommon, world.getTestEnvironment().toUpperCase() + " - EBS Jobs are OnHold ",
					"'PP EBS to Coach Office' or 'PP Order Import' status is onHOLD, so E2E will not be executed. <br>Please find "
							+ world.getTestEnvironment().toUpperCase()
							+ " - EBS Jobs details which are OnHold <br><br><table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
							+ onHold + "</table><br><br>");
			// updateHealthCheckDB("EBSJOBS","FAIL");
			updateHealthCheckPoint("EBSJOBS", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
			testCaseStatus = false;
		} else {
			goodPgm.add("PP EBS to Coach Office Program : PP US Job Scheduler -  is not inactive");
			goodPgm.add("PP Batch Order Import/PP Order Import - is not inactive");
			logger.debug("Inside else");
			if (inactiveEBSJobs.size() > 0) {
				logger.debug("inside **********" + getEmailToEBS + "********");
				sendEmail(getEmailToEBS, world.getTestEnvironment().toUpperCase() + " - EBS Jobs are OnHold ",
						"Please find " + world.getTestEnvironment().toUpperCase()
								+ " - EBS Jobs details which are OnHold <br><br><table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
								+ onHold + "</table><br><br>");
			}
			updateHealthCheckPoint("EBSJOBS", "PASS");
			// updateHealthCheckDB("EBSJOBS","PASS");

			String runningJobs = "";
			boolean ppEBSCoachOfficePrg_us = false;
			boolean ppEBSCoachOfficePrg_ca = false;
			boolean ppEBSCoachOfficePrg_uk = false;

			boolean ppOrderImport_us = false;
			boolean ppOrderImport_ca = false;
			boolean ppOrderImport_uk = false;
			for (String s : runningEBSJobs.keySet()) {
				if (runningEBSJobs.get(s).contains("PP EBS to Coach Office Program : PP US Job Scheduler")) {
					ppEBSCoachOfficePrg_us = true;
					goodPgm.add("PP EBS to Coach Office Program : PP US Job Scheduler - is running");
				}
				if (runningEBSJobs.get(s).contains("PP EBS to Coach Office Program : CA JOB SCHEDULER")) {
					ppEBSCoachOfficePrg_ca = true;
					goodPgm.add("PP EBS to Coach Office Program : CA JOB SCHEDULER - is running");
				}
				if (runningEBSJobs.get(s).contains("PP EBS to Coach Office Program : UK Job Scheduler")
						|| runningEBSJobs.get(s).contains("PP EBS to Coach Office Program : UK-FR Job Scheduler")) {
					ppEBSCoachOfficePrg_uk = true;
					goodPgm.add("PP EBS to Coach Office Program : UK-FR Job Scheduler - is running");
				}
				if (runningEBSJobs.get(s).contains("Report Set:PP Order Import : PP US Job Scheduler")) {
					ppOrderImport_us = true;
					goodPgm.add("Report Set:PP Order Import : PP US Job Scheduler - is running");
				}
				if (runningEBSJobs.get(s).contains("Report Set:PP Order Import : CA JOB SCHEDULER")) {
					ppOrderImport_ca = true;
					goodPgm.add("Report Set:PP Order Import : CA JOB SCHEDULER - is running");
				}
				if (runningEBSJobs.get(s).contains("Report Set:PP Order Import : UK Job Scheduler")
						|| runningEBSJobs.get(s).contains("Report Set:PP Order Import : UK-FR Job Scheduler")) {
					ppOrderImport_uk = true;
					goodPgm.add("Report Set:PP Order Import : UK-FR Job Scheduler - is running");
				}
				runningJobs = runningJobs + "<tr><td>" + s + "</td><td>" + runningEBSJobs.get(s) + "</td></tr>";
			}
			String pendingJobs = "";
			for (String s : pendingEBSJobs.keySet()) {
				if (!ppEBSCoachOfficePrg_us
						&& pendingEBSJobs.get(s).contains("PP EBS to Coach Office Program : PP US Job Scheduler")) {
					ppEBSCoachOfficePrg_us = true;
					goodPgm.add("PP EBS to Coach Office Program : PP US Job Scheduler - is running");
				}
				if (!ppEBSCoachOfficePrg_ca
						&& pendingEBSJobs.get(s).contains("PP EBS to Coach Office Program : CA JOB SCHEDULER")) {
					ppEBSCoachOfficePrg_ca = true;
					goodPgm.add("PP EBS to Coach Office Program : CA JOB SCHEDULER - is running");
				}
				if ((!ppEBSCoachOfficePrg_uk
						&& pendingEBSJobs.get(s).contains("PP EBS to Coach Office Program : UK Job Scheduler"))
						|| (!ppEBSCoachOfficePrg_uk && pendingEBSJobs.get(s)
								.contains("PP EBS to Coach Office Program : UK-FR Job Scheduler"))) {
					ppEBSCoachOfficePrg_uk = true;
					goodPgm.add("PP EBS to Coach Office Program : UK-FR Job Scheduler - is running");
				}
				if (!ppOrderImport_us
						&& pendingEBSJobs.get(s).contains("Report Set:PP Order Import : PP US Job Scheduler")) {
					ppOrderImport_us = true;
					goodPgm.add("Report Set:PP Order Import : PP US Job Scheduler - is running");
				}
				if (!ppOrderImport_ca
						&& pendingEBSJobs.get(s).contains("Report Set:PP Order Import : CA JOB SCHEDULER")) {
					ppOrderImport_ca = true;
					goodPgm.add("Report Set:PP Order Import : CA JOB SCHEDULER - is running");
				}
				if ((!ppOrderImport_uk
						&& pendingEBSJobs.get(s).contains("Report Set:PP Order Import : UK Job Scheduler"))
						|| (!ppOrderImport_uk && pendingEBSJobs.get(s)
								.contains("Report Set:PP Order Import : UK-FR Job Scheduler"))) {
					ppOrderImport_uk = true;
					goodPgm.add("Report Set:PP Order Import : UK-FR Job Scheduler - is running");
				}
				pendingJobs = pendingJobs + "<tr><td>" + s + "</td><td>" + pendingEBSJobs.get(s) + "</td></tr>";
			}
			if (!ppEBSCoachOfficePrg_us || !ppOrderImport_us || !ppEBSCoachOfficePrg_ca || !ppOrderImport_ca
					|| !ppEBSCoachOfficePrg_uk || !ppOrderImport_uk) {
				if (!ppEBSCoachOfficePrg_us) {
					containsFalse = containsFalse
							+ "<b>     'PP EBS to Coach Office Program : PP US Job Scheduler', </b>\n";
					badPgm.add("PP EBS to Coach Office Program : PP US Job Scheduler - is running");
				}
				if (!ppOrderImport_us) {

					containsFalse = containsFalse
							+ "<b>     'Report Set:PP Order Import : PP US Job Scheduler', </b>\n";
					badPgm.add("Report Set:PP Order Import : PP US Job Scheduler - is running");
				}
				if (!ppEBSCoachOfficePrg_ca) {
					containsFalse = containsFalse
							+ "<b>     'PP EBS to Coach Office Program : CA JOB SCHEDULER', </b>\n";
					badPgm.add("PP EBS to Coach Office Program : CA JOB SCHEDULER - is running");
				}
				if (!ppOrderImport_ca) {
					containsFalse = containsFalse + "<b>     'Report Set:PP Order Import : CA JOB SCHEDULER', </b>\n";
					badPgm.add("Report Set:PP Order Import : CA JOB SCHEDULER - is running");
				}
				if (world.getTestEnvironment().toUpperCase().equalsIgnoreCase("QA3")) {
					if (!ppEBSCoachOfficePrg_uk) {
						containsFalse = containsFalse
								+ "<b>     'PP EBS to Coach Office Program : UK-FR Job Scheduler', </b>\n";
						badPgm.add("PP EBS to Coach Office Program : UK-FR Job Scheduler - is running");
					}
					if (!ppOrderImport_uk) {
						containsFalse = containsFalse
								+ "<b>     'Report Set:PP Order Import : UK-FR Job Scheduler', </b>\n";
						badPgm.add("Report Set:PP Order Import : UK-FR Job Scheduler - is running");
					}
				} else {
					if (!ppEBSCoachOfficePrg_uk) {
						containsFalse = containsFalse
								+ "<b>     'PP EBS to Coach Office Program : UK-FR Job Scheduler', </b>\n";
						badPgm.add("PP EBS to Coach Office Program : UK-FR Job Scheduler - is running");
					}
					if (!ppOrderImport_uk) {
						containsFalse = containsFalse
								+ "<b>     'Report Set:PP Order Import : UK-FR Job Scheduler', </b>\n";
						badPgm.add("Report Set:PP Order Import : UK-FR Job Scheduler - is running");
					}
				}

				logger.info("'PP EBS to Coach Office Program' or 'PP Order Import' jobs are not in running jobs");
				logger.info("========================================");
				if (containsFalse.contains("PP EBS to Coach Office Program")) {
					sendEmail(getEmailToCommon,
							world.getTestEnvironment().toUpperCase() + " - EBS Jobs which are not running or scheduled",
							"Below programs :<br>\t\t" + containsFalse
									+ "<br>   are not in either of Running or Scheduled jobs, so <b>E2E will not be executed.</b> "
									+ "<br><br><b>Please find " + world.getTestEnvironment().toUpperCase()
									+ " - EBS Jobs details which are Running and its count is : "
									+ runningEBSJobs.size() + "</b><br>"
									+ "<table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
									+ runningJobs + "</table><br><br><br>" + "<b>Please find "
									+ world.getTestEnvironment().toUpperCase()
									+ " - EBS Jobs details which are Schedule(pending) and its count is : "
									+ pendingEBSJobs.size() + "</b><br>"
									+ "<table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
									+ pendingJobs + "</table><br><br>");
					// updateHealthCheckDB("EBSJOBS","FAIL");
					updateHealthCheckPoint("EBSJOBS", "FAIL");
					updateHealthCheckPoint("E2E", "NOT READY");
				} else {
					sendEmail(getEmailToCommon,
							world.getTestEnvironment().toUpperCase() + " - EBS Jobs which are not running or scheduled",
							"Below programs :<br>\t\t" + containsFalse
									+ "<br>   are not in either of Running or Scheduled jobs"
									+ "<br><br><b>Please find " + world.getTestEnvironment().toUpperCase()
									+ " - EBS Jobs details which are Running and its count is : "
									+ runningEBSJobs.size() + "</b><br>"
									+ "<table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
									+ runningJobs + "</table><br><br><br>" + "<b>Please find "
									+ world.getTestEnvironment().toUpperCase()
									+ " - EBS Jobs details which are Schedule(pending) and its count is : "
									+ pendingEBSJobs.size() + "</b><br>"
									+ "<table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
									+ pendingJobs + "</table><br><br>");
					// updateHealthCheckDB("EBSJOBS","FAIL");
					updateHealthCheckPoint("EBSJOBS", "FAIL");
				}
				testCaseStatus = false;
				logger.error("Running and Scheduled jobs NOT present are : \n" + containsFalse);
			} else {

				logger.info("Inside else");
				logger.info("Below programs jobs are present either in running or scheduled jobs for all locales");
				if (runningEBSJobs.size() > 0) {
					// logger.info("inside **********"+testConfig.getEmailToEBS()+"********");
					logger.info("******************Running Jobs************************");
					logger.info("Running jobs count: " + runningEBSJobs.size() + "  and Scheduled jobs count: "
							+ pendingEBSJobs.size());
					/*
					 * sendEmail(testConfig.getEmailToEBS(),
					 * world.getTestEnvironment().toUpperCase() +
					 * " - EBS Jobs are running and scheduled", "Please find " +
					 * world.getTestEnvironment().toUpperCase() +
					 * " - Below programs  - EBS Jobs details which are running and its count is : "
					 * +runningEBSJobs.size()
					 * +"<br><br><table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
					 * + +pendingEBSJobs.size()+ + "</table><br><br><br>" + "Please find " +
					 * world.getTestEnvironment().toUpperCase() +
					 * " - EBS Jobs details which are Schedule(pending) and its count is : "
					 * +pendingEBSJobs.size()
					 * +"<br><br><table border='1'><tr><td><b>RequestId</b></td><td><b>Request Name</b></td></tr>"
					 * + pendingJobs + "</table><br><br>");
					 */
				}
				// updateHealthCheckDB("EBSJOBS","PASS");
				updateHealthCheckPoint("EBSJOBS", "PASS");
			}
		}
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		for (String goodUrl : goodPgm) {
			scenarioSteps.put(goodUrl, "PASSED");
		}
		for (String badUrl : badPgm) {
			scenarioSteps.put(badUrl, "FAILED");
		}
		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		if (scenarioSteps.containsValue("FAILED"))
			return false;
		else
			return true;
	}

	public void printReportBasedOnStatus() {

		logger.info("=================SOA Service========================");
		for (String status : soaServiceMap.keySet()) {
			logger.info("\t" + status + " : " + soaServiceMap.get(status));
		}

		logger.info("================= Manage Server ATG ========================");
		for (String status : mgServerATG.keySet()) {
			logger.info("\t" + status + " : " + mgServerATG.get(status));
		}
		logger.info("================= Manage Server SOA ========================");
		for (String status : mgServerSOA.keySet()) {
			logger.info("\t" + status + " : " + mgServerSOA.get(status));
		}
		logger.info("================= Manage Server OSB ========================");
		for (String status : mgServerOSB.keySet()) {
			logger.info("\t" + status + " : " + mgServerOSB.get(status));
		}
		logger.info("================= EBS Pending Jobs ========================");
		for (String reqId : pendingEBSJobs.keySet()) {
			logger.info("\t" + reqId + " : " + pendingEBSJobs.get(reqId));
		}
		logger.info("================= EBS Running Jobs ========================");
		for (String reqId : runningEBSJobs.keySet()) {
			logger.info("\t" + reqId + " : " + runningEBSJobs.get(reqId));
		}
		logger.info("================= EBS Inactive Jobs ========================");
		for (String reqId : inactiveEBSJobs.keySet()) {
			logger.info("\t" + reqId + " : " + inactiveEBSJobs.get(reqId));
		}
	}

	public Object[] checkStatus(String key) {

		logger.debug("======================" + key + "============================");
		Map<String, List<String>> list = null;
		Object[] returnValue = new Object[3];
		String dbColumn = "";
		String active = "RUNNING";
		String toEmail = "";
		String scenarioName = "";
		if (key.equalsIgnoreCase("SOA Services")) {
			dbColumn = "SOASERVICE";
			list = soaServiceMap;
			active = "Active";
			toEmail = getEmailToSOA;
			scenarioName = "Check SOA Services are Active";
		} else if (key.equalsIgnoreCase("ATG Servers")) {
			dbColumn = "ATGSERVER";
			list = mgServerATG;
			toEmail = getEmailToATG;
			scenarioName = "Check ATG Servers are RUNNING";
		} else if (key.equalsIgnoreCase("SOA Servers")) {
			dbColumn = "SOASERVER";
			list = mgServerSOA;
			toEmail = getEmailToSOA;
			scenarioName = "Check SOA Servers are RUNNING";
		} else if (key.equalsIgnoreCase("OSB Servers")) {
			dbColumn = "OSBSERVER";
			list = mgServerOSB;
			toEmail = getEmailToOSB;
			scenarioName = "Check OSB Servers are RUNNING";
		}
		///////////////////// To check all services/servers are
		///////////////////// down/////////////////////////////////////
		// if((list.size() == 1 && (list.containsKey("WARNING") ||
		///////////////////// list.containsKey("CRITICAL"))) ||
		// (list.size() == 2 && (list.containsKey("WARNING") &&
		///////////////////// list.containsKey("CRITICAL"))))
		String allFailedMsg = "";
		if (!list.containsKey(active)) {
			allFailedMsg = "None of the \"+key+\" are \"+active+\", so E2E will not be executed.<br>";
			toEmail = getEmailToCommon;
			returnValue[2] = false;
			logger.debug("All are down");
		} else {
			allFailedMsg = "";
			returnValue[2] = true;
			logger.debug("All are not down");
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		String failedServers = "";
		if (list.size() == 1 && list.containsKey(active)) {
			returnValue[0] = true;
			returnValue[1] = "All are " + active;
			goodServer = list.get(active);
		} else {
			returnValue[0] = false;
			String temp = "";
			for (String status : list.keySet()) {
				if (!status.equalsIgnoreCase(active)) {
					temp = temp + status + " : " + list.get(status) + "\n";
					failedServers = failedServers + "<tr><td>" + status + "</td><td>"
							+ String.join("<br>", list.get(status)) + "</td></tr>";
					badServer.addAll(list.get(status));
				} else {
					goodServer = list.get(active);
				}
			}
			if (temp.equals(""))
				returnValue[1] = "No " + key + " details found in API response";
			else
				returnValue[1] = temp;
		}
		logger.debug("::::::::::::::::::::::::::::::::" + returnValue[1] + ":::::::::::::::::::::::::::::::");
		if (world.getTestEnvironment().toUpperCase().equals("QA3")
				&& (dbColumn.equals("SOASERVICE") || dbColumn.equals("SOASERVER"))) {
			updateHealthCheckPoint(dbColumn, "PASS");
		} else {
			if (!(boolean) returnValue[0] && !returnValue[1].toString().equals("")) {
				/*
				 * for (String s1 : returnValue[1].toString().split("\n")) { temp = temp +
				 * "<tr><td>" + (s1.split(":")[0].trim()) + "</td><td>" +
				 * (s1.split(":")[1].trim()) + "</td></tr>"; }
				 */
				sendEmail(toEmail, world.getTestEnvironment().toUpperCase() + " - " + key + " are not " + active,
						allFailedMsg + "Please find " + world.getTestEnvironment().toUpperCase() + " - " + key
								+ " details which are not " + active
								+ " <br><br><table border='1'><tr><td><b>STATUS</b></td><td><b>[List of " + key
								+ "]</b></td></tr>" + failedServers + "</table><br><br>"
								+ ((goodServer.size() > 0) ? "Please find following " + key + " which are " + active
										+ "<br><table border='1'><tr><td><b>STATUS</b></td><td><b>" + key
										+ "</b></td></tr>" + "<tr><td>RUNNING</td><td>"
										+ String.join("</td></tr><tr><td>RUNNING</td><td>", goodServer)
										+ "</td></tr></table>" : ""));
			} else if (!(boolean) returnValue[0]) {
				sendEmail(toEmail, world.getTestEnvironment().toUpperCase() + " - " + key + " are not " + active,
						allFailedMsg + key + " API is not returning any " + key + " details");
			}
			if (!(Boolean) returnValue[2]) {
				logger.debug("None of the " + key + " urls are " + active);
				// updateHealthCheckDB(dbColumn,returnValue[1].toString());
				updateHealthCheckPoint(dbColumn, "FAIL");
				updateHealthCheckPoint("E2E", "NOT READY");
			} else {
				// updateHealthCheckDB(dbColumn,"PASS");
				updateHealthCheckPoint(dbColumn, "PASS");
			}
		}

		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		for (String goodUrl : goodServer) {
			scenarioSteps.put(goodUrl, "PASSED");
		}
		for (String badUrl : badServer) {
			scenarioSteps.put(badUrl, "FAILED");
		}
		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);

		return returnValue;
	}

	public void sendEmail(String toEmail, String sub, String msg) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		datePrintInEmail = formatter.format(date).toString();
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

	public boolean doPostRequestWithKey(String endpoint, String xAPIKey) {

		logger.debug("Endpoint: " + endpoint);
		RestAssured.defaultParser = Parser.JSON;

		response = given().header("x-api-key", xAPIKey).post(endpoint).then().extract().response();

		logger.debug("Response:\n");
		statusFound = response.prettyPrint().contains("status");
		return statusFound;
	}

	public boolean doPostIngestRequestWithKey() {

		logger.debug("Endpoint: " + ApplicationsEndpointObject.coo.cooHealthCheck);
		RestAssured.defaultParser = Parser.JSON;

		try {
			response = given().get(ApplicationsEndpointObject.coo.cooHealthCheck).then().extract().response();
		} catch (Exception e) {
			e.printStackTrace();
//			sendEmail(getEmailToCommon, world.getTestEnvironment().toUpperCase() + " - Ingest API is not responding ",
//					"Ingest API is not responding. Something went wrong with Ingest API. Hence E2E wont be executed <br><br>URL : "
//							+ healthCheckIngestURL);
			// throw new RuntimeException(e.getMessage());
			return false;
		}
		logger.debug("without" + response);
//        }

		logger.info("Response:\n");
		statusFound = false;
//        if(world.getTestEnvironment().toUpperCase().equals("QA3")) {

		statusFound = response.prettyPrint().contains("\"ingestCheck\": {\n" + "            \"status\":");
//        }
//        else {
//        statusFound=response.prettyPrint().contains("status");
//        }

		logger.debug("status is: " + statusFound);

		if (!statusFound) {
			sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - Ingest API not returning any response ",
					"Ingest API not returning any response<br><b>So, E2E will not be executed </b><br><br>URL : "
							+ ApplicationsEndpointObject.coo.cooHealthCheck);

//            }
			logger.debug("Ingest is not RUNNING");
			updateHealthCheckPoint("INGESTSERVER", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
		}
		return statusFound;
	}

	public boolean getIngestStatus() {
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		boolean status = false;
//        List<String> rideStates = response.path("status");
//                List<String> rideStates = response.jsonPath().get("ingestCheck");
		logger.debug("hello");
		JSONArray jsonArray = new JSONArray("[" + response.getBody().prettyPrint() + "]");
		logger.debug("JSON array: " + jsonArray.length());
		String status_injest = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			logger.info("***********");
			String items = jsonArray.get(i).toString();
			logger.info("All items" + items);
			String status1 = items.substring(items.indexOf("ingestCheck"));
			logger.info("status 8" + status1);
//            String status3=status1.substring()
			status_injest = status1.substring(status1.indexOf("status"), status1.indexOf("}"));
			logger.debug("Injest status" + status_injest);
//            logger.info(jsonArray.getJSONObject(2));
		}

		if (status_injest != null) {
//            rideStates.replaceAll(String::toLowerCase);
			if (status_injest.contains("SUCCESS")) {
				logger.info("Ingest is RUNNING");

				status = true;
			} else {
				logger.error("Ingest is NOT RUNNING\n" + response.prettyPrint());
			}
		}
		logger.info("Status for Ingest: " + world.getTestEnvironment() + " is " + status);
//        if(world.getTestEnvironment().toUpperCase().equals("QA3") || world.getTestEnvironment().toUpperCase().equals("UAT") ) {
		if (!status) {
			sendEmail(getEmailToCommon, world.getTestEnvironment().toUpperCase() + " - Ingest is NOT RUNNING ",
					"Ingest is not RUNNING, so E2E will not be executed. Please find "
							+ world.getTestEnvironment().toUpperCase() + " ingest response as follows  <br><br>"
							+ response.prettyPrint().replaceAll(",", "<br>"));
			logger.debug("Ingest is not RUNNING");
			// updateHealthCheckDB("INGESTSERVER", "FAIL");
			updateHealthCheckPoint("INGESTSERVER", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
			scenarioSteps.put("Validate Ingest is RUNNING", "FAILED");

		} else {
			scenarioSteps.put("Validate Ingest is RUNNING", "PASSED");
			// updateHealthCheckDB("INGESTSERVER", "PASS");
			updateHealthCheckPoint("INGESTSERVER", "PASS");
		}

		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		return status;
	}

	public void getHealthCheckOrderImportURL() {
		logger.debug("======================getHealthCheckOrderImportURL=================================");
		logger.debug("Env: " + world.getTestEnvironment());
		logger.debug("Env: " + world.getTestEnvironment() + ", Retrieved Order Import URL from properties: "
				+ ApplicationsEndpointObject.hce2e.orderimport);
	}

	public String quickDeviceRequest() {
		String strBody = getJson("device.json");
		Map<String, String> headers = new HashMap<String, String>();

		if (world.getTestEnvironment().equalsIgnoreCase("UAT") || world.getTestEnvironment().equalsIgnoreCase("STAGE1")) {
			headers.put("x-api-key", "Lq785F5x2E3FDnVSCaWSS41wlWNPhkA0aF2NHyJX");
			headers.put("Content-Type", "application/json");
			response = given().filter(new CookieFilter()).body(strBody).log().all().headers(headers).when()
					.post("https://api.stage.cd.testondemand.com/devices/preregister").then().log().all().extract()
					.response();
		} else {
			headers.put("x-api-key", "RVDpxtITDvaljcNbsv01e16X6Liv2W3T9KSkwjcf");
			headers.put("Content-Type", "application/json");
			response = given().filter(new CookieFilter()).body(strBody).log().all().headers(headers).when()
					.post("https://api.qa.cd.testondemand.com/devices/preregister").then().log().all().extract()
					.response();
		}
		logger.debug("Request to got status code of " + response.statusCode());
		return response.jsonPath().get("activationCode");
	}

	public String quickFlexRequest() {
		RuntimeProperties r = new RuntimeProperties() ; 
		String flex = ApplicationsEndpointObject.hce2e.cybersource + "cc/flexKey";
		logger.info("flex url:\t" + flex);
		String strBody = getJson("cybersource_flex.json"); 
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-api-key", ApplicationsEndpointObject.hce2e.cybersourceAuth);
		headers.put("Content-Type", "application/json");
		headers.put("cybersource-merchant-id", "qentelli")  ;

		response = given().filter(new CookieFilter()).body(strBody).log().all().headers(headers).when().post(flex).then()
				.log().all().extract().response();
		logger.debug("Request to got status code of " + response.statusCode());
		logger.info(strBody) ;
		strBody = response.jsonPath().get("keyId") ; 
		r.writeProp("keyId", strBody) ; 
		logger.info(strBody) ;
		return strBody ; 
	}
	public String quickSetupAuthRequest() {
		RuntimeProperties r = new RuntimeProperties() ; 
		String flex = ApplicationsEndpointObject.hce2e.cybersourceInt + "cc/3DS/setupAuth"  ;
		logger.info("setupAuth url:\t" + flex);
		String strBody = getJson("cybersource_setup_auth.json"); 
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-api-key", ApplicationsEndpointObject.hce2e.cybersourceIntAuth);
		headers.put("Content-Type", "application/json");
		headers.put("cybersource-merchant-id", "318596")  ;

		response = given().filter(new CookieFilter()).body(strBody).log().all().headers(headers).when().post(flex).then()
				.log().all().extract().response();
		logger.debug("Request to got status code of " + response.statusCode());
		logger.info(strBody) ;
		strBody = response.jsonPath().get("consumerAuthenticationInformation").toString() ; 
		r.writeProp("accessToken", strBody) ; 
		logger.info(strBody) ;
		return strBody ; 
	}

	public String quickEnrollAuthRequest() {
		RuntimeProperties r = new RuntimeProperties() ; 
		String flex = ApplicationsEndpointObject.hce2e.cybersourceInt + "cc/3DS/enrollAuth"  ;
		logger.info("enrollAuth url:\t" + flex);
		String strBody = getJson("cybersource_enroll_auth.json"); 
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-api-key", ApplicationsEndpointObject.hce2e.cybersourceIntAuth);
		headers.put("Content-Type", "application/json");
		headers.put("cybersource-merchant-id", "318599")  ;

		response = given().filter(new CookieFilter()).body(strBody).log().all().headers(headers).when().post(flex).then()
				.log().all().extract().response();
		logger.debug("Request to got status code of " + response.statusCode());
		logger.info(strBody) ;
		strBody = response.jsonPath().get("consumerAuthenticationInformation").toString() ; 
		String s[] = strBody.split("accessToken") ;
		if (strBody.length()==0) throw new AppIssueException("auth token not found") ; 
		r.writeProp("accessToken", s[1]) ; 
		logger.info(strBody) ;
		return strBody ; 
	}
	public boolean doPostRequestForOrderImport() {
		logger.debug("OI Endpoint: " + ApplicationsEndpointObject.hce2e.orderimport);
		statusFound = false;
		Long abc = System.currentTimeMillis();
		String tempabc = String.valueOf(abc);
		tempabc = tempabc.substring(0, tempabc.length() - 3);
		String mstrOrderRefNumber = "STORE_" + tempabc;
		logger.debug("ORIG_SYS_DOCUMENT_REF: " + mstrOrderRefNumber);
		RestAssured.defaultParser = Parser.JSON;
		try {
			logger.debug("Try to get post response from below payload >>>>>>>>>>>>>>>>>>>>>");
			logger.info(
					"src/test/resources/body/OrderImport_PayLoad_" + world.getTestEnvironment().toUpperCase() + ".txt");
			Path content = Paths.get("src/test/resources/body/OrderImport_PayLoad_UAT.txt");
			byte[] f = null;
			int i = 0;
			while (f == null && i++ < 10) {
				BasePage.sleep(5);
				try {
					f = Files.readAllBytes(content);
				} catch (Exception e) {
					f = null;
					e.printStackTrace();
				}
			}
			String strBody = new String(f);
			strBody = strBody.replaceAll("<orderReferenceNumber>", mstrOrderRefNumber).replaceAll("<guid>", getStrGUID)
					.replaceAll("<customerNumber>", getCustomerNumber).replaceAll("<gncCustomerID>", getGncCustomerID)
					.replaceAll("<gncSponsorID>", getGncSponsorID).replaceAll("<email>", getCustomerEmailID);
			logger.debug("BODY of the payload : " + strBody);
			response = given().header("Content-Type", "text/xml").body(strBody).when()
					.post(ApplicationsEndpointObject.hce2e.orderimport).then().extract().response();

			int orderImportResponseCode = response.getStatusCode();
			logger.debug("Order Import Response status code:  " + orderImportResponseCode);
			if (orderImportResponseCode == 200) {
				statusFound = true;
			}
			if (orderImportResponseCode == 500) {
				statusFound = false;
			}
			if (orderImportResponseCode == 404) {
				statusFound = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - Order Import API is not responding ",
					"Order Import API is not responding. Something went wrong with Order Import API. Hence E2E wont be executed <br><br>URL : "
							+ ApplicationsEndpointObject.hce2e.orderimport + " \t Exception: "
							+ e.getLocalizedMessage());
			;
			return false;
		}
		logger.debug("Order Import Response: " + response.getStatusLine());
		logger.debug("Order Import response status is: " + statusFound);
		if (!statusFound) {
			/*
			 * sendEmail(getEmailToCommon,world.getTestEnvironment().toUpperCase() +
			 * " - Order Import API not returning any response ",
			 * "Order Import API not returning expected response. Hence E2E wont be executed. <br><br>URL : "
			 * + healthCheckOrderImportURL
			 * +" \t  Response details: "+response.prettyPrint().toString() );
			 */
			logger.debug("Order Import is not RUNNING");
			// updateHealthCheckDB("ORDERIMPORTSERVICE","FAIL");
		} else {
			logger.debug("Order Import API is RUNNING as expected");
			// updateHealthCheckDB("ORDERIMPORTSERVICE","PASS");
		}
		return statusFound;
	}

	public boolean getOrderImportResponseStatus() {
		String scenarioName = "Check Order Import API status";
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		statusFound = false;
		if (response.getStatusCode() == 200 && response.prettyPrint().contains("<status>S</status>")
				&& response.prettyPrint().contains("<message>Queued</message>")) {
			logger.debug("Order Import is working fine i.e., " + response.prettyPrint() + "\n with Status Code: "
					+ response.getStatusCode());
			statusFound = true;
			scenarioSteps.put("Order Import is working fine", "PASSED");
		} else {
			statusFound = false;
			logger.debug("Order Import is NOT working fine i.e., " + response.prettyPrint() + "\n with Status Code: "
					+ response.getStatusCode());
			scenarioSteps.put("Order Import is working fine", "FAILED");
		}
		if (!statusFound) {
			sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - Order Import API not returning any response ",
					"Order Import API not returning expected response <br><br>URL : "
							+ ApplicationsEndpointObject.hce2e.orderimport + " \t  Response details: "
							+ response.prettyPrint());
			logger.debug("Order Import is not RUNNING");
			// updateHealthCheckDB("ORDERIMPORTSERVICE","FAIL");
			updateHealthCheckPoint("ORDERIMPORTSERVICE", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
		} else {
			logger.debug("Order Import is RUNNING as expected");
			// updateHealthCheckDB("ORDERIMPORTSERVICE","PASS");
			updateHealthCheckPoint("ORDERIMPORTSERVICE", "PASS");
		}

		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		return statusFound;
	}

	public void getHealthCheckSabrixURL() {
		logger.debug("======================getHealthCheckSabrixURL=================================");
		logger.debug("Sabrix Env: " + world.getTestEnvironment());
		logger.debug(
				"Sabrix Env: " + world.getTestEnvironment() + ", Retrieved Sabrix URL from properties: " + hcEP.sabrix);
	}

	public boolean doPostRequestForSabrix() {
		logger.debug("Sabrix Endpoint: " + hcEP.sabrix);
		statusFound = false;

		RestAssured.defaultParser = Parser.JSON;
		try {
			Path content = Paths.get("src/test/resources/body/Sabrix_PayLoad.txt");
			String strBody = new String(Files.readAllBytes(content));
			response = given().header("Content-Type", "text/xml").body(strBody).when().post(hcEP.sabrix).then()
					.extract().response();

			int sabrixResponseCode = response.getStatusCode();
			logger.debug("Sabrix Response status code:  " + sabrixResponseCode);
			if (sabrixResponseCode == 200) {
				statusFound = true;
			}
			if (sabrixResponseCode == 500) {
				statusFound = false;
			}
			if (sabrixResponseCode == 404) {
				statusFound = false;
			}
		} catch (Exception e) {
			sendEmail(getEmailToCommon, world.getTestEnvironment().toUpperCase() + " - Sabrix API is not responding ",
					"Sabrix API is not responding. Something went wrong with Sabrix API. Hence E2E wont be executed <br><br>URL : "
							+ hcEP.sabrix + " <br><b> Exception: " + e.getLocalizedMessage() + "</b>");
			// updateHealthCheckDB("SABRIXSERVICE","FAIL");
			updateHealthCheckPoint("SABRIXSERVICE", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
			return false;
		}
		logger.debug("Sabrix Response status line: " + response.getStatusLine());
		logger.debug("Sabrix response is: " + statusFound);
		if (!statusFound) {
			sendEmail(getEmailToCommon, world.getTestEnvironment().toUpperCase() + " - Sabrix API is not responding",
					"Sabrix API not returning expected response. Hence E2E wont be executed <br><br>URL : "
							+ hcEP.sabrix + " <br><b> Response details: " + response.getStatusLine() + "</b>");
			logger.debug("Sabrix API is not RUNNING");
			// updateHealthCheckDB("SABRIXSERVICE","FAIL");
			updateHealthCheckPoint("SABRIXSERVICE", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
		} else {
			logger.debug("Sabrix API is RUNNING as expected");
			// updateHealthCheckDB("SABRIXSERVICE","PASS");
			updateHealthCheckPoint("SABRIXSERVICE", "PASS");
		}
		logger.info("Sabrix response: \t" + response.toString());
		return statusFound;
	}

	public boolean getSabrixResponseStatus() {
		String scenarioName = "Check Sabrix Service API status";
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		statusFound = false;
		String statusCheck = response.prettyPrint();
		statusCheck = statusCheck.substring(statusCheck.indexOf("<ns:REQUEST_STATUS>"),
				statusCheck.indexOf("</ns:REQUEST_STATUS>")) + "</ns:REQUEST_STATUS>";
		logger.info("Status for Sabrix after response: " + statusCheck);
		if (response.getStatusCode() == 200 && statusCheck.contains("<ns:IS_SUCCESS>true</ns:IS_SUCCESS>")
				&& statusCheck.contains("<ns:IS_PARTIAL_SUCCESS>true</ns:IS_PARTIAL_SUCCESS>")) {
			logger.debug("Sabrix tax calculation is working fine i.e., with Status Code: " + response.getStatusCode());
			statusFound = true;
			scenarioSteps.put("Sabrix tax calculation is working fine", "PASSED");
		} else {
			scenarioSteps.put("Sabrix tax calculation is working fine", "FAILED");
			statusFound = false;
			logger.debug("Sabrix tax calculation is NOT working fine i.e., " + statusCheck + "\n with Status Code: "
					+ response.getStatusCode());
		}
		if (!statusFound) {
			sendEmail(getEmailToCommon, world.getTestEnvironment().toUpperCase() + " - Sabrix API is not responding",
					"Sabrix API not returning expected response <br><br>URL : " + hcEP.sabrix
							+ "<br><b>  Response details contains : " + statusCheck + "</b>");
			logger.debug("Sabrix API is not RUNNING as expected");
			// updateHealthCheckDB("SABRIXSERVICE","FAIL");
			updateHealthCheckPoint("SABRIXSERVICE", "FAIL");
			updateHealthCheckPoint("E2E", "NOT READY");
		} else {
			logger.debug("Sabrix API is RUNNING as expected");
			// updateHealthCheckDB("SABRIXSERVICE","PASS");
			updateHealthCheckPoint("SABRIXSERVICE", "PASS");
		}

		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		return statusFound;
	}

	public void updateHealthCheckPoint(String columnName, String value) {
		Point point = null;
		String rd = null;
		if (System.getProperty("RUNID") == null) {
			rd = RuntimeSingleton.getInstance().runid;
		} else {
			rd = System.getProperty("RUNID");
		}
		if (firstEntrytoE2E == 0) {
			point = Point.measurement("healthcheck").time(RuntimeSingleton.getInstance().getId(), TimeUnit.MILLISECONDS)
					.addField("runid", String.valueOf(RuntimeSingleton.getInstance().getId())).addField("JRUNID", rd)
					.addField("E2E", "READY").build();

			ResultSender.send(point);
		}
		firstEntrytoE2E = firstEntrytoE2E + 1;

		logger.info("E2E Entered count :::::::::::  " + firstEntrytoE2E);
		logger.info("RunID: " + System.getProperty("RUNID") + " with Instance Start >>>>>> "
				+ RuntimeSingleton.getInstance().getId());
		point = Point.measurement("healthcheck").time(RuntimeSingleton.getInstance().getId(), TimeUnit.MILLISECONDS)
				.addField("runid", String.valueOf(RuntimeSingleton.getInstance().getId())).addField("JRUNID", rd)
				.addField(columnName, value).build();
		ResultSender.send(point);
	}

	String getJson(String file) {
		try {

			return new String(Files.readAllBytes(Paths.get("src/test/resources/body/" + file)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
