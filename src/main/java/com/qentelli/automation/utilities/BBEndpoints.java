package com.qentelli.automation.utilities;

public class BBEndpoints extends AbstractApplicationsEndpointsResourceBundle {

    private static final String NAME = "BB";
    private static final String BBODPURL = "BBODPURL";
    private static final String BBHOMEURL = "BBHOMEURL";
    private static final String BBBODSTANDARDFLOWURL = "BBBODSTANDARDFLOWURL";
    private static final String BBCREATEACCOUNTPAGEURL = "BBCREATEACCOUNTPAGEURL";
    private static final String BBMYXBIKEFLOWURL = "BBMYXBIKEFLOWURL";
    private static final String BBBODICREATEACCOUNTPAGEURL = "BBBODICREATEACCOUNTPAGEURL";
    private static final String BBODPFLOWCREATEACCOUNTPAGEURL = "BBODPFLOWCREATEACCOUNTPAGEURL";

    public String homeODP;
    public String bbHomeUrl;
    public String bbBodStandardFlowUrl;
    public String bbCreateAccountPageURL;
    public String bbMYXBIKEFlowUrl;
    public String bbBODICreateAccountPageURL;
    public String bbODPFlowCreateAccountPageURL;

    BBEndpoints() {
        super(NAME);
        homeODP = bundle.getString(BBODPURL);
        bbHomeUrl = bundle.getString(BBHOMEURL);
        bbBodStandardFlowUrl = bundle.getString(BBBODSTANDARDFLOWURL);
        bbCreateAccountPageURL = bundle.getString(BBCREATEACCOUNTPAGEURL);
        bbMYXBIKEFlowUrl = bundle.getString(BBMYXBIKEFLOWURL);
        bbBODICreateAccountPageURL = bundle.getString(BBBODICREATEACCOUNTPAGEURL);
        bbODPFlowCreateAccountPageURL = bundle.getString(BBODPFLOWCREATEACCOUNTPAGEURL);
    }
}

