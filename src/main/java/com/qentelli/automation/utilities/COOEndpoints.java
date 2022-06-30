package com.qentelli.automation.utilities;

public class COOEndpoints extends AbstractApplicationsEndpointsResourceBundle {
    private static final String NAME = "COO";
    private static final String HOMEURL = "HOMEURL";
    private static final String SIGNINURL = "SIGNINURL";
    private static final String ORDERSERVICEURL = "ORDERSERVICEURL";
    private static final String ORDERSERVICEAPIKEY = "ORDERSERVICEAPIKEY";
    private static final String COACHPROFILEURL = "COACHPROFILEURL";
    private static final String COACHPROFILEAPIKEY = "COACHPROFILEAPIKEY";
    private static final String COOHEALTHCHECKURL = "COOHEALTHCHECKURL";
    private static final String COOORDERS = "COOORDERS";
    private static final String COCUSTOMERS = "COCUSTOMERS";// https://cms.stage.coo.tbbtest.com/customers-report?locale=en_US";
    private static final String COMEMBERS = "COMEMBERS"; // https://cms.stage.coo.tbbtest.com/coaches-and-preferred-customers-report?locale=en_US";
    private static final String COOORDERSX = "COOORDERSXAPIKEY";
    private static final String HARNESS = "HARNESS";
    private static final String PERSONALLYSPONSORED = "PERSONALLYSPONSORED";
    private static final String COACHIMAGEFORMYSITEFILENAME = "COACHIMAGEFORMYSITEFILENAME";
    private static final String COACHIMAGEGOOGLEDOCDOWNLOADLINK = "COACHIMAGEGOOGLEDOCDOWNLOADLINK";
    private static final String COIMPERSONATIONURL = "COIMPERSONATIONURL";

    public String signin = null;
    public String orders = null;
    public String ordersKey = null;
    public String coachProfile = null;
    public String coachProfileKey = null;
    public String cooHealthCheck = null;
    public String cooOrders = null;
    public String cooOrderskey = null;
    public String cooCustomers = null;
    public String cooSponsored = null;
    public String harness = null;
    public String home = "null";
    public String personallySponsoredCoaches = "null";
    public String coachImageForMysiteFileName = "null";
    public String coachImageGoogleDocDownloadLink = "null";
    public String impersonatorDashboard;

    COOEndpoints() {
        super(NAME);
        home = getResString(HOMEURL);
        signin = getResString(SIGNINURL);
        orders = getResString(ORDERSERVICEURL);
        ordersKey = getResString(ORDERSERVICEAPIKEY);
        coachProfile = getResString(COACHPROFILEURL);
        coachProfileKey = getResString(COACHPROFILEAPIKEY);
        cooHealthCheck = getResString(COOHEALTHCHECKURL);
        cooOrders = getResString(COOORDERS);
        cooOrderskey = getResString(COOORDERSX);
        cooCustomers = getResString(COCUSTOMERS);
        cooSponsored = getResString(COMEMBERS);
        harness = getResString(HARNESS);
        personallySponsoredCoaches = getResString(PERSONALLYSPONSORED);
        coachImageForMysiteFileName = getResString(COACHIMAGEFORMYSITEFILENAME);
        coachImageGoogleDocDownloadLink = getResString(COACHIMAGEGOOGLEDOCDOWNLOADLINK);
        impersonatorDashboard = getResString("COIMPERSONATIONURL");
    }
}
