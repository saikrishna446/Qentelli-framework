package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class COOTestDataObject extends AbstractApplicationsTestdataResourceBundle {
	protected static Logger logger = LogManager.getLogger(COOTestDataObject.class);
	private final static String NAME = "COO";
	public String password;
	public String s3bucket;
	public String username;
	public String mySqlPassword ; 
	public String mySqlDatabase ;  		
	public String customerOrderNumberOfShakeology;
	public String customerOrderOfShakeologyEmail;
	public String emptyReportMessage;
	public String fileDownloadedString;
	public String textMyBio;
	public String textMyCoachBio;
	public String textYourInspiration;
	public String yourFavBodTrainer;
	public String yourFavShakeologyFlavor;
	public String yourFavThingBeingTbbCoach;
	public String language;
	public String showingGoOfMessage;
	public String myEFTUserEmailID;
	public String impersonatorAccount;
	public String impersonatorAccount2;
	public String cooCoachWithGenealogy;
	public String cooCoachWithOrders;
	public String cooCoachWithMyWebsites;

	//My Customers order tab
	public String cooTspMyCustomersOrderDate;
	public String cooTspMyCustomersOrderId;
	public String cooTspMyCustomersOrderNumber;
	public String cooTspMyCustomersOrderName;
	public String cooTspMyCustomersOrderSubscription;
	public String cooTspMyCustomersOrderEmail;
	public String cooTspMyCustomersOrderAmount;
	public String cooTspMyCustomersOrderPhone;
	public String cooTspMyCustomersOrderStatus;
	public String cooTspMyCustomersOrderChildItems;
	public String cooTspMyCustomersOrderChildItemsInvoice;

// My PC order tab
public String cooTspMyPCOrderDate;
	public String cooTspMyPCOrderId;
	public String cooTspMyPCOrderNumber;
	public String cooTspMyPCOrderName;
	public String cooTspMyPCOrderSubscription;
	public String cooTspMyPCOrderEmail;
	public String cooTspMyPCOrderAmount;
	public String cooTspMyPCOrderPhone;
	public String cooTspMyPCOrderStatus;

	// My Coaches tab
	public String cooTspMyCoachesOrderDate;
	public String cooTspMyCoachesOrderNumber;
	public String cooTspMyCoachesOrderName;
	public String cooTspMyCoachesOrderId;
	public String cooTspMyCoachesOrderSubscription;
	public String cooTspMyCoachesOrderEmail;
	public String cooTspMyCoachesOrderAmount;
	public String cooTspMyCoachesOrderPhone;
	public String cooTspMyCoachesOrderStatus;
	public String cooTspMyCoachesOrderChildItems;
	public String cooTspMyCoachesOrderChildItemsInvoice;

	// My Personal tab
	public String cooTspMyPersonalOrderNumber;
	public String cooTspMyPersonalOrderName;
	public String cooTspMyPersonalOrderId;
	public String cooTspMyPersonalOrderDate;
	public String cooTspMyPersonalOrderSubscription;
	public String cooTspMyPersonalOrderEmail;
	public String cooTspMyPersonalOrderAmount;
	public String cooTspMyPersonalOrderPhone;
	public String cooTspMyPersonalOrderStatus;
	public String cooTspMyPersonalOrderChildItems;

	public String expectedHeaders;
	public String expectedHeadersStage;
	public String videoFileName;
	public String coachMysiteDefaultImageSrc;
	public String updatedTeamName;
	public String myCoachBioText;
	public String phoneCountryCode;
	public String phoneNumber;
	public String teamName;
	public String blogWebsiteUrl;
	public String preferredPlacementSelectPlacementDropdownValues;
	public String preferredPlacementSelectPositionDropdownValues;
	public String initialSponsorshipHeaderValues;
	public String rankAuditViewHeaderValues;
	public String downlineCoachViewHeaderValues;

	public COOTestDataObject() {
		super(NAME);
		password = getResString("PASSWORD");
		s3bucket = getResString("BUCKET");
		username = getResString("USERNAME");
		mySqlPassword = getResString("COMYSQLDBPWD");
		mySqlDatabase= 		getResString("COOMYSQLDB");
		customerOrderNumberOfShakeology = getResString("CUSTOMERORDERNUMBEROFSHAKEOLOGY");
		customerOrderOfShakeologyEmail = getResString("CUSTOMERORDEROFSHAKEOLOGYEMAIL");
		emptyReportMessage = getResString("EMPTYREPORTMESSAGE");
		fileDownloadedString = getResString("FILEDOWNLOADEDSTRING");
		textMyBio = getResString("TEXTMYBIO");
		textMyCoachBio = getResString("TEXTMYCOACHBIO");
		textYourInspiration = getResString("TEXTYOURINSPIRATION");
		yourFavBodTrainer = getResString("YOURFAVBODTRAINER");
		yourFavShakeologyFlavor = getResString("YOURFAVSHAKEOLOGYFLAVOR");
		yourFavThingBeingTbbCoach = getResString("YOURFAVTHINGBEINGTBBCOACH");
		language = getResString("LANGUAGE");
		showingGoOfMessage = getResString("SHOWINGOXOFMESSAGE");
		myEFTUserEmailID = getResString("MYEFTUSEREMAILID");
		impersonatorAccount = getResString("IMPERSONATORACCOUNT");
		impersonatorAccount2 = getResString("IMPERSONATORACCOUNT2");
		cooCoachWithGenealogy = getResString("COOCOACHWITHGENEALOGY");
		cooCoachWithOrders = getResString("COOCOACHWITHORDERS");
		cooCoachWithMyWebsites = getResString("COACHWITHMYWEBSITES");

		// All TSP related test data definitions
		cooTspMyCustomersOrderDate = getResString("COOTSPMYCUSTOMERSORDERDATE");
		cooTspMyCustomersOrderId = getResString("COOTSPMYCUSTOMERSORDERID");
		cooTspMyCustomersOrderNumber = getResString("COOTSPMYCUSTOMERSORDERNUMBER");
		cooTspMyCustomersOrderName = getResString("COOTSPMYCUSTOMERSORDERNAME");
		cooTspMyCustomersOrderSubscription = getResString("COOTSPMYCUSTOMERSORDERSUBSCRIPTION");
		cooTspMyCustomersOrderEmail = getResString("COOTSPMYCUSTOMERSORDEREMAIL");
		cooTspMyCustomersOrderAmount = getResString("COOTSPMYCUSTOMERSORDERAMOUNT");
		cooTspMyCustomersOrderPhone = getResString("COOTSPMYCUSTOMERSORDERPHONE");
		cooTspMyCustomersOrderStatus = getResString("COOTSPMYCUSTOMERSORDERSTATUS");
		cooTspMyCustomersOrderChildItems = getResString("COOTSPMYCUSTOMERSORDERCHILDITEMS");
		cooTspMyCustomersOrderChildItemsInvoice = getResString("COOTSPMYCUSTOMERSORDERCHILDITEMSINVOICE");

		// My PC customers
		cooTspMyPCOrderDate = getResString("COOTSPMYPCORDERDATE");
		cooTspMyPCOrderId = getResString("COOTSPMYPCORDERID");
		cooTspMyPCOrderNumber = getResString("COOTSPMYPCORDERNUMBER");
		cooTspMyPCOrderName = getResString("COOTSPMYPCORDERNAME");
		cooTspMyPCOrderSubscription = getResString("COOTSPMYPCORDERSUBSCRIPTION");
		cooTspMyPCOrderEmail = getResString("COOTSPMYPCORDEREMAIL");
		cooTspMyPCOrderAmount = getResString("COOTSPMYPCORDERAMOUNT");
		cooTspMyPCOrderPhone = getResString("COOTSPMYPCORDERPHONE");
		cooTspMyPCOrderStatus = getResString("COOTSPMYPCORDERSTATUS");

		// My Coaches
		cooTspMyCoachesOrderDate = getResString("COOTSPMYCOACHESORDERDATE");
		cooTspMyCoachesOrderId = getResString("COOTSPMYCOACHESORDERID");
		cooTspMyCoachesOrderNumber = getResString("COOTSPMYCOACHESORDERNUMBER");
		cooTspMyCoachesOrderName = getResString("COOTSPMYCOACHESORDERNAME");
		cooTspMyCoachesOrderSubscription = getResString("COOTSPMYCOACHESORDERSUBSCRIPTION");
		cooTspMyCoachesOrderEmail = getResString("COOTSPMYCOACHESORDEREMAIL");
		cooTspMyCoachesOrderAmount = getResString("COOTSPMYCOACHESORDERAMOUNT");
		cooTspMyCoachesOrderPhone = getResString("COOTSPMYCOACHESORDERPHONE");
		cooTspMyCoachesOrderStatus = getResString("COOTSPMYCOACHESORDERSTATUS");
		cooTspMyCoachesOrderChildItems = getResString("COOTSPMYCOACHESORDERCHILDITEMS");
		//cooTspMyCoachesOrderChildItemsInvoice = getResString("COOTSPMYCOACHESORDERCHILDITEMSINVOICE");

		// My Orders
		cooTspMyPersonalOrderDate = getResString("COOTSPMYPERSONALORDERDATE");
		cooTspMyPersonalOrderId = getResString("COOTSPMYPERSONALORDERID");
		cooTspMyPersonalOrderNumber = getResString("COOTSPMYPERSONALORDERNUMBER");
		cooTspMyPersonalOrderName = getResString("COOTSPMYPERSONALORDERNAME");
		cooTspMyPersonalOrderSubscription = getResString("COOTSPMYPERSONALORDERSUBSCRIPTION");
		cooTspMyPersonalOrderEmail = getResString("COOTSPMYPERSONALORDEREMAIL");
		cooTspMyPersonalOrderAmount = getResString("COOTSPMYPERSONALORDERAMOUNT");
		cooTspMyPersonalOrderPhone = getResString("COOTSPMYPERSONALORDERPHONE");
		cooTspMyPersonalOrderStatus = getResString("COOTSPMYPERSONALORDERSTATUS");
		cooTspMyPersonalOrderChildItems = getResString("COOTSPMYPERSONALORDERCHILDITEMS");
		// End of All TSP related test data definitions

		expectedHeaders = getResString("EXPECTEDHEADERS");
		expectedHeadersStage = getResString("EXPECTEDHEADERSSTAGE");
		videoFileName = getResString("VIDEOFILENAME");
		coachMysiteDefaultImageSrc = getResString("COACHMYSITEDEFAULTIMAGESRC");
		updatedTeamName = getResString("UPDATEDTEAMNAME");
		myCoachBioText = getResString("MYCOACHBIOTEXT");
		phoneCountryCode = getResString("PHONECOUNTRYCODE");
		phoneNumber = getResString("PHONENUMBER");
		teamName = getResString("TEAMNAME");
		blogWebsiteUrl = getResString("BLOGWEBSITEURL");
		preferredPlacementSelectPlacementDropdownValues = getResString("PREFERREDPLACEMENTSELECTPLACEMENTDROPDOWNVALUES");
		preferredPlacementSelectPositionDropdownValues = getResString("PREFERREDPLACEMENTSELECTPOSITIONDROPDOWNVALUES");
		initialSponsorshipHeaderValues = getResString("INITIALSPONSORSHIPHEADERVALUES");
		rankAuditViewHeaderValues = getResString("RANKAUDITVIEWHEADERVALUES");
		downlineCoachViewHeaderValues = getResString("DOWNLINECOACHVIEWHEADERVALUES");
	}

	public String getValue(String key){
		return getResString(key);
	}
}
