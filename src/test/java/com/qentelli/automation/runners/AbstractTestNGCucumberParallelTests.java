package com.qentelli.automation.runners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qentelli.automation.libraries.ExamplesBuilder;
import com.qentelli.automation.singletons.RuntimeSingleton;


import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import gherkin.events.PickleEvent;
import gherkin.pickles.PickleTag;


public abstract class AbstractTestNGCucumberParallelTests extends AbstractTestNGCucumberTests implements ITest {
	private ThreadLocal<String> testName = new ThreadLocal<>();
	static Logger logger = LogManager.getLogger(AbstractTestNGCucumberParallelTests.class);
	private static long duration;
	private String featureName;
	ITestContext context;

	@BeforeMethod
	public void BeforeMethod(Method method, Object[] testData) {
		testName.set(method.getName() + "_" + testData[0]);
		featureName = testData[0].toString();
		// System.out.println(featureName);
		testName.set(featureName);
		long id = Thread.currentThread().getId();
		System.out.println("Before test-method. Thread id is: " + id);
		;
	}

	@Override
	public String getTestName() {
		return testName.get();
	}

	@BeforeClass
	public void before(ITestContext testContext) {
		duration = System.currentTimeMillis();
		context = testContext;
	}

	@AfterClass
	public void after() {
		duration = System.currentTimeMillis() - duration;
		logger.info("AfterClass DURATION - " + duration);

	}

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		if (this.context.getCurrentXmlTest().getParameter("overrideLocale").equalsIgnoreCase("true")) {
			return super.scenarios();
		} else {
			String runningLocale = (System.getProperty("locale") != null) ? System.getProperty("locale")
					: this.context.getCurrentXmlTest().getParameter("locale");
			runningLocale = "@" + runningLocale;
			// Handling test selection based on locale tags
			List<String> allLocales = Arrays.asList(
					new String[] { "@en_US", "@es_US", "@en_CA", "@fr_CA", "@en_GB", "@fr_FR", "@none", "@ALL" });

			Object[][] allScenarios = super.scenarios();
			List<Object[]> applicableScenarios = new ArrayList();

			for (Object[] scenario : allScenarios) {
				List<String> myLocales = new ArrayList<>();
				for (PickleTag pickleTag : ((PickleEventWrapper) scenario[0]).getPickleEvent().pickle.getTags()) {
					if (allLocales.contains(pickleTag.getName())) {
						myLocales.add(pickleTag.getName());
					}
				}
				if (myLocales.size() == 0 || myLocales.contains(runningLocale)) {
					applicableScenarios.add(scenario);
				}
			}
			if (applicableScenarios.size() == 0)
				logger.info("None of the scenarios are applicable to " + runningLocale);
			return applicableScenarios.toArray(new Object[0][]);
		}
	}

	@Override
	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
		// logger.info("Pickle Thread: "+Thread.currentThread().getId());
		PickleEvent pEvent = pickleWrapper.getPickleEvent();
		context.setAttribute("featureName", pickleWrapper.getPickleEvent().pickle.getName());
		context.setAttribute("tags", pickleWrapper.getPickleEvent().pickle.getTags());
		RuntimeSingleton.getInstance().setFeatureName(pickleWrapper.getPickleEvent().pickle.getName());
		ExamplesBuilder example = new ExamplesBuilder(pEvent);
		List<PickleEvent> pEvents = example.createPickle();

		logger.info(pEvents.stream().findFirst().get().pickle.getName() + "(" + pEvents.size() + ") events! ");
		int i = 0;

		for (PickleEvent pE : pEvents) {

			logger.info("URI - " + pE.uri.toString());
			logger.info("URI - " + pE.pickle.getName());
			PickleEventWrapper yo = new ExamplesBuilder(pE);

			super.runScenario(yo, featureWrapper);
		}
		logger.printf(Level.INFO, "pickle event finished","");
	}
}