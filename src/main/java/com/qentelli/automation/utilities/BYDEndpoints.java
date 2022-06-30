package com.qentelli.automation.utilities;

public class BYDEndpoints extends AbstractApplicationsEndpointsResourceBundle {
	private static final String NAME = "BYD";
	private static final String BYDADMINISTRATIONURL = "BYDADMINISTRATIONURL";

	public String bydAdministrationUrl = null;

	BYDEndpoints() {
		super(NAME);
		bydAdministrationUrl = bundle.getString(BYDADMINISTRATIONURL);
	}
}