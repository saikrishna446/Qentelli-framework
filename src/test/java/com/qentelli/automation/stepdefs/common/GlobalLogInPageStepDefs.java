package com.qentelli.automation.stepdefs.common;

import java.net.MalformedURLException;
import java.util.ResourceBundle;

import com.qentelli.automation.utilities.TBBTestDataObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.common.World;

public class GlobalLogInPageStepDefs {

    static Logger logger = LogManager.getLogger(GlobalLogInPageStepDefs.class);
    private World world;
//    GlobalLogInPage logInPage = null;
    private TBBTestDataObject tbbTestDataObject;
    // If this is to support global login, we need to create an instance of project based test data object (or the parent type then we cast at run time to specific proj test data type)
    private String email;
    private String password;

    ResourceBundle addressLocale = null;
    ResourceBundle commonTestData =
            ResourceBundle.getBundle("com.qentelli.automation.testdata.CommonTestData");
    ResourceBundle loginTestData;

    private ResourceBundle environmentURLS;
    ResourceBundle localeProperties;

    public GlobalLogInPageStepDefs(World world) throws MalformedURLException {
        this.world = world;
    //    logInPage = new GlobalLogInPage(world);

        localeProperties = ResourceBundle.getBundle("com.qentelli.automation.locales.Locale",
                world.getFormattedLocale());
        loginTestData = ResourceBundle.getBundle(
                "com.qentelli.automation.testdata." + world.getTestEnvironment() + ".LoginTestData",
                world.getFormattedLocale());
        localeProperties = ResourceBundle.getBundle("com.qentelli.automation.locales.Locale",
                world.getFormattedLocale());

        tbbTestDataObject = new TBBTestDataObject();
        // TODO(GZ) If pending issue identified and resolved by framework architect, we can use tbbTestDataObject approach for global log in to support all evn/locale test data input. WIP for now.
    }




}