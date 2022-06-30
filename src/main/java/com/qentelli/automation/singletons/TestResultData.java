package com.qentelli.automation.singletons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.common.Constants;
import com.qentelli.automation.utilities.TextUtils;
import cucumber.api.Result;

public class TestResultData {
	Logger logger = LogManager.getLogger(TestResultData.class);
	public long start = 0;
	public long end = 0;
	public long duration;
	public String name = "undefined";
	public Result.Type result;
	public String rid;
	public Constants.BROWSER browser;
	public String env;
	public String suite = "unknown";
	public String testrail = "n/a";
	public String testrailLink = "n/a";
	public String locale = null;

	public String project = "Ops";

	public boolean localeSpecific = false;
	public List<String> taggedLocales = new ArrayList<>();
	public String bucket = "Test";

	public String application = "test";
	public List<String> tags = new ArrayList<String>();

	public TestResultData() {
		start = System.currentTimeMillis();
		if (System.getProperty("DATABASE") != null) {
			System.setProperty("Database", System.getProperty("DATABASE"));
		}
		if (System.getProperty("database") != null) {
			if (System.getProperty("database").toUpperCase().equals("PROD")
					|| System.getProperty("database").toUpperCase().equals("PRODUCTION")) {
				bucket = "Production";

			}
			if (System.getProperty("database").toUpperCase().equals("STABILITY")) {
				bucket = "Stability";

			}
			RuntimeSingleton.getInstance().bucket = bucket;
		}

		if (System.getProperty("ENV") != null) {
			env = System.getProperty("ENV");

		}
		if (System.getProperty("browser") != null) {
			browser = Constants.BROWSER.valueOf(System.getProperty("browser").toUpperCase());

		}
	}

	public TestResultData(String id) {
		start = System.currentTimeMillis();
		rid = id;
	}

	public void setupTagFields() {
		bucket = RuntimeSingleton.getInstance().bucket;
		String testrailPattern = "[C|T]\\d{7,}(_S\\d+)?";
		String hcTestrailPattern = "HC\\d{4}";// HealthCheck ID pattern
		for (String s : tags) {

			if (s.startsWith("@")) {
				s = s.replace("@", "");
			}
			if (s.startsWith("ATG0") || s.startsWith("SAB0") || s.startsWith("EBS0")) {
				testrail = s;
				continue;
			}
			if ((s.startsWith("T") || s.startsWith("C") || s.startsWith("HC")) && s.length() >= 4
					&& s.matches("^.+?\\d$")) {
				if (testrail.equalsIgnoreCase("n/a")) {
					testrail = s;
				} else {
					if (s.matches("[C|T]\\d{7,}(_S\\d+)")) {
						testrail = s;
					}
				}

			} else if (s.toLowerCase().contains("en_us") || s.toLowerCase().contains("es_us")
					|| s.toLowerCase().contains("en_gb") || s.toLowerCase().contains("en_ca")
					|| s.toLowerCase().contains("fr_ca") || s.toLowerCase().contains("fr_fr")) {
				taggedLocales.add(s);
			} else if (s.contains("Smoke") || s.contains("CriticalRegression") || s.contains("FullRegression")
					|| s.contains("Frontend") || application.contains("E2E") || s.contains("Sanity")) {
				suite = s;
			} else {
				cleanApplicationTag(s);
			}
		}
		if (application.contains("E2E")) {
			suite = "E2E";
		}
		// the application is set in the execution listener onStartMethod ;
		// this is done because multiple application tags might be found in features
		// this forces the application from the command line over tag defs
		if (Objects.isNull(RuntimeSingleton.getInstance().setData.application)) {
			RuntimeSingleton.getInstance().setData.application = application;
		}
		if (System.getProperty("database") != null) {
			if (System.getProperty("database").toLowerCase().equals("PROD")
					|| System.getProperty("database").toLowerCase().equals("Production")) {
				bucket = "Production";
			}
			if (System.getProperty("database").toUpperCase().equals("STABILITY")) {
				bucket = "Stability";
			}
		}
		if (System.getProperty("ENV") != null) {
			env = System.getProperty("ENV");
		}
		if (System.getProperty("browser") != null) {
			browser = Constants.BROWSER.valueOf(System.getProperty("browser").toUpperCase());
		}
		logger.info("Your project is: " + project);
	}

	protected void cleanData() {
		if (testrail.startsWith("@")) {

			testrail = testrail.substring(1);
		}

		if (testrail.startsWith("T")) {
			testrailLink = "<a href=http://test.testrail.net/index.php?/tests/view/" + testrail.substring(1)
					+ " target=\"_blank\">" + testrail + "</a>";
		} else if (testrail.startsWith("C")) {
			testrailLink = "<a href=http://test.testrail.net/index.php?/cases/view/" + testrail.substring(1)
					+ " target=\"_blank\">" + testrail + "</a>";
		} else if (testrail.startsWith("HC")) {
			testrailLink = " <p>" + testrail + "</p> ";
		}
		if (application.startsWith("@")) {
			application = application.substring(1);
		}
		if (suite.startsWith("@")) {
			suite = suite.substring(1);
		}

	}

