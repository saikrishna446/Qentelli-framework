package com.qentelli.automation.utilities;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.apache.commons.lang.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.TextUtils.ConsoleColors;

import edu.emory.mathcs.backport.java.util.Arrays;

public class AbstractResourceBundle extends ResourceBundle {
	protected static Logger logger = LogManager.getLogger(AbstractResourceBundle.class);

	public static final String[] APPS = { "TBB", "COO", "HCE2E", "BYD", "EBS", "test", "CTR", "E2E", "SHAC",
			"BAMI", "MYX", "HARMONY", "BOD", "BB" };
	// ----------------------
	// do not make root static
	String root = "objects.";
	// ----------------------
	protected ResourceBundle bundle = null;
	protected String env;
	protected Boolean checkOtherApps = true;
    // private final String basename = "objects.";
	private String bundle_name;

	public AbstractResourceBundle(String appName, String s) {
		env = getEnv();
		s = root + appName + s;
		logger.info(TextUtils.logDiamonds(s, ConsoleColors.CYAN));
		setBundle(s);
		if (bundle == null)
			bundle = ResourceBundle.getBundle(s, getScenarioLocale());
		root = s;
		checkOtherApps = false;
	}

	public AbstractResourceBundle(String s) {
		env = getEnv();
		logger.info(TextUtils.logDiamonds(s, ConsoleColors.PURPLE));

		s = !s.contains(env) ? root + s + "." + env : root + s;

		setBundle(s);
		root = s;

	}

	@Override
	protected Object handleGetObject(String s) {
		return s;
	}

	@Override
	public Enumeration<String> getKeys() {
		return bundle.getKeys();
	}

	protected void setBundle(String objName) {
		String checkAppsFirst = objName.substring(0, objName.lastIndexOf("."));

		while (objName.lastIndexOf(".") > 0) {
			try {
				bundle = ResourceBundle.getBundle(objName, getScenarioLocale());
				root = objName;
				// logger.info("good: " + objName + " " + bundle.getLocale());

				return;
			} catch (Exception e) {
				// logger.info("no good: " + objName);
				objName = objName.substring(0, objName.lastIndexOf("."));
				// logger.info(e.getLocalizedMessage());

			}
		}
		if (bundle == null) {
			// logger.info("check apps first bundle in applications." + checkAppsFirst);
			try {
				bundle = ResourceBundle.getBundle(checkAppsFirst, getScenarioLocale());
				root = checkAppsFirst;
				// logger.info("good: " + checkAppsFirst + "@" + bundle.getLocale());
				bundle_name = root;
				return;
			} catch (Exception e) {
				// e.printStackTrace();
				// logger.info(e.getLocalizedMessage());
			}
			if (checkOtherApps) {
				// logger.info("Searching in non-envirornment bundle");
				if (checkNonEnv(checkAppsFirst)) {
					logger.info("bundle found exit");
					bundle_name = root;
					return;
				}
				// logger.info("no bundle found :(");
				// it would be ok to make this != 0 but I dont want to check the obj folder
				// logger.info("Searching in other applications");

				if (checkOtherApplications(checkAppsFirst)) {
					// logger.info("------->Searching for bundle in applications.");
					root = root.substring(0, root.lastIndexOf("."));
					bundle_name = root;
					return;
				}
				// it would be ok to make this != 0 but I dont want to check the obj folder
				// logger.info(ConsoleColors.BLUE_UNDERLINED + "ResourceBundle - not found <" +
				// objName
				// + ">" + ConsoleColors.RESET);

			}
		}
		bundle_name = root;

	}

