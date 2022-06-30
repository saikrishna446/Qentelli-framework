package com.qentelli.automation.pages;

import static com.qentelli.automation.utilities.CommonUtilities.normalizeSpecialChars;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qentelli.automation.common.Constants;
import com.qentelli.automation.common.World;
import com.qentelli.automation.drivers.Waits;
import com.qentelli.automation.exceptions.base.AppIssueException;
import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.exceptions.base.MissingElementException;
import com.qentelli.automation.exceptions.base.PerformanceIssueException;
import com.qentelli.automation.factory.ExceptionFactory;
import com.qentelli.automation.singletons.RuntimeSingleton;
import com.qentelli.automation.utilities.CommonUtilities;
//import com.qentelli.automation.utilities.PageObject;
import com.qentelli.automation.utilities.PageObject;

public class BasePage {
	protected static Logger logger = LogManager.getLogger(BasePage.class);

	protected World world;
	private WebDriver driver;
	protected ResourceBundle configLib;
	protected ClassLoader configLoader;
	private String parentWindow;
	protected Waits waits;
	protected PageObject objects;
	protected String pagename = "BasePage";

	public BasePage(World world, WebDriver driver) {
		this.world = world;
		this.driver = driver;
		this.waits = new Waits(world, driver);
		loadConfig();
		if (driver == null)
			throw new AutomationIssueException("the driver is null in base page ");

	}

	private void loadConfig() {
		File file = new File("config");
		try {
			URL[] urls = { file.toURI().toURL() };
			configLoader = new URLClassLoader(urls);
			configLib = ResourceBundle.getBundle("config", Locale.getDefault(), configLoader);
		} catch (Exception e) {
			throw new AutomationIssueException("Error loading config file - " + e.getMessage());
		}
	}

	// Performs some driver checks and navigates to the given starting url
	public boolean launchBrowserAndNavigate(String url) {
		if (driver == null) {
			logger.info("Creating '" + world.getBrowser().name() + "' browser instance");
			try {
				driver = world.getDriver();
			} catch (Exception e) {
				throw new AutomationIssueException("Could not get driver instance - " + e.getMessage());
			}
		}
		navigateToUrl(url);
		if (world.getIsE2E()) {
			try {
				String sessionId = ((RemoteWebDriver) this.driver).getSessionId().toString();
				logger.info("Session:\t" + sessionId);
				if (world.getDriverType().equals(Constants.DRIVERTYPE.SAUCE)) {
					String tempWebLink = world.getSauceRest().getPublicJobLink(sessionId);
					logger.info("::::::::: Sauce video link :\t" + tempWebLink);
				}
			} catch (Exception e1) {
				logger.info("Exception: sauce video link --- " + e1.getLocalizedMessage());
			}
		}
		resizeWindowBasedOnPlatform();
		return true;
	}

	private void resizeWindowBasedOnPlatform() {
		if (world.isMobile()) {
			if (world.getDriverType() == Constants.DRIVERTYPE.LOCAL) {
				logger.info("Resizing window for Driver type -" + Constants.DRIVERTYPE.LOCAL);
				driver.manage().window().setSize(new Dimension(370, 801));
			}
		} else {
			driver.manage().window().maximize();
		}
	}

	// Navigates to given url
	public void navigateToUrl(String url) {
		long start = System.currentTimeMillis();
		driver.get(url);
		letMeFinishLoading();
		long end = System.currentTimeMillis();
		logger.info("Navigating to - '" + url + "' (" + (end - start) / 1000 + " secs.)");
	}

	// Turbo navigates to given url
	public void load(String url) {
		driver.get(url);
		logger.info("loaded -> " + url);
	}

