package com.qentelli.automation.stepdefs.application;

import java.util.ResourceBundle;

import com.qentelli.automation.libraries.ConfigFileReader;

import com.qentelli.automation.stepdefs.common.application.HomePage;
import com.qentelli.automation.stepdefs.common.application.MyAccountPage;
import com.qentelli.automation.utilities.TBBTestDataObject;
import com.qentelli.automation.common.World;
import com.qentelli.automation.utilities.CommonUtilities;
import cucumber.api.java.en.When;

public class TBB_MyAccountPageStepDefs {
	private World world;
	MyAccountPage tbbMyAccountpage;
	CommonUtilities commonUtilities;
	ResourceBundle myAccountPageTestData;
	ResourceBundle commonTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.CommonTestData");
	ResourceBundle addressLocale = null;
	ResourceBundle paymentTestData;
	ResourceBundle tbbTestData;
	TBBTestDataObject tbbTestDataObject;
	private ConfigFileReader configFileReader;

    public TBB_MyAccountPageStepDefs(World world) {
		this.world = world;
		tbbHome = new HomePage(world);
		tbbMyAccountpage = new MyAccountPage(this.world);
		addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		tbbTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.TBBTestData",
				world.getFormattedLocale());
		myAccountPageTestData = ResourceBundle.getBundle(
				"com.qentelli.automation.testdata." + world.getTestEnvironment() + ".MyAccountDetails",
				world.getFormattedLocale());
		paymentTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.PaymentTestData",
				world.getFormattedLocale());
		commonUtilities = new CommonUtilities();
		tbbTestDataObject = new TBBTestDataObject();
		configFileReader = new ConfigFileReader(world);
	}

	HomePage tbbHome;


	@When("I click on Edit link under Shipping Address tab")
	public void iClickOnEditLinkUnderShippingAddressTab() {
		tbbMyAccountpage.clickShippingAddressTab();
		tbbMyAccountpage.clickEditLinkShoppingAddress();
	}

}
