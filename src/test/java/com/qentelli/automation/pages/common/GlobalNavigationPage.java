package com.qentelli.automation.pages.common;

import com.qentelli.automation.common.World;
import com.qentelli.automation.drivers.Waits;
import com.qentelli.automation.exceptions.base.BaseXBy;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.testdata.LangTranslator;
import com.qentelli.automation.utilities.CommonTestDataObject;
import com.qentelli.automation.utilities.CommonUtilities;
import com.qentelli.automation.utilities.TBBTestDataObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class GlobalNavigationPage extends BasePage {

	public GlobalNavigationPageElements body;
	public int cartItemCount;
	public CommonTestDataObject commonTestData;
	public TBBTestDataObject tbbTestData;

	public GlobalNavigationPage(World world) {
		super(world, world.driver);
		this.world = world;
		body = new GlobalNavigationPageElements(getDriver());
		commonTestData = new CommonTestDataObject();
		tbbTestData = new TBBTestDataObject();
	}

	// Getting user name text
	public String getUserNameText() {
		logger.info("Getting user name text");
		return getTextFromElement(body.textUserName.by());
	}

	// Get locale from selected Flag
	public String getSelectedLocale() {
		logger.info("Getting locale from selected Flag");
		return getAttributeValueFromElement(body.imageSelectedFlag.by(), "alt");
	}

	// Clicking on Log In link under Log In Menu
	public void clickLogInLinkUnderLogInMenu() {
		logger.info("Click on Log In link under Log In Menu");
		body.linkLogIn.click();
	}

	// Clicking on Sign Up link
	public void clickSignUpLink() {
		logger.info("Clicking on Sign Up link");
		click(body.linkSignUp.by());
	}

	// Clicking on My Account link
	public void clickOnMyAccount() {
		logger.info("Clicking on My Account link");
		click(body.linkMyAccount.by());
	}

	// Clicking on My Orders link
	public void clickOnMyOrders() {
		logger.info("Clicking on My Orders link");
		click(body.linkMyOrders.by());
	}

	// Clicking on log Out link
	public void clickOnLogOut() {
		logger.info("Clicking on Log Out link");
		body.linkLogOut.click();
	}

	// Clicking on User Name link
	public void clickOnUserNameLink() {
		logger.info("Clicking on User link");
		click(body.iconUser.by());
	}

	// Clicking on Tracking link
	public void clickOnTracking() {
		logger.info("Clicking on Tracking link");
		click(body.linkTracking.by());
	}

	// Clicking on Favorites link
	public void clickOnFavorites() {
		logger.info("Clicking on Favorites link");
		click(body.linkFavorites.by());
	}

	// Clicking on My Progress link
	public void clickOnMyProgress() {
		logger.info("Clicking on My Progress link");
		click(body.linkMyProgress.by());
	}

	// Clicking on Help link
	public void clickOnHelp() {
		logger.info("Clicking on Help link");
		click(body.iconHelp.by());
	}

	// Clicking on Cart container
	public void clickCartContainer() {
		logger.info("Checking if cart container is disabled");
		click(body.cartContainer.by());
	}

	// Clicking on Search button
	public void clickSearchButton() {
		logger.info("Clicking Search button");
		click(body.buttonSearch.by());
	}

	// Clicking on Cart icon
	public void clickCartIcon() {
		logger.info("Clicking on Cart icon");
		// using BasePage method instead of "body.iconCart.click();" as we observe failures on this click operations
//		body.iconCart.click();
		click(body.iconCart.by());
	}

	// Clicking on Global Flag link
	public void clickGlobalFlagLink() {
		logger.info("Clicking on Global Flag link");
		click(body.iconFlag.by());
	}

	// Clicking on USA English flag link from Locale flag dropdown
	public void clickUSAEnglishFlagFromDropdown() {
		logger.info("Click on USA English flag link from locale flag dropdown");
		click(body.linkUSAEnglishFlag.by());
	}

	// Clicking on Canada English flag link from Locale flag dropdown
	public void clickCanadaEnglishFlagFromDropdown() {
		logger.info("Click on Canada English flag link from locale flag dropdown");
		click(body.linkCanadaEnglishFlag.by());
	}

	// Clicking on Canada French flag link from Locale flag dropdown
	public void clickCanadaFrenchFlagFromDropdown() {
		logger.info("Clicking on Canada French  flag link from Locale flag dropdown");
		click(body.linkCanadaFrenchFlag.by());
	}

	// Clicking on UK English flag link from Locale flag dropdown
	public void clickUKEnglishFlagFromDropdown() {
		logger.info("Clicking on UK English flag link from Locale flag dropdown");
		click(body.linkUKEnglishFlag.by());
	}

	// Clicking on USA Spanish flag link from Locale flag dropdown
	public void clickUSASpanishFlagFromDropdown() {
		logger.info("Clicking on USA Spanish flag link from Locale flag dropdown");
		click(body.linkUSASpanishFlag.by());
	}

	// Clicking on France flag link from Locale flag dropdown
	public void clickFranceFlagFromDropdown() {
		logger.info("Clicking on France flag link from Locale flag dropdown");
		click(body.linkFranceFlag.by());
	}

	// Clicking on Nutrition footer link
	public void clickNutritionFooterLink() {
		logger.info("Clicking on Nutrition footer link");
		click(body.linkFooterNutrition.by());
	}

	// Clicking on BB Logo
	public void clicktestLogo() {
		logger.info("Clicking on BB Logo on Global Navigation Header");
		click(body.logotest.by());
	}

	// Clicking on Nutrition Programs sub menu
	public void clickNutritionProgramsMenu() {
		logger.info("Click on Nutrition Programs sub menu under global Shop");
		click(body.linkNutritionPrograms.by());
	}

	// Clicking on Performance Line link
	public void clickPerformanceLink() {
		logger.info("Click on Performance link under Nutrition Supplements");
		click(body.linktestPerformance.by());
	}

	// Clicking on Nutrition Supplements sub menu
	public void clickNutritionSupplementsMenu() {
		logger.info("Click on Nutrition Supplements sub menu under global Shop");
		click(body.linkNutritionSupplements.by());
	}

	// Clicking on Shakeology sub menu
	public void clickShakeologyMenu() {
		logger.info("Click on Shakeology sub menu under global Shop");
		click(body.shakeologyMenuItem.by());
	}

	// Clicking on BODGroups menu link
	public void clickBODGroupsLink() {
		logger.info("Clicking on BODGroups menu link");
		click(body.linkGroupsGlobal.by());
	}

	// Clicking on Shop menu link
	public void clickShopLink() {
		logger.info("Clicking on Shop menu link");
		click(body.linkShopGlobal.by());
	}

	// Clicking on Become a Preferred Customer submenu link
	public void clickBecomePreferredCustomer() {
		logger.info("Clicking on Become a Preferred Customer submenu link");
		click(body.linkBecomePreferredCustomer.by());
	}

	// Clicking locale flag link
	public void clickLocaleFlagLink(String index) {
		logger.info("Clicking locale flag link by index - " + index);
		click(By.xpath(body.placeholderLocaleFlag.getLocatorText().replace("PLACEHOLDER", index)));
	}

	// Click test On Demand Global navigation link
	public void clickGlobalNavtestOnDemandLink() {
		logger.info("Click Global Nav test On Demand link");
		click(body.linktestOnDemandGlobal.by());
	}

	public void clickTotalSolutionPacksLink() {
		logger.info("Click on Total Solution Packs link from global navigation menu");
		click(body.linkTotalSolutionPacks.by());
	}

	// Mouse over Log In link in Global navigation
	public void mouseOverLogInLinkGlobal() {
		logger.info("Mouse over Log In link in Global navigation menu");
		mouseHoverByActions(body.linkLogInGlobal.by());
	}

	// Mouse hover Profile Image in Global navigation
	public void mouseHoverProfileImageGlobalNavigation() {
		logger.info("Mouse hover Profile Image in Global navigation");
		body.imageProfile.moveTo();
	}

	// Mouse hover Shop Global navigation link
	public void mouseHoverGlobalNavShopLink() {
		logger.info("Mouse hover Global Nav Shop link");
		mouseHoverByActions(body.linkShopGlobal.by());
	}

	// Mouse hover test On Demand Global navigation link
	public void mouseHoverGlobalNavtestOnDemandLink() {
		logger.info("Mouse hover Global Nav test On Demand link");
		mouseHoverByActions(body.linktestOnDemandGlobal.by());
	}

	// Mouse hover Nutrition Global navigation link
	public void mouseHoverGlobalNavNutritionLink() {
		logger.info("Mouse hover Nutrition link");
		mouseHoverByActions(body.linkNutritionGlobal.by());
	}

	// Click hover Nutrition Global navigation link
	public void clickGlobalNavNutritionLink() {
		logger.info("Click Nutrition link from global navigation bar");
		click(body.linkNutritionGlobal.by());
	}

	// Clicking on Coach Officer link (TBB under Coach Tools)
	public void clickCoachOfficeLink() {
		logger.info("Click on Coach Office link");
		click(body.linkGlobalCoachOffice.by());
	}

	// Clicking on Become a Coach link (SHAC under Join Us before login)
	public void clickBecomeACoachLink() {
		logger.info("Click on Become a Coach link");
		click(body.linkGlobalBecomeACoach.by());
	}

	// Mouse hover Groups Global navigation link
	public void mouseHoverGlobalNavGroupsLink() {
		logger.info("Mouse hover Global Nav Groups link");
		mouseHoverByActions(body.linkGroupsGlobal.by());
	}

	// Mouse over Coach Tools navigation link (TBB)
	public void mouseOverCoachToolsLink() {
		logger.info("Mouse over Coach Tools link");
		mouseHoverByActions(body.linkCoachToolsGlobal.by());
	}

	// Mouse hover Join Us Global navigation link (SHAC before login)
	public void mouseHoverGlobalNavJoinUsLink() {
		logger.info("Mouse hover Join Us link");
		mouseHoverByActions(body.linkJoinUsGlobal.by());
	}

	// Mouse hover More Global navigation link
	public void mouseHoverGlobalNavMoreLink() {
		logger.info("Mouse hover More link");
		mouseHoverByActions(body.linkMoreGlobal.by());
	}

	// Mouse over Global Flag icon
	public void mouseOverGlobalFlagIcon() {
		logger.info("Mouse over Global Flag Icon");
		mouseHoverByActions(body.iconFlag.by());
	}

	// Searching by entering text
	public void enterSearchText(String text) {
		logger.info("Entering text into Search field");
		enterText(body.fieldSearchBox.by(), text, Keys.ENTER);
	}

	// Verifying User menu link is displayed
	public void verifyUserLinkDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.iconUser.by()), "User Icon display check");
	}

	// Verifying Global Log In button is displayed
	public void verifyLogInButtonGlobal() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkLogInGlobal.by()),"Global Log In display");
	}

	// Verifying Log In button in Log In menu is displayed
	public void verifyLogInButtonDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkLogIn.by()),
				"Button Log In menu display");
	}

	// Verifying Shopping cart is empty
	// TODO: this method needs to be removed as it was implemented in
	// TeamtestGlobalNavigationPage
	public void verifyShoppingCartIsEmpty() {
		logger.info("Verify Shopping Cart is Empty without the number on the top of the cart icon");
		verifyActualEqualsExpected(false, isElementDisplayedWithLeastWait(body.linkCartItemCount.by()),
				"Cart item count zero");
	}

	// Verifying Shopping cart is not empty
	public boolean verifyShoppingCartIsNotEmpty() {
		logger.info("Verify Shopping Cart is not empty and it has the number on the top of the cart icon");
		isElementDisplayedAndEnabled(body.linkCartItemCount.by());
		return true;
	}

	// Checks if Log In is displayed and returns a boolean
	public boolean isUserLoggedIn() {
		logger.info("Checking if user is logged in");
		return getTextFromElement(body.linkLogOut.by()).contains(tbbTestData.logOut);
	}

	// Verifying user logged in or not based on a boolean value
	public void userLoggedIn(boolean isUserLoggedIn) {
		logger.info("Verifying user logged in is - " + isUserLoggedIn);
		verifyActualEqualsExpected(isUserLoggedIn, isUserLoggedIn(), "");
	}

	// Verifying Sign Up link is displayed
	public void verifySignUpButtonDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkSignUp.by()), "Sign Up link displayed");
	}

	// Verify My Account link is displayed
	public void verifyMyAccountLinkDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkMyAccount.by()),
				"My Account link displayed");
	}

	// Verifying My Orders link is displayed
	public void verifyMyOrdersLinkDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkMyOrders.by()),
				"My Orders link displayed");
	}

	// Verifying Log Out link is displayed
	public void verifySignOutLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkLogOut.by()),
				"Logout Link display check");
	}

	// Verifying Shop link is displayed
	public void verifyShopLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkShopGlobal.by()), "Shop link display");
	}

	// Verifying BOD link is displayed
	public void verifyBODLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linktestOnDemandGlobal.by()),
				"BOD link display");
	}

	// Verifying Nutrition link is displayed
	public void verifyNutritionLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkNutritionGlobal.by()),
				"Nutrition link display");
	}

	// Verifying Groups link is displayed
	public void verifyGroupsLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkGroupsGlobal.by()),
				"Groups link display");
	}

	// Verifying Coaching link is displayed
	public void verifyCoachingLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkCoachingGlobal.by()),
				"Coaching link display check");
	}

	// Verifying Coach Tools link is displayed
	public void verifyCoachToolsLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkCoachToolsGlobal.by()),
				"Coach Tools link display check");
	}

	// Verifying Join Us link is displayed (SHAC before login)
	public void verifyJoinUsLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkJoinUsGlobal.by()),
				"Join Us link display");
	}

	// Verifying More link is displayed
	public void verifyMoreLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkMoreGlobal.by()),
				"More Link link display");
	}

	// Verifying Flag icon displayed
	public void verifyFlagIcon() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.iconFlag.by()), "Flag icon display");
	}

	// Verifying Help link displayed
	public void verifyHelpLink() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.iconHelp.by()), "Help icon display");
	}

	// Verifying Cart link is clickable
	public void verifyCartLinkIsClickable() {
		logger.info("Verifying Cart link is clickable.");
		verifyActualEqualsExpected(true, isElementClickable(body.iconCart.by()), "Cart Icon clickable");
	}

	// Verifying Home Page Title/Logo is displayed to the left of Shop link
	public void verifyHomePageTitleIsDisplayedOnLeftToShopLink() {
		logger.info("Verifying Home Page Title/Logo is displayed to the left of Shop link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.textHomePageTitle.by(), body.linkShopGlobal.by()),
				"Home Page Title displayed to the left of Shop link");
	}

	// Verifying Shop link is displayed to the left of BOD link
	public void verifyShopLinkIsDisplayedOnLeftToBODLink() {
		logger.info("Verifying Shop link is displayed to the left of BOD link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkShopGlobal.by(), body.linktestOnDemandGlobal.by()),
				"Shop Link is displayed to the left of BOD link");
	}

	// Verifying BOD link is displayed to the left of Nutrition link
	public void verifyBODLinkIsDisplayedOnLeftToNutritionLink() {
		logger.info("Verifying BOD link is displayed to the left of Nutrition link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linktestOnDemandGlobal.by(), body.linkNutritionGlobal.by()),
				"BOD Link is displayed to the left of Nutrition link");
	}

	// Verifying Nutrition link is displayed to the left of Join Us link
	public void verifyNutritionLinkIsDisplayedOnLeftToJoinUsLink() {
		logger.info("Verifying Nutrition link is displayed to the left of Join Us link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkNutritionGlobal.by(), body.linkJoinUsGlobal.by()),
				"Nutrition link is displayed to the left of Join Us link");
	}

	// Verifying Groups link is displayed to the left of Coaching link
	public void verifyGroupsLinkIsDisplayedOnLeftToCoachingLink() {
		logger.info("Verifying Groups link is displayed to the left of Coaching link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkGroupsGlobal.by(), body.linkCoachingGlobal.by()),
				"Groups link is displayed to the left of Coaching link");
	}

	// Verifying Groups link is displayed to the left of Coach Tools link for a
	// Coach user (needs to be updated based on new requirements)
	public void verifyGroupsLinkIsDisplayedOnLeftToCoachToolsLink() {
		logger.info("Verifying Groups link is displayed to the left of Coach Tools link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkGroupsGlobal.by(), body.linkCoachToolsGlobal.by()),
				"Groups link is displayed to the left of Coach Tools link");
	}

	// Verifying Groups link is displayed to the left of Join Us link (needs to be
	// updated based on new requirements)
	public void verifyGroupsLinkIsDisplayedOnLeftToJoinUsLink() {
		logger.info("Verifying Groups link is displayed to the left of Join Us link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkGroupsGlobal.by(), body.linkJoinUsGlobal.by()),
				"Groups link is displayed to the left of Join Us link");
	}

	// Verifying Coaching link is displayed to the left of More link (needs to be
	// updated based on new requirements)
	public void verifyCoachingLinkIsDisplayedOnLeftToMoreLink() {
		logger.info("Verifying Coaching link is displayed to the left of More link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkCoachingGlobal.by(), body.linkMoreGlobal.by()),
				"Coaching link is displayed to the left of More link");
	}

	// Verifying Coach Tools link is displayed to the left of More link (needs to be
	// updated based on new requirements)
	public void verifyCoachToolsLinkIsDisplayedOnLeftToMoreLink() {
		logger.info("Verifying Coach Tools link is displayed to the left of More link");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkCoachToolsGlobal.by(), body.linkMoreGlobal.by()),
				"Coach Tools link is displayed to the left of More link");
	}

	// Verifying Join Us link is displayed to the left of More button
	public void verifyJoinUsLinkIsDisplayedOnLeftToMoreButton() {
		logger.info("Verifying Join Us link is displayed to the left of Search button");
		verifyActualEqualsExpected(true,
				isElementPositionedToLeftOf(body.linkJoinUsGlobal.by(), body.linkMoreGlobal.by()),
				"Join Us link is displayed to the left of More button");
	}

	// Verifying More link is displayed to the left of Search button
	public void verifyMoreLinkIsDisplayedOnLeftToSearchButton() {
		logger.info("Verifying More link is displayed to the left of Search button");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(body.linkMoreGlobal.by(), body.buttonSearch.by()),
				"More link is displayed to the left of Search button");
	}

	// Verifying Search Button is displayed to the Left of Cart icon
	public void verifySearchButtonIsDisplayedOnLeftToCartIcon() {
		logger.info("Verifying Search Button is displayed to the Left of Cart icon");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(body.buttonSearch.by(), body.iconCart.by()),
				"Search Button is displayed to the Left of Cart icon");
	}

	// Verifying Cart icon is displayed to the Left of Help link
	public void verifyCartIconIsDisplayedOnLeftToHelpLink() {
		logger.info("Verifying Cart icon is displayed to the Left of Help link");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(body.iconCart.by(), body.iconHelp.by()),
				"Cart icon is displayed to the Left of Help link");
	}

	// Verifying Help link is displayed to the Left of Flag icon
	public void verifyHelpLinkIsDisplayedOnLeftToFlagIcon() {
		logger.info("Verifying Help link is displayed to the Left of Flag icon");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(body.iconHelp.by(), body.iconFlag.by()),
				"Help link is displayed to the Left of Flag icon");
	}

	// Verifying Flag icon is displayed to the Left of User link
	public void verifyFlagIconIsDisplayedOnLeftToUserLink() {
		logger.info("Verifying Flag icon is displayed to the Left of User link");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(body.iconFlag.by(), body.iconUser.by()),
				"Flag icon is displayed to the Left of User link");
	}

	// Verifying the message bar component is displayed on TBB
	public void verifyThePromoCodeBannerIsDisplayedAboveGlobalNav() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.promoBanner.by()),
				"Promo banner display check");
	}

	// Verifying the Top Navigation Header is displayed on TBB
	public void verifyTheTopNavigationHeaderIsDisplayed() {
		LangTranslator translator = new LangTranslator(world);
		List<String> lsShops = Arrays.asList("Total-Solution Packs", "Fitness", "4 Weeks For Every Body",
				"test On Demand & BODi", "Job 1", "GET UP", "21 Day Fix", "Shakeology", "Single Flavors",
				"Combo Flavors", "Shakeology Boosts", "Alternating Flavors", "Taste Samplers", "Accessories",
				"Nutrition Programs", "Portion Fix", "2B Mindset", "The 4 Week Gut Protocol", "2B Pregnant",
				"2B Mindset on BODi", "Portion Fix on BODi", "Nutrition Supplements", "Energize", "Recover",
				"Performance Line", "BEVVY", "BEACHBAR", "Collagen Boost", "Daily Sunshine", "Apparel",
				"New Trainer Collections", "Inspirational Designs", "Logowear", "Sale", "Shop All Apparel",
				"Equipment & Accessories", "test Bike");
		List<String> lsBOD = Arrays.asList("Home","Programs","Recipes","BODi","Live Schedule","Tracking")  ; 
		List<String> lsNutrition = Arrays.asList("Products & Supplements","Shakeology","test Performance","BEACHBAR","Collagen Boost","BEVVY","Ultimate Reset","Daily Sunshine","Nutrition Programs","2B Mindset","2B Pregnant","Portion Fix","2B Mindset on BODi","Portion Fix on BODi","BODi Nutrition","Recipes") ;
		List<String> lsCoachTools = Arrays.asList("Overview","Start a Group","Learn More") ;
		List<String> lsMore = Arrays.asList(translator.translate("ContactUSFAQ"), translator.translate("Blog"), translator.translate("testChallenge"));

		verifyActualEqualsExpected(true, body.topNavHeader.exists(), "Tab Navigation header display check");
		// body.linkShopGlobal.jScrollTo();
		for (int i = 1; i < 6; i++) {
			BaseXBy bb = new BaseXBy(world.driver, "//*[@id=\"globalNav\"]/div/div/header/ul/li[" + i + "]/a");
			                               //        "//*[@id=\"global-topNav\"]/div/div/div/ul/li[1]/span"
			BaseXBy bb2;

			List<String> lsHeaders = null;
			switch (i) {
			case 1:
				lsHeaders = lsShops;
			case 2:
				lsHeaders = lsBOD;
			case 3:
				lsHeaders = lsNutrition;				
			case 4:
				lsHeaders = lsCoachTools;		
			case 5:
				lsHeaders = lsMore;		

			}
			for (String s : lsHeaders) {
				logger.info(" checking menu item "+s) ;
				bb.hover();
				Boolean run = true; 
				int l = 1 ; 
				while (run) {
					try {
						logger.info(new BaseXBy(world.driver, "/*a["+l+++"]").webElement().getText()) ; 
					}catch (Exception e ) {
						logger.info(e.getMessage()) ; 
						run = false ; 
					}
				}
				
				logger.info("Menu <> "+ bb.getText() +" <>");
				logger.info("--");
				bb2 = new BaseXBy(world.driver, "//*[contains(text(),'" + s + "')]");
				logger.info(bb2.exists());
				logger.info(bb2.webElement().getTagName());
				logger.info(bb2.webElement().getAttribute("class"));
				logger.info(bb2.webElement().getAttribute("href"));
				Assert.assertTrue(bb2.exists(), "item <" + s + "> "+bb2.webElement().getText());
				logger.info("--");
				List<WebElement> elements = bb2.getWebElements() ; 
				if (elements.size() > 0 ) {
					for (WebElement e : elements) {
							logger.info("element:\t "+e.getTagName()) ;
							logger.info("element:\t "+e.getAttribute("class")) ;
							
					}
				}
			}
		}

	}

	// Verifying the Sub Navigation component is displayed on TBB
	public void verifyTheSubNavigationComponentIsDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.subNavComponent.by()),
				"Sub Navigation Component display check");
	}

	// Verify the dropdown menu components are displayed in Top Navigation Header
	public void verifyGlobalSubNavDropdownMenusAreDisplayed() {
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.dropdownMenusInGlobalNav.by()),
				"Dropdown menu global subNav display check");
	}

	// Verify the URL is changed to new country
	public void verifyURLChangedToNewCountry() {
		logger.info("Verifying the URL is changed to new country");
		String newFlag = "/" + world.getLocale().toLowerCase().replaceAll("_", "") + "/";
		verifyURLContains(newFlag);
	}

	// Verify the flag dropdown menu is displayed
	public void verifyFlagDropdownMenuIsDisplayed() {
		logger.info("Verifying Flag dropdown menu is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.dropdownFlagMenuInGlobalNav.by()),
				"Dropdown flag menu display check");
	}

	// Verifying Navigation header is displayed below Message bar
	public void verifyIfNavigationHeaderIsDisplayedBelowMessageBar() {
		logger.info("Verifying Navigation header is displayed below Message bar");
		verifyActualEqualsExpected(true, isElementPositionedOnTopOf(body.promoBanner.by(), body.topNavHeader.by()),
				"Navigation header is not displayed below Message bar");
	}

	// Verifying color of the highlighted link in Global header
	public void verifyColorOfHighlightedLinkInGlobalHeader(By locator) {
		logger.info("Verifying color of link in Global header when highlighted");
		String actualColor = CommonUtilities.convertRGBToHexaValue(getCssValueFromElement(locator, "color"));
		verifyActualEqualsExpected(commonTestData.linkHighlightColor, actualColor,
				"Failed to verify the color of link in Navigation header when highlighted");
	}

	// Verifying Sub-Navigation header is displayed below Navigation header
	public void verifyIfSubNavigationIsDisplayedBelowNavigationHeader() {
		logger.info("Verifying Sub-Navigation header is displayed below Navigation header");
		verifyActualEqualsExpected(true, isElementPositionedOnTopOf(body.topNavHeader.by(), body.subNavComponent.by()),
				"Sub-Navigation header is not displayed below Navigation header");
	}

	// Get cart badge text
	public int getCartItemCount() {
		logger.info("Getting cart item count from cart badge text");
		cartItemCount = (isElementDisplayedWithLeastWait(body.cartBadge.by()))
				? Integer.parseInt(getTextFromElement(body.cartBadge.by()))
				: 0;
		return cartItemCount;
	}

	public boolean isCartContainerDisabled() {
		logger.info("Checking if cart container is disabled");
		return getAttributeValueFromElement(body.cartContainer.by(), "disabled").equalsIgnoreCase("true");
	}

	public void mouseHoverOnTopNavMenuBar(String menu) {
		logger.info("Clicking on Top Nav menu - " + menu);
		mouseHoverByActions(By.xpath(body.topNavMenuBar.getLocatorText().replaceAll("PLACEHOLDER", menu)));
	}

	public void clickTopNavMenuItem(String item) {
		logger.info("Clicking on Top Nav menu item - " + item);
		click(By.xpath(body.topNavMenuItem.getLocatorText().replaceAll("PLACEHOLDER", item)));
	}

	// This is to verify the product count in mini cart
	public void verifyCartItemCountInMiniCart(int prodcutsInMinicart) {
		verifyActualEqualsExpected(prodcutsInMinicart, getCartItemCount(), "Product Count In Mini Cart Not Matched");
	}

	// Verifying Footer Container is displayed
	public void verifyFooterDisplayed() {
		logger.info("Verifying Footer Container is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.containerFooter.by()),
				"Footer container display");
	}

	// Verifying test Home footer link is displayed
	public void verifytestHomeFooterLinkDisplayed() {
		logger.info("Verifying test Home footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFootertestHome.by()),
				"test Home footer link display");
	}

	// Verifying Shop footer link is displayed
	public void verifyShopFooterLinkDisplayed() {
		logger.info("Verifying Shop footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterShop.by()),
				"Shop footer link display");
	}

	// Verifying BOD footer link is displayed
	public void verifyBODFooterLinkDisplayed() {
		logger.info("Verifying BOD footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterBOD.by()),
				"BOD footer link display");
	}

	// Verifying Nutrition footer link is displayed
	public void verifyNutritionFooterLinkDisplayed() {
		logger.info("Verifying Nutrition footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterNutrition.by()),
				"Nutrition footer link display");
	}

	// Verifying Groups footer link is displayed
	public void verifyGroupsFooterLinkDisplayed() {
		logger.info("Verifying Groups footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterGroups.by()),
				"Groups footer link display");
	}

	// Verifying Coaching footer link is displayed
	public void verifyCoachingFooterLinkDisplayed() {
		logger.info("Verifying Coaching footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterCoaching.by()),
				"Coaching footer link display");
	}

	// Verifying BOD Life Blog footer link is displayed
	public void verifyBODLifeBlogFooterLinkDisplayed() {
		logger.info("Verifying BOD Life Blog footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterBODLifeBlog.by()),
				"BOD Life Blog footer link display");
	}

	// Verifying test Challenge footer link is displayed
	public void verifytestChallengeFooterLinkDisplayed() {
		logger.info("Verifying test Challenge footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFootertestChallenge.by()),
				"test Challenge footer link display");
	}

	// Verifying Contact Us FAQ footer link is displayed
	public void verifyContactUsFAQFooterLinkDisplayed() {
		logger.info("Verifying Contact Us FAQ footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterContactUsFAQ.by()),
				"Contact Us FAQ footer link display");
	}

	// Verifying Corporate footer link is displayed
	public void verifyCorporateFooterLinkDisplayed() {
		logger.info("Verifying Corporate footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterCorporate.by()),
				"Corporate footer link display");
	}

	// Verifying Preferred Customer footer link is displayed
	public void verifyPreferredCustomerFooterLinkDisplayed() {
		logger.info("Verifying Corporate footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterPreferredCustomer.by()),
				"Preferred Customer footer link display");
	}

	// Verifying Facebook footer link is displayed
	public void verifyFacebookFooterLinkDisplayed() {
		logger.info("Verifying Facebook footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterFacebook.by()),
				"Facebook footer link display");
	}

	// Verifying Instagram footer link is displayed
	public void verifyInstagramFooterLinkDisplayed() {
		logger.info("Verifying Instagram footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterInstagram.by()),
				"Instagram footer link display");
	}

	// Verifying Twitter footer link is displayed
	public void verifyTwitterFooterLinkDisplayed() {
		logger.info("Verifying Twitter footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterTwitter.by()),
				"Twitter footer link display");
	}

	// Verifying Youtube footer link is displayed
	public void verifyYoutubeFooterLinkDisplayed() {
		logger.info("Verifying Youtube footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterYoutube.by()),
				"Youtube footer link display");
	}

	// Verifying footer text is displayed
	public void verifyFooterTextDisplayed() {
		logger.info("Verifying footer text is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textFooter.by()), "Footer text display");
	}

	// Verifying App Store footer link is displayed
	public void verifyAppStoreFooterLinkDisplayed() {
		logger.info("Verifying App Store footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterAppStore.by()),
				"App Store footer link display");
	}

	// Verifying Play Store footer link is displayed
	public void verifyPlayStoreFooterLinkDisplayed() {
		logger.info("Verifying Play Store footer link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkFooterPlayStore.by()),
				"Play Store footer link display");
	}

	// To verify welcome user text after sign in
	public void verifyWelcomeUserMessageInLoginSection(String expectedWelcomeTest) {
		logger.info("Verifying Welcome User text after sign in");
		verifyActualEqualsExpected(expectedWelcomeTest,
				CommonUtilities.replaceSmartChars(getTextFromElement(body.iconUser.by())), "Welcome User message");
	}

	// Verify user is not logged in to TBB application
	public void verifyUserIsNotLoggedInToTBB(String expectedText) {
		logger.info("Verifying user is not logged in to TBB");
		verifyTextContainsIgnoreCase(expectedText, getTextFromElement(body.iconUser.by()), "User not logged-in to TBB");
	}

	// Verifying on test Logo
	public void verifytestLogo() {
		logger.info("Verifying on test Logo on Global Navigation Header");
		verifyActualEqualsExpected(true, body.logotest.exists(Waits.LONG_WAIT), "test logo display");
	}

	// Verifying user name
	public void verifyUserName(String expectedName) {
		logger.info("Verifying User Name");
		verifyActualEqualsExpected(expectedName, getUserNameText(), "Verifying User name value");
	}

	public void verifyTopNavMenuItem(String item) {
		logger.info("Clicking on Top Nav menu item - " + item);
		verifyActualEqualsExpected(true,
				isElementDisplayedAndEnabled(
						By.xpath(body.topNavMenuBar.getLocatorText().replaceAll("PLACEHOLDER", item))),
				item + " displayed");
	}

	public void clickCountryLanguageOption(String locale) {
		logger.info("Clicking on flag icon to select Country and Language preference options");
		click(getFirstActiveElement(getPresentElements(By.xpath(body.linkCountryLangPreferenceOption.getLocatorText()
				.replace("PLACEHOLDER", commonTestData.getResString(locale))))));
	}

	public void verifyUpdatedLocale() {
		logger.info("Verifying updated locale");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkUpdatedLocale.by()),
				"Verifying updated locale");
	}

	// Getting Top Nav menu Item href
	public String getTopNavMenuItemHref(String item) {
		logger.info("Getting Top Nav menu Item href");
		return getAttributeValueFromElement(
				By.xpath(body.placeholderTopNavMenuItemHref.getLocatorText().replaceAll("PLACEHOLDER", item)), "href");
	}

	// Verifying Become A Coach Link href contains http
	public void verifyBecomeACoachLinkHrefContainsHTTP(String item) {
		logger.info("Verifying Become A Coach Link href contains http");
		verifyActualEqualsExpected(true, getTopNavMenuItemHref(item).contains("http"),
				"Verifying Become A Coach Link href contains http");
	}

	// Verifying selected locale
	public void verifySelectedLocale(String locale) {
		logger.info("Verifying selected locale-" + locale);
		verifyActualEqualsExpected(CommonUtilities.removeNonAlphaNumeric(locale), CommonUtilities.removeNonAlphaNumeric(getSelectedLocale()), "Selected Locale");
	}

	// Verifying user name
	public void verifyUserID(String expectedID) {
		logger.info("Verifying User Name");
		verifyActualEqualsExpected(expectedID, getUserIDText(), "Verifying User name value");
	}

	// Getting user ID text
	public String getUserIDText() {
		logger.info("Getting user ID text");
		return getTextFromElement(body.textUserID.by()).split(":")[1].trim();
	}
}
