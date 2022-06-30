package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class E2ETestDataObject extends AbstractApplicationsTestdataResourceBundle {
	protected static Logger logger = LogManager.getLogger(E2ETestDataObject.class);
	private final static String NAME = "E2E";

	public E2ETestDataObject() {
		super(NAME);
	}

	public String syncid() {
		return bundle.getString("SYNCID");
	}

	public String pack() {
		return bundle.getString("pack");
	}

	public String packType() {
		return bundle.getString("packtype");
	}

	public String flavor() {
		return bundle.getString("flavor");
	}

	public String packaging() {
		return bundle.getString("packaging");
	}

	public String reovery() {
		return bundle.getString("recover");
	}

	public String rpacking() {
		return bundle.getString("RPacking");
	}

	public String qty() {
		return bundle.getString("qty");
	}

	public String email() {
		return CommonUtilities.randomEmail();
	}

	public String phone() {
		return CommonUtilities.randomPhone();
	}

}
