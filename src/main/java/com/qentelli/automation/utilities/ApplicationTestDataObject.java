package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationTestDataObject extends AbstractApplicationsResourceBundle {
	protected static Logger logger = LogManager.getLogger(ApplicationTestDataObject.class);

	private static final String APPTESTOBJ = "testdata." + "testdata";

	public ApplicationTestDataObject() {
		super(APPTESTOBJ);
// 		logger.info(bundle.getString("syncid.url"));
		// logger.info(bundle.getString("TBBURL"));

	}

	public String syncid() {
		return bundle.getString("SYNCID");
	}

}