	@Override
	public String toString() {

		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(TextUtils.format(field.getName(), field.get(this)));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

	public void addRuntimeDetails(ScenarioTestResultData d) {
		if (System.getProperty("ENV") != null) {
			env = System.getProperty("ENV");

		}
		if (System.getProperty("browser") != null) {
			browser = Constants.BROWSER.valueOf(System.getProperty("browser").toUpperCase());

		}
		suite = d.suite;
		locale = d.locale;
		project = d.project;
		application = d.application;
		rid = d.rid;
		bucket = d.bucket;

	}

	public void checkBrowser() {
		if (browser == null) {
			logger.warn("Browser value not set. Using chrome as default..");
			browser = Constants.BROWSER.CHROME;
		}
	}

	public void cleanApplicationTag(String s) {
		// this is for reporting do not comment out -snelson
		if (System.getProperty("ENV") != null) {
			env = System.getProperty("ENV");

		}
		if (System.getProperty("browser") != null) {
			browser = Constants.BROWSER.valueOf(System.getProperty("browser").toUpperCase());

		}
		if (System.getProperty("application") != null) {
			application = System.getProperty("application");
		} else {
			List<String> projects = new ArrayList<String>();

			projects.add("PreferredCustomer");
			projects.add("BundleUX");
			projects.add("Unification");
			projects.add("UniOct");
			projects.add("BODI");

			if (projects.contains(s)) {
				project = s;
				RuntimeSingleton.getInstance().setData.project = s;
			}

			if (application.equals("test")) {
				List<String> apps = new ArrayList<String>();

				apps.add("TBB");
				apps.add("BYD");
				apps.add("COO");
				apps.add("SSE");
				apps.add("E2E");
				apps.add("CTR");
				apps.add("HCE2E");
				apps.add("SHAC");
				apps.add("BAMI");
				apps.add("BB");
				apps.add("BOD");

				if (apps.contains(s.toUpperCase())) {
					application = s.toUpperCase();
					logger.info("Your application is: " + application);
				} else {
					String allTags = String.join(",", tags);
					if (allTags.toLowerCase().contains("@shac")) {
						application = "SHAC";
					}
				}

				if (application.equals("S3"))
					application = "COO";
			}
		}
	}

	public void makeSureDataBaseIsSetCorrectly() {

		if (RuntimeSingleton.getInstance().bucket != null) {
			bucket = RuntimeSingleton.getInstance().bucket;
		} else {
			if (System.getProperty("database") != null) {
				if (System.getProperty("database").equalsIgnoreCase("PROD")
						|| System.getProperty("database").equalsIgnoreCase("Production")) {
					bucket = "Production";

				}
				if (System.getProperty("database").equalsIgnoreCase("STABILITY")
						|| System.getProperty("database").equalsIgnoreCase("Stab")) {
					bucket = "Stability";
				}
			} else {
				bucket = "Test";
			}

		}

		if (bucket.equalsIgnoreCase("Stability") || bucket.equalsIgnoreCase("Test")) {
			bucket = "Test";
		}
	}

	public void makeSureLocaleIsSetCorrectly() {
		if (System.getProperty("locale") != null) {
			locale = System.getProperty("locale");
		}
		if (!(locale.equals("en_US") || locale.equals("fr_FR") || locale.equals("en_CA") || locale.equals("fr_CA")
				|| locale.equals("en_GB") || locale.equals("es_US") || locale.equals("en_UK")
				|| locale.equals("en_FR"))) {

			locale = "none";

		}
		if (application.equals("HCE2E")) {
			locale = "none";
		}
		if (application.equals("E2E")) {
			// locale = "ALL";
			int i = name.lastIndexOf(" - ");
			if (i > 0) {
				String newlocale = name.substring(i, name.length());
				locale = newlocale.substring(3);
				logger.info("e2e locale setting\t>>>\t" + newlocale);

			}

		}

	}

	public String getPointName() {

		makeSureDataBaseIsSetCorrectly();

		// this is a reporting change requested by Damon; snelson 10/13/20
		String e2eCorrectBucket = project + "_" + application + "_" + suite + "_" + env + "_" + locale + "_" + bucket;
		if (application.contains("E2E")) {
			e2eCorrectBucket = project + "_" + application + "_" + "E2E" + "_" + env + "_ALL_" + bucket;
			if (application.equals("HCE2E")) {
				locale = "none";
				e2eCorrectBucket = project + "_" + application + "_" + suite + "_" + env + "_" + locale + "_" + bucket;

			}

		}
		return e2eCorrectBucket;
	}

}
