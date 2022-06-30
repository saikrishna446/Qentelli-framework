package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class COOTestcaseResourceBundle extends AbstractApplicationsResourceBundle {
	protected static Logger logger = LogManager.getLogger(COOTestcaseResourceBundle.class);

	private final static String BASENAME = "testdata.";
	public String user;
	public String password;
	public String coachID;
	public String sponsorID;
	public String pcsponsorID;
	public String parentID = null;
	public String leg;
	public String guid;

	public String member;
	public String memberEmail;
	public String customerEmail;

	public COOTestcaseResourceBundle() {
		super(BASENAME + getEnv() + "." +
				BASENAME);

		if (bundle != null) {
			user = getResString("USR");
			password = getResString("PWD");
			sponsorID = getResString("SPONSORID") ;
			logger.info(sponsorID);
			coachID = getResString("COACHID");
			pcsponsorID = getResString("PCSPONSORID") ;
		}
	}

}
