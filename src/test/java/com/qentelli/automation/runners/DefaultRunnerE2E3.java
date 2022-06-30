package com.qentelli.automation.runners;

import cucumber.api.CucumberOptions;
import org.apache.logging.log4j.core.config.Configurator;

@CucumberOptions(glue = { "com.qentelli.automation.stepdefs", "com.qentelli.automation.hookse2e" }, plugin = {
		"json:target/json-cucumber-reports/cukejson.json", "testng:target/testng-cucumber-reports/cuketestng.xml",
		"html:target/cucumber", "html:target/reports/htmlreport", "json:target/cucumber1.json",
		"html:target/site/cucumber-pretty" },tags={"@en_GB"}, features = "src/test/resources/features/healthcheck/HCE2EALL")

public class DefaultRunnerE2E3 extends AbstractTestNGCucumberParallelTests {
	static {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
		Configurator.initialize(null, "log4j2.xml");
	}
}
