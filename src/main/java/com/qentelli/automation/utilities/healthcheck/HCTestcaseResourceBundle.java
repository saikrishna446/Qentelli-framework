package com.qentelli.automation.utilities.healthcheck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.utilities.AbstractApplicationsResourceBundle;

public class HCTestcaseResourceBundle extends AbstractApplicationsResourceBundle {
	protected static Logger logger = LogManager.getLogger(HCTestcaseResourceBundle.class);

	private final static String BASENAME = "testdata";
//    "application": "APP",
//    "environment": "ENV",
//    "request_type": "TYPE",
//    "request_info": "INFO"
	public String application;
	public String type;
	public String info;
	public String threshold;

	public HCTestcaseResourceBundle(String app) {
		super(BASENAME + "."  + app + "." + BASENAME);

		application = getResString("APP");
		type = getResString("TYPE");
		info = getResString("INFO");
		logger.info(app);
		if (app.equals("ATG"))
			threshold = getResString("THRESHOLD"+getEnv());

	}

}
