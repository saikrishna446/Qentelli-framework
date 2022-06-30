package com.qentelli.automation.testdata;

public class ShopPackItem {
	public enum Package {
		testOnDemand
    }

	public enum testOnDemandOptions {
		Annual
    }

	public ShakeOptionsData shake = new ShakeOptionsData();
	public Package pack;
	public Enum<?> opt;
}
