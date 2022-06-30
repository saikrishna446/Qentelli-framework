package com.qentelli.automation.stepdefs.application;

import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;
import com.qentelli.automation.stepdefs.common.GlobalNavigationPageStepDefs;
import com.qentelli.automation.stepdefs.common.application.HomePage;
import com.qentelli.automation.stepdefs.common.application.MyAccountPage;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.common.World;
import com.qentelli.automation.libraries.ConfigFileReader;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.pages.common.CommonPage;
import com.qentelli.automation.stepdefs.common.PreConditionApiSteps;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;
import com.qentelli.automation.utilities.CommonUtilities;
import com.qentelli.automation.utilities.TBBTestDataObject;
import com.qentelli.automation.utilities.TDMUser;
import cucumber.api.java.en.Given;

public class TBB_HomePageStepDefs {

	static Logger logger = LogManager.getLogger(TBB_HomePageStepDefs.class);
	private World world;
	HomePage home = null;
	BasePage basePage;

	private String subtotal;
	private String estimatedtotal;
	private String emailId;
	private String password;
	ResourceBundle addressLocale = null;
	ResourceBundle commonTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.CommonTestData");
	ResourceBundle loginTestData;
	ResourceBundle localeProperties;
	ResourceBundle myOrdersPage = null;
	ResourceBundle e2eTestData = null;
	ResourceBundle coachSignUpTestData;
	ResourceBundle accountTestData;
	ResourceBundle packsTestData;

	private ResourceBundle environmentURLS;
	private TDMUser tdmUser;
	private ConfigFileReader configFileReader;

	// TeamtestShopBODDeluxElements(world.driver);
	private ResourceBundle tbbTestData;
	private TBBTestDataObject tbbTestDataObject;
	private PreConditionApiSteps preConditionApiSteps;
	private GlobalNavigationPageStepDefs globalNavStepdefs;

	public TBB_HomePageStepDefs(World world) throws MalformedURLException {
		this.world = world;
		home = new HomePage(world);
		basePage = new BasePage(world, world.driver);

//		tbbMyAccountpage = new MyAccountPage(this.world);
//		utilities = new CommonUtilities();
//
//		tbbMyAccountPageStepDefs = new TBB_MyAccountPageStepDefs(world);
//
//		tbbTestDataObject = new TBBTestDataObject();
//
//		preConditionApiSteps = new PreConditionApiSteps(world);
//
//		globalNavStepdefs = new GlobalNavigationPageStepDefs(world);

		addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		myOrdersPage = ResourceBundle.getBundle("com.qentelli.automation.testdata.MyOrdersPage",
				world.getFormattedLocale());
		tbbAccountPageStepDefs = new TBB_AccountPageStepDefs(world);
		addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		localeProperties = ResourceBundle.getBundle("com.qentelli.automation.locales.Locale",
				world.getFormattedLocale());
		loginTestData = ResourceBundle.getBundle(
				"com.qentelli.automation.testdata." + world.getTestEnvironment() + ".LoginTestData",
				world.getFormattedLocale());
		localeProperties = ResourceBundle.getBundle("com.qentelli.automation.locales.Locale",
				world.getFormattedLocale());
		addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		myOrdersPage = ResourceBundle.getBundle("com.qentelli.automation.testdata.MyOrdersPage",
				world.getFormattedLocale());
		e2eTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.E2E", world.getFormattedLocale());
		coachSignUpTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.CoachSignUp",
				world.getFormattedLocale());
		configFileReader = new ConfigFileReader(world);
		commonPage = new CommonPage(world);
		accountTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.AccountPageTestData",
				Locale.getDefault());
		environmentURLS = ResourceBundle.getBundle(
				"com.qentelli.automation..testdata." + world.getTestEnvironment().toUpperCase() + ".EnvironmentURLS",
				world.getFormattedLocale());
		packsTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.PacksTestData");
		tbbTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.TBBTestData",
				world.getFormattedLocale());
	}

	MyAccountPage tbbMyAccountpage;

	CommonPage commonPage;
	CommonUtilities utilities;
	TBB_AccountPageStepDefs tbbAccountPageStepDefs;
	TBB_MyAccountPageStepDefs tbbMyAccountPageStepDefs;

	@When("I am logged in to TBB as a free customer")
	public void i_am_logged_in_to_TBB_as_a_free_customer() {
		// new StepExecutive(world) {
		// @Override
		// public void step() {{
		i_am_on_TBB_home_page();
//		String email = tbbTestDataObject.freeUserEmailId;
//		String password = tbbTestDataObject.password;
		//signInTBB(email, password);
//		world.getTestDataJson().put("TBB_ExistingFreeUser_Username", email);
//		world.getTestDataJson().put("TBB_ExistingFreeUser_Password", password);
		// }
		// }.run();

	}
	@Given("I am on TBB home page")
	public void i_am_on_TBB_home_page() {
		logger.info(ApplicationsEndpointObject.tbb.home);
		world.driver.get(ApplicationsEndpointObject.tbb.home);
//		globalNav.verifytestLogo();
	}


	}
