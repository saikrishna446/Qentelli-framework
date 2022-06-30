package com.qentelli.automation.singletons;

import java.util.ArrayList;

import com.qentelli.automation.utilities.TextUtils;

public class SetTestResultData extends TestResultData {
	public int total = 0;
	public int failed = 0;
	public int passed = 0;
	public int skipped = 0;
	public int undefined = 0;

	public String logLink = null;
	public ArrayList<String> errors = new ArrayList<String>();

	public SetTestResultData(String id, String b) {
		super(id);

		logLink = "<a href=\"http://automation.test.com:4000/d/u9GK41aWk/log-view?orgId=1&var-run=" + rid
				+ "\" target=_new>log</a>";
		bucket = b;
	}


	public void printSet() {
		logger.info("RunId@ " + rid);

		logger.info(TextUtils.center("-- set --"));

// 		cleanData();
		// logger.info(TextUtils.center(name));
		logger.info(TextUtils.format("runtime", rid));
		logger.info(TextUtils.center("----- <> <> <> ------"));

		logger.info(TextUtils.format("Start", start));
		logger.info(TextUtils.format("End", end));
		logger.info(TextUtils.format("duration", duration));

		logger.info(TextUtils.format("Application", application));
        logger.info(TextUtils.format("Project", project));
		logger.info(TextUtils.format("Set", suite));
		logger.info(TextUtils.format("env", env));
		logger.info(TextUtils.format("locale", locale));

		logger.info(TextUtils.format("browser", browser));

		logger.info(TextUtils.format("Total", total));
		logger.info(TextUtils.format("Passed", passed));
		logger.info(TextUtils.format("Failed", failed));
		logger.info(TextUtils.format("Skipped", skipped));
		logger.info(TextUtils.format("Undefined", undefined));
		logger.info(TextUtils.format("log", logLink));
		logger.info(TextUtils.format("bucket", bucket));

	}

	public void syncTagData() {


		if (!(suite.equals("CriticalRegression") || suite.equals("Smoke") || suite.equals("FullRegression")
				|| suite.equals("Mobile") || suite.equals("E2E") || suite.equals("Sanity"))) {
			logger.warn(
					"the application / suite (set)  isn't to spec; hardcording your run in Grafana to something valid");
			logger.warn(application + "|" + suite + "NOW>>> test Smoke!");
			suite = "Smoke";
		}

		if (RuntimeSingleton.getInstance().isMobile == true) {
			suite = "Mobile";
		}
	}
}