	public void pause(long ms) {
		logger.info("pausing for " + ms + " milli seconds");
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException ignored) {
		}
	}

	// Sync logic for page load for all except safari browser.
	public void letMeFinishLoading(int... optionalTimeoutSecs) {
		int timeoutSecs = (optionalTimeoutSecs.length > 0) ? optionalTimeoutSecs[0] : Waits.PAGE_LOAD_TIMEOUT;
		long startTime = System.currentTimeMillis(), endTime;
		try {
			waits.fluentWait(timeoutSecs, 2).until(driver -> {
				String state;
				state = String.valueOf((executeJavaScript(
						"try{return document.readyState;}catch(exception){return \"loading\";}", null)));
				logger.info("Current page state: " + state);
				return state.equals("complete") || state.equals("null");
			});
			waitUntilJQueryReady();
		} catch (TimeoutException e) {
			throw new PerformanceIssueException(e);
		}
		endTime = System.currentTimeMillis();
		logger.warn("This page took " + CommonUtilities.getElapsedTime(startTime, endTime) + " to sync.");
	}

	// wait until JQuery is ready
	public void waitUntilJQueryReady() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("Checking if the page uses jquery...");
			boolean isJQueryDefined = (boolean) executeJavaScript("return typeof jQuery != 'undefined'", null);
			if (isJQueryDefined) {
				logger.info("Page uses jquery - " + isJQueryDefined);
				ExpectedCondition<Boolean> jQueryLoaded = driver -> ((Long) ((JavascriptExecutor) this.driver)
						.executeScript("return jQuery.active") == 0);
				waits.fluentWait(Waits.MEDIUM_WAIT, 2).until(jQueryLoaded);
			}
		} catch (WebDriverException e) {
			logger.info("Skipping all WebDriverExceptions while waiting for jquery...");
		} catch (NullPointerException ignore) {
		} finally {
			logger.info("Waited " + CommonUtilities.getElapsedTime(startTime, System.currentTimeMillis())
					+ " for JQuery...");
		}
	}

	// Waits until angular is ready
	public void waitUntilAngularReady() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info("Checking if page uses angular...");
			boolean isAngularDefined = (boolean) executeJavaScript("return window.angular != undefined", null);
			if (isAngularDefined) {
				boolean isAngularInjectorDefined = (boolean) executeJavaScript(
						"return angular.element(document).injector() != undefined", null);
				if (isAngularInjectorDefined) {
					logger.info("Page uses angular.");
					ExpectedCondition<Boolean> angularLoaded = driver -> Boolean.valueOf(executeJavaScript(
							"return angular.element(document).injector().get('$http').pendingRequests.length === 0",
							null).toString());
					waits.explicitWait(Waits.MEDIUM_WAIT).until(angularLoaded);
				}
			}
		} catch (WebDriverException ignored) {
			logger.info("Skipping all WebDriverExceptions while checking for angular");
		} finally {
			logger.info("Waited " + CommonUtilities.getElapsedTime(startTime, System.currentTimeMillis())
					+ " for Angular...");
		}
	}

	// Scroll 250 pixels and wait for so many secs
	public void scrollDownToTriggerJSEvents(long milliSecs) {
		logger.info("Scrolling once and waiting " + milliSecs + " secs.");
		try {
			executeJavaScript("window.scrollBy(0,250)", null);
			pause(milliSecs);
		} catch (Exception ignored) {
			logger.info("Skipping all Exceptions while scrolling by javascript...");
		}
	}

	// waits for elements to disappear, with time in seconds
	public void waitForElementInvisibility(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_VISIBILITY_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		try {
			fluentWaitWithExceptionHandling(ExpectedConditions.invisibilityOfElementLocated(locator),
					"ELEMENT TO BE INVISIBLE", locator, seconds, polling);
		} catch (TimeoutException toe) {
			refresh();
			fluentWaitWithExceptionHandling(ExpectedConditions.invisibilityOfElementLocated(locator),
					"ELEMENT TO BE INVISIBLE", locator, 15, polling);
		}
		waitUntilJQueryReady();
	}

	// waits for an element text to be updated
	public void waitForElementTextUpdate(By locator, String expectedText) {
		int seconds = Waits.LONG_WAIT;
		int polling = 2;
		fluentWaitWithExceptionHandling(
				ExpectedConditions.textMatches(locator, Pattern.compile("(?i).*" + expectedText + ".*")),
				"ELEMENT TEXT TO UPDATE", locator, seconds, polling);
	}

	// waits for an element text to not be empty
	public void waitForElementTextNotEmpty(By locator) {
		int seconds = Waits.LONG_WAIT;
		int polling = 2;
		fluentWaitWithExceptionHandling(ExpectedConditions.textMatches(locator, Pattern.compile("[^\\s]")),
				"ELEMENT TEXT TO APPEAR", locator, seconds, polling);
	}

	// Wait for element attribute value is not empty
	public void waitForElementAttributeValueNotEmpty(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_VISIBILITY_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		fluentWaitWithExceptionHandling(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(locator), "value"),
				"ELEMENT ATTRIBUTE VALUE NOT EMPTY", locator, seconds, polling);
	}

	// Switches to child tab from parent tab
	public void switchToChildWindow() {
		logger.info("Switching to child window");
		parentWindow = driver.getWindowHandle();
		driver.getWindowHandles().forEach(x -> {
			if (!x.equalsIgnoreCase(parentWindow))
				driver = driver.switchTo().window(x);
		});
		letMeFinishLoading();
	}

	// switches to parent window tab
	public void switchToParentWindow() {
		logger.info("Switching back to parent window");
		String windowHandler = driver.getWindowHandles().iterator().next();
		if (parentWindow != null) {
			driver.switchTo().window(parentWindow);
		} else {
			driver.switchTo().window(windowHandler);
		}
	}

	// Switch to frame
	public void switchToFrame(By locator) {
		logger.info("Switching to frame: " + locator);
		new WebDriverWait(world.driver, Waits.HALF_MINUTE)
				.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	// Switch to frame with long wait
	public void switchToFrameWithLongWait(By locator) {
		logger.info("Switching to frame: " + locator);
		new WebDriverWait(world.driver, Waits.LONG_WAIT)
				.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	// click and verifies new tab and switch back focus to parent tab
	public void validateNewTabElementAndSwitchBackToParent(By locatorToClick, By locatorOfElementToVerifyInNewTab) {
		logger.info("Validating new tab by checking element displayed - " + locatorOfElementToVerifyInNewTab
				+ " , and switching back to parent");
		click(locatorToClick);
		switchToChildWindow();
		getElementDisplayedAndEnabled(locatorOfElementToVerifyInNewTab);
		switchToParentWindow();
	}

	// click and verifies new tab url and switch focus back to parent tab
	public void validateNewTabUrlAndSwitchBackToParent(By locatorToClick, String expectedURL) {
		logger.info("Validating new tab by checking expected url - " + expectedURL + " , and switching back to parent");
		click(locatorToClick);
		switchToChildWindow();
		logger.info("Verifying New Tab URL contains expected");
		verifyURLContains(expectedURL);
		closeWindow();
		switchToParentWindow();
	}

	// switches to child window, captures child url as string, switches back to
	// parent window and returns child url
	public String getChildWindowURLAndSwitchBackToParent() {
		logger.info("Returns child window URL");
		switchToChildWindow();
		String redirectURL = driver.getCurrentUrl();
		logger.info("Window Re-Route URL : " + redirectURL);
		closeWindow();
		switchToParentWindow();
		return redirectURL;
	}

	// switches and focuses on first tab
	public void focusOnFirstBrowserTab() {
		logger.info("Focus on first browser tab");
		List<String> browserTabs = new ArrayList<>(world.driver.getWindowHandles());
		world.driver.switchTo().window(browserTabs.get(0));
	}

	// switches browser focus to tab by tab number
	public void switchToBrowserTab(int tabNumber) {
		logger.info("Focusing on browser tab by Index" + tabNumber);
		// small pause to load the other tab
		pause(3000);
		List<String> browserTabs = new ArrayList<>(world.driver.getWindowHandles());
		world.driver.switchTo().window(browserTabs.get(tabNumber - 1));
	}

	// This method is to navigate back
	public void navigateBack() {
		logger.info("Navigating back to previous page");
		driver.navigate().back();
		letMeFinishLoading();
	}

	// This method opens URL in a new tab
	public void openLinkInNewTab(String url) {
		executeJavaScript("window.open('about:blank','_blank');", null);
		switchToChildWindow();
		logger.info("Opening the link in next tab :" + url);
		if (url != null && !url.isEmpty()) {
			logger.info("Opening the link in next tab :" + url);
			world.driver.get(url);
		}
		letMeFinishLoading();
	}

	// scrolls till the end of page
	public boolean scrollTillEndOfPage() {
		try {
			logger.info("Scroll to end of the page");
			executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Scrolls to top of the page using javascript
	public boolean scrollTillTopOfPage() {
		logger.info("Scrolling to top of the page");
		boolean isDisplayed = false;
		try {
			executeJavaScript("window.scrollTo(0, 0);", null);
			return true;
		} catch (Exception e) {
			return isDisplayed;
		}
	}

	// Executes the given javascript command on the browser
	public Object executeJavaScript(String script, WebElement element) {
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		if (element != null) {
			try {
				return jsExec.executeScript(script, element);
			} catch (StaleElementReferenceException se) {
				if (isElementStale(element, Waits.MIN_WAIT))
					return jsExec.executeScript(script,
							refreshWebElement(ExpectedConditions.elementToBeClickable(element)));
				else
					throw (se);
			}
		} else {
			return jsExec.executeScript(script);
		}
	}

	// set focus using js
	public void setFocus(String id) {
		logger.info("Set focus using JS on element by id '" + id + "'");
		executeJavaScript("document.getElementById(" + id + ").focus();", null);
	}

	// set focus using js
	public void setFocus(By locator) {
		logger.info("Set focus using JS on element ," + locator + ">");
		WebElement element = getPresentElement(locator);
		executeJavaScript("arguments[0].focus();", element);
	}

	// todo to check for onChangeByJS
	// triggers any onChange JS on the application
	public void onChangeByJQuery(By locator) {
		onChangeByJQuery(world.driver.findElement(locator));
	}

	// todo to check for onChangeByJS
	// triggers any onChange JS on the application
	public void onChangeByJQuery(WebElement element) {
		executeJavaScript("$(arguments[0]).change(); return true;", element);
	}

	// Will triggers JS event on mouse over
	// todo create a reusable for below 3(onMouseOver(), onClick(), onChange() )
	// methods
	public void onMouseOver(By locator) {
		logger.info("Using java script to trigger JS event on mouse over " + locator);
		String mouseOverScript = "try{if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',"
				+ "true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject)"
				+ "{ arguments[0].fireEvent('onmouseover');}}catch(Exception){}";
		WebElement element = getPresentElement(locator);
		executeJavaScript(mouseOverScript, element);
	}

	// Will triggers JS event on click
	public void onClick(By locator) {
		logger.info("Using java script to trigger JS even on click " + locator);
		String onClickScript = "try{if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click',"
				+ "true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject)"
				+ "{ arguments[0].fireEvent('onclick');}}catch(Exception){}";
		WebElement element = getPresentElement(locator);
		executeJavaScript(onClickScript, element);
		letMeFinishLoading();
	}

	// Will triggers JS change event
	public void onChange(By locator) {
		logger.info("Using java script to trigger JS even on change " + locator);
		String onchangeScript = "try{if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',"
				+ "true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject)"
				+ "{ arguments[0].fireEvent('change');}}catch(Exception){}";
		WebElement element = getPresentElement(locator);
		executeJavaScript(onchangeScript, element);
		letMeFinishLoading();
	}

	// scrolls element into view by javascript
	public void scrollElementIntoViewJS(WebElement element, boolean... optionalAlignTop) {
		boolean align = (optionalAlignTop.length <= 0) || optionalAlignTop[0];
		if (isVisibleInViewport(element) == false) {
			logger.info("Scrolling element into view");
			try {
				executeJavaScript(
						"return arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});",
						element);
			} catch (StaleElementReferenceException e) {
				try {
					element = waits.fluentWait(Waits.LONG_WAIT, 2)
							.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
					executeJavaScript(
							"return arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});",
							element);
				} catch (JavascriptException jse) {
					executeJavaScript("return arguments[0].scrollIntoView(" + align + ");", element);
				}
			} catch (JavascriptException jse) {
				executeJavaScript("return arguments[0].scrollIntoView(" + align + ");", element);
			}
		}
	}

	// Scrolls the element into view using JS, can be align to top or bottom - if
	// not top then bottom
	// default is align to top
	public void scrollElementIntoViewJS(By selector, boolean... optionalAlignTop) {
		boolean align = (optionalAlignTop.length <= 0) || optionalAlignTop[0];
		scrollElementIntoViewJS(getVisibleElement(selector), align);
	}

	// Check if the object is in the viewport of the screen
	public boolean isVisibleInViewport(WebElement element) {
		return (boolean) executeJavaScript(
				"var elem = arguments[0],                 " + "  box = elem.getBoundingClientRect(),    "
						+ "  cx = box.left + box.width / 2,         " + "  cy = box.top + box.height / 2,         "
						+ "  e = document.elementFromPoint(cx, cy); " + "for (; e; e = e.parentElement) {         "
						+ "  if (e === elem)                        " + "    return true;                         "
						+ "}                                        " + "return false;                            ",
				element);
	}

	// returns page title
	public String getPageTitle() {
		logger.info("Getting page title");
		String title;
		try {
			title = driver.getTitle().trim();
		} catch (Exception e) {
			throw new AutomationIssueException("Could not get title");
		}
		logger.info(" >>>> Page Title: " + title);
		return title;
	}

	// returns list of elements
	public List<WebElement> getPresentElements(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_PRESENCE_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : 3;
		logger.info("Returning list of web elements");
		try {
			getPresentElement(locator, seconds, polling);
			return driver.findElements(locator);
		} catch (TimeoutException | MissingElementException ignore) {
			// ignoring as element list size could also be zero
			return new ArrayList<>();
		}
	}

	// Get locator from element object
	public By getLocator(WebElement element) {
		By by = null;
		String[] pathVariables = (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");
		String selector = pathVariables[0].trim();
		String value = pathVariables[1].trim();

		switch (selector) {
		case "id":
			by = By.id(value);
			break;
		case "className":
			by = By.className(value);
			break;
		case "tagName":
			by = By.tagName(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "cssSelector":
			by = By.cssSelector(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		default:
			throw new IllegalStateException("locator : " + selector + " not found!!!");
		}
		return by;
	}

	private Object fluentWaitWithExceptionHandling(ExpectedCondition condition, String waitType,
			Object locatorOrElement, int seconds, int polling) {
		long startTime = System.currentTimeMillis();
		Object output;
		By locator = null;
		try {
			// making logging less noisy
			// logger.info("Waiting for max " + seconds + " secs. for <" +
			// locatorOrElement.toString() + "> " + waitType + "...");
			output = waits.fluentWait(seconds, polling).until(condition);
			if (output instanceof WebElement && !waitType.equalsIgnoreCase("ELEMENT TO BE PRESENT")) {
				if (isElementStale((WebElement) output, 1)) {
					if (locatorOrElement instanceof WebElement) {
						locator = getLocator((WebElement) locatorOrElement);
					} else {
						locator = (By) locatorOrElement;
					}
					output = driver.findElement(locator);
				}
			}
		} catch (TimeoutException e) {
			RuntimeException exception = e;
			switch (waitType.toUpperCase()) {
			case "ELEMENT TO BE PRESENT":
			case "ELEMENT TO BE VISIBLE":
				exception = new MissingElementException(
						"Timed out waiting for <" + locatorOrElement.toString() + "> " + waitType + "...", e);
				break;
			case "ELEMENT TO BE CLICKABLE":
			case "ELEMENT TO BE INVISIBLE":
			case "ELEMENT TEXT TO APPEAR":
			case "ELEMENT TEXT TO UPDATE":
				exception = new AppIssueException(
						"Timed out waiting for <" + locatorOrElement.toString() + "> " + waitType + "...", e);
				break;
			case "ELEMENT STALENESS CHECK":
				return false;
			}
			throw exception;
		} finally {
			logger.info("Waited " + CommonUtilities.getElapsedTime(startTime, System.currentTimeMillis()) + " for <"
					+ locatorOrElement.toString() + "> " + waitType + "...");
		}
		return output;
	}

	// returns element if present
	public WebElement getPresentElement(By locator, int... optionalWaitSecs) throws WebDriverException {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_PRESENCE_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		return (WebElement) fluentWaitWithExceptionHandling(ExpectedConditions.presenceOfElementLocated(locator),
				"ELEMENT TO BE PRESENT", locator, seconds, polling);
	}

	// returns element if visible
	public WebElement getVisibleElement(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_VISIBILITY_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		return (WebElement) fluentWaitWithExceptionHandling(ExpectedConditions.visibilityOfElementLocated(locator),
				"ELEMENT TO BE VISIBLE", locator, seconds, polling);
	}

	// returns element if clickable
	public WebElement getClickableElement(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		return (WebElement) fluentWaitWithExceptionHandling(ExpectedConditions.elementToBeClickable(locator),
				"ELEMENT TO BE CLICKABLE", locator, seconds, polling);
	}

	// Get index of first active element
	public int getIndexOfFirstActiveElement(List<WebElement> elementList) {
		int out = 0;
		for (int i = 0; i < elementList.size(); i++) {
			if (elementList.get(i).isDisplayed() && elementList.get(i).isEnabled()) {
				out = i + 1;
				break;
			}
		}
		if (elementList.size() == 0 || out == 0) {
			throw new MissingElementException("No active elements found");
		}
		return out;
	}

	// Get locator of first active element
	public By getLocatorOfFirstActiveElement(By locator) {
		String xpath = locator.toString().replaceAll("By\\.xpath: ", "");
		return By.xpath("(" + xpath + ")[" + getIndexOfFirstActiveElement(getPresentElements(locator)) + "]");
	}

	// returns first active element
	public WebElement getFirstActiveElement(List<WebElement> elementList) {
		logger.info("Getting first active element from list of '" + elementList.size() + "' elements");
		WebElement selectElement = null;
		for (WebElement element : elementList) {
			if (isElementStale(element, 1))
				element = refreshWebElement(ExpectedConditions.elementToBeClickable(element));
			if (element.isDisplayed() && element.isEnabled()) {
				logger.info("First visible and enabled element found...");
				selectElement = element;
				break;
			}
		}
		if (selectElement == null) {
			String msg = (elementList.size() > 0) ? "None of the elements were visible and enabled"
					: "No elements found " + elementList;
			throw new MissingElementException(msg);
		}
		return selectElement;
	}

	// Wait for element to be clickable
	public boolean isElementStale(WebElement element, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : 5;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : 1;
		return (boolean) fluentWaitWithExceptionHandling(ExpectedConditions.stalenessOf(element),
				"ELEMENT STALENESS CHECK", element, seconds, polling);
	}

	// Wait for element to be clickable
	public void waitForElementToBeClickable(WebElement element, int... optionalWaitSecs) {
		// less noise
		// logger.info("Waiting for element to be clickable " + element);
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		fluentWaitWithExceptionHandling(ExpectedConditions.elementToBeClickable(element), "ELEMENT TO BE CLICKABLE",
				element, seconds, polling);
	}

	// This method will delete all cookies.
	public void deleteAllCookies() {
		logger.info("Deleting all cookies.");
		driver.manage().deleteAllCookies();
	}

	// accepts browser alert message
	public void acceptAlert() {
		try {
			logger.info("Accepting Alert");
			Alert alert = driver.switchTo().alert();
			alert.accept();
			driver.switchTo().defaultContent();
			letMeFinishLoading();
			logger.info("Alert accepted");
		} catch (NoAlertPresentException e) {
			throw new AutomationIssueException("Could not accept browser alert :" + e.getMessage());
		}
	}

	// Handling secure warning popup on safari
	public void handleIOSRelatedNonSecureWarningPopup() {
		logger.info("Handling IOS related non secure warning popup");
		if (world.getBrowser().equals(Constants.BROWSER.SAFARI)) {
			driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
			driver.navigate().refresh();
		}
	}

	// Closes active window
	public void closeWindow() {
		logger.info("Closing the window");
		world.driver.close();
	}

	// This method closes tabs except the parent tab where actions need to be
	// performed
	public void closeAllChildTabs() {
		logger.info("Closing all child tabs");
		parentWindow = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}
		switchToParentWindow();
	}

	// Send these keys to the element
	public void sendKeys(By locator, String text) {
		logger.info("Sending keys '" + text + "' into <" + locator + ">");
		WebElement element = getElementDisplayedAndEnabled(locator);
		element.sendKeys(text);
	}

	public void sendKeys(By locator, Keys text) {
		logger.info("Sending keys '" + text + "' into <" + locator + ">");
		WebElement element = getElementDisplayedAndEnabled(locator);
		element.sendKeys(text);
	}

	// clear existing text before enter text
	public void enterText(By locator, String toEnter, Keys... optionalKey) {
		Keys sendKey = (optionalKey.length > 0) ? optionalKey[0] : null;
		logger.info("Clear <" + locator.toString() + "> and enter text " + toEnter);
		WebElement element = getElementDisplayedAndEnabled(locator);
		if (isJSEnterTextNecessary(element)) {
			enterTextByJS(element, toEnter);
		} else {
			click(element);
			element.clear();
			element.sendKeys(toEnter);
		}
		if (sendKey != null) {
			element.sendKeys(sendKey);
		}
		letMeFinishLoading();
	}

	// todo - will remove this when all page classes have been refactored and we
	// have no active references to this method
	// clear existing text before enter text using Java Script
	public void enterTextByJS(By locator, String text) {
		logger.info("Clear <" + locator.toString() + "> and enter text '" + text + "' using javascript");
		scrollElementIntoViewJS(locator);
		WebElement element = getElementDisplayedAndEnabled(locator);
		enterTextByJS(element, text);
	}

	// clear existing text before enter text using Java Script
	public void enterTextByJS(WebElement element, String text) {
		logger.info("Clear <" + element.toString() + "> and enter text '" + text + "' using javascript");
		scrollElementIntoViewJS(element);
		clickByJS(element);
		element.clear();
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("arguments[0].value=arguments[1];", element, text);
		letMeFinishLoading();
	}

	// returns true if JS is to be used instead of native selenium, based on data
	// rule attribute from the element
	public boolean isJSEnterTextNecessary(WebElement element) {
		for (String attribute : Constants.DATARULE_TRIGGERS) {
			if (StringUtils.equalsIgnoreCase(element.getAttribute(attribute), "true")) {
				logger.info("Enter text to be done by JS for " + element);
				return true;
			}
		}
		return false;
	}

	// Mouse hover on an element uses actions
	public void mouseHoverByActions(By element) {
		logger.info("Firing mouse hover event using actions");
		WebElement webElement = getVisibleElement(element);
		Actions act = new Actions(driver);
		act.moveToElement(webElement).perform();
	}

	// Mouse hover on an element uses actions
	public void mouseHoverByActions(WebElement element) {
		logger.info("Firing mouse hover event using actions");
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
	}

	// Mouse hover and click element uses actions
	public void mouseHoverAndClickElementByActions(By element) {
		logger.info("Clicking by actions");
		WebElement webElement = getVisibleElement(element);
		Actions act = new Actions(driver);
		act.moveToElement(webElement).click().build().perform();
	}

	// waits for element to be displayed and clicks the element by web element
	public void click(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
		scrollElementIntoViewJS(getPresentElement(locator));
		click(getVisibleElement(locator, seconds));
	}

	// Scrolls and Clicks a web element on the ui ElementClickInterceptedException
	public void click(WebElement element, int... optionalWaitSecs) {
		try {
			int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
			waitForElementToBeClickable(element, seconds);
			scrollElementIntoViewJS(element);
			// logger.info("Native click : " + element);
			clickWithStaleElementExceptionHandling(element);
			letMeFinishLoading();
		} catch (ElementNotInteractableException | TimeoutException e) {
			logger.info("WebDriverException occurred... clicking by JS... " + e.getMessage());
			clickByJS(element);
			e.printStackTrace();
		}
	}

	// Refresh element
	private WebElement refreshWebElement(ExpectedCondition<WebElement> condition) {
		return waits.fluentWait(Waits.LONG_WAIT, 2).until(ExpectedConditions.refreshed(condition));
	}

	// click element with stale element exception
	private void clickWithStaleElementExceptionHandling(WebElement element) {
		try {
			element.click();
		} catch (StaleElementReferenceException e) {
			refreshWebElement(ExpectedConditions.elementToBeClickable(element)).click();
		}
	}

	// Clicks element if displayed
	// todo discuss the merit of having a method to ignore possible exceptions
	public void clickIfVisible(By locator, int... optionalWaitSecs) {
		logger.info("Checking visibility of element and clicking");
		try {
			click(getFirstActiveElement(getPresentElements(locator, optionalWaitSecs)), optionalWaitSecs);
		} catch (Exception ignore) {
			logger.info("Element display timed out");
		}
	}

	// Click on element with least wait, this method is required to click on
	// pop-up's which may appear during execution
	public void clickIfVisibleWithLeastWait(By locator) {
		logger.info("Checking visibility of element and clicking if it visible");
		try {
			click(getFirstActiveElement(getPresentElements(locator, Waits.MEDIUM_WAIT)), Waits.MEDIUM_WAIT);
		} catch (Exception ignore) {
			logger.info("Element display timed out, Skipping exception");
		}
	}

	// waits for element to be displayed and clicks the element by web element
	public void clickByJS(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
		clickByJS(getVisibleElement(locator, seconds), seconds);
	}

	// Submitting form by locator
	public void submitForm(By locator) {
		logger.info("Submitting form by locator:" + locator);
		getClickableElement(locator).submit();
	}

	// Scrolls and Clicks a web element on the ui ElementClickInterceptedException
	public void clickByJS(WebElement element, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
		waitForElementToBeClickable(element, seconds);
		scrollElementIntoViewJS(element);
		logger.info("Javascript click : " + element);
		executeJavaScript("return arguments[0].click();", element);
		letMeFinishLoading();
	}

	// Clicks element if displayed
	// todo discuss the merit of having a method to ignore possible exceptions
	public void clickIfVisibleByJS(By locator, int... optionalWaitSecs) {
		try {
			clickByJS(locator, optionalWaitSecs);
		} catch (Exception ignore) {
			logger.info("Element display timed out");
		}
	}

	// todo check if javascript scroll and click could be used instead of this
	// Scrolls to an element with wait and clicks, wait is in seconds.
	public void scrollByActionAndNativeClick(By locator, int seconds) {
		logger.info("Using JS scroll and click instead of actions " + locator);
		clickByJS(locator, seconds);
		/*
		 * logger.info("Scroll to element and perform click operation"); Actions actions
		 * = new Actions(driver); WebElement element = getVisibleElement(locator);
		 * waitForElementToBeClickable(element,seconds);
		 * actions.moveToElement(element).build().perform(); element.click();
		 * logger.info("Element is clicked successfully");
		 */
	}

	// gets text from the web element - takes By locator as a parameter
	public String getTextFromElement(By locator, boolean... optionalEmptyCheck) throws MissingElementException {
		boolean check = (optionalEmptyCheck.length <= 0) || optionalEmptyCheck[0];
		logger.info("Getting text from element : " + locator.toString() + "");
		if (world.getBrowser().equals(Constants.BROWSER.SAFARI)) {
			scrollElementIntoViewJS(getPresentElement(locator));
		}
		WebElement element = getVisibleElement(locator);
		if (check)
			waitForElementTextNotEmpty(locator);
		return getTextFromElement(element);
	}

	public String getTextFromElement(By locator, int timeout) throws MissingElementException {
		logger.info("Getting text from element : " + locator.toString() + "");
		if (world.getBrowser().equals(Constants.BROWSER.SAFARI)) {
			scrollElementIntoViewJS(getPresentElement(locator));
		}
		WebElement element = getVisibleElement(locator, timeout);
		return getTextFromElement(element);
	}

	// gets text from the web element - takes WebElement as a parameter
	public String getTextFromElement(WebElement element) {
		String innerText = "";
		try {
			innerText = element.getText().trim();
		} catch (StaleElementReferenceException e) {
			innerText = waits.fluentWait(Waits.MIN_WAIT, 2).until(ExpectedConditions.visibilityOf(element)).getText()
					.trim();
		}
		logger.info("The Inner Text Of An Element is (trimmed): " + innerText);
		return innerText;
	}

	// Get attribute value from an element which is visible
	public String getAttributeValueFromElement(By locator, String attribute) {
		logger.info("Getting attribute : " + attribute + " value using by element : " + locator.toString() + " ");
		WebElement element = getVisibleElement(locator);
		if (isElementStale(element))
			element = getPresentElement(locator);
		try {
			return element.getAttribute(attribute).trim();
		} catch (NullPointerException ignore) {
			// comes into this catch if required attribute is not available on the element
			return null;
		}
	}

	// Get CSS value from an element which is visible
	public String getCssValueFromElement(By locator, String cssAttribute) {
		logger.info("Getting css value : " + cssAttribute + " from element : " + locator.toString() + " ");
		WebElement element = getVisibleElement(locator);
		if (isElementStale(element))
			element = getPresentElement(locator);
		return element.getCssValue(cssAttribute).trim();
	}

	// Get CSS value from an element
	public String getCssValueFromElement(WebElement element, String cssAttribute) {
		logger.info("Getting css value : " + cssAttribute + " from element");
		return element.getCssValue(cssAttribute).trim();
	}

	// Returns attribute value of an element on DOM, irrespective of element being
	// displayed or not
	public String getAttributeValueFromHiddenElement(By locator, String attribute) {
		logger.info("Getting attribute : " + attribute + " , value using from element : " + locator.toString() + " ");
		return getPresentElement(locator).getAttribute(attribute);
	}

	// selects dropdown option by visible text
	public void selectByVisibleTextInDropdown(By locator, String value) {
		logger.info("Selecting value : " + value + " : from dropdown using element : " + locator);
		WebElement element = getElementDisplayedAndEnabled(locator);
		scrollElementIntoViewJS(element);
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}

	// gets the index of the option
	public int getOptionIndexInDropdown(Select select, String searchBy, String value) {
		logger.info("Getting index of option (" + searchBy + "=" + value + ")");

		if (searchBy.equalsIgnoreCase("value")) {
			for (int i = 0; i < select.getOptions().size(); i++) {
				if (select.getOptions().get(i).getAttribute("value").equalsIgnoreCase(value)) {
					return i;
				}
			}
		} else if (searchBy.equalsIgnoreCase("visibletext")) {
			for (int i = 0; i < select.getOptions().size(); i++) {
				if (select.getOptions().get(i).getText().equalsIgnoreCase(value)) {
					return i;
				}
			}
		}
		return 1;
	}

	// Selects a dropdown option by value
	public void selectByValueInDropdown(By locator, String value) {
		logger.info("Select '" + value + "' from dropdown <" + locator.toString() + ">");
		WebElement webElement = getElementDisplayedAndEnabled(locator);
		scrollElementIntoViewJS(webElement);
		Select select = new Select(webElement);

		// Get all options
		List<WebElement> dd = select.getOptions();

		// Get the length
		System.out.println("possible item: " + dd.size());
		for (int j = 0; j < dd.size(); j++) {
			// System.out.println("possible item: " + dd.get(j).getText() + " vs. " +
			// value);
			if (select.getOptions().get(j).getText().contains(value)) {
				select.selectByIndex(j);
				return;
			}

		}

		// Loop to print one by one

		int idx = getOptionIndexInDropdown(select, "value", value);
		if (select.getOptions().get(idx).isEnabled()) {
			select.selectByValue(value);
		} else {
			select.selectByIndex(idx + 1);
		}
	}

	// selects dropdown option by index
	public void selectByIndexInDropdown(By locator, int index) {
		selectByIndexInDropdown(getVisibleElement(locator), index);
	}

	// selects dropdown option by index
	public void selectByIndexInDropdown(WebElement element, int index) {
		logger.info("Select value from dropdown using element : " + element + " using index : " + index + "");
		scrollElementIntoViewJS(element);
		waitForElementToBeClickable(element);
		Select select = new Select(element);
		List<WebElement> dd = select.getOptions();
		System.out.println("possible item:");
		// for (int j = 0; j < dd.size(); j++) {
		// System.out.println("possible item: " + dd.get(j).getText());
		//
		// }
		select.selectByIndex(index);
	}

	// Selects a dropdown option by value using java script
	public void selectByValueInDropdownByJS(By locator, String value) {
		logger.info("Selecting value : " + value + " : from dropdown using element : " + locator);
		WebElement element = getElementDisplayedAndEnabled(locator);
		scrollElementIntoViewJS(element);
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("var select = arguments[0]; " + "for(var i = 0; i < select.options.length; i++)" + "{ "
				+ "if(select.options[i].value == arguments[1])" + "{ " + "select.options[i].selected = true; " + "} "
				+ "}", element, value);
	}

	// toggles checkbox status
	public void toggleCheckbox(By locator, boolean checked) {
		if (getPresentElement(locator).isSelected() != checked) {
			logger.info("Clicking the checkbox to set it to " + checked + "\n\t\t\t\t\telement <" + locator + ">");
			click(locator);
		}
	}

	// returns the selected option value from dropdown
	public String getSelectedValueFromDropdown(By locator, boolean... optionalVisibilityCheck) {
		boolean visibilityCheck = (optionalVisibilityCheck.length <= 0) || optionalVisibilityCheck[0];
		logger.info("Getting selected value from dropdown");
		WebElement webElement = (visibilityCheck) ? getElementDisplayedAndEnabled(locator) : getPresentElement(locator);
		Select select = new Select(webElement);
		return select.getFirstSelectedOption().getAttribute("value");
	}

	// returns selected text value from dropdown
	public String getSelectedTextFromDropdown(By locator, boolean... optionalVisibilityCheck) {
		boolean visibilityCheck = (optionalVisibilityCheck.length <= 0) || optionalVisibilityCheck[0];
		logger.info("Getting selected value from dropdown");
		WebElement webElement = (visibilityCheck) ? getVisibleElement(locator) : getPresentElement(locator);
		Select select = new Select(webElement);
		return select.getFirstSelectedOption().getText();
	}

	// Returns all options as a list for a dropdown
	public List<WebElement> getDropdownOptions(By locator, boolean... optionalVisibilityCheck) {
		boolean visibilityCheck = (optionalVisibilityCheck.length <= 0) || optionalVisibilityCheck[0];
		logger.info("Getting select dropdown options");
		WebElement webElement = (visibilityCheck) ? getElementDisplayedAndEnabled(locator) : getPresentElement(locator);
		Select select = new Select(webElement);
		return select.getOptions();
	}

	// todo MARKED FOR DELETION. Remove this method after all calls from other files
	// are removed
	// handling error based on exception and reporting
	public void failScenarioAndReportInfo(String info, Exception exception) {
		info = this.getClass().getSimpleName() + " >> " + this.getClass().getSimpleName() + " " + info;
		logger.info(exception.getMessage());
		logger.info(info);
		logger.info(exception.getStackTrace());
		exception.printStackTrace();
		List<String> excludedExceptions = new ArrayList<>();
		excludedExceptions.add("Missing Template ERR_CONNECT_FAIL");
		excludedExceptions.add("JavaScript error");
		try {
			if (exception instanceof org.openqa.selenium.json.JsonException
					&& exception.getMessage().contains("Unable to determine type from: E. Last 1 characters read: E")) {
				// Skip getting title since it goes into loop and causes Stack overflow
				RuntimeSingleton.getInstance().pageTitle = "";
			} else {
				RuntimeSingleton.getInstance().pageTitle = getPageTitle();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (String exclExc : excludedExceptions) {
			if (exception.getMessage().contains(exclExc)) {
				logger.info("Skipping Exception: " + exception.getClass().getSimpleName());
				return;
			}
		}
		try {
			checkForAppIssue();
		} catch (Exception e) {
			AppIssueException ai = new AppIssueException(exception.getMessage(), e);
			e.printStackTrace();
			throw new ExceptionFactory(info, ai).redirectException();
		} finally {
			if (world.bydSessionUserDetails != null)
				world.bydSessionMonitor.releaseUser(world.bydSessionUserDetails.get("username"));
			else if (world.cooSessionMonitor != null)
				world.cooSessionMonitor.releaseUser(world.cooImpersonateSessionUserDetails.get("username"));
		}
		if (!(exception instanceof RuntimeException)) {
			info = exception.getClass().getSimpleName() + " occurred in \n" + info;
		}
		logger.info("failed scenario:" + info);
		throw new ExceptionFactory(info, exception).redirectException();
	}

	// todo MARKED FOR DELETION. Remove this method after all calls from other files
	// are removed
	// handling exceptions and reporting
	public void failScenarioAndReportInfo(String info) {
		info = this.getClass().getSimpleName() + " >> " + world.getMyMethodName() + " " + info;
		try {
			checkForAppIssue();
		} catch (Exception e) {
			if (!(e instanceof RuntimeException)) {
				info = e.getClass().getSimpleName() + " occurred in \n" + info;
			}
		} finally {
			if (world.bydSessionUserDetails != null)
				world.bydSessionMonitor.releaseUser(world.bydSessionUserDetails.get("username"));
			else if (world.cooSessionMonitor != null)
				world.cooSessionMonitor.releaseUser(world.cooImpersonateSessionUserDetails.get("username"));
		}
		logger.info(info);
		throw new AppIssueException(info);
	}

	// returns element which is displayed and is enabled
	public WebElement getElementDisplayedAndEnabled(By locator, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_VISIBILITY_WAIT;
		WebElement webElement;
		webElement = getVisibleElement(locator, seconds);
		if (webElement.isEnabled()) {
			logger.info("Element is enabled and displayed in page:" + locator);
			return webElement;
		} else {
			throw new AppIssueException("Element is not enabled " + locator);
		}
	}

	// todo check usage is valid for mobile, refer Test Rail TC
	// Compares 2 elements locations by coordinates and returns if first element is
	// on left side of the second element
	public boolean isElementPositionedToLeftOf(By leftElement, By rightElement) {
		logger.info("Checking element position");
		return getVisibleElement(leftElement).getLocation().getX() < getVisibleElement(rightElement).getLocation()
				.getX();
	}

	// Compares if first element position is located on top of the second element
	// position
	public boolean isElementPositionedOnTopOf(By topElement, By bottomElement) {
		logger.info("Checking first element position is located on top of second element");
		return getVisibleElement(topElement).getLocation().getY() < getVisibleElement(bottomElement).getLocation()
				.getY();
	}

	// checks if the element is clickable
	public boolean isElementClickable(By locator, int... optionalWaitSecs) {
		logger.info("Checking if element is clickable");
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_CLICKABILITY_WAIT;
		try {
			getClickableElement(locator, seconds);
			return true;
		} catch (Exception ignore) {
			return false;
		}
	}

	// checks if alert is present with least wait
	public boolean isAlertPresentWithinLeastWait() {
		return isAlertPresent(Waits.MIN_WAIT);
	}

	// checks if alert is present
	public boolean isAlertPresent(int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_PRESENCE_WAIT;
		int polling = (optionalWaitSecs.length > 1) ? optionalWaitSecs[1] : Waits.POLLING;
		logger.info("Waiting " + seconds + " seconds for alert to be present");
		try {
			waits.fluentWait(seconds, polling).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception ignore) {
			return false;
		}
	}

	// checks if alert is present with minimum wait time
	public boolean isAlertPresentWithLeastWait() {
		logger.info("Checking for alert to be present with minimum time");
		return isAlertPresent(Waits.MIN_WAIT);
	}

	// returns selected status of Radio Button/Check Box - true or false
	public boolean isSelected(By locator) {
		logger.info("Checking if element <" + locator + "> is selected");
		WebElement element = getPresentElement(locator);
		return element.isSelected();
	}

	// returns selected status of Radio Button/Check Box - true or false
	public boolean isSelected(By locator, int i) {
		logger.info("Checking if element <" + locator + "> is selected");
		WebElement element = getPresentElement(locator, i);
		return element.isSelected();
	}

	// checks element displayed - returns boolean
	public boolean isElementDisplayedAndEnabled(By locator, int... optionalWaitSecs) {
		logger.info("Checking if element is displayed and enabled");
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_VISIBILITY_WAIT;
		try {
			if (world.getBrowser().equals(Constants.BROWSER.SAFARI)) {
				scrollElementIntoViewJS(getPresentElement(locator, seconds));
			}
			WebElement webElement = getVisibleElement(locator, seconds);
			if (webElement.isEnabled()) {
				return true;
			}
		} catch (Exception ignore) {
			return false;
		}
		return false;
	}

	public void resetBrowserZoom() {
//		getPresentElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,"0"));
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0030')).build().perform();
	}

	// validates if a string contains in current url
	public void verifyURLContains(String url) {
		logger.info("Verifying url contains: " + url);
		try {
			waits.fluentWait(Waits.MEDIUM_WAIT, 2).until(ExpectedConditions.urlContains(url));
		} catch (TimeoutException e) {
			throw new AppIssueException(
					"Current url '" + driver.getCurrentUrl() + "' doesn't contain expected text '" + url + "'");
		}
	}

	// verifies url with current url
	public void verifyURL(String url) {
		logger.info("Verifying url equals: " + url);
		try {
			waits.fluentWait(Waits.MEDIUM_WAIT, 2).until(ExpectedConditions.urlContains(url));
		} catch (TimeoutException e) {
			throw new AppIssueException(
					"Current url '" + driver.getCurrentUrl() + "' doesn't match expected url '" + url + "'");
		}
	}

	// verify popup message text
	public void verifyElementTextEquals(By locator, String expectedText) {
		String eleText = getTextFromElement(locator).trim();
		logger.info("Verifying Element Text: " + eleText + " Expected Text: " + expectedText);
		if (!StringUtils.equalsIgnoreCase(expectedText, eleText)) {
			throw new AppIssueException(locator + " does not have the expected text: " + expectedText);
		}
	}

	// Based on known application/environment failures, comparing error message to
	// throws application/environment issue
	public void checkForAppIssue() {
		logger.info("Checking for App issue");
		List<String> errXpaths = new ArrayList<>();
		List<String> skipThese = new ArrayList<>();
		errXpaths.add("//label[@class='error']");
		errXpaths.add("//div[@class='title'][.='Can’t reach this page']");
		errXpaths.add("//div[@id='error-main']");
		skipThese.add("Please sign in with your updated information. Thank you!");
		skipThese.add("The email address or password you entered is incorrect.");
		skipThese.add("Email Address not valid. Please enter a valid email address, example: name@email.com");

		boolean skip = false;
		for (String xpath : errXpaths) {
			By errElem = By.xpath(xpath);
			if (isElementDisplayedAndEnabled(errElem, 1)) {
				String msg = getTextFromElement(errElem);
				for (String skipMsg : skipThese) {
					if (msg.equalsIgnoreCase(skipMsg)) {
						skip = true;
						break;
					}
				}
				if (!skip) {
					throw new AppIssueException("Application error displayed\nMessage = " + msg);
				}
			}
		}
	}

	// This method verifies gets text from an element and verifies against expected
	// value
	public void verifyElementTextContainsExpectedText(String expectedText, By locator) {
		// replaceAll(" "," ") looks same but are different, text fetched from safari
		// browser some how has this and has to be replaced by a normal space
		logger.info("Verify element <" + locator + "> text contains expected text " + expectedText);
		String actualText = getTextFromElement(locator).trim().replaceAll(" ", " ");
		if (!CommonUtilities.replaceSmartChars(actualText).contains(CommonUtilities.replaceSmartChars(expectedText))) {
			throw new AppIssueException(
					"Actual text '" + actualText + "' does not contain the expected text: '" + expectedText + "'");
		}
	}

	// validates dropdown option text and it's sequence
	public void verifyDropdownOptionSequence(By dropdownLocator, List<String> expectedOptions) {
		logger.info("Verifying dropdown option sequence for <" + dropdownLocator + ">");
		WebElement webElement = getElementDisplayedAndEnabled(dropdownLocator);
		Select select = new Select(webElement);
		int i = 0;
		for (WebElement actualOption : select.getOptions()) {
			if (!StringUtils.equalsIgnoreCase(normalizeSpecialChars(expectedOptions.get(i)),
					normalizeSpecialChars(actualOption.getText().trim()))) {
				throw new AppIssueException(
						"Dropdown text values did not match " + normalizeSpecialChars(expectedOptions.get(i)) + " "
								+ normalizeSpecialChars(actualOption.getText().trim()));
			}
			i++;
		}
	}

	// Verify searchStr contains in str
	public void verifyTextContains(String searchText, String text, String comments) {
		logger.info("Verifying '" + text + "' contains '" + searchText + "' - " + comments);
		if (!StringUtils.contains(text, searchText)) {
			throw new AppIssueException(comments + " - value '" + searchText + "' was not found in '" + text + "'.");
		}
	}

	// Verify searchStr contains in str case insensitive
	public void verifyTextContainsIgnoreCase(String searchText, String text, String comments) {
		logger.info("Verifying '" + text + "' contains '" + searchText + "' - " + comments);
		if (!StringUtils.containsIgnoreCase(text, searchText)) {
			throw new AppIssueException(comments + " - value '" + searchText + "' was not found in '" + text + "'.");
		}
	}

	// Verify actual value equals the expected value ignoring case
	public void verifyActualEqualsExpected(String expected, String actual, String comments) {
		logger.info("Verifying actual and expected values are equal - " + comments);
		if (!StringUtils.equalsIgnoreCase(expected, actual)) {
			throw new AppIssueException(
					comments + "\n actual value '" + actual + "' did not match expected '" + expected + "'.");
		}
	}

	// Verify actual value equals the expected value
	public void verifyActualEqualsExpected(double expected, double actual, String fieldName) {
		logger.info("Verifying actual and expected values match for '" + fieldName + "'");
		if (actual != expected) {
			throw new AppIssueException(
					fieldName + " - value '" + actual + "' did not match expected '" + expected + "'.");
		}
	}

	// the below method is to accept the cookies in TBB application
	public void acceptAllCookies() throws Exception {
		if (isElementDisplayedAndEnabled(By.cssSelector("#onetrust-accept-btn-handler"), 5))
			click(By.cssSelector("#onetrust-accept-btn-handler"));
	}

	// Verify actual value equals the expected
	public void verifyActualEqualsExpected(boolean expected, boolean actual, String comments) {
		logger.info("Verifying if the actual [" + actual + "] equals expected [" + expected + "] - " + comments);
		if (expected != actual) {
			throw new AppIssueException(comments + " , actual condition '" + actual
					+ "' did not match expected condition - '" + expected + "'.");
		}
	}

	// Verify the text is all upper case
	public void verifyAllUpperCase(String text, String fieldName) {
		logger.info("Verifying if the text [" + text + "] is in upper case...");
		if (!StringUtils.isAllUpperCase(StringUtils.deleteWhitespace(text))) {
			throw new AppIssueException(fieldName + " is not in upper case");
		}
	}

	// This method is to refresh the page
	public void refresh() {
		logger.info("Refreshing page");
		driver.navigate().refresh();
		letMeFinishLoading();
	}

	public void verifyPageTitle(String expectedTitle) {
		int seconds = Waits.LONG_WAIT;
		int polling = 2;
		waits.fluentWait(seconds, polling).until(ExpectedConditions.titleContains(expectedTitle));
	}

	// Check if the element is displayed/not displayed within least time
	public boolean isElementDisplayedWithLeastWait(By locator) {
		logger.info("Checking if the element displayed within the least wait time");
		return isElementDisplayedAndEnabled(locator, Waits.MIN_WAIT);
	}

	// Check if the element is displayed/not displayed within quarter minute
	public boolean isElementDisplayedWithQuarterMinuteWait(By locator) {
		logger.info("Checking if the element displayed within the quarter minute wait time");
		return isElementDisplayedAndEnabled(locator, Waits.MEDIUM_WAIT);
	}

	// Check if the element is displayed/not displayed within half minute
	public boolean isElementDisplayedWithMediumWait(By locator) {
		logger.info("Checking if the element displayed within the half minute wait time");
		return isElementDisplayedAndEnabled(locator, Waits.HALF_MINUTE);
	}

	// Checking is the element is interactable
	public boolean isElementInteractable(By locator, Keys key) {
		logger.info("Checking is element is interactable");
		try {
			getPresentElement(locator).sendKeys(key);
			return true;
		} catch (ElementNotInteractableException e) {
			return false;
		}
	}

	// Checks if a string contains in current url and returns a boolean value
	public boolean isURLContains(String url) {
		logger.info("Verifying url contains: '" + url + "' , and returns a boolean value");
		try {
			waits.fluentWait(Waits.MEDIUM_WAIT, 2).until(ExpectedConditions.urlContains(url));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	// Verify all elements in the collection are displayed
	public void verifyAllElementsAreDisplayed(List<WebElement> elements) {
		logger.info("Verifying all elements in the collection are displayed");
		for (WebElement element : elements) {
			scrollElementIntoViewJS(element);
			verifyActualEqualsExpected(true, element.isDisplayed(),
					"Element display check for: " + element.getText().trim());
		}
	}

	// Wait for number of windows to match expected
	public void waitForNumberOfWindowsToBe(int expectedNumberOfWindows) {
		logger.info("Wait for number of windows to match expected");
		waits.fluentWait(Waits.MEDIUM_WAIT, 2).until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
	}

	// Getting Current URL from browser
	public String getCurrentURL() {
		logger.info("Getting Current URL from browser");
		return driver.getCurrentUrl();
	}

	public List<WebElement> getPresentElementsWithLeastWait(By locator) {
		logger.info("Returning list of web elements");
		try {
			getPresentElement(locator, Waits.MIN_WAIT);
			return driver.findElements(locator);
		} catch (TimeoutException | MissingElementException ignore) {
			// ignoring as element list size could also be zero
			return new ArrayList<>();
		}
	}

	public void navigate(String url) {
		logger.info("navigate: -> " + url);
		driver.get(url);
		driver.manage().window().maximize();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void waitUntilAttributeValueMatches(By locator, String attributeName, String attributeValue) {
		logger.info("Waiting for element attibute value matches");
		waits.fluentWait(Waits.LONG_WAIT, 2)
				.until(ExpectedConditions.attributeContains(locator, attributeName, attributeValue));
	}

	static public void sleep(int secs) {
		try {
			logger.info("Sleeping for "+1 * 1000 * secs + " milliseconds") ;
			Thread.sleep(1 * 1000 * secs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Wait for expected string to display on element
	public boolean waitForElementTextToBe(By locator, String expectedString) {
		WebElement element = getVisibleElement(locator);
		logger.info("Wait for element text match as expected");
		return waits.fluentWait(Waits.HALF_MINUTE, 2)
				.until(ExpectedConditions.textToBePresentInElement(element, expectedString));
	}

	public void printFrames() {
		final List<WebElement> iframes = world.driver.findElements(By.tagName("iframe"));
		logger.info("FRAME COUNT:\t" + iframes.size());
		for (WebElement iframe : iframes) {
			String frame = iframe.getAttribute("id");
			logger.info(frame);
		}
	}

	public void switchToDefaultContent() {
		logger.info("Switching to default content");
		driver.switchTo().defaultContent();
	}

	public void openNewTab() {
		logger.info("Opening new tab");
		((JavascriptExecutor) world.driver).executeScript("window.open('about:blank','_blank');");
	}

	public void clearText(By locator) {
		logger.info("Clearing the text in the given text box");
		driver.findElement(locator).clear();
	}
}