	private boolean checkNonEnv(String checkAppsFirst) {
		try {
			checkAppsFirst = checkAppsFirst.replaceAll(env, "");
			checkAppsFirst = checkAppsFirst.replaceAll("\\.\\.", ".");
			if (checkAppsFirst.endsWith(".")) {
				checkAppsFirst = checkAppsFirst.substring(0, checkAppsFirst.length() - 1);
			}
			// logger.info("checkFrist<>" + checkAppsFirst);

			bundle = ResourceBundle.getBundle(checkAppsFirst, getScenarioLocale());
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return false;
	}

	public static String getEnv() {
		String env = null;
		env = System.getProperty("environment");
		if (env == null)
			env = System.getProperty("ENVIROMENT");
		if (env == null)
			env = System.getProperty("ENV");
		if (env == null)
			env = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("environment");
		if (env == null) {
			logger.info("the env parameter is null; check runtime params");
			throw new AutomationIssueException("No env found ... ");
		}
		return env;
	}

	public static Locale getScenarioLocale() {
		String l = null;
		l = System.getProperty("locale");
		if (l == null)
			l = System.getProperty("LOCALE");
		if (l == null)
			l = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("locale");
		if (l == null) {
			logger.info("the locale parameter is null; check runtime params");
			throw new AutomationIssueException("No locale found ... ");
		}
		return LocaleUtils.toLocale(l);
	}

	public String getRoot() {
		return root;
	}

	protected String getBaseName() {
		return bundle_name;
	}

	protected static String getApplicationName() {
		for (String s : RuntimeSingleton.getInstance().getTags()) {
			s = s.replace("@", "");
			if (Arrays.asList(APPS).contains(s))
				return s;
		}
		throw new AutomationIssueException(
				"application not found in resource bundle; check abstract customer resource");
	}

	public Boolean checkOtherApplications(String s) {
		String appName = null;

		for (String s1 : APPS) {
			if (s.contains(s1)) {
				appName = s1;
				if (s.contains("HCE2E"))
					appName = "HCE2E";
			}
		}
		// logger.info("app name:\t" + appName);

		for (String s1 : APPS) {
			// logger.info("!checking resource bundle in " + appName);
			String left = s.substring(0, s.indexOf(".applications.")) + ".applications.";
			String right = s.substring(s.indexOf("applications."), s.length()).replace("applications.", "");
			right = right.substring(right.indexOf("."), right.length());
            String newBase = left + s1 + right; // s.replace(appName, s1);
			// logger.info("!!checking resource bundle in " + newBase);
			// logger.info("checking resource bundle in " + s1);
			try {
				// logger.info("bundle check@" + newBase + "!");

				bundle = ResourceBundle.getBundle(newBase, getScenarioLocale());
				// logger.info("bundle found@" + s1 + "!");

				root = newBase;
				bundle_name = root;
				// props.writeBundle(root);

				return true;
			} catch (Exception e) {
				// e.printStackTrace();
			}

		}
		if (appName == null) {
			throw new AutomationIssueException("checkOtherApplications didn't find any resource ");
		}
		return false;

	}

	public ResourceBundle globalBundle() {
		if (root.contains(env)) { // global root
			String groot = root.replaceAll(env, "");
			groot = groot.replace("..", ".");
			// logger.info("global resource:\t" + groot);
			root = groot;
			bundle = ResourceBundle.getBundle(groot, getScenarioLocale());
			bundle_name = groot;
		} else {
			bundle = ResourceBundle.getBundle(root, getScenarioLocale());
			bundle_name = root;
		}
		return bundle;
	}

	public String trygetString(String s) {
		try {
			return getString(s);
		} catch (Exception e) {
			return null;
		}
	}

	static <T> T withNoException(Supplier<? extends T> supplier, T defaultValue) {
		try {
			return supplier.get();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getResString(String s) {
		String v = null;
		try {
			if (!root.contains(getEnv())) {

				String type = root.substring(root.lastIndexOf(".") + 1);
				root = root.replace(type + "." + type, type + "." + env + "." + type);
				// logger.info("checking env first @" + root);
				bundle = ResourceBundle.getBundle(root, getScenarioLocale());
				bundle_name = root;
			}
			setBundle(root);
//				logger.info("bundle:" + root);
			v = bundle.getString(s);

		} catch (Exception e) {
//				logger.info("env resource:\t" + e.getMessage());
		}

		if (v == null) {
			// logger.info(">>>>>>> looking for global property");
			globalBundle();
			v = bundle.getString(s);
		}

		return v;
	}

}
