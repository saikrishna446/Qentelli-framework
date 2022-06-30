package com.qentelli.automation.pages.common;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import com.qentelli.automation.common.Constants;
import com.qentelli.automation.common.World;
import com.qentelli.automation.drivers.Waits;
import com.qentelli.automation.exceptions.base.AppIssueException;
import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.exceptions.custom.ApplicationTooSlowException;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.stepdefs.common.application.HomePage;

public class CommonPage extends BasePage {
	private World world;
	Logger logger = LogManager.getLogger(CommonPage.class);
	By loadingSymbol;
    By tbbSpinner;
	By bitDefenderTitle;
	By bitDefenderHandler;
	By buttonAcceptAllCookies;
	By shacSpinner;
	private String fauxDropDownForMobile;
	private String fauxSelectValueFromListForMobile;
	private String productOptionListForMobile;
    HomePage home;

    public void InitElements() {
      // home = new TeamtestHomePage(world);

		loadingSymbol = By.cssSelector("#bb-loading");
        tbbSpinner = By.xpath(("//div[@class='icon icon-spinner icon-spin cart-spinner']"));
		bitDefenderTitle = By.xpath("//div[@class='title'][.='Bitdefender Endpoint Security Tools blocked this page']");
		bitDefenderHandler = By.xpath("//a[@href='javascript:proceedAddMitmExclusion()']");
		fauxDropDownForMobile = "//following::div[@class='select-faux mod show-for-small-only']//li";
		fauxSelectValueFromListForMobile = "//following::div[contains(@class,'select-faux')]//li";
		productOptionListForMobile = "//*[@class='drawer-overlay']//li";
		buttonAcceptAllCookies = By.xpath(
				"//button[@id='onetrust-accept-btn-handler' or @data-track-value='acceptallcookies' or @id='acceptAllButton' or contains(@class,'osano-cm-accept')]");
		shacSpinner = By.xpath("//form//i[@class='fas fa-spinner fa-spin']");
	}

	public CommonPage(World world) {
		super(world, world.driver);
		this.world = world;
		InitElements();
	}

	// This method is to handle the Bit Defender security popup if displayed
    public void handleBitDefenderSecurity() {
		if (isElementDisplayedAndEnabled(bitDefenderTitle, 1)) {
			click(bitDefenderHandler);
		}
	}

	// the below method is to accept the cookies in TBB application
	@Override
    public void acceptAllCookies() {
		logger.info("Accepting all cookies if popup appears");
		if (isElementDisplayedAndEnabled(buttonAcceptAllCookies, 10))
			click(buttonAcceptAllCookies);
	}

	// This method is used to validate an element appearance and disappearance, for
	// example - Spinner
	public void verifyElementAppearanceAndDisappearance(By locator, int timeoutSecs) {
		try {
			logger.info("Waiting max " + timeoutSecs + "secs. for Spinner to go away...");
			if (isElementDisplayedAndEnabled(locator, Waits.MIN_WAIT))
				waitForElementInvisibility(locator, timeoutSecs);
		} catch (Exception ignored) {
			throw new ApplicationTooSlowException(
					"Applicaiton is taking too long for element disappearance -" + locator.toString());
		}
	}

	// This method is used to wait if spinner is displayed on application
	public void validateLoadingSpinnerByApplicationName(String applicationName, int... optionalWaitSecs) {
		int seconds = (optionalWaitSecs.length > 0) ? optionalWaitSecs[0] : Waits.ELEMENT_VISIBILITY_WAIT;

		switch (applicationName) {
		case "TBB":
          verifyElementAppearanceAndDisappearance(tbbSpinner, seconds);
          // home.body.imgSpinner.waitWhile();

			break;
		case "COO":
			verifyElementAppearanceAndDisappearance(loadingSymbol, seconds);
			break;
		case "SHAC":
			verifyElementAppearanceAndDisappearance(shacSpinner, seconds);
			break;
		default:
			throw new AutomationIssueException(
					"Application name is not valid, kindly pass the correct app name: " + applicationName);
		}
	}

	// This methos is to select value from dropdown based on index for Mobile
	// devices
	public boolean selectByIndexFromMobileFauxDropDown(String xPath, int index) {
		try {
			if (world.isMobile()) {
				scrollByActionAndNativeClick(By.xpath(xPath + fauxSelectValueFromListForMobile), 5);
				List<WebElement> productOptions = getPresentElements(By.xpath(productOptionListForMobile));
				for (WebElement productOpt : productOptions) {
					if (productOpt.getAttribute("class").equalsIgnoreCase(" ")) {
						if (!productOpt.getAttribute("data-value").isEmpty()) {
							productOpt.click();
							break;
						}
					} else if (productOpt.getAttribute("class").toLowerCase().contains("active")) {
						if (!productOpt.getAttribute("data-value").isEmpty()) {
							productOpt.click();
							break;
						}
					}
				}
				return true;
			} else {
				scrollElementIntoViewJS(By.xpath(xPath), true);
				selectByIndexInDropdown(By.xpath(xPath), index);
			}
			return true;
		} catch (Exception e) {
			failScenarioAndReportInfo(this.getClass().getSimpleName() + " >> " + world.getMyMethodName(), e);
		}
		return false;
	}

