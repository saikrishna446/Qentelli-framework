package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class AbstractApplicationsResourceBundle extends AbstractResourceBundle {
	protected static Logger logger = LogManager.getLogger(AbstractApplicationsResourceBundle.class);
	private static String basename = "applications.";

	private String objName;

	protected AbstractApplicationsResourceBundle(String objN) {
		super(basename + getApplicationName() + "." + objN);

	}

	AbstractApplicationsResourceBundle(String app, String objN) {
		super(basename, app + "." + objN);
        // logger.info("root application: app" +
	}

	AbstractApplicationsResourceBundle(String name, String basename, String extension) {
		super(basename + extension);
		logger.info("Getting " + name + " resource bundle from " + basename + extension);
	}

	public String getObjName() {
		return objName;
	}

}
