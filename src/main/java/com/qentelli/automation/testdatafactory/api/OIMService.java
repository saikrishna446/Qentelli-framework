package com.qentelli.automation.testdatafactory.api;

import com.qentelli.automation.common.World;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import com.qentelli.automation.testdatafactory.data.CustomerType;
import com.qentelli.automation.testdatafactory.data.User;
import com.qentelli.automation.testdatafactory.dataUtils.DataCreation;
import com.qentelli.automation.testdatafactory.exceptions.GNCIDNotFoundException;
import com.qentelli.automation.utilities.CommonUtilities;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.TDMUser;
import com.mongodb.client.MongoCollection;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.testng.Assert;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.lang.Thread.sleep;

public class OIMService {
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private boolean blnValidateGNCID = false;
	private static final String oimEmptyCreateUserRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prox=\"http://proxy.test.com/\">\n"
			+ "\t<soapenv:Header/>\n" + "\t<soapenv:Body>\n" + "\t\t<prox:createOIMIdentity>\n"
			+ "\t\t\t<create_identity_request>\n" + "\t\t\t\t<user_account>\n"
			+ "\t\t\t\t\t<firstName>dummyFirst</firstName>\n" + "\t\t\t\t\t<lastName>dummyLast</lastName>\n"
			+ "\t\t\t\t\t<username>dummyUser</username>\n" + "\t\t\t\t\t<email>dummyEmail.com</email>\n"
			+ "\t\t\t\t\t<password>dummyPassword</password>\n" + "\t\t\t\t\t<dob>1990/04/16</dob>\n"
			+ "\t\t\t\t\t<gender>MALE</gender>\n" + "\t\t\t\t\t<customerType>dummyType</customerType>\n"
			+ "\t\t\t\t\t<sponsorREPID>dummySponsor</sponsorREPID>\n"
			+ "\t\t\t\t\t<gncCoachID>dummyGNCID</gncCoachID>\n"
			+ "\t\t\t\t\t<preferredLanguage>dummyLocale</preferredLanguage>\n"
			+ "\t\t\t\t\t<telephoneNumber>dummyPhone</telephoneNumber>\n"
			+ "\t\t\t\t\t<leadWheelType>dummyLWType</leadWheelType>\n"
			+ "\t\t\t\t\t<companyName>dummyCompany</companyName>\n" + "\t\t\t\t\t<startDate>dummyDate</startDate>\n"
			+ "\t\t\t\t\t<inputSystem>dummyInputSystem</inputSystem>\n"
			+ "\t\t\t\t\t<billAddress1>dummyAddress1</billAddress1>\n" + "\t\t\t\t\t<billCity>dummyCity</billCity>\n"
			+ "\t\t\t\t\t<billState>dummyState</billState>\n" + "\t\t\t\t\t<billPostalCode>dummyZip</billPostalCode>\n"
			+ "\t\t\t\t\t<billCountry>dummyCountry</billCountry>\n"
			+ "\t\t\t\t\t<shipAddress1>dummyAddress1</shipAddress1>\n" + "\t\t\t\t\t<shipCity>dummyCity</shipCity>\n"
			+ "\t\t\t\t\t<shipState>dummyState</shipState>\n" + "\t\t\t\t\t<shipPostalCode>dummyZip</shipPostalCode>\n"
			+ "\t\t\t\t\t<shipCountry>dummyCountry</shipCountry>\n"
			+ "\t\t\t\t\t<preferredProduct>BE</preferredProduct>\n" + "\t\t\t\t</user_account>\n"
			+ "\t\t\t</create_identity_request>\n" + "\t\t</prox:createOIMIdentity>\n" + "\t</soapenv:Body>\n"
			+ "</soapenv:Envelope>\n";
	private static final String oimEmptyCreateUserRequestForPCUser = oimEmptyCreateUserRequest.replaceAll(
			"<shipCountry>dummyCountry</shipCountry>",
			"<shipCountry>dummyCountry</shipCountry>\n\t\t\t<mostRecentPcDate>dummyMostRecentPcDate</mostRecentPcDate>\n\t\t\t"
					+ "<originalPcDate>dummyOriginalPcDate</originalPcDate>\n");

