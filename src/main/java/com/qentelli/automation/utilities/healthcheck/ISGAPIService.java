package com.qentelli.automation.utilities.healthcheck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.qentelli.automation.common.World;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;
import com.qentelli.automation.utilities.api.RestRequest;

import io.restassured.response.Response;

public class ISGAPIService {
	Logger logger = LogManager.getLogger(ISGAPIService.class);

	private World world = null;
	// private static final Logger logger =
	// LogManager.getLogger(MethodHandles.lookup().lookupClass());
	ResourceBundle healthCheck = ResourceBundle.getBundle("com.qentelli.automation.testdata.HealthCheck");
	private RESTAPIService restapiService;

	private RestRequest restRequest;

	private Response response;

//	private String iSGUrl;
//	private String iSGAuthorization;

//	private String getOrderImportIsgUrl;
//	private String getOrderImportIsgAuthorization;
//	private String getcardconnectUrl;
//	private String getcardconnectAuthorization;

	private String getEmailToCommon = healthCheck.getString("email.group.common");

	public ISGAPIService(World world) {
		// super(world, world.driver);
		this.world = world;
		restRequest = new RestRequest(world);
		restapiService = new RESTAPIService(world);
		InitElements();
	}

	public void InitElements() {

//		getOrderImportIsgUrl = healthCheck
//				.getString(world.getTestEnvironment().toLowerCase() + ".order.import.isg.url");
//		getOrderImportIsgAuthorization = healthCheck
//				.getString(world.getTestEnvironment().toLowerCase() + ".order.import.isg.authorization");
//		getcardconnectUrl = healthCheck.getString("cardconnect.url");
//		getcardconnectAuthorization = healthCheck.getString("cardconnect.authorization");
	}

