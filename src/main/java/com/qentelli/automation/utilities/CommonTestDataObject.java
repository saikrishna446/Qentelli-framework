package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonTestDataObject extends AbstractApplicationsResourceBundle {
	private static String basename = "data.";
	private static String extension = "testdata";
	private static String name ="Common";
	protected static Logger logger = LogManager.getLogger(CommonTestDataObject.class);
	public String ccCVV;
	public String ccExpiration;
	public String ccMasterCardNumber;
	public String ccVISACardNumber;
	public String linkHighlightColor;
	public String birthDay;
	public String birthMonth;
	public String enUS;
	public String esUS;
	public String enCA;
	public String frCA;
	public String enGB;
	public String frFR;
	public String coachWithSponsor;
	public String clubCoachUserSponsorFalse;
	public String freeUserSponsorFalse;
	public String clubUserSponsorFalse;
	public String preferredCustomerNoSponsor;
	public String preferredCustomerWithSponsor;
	public String clubPCWithSponsor;
	public String clubPCNoSponsor;

	public CommonTestDataObject() {
		super(name, basename, extension);
		ccCVV = getResString("CVV");
		ccExpiration = getResString("EXPIRATIONDATE");
		ccMasterCardNumber = getResString("MASTERCARDNUMBER");
		ccVISACardNumber = getResString("VISACARDNUMBER");
		linkHighlightColor = getResString("LINKHIGHLIGHTCOLOR");
		birthDay = getResString("BIRTHDAY");
		birthMonth = getResString("BIRTHMONTH");
		enUS = getResString("ENUS");
		esUS = getResString("ESUS");
		enCA = getResString("ENCA");
		frCA = getResString("FRCA");
		enGB = getResString("ENGB");
		frFR = getResString("FRFR");
		coachWithSponsor = getResString("COACHWITHSPONSOR");
		clubCoachUserSponsorFalse = getResString("CLUBCOACHUSERSPONSORFALSE");
		freeUserSponsorFalse = getResString("FREEUSERSPONSORFALSE");
		clubUserSponsorFalse = getResString("CLUBUSERSPONSORFALSE");
		preferredCustomerNoSponsor = getResString("PREFERREDCUSTOMERNOSPONSOR");
		preferredCustomerWithSponsor = getResString("PREFERREDCUSTOMERWITHSPONSOR");
		clubPCWithSponsor = getResString("CLUBPCWITHSPONSOR");
		clubPCNoSponsor = getResString("CLUBPCNOSPONSOR");
	}
}