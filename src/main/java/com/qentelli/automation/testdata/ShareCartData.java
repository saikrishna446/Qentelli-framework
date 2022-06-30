package com.qentelli.automation.testdata;

import com.qentelli.automation.utilities.CommonUtilities;

public class ShareCartData {
	public String firstname;
	public String lastname;
	public String email;
	public Boolean bCoach;
	public Boolean bTermsConditions;
	public String coachId;

	public ShareCartData() {
		firstname = CommonUtilities.randomString(6);
		lastname = CommonUtilities.randomString(3);
		email = firstname + "@yopmail.com";
		bTermsConditions = true;
	}
}
