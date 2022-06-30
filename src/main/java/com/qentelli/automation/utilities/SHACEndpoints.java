package com.qentelli.automation.utilities;

public class SHACEndpoints extends AbstractApplicationsEndpointsResourceBundle {
	private static final String NAME = "SHAC";
	private static final String HOMEURL = "HOMEURL";
	private static final String SHACENROLLMENTLINK = "SHACENROLLMENTLINK";
	private static final String SIGNINURL = "SIGNINURL";
	private static final String BODGROUPS = "BODGROUPS";
	private static final String testONDEMAND = "testONDEMAND";
	private static final String BODMEMBERSHIPPACKURL="BODMEMBERSHIPPACKURL";
	private static final String BODIONEDAYPASSURL="BODIONEDAYPASSURL";
	private static final String BODIMONTHLYMEMBERSHIPURL = "BODIMONTHLYMEMBERSHIPURL";

	public String home = null;
	public String shacEnrollmentLink = null;
	public String signInUrl = null;
	public String bodGroups = null;
	public String testOnDemand = null;
	public String bodMembershipPackURL = null;
	public String bodiOneDayPassURL = null;
	public String bodiMonthlyMembershipURL = null;

	SHACEndpoints() {
		super(NAME);
		home = bundle.getString(HOMEURL);
		shacEnrollmentLink = bundle.getString(SHACENROLLMENTLINK);
		signInUrl = bundle.getString(SIGNINURL);
		bodGroups = bundle.getString(BODGROUPS);
		testOnDemand = bundle.getString(testONDEMAND);
		bodMembershipPackURL = bundle.getString(BODMEMBERSHIPPACKURL);
		bodiOneDayPassURL = bundle.getString(BODIONEDAYPASSURL);
		bodiMonthlyMembershipURL = bundle.getString(BODIMONTHLYMEMBERSHIPURL);
	}
}