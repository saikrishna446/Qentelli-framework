package com.qentelli.automation.enums;

import com.qentelli.automation.exceptions.base.AutomationIssueException;

public enum ShacRecipientType {
	BODCustomer("BOD Customer"), RegularCustomer("Regular Customer"), BODCoach("Coach with BOD"),
	RegularCoach("Coach without BOD");

	private String text;

	ShacRecipientType(String text) {
		this.text = text;
	}

	// Get String value for enum
	@Override
	public String toString() {
		return this.text;
	}

	// Get enum from String value
	public static ShacRecipientType fromString(String text) {
		for (ShacRecipientType user : ShacRecipientType.values()) {
			if (user.toString().equalsIgnoreCase(text)) {
				return user;
			}
		}
		// no matches found
		throw new AutomationIssueException("No valid user type found for ShacRecipientType: " + text);
	}
}
