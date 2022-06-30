package com.qentelli.automation.utilities.healthcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.common.World;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;
import com.qentelli.automation.utilities.api.RestRequest;

public class Merlin {

	private World world = null;
	// private static final Logger logger =
	// LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Logger logger;
	private RestRequest restRequest;
	private RESTAPIService restapiService;
	ResourceBundle healthCheck = ResourceBundle.getBundle("com.qentelli.automation.testdata.HealthCheck");
	private List<String> healthCheckMerlinURLS;
	private List<String> healthCheckMerlinResponses;

	private List<String> badMerlinURLS = null;
	private List<String> goodMerlinURLS = null;

	// private String getHealthCheckMerlinURLs;
	private String getHealthCheckMerlinResponse;

	private String getEmailToCommon;
	private String getEmailToMerlin;

	public Merlin(World world) {
		// super(world, world.driver);
		this.world = world;
		restRequest = new RestRequest(world);
		restapiService = new RESTAPIService(world);
		InitElements();
	}

	public void InitElements() {
		logger = LogManager.getLogger(Merlin.class);
//		getHealthCheckMerlinURLs = healthCheck
//				.getString(world.getTestEnvironment().toLowerCase() + ".merlin.healthcheck.urls");
		getHealthCheckMerlinResponse = healthCheck
				.getString(world.getTestEnvironment().toLowerCase() + ".merlin.healthcheck.response");
		getEmailToCommon = healthCheck.getString("email.group.common");
		getEmailToMerlin = healthCheck.getString("email.group.merlin");
	}

	public void getMerlinHealthURLS() {
		// String tempURLS = getHealthCheckMerlinURLs;
		healthCheckMerlinURLS = Arrays.asList(ApplicationsEndpointObject.hce2e.merlin.split(","));
		logger.debug("Retrieved Merlin Health Check URL's from properties: ");
		healthCheckMerlinURLS.forEach(url -> logger.debug(url));
	}

	public void sendHealthCheckMerlinRequests() {
		healthCheckMerlinResponses = new ArrayList<>();
		for (String url : healthCheckMerlinURLS) {
			restRequest.tryGetRequest(url);
			String response = restRequest.getResponseAsStringNew();
			logger.debug("This is the response: " + response);
			healthCheckMerlinResponses.add(response);
		}

	}

	public Boolean checkMerlinHealthRequests() {
		Boolean pass = true;
		badMerlinURLS = new ArrayList<>();
		goodMerlinURLS = new ArrayList<>();
		logger.debug("Checking URL Status, if Failed");
		int index = 0;
		for (String response : healthCheckMerlinResponses) {
			logger.debug("Checking this response: " + response);
			if (response.contains("Ncat: Connected to ")) {
				goodMerlinURLS.add(healthCheckMerlinURLS.get(index));
			} else if (!response.contains(getHealthCheckMerlinResponse)) {
				pass = false;
				badMerlinURLS.add(healthCheckMerlinURLS.get(index));
			} else
				goodMerlinURLS.add(healthCheckMerlinURLS.get(index));
			index++;
		}

		if (goodMerlinURLS.size() == 0) {
			logger.debug("None of the MERLIN urls are RUNNING");
			restapiService.sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - Merlin servers are not RUNNING",
					"None of the MERLIN urls are RUNNING, so E2E will not be executed.<br>Please find "
							+ world.getTestEnvironment().toUpperCase() + " - Merlin URLs which are not RUNNING <br><br>"
							+ "<b>URLs</b><br>" + String.join("<br>", badMerlinURLS)
							+ "<br><br>Please find Merlin URLs below which are RUNNING normal<br>"
							+ String.join("<br>", goodMerlinURLS));
			restapiService.updateHealthCheckPoint("MERLINSERVER", "FAIL");
			restapiService.updateHealthCheckPoint("E2E", "NOT READY");
			// restapiService.updateHealthCheckDB("MERLINSERVER",String.join("|",
			// badMerlinURLS));
		} else {
			if (!pass) {
				restapiService.sendEmail(getEmailToMerlin,
						world.getTestEnvironment().toUpperCase() + " - Merlin servers are not RUNNING",
						"Please find " + world.getTestEnvironment().toUpperCase()
								+ " - Merlin URLs which are not RUNNING <br><br>" + "<b>URLs</b><br>"
								+ String.join("<br>", badMerlinURLS)
								+ "<br><br>Please find Merlin URLs below which are RUNNING normal<br>"
								+ String.join("<br>", goodMerlinURLS));
			}
			restapiService.updateHealthCheckPoint("MERLINSERVER", "PASS");
			// restapiService.updateHealthCheckDB("MERLINSERVER","PASS");
		}
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		for (String goodUrl : goodMerlinURLS) {
			scenarioSteps.put(goodUrl, "PASSED");
		}
		for (String badUrl : badMerlinURLS) {
			scenarioSteps.put(badUrl, "FAILED");
		}
		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		if (scenarioSteps.containsValue("FAILED"))
			pass = false;
		return pass;
	}

	public String getBadMerlinURLS() {
		String returnURLString = String.join(",", badMerlinURLS);
		return returnURLString;
	}
}
