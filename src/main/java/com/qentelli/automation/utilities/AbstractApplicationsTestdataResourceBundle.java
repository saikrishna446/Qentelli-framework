package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class AbstractApplicationsTestdataResourceBundle extends AbstractApplicationsResourceBundle {
	protected static Logger logger = LogManager.getLogger(AbstractApplicationsTestdataResourceBundle.class);

	private final static String BASENAME = "testdata.";
	private final static String EXTENSION = ".testdata";

	public AbstractApplicationsTestdataResourceBundle() {
		super(BASENAME + getEnv() + EXTENSION);

	}

	public AbstractApplicationsTestdataResourceBundle(String app) {
		super(app, BASENAME + getEnv() + EXTENSION);

	}

}
