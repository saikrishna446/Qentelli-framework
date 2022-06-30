package com.qentelli.automation.stepdefs.application;

import java.util.ResourceBundle;

import com.qentelli.automation.pages.common.CommonPage;
import com.qentelli.automation.utilities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.common.World;
import com.qentelli.automation.libraries.ConfigFileReader;

public class TBB_AccountPageStepDefs {

	private World world;
	public CommonPage commonPage;
	ResourceBundle accountTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.AccountPageTestData");
	ResourceBundle addresslocale;
	ResourceBundle commonTestData;
	ResourceBundle localeProperties;
	private ConfigFileReader configFileReader;
	private TBBTestDataObject tbbTestDataObject;
	Logger logger;
	ATGProps props = new ATGProps();

	public TBB_AccountPageStepDefs(World world) {
		this.world = world;
		commonPage = new CommonPage(world);
		configFileReader = new ConfigFileReader(world);
		tbbTestDataObject = new TBBTestDataObject();
//		tbbAccountPage = new TeamtestAccountPage(this.world);
//		signupPage = new TeamtestCoachSignUpPage(this.world);
		addresslocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
				world.getFormattedLocale());
		commonTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.CommonTestData");
		logger = LogManager.getLogger(TBB_AccountPageStepDefs.class);
		localeProperties = ResourceBundle.getBundle("com.qentelli.automation.locales.Locale",
				world.getFormattedLocale());
	}


}
