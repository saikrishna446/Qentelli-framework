package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class AbstractApplicationsEndpointsResourceBundle extends AbstractApplicationsResourceBundle {
	protected static Logger logger = LogManager.getLogger(AbstractApplicationsEndpointsResourceBundle.class);

	private static String basename = "endpoints.";
    public String home;

	public AbstractApplicationsEndpointsResourceBundle() {
		super(basename + getEnv() + "." + "endpoints");

	}

	public AbstractApplicationsEndpointsResourceBundle(String app) {
		super(app, basename + getEnv() + "." + "endpoints");

	}

	// if you dont want http in the string
	static public String clean(String endpoint) {
		return endpoint.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");

	}


    public void navigate(WebDriver d) {
      logger.info(" -> " + d);
      d.get(home);
    }

}