	// This method is to select product from drop down for mobile device only.
	public boolean selectFromMobileFauxDropdown(String xPath, String valueToSelect) {
		try {
			logger.info("Select from mobile faux dropdown using xpath with value : " + valueToSelect + "");
			logger.info("Clicking link using xpath : " + xPath + " with appended framed path");
			if (world.getBrowser() == Constants.BROWSER.SAFARI && world.isMobile()) {
				clickByJS(By.xpath(xPath + fauxDropDownForMobile));

			} else {
				click(By.xpath(xPath + fauxDropDownForMobile));
			}
			logger.info(
					"Clicking element using xpath and sending @data-value attribute value as : " + valueToSelect + "");
			click(By.xpath("//li[@data-value='" + valueToSelect + "']"));
			return true;
		} catch (Exception e) {
			failScenarioAndReportInfo(this.getClass().getSimpleName() + " >> " + world.getMyMethodName(), e);
		}
		return false;
	}

	@Deprecated
	// compare retail price and sub total
	public boolean compareTwoPrices(String firstPrice, String secondPrice, String qty) {
		try {
			logger.info("comparing the two prices");
			int quantity = Integer.parseInt(qty.trim());
			Double retailsPrice = Double.parseDouble(firstPrice.trim());
			Double subtotal = Double.parseDouble(secondPrice.trim());
			Double expectedSubtotal = quantity > 1 ? Math.floor(retailsPrice * quantity * 100) / 100
					: retailsPrice * quantity;
			if (expectedSubtotal.equals(subtotal)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			failScenarioAndReportInfo("Verify Prices\n", e);
		}
		return false;
	}

	// compare two prices
	public boolean compareTwoPrices(String firstPrice, String secondPrice) {
		Double retailsPrice = null;
		Double subtotal = null;
		try {
			logger.info("comparing the two prices");
			retailsPrice = Double.parseDouble(firstPrice.trim());
			subtotal = Double.parseDouble(secondPrice.trim());
			if (retailsPrice.equals(subtotal)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppIssueException("Retail price (" + retailsPrice + ") and subtotal (" + subtotal
					+ ") are not same in OrderSummery on shippingpage");
		}
		return false;
	}

	// compare retail price and sub total
	public boolean compareTwoPrices(double firstPrice, double secondPrice, int qty) {
		logger.info("comparing the two prices");
		Double expectedSubtotal = qty > 1 ? Math.floor(firstPrice * qty * 100) / 100 : firstPrice * qty;
		return expectedSubtotal.equals(secondPrice);
	}

	// calculating discount and returns a double value
	public double getDiscountPrice(String price, String percentage) {
		try {
			logger.info("calculating the discount percentage on price");
			double percentageValue = Double.parseDouble(percentage);
			double priceValue = Double.parseDouble(price);
			double finalDiscountPrice = priceValue * percentageValue / 100;
			return priceValue - finalDiscountPrice;
		} catch (Exception e) {
			failScenarioAndReportInfo(this.getClass().getSimpleName() + " >> " + world.getMyMethodName(), e);
		}
		return 0;
	}

	// Verify expected message with text from Element removing spaces, as actual
	// text from safari browser is different
    public boolean verifyMessageWithElementTextRemovingSpaces(By locator, String expectedMessage) {
		String actualMessage = "";
		try {
			scrollElementIntoViewJS(locator);
			actualMessage = getTextFromElement(locator).trim();
			if (expectedMessage.replaceAll(" ", "").equalsIgnoreCase(actualMessage.replaceAll(" ", ""))) {
				return true;
			} else {
				throw new AppIssueException(
						"Expected message (" + expectedMessage + ") not matched with actual (" + actualMessage + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;

	}

	// verifies disclaimer text
	public void verifyDisclaimerText(String productName, By locatorName, String pageName) {
		try {
			logger.info("Verifying disclaimers text for " + productName + " on " + pageName);
			String expectedBODMembershipDisclaimer = world.getLocaleResource().getString(productName);
			verifyElementTextContainsExpectedText(expectedBODMembershipDisclaimer, locatorName);
			logger.info("productName Disclaimers are displayed on " + pageName);
		} catch (Exception e) {
			failScenarioAndReportInfo(this.getClass().getSimpleName() + " >> " + world.getMyMethodName(), e);
		}
	}

	// To validate the price in the element
	public boolean verifyPriceGreaterThanZero(By locatorName) {
		try {
			logger.info("Verifying Price from element " + locatorName);
			String price = getTextFromElement(locatorName).replaceAll("[^0-9.,]", "").replaceAll(",", ".");
			logger.info("Verifying Price- " + price);
			if (Double.parseDouble(price) > 0)
				return true;
		} catch (Exception e) {
			failScenarioAndReportInfo(this.getClass().getSimpleName() + " >> " + world.getMyMethodName(), e);
		}
		return false;
	}

	public boolean isMiniCartDisplayed() {
		boolean displayed = isElementDisplayedWithLeastWait(
				By.xpath("//div[@id='miniCartData'][contains(@style,'opacity: 1')]"));
		String status = (displayed) ? "displayed" : "not displayed";
		logger.info("Minicart is " + status);
		return displayed;
	}

    public void closeMiniCart() {
		logger.info("Closing mini cart...");
		By miniCartClose = By.xpath("//section[@data-track='mini-cart']//p[@class='right close-mini-cart']");
		if (isMiniCartDisplayed()) {
			click(miniCartClose);
		}
	}

    public void openMiniCart() {
		logger.info("Opening mini cart...");
		By miniCart = By.xpath("//a[@data-track='cart-link']");
		letMeFinishLoading();
		click(miniCart);
	}

	// Searches supplied Directory for files with specified file extension and
	// deletes the file
	public boolean isFileExtensionDownloaded(String downloadPath, String fileExtension, boolean delete) {
		File[] listOfFiles = null;
		int count = 0;
		do {
			logger.info("Searching Directory for Files");
			File directory = new File(downloadPath);
			listOfFiles = directory.listFiles();
			count++;
			pause(2000);
		} while (listOfFiles.length <= 0 && count < 30);

		for (File downloadedFile : listOfFiles) {
			logger.info("Found some files in the dir, checking if they match extension: " + fileExtension);
			if (downloadedFile.isFile()) {
				String fileName = downloadedFile.getName();
				if (fileName.contains(fileExtension)) {
					logger.info("File found: " + fileName);
					File file = new File(downloadPath + fileName);
					if (file.exists() && delete == true) {
						logger.info("Deleting " + fileName);
						file.delete();
						return true;
					}
				}
			}
		}
		logger.info("Could Not find Export Report File by File Extension: " + fileExtension);
		return false;
	}

	// returns the number of row from a table, also clicks on pagination button and
	// gets total count
	public int getRowCountFromPaginatedWebTable(By rowElement, By nextPageButton) {
		logger.info("Getting total of elements while paginating");
		int tries = 0;
		int pageTotalElements;
		int totalElements = 0;
		int initialPageElementCount = getPresentElements(rowElement).size();
		if (initialPageElementCount <= 0) {
			logger.info("Found No elements");
			return totalElements;
		} else {
			if (isElementDisplayedAndEnabled(nextPageButton)) {
				scrollElementIntoViewJS(nextPageButton, false);
				while (isElementDisplayedAndEnabled(nextPageButton) && tries < 150) {
					List<WebElement> currentPageRows = getPresentElements(rowElement);
					pageTotalElements = currentPageRows.size();
					logger.info("Found " + pageTotalElements + " elements on this page");
					totalElements += pageTotalElements;
					logger.info("Total elements currently is " + totalElements);
					click(nextPageButton);
					tries++;
					letMeFinishLoading();
					if (isElementDisplayedAndEnabled(nextPageButton)) {
						scrollElementIntoViewJS(nextPageButton, false);
					} else {
						break;
					}
				}
				List<WebElement> lastPageElements = getPresentElements(rowElement);
				totalElements += lastPageElements.size();
				logger.info("Found a total of " + totalElements + " elements");
				return totalElements;
			} else {
				logger.info("Found " + initialPageElementCount + " elements on the only page");
				return initialPageElementCount;
			}
		}
	}

	// Downloads the file(s) to default download location
	public void downloadFilesToLocal(List<String> downloadFileUrls) {
		String skipWarningXpath = "//div[@id='bb-com-modal-mysite-warning']//button[contains(@class,'button--cancel')]";
		for (String fileUrl : downloadFileUrls) {
			logger.info("Downloading file from : " + fileUrl);
			if (world.getBrowser() == Constants.BROWSER.FIREFOX) {
				try {
					world.driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
					world.driver.get(fileUrl);
				} catch (TimeoutException toe) {
					// DO nothing. this is a known issue with firefox
				} finally {
					world.driver.manage().timeouts().pageLoadTimeout(Waits.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
				}
			} else {
				// Download file a folder of the local machine
				world.driver.get(fileUrl);
			}
			if (isAlertPresent(5)) {
				acceptAlert();
			}
			// closing warning
			if (isElementDisplayedAndEnabled(By.xpath(skipWarningXpath), 3)) {
				world.driver.findElement(By.xpath(skipWarningXpath)).click();
			}
			// Pausing to make sure the file is downloaded successfully
			pause(4000);
		}
	}

	// Uploads file(s) from the UI.
	public boolean uploadFilesFromUI(List<String> fileNames, By fileElementLocator) {
		try {
			WebElement uploadFileElement = world.driver.findElement(fileElementLocator);
			String fileDetails = "";
			// Building file path for all files to be uploaded
			// Supports Sauce execution and local execution on windows
			for (String fileName : fileNames) {
				if (world.getDriverType().equals(Constants.DRIVERTYPE.SAUCE)) {
					if (world.getBrowser() == Constants.BROWSER.SAFARI) {
						fileDetails = fileDetails + "/Users/chef/Downloads/" + fileName + "\n";
					} else {
						fileDetails = fileDetails + "C:\\Users\\Administrator\\Downloads\\" + fileName + "\n";
					}
				} else {
					fileDetails = fileDetails + System.getProperty("user.home") + "\\Downloads\\" + fileName + "\n";
				}
			}
			logger.info("Uploading Files : " + fileDetails);
			if (world.getDriverType().equals(Constants.DRIVERTYPE.SAUCE)) {
				LocalFileDetector detector = new LocalFileDetector();
				if (uploadFileElement instanceof RemoteWebElement)
					((RemoteWebElement) uploadFileElement).setFileDetector(detector);
				if (world.getBrowser() == Constants.BROWSER.SAFARI) {
					executeJavaScript("arguments[0].focus();", uploadFileElement);
					executeJavaScript("arguments[0].style.display = 'block';", uploadFileElement);
				}
			}
			// removing last line separator "\n" from the string, can not pass an empty line
			uploadFileElement.sendKeys(fileDetails.substring(0, fileDetails.length() - 1));
		} catch (NoSuchElementException ex) {
			if (world.getBrowser() == Constants.BROWSER.SAFARI) {
				// Do nothing, even on success multi file upload on safari we are facing
				// NoSuchElementException, eating the exception for now
				return true;
			} else {
				throw ex;
			}
		}
		return true;
	}

	// Set the customer type value in the CustomerDetails map of world
	public void setCustomerType(String customerType) {
		logger.info("Setting the customer type to " + customerType);
		String existingCustType = world.getCustomerDetails().get("CustomerType");
		switch (customerType.toLowerCase()) {
		case "club":
			if (existingCustType != null && (!existingCustType.contains("CLUB"))) {
				world.getCustomerDetails().put("CustomerType", existingCustType + ",CLUB");
			}
			break;
		default:
			throw new AutomationIssueException("Customer Type '" + customerType + "' is invalid!");
		}
	}

	// Bypass captcha by manipulating the url
	public void bypassCaptcha() {
      String bypassKey = "?bypassCaptcha=remove";
		String url = null;
			url = world.driver.getCurrentUrl();
			// added by snelson to stop adding bypassCaptcha too many time to the same url,
			// once is enough ...
			if (url.contains("bypassCaptcha"))
				return;
			// -----------------------------------------------------------------
            url = url.contains("?") ? url.replace("?", bypassKey + "&") : url + bypassKey;
			navigateToUrl(url);
	}

	// Bypass captcha by manipulating the url
	public void bypassCaptchaV2Remove() {
		String bypassKey = world.tbbTestDataObject.bypassCaptchaRemoveV2;
		String url = world.driver.getCurrentUrl();
		if (url.contains("bypassCaptcha")) return;
		url = url.contains("?") ? url.replace("?", bypassKey + "&") : url + bypassKey;
		navigateToUrl(url);
	}

	// Bypass captcha by manipulating the url
	public void bypassCaptchaV3True() {
		String bypassKey = world.tbbTestDataObject.bypassCaptchaTrueV3;
		String url = world.driver.getCurrentUrl();
		if (url.contains("bypassCaptcha")) return;
		url = url.contains("?") ? url.replace("?", bypassKey + "&") : url + bypassKey;
		navigateToUrl(url);
	}

    public void verifyMiniCartClosedInFewSecs() {
        logger.info("Checking mini cart is automatically closed in few seconds");
        By miniCartLocator = By.xpath("//div[@id='miniCartData'][contains(@style,'opacity: 1')]");
        try {
            if (isElementDisplayedAndEnabled(miniCartLocator,Waits.MIN_WAIT))
                waitForElementInvisibility(miniCartLocator, waits.MEDIUM_WAIT);
            letMeFinishLoading();
        } catch (Exception ignored) {
            throw new AppIssueException("Mini Cart Not Closed Automatically");
        }
    }
}