	private String userNamePattern = "dummyUser";
	private String firstNamePattern = "dummyFirst";
	private String lastNamePattern = "dummyLast";
	private String emailPattern = "dummyEmail.com";
	private String cusTypePattern = "dummyType";
	private String sponsorPattern = "dummySponsor";
	private String localePattern = "dummyLocale";
	private String passwordPattern = "dummyPassword";
	private String companyPattern = "dummyCompany";
	private String datePattern = "dummyDate";
	private String inputSystemPattern = "dummyInputSystem";
	private String leadWheelTypePattern = "dummyLWType";
	private String address1Pattern = "dummyAddress1";
	private String cityPattern = "dummyCity";
	private String statePattern = "dummyState";
	private String zipcodePattern = "dummyZip";
	private String countryPattern = "dummyCountry";
	private String searchNamePattern = "dummySearchName";
	private String searchValuePattern = "dummySearchValue";
	private String phonePattern = "dummyPhone";
	private String gncPattern = "dummyGNCID";
	private String originalPcDatePattern = "dummyOriginalPcDate";
	private String mostRecentPcDatePattern = "dummyMostRecentPcDate";

	private static final String oimEmptyGetUserInfo = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:prox=\"http://proxy.test.com/\">\n"
			+ "\t<soapenv:Header/>\n" + "\t<soapenv:Body>\n" + "\t\t<prox:searchOIMIdentity>\n"
			+ "\t\t\t<search_identity_request>\n" + "\t\t\t\t<searchFilterName>dummySearchName</searchFilterName>\n"
			+ "\t\t\t\t<searchFilterValue>dummySearchValue</searchFilterValue>\n" + "\t\t\t</search_identity_request>\n"
			+ "\t\t</prox:searchOIMIdentity>\n" + "\t</soapenv:Body>\n" + "</soapenv:Envelope>\n";

	private FactoryConfig factoryConfig;

	private SoapRequest soapRequest;
	private World world;
	private static boolean COACH_NO_SPONSOR_CLEAN_UP_CHECK = false;

	public OIMService(World world) {
		this.world = world;
		factoryConfig = world.factoryConfig;
		soapRequest = new SoapRequest(world);
		// Wait for gnc id only if it is BYD test else, ignore GNC ID
		blnValidateGNCID = RuntimeSingleton.getInstance().getTags().contains("@BYD");
	}

	public boolean getValidateGNCID() {
		return blnValidateGNCID;
	}

	public void setValidateGNCID(boolean validate) {
		blnValidateGNCID = validate;
	}

	// ------------------------
	private void createIdentity(CustomerType customerType) {
		createIdentity(DataCreation.createEmail(), customerType, factoryConfig.getDefaultSponsor(), null);
	}

	public void createFreeOIMUser() {
		createIdentity(CustomerType.FreeUser);

	}

	public void createCoachOIMUser() {
		createIdentity(CustomerType.CoachUser);

	}

	public void createClubOIMUser() {
		createIdentity(CustomerType.ClubUser);

	}

	public void createClubCoachOIMUser() {
		createIdentity(CustomerType.ClubCoachUser);

	}

	public void createPreferredCustomerOIMUser() {
		createIdentity(CustomerType.PreferredCustomer);
	}

	public void createPreferredCustomerOIMUser(String sponsorRepId) {
		createIdentityWithSponsor(sponsorRepId, CustomerType.PreferredCustomer);
	}

	public void createClubPCOIMUser() {
		createIdentity(CustomerType.ClubPreferredCustomer);
	}

	public void createClubPCOIMUser(String sponsorRepId) {
		createIdentityWithSponsor(sponsorRepId, CustomerType.ClubPreferredCustomer);
	}

	// ------------------------
	private void createIdentityWithEmail(String email, CustomerType customerType) {
		createIdentity(email, customerType, factoryConfig.getDefaultSponsor(), null);
	}

