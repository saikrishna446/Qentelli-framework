package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractApplicationEndpointsResourceBundle extends AbstractApplicationsResourceBundle {
	protected static Logger logger = LogManager.getLogger(AbstractApplicationEndpointsResourceBundle.class);
	private final static String BASENAME = "endpoints.";
	private final static String EXTENSION = ".endpoints";
	private final static String REFGEX = "^(http[s]?://www\\.|http[s]?://|www\\.)";

	public AbstractApplicationEndpointsResourceBundle() {
		super(BASENAME + getEnv() + EXTENSION);

	}

	public AbstractApplicationEndpointsResourceBundle(String app) {
		super(app, BASENAME + getEnv() + EXTENSION);

	}

	// if you dont want http in the string
	static public String clean(String endpoint) {
		return endpoint.replaceFirst(REFGEX, "");

	}

}
