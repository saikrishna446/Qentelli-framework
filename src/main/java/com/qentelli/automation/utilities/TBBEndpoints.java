package com.qentelli.automation.utilities;

public class TBBEndpoints extends AbstractApplicationsEndpointsResourceBundle {
	private static final String NAME = "TBB";
	private static final String TBBURL = "TBBURL";
	private static final String TBBENROLLMENT = "TBBENROLLMENTL";
	private static final String TBBURLBYPASSS = "BYPASSCAPTCHATBBENROLLURL";
	private static final String TBBBODSTANDALONEMEMBERSHIP = "TBBBODSTANDALONEMEMBERSHIP";
	private static final String PREFERREDCUSTOMERURL = "PREFERREDCUSTOMERURL";
	private static final String PREFERREDCUSTOMERURLTSP = "PREFERREDCUSTOMERURLTSP";
	private static final String REFERRINGREPID = "REFERRINGREPID";
	private static final String REFERRINGREPIDONDESTPAGE = "REFERRINGREPIDONDESTPAGE";
	private static final String DYNADMINURL = "DYNADMINURL";
	private static final String TBBNUTRITIONURL = "TBBNUTRITIONURL";
	private static final String TBB2BMINDSETBASEKIT = "TBB2BMINDSETBASEKIT";
	private static final String CHINUPBAR= "CHINUPBAR";
	private static final String PIYOSHAKEOLOGYCPURL ="PIYOSHAKEOLOGYCPURL";
	private static final String TWENTYONEDAYFIXCPURL ="21DAYFIXCPURL";
	private static final String ULTIMATEPORTIONFIXURL = "ULTIMATEPORTIONFIXURL";
	private static final String CHOCOLATEVEGANSHAKEOLOGY = "CHOCOLATEVEGANSHAKEOLOGY";
	private static final String STRENGTHSLIDESBOOTIES = "STRENGTHSLIDESBOOTIES";
	private static final String URLCOMPLIANCETIPS = "URLCOMPLIANCETIPS";
	private static final String URLCOACHPOLICIESANDPROCEDURE = "URLCOACHPOLICIESANDPROCEDURE";
	private static final String FAQURL = "FAQURL";
	private static final String TBBBODPERFORMANCEPACKURL = "TBBBODPERFORMANCEPACKURL";
	private static final String TERMSANDCONDITIONS = "TERMSANDCONDITIONS";
	private static final String PRIVACY = "PRIVACY";
	private static final String BODIONEDAYPASSURL = "BODIONEDAYPASSURL";
	private static final String BODISTANDALONEURL = "BODISTANDALONEURL";
	private static final String BODIMONTHLYSTANDALONEURL = "BODIMONTHLYSTANDALONEURL";
	private static final String TBBTSPPRODUCTDISPLAYPAGEURL = "TBBTSPPRODUCTDISPLAYPAGEURL";
	private static final String DSAURL = "DSAURL";
	private static final String BBBURL = "BBBURL";
	private static final String FLAGREDESIGNPROJECT = "FLAGREDESIGNPROJECT";


	public String enrollmentBypass = null;
	public String enrollment = null;
	public String standAloneMembership = null;
    // public String home = "null";
	public String pcPage = "null";
	public String pcPageTSP = "null";
	public String referringRepId = null;
	public String referringRepIdWithDestPage = null;
	public String dynAdmin = null;
	public String nutritionUrl = null;
	public String tbb2BMindsetBaseKit = null;
	public String chinUpBar = null;
	public String piyoShakeologyCPUrl = null;
	public String twentyOneDayFixCPUrl = null;
	public String ultimatePortionFixURL = null;
	public String chocolateVeganShakeology = null;
	public String strengthSlidesBooties = null;
	public String complianceTips = null;
	public String coachPoliciesAndProcedure = null;
	public String faqUrl = null;
	public String tbbBODPerformancePackURL = null;
	public String tbbTermsAndConditionsFooterURL = null;
	public String tbbPrivacyFooterURL = null;
	public String tbbBODIOneDayPassURL = null;
	public String tbbBODIStandaloneURL = null;
	public String tbbBODIMonthlyStandaloneURL = null;
	public String tbbTSPProductDisplayPageURL = null;
	public String dsaUrl = null;
	public String bbbUrl = null;
	public String flagRedesignProject = null;

