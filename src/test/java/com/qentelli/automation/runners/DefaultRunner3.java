package com.qentelli.automation.runners;

import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import cucumber.api.CucumberOptions;

@CucumberOptions(glue = { "com.qentelli.automation.stepdefs", "com.qentelli.automation.hooks" }, plugin = {
		"json:target/json-cucumber-reports/cukejson.json", "testng:target/testng-cucumber-reports/cuketestng.xml",
		"html:target/cucumber", "html:target/reports/htmlreport", "json:target/cucumber1.json",
    "html:target/site/cucumber-pretty"}, features = "src/test/resources/features")

public class DefaultRunner3 extends AbstractTestNGCucumberParallelTests {
	static {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
		Configurator.initialize(null, "log4j2.xml");
	}

	@BeforeMethod
	public void beforeMethod() {
//		long id = Thread.currentThread().getId();
//		System.out.println("Before test-method. Thread id is: " + id);
//		System.out.println(Thread.currentThread().getName());

	}

	@AfterMethod
	public void afterMethod() {
		long id = Thread.currentThread().getId();
	}



}