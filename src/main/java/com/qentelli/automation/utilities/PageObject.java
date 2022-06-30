package com.qentelli.automation.utilities;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.utilities.TextUtils.ConsoleColors;

public class PageObject extends AbstractApplicationsResourceBundle {
	protected Logger logger = LogManager.getLogger(PageObject.class);

	By element;
	String item;
	String className = null;
	WebDriver driver;
	public String xpath = null;
	public String resource_path;

	public PageObject(WebDriver drv, String simpleName) {
		super("pages" + "." + simpleName + "." + simpleName);
		driver = drv;
	}

	public By byxpath(String s) {
		try {
			resource_path = s;
			// logger.info("xpath: " + getResString(resource_path));
			xpath = getResString(resource_path); // bundle.getString(s);
		} catch (Exception e) {
			logger.info(formatLoggingString(e.getMessage()));
			e.printStackTrace();
		}

		if (xpath == null) {
			logger.info("reset bundle: " + getBaseName());
			getBaseName();
		}

		element = By.xpath(xpath);
		// while ()
		if (element == null)
			throw new AutomationIssueException("element returned from xpath is null -->" + s);
		return element;
	}

	public By bycss(String string) {
		if (string == null)
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + string);
		// String name = this.getClass().getSimpleName() + " loaded abstract xpath <";
		xpath = bundle.getString(string);

		return By.cssSelector(xpath);
	}

	public By byclassname(String string) {
		if (bundle == null) {
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + string);
		}
		// String name = this.getClass().getSimpleName() + " loaded abstract xpath <";
		xpath = bundle.getString(string);
		logger.info("CLASSNAME "+xpath);
		return By.className(xpath);
	}

	public By byid(String string) {
		if (string == null)
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + string);
		// String name = this.getClass().getSimpleName() + " loaded abstract xpath <";
		xpath = bundle.getString(string);

		return By.id(xpath);
	}

	public By bylinktext(String string) {
		if (string == null) {
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + string);
		}
		// String name = this.getClass().getSimpleName() + " loaded abstract xpath <";
		xpath = bundle.getString(string);
		// logger.info(name + xpath + "> in element");
		return By.linkText(xpath);

	}

	public By bypartiallinktext(String string) {
		if (string == null) {
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + string);
		}
		// String name = this.getClass().getSimpleName() + " loaded abstract xpath <";
		xpath = bundle.getString(string);

		return By.partialLinkText(xpath);
	}

	public By byname(String s) {
		if (s == null) {
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + s);
		}
		xpath = bundle.getString(s);

		return By.name(xpath);
	}

	public By bytagname(String string) {
		if (bundle == null) {
			throw new AutomationIssueException(
					" the string passed to base by is null; the resource might be missing in -->" + string);
		}
		// String name = this.getClass().getSimpleName() + " loaded abstract xpath <";
		xpath = bundle.getString(string);
		// logger.info(name + xpath + "> in element");

		return By.tagName(xpath);
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public WebDriver getDriver() {
		return driver;
	}

	private String formatLoggingString(String m) {
		return "" + ConsoleColors.YELLOW + "<<<" + ConsoleColors.RESET + ConsoleColors.RED_BOLD + "bad element"
				+ ConsoleColors.YELLOW + ">>>" + ConsoleColors.RESET + m;
	}

	public String xpath() {
		return xpath;
	}

}