	public boolean doPostRequestForCustomerCreate(String testCase, String... bodyReplaceStrings) throws IOException {
		Boolean reTry = true;
		int i = 0;
		String msg = null;

		String bodyTextFileName = "";
		switch (testCase) {
		case "customer_create_update_ISG":
			bodyTextFileName = "customer_create_update_ISG_body";
			break;
		case "order_import_ISG":
			bodyTextFileName = "order_import_ISG_body";
			break;

		case "cardconnect_ISG":
			bodyTextFileName = "cardconnect_payload";
			break;
		}
		Path content = Paths.get("src/test/resources/body/" + bodyTextFileName + ".txt");
		String strBody = new String(Files.readAllBytes(content));
		StringBuilder s = new StringBuilder();
		;
		for (String rplString : bodyReplaceStrings) {
			strBody = strBody.replace(rplString.split("=")[0], rplString.split("=")[1]);
			logger.info("Replace : " + rplString);
			s.append(strBody);
		}
		logger.info("here <->");
		logger.info(s);
		while (reTry && i++ < 10) {
			logger.info("ISG reTry " + i + " of 10");
			try {

				if (testCase.equals("cardconnect_ISG")) {
					response = restRequest.doPostRequestWithAuth(ApplicationsEndpointObject.hce2e.cardconnect, strBody,
							ApplicationsEndpointObject.hce2e.cardconnectauth);
					if (response.getStatusCode() == 200)
						reTry = false;

				} else {
					response = restRequest.doPostRequestWithAuth(ApplicationsEndpointObject.hce2e.customerCreateUpdate,
							strBody, ApplicationsEndpointObject.hce2e.customerCreateUpdateAuth);
					if (response.getStatusCode() == 200)
						reTry = false;
				}

			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
				BasePage.sleep(new Random().nextInt(10 - 1 + 1) + 1);
			}
		}
		if (i >= 10) {
			restapiService.sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
					"Exception occurred while getting response for " + world.getTestEnvironment().toUpperCase()
							+ " - ISG API - " + testCase + "  <br> <br>" + msg);
			return false;
		}
		return true;
	}

	public boolean checkISGResponseStatus(String testCase) {
		String dbColName = "";

		String scenarioName = "";
		HashMap<String, String> scenarioSteps = new HashMap<String, String>();
		try {
			switch (testCase) {
			case "customer_create_update_ISG":
				scenarioName = "Check Customer Create Update ISG";
				dbColName = "ISGCUSTOMER";
				break;
			case "order_import_ISG":
				scenarioName = "Check Order Import ISG";
				dbColName = "ISGORDERIMPORT";
				break;
			case "cardconnect_ISG":
				scenarioName = "Check the card connnect serivce";
				dbColName = "ISGCARDCONNECT";
				break;
			}
			if (response.getStatusCode() != 200) {
				restapiService.sendEmail(getEmailToCommon,
						world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
						"Status code is not 200 for " + world.getTestEnvironment().toUpperCase() + " - ISG API - "
								+ testCase + ". So E2E will not be executed  <br> <br>" + "Actual status code is : "
								+ response.getStatusCode() + "<br>Actual response is : <br>" + response.prettyPrint());
				restapiService.updateHealthCheckPoint(dbColName, "FAIL");
				restapiService.updateHealthCheckPoint("E2E", "NOT READY");
				// restapiService.updateHealthCheckDB(dbColName,"FAIL");
				scenarioSteps.put("Status code is 200", "FAILED");
				RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
				return false;
			}
			JSONObject jsonObject = new JSONObject(response.prettyPrint());
			scenarioSteps.put("Status code is 200", "PASSED");

			System.out.println(":Response" + jsonObject);
			if (!dbColName.equals("ISGCARDCONNECT")) {
				if (!jsonObject.has("OutputParameters")) {
					restapiService.sendEmail(getEmailToCommon,
							world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
							"Failed to find key 'OutputParameters' in response for "
									+ world.getTestEnvironment().toUpperCase() + " - ISG API - " + testCase
									+ ". So E2E will not be executed  <br> <br>" + "Actual response is : <br>"
									+ response.prettyPrint());
					// restapiService.updateHealthCheckDB(dbColName,"FAIL");
					restapiService.updateHealthCheckPoint(dbColName, "FAIL");
					restapiService.updateHealthCheckPoint("E2E", "NOT READY");
					scenarioSteps.put("Find key 'OutputParameters' in response", "FAILED");
					RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
					return false;
				}
				scenarioSteps.put("Find key 'OutputParameters' in response", "PASSED");
				jsonObject = (JSONObject) jsonObject.get("OutputParameters");
				if (!jsonObject.has("P_ERR_MESSAGE")) {
					restapiService.sendEmail(getEmailToCommon,
							world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
							"Failed to find key 'OutputParameters -> P_ERR_MESSAGE' in response for "
									+ world.getTestEnvironment().toUpperCase() + " - ISG API - " + testCase
									+ ". So E2E will not be executed  <br> <br>" + "Actual response is : <br>"
									+ response.prettyPrint());
					// restapiService.updateHealthCheckDB(dbColName,"FAIL");
					restapiService.updateHealthCheckPoint(dbColName, "FAIL");
					restapiService.updateHealthCheckPoint("E2E", "NOT READY");
					scenarioSteps.put("Find key 'OutputParameters -> P_ERR_MESSAGE' in response", "FAILED");
					RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
					return false;
				}
				scenarioSteps.put("Find key 'OutputParameters -> P_ERR_MESSAGE' in response", "PASSED");
				if (!jsonObject.isNull("P_ERR_MESSAGE")) {
					restapiService.sendEmail(getEmailToCommon,
							world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
							"Error message in key 'OutputParameters -> P_ERR_MESSAGE' is not null for "
									+ world.getTestEnvironment().toUpperCase() + " - ISG API - " + testCase
									+ ". So E2E will not be executed  <br> <br>" + "Actual response is : <br>"
									+ response.prettyPrint());
					// restapiService.updateHealthCheckDB(dbColName,"FAIL");
					restapiService.updateHealthCheckPoint(dbColName, "FAIL");
					restapiService.updateHealthCheckPoint("E2E", "NOT READY");
					scenarioSteps.put("'OutputParameters -> P_ERR_MESSAGE' is null", "FAILED");
					RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
					return false;
				}
				scenarioSteps.put("'OutputParameters -> P_ERR_MESSAGE' is null", "PASSED");
			}

			if (dbColName.equals("ISGCARDCONNECT")) {

				String api_response = jsonObject.get("resptext").toString();
				System.out.println("API Response : " + api_response);

				if (!api_response.equals("Approval")) {
					{
						restapiService.sendEmail(getEmailToCommon,
								world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
								"Expected response text is not approval in the card connect response for "
										+ world.getTestEnvironment().toUpperCase() + " - ISG API - " + testCase
										+ ". So E2E will not be executed  <br> <br>" + "Actual response is : <br>"
										+ response.prettyPrint());
						// restapiService.updateHealthCheckDB(dbColName,"FAIL");
						restapiService.updateHealthCheckPoint(dbColName, "FAIL");
						restapiService.updateHealthCheckPoint("E2E", "NOT READY");

						scenarioSteps.put("Card Connect response should be Approval", "FAILED");
						RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(),
								scenarioSteps);
						return false;

					}
				}
				scenarioSteps.put("Card Connect response should be Approval", "PASSED");
			}

		} catch (Exception e) {
			restapiService.sendEmail(getEmailToCommon,
					world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
					"Exception occurred while getting response for " + world.getTestEnvironment().toUpperCase()
							+ " - ISG API - " + testCase + "  <br> <br>" + "<br>Actual response is : <br>"
							+ response.prettyPrint() + "<br> Exception is :" + e.getMessage());
			// restapiService.updateHealthCheckDB(dbColName,"FAIL");
			restapiService.updateHealthCheckPoint(dbColName, "FAIL");
			restapiService.updateHealthCheckPoint("E2E", "NOT READY");
			scenarioSteps.put("No exception occurred while fetching response", "FAILED");

			RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
			return false;
		}
		restapiService.updateHealthCheckPoint(dbColName, "PASS");
		// restapiService.updateHealthCheckDB(dbColName,"PASS");
		scenarioSteps.put("No exception occurred while fetching response", "PASSED");

		RuntimeSingleton.getInstance().healthCheckSteps.put(Thread.currentThread().getId(), scenarioSteps);
		return true;
	}

	void sendHCEmail(String testCase) {
		restapiService.sendEmail(getEmailToCommon,
				world.getTestEnvironment().toUpperCase() + " - ISG API for " + testCase + " failed",
				"Exception occurred while getting response for " + world.getTestEnvironment().toUpperCase()
						+ " - ISG API - " + testCase + "  <br> <br>" + "<br>Actual response is : <br>"
						+ response.prettyPrint() + "<br> Exception is :");
		;
		// restapiService.updateHealthCheckDB(dbColName,"FAIL");

	}
}