	public void createFreeOIMUserWithEmail(String email) {
		createIdentityWithEmail(email, CustomerType.FreeUser);
	}

	public void createClubhOIMUser(String email) {
		createIdentityWithEmail(email, CustomerType.ClubUser);
	}

	public void createCoachOIMUser(String email) {
		createIdentityWithEmail(email, CustomerType.CoachUser);
	}

	public void createClubCoachOIMUser(String email) {
		createIdentityWithEmail(email, CustomerType.ClubCoachUser);
	}

	// ------------------------
	private void createIdentityWithSponsor(String sponsor, CustomerType customerType) {
		createIdentity(DataCreation.createEmail(), customerType, sponsor, null);

	}

	public void createFreeOIMUserWithSponsor(String sponsor) {
		createIdentityWithSponsor(sponsor, CustomerType.FreeUser);
	}

	public void createClubhOIMUserWithSponsor(String sponsor) {
		createIdentityWithSponsor(sponsor, CustomerType.ClubUser);
	}

	public void createCoachOIMUserWithSponsor(String sponsor) {
		createIdentityWithSponsor(sponsor, CustomerType.CoachUser);
	}

	public void createClubCoachOIMUserWithSponsor(String sponsor) {
		createIdentityWithSponsor(sponsor, CustomerType.ClubCoachUser);
	}

	// ------------------------
	public void createCoachOIMUser(String email, String sponsorID) {
		createIdentity(email, CustomerType.CoachUser, sponsorID, LeadWheelType.Coach);
	}

	public void createClubOIMUser(String email, String sponsorID) {
		createIdentity(email, CustomerType.ClubUser, sponsorID, LeadWheelType.Customer);
	}

	public void createClubCoachOIMUser(String email, String sponsorID) {
		createIdentity(email, CustomerType.ClubCoachUser, sponsorID, LeadWheelType.Coach);
	}

	// ------------------------
	public void createFreeOIMUser(String email, String sponsorID) {
		createIdentity(email, CustomerType.FreeUser, sponsorID, LeadWheelType.Customer);
	}

	public void createPCOIMUser(String email, String sponsorID) {
		createIdentity(email, CustomerType.PreferredCustomer, sponsorID, LeadWheelType.Customer);
	}

	public void createFreeOIMUserWithLeadWheel(String sponsorID, LeadWheelType leadWheelType) {
		createIdentity(DataCreation.createEmail(), CustomerType.FreeUser, sponsorID, leadWheelType);
	}

	public void createClubOIMUserWithLeadWheel(String sponsorID, LeadWheelType leadWheelType) {
		createIdentity(DataCreation.createEmail(), CustomerType.ClubUser, sponsorID, leadWheelType);
	}

	// ------------------------
	// Search User

