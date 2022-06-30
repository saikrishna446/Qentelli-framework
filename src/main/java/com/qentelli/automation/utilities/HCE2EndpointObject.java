package com.qentelli.automation.utilities;

public class HCE2EndpointObject extends AbstractApplicationsEndpointsResourceBundle {
	private static final String NAME = "HCE2E";
	private static final String ATGHEATHCHECKURL = "ATGHEATHCHECKURL";

	private static final String SYNCID = "SYNCID";
	private static final String SOASERVICE = "SOASERVICE";
	private static final String MGSERVERSURL = "MGSERVERSURL";
	private static final String EBSCCJOBSURL = "EBSCCJOBSURL";
	private static final String CARDCONNECTURL = "CARDCONNECTURL";
	private static final String CARDCONNECTAUTH = "CARDCONNECTAUTH";
	private static final String ISGCUSTOMERCREATEUPDATEURL = "ISGCUSTOMERCREATEUPDATEURL";
	private static final String ISGCUSTOMERCREATEUPDATEAUTH = "ISGCUSTOMERCREATEUPDATEAUTH";

	private static final String ORDERIMPORTURL = "ORDERIMPORTURL";

	private static final String ORDERIMPORTAUTH = "ORDERIMPORTAUTH";
	private static final String SABRIX = "SABRIX";
	private static final String MERLIN = "MERLIN";
	private static final String CYBERSOURCEINT = "CYBERSOURCEINT";
	private static final String CYBERSOURCEINTXAPIKEY = "CYBERSOURCEINTXAPIKEY";	
	private static final String CYBERSOURCE = "CYBERSOURCE";
	private static final String CYBERSOURCEXAPIKEY = "CYBERSOURCEXAPIKEY";
	private static final String COMPASSAPI = "COMPASSAPI" ;
	public String atgHealthcheck = null;
	public String syncid = null;
	public String soaservices = null;
	public String mgserver = null;
	public String ebsccjob = null;
	public String cardconnect = null;
	public String cardconnectauth = null;
	public String orderimport = null;
	public String orderimportAuth = null;
	public String customerCreateUpdate = null;
	public String customerCreateUpdateAuth = null;

	public String sabrix = null;
	public String merlin = null;
	public String compassapi = null;

	public String cybersource = null;
	public String cybersourceAuth = null;
	public String cybersourceInt = null;
	public String cybersourceIntAuth = null;
	public HCE2EndpointObject() {
		super(NAME);
		syncid = bundle.getString(SYNCID);
		soaservices = bundle.getString(SOASERVICE);
		mgserver = bundle.getString(MGSERVERSURL);
		ebsccjob = bundle.getString(EBSCCJOBSURL);
		cardconnect = bundle.getString(CARDCONNECTURL);
		cardconnectauth = bundle.getString(CARDCONNECTAUTH);
		customerCreateUpdate = bundle.getString(ISGCUSTOMERCREATEUPDATEURL);
		customerCreateUpdateAuth = bundle.getString(ISGCUSTOMERCREATEUPDATEAUTH);

		orderimport = bundle.getString(ORDERIMPORTURL);
		sabrix = bundle.getString(SABRIX);
		orderimportAuth = bundle.getString(ORDERIMPORTAUTH);
		atgHealthcheck = bundle.getString(ATGHEATHCHECKURL);
		merlin = bundle.getString(MERLIN);
		compassapi = bundle.getString(COMPASSAPI);
		cybersource = bundle.getString(CYBERSOURCE);
		cybersourceAuth = bundle.getString(CYBERSOURCEXAPIKEY);
		cybersourceInt = bundle.getString(CYBERSOURCEINT);
		cybersourceIntAuth = bundle.getString(CYBERSOURCEINTXAPIKEY);
	}

}
