package com.qentelli.automation.utilities;

public class BAMIEndpoints extends AbstractApplicationsEndpointsResourceBundle {
	private static final String NAME = "BAMI";
	private static final String BAMIHOMEURL = "BAMIHOMEURL";
	private static final String BAMISIGNINURL = "BAMISIGNINURL";

	public String signin = null;
	public String home = null;

	BAMIEndpoints() {
		super(NAME);
		home = bundle.getString(BAMIHOMEURL);
		signin = bundle.getString(BAMISIGNINURL);
	}
}