	public User searchOIMUser(String key, String value) {
		User user = new User();
		user.setValue(key, value);
		RuntimeProperties p = new RuntimeProperties();
		LOGGER.debug("Getting User Info");
		String srchStatus = "";
		String endpoint = factoryConfig.getOimEndpoint() + "/search";
		String searchUserInfoBody = generateGetUserInfoBody(key, value);
		Response response;
		for (int i = 0; i < 5; i++) {
			try {
				soapRequest.setDescription("Search User");
				response = soapRequest.doSoapRequest(endpoint, searchUserInfoBody);
				srchStatus = soapRequest.getSoapResponseValue(
						"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchStatus");
				if (srchStatus.contains("FAIL")) {
					// Retry 5 times on fail status or exception
					LOGGER.info("Retrying to get User Info due to SOAP RESPONSE system error - "
							+ soapRequest.getSoapResponseValue(
									"Envelope.Body.searchOIMIdentityResponse.search_identity_response.systemErrorMessage"));
					sleep(10000);
					continue;
				}
			} catch (Exception e) {
				LOGGER.debug("Caught exception on Search User call: " + endpoint + "\n" + "Exception: " + e);
				LOGGER.debug("Request Body: " + searchUserInfoBody);
				continue;
			}
			if (response.getStatusCode() != 200) {
				LOGGER.debug("Status code failed for Search User call: " + endpoint);
				LOGGER.debug("Request Body: \n" + searchUserInfoBody);
				LOGGER.debug("Response Body: \n" + response.getBody().asString());
				Assert.fail("Search User call failed : " + endpoint + " with response: \n" + response.getStatusCode());
			} else {
				// good response, proceed
				break;
			}
		}

		if (srchStatus.contains("USER_FOUND")) {
			LOGGER.info("User [" + value + "] Found:");
			String customerType = (soapRequest
					.getSoapResponseValue(
							"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.customerType")
					.contains("COACH")) ? "COACH" : "CUSTOMER";
			// Set user details
			user.setEmail(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.email"));
			user.setCustomerType(getCustomerType(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.customerType")));
			user.setSponsorID(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.sponsorREPID"));
			String username = DataCreation.getUserFromEmail(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.email"));
			user.setUsername(username);
			user.setFirstName(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.firstName"));
			user.setLastName(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.lastName"));
			user.setPhoneNumber(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.telephoneNumber"));
			user.setJoinDate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.startDate"));
			user.setDob(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.dob"));
			user.setGender(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.gender"));
			user.setPreferredLanguage(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.preferredLanguage"));
			user.setBillAddress1(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billAddress1"));
			user.setBillAddress2(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billAddress2"));
			user.setBillCity(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billCity"));
			user.setBillState(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billState"));
			user.setBillPostalCode(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billPostalCode"));
			user.setBillCountry(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billCountry"));
			user.setShipAddress1(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipAddress1"));
			user.setShipAddress2(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipAddress2"));
			user.setShipCity(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipCity"));
			user.setShipState(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipState"));
			user.setShipPostalCode(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipPostalCode"));
			user.setShipCountry(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipCountry"));
			user.setGuid(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.guid"));
			user.setCompanyName(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.companyName"));
			user.setMostRecentPcDate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.mostRecentPcDate"));
			user.setOriginalPcDate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.originalPcDate"));
			user.setOriginalCoachDate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.originalCoachDate"));
			user.setMostRecentCoachdate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.mostRecentCoachDate"));
			user.setCurrentIdPcDate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.currentIdPcDate"));
			user.setCurrentIdCoachDate(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.currentIdCoachDate"));
			user.setDowngradeReason(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.downgradeReason"));
			user.setDowngradeType(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.downgradeType"));
			user.setUpgradeType(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.upgradeType"));

			String gncNodeName = "";
			if (customerType.equalsIgnoreCase("COACH")) {
				gncNodeName = "gncCoachID";
			} else if (customerType.equalsIgnoreCase("CUSTOMER")) {
				gncNodeName = "gncCustomerID";
			}
			String actualGncID = soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser." + gncNodeName);

			if (blnValidateGNCID) {
				int retryCount = 50; // would take max retryCount * 2 secs.
				boolean gncFound = false;
				for (int i = 1; i <= retryCount; i++) {
					if ((actualGncID == null || actualGncID.isEmpty())) {
						try {
							LOGGER.debug("Waiting 2 sec. for GNC " + customerType + " ID - Search retry(" + i + " / "
									+ retryCount + ")");
							sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						try {
							soapRequest.doSoapRequest(factoryConfig.getOimEndpoint() + "/search", searchUserInfoBody);
							actualGncID = soapRequest.getSoapResponseValue(
									"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser."
											+ gncNodeName);
						} catch (Exception e) {
							LOGGER.debug(
									"Catch exception for Soap Request: Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser. "
											+ gncNodeName);
							LOGGER.debug(e.getMessage());
						}
					} else {
						gncFound = true;
						LOGGER.info("GNC ID Found:");
						user.setGncID(actualGncID);
						break;
					}
				}
				if (!gncFound) {
					LOGGER.info("GNC ID NOT Found:");
					throw new GNCIDNotFoundException("GNC " + customerType + " ID for " + value
							+ " was not returned. This user may fail to login");

				}
				p.writeProp("GNC_ID_FOUND", Boolean.toString(gncFound));
			}
			user.setGncCustomerID(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.gncCustomerID"));
			user.setGncCoachID(soapRequest.getSoapResponseValue(
					"Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.gncCoachID"));
		} else {
			LOGGER.error("User [" + value + "] Not Found:");
		}

		return user;
	}

	public CustomerType getCustomerType(String custType) {
		CustomerType cType;
		if (custType.contains("COACH") && custType.contains("CLUB")) {
			cType = CustomerType.ClubCoachUser;
		} else if (custType.contains("CLUB") && custType.contains("PC")) {
			cType = CustomerType.ClubPreferredCustomer;
		} else if (custType.contains("COACH")) {
			cType = CustomerType.CoachUser;
		} else if (custType.contains("CLUB")) {
			cType = CustomerType.ClubUser;
		} else if (custType.contains("PC")) {
			cType = CustomerType.PreferredCustomer;
		} else {
			cType = CustomerType.FreeUser;
		}
		return cType;
	}

	private void createIdentity(String email, CustomerType customerType, String sponsorID, LeadWheelType lwType) {

		String bucket = lwType == null ? customerType.toString()
				: customerType.toString() + "_WHEEL_" + lwType.toString();
		TDMUser tdm = new TDMUser(bucket, world.getLocale());
		LOGGER.debug("Lead wheel type in createIdentity = " + lwType);

		ResourceBundle addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		setValidateGNCID(true); // this will cause waits till gnc id is returned or timeout occurs within 3mins.
		// Ensures user is usable.
		User user = new User();
		user.setEmail(email);
		user.setCustomerType(customerType);
		user.setSponsorID(sponsorID);
		String username = CommonUtilities.removeNumbers(DataCreation.getUserFromEmail(email));
		user.setFirstName(username);
		user.setLastName(username);
		user.setPhoneNumber(addressLocale.getString("PHONE"));
		user.setUsername(username);
		// leave lwType null and pass sponsorID on the request to avoid gnc delay issues
		// on create
		String createUserbody = generateCreateUserBody(username, email, customerType.getCustString(), sponsorID,
				lwType);
		String endpoint = factoryConfig.getOimEndpoint() + "/account";
		LOGGER.info("OIM ENDPOINT: "+endpoint);
		LOGGER.info(createUserbody);
		soapRequest.setDescription("Create User");
		Response response;
		for (int i = 0; i < 5; i++) {
			try {
				LOGGER.debug("Calling createIdentity endpoint");
				response = soapRequest.doSoapRequest(endpoint, createUserbody);
				if (soapRequest
						.getSoapResponseValue("Envelope.Body.createOIMIdentityResponse.create_identity_response.status")
						.contains("FAIL")) {
					// Retry 5 times on fail status or exception
					LOGGER.info("Retrying to create a user due to the SOAP RESPONSE system error "
							+ soapRequest.getSoapResponseValue(
									"Envelope.Body.createOIMIdentityResponse.create_identity_response.systemErrorMessage"));
					sleep(60000);
				}
			} catch (Exception e) {
				LOGGER.debug("Caught exception on createIdentity call: " + endpoint + "\n" + "Exception: " + e);
				LOGGER.debug("Request Body: " + createUserbody);
				continue;
			}
			if (response.getStatusCode() != 200) {
				LOGGER.debug("Status code failed for createIdentity call: " + endpoint);
				LOGGER.debug("Request Body: \n" + createUserbody);
				LOGGER.debug("Response Body: \n" + response.getBody().asString());
				Assert.fail("Status code failed for createIdentity call: " + endpoint + " with response: \n"
						+ response.getStatusCode());
			} else {
				// good response, proceed
				break;
			}
		}
		world.getCustomerDetails().put("CustomerType", customerType.getCustString());
		user.setValue("guid", soapRequest
				.getSoapResponseValue("Envelope.Body.createOIMIdentityResponse.create_identity_response.guid"));

		User user1 = searchOIMUser("guid", user.getGuid());
		tdm.insertDoc(user1.getOutput());
		world.setEmail(email);
		world.factoryData.setUser(user1);
	}

	private String generateCreateUserBody(String username, String email, String customerType, String sponsorID,
			LeadWheelType lwType) {
		LOGGER.debug("Create user body for: " + username + ", " + email + ", " + customerType + ", " + sponsorID);

		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
		sf.setTimeZone(TimeZone.getTimeZone("PST"));
		String date = sf.format(new Date());
		ResourceBundle addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());

		// LocalDateTime localDateTime = LocalDateTime.now();
		// String date =
		// DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDateTime);
		String inputSystem = "SOA_EBS"; // customerType.contains("COACH")?"SOA_EBS":"DIGITAL"; //LIFERAY
		LeadWheelType lwt = lwType;
		// use lead wheel only if sponsorID is not specified
		if (lwt == null && (sponsorID == null || sponsorID.isEmpty())) {
			lwt = (customerType.contains("COACH")) ? LeadWheelType.Coach : LeadWheelType.Customer;
		} else if (sponsorID != null && !sponsorID.isEmpty()) {
			lwt = LeadWheelType.None;
		}
		LOGGER.debug("Lead wheel type in createIdentity = " + lwt.getLwType());

		String fname = (world.factoryData.getGeneology() == null) ? username : world.factoryData.getGeneology();
		String str = customerType.toUpperCase().contains("PC") ? oimEmptyCreateUserRequestForPCUser
				: oimEmptyCreateUserRequest;
		str = str.replaceAll(userNamePattern, username).replaceAll(emailPattern, email)
				.replaceAll(cusTypePattern, customerType).replaceAll(lastNamePattern, username)
				.replaceAll(companyPattern, username).replaceAll(sponsorPattern, sponsorID)
				.replaceAll(localePattern, factoryConfig.getLocaleString())
				.replaceAll(passwordPattern, factoryConfig.getPassword()).replaceAll(datePattern, date)
				.replaceAll(firstNamePattern, fname).replaceAll(inputSystemPattern, inputSystem)
				.replaceAll(leadWheelTypePattern, lwt.getLwType())
				.replaceAll(address1Pattern, addressLocale.getString("ADDRESS"))
				.replaceAll(cityPattern, addressLocale.getString("CITY"))
				.replaceAll(zipcodePattern, addressLocale.getString("ZIP"))
				.replaceAll(phonePattern, addressLocale.getString("PHONE")).replaceAll(gncPattern, "");
		str = str.replaceAll(countryPattern, addressLocale.getString("COUNTRY")).replaceAll(statePattern,
				addressLocale.getString("STATE"));
		if (customerType.contains("PC")) {
			str = str.replaceAll(mostRecentPcDatePattern, date).replaceAll(originalPcDatePattern, date);

		}
		world.getCustomerDetails().put("LeadWheelType", lwt.getLwType());
		// LOGGER.debug("Create User Body Request:\n" + str);
		return str;
	}

	private String generateGetUserInfoBody(String searchFilterName, String searchFilterValue) {
		LOGGER.debug("Get user info for " + searchFilterName + ", " + searchFilterValue);
		String str = oimEmptyGetUserInfo;
		str = str.replaceAll(searchNamePattern, searchFilterName).replaceAll(searchValuePattern, searchFilterValue);
		// LOGGER.debug("Get user info response:\n" + str);
		return str;
	}

	public enum LeadWheelType {
		Coach("COACH"), Customer("CUSTOMER"), Shakeology("SUCCESSCLUB"), None("");

		private final String lwType;

		LeadWheelType(String lwType) {
			this.lwType = lwType;
		}

		public String getLwType() {
			return lwType;
		}
	}

	/*
	 * @Description: To store customer data, which is used to validate the data at
	 * later steps
	 */
	public void storeCustomerData() {
		try {
			ResourceBundle addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
					world.getFormattedLocale());
			world.getCustomerDetails().put("CreatedFrom", "API");
			world.getCustomerDetails().put("Email", world.factoryData.getUser().getEmail());
			world.getCustomerDetails().put("Password", "Test1234");
			world.getCustomerDetails().put("fname", world.factoryData.getUser().getFirstName());
			world.getCustomerDetails().put("lname", world.factoryData.getUser().getLastName());
			world.setCustomerDetails("FirstName", world.factoryData.getUser().getFirstName());
			world.setCustomerDetails("LastName", world.factoryData.getUser().getLastName());
			world.getCustomerDetails().put("Phone", world.factoryData.getUser().getPhoneNumber());
			world.getCustomerDetails().put("Address1", addressLocale.getString("ADDRESS"));
			world.getCustomerDetails().put("City", addressLocale.getString("CITY"));
			world.getCustomerDetails().put("State", addressLocale.getString("STATE"));
			world.getCustomerDetails().put("Zip", addressLocale.getString("ZIP"));
			world.getCustomerDetails().put("Country", addressLocale.getString("COUNTRY"));
			world.getCustomerDetails().put("DOB", "1990/04/16");
			world.getCustomerDetails().put("Gender", "MALE");
			world.getCustomerDetails().put("Customer_Id", world.factoryData.getUser().getGncID());
			world.getCustomerDetails().put("GncCoach_Id", world.factoryData.getUser().getGncCoachID());
			world.getCustomerDetails().put("Sponsor_Id", world.factoryData.getUser().getSponsorID());

			world.setCustomerDetails("DOB", world.factoryData.getUser().getDob());
			world.setCustomerDetails("Gender", world.factoryData.getUser().getGender());
			world.setCustomerDetails("coachID", world.factoryData.getUser().getSponsorID());
			world.setCustomerDetails("OriginalPcDate", world.factoryData.getUser().getOriginalPcDate());
			world.setCustomerDetails("MostRecentPcDate", world.factoryData.getUser().getMostRecentPcDate());
			world.setCustomerDetails("MostRecentCoachDate", world.factoryData.getUser().getMostRecentCoachdate());
			world.setCustomerDetails("OriginalCoachDate", world.factoryData.getUser().getOriginalCoachDate());
			world.setCustomerDetails("CurrentIdPcDate", world.factoryData.getUser().getCurrentIdPcDate());
			world.setCustomerDetails("CurrentIdCoachDate", world.factoryData.getUser().getCurrentIdCoachDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error : failed to get data from API and free user creation failed");
		}
	}

	public void filterCollectionForValidCustomerType(String TDMBucket) {
		int i = 0;
		if (!COACH_NO_SPONSOR_CLEAN_UP_CHECK) {
			if (("COACH_NO_SPONSOR_" + world.getTestEnvironment() + "_" + world.getLocale())
					.contains(TDMBucket.toUpperCase())) {
				COACH_NO_SPONSOR_CLEAN_UP_CHECK = true;
				TDMUser tdmUser = new TDMUser(TDMBucket, world.getLocale());
				String collcetionName = tdmUser.getCollection().getNamespace().getCollectionName();
				OIMService oimService = new OIMService(world);
				MongoCollection<Document> coll = tdmUser.getCollection(); // retrieving all the records
				for (Document doc : coll.find()) {
					i++;
					// here we are iteraring each record and removing the records which are not, any
					// of from coach or coach come club user and targeted locale
					String GUID = doc.getString("GUID");
					LOGGER.info("Search For GUID " + GUID);
					User user = oimService.searchOIMUser("guid", GUID);

					switch (user.getCustomerType()) {
					case CoachUser:
					case ClubCoachUser:
						if (collcetionName.toUpperCase().contains("COACH")
								&& !collcetionName.toUpperCase().contains(user.getPreferredLanguage().toUpperCase())) {
							tdmUser.removeDocument(doc.getString("email"));
							LOGGER.info("Removing an user from " + collcetionName + " bucket with an email"
									+ doc.getString("email"));
						}
						break;
					default:
						if (collcetionName.toUpperCase().contains("COACH")) {
							tdmUser.removeDocument(doc.getString("email"));
							LOGGER.info("Removing an user from " + collcetionName + " bucket with an email"
									+ doc.getString("email"));
						}
						break;
					}
					if (i >= 2)
						break;
				}
			}
		}
	}
}
