package com.qentelli.automation.exceptions.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.qentelli.automation.utilities.PageObject;
import com.qentelli.automation.utilities.TextUtils;
import com.qentelli.automation.utilities.TextUtils.ConsoleColors;

public abstract class AbstractBaseBy {

	protected static Logger logger = LogManager.getLogger(AbstractBaseBy.class);

	protected By element = null;
	List<By> elements = new ArrayList<>();
	protected WebDriver driver = null;
	protected String locatorText = null;
	protected WebElement welement = null;

	public AbstractBaseBy(PageObject objects) {
		driver = objects.getDriver();
		if (driver == null)
			throw new AutomationIssueException("the driver is null ");
	}

	public AbstractBaseBy(WebDriver objects) {
		driver = objects;
	}

	public void click() {
		if (element == null)
			throw new AutomationIssueException("the element is null ");
		webElement().click();
	}

	public void click(WebDriver dvr) {
		if (element == null)
			throw new AutomationIssueException("the element is null ");
		dvr.findElement(element).click();
	}

	public void sendKeys(CharSequence... keysToSend) {
		webElement().sendKeys(keysToSend);
	}

	public void sendKeys(String s) {
		webElement().sendKeys(s);
	}

	public void clear() {
		webElement().clear();
	}

	public WebElement webElement() {
		return driver.findElement(element);
	}

	public String getText() {
		return webElement().getText();
	}

	public void sendKeys(By b, String txt) {
		driver.findElement(b).sendKeys(txt);
	}

	public List<WebElement> getWebElements() {
		return driver.findElements(element);
	}

	public By by() {
		return element;
	}

	public String getLocatorText() {
		return locatorText;
	}

	public void select(String txt) {
		Select dropDown = new Select(webElement());
		dropDown.selectByVisibleText(txt);
	}

	public void selectByValue(String txt) {
		Select dropDown = new Select(webElement());
		dropDown.selectByValue(txt);
	}

	public void selectByIndex(int index) {
		Select dropDown = new Select(webElement());
		dropDown.selectByIndex(index);
	}

	public WebElement getFirstSelectedOption() {
		Select dropDown = new Select(webElement());
		return dropDown.getFirstSelectedOption();
	}

	public void selectWild(String txt) {
		Select dropDown = new Select(webElement());
		/// dropDown.selectByVisibleText(txt);
		for (int i = 0; i < dropDown.getOptions().size(); i++) {
			if (dropDown.getOptions().get(i).getText().contains(txt))
				dropDown.selectByIndex(i);
		}
	}

	public static void selectWild(WebElement e, String txt) {
		Select dropDown = new Select(e);
		/// dropDown.selectByVisibleText(txt);
		for (int i = 0; i < dropDown.getOptions().size(); i++) {
			if (dropDown.getOptions().get(i).getText().contains(txt))
				dropDown.selectByIndex(i);
		}
	}

	public void printOptions() {
		Select dropDown = new Select(webElement());
		/// dropDown.selectByVisibleText(txt);
		logger.info("name:\t" + dropDown.getFirstSelectedOption());
		for (int i = 0; i < dropDown.getOptions().size(); i++) {
			logger.info("item:" + dropDown.getOptions().get(i).getText());
		}

	}

	static public void printWebOptions(WebElement e) {
		Select dropDown = new Select(e);
		/// dropDown.selectByVisibleText(txt);
		logger.info("name:\t" + dropDown.getFirstSelectedOption());
		for (int i = 0; i < dropDown.getOptions().size(); i++) {
			logger.info("item:" + dropDown.getOptions().get(i).getText());
		}
	}

	public Boolean exists(Boolean silent) {
		try {
			WebElement e = webElement() ;
			if (e != null) {
				if (e.isDisplayed()) {
					if (e.isEnabled()) {
						logger.info(TextUtils.center(" <exists> "));
						return true;
					}
				}
			}

		} catch (Exception e) {
			if (silent)
				e.printStackTrace();
			logger.info(TextUtils.center(" < " + "<>" + " > "));
		}

		return false;
	}

	public void scrollToElement() {
		Actions actions = new Actions(driver);

		try {
			actions.moveToElement(webElement());
			actions.perform();
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info("-------------------------");
		}

	}

	public Boolean waitUntil(int... seconds) {
		int i = 0;
		int limit = 1;
		if (seconds != null)
			limit = seconds[0];

		while (!exists(false) && i++ < limit) {
			logger.info("loop <" + i + ">");
		}
		Boolean b = false; // exists(true);
		String s = b ? ConsoleColors.GREEN_BACKGROUND + b.toString() + ConsoleColors.RESET
				: ConsoleColors.WHITE_UNDERLINED + b + ConsoleColors.RESET;
		logger.info("element @ " + s + " waited <" + i + "> secs. ");
		return exists(true);
	}

	public Boolean exists(int seconds) {
		logger.info("wait limit " + seconds);
		if (seconds == 0)
			exists(true);
		for (int i = 0; i < seconds; i++) {
			if (exists(false))
				return true;
		}
		return false;
	}

	public Boolean exists() {
		return exists(true);
	}

	public void moveTo() {
		Actions action = new Actions(driver);
		action.moveToElement(webElement());
		action.build().perform();
	}

	public void checkLoad() {
		logger.info(String.format("%-6s|%-20s|%-15s", ConsoleColors.GREEN + "LOADED" + ConsoleColors.RESET,
				getClass().getSuperclass().getSimpleName(),
				"<" + ConsoleColors.PURPLE_BOLD + getClass().getSimpleName() + ConsoleColors.RESET + ">"));

	}

	public boolean checkElement() {
		String status = element == null ? ConsoleColors.RED + "FAILED" + ConsoleColors.RESET
				: ConsoleColors.GREEN + "OK" + ConsoleColors.RESET;
		logger.info(String.format("%-6s|%-20s|%-15s", status, getClass().getSuperclass().getSimpleName(),
				"<" + ConsoleColors.PURPLE_BOLD + getClass().getSimpleName() + ConsoleColors.RESET + ">"));

		return element != null;
	}

	public boolean isEnabled() {
		return webElement().isEnabled();
	}

	public boolean isEnabled(int wait) {
		exists(wait);
		return webElement().isEnabled();
	}

	public boolean isSelected() {
		return webElement().isSelected();
	}

	public Rectangle getRect() {
		return webElement().getRect();
	}

	public void actionClick() {
		Actions action = new Actions(driver);

		action.click(webElement()).perform();

	}

	public void jClick() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", webElement());
	}

	public void jScrollTo() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", webElement());
	}

	public void setElement(By b) {
		element= b ; 
		webElement() ; 
	}
	
	public void hover() {
			logger.info("Firing mouse hover event using actions");
			// WebElement webElement = getVisibleElement(element);
			Actions act = new Actions(driver);
			act.moveToElement(webElement() ).perform();
	}
}
