package com.qentelli.automation.testdata;

public class ShopCartLoginData {
	public String username;
	public String password;
	public Boolean showPwd;

	public ShopCartLoginData(String e, String p) {
		username = e;
		password = p;
		showPwd = false;
	}
}
