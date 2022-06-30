package com.qentelli.automation.utilities;

import org.openqa.selenium.By;

public class EndpointObject extends AbstractApplicationsResourceBundle {
	By element;
	String item;
	String className = null;
	String home = null;

	public EndpointObject() {
		super("endpoints." + getEnv() + "." + "endpoints");
		System.out.println(bundle.getLocale() + " page out " + root);
		home = bundle.getString("TBBURL");
		System.out.println("home " + home);

	}

	public String getTBB() {
		return bundle.getString("TBBURL");
	}

}