	TBBEndpoints() {
		super(NAME);
		home = getResString(TBBURL);
		enrollment = getResString(TBBENROLLMENT);
		enrollmentBypass = getResString(TBBURLBYPASSS);
		standAloneMembership = getResString(TBBBODSTANDALONEMEMBERSHIP);
		pcPage = getResString(PREFERREDCUSTOMERURL);
		pcPageTSP = getResString(PREFERREDCUSTOMERURLTSP);
		referringRepId = getResString(REFERRINGREPID);
		referringRepIdWithDestPage = getResString(REFERRINGREPIDONDESTPAGE);
		dynAdmin = getResString(DYNADMINURL);
		nutritionUrl = getResString(TBBNUTRITIONURL);
		tbb2BMindsetBaseKit = getResString(TBB2BMINDSETBASEKIT);
		chinUpBar = getResString(CHINUPBAR);
		piyoShakeologyCPUrl = getResString(PIYOSHAKEOLOGYCPURL);
		twentyOneDayFixCPUrl =getResString(TWENTYONEDAYFIXCPURL);
		ultimatePortionFixURL =getResString(ULTIMATEPORTIONFIXURL);
		chocolateVeganShakeology = getResString(CHOCOLATEVEGANSHAKEOLOGY);
		strengthSlidesBooties = getResString(STRENGTHSLIDESBOOTIES);
		complianceTips = getResString(URLCOMPLIANCETIPS);
		coachPoliciesAndProcedure = getResString(URLCOACHPOLICIESANDPROCEDURE);
		faqUrl = getResString(FAQURL);
		tbbBODPerformancePackURL = getResString(TBBBODPERFORMANCEPACKURL);
		tbbTermsAndConditionsFooterURL = getResString(TERMSANDCONDITIONS);
		tbbPrivacyFooterURL = getResString(PRIVACY);
		tbbBODIOneDayPassURL = getResString(BODIONEDAYPASSURL);
		tbbBODIStandaloneURL = getResString(BODISTANDALONEURL);
		tbbBODIMonthlyStandaloneURL = getResString(BODIMONTHLYSTANDALONEURL);
		tbbTSPProductDisplayPageURL = getResString(TBBTSPPRODUCTDISPLAYPAGEURL);
		dsaUrl = getResString(DSAURL);
		bbbUrl = getResString(BBBURL);
		flagRedesignProject = getResString(FLAGREDESIGNPROJECT);
	}

    // public static String getChinUpProduct() {
    // return "https://www-tbb" + getEnv() + ".qentelli.com/shop/us/d/chin-up-bar-ChinUpBar";
    // }

    public static String getPRProduct() {
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/us/d/PreferredCustomer?ICID=TBB_HEROSELL_SIGNUPANDSAVE_PREFERREDCUSTOMER";
    }

    public static String getBODDeluxProduct() {
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/us/d/annual-qentelli-on-demand-deluxe-shakeology-challenge-pack-BODDeluxe?programId=testOnDemand";

    }

    public static String getBODDeluxProductGB() {
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/gb/d/deluxe-shakeology-completion-pack-DeluxeCompletion?programId=Shakeology?locale=en_GB";

    }

    public static String getBODDeluxProductCA() {
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/ca/d/deluxe-shakeology-completion-pack-DeluxeCompletion?programId=Shakeology?locale=en_CA";

    }

    public static String getBODDeluxProductFR() {
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/fr/d/deluxe-shakeology-completion-pack-DeluxeCompletion?programId=Shakeology?locale=fr_FR";

    }
    public static String getLetsBODDeluxProduct() {
      //https://www-tbbqa4.test.com/shop/us/d/deluxe-shakeology-completion-pack-DeluxeCompletion?programId=Shakeology
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/us/d/deluxe-shakeology-completion-pack-DeluxeCompletion?programId=Shakeology";
    }

    public static String getBODMegaProduct() {
      return "https://www-tbb" + getEnv()
          + ".qentelli.com/shop/us/d/annual-qentelli-on-demand-mega-shakeology-challenge-pack-BODMega?programId=testOnDemand";

    }

    // public static String getFreeUserSignupUS() {
    // return "https://www-tbb" + getEnv()
    // + ".qentelli.com/shop/us/enrollment";
    //
    // }
}
