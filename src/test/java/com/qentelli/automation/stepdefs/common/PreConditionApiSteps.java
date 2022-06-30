package com.qentelli.automation.stepdefs.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.utilities.CommonTestDataObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import com.qentelli.automation.common.World;
import com.qentelli.automation.managers.StepExecutive;
import com.qentelli.automation.stepdefs.common.application.HomePage;
import com.qentelli.automation.services.OnlineAPIServices;
import com.qentelli.automation.testdatafactory.api.OIMService;
import com.qentelli.automation.testdatafactory.api.TBBOrder;
import com.qentelli.automation.testdatafactory.data.User;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.TDMUser;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PreConditionApiSteps {
	static Logger logger = LogManager.getLogger(PreConditionApiSteps.class);
	ResourceBundle addressLocale;
	TDMUser tdm;

	private World world;
	private HomePage tbbHomepage;
	private String sponsorRepId;
	private GlobalNavigationPageStepDefs globalNav;
	public CommonTestDataObject commonTestData;

	public PreConditionApiSteps(World world) {
		this.world = world;
		tbbHomepage = new HomePage(world);
		globalNav = new GlobalNavigationPageStepDefs(world);
		addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		commonTestData = new CommonTestDataObject();
	}

	@Given("I have {string} user taken from TDM")
	public void iHaveUserTakenFromTDM(String TDMBucket) {
		RuntimeProperties props = new RuntimeProperties();
		try {
			new OIMService(world).filterCollectionForValidCustomerType(TDMBucket);
		} catch (Exception e) {
			logger.info("Error of filtering");
		}
		tdm = new TDMUser(TDMBucket, world.getLocale());
		logger.info("The thread Id of this test is: " + tdm.getThreadID() + "|"
				+ (tdm.getSize() > tdm.getThreadID() && tdm.getEmail() != null));
		// removed pull from TDM because it was returning blank data causing healthcheck
		// to fail

		if (tdm.getEmail() != null) {
			logger.info("user taken from TDM: " + tdm.getEmail());
			props.writeProp("TDM_EMAIL", tdm.getEmail());
			props.writeProp("TDM_PWD", tdm.getPwd());

			world.setEmail(tdm.getEmail());
			world.setPassword(tdm.getPwd());

			Document doc = tdm.getRequiredDoc();
			world.setCustomerDetails("Email", tdm.getEmail());
			world.setExistingCoachDetails("Password", tdm.getPwd());
			world.setCustomerDetails("FirstName", doc.getString("firstname"));
			world.setCustomerDetails("LastName", doc.getString("lastname"));
			world.setCustomerDetails("Phone", doc.getString("phonenumber"));
			world.setCustomerDetails("coachID", doc.getString("coachID"));
			world.setCustomerDetails("Gender", doc.getString("Gender"));
			world.setCustomerDetails("userID", doc.getString("coachID"));
			world.setCustomerDetails("DOB", doc.getString("DOB"));
			world.setCustomerDetails("OriginalPcDate", doc.getString("originalPcDate"));
			world.setCustomerDetails("MostRecentPcDate", doc.getString("mostRecentPcDate"));
			world.setCustomerDetails("MostRecentCoachDate", doc.getString("mostRecentCoachDate"));
			world.setCustomerDetails("OriginalCoachDate", doc.getString("originalCoachDate"));
			world.setCustomerDetails("joinDate", doc.getString("joindate"));
			world.setCustomerDetails("CreatedFrom", "TDM");
//
//			String gncID = doc.getString("gncCoachID");
//			if (gncID == null || Ints.tryParse(gncID) == null) {
//				
//			} else {
//				new RuntimeProperties().writeProp("GNC_ID_FOUND", "true");
//			}
//
		} else {
			PreConditionApiSteps apiSteps = new PreConditionApiSteps(world);
			logger.info("selecting tdm api creation for " + TDMBucket);

			switch (TDMBucket) {
				case "COACH_WITH_SPONSOR":
					apiSteps.iCreateACoachWithSponsorAsUsingAPI("", world.factoryConfig.getDefaultSponsor());
					break;
				case "COACH_NO_SPONSOR":
					iCreateACoachWithSponsorAsUsingAPI("", "");
					break;
				case "CLUBCOACH_USER_SPONSOR_false":
					apiSteps.createAUserWithSponsorCoachAsUsingAPI("clubCoach", "false", "");
					break;
				case "FREE_USER_SPONSOR_false":
					apiSteps.createAUserWithSponsorCoachAsUsingAPI("free", "false", "");
					break;
				case "CLUB_USER_SPONSOR_false":
					apiSteps.createAUserWithSponsorCoachAsUsingAPI("club", "false", "");
					break;
				case "CUSTOMER_NO_SPONSOR_WPRODUCT":
					apiSteps.iCreateACustomerWithoutSponsorWithPurchage("A", "shakeology");
					break;
				case "COACH_NO_SPONSOR_WPRODUCT":
					apiSteps.iCreateACoachWithoutSponsorWithPurchaseUsingAPI("A", "shakeology");
					break;
				case "PREFERRED_CUSTOMER_NO_SPONSOR":
					createPCWithOutSponsor();
					break;
				case "PREFERRED_CUSTOMER_WITH_SPONSOR":
					createPCWithSponsor();
					break;
				case "CLUB_PC_WITH_SPONSOR":
					createClubPCWithSponsor();
					break;
				case "CLUB_PC_NO_SPONSOR":
					createClubPCWithOutSponsor();
					break;
			}
		}
//		world.setEmail(world.getEmail());
//		world.setPassword("Test1234");
//		world.setCustomerDetails("Email", world.getEmail());
//		world.setExistingCoachDetails("Password", world.getPassword());
//		world.setCustomerDetails("FirstName", world.factoryData.getUser().getFirstName());
//		world.setCustomerDetails("LastName", world.factoryData.getUser().getLastName());
//		world.setCustomerDetails("Phone", world.factoryData.getUser().getPhoneNumber());
//		world.setCustomerDetails("coachID", world.factoryData.getUser().getSponsorID());
//		world.setCustomerDetails("userID", world.factoryData.getUser().getGncID());
//		world.setCustomerDetails("companyName", world.factoryData.getUser().getCompanyName());
//		world.setCustomerDetails("DOB", world.factoryData.getUser().getDob());
//		world.setCustomerDetails("Gender", world.factoryData.getUser().getGender());
//		world.setCustomerDetails("OriginalPcDate", world.factoryData.getUser().getOriginalPcDate());
//		world.setCustomerDetails("MostRecentPcDate", world.factoryData.getUser().getMostRecentPcDate());
//		world.setCustomerDetails("MostRecentCoachDate", world.factoryData.getUser().getMostRecentCoachdate());
//		world.setCustomerDetails("OriginalCoachDate", world.factoryData.getUser().getOriginalCoachDate());
//		world.setCustomerDetails("joinDate", world.factoryData.getUser().getJoinDate());
//
//		if (TDMBucket.contains("COACH"))
//			world.setIsCoachUser(true);
		tdm.removeDocument(world.getEmail());
	}

	@Then("I create a coach {string} with coach {string} referral using API")
	public void iCreateACoachWithCoachReferralUsingAPI(String coachNickname, String sponsorNickname) {

		TDMUser tdm = new TDMUser("COACH_WREFERAL", world.getLocale());
		OIMService oimUser = new OIMService(world);
		String sponsorID = world.getCustomerDetails().get("Coach_" + sponsorNickname + "_Id");
		oimUser.createCoachOIMUserWithSponsor(sponsorID);
		HashMap<String, String> in = new HashMap<String, String>();
		
		in.put("email", world.factoryData.getUser().getEmail());
		world.setEmail(world.factoryData.getUser().getEmail());
		world.setCustomerDetails("Email", world.factoryData.getUser().getEmail());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_Email", world.factoryData.getUser().getEmail());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_coachID",
				world.factoryData.getUser().getGncCoachID());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_Password", "Test1234");
		in.put("password", "Test1234");
		world.getCustomerDetails().put("Coach_" + coachNickname + "_Id", world.factoryData.getUser().getGncID());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_sponsorId", world.factoryData.getUser().getGncID());
		in.put("coachID", world.factoryData.getUser().getGncID());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_lastName",
				world.factoryData.getUser().getLastName());
		in.put("lastname", world.factoryData.getUser().getLastName());

		world.getCustomerDetails().put("Coach_" + coachNickname + "_firstName",
				world.factoryData.getUser().getFirstName());

		in.put("first", world.factoryData.getUser().getFirstName());

		world.getCustomerDetails().put("Coach_" + coachNickname + "_joinDate",
				world.factoryData.getUser().getJoinDate());

		in.put("joindate", world.factoryData.getUser().getJoinDate());

		world.getCustomerDetails().put("Coach_" + coachNickname + "_phoneNumber",
				world.factoryData.getUser().getPhoneNumber());

		in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
		in.put("coachnickname", coachNickname);
		in.put("sponsornickname", sponsorNickname);
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Id"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Email"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Password"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_lastName"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_firstName"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_phoneNumber"));

		tdm.insertDoc(in);

	}

	@When("I create a coach {string} using API")
	public void iCreateACoachUsingAPI(String coachNickname) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				OIMService oimUser = new OIMService(world);
				oimUser.createCoachOIMUser();
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("Email", world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_sponsorId",
						world.factoryData.getUser().getSponsorID());
				logger.info("CoachID: " + world.getCustomerDetails().get("Coach_" + coachNickname + "_Id"));
				logger.info("CoachEmail: " + world.getCustomerDetails().get("Coach_" + coachNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_phoneNumber"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_sponsorId"));
			}
		}.run();
	}

	@Then("I create a coach {string} without sponsor using API")
	public void iCreateACoachWithSponsorAsUsingAPI(String coachNickname) {
		// TODO: update these calls to use a new gherkin step for coach WITH sponsor
		// where appropriate, pass-in the factory default sponsor
		iCreateACoachWithSponsorAsUsingAPI(coachNickname, "");
	}

	@Then("I create a coach {string} with default sponsor using API")
	public void iCreateACoachWithDefaultSponsorAsUsingAPI(String coachNickname) {
		// TODO: update these calls to use a new gherkin step for coach WITH sponsor
		// where appropriate, pass-in the factory default sponsor
		iCreateACoachWithSponsorAsUsingAPI(coachNickname, world.factoryConfig.getDefaultSponsor());
	}

	public void iCreateACoachWithSponsorAsUsingAPI(String coachNickname, String sponsorID) {

		tdm = new TDMUser("COACH_WITH_SPONSOR", world.getLocale());
		HashMap<String, String> in = new HashMap<String, String>();

		OIMService oimUser = new OIMService(world);
		logger.info("incoming sponsor: " + sponsorID);
		oimUser.createCoachOIMUserWithSponsor(sponsorID);
		String username = world.factoryData.getUser().getEmail();
		String coachId = world.factoryData.getUser().getGncID();
		String password = "Test1234";
		world.getCustomerDetails().put("Coach_" + coachNickname + "_Email", world.factoryData.getUser().getEmail());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_Password", "Test1234");
		world.getCustomerDetails().put("Coach_" + coachNickname + "_Id", world.factoryData.getUser().getGncID());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_lastName",
				world.factoryData.getUser().getLastName());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_firstName",
				world.factoryData.getUser().getFirstName());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_joinDate",
				world.factoryData.getUser().getJoinDate());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_phoneNumber",
				world.factoryData.getUser().getPhoneNumber());
		world.setCustomerDetails("GUID", world.factoryData.getUser().getGuid());
		world.getCustomerDetails().put("Coach_" + coachNickname + "_coachID",
				world.factoryData.getUser().getGncCoachID());

		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Id"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Email"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Password"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_lastName"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_firstName"));
		logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_phoneNumber"));
		world.getCustomerDetails().put("FirstName", world.factoryData.getUser().getFirstName());
		world.getCustomerDetails().put("LastName", world.factoryData.getUser().getLastName());
		world.getCustomerDetails().put("Phone", world.factoryData.getUser().getPhoneNumber());
		world.getCustomerDetails().put("Address1", addressLocale.getString("ADDRESS"));
		world.getCustomerDetails().put("City", addressLocale.getString("CITY"));
		world.getCustomerDetails().put("State", addressLocale.getString("STATE"));
		world.getCustomerDetails().put("Zip", addressLocale.getString("ZIP"));
		world.getCustomerDetails().put("Country", addressLocale.getString("COUNTRY"));
		world.getCustomerDetails().put("DOB", "1990/04/16");
		world.getCustomerDetails().put("Gender", "MALE");
		in.put("email", world.factoryData.getUser().getEmail());
		in.put("password", "Test1234");
		in.put("coachID", world.factoryData.getUser().getGncID());
		in.put("lastname", world.factoryData.getUser().getLastName());
		in.put("firstname", world.factoryData.getUser().getFirstName());
		in.put("joindate", world.factoryData.getUser().getJoinDate());
		in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
		in.put("GUID", world.factoryData.getUser().getGuid());

		in.put("Gender", "MALE");
		in.put("Address1", world.factoryData.getUser().getGuid());
		in.put("DOB", "1990/04/16");
		in.put("Country", addressLocale.getString("COUNTRY"));
		in.put("Zip", addressLocale.getString("ZIP"));
		in.put("State", addressLocale.getString("STATE"));
		in.put("Address1", world.factoryData.getUser().getGuid());

		world.getCustomerDetails().put("fname", world.factoryData.getUser().getFirstName());
		world.getCustomerDetails().put("lname", world.factoryData.getUser().getLastName());
		world.getCustomerDetails().put("coachID", world.factoryData.getUser().getGncID());
		world.getCustomerDetails().put("CreatedFrom", "API");
		world.getExistingCoachDetails().put("Email",
				world.getCustomerDetails().get("Coach_" + coachNickname + "_Email"));
		world.setIsCoachUser(true);
		world.setPassword(world.tbbTestDataObject.password);
		tdm.insertDoc(in);

	}

	@When("I create a customer {string} withoutSponsor with {string} purchage")
	public void iCreateACustomerWithoutSponsorWithPurchage(String coachNickname, String productType) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				TDMUser tdm = new TDMUser("CUSTOMER_NO_SPONSOR_WPRODUCT", world.getLocale());

				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithSponsor("");
				// TBBOrder tbbOrder = new TBBOrder(world);
				// String orderNumber =
				// tbbOrder.purchaseGivenProductWithCurrentUser(productType);
				// world.factoryData.getUser().setOrderNumber(orderNumber);
				String username = world.factoryData.getUser().getEmail();
				String coachId = world.factoryData.getUser().getGncID();
				String password = "Test1234";
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Id"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_phoneNumber"));
				oimUser.storeCustomerData();
				HashMap<String, String> in = new HashMap<String, String>();
				in.put("email", world.factoryData.getUser().getEmail());
				in.put("password", "Test1234");
				in.put("coachID", world.factoryData.getUser().getGncID());
				in.put("lastname", world.factoryData.getUser().getLastName());
				in.put("firstname", world.factoryData.getUser().getFirstName());
				in.put("joindate", world.factoryData.getUser().getJoinDate());
				in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
				// in.put("store", orderNumber);

				tdm.insertDoc(in);
			}
		}.run();
	}

	@Then("I create a coach without sponsor using TDM")
	public void iCreateACoachWithOutCoachUsingTDM() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				List<HashMap> users = OnlineAPIServices.getCoachWithoutSponsorFromTDM(world.getLocale(),
						world.getLocale());
				world.getCustomerDetails().put("Coach_WithoutSponsor_Email", users.get(0).get("Email").toString());
				world.getCustomerDetails().put("Coach_WithoutSponsor_Password",
						users.get(0).get("Password").toString());
				logger.info(world.getCustomerDetails().get("Coach_WithoutSponsor_Email"));
				logger.info(world.getCustomerDetails().get("Coach_WithoutSponsor_Password"));
			}
		}.run();
	}

	public void iCreateACoachWithoutSponsorWithPurchaseUsingAPI(String coachNickname, String productType) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				TDMUser tdm = new TDMUser("COACH_NO_SPONSOR_WPRODUCT", world.getLocale());
				String orderNumber = null;
				OIMService oimUser = new OIMService(world);
				oimUser.createCoachOIMUserWithSponsor("");
				TBBOrder tbbOrder = new TBBOrder(world);
				try {
					orderNumber = tbbOrder.purchaseGivenProductWithCurrentUser(productType);
					world.factoryData.getUser().setOrderNumber(orderNumber);
				} catch (Exception e) {

				}
				String username = world.factoryData.getUser().getEmail();
				String coachId = world.factoryData.getUser().getGncID();
				String password = "Test1234";
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("Coach_" + coachNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("Coach_" + coachNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				world.setCustomerDetails("GUID", world.factoryData.getUser().getGuid());
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Id"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("Coach_" + coachNickname + "_phoneNumber"));
				oimUser.storeCustomerData();
				HashMap<String, String> in = new HashMap<String, String>();
				in.put("email", world.factoryData.getUser().getEmail());
				in.put("password", "Test1234");
				in.put("coachID", world.factoryData.getUser().getGncID());
				in.put("lastname", world.factoryData.getUser().getLastName());
				in.put("firstname", world.factoryData.getUser().getFirstName());
				in.put("joindate", world.factoryData.getUser().getJoinDate());
				in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
				in.put("store", orderNumber);

				tdm.insertDoc(in);

			}
		}.run();
	}

	@When("I create a customer {string} with Sponsor {string}")
	public void createFreeCustomerWithSponsor(String freeUserNickname, String coachSponsor) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				TDMUser tdm = new TDMUser("CUSTOMER_WITH_SPONSOR", world.getLocale());
				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithSponsor(world.getCustomerDetails().get("Coach_" + coachSponsor + "_Id"));
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_customerID",
						world.factoryData.getUser().getGncCustomerID());
				logger.info("CustomerID: " + world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Id"));
				logger.info(
						"CustomerEmail: " + world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_phoneNumber"));
				HashMap<String, String> in = new HashMap<String, String>();
				in.put("email", world.factoryData.getUser().getEmail());
				in.put("password", "Test1234");
				in.put("coachID", world.factoryData.getUser().getGncID());
				in.put("lastname", world.factoryData.getUser().getLastName());
				in.put("firstname", world.factoryData.getUser().getFirstName());
				in.put("joindate", world.factoryData.getUser().getJoinDate());
				in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
				in.put("sponsorId", world.getCustomerDetails().get("Coach_" + coachSponsor + "_sponsorId"));

				tdm.insertDoc(in);

			}
		}.run();
	}

	@Then("I create a customer {string} without sponsor using API")
	public void createACustomerWithoutSponsorUsingAPI(String customerNickname) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				TDMUser tdm = new TDMUser("CUSTOMER_NOSPONOR", world.getLocale());
				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithSponsor("");
				String username = world.factoryData.getUser().getEmail();
				String coachId = world.factoryData.getUser().getGncID();
				String password = "Test1234";
				world.getCustomerDetails().put("Customer_" + customerNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("Customer_" + customerNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("Customer_" + customerNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("Customer_" + customerNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("Customer_" + customerNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("Customer_" + customerNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("Customer_" + customerNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				logger.info(world.getCustomerDetails().get("Customer_" + customerNickname + "_Id"));
				logger.info(world.getCustomerDetails().get("Customer_" + customerNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("Customer_" + customerNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("Customer_" + customerNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("Customer_" + customerNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("Customer_" + customerNickname + "_phoneNumber"));
				HashMap<String, String> in = new HashMap<String, String>();
				in.put("email", world.factoryData.getUser().getEmail());
				in.put("password", "Test1234");
				in.put("coachID", world.factoryData.getUser().getGncID());
				in.put("lastname", world.factoryData.getUser().getLastName());
				in.put("firstname", world.factoryData.getUser().getFirstName());
				in.put("joindate", world.factoryData.getUser().getJoinDate());
				in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());

				tdm.insertDoc(in);
			}
		}.run();
	}




	@When("I create a {string} user with sponsor coach as {string} using API")
	public void createAUserWithSponsorCoachAsUsingAPI(String userType, String sponsor) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				createAUserWithSponsorCoachAsUsingAPI(userType, sponsor, "");
			}
		}.run();
	}

	@When("I create a {string} user with sponsor coach as ShareACart log-in coach using API")
	public void createAUserWithSponsorCoachAsShareACartCoach(String userType) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				File file = new File("config");
				URL[] urls = { file.toURI().toURL() };
				ClassLoader loader = new URLClassLoader(urls);
				ResourceBundle configLib = ResourceBundle.getBundle("config", Locale.getDefault(), loader);
				String shacUserID = configLib.getString("SHAREACART_" + world.getTestEnvironment() + "_UserID");

				createAUserWithSponsorCoachAsUsingAPI(userType, "true", shacUserID);
			}
		}.run();
	}

	@Given("I save continuity order in TDM with email {string} and {string} order number")
	public void iSaveContinuityOrderInTDM(String email, String orderNumber) throws MalformedURLException {
		tdm = new TDMUser("continuityOrder", world.getLocale());
		HashMap<String, String> in = new HashMap<String, String>();
		in.put("email", email);
		in.put("orderNumber", orderNumber);
		tdm.insertDoc(in);

	}

	@Given("I have continuity order from TDM")
	public void iHaveContinuityOrderFromTDM() throws MalformedURLException {
		tdm = new TDMUser("continuityOrder", world.getLocale());
		world.setEmail(tdm.getEmail());
		world.setOrderNum(tdm.getOrder());
		System.out.println("printed email: " + world.getEmail());
		System.out.println("printed order: " + world.getOrderNum());

	}

	@When("I create a {string} user with sponsor coach as {string}, {string} using API")
	public void createAUserWithSponsorCoachAsUsingAPI(String userType, String sponsor, String sponsorID) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				boolean isSponser = Boolean.parseBoolean(sponsor);
				logger.info("Creating" + userType + "user with sponsor as: " + sponsor + "and sponsorID: " + sponsorID);
				OIMService oimUser = new OIMService(world);
				switch (userType.toLowerCase()) {
				case "free":
					tdm = new TDMUser("FREE_USER_SPONSOR_" + isSponser, world.getLocale());
					if (isSponser) {
						oimUser.createFreeOIMUserWithSponsor(sponsorID);

					} else {
						oimUser.createFreeOIMUser();
					}
					logger.info("The Recently created Free User email is: " + world.factoryData.getUser().getEmail());
					break;
				case "coach":
					tdm = new TDMUser("COACH_USER_SPONSOR_" + isSponser, world.getLocale());

					if (isSponser) {
						oimUser.createCoachOIMUserWithSponsor(sponsorID);
					} else {
						oimUser.createCoachOIMUser();
					}
					world.setIsCoachUser(true);
					logger.info("The Recently created Coach User email is: " + world.factoryData.getUser().getEmail());
					break;
				case "club":
					tdm = new TDMUser("CLUB_USER_SPONSOR_" + isSponser, world.getLocale());

					if (isSponser) {
						oimUser.createClubhOIMUserWithSponsor(sponsorID);
					} else {
						oimUser.createClubOIMUser();
					}
					logger.info("The Recently created Club User email is: " + world.factoryData.getUser().getEmail());
					break;
				case "clubcoach":
					tdm = new TDMUser("CLUBCOACH_USER_SPONSOR_" + isSponser, world.getLocale());

					if (isSponser) {
						oimUser.createClubCoachOIMUserWithSponsor(sponsorID);
					} else {
						oimUser.createClubCoachOIMUser();
					}
					world.setExistingCoachDetails("Email", world.factoryData.getUser().getEmail());
					world.setIsCoachUser(true);
					logger.info(
							"The Recently created Coach Club User email is: " + world.factoryData.getUser().getEmail());
					break;
				}
				world.setEmail(world.factoryData.getUser().getEmail());
				world.setPassword("Test1234");
				world.setCustomerDetails("Address1", addressLocale.getString("ADDRESS"));
				world.setCustomerDetails("City", addressLocale.getString("CITY"));
				world.setCustomerDetails("State", addressLocale.getString("STATE"));
				world.setCustomerDetails("Zip", addressLocale.getString("ZIP"));
				world.setCustomerDetails("Email", world.getEmail());
				world.setCustomerDetails("FirstName", world.factoryData.getUser().getFirstName());
				world.setCustomerDetails("LastName", world.factoryData.getUser().getLastName());
				world.setCustomerDetails("Phone", world.factoryData.getUser().getPhoneNumber());
				world.getCustomerDetails().put("Country", addressLocale.getString("COUNTRY"));
				world.getCustomerDetails().put("DOB", "1990/04/16");
				world.getCustomerDetails().put("Gender", "MALE");
				world.getCustomerDetails().put("fname", world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("lname", world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("coachID", world.factoryData.getUser().getGncCoachID());
				world.getCustomerDetails().put("customerID", world.factoryData.getUser().getGncCustomerID());
				world.getCustomerDetails().put("CreatedFrom", "API");
				oimUser.storeCustomerData();
				HashMap<String, String> in = new HashMap<String, String>();
				if (isSponser) {
					in.put("sponsor", sponsor);
					in.put("sponsorID", sponsorID);
				}

				in.put("email", world.factoryData.getUser().getEmail());
				in.put("password", "Test1234");
				in.put("coachID", world.factoryData.getUser().getGncID());
				in.put("lastname", world.factoryData.getUser().getLastName());
				in.put("firstname", world.factoryData.getUser().getFirstName());
				in.put("joindate", world.factoryData.getUser().getJoinDate());
				in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
				in.put("GUID", world.factoryData.getUser().getGuid());

				in.put("Gender", "MALE");
				in.put("Address1", world.factoryData.getUser().getGuid());
				in.put("DOB", "1990/04/16");
				in.put("Country", addressLocale.getString("COUNTRY"));
				in.put("Zip", addressLocale.getString("ZIP"));
				in.put("State", addressLocale.getString("STATE"));
				tdm.insertDoc(in);
			}
		}.run();
	}

	@When("I create a customer {string} without Sponsor")
	public void createFreeCustomerWithOutSponsor(String freeUserNickname) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				tdm = new TDMUser("CUSTOMER_NOSPONOR", world.getLocale());
				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithSponsor("");
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Id"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_phoneNumber"));
				world.getCustomerDetails().put("Email", world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("fname", world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("lname", world.factoryData.getUser().getLastName());
				world.setCustomerDetails("shippingAddress", world.factoryData.getUser().getShipAddress1());
				world.setCustomerDetails("gncID", world.factoryData.getUser().getGncID());
				world.setCustomerDetails("accountType", (world.factoryData.getUser().getCustomerType()).toString());
				world.setCustomerDetails("sponsorID", world.factoryData.getUser().getSponsorID());
				world.setPassword("Test1234");
				oimUser.storeCustomerData();
				HashMap<String, String> in = new HashMap<String, String>();
				in.put("email", world.factoryData.getUser().getEmail());
				in.put("password", "Test1234");
				in.put("coachID", world.factoryData.getUser().getGncID());
				in.put("lastname", world.factoryData.getUser().getLastName());
				in.put("firstname", world.factoryData.getUser().getFirstName());
				in.put("joindate", world.factoryData.getUser().getJoinDate());
				in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
				tdm.insertDoc(in);
			}
		}.run();
	}

	@When("I create Free User {string} with Sponsor {string}")
	public void createFreeUserWithSponsor(String nickName, String coachSponsor) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				ResourceBundle addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
						world.getFormattedLocale());
				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithLeadWheel(world.getCustomerDetails().get("Coach_" + coachSponsor + "_Id"),
						OIMService.LeadWheelType.None);
				world.getCustomerDetails().put("FreeUser_" + nickName + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("FreeUser_" + nickName + "_Password", "Test1234");
				oimUser.storeCustomerData();
				world.getCustomerDetails().put("Sponsor_Id",
						world.getCustomerDetails().get("Coach_" + coachSponsor + "_Id"));
			}
		}.run();
	}

	@When("I create a customer {string} withoutSponsor with Shakelogy Lead Wheel type")
	public void iCreateACustomerWithoutSponsorWithShakelogyLeadWheelType(String customerNickName) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				logger.info("Creating customer withoutSponsor with Shakelogy Lead Wheel type");
				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithLeadWheel("", OIMService.LeadWheelType.Shakeology);
				world.getCustomerDetails().put("enrolledCustomerId", world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("customer_" + customerNickName + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("customer_" + customerNickName + "_Password", "Test1234");
				world.getCustomerDetails().put("customer_" + customerNickName + "_LastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("customer_" + customerNickName + "_FirstName",
						world.factoryData.getUser().getFirstName());
				logger.info(world.getCustomerDetails().get("customer_" + customerNickName + "_Id"));
				logger.info(world.getCustomerDetails().get("customer_" + customerNickName + "_Email"));
				logger.info(world.getCustomerDetails().get("customer_" + customerNickName + "_Password"));
				logger.info(world.getCustomerDetails().get("customer_" + customerNickName + "_LastName"));
				logger.info(world.getCustomerDetails().get("customer_" + customerNickName + "_FirstName"));
				oimUser.storeCustomerData();
			}
		}.run();
	}

	@When("I create free customer {string} with lead wheel type none")
	public void createFreeUserWithLeadwheelTypeNone(String freeUserNickname) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				OIMService oimUser = new OIMService(world);
				oimUser.createFreeOIMUserWithLeadWheel("", OIMService.LeadWheelType.None);
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Email",
						world.factoryData.getUser().getEmail());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Password", "Test1234");
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_Id",
						world.factoryData.getUser().getGncID());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_lastName",
						world.factoryData.getUser().getLastName());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_firstName",
						world.factoryData.getUser().getFirstName());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_joinDate",
						world.factoryData.getUser().getJoinDate());
				world.getCustomerDetails().put("FreeUser_" + freeUserNickname + "_phoneNumber",
						world.factoryData.getUser().getPhoneNumber());
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Id"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Email"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_Password"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_lastName"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_firstName"));
				logger.info(world.getCustomerDetails().get("FreeUser_" + freeUserNickname + "_phoneNumber"));
				oimUser.storeCustomerData();
			}
		}.run();
	}

	@When("I create a Preferred Customer without Sponsor")
	public void createPCWithOutSponsor() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				createPCUser(false, "");
			}
		}.run();
	}

	@When("I create a Preferred Customer with Sponsor")
	public void createPCWithSponsor() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				createPCUser(true, world.factoryConfig.getCoachIdWithMySite());
			}
		}.run();
	}

	@When("I create a Club Preferred Customer without Sponsor")
	public void createClubPCWithOutSponsor() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				createClubPCUser(false, "");
			}
		}.run();
	}

	@When("I create a Club Preferred Customer with Sponsor")
	public void createClubPCWithSponsor() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				createClubPCUser(true, world.factoryConfig.getCoachIdWithMySite());
			}
		}.run();
	}

	@And("I remove recently created user from TDM")
	public void removeRecentlyCreatedUser() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				tdm.removeDocument(world.factoryData.getUser().getEmail());
			}
		}.run();
	}

	@When("I remove user from TDM")
	public void iRemoveUserFromTDM() {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				tdm.removeDocument(world.getEmail());
			}
		}.run();
	}

	@Then("I save user details to {string} TDM database")
	public void iSaveUserDetailsToTDMDatabase(String TDMBucket) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				tdm = new TDMUser(TDMBucket, world.getLocale());
				HashMap<String, String> data = new HashMap<>();
				data.put("email", world.getEmail());
				data.put("password", world.getPassword());
				tdm.insertDoc(data);
			}
		}.run();
	}

	@When("I create a {string} user with sponsor coach {string}, {string} using API")
	public void createAUserWithSponsorCoachAs(String userType, String sponsor, String coachSponsor) {
		new StepExecutive(world) {
			@Override
			public void step() throws Exception {
				createAUserWithSponsorCoachAsUsingAPI(userType, sponsor,
						world.getCustomerDetails().get("Coach_" + coachSponsor + "_Id"));
			}
		}.run();
	}

	@When("I have current logged in user coach details")
	public void iHaveCurrentLoggedInUserCoachDetails() {
		OIMService oimService = new OIMService(world);
		User currentUser = oimService.searchOIMUser("email", world.getEmail());
		sponsorRepId = currentUser.getSponsorID();
		User coachUserDetails = getCoachDetails(sponsorRepId);
		world.getCoachDetailOfCurrentUser().put("SponsorRepFirstName", coachUserDetails.getFirstName());
		world.getCoachDetailOfCurrentUser().put("SponsorRepLastName", coachUserDetails.getLastName());
		world.getCoachDetailOfCurrentUser().put("SponsorRepId", coachUserDetails.getGncCoachID());
	}

	@Then("I verify the sponsor rep id is not updated")
	public void iVerifyTheSponsorRepIdIsNotUpdated() {
		OIMService oimService = new OIMService(world);
		User currentUser = oimService.searchOIMUser("email", world.getEmail());
		tbbHomepage.verifyActualEqualsExpected(sponsorRepId, currentUser.getSponsorID(), "Sponsor rep Id got updated");
	}

	@Then("I verify the sponsor rep id is updated")
	public void iVerifyTheSponsorRepIdIsUpdated() {
		OIMService oimService = new OIMService(world);
		User currentUser = oimService.searchOIMUser("email", world.getEmail());
		tbbHomepage.verifyActualEqualsExpected(world.getCoachDetailOfCurrentUser().get("ReferringRepId"),
				currentUser.getSponsorID(), "Sponsor rep Id got updated");
	}

	@When("I create a Preferred Customer with Sponsor {string}")
	public void iCreateAPreferredCustomerWithSponsor(String gncCoachIdOrSponsorID) {
		logger.info("Creating a Preferred Customer With Sponsor Rep As " + gncCoachIdOrSponsorID);
		createPCUser(true, gncCoachIdOrSponsorID);
	}

	private void createPCUser(Boolean withSponsor, String sponsorRepId) {
		OIMService oimUser = new OIMService(world);
		if (withSponsor) {
			tdm = new TDMUser("PREFERRED_CUSTOMER_WITH_SPONSOR", world.getLocale());
			oimUser.createPreferredCustomerOIMUser(sponsorRepId);
		} else {
			tdm = new TDMUser("PREFERRED_CUSTOMER_NO_SPONSOR", world.getLocale());
			oimUser.createPreferredCustomerOIMUser();
		}

		world.getCustomerDetails().put("PCEmail", world.factoryData.getUser().getEmail());
		oimUser.storeCustomerData();
		insertDataIntoTDM(tdm);
	}

	public void createClubPCUser(Boolean withSponsor, String sponsorRepId) {
		OIMService oimUser = new OIMService(world);
		if (withSponsor) {
			tdm = new TDMUser("CLUB_PC_WITH_SPONSOR", world.getLocale());
			oimUser.createClubPCOIMUser(sponsorRepId);
		} else {
			tdm = new TDMUser("CLUB_PC_NO_SPONSOR", world.getLocale());
			oimUser.createClubPCOIMUser();
		}

		world.getCustomerDetails().put("PCEmail", world.factoryData.getUser().getEmail());
		oimUser.storeCustomerData();
		insertDataIntoTDM(tdm);
	}

	private void insertDataIntoTDM(TDMUser tdm) {
		world.setEmail(world.factoryData.getUser().getEmail());
		world.setPassword("Test1234");
		world.setExistingCoachDetails("Password", world.getPassword());
		HashMap<String, String> in = new HashMap<String, String>();
		in.put("email", world.factoryData.getUser().getEmail());
		in.put("password", "Test1234");
		in.put("coachID", world.factoryData.getUser().getGncID());
		in.put("lastname", world.factoryData.getUser().getLastName());
		in.put("firstname", world.factoryData.getUser().getFirstName());
		in.put("joindate", world.factoryData.getUser().getJoinDate());
		in.put("phonenumber", world.factoryData.getUser().getPhoneNumber());
		in.put("Gender", world.factoryData.getUser().getGender());
		in.put("DOB", world.factoryData.getUser().getDob());
		in.put("originalPcDate", world.factoryData.getUser().getOriginalPcDate());
		in.put("mostRecentPcDate", world.factoryData.getUser().getMostRecentPcDate());
		tdm.insertDoc(in);
	}

	public User getCoachDetails(String repID) {
		User coachUser = new OIMService(world).searchOIMUser("gncCoachId", repID);
		world.getCoachDetailOfCurrentUser().put("FirstName", coachUser.getFirstName());
		world.getCoachDetailOfCurrentUser().put("LastName", coachUser.getLastName());
		world.getCoachDetailOfCurrentUser().put("Phone", coachUser.getPhoneNumber());
		world.getCoachDetailOfCurrentUser().put("Email", coachUser.getEmail());
		world.getCoachDetailOfCurrentUser().put("DOB", coachUser.getDob());
		world.getCoachDetailOfCurrentUser().put("Gender", coachUser.getGender());
		return coachUser;
	}

	@When("I have user details for a SHAC logged in coach")
	public void iHaveUserDetailsForASHACCoach() {
		User coachUserDetails = new OIMService(world).searchOIMUser("email",
				world.getTestDataJson().get("SHAREACART_Username"));
		world.getCoachDetailOfCurrentUser().put("ReferringRepFirstName", coachUserDetails.getFirstName());
		world.getCoachDetailOfCurrentUser().put("ReferringRepLastName", coachUserDetails.getLastName());
		world.getCoachDetailOfCurrentUser().put("ReferringRepId", coachUserDetails.getGncCoachID());
	}

	@When("I have current enrolled user details")
	public void iHaveCurrentEnrolledUserDetails() {
		User userDetails = new OIMService(world).searchOIMUser("email", world.getEmail());
		world.getCustomerDetails().put("FirstName", userDetails.getFirstName());
		world.getCustomerDetails().put("LastName", userDetails.getLastName());
		world.getCustomerDetails().put("Country", userDetails.getBillCountry());
		world.getCustomerDetails().put("Language", userDetails.getPreferredLanguage());
	}

	@When("I remove user from TDM collection {string}")
	public void iRemoveUserFromTDMCollection(String tdmCollection) {
		TDMUser tdm = new TDMUser(tdmCollection, world.getLocale());
		tdm.removeDocument(world.getEmail());
	}

    @Then("I validate all {string} users in TDM against search identity API")
    public void verifyAllTDMUsers(String userType) {
        TDMUser tdm = new TDMUser(userType, world.getLocale());
        logger.info("Number records in TDM for " + userType + " - " + tdm.getEmails().length);

        String testDataUserType = "";
        switch (userType) {
            case "COACH_WITH_SPONSOR":
                testDataUserType = commonTestData.coachWithSponsor;
                break;
            case "CLUBCOACH_USER_SPONSOR_false":
                testDataUserType = commonTestData.clubCoachUserSponsorFalse;
                break;
            case "FREE_USER_SPONSOR_false":
                testDataUserType = commonTestData.freeUserSponsorFalse;
                break;
            case "CLUB_USER_SPONSOR_false":
                testDataUserType = commonTestData.clubUserSponsorFalse;
                break;
            case "PREFERRED_CUSTOMER_NO_SPONSOR":
                testDataUserType = commonTestData.preferredCustomerNoSponsor;
                break;
            case "PREFERRED_CUSTOMER_WITH_SPONSOR":
                testDataUserType = commonTestData.preferredCustomerWithSponsor;
                break;
            case "CLUB_PC_WITH_SPONSOR":
                testDataUserType = commonTestData.clubPCWithSponsor;
                break;
            case "CLUB_PC_NO_SPONSOR":
                testDataUserType = commonTestData.clubPCNoSponsor;
                break;
            default:
                throw new AutomationIssueException("Not a valid TDM User Type - " + userType);
        }
        OIMService oimService = new OIMService(world);

        for (String tdmUser : tdm.getEmails()) {
			User oimUser = oimService.searchOIMUser("email", tdmUser);
            if (oimUser.getCustomerType() != null && !oimUser.getCustomerType().name().equalsIgnoreCase(testDataUserType)) {
                tdm.removeDocument(tdmUser);
            }
        }
    }
}
