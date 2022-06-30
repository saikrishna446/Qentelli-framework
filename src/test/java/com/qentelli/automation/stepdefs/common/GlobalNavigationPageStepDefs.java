package com.qentelli.automation.stepdefs.common;

import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.pages.common.GlobalNavigationPage;
import com.qentelli.automation.stepdefs.common.application.HomePage;
import com.qentelli.automation.stepdefs.common.application.MyAccountPage;
import com.qentelli.automation.utilities.TBBTestDataObject;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GlobalNavigationPageStepDefs {
    private World world;
    GlobalNavigationPage globalNav = null;
    HomePage tbbHome = null;
    MyAccountPage tbbMyAccountPage;
    TBBTestDataObject tbbTestData;
    BasePage basePage;


    public GlobalNavigationPageStepDefs(World world) {
        this.world = world;
        globalNav = new GlobalNavigationPage(world);
        tbbHome = new HomePage(world);
        tbbMyAccountPage = new MyAccountPage(this.world);
        tbbTestData = new TBBTestDataObject();
        basePage = new BasePage(world, world.driver);
    }

    @When("I navigate to {string} page from global navigation header")
    public void hoverOverProfileImageAndClickDropdownOption(String dropdownOption) {
        globalNav.mouseHoverProfileImageGlobalNavigation();
        switch (dropdownOption) {
            case "My Account":
                globalNav.clickOnMyAccount();
                break;
            case "My Orders":
                globalNav.clickOnMyOrders();
                break;
            default:
                throw new AutomationIssueException("Invalid profile image dropdown option - " + dropdownOption);
        }
    }
    @Then("I verify all the links in the Top Navigation Header are displayed and clickable {string}")
    public void iVerifyAllTheLinksInTbbGlobalNavigationAreDisplayedAndClickable(String userType) throws Exception {
        globalNav.verifyLogInButtonGlobal();
        globalNav.mouseOverLogInLinkGlobal();
        globalNav.verifyLogInButtonDisplayed();
        globalNav.verifySignUpButtonDisplayed();
        globalNav.verifyMyAccountLinkDisplayed();
        globalNav.verifyMyOrdersLinkDisplayed();
        globalNav.verifyShopLink();
        globalNav.verifyBODLink();
        globalNav.verifyGroupsLink();
        globalNav.verifyJoinUsLink();
        globalNav.verifyMoreLink();
        globalNav.verifyFlagIcon();
        globalNav.verifyHelpLink();
        globalNav.verifyCartLinkIsClickable();
    }

    @Then("I verify the layout of all links in the Top Navigation Header {string}")
    public void iVerifyTheLayoutOfAllTheLinksInTbbGlobalNavigation(String userType) throws Exception {
        globalNav.verifyHomePageTitleIsDisplayedOnLeftToShopLink();
        globalNav.verifyShopLinkIsDisplayedOnLeftToBODLink();
        globalNav.verifyBODLinkIsDisplayedOnLeftToNutritionLink();
        globalNav.verifyNutritionLinkIsDisplayedOnLeftToJoinUsLink();
        globalNav.verifyJoinUsLinkIsDisplayedOnLeftToMoreButton();
        globalNav.verifyMoreLinkIsDisplayedOnLeftToSearchButton();
        globalNav.verifySearchButtonIsDisplayedOnLeftToCartIcon();
        globalNav.verifyCartIconIsDisplayedOnLeftToHelpLink();
        globalNav.verifyHelpLinkIsDisplayedOnLeftToFlagIcon();
    }

    @Then("I verify the promo code banner is displayed above the Global Navigation Header")
    public void iVerifyThePromoCodeBannerIsDisplayedAboveGlobalNav() throws Exception {
        globalNav.verifyThePromoCodeBannerIsDisplayedAboveGlobalNav();
    }

    @Then("I verify the Top Navigation Header is displayed")
    public void iVerifyTheTopNavigationHeaderIsDisplayed() throws Exception {
        globalNav.verifytestLogo();
        globalNav.verifyTheTopNavigationHeaderIsDisplayed();
    }

    @Then("I verify the sub-nav component is displayed in the Top Navigation Header")
    public void iVerifyTheSubNavComponentIsDisplayedOnHomepage() throws Exception {
        globalNav.verifyTheSubNavigationComponentIsDisplayed();
    }

    @Then("I verify the Global Sub Nav components are displayed when user hovers over the Global Nav menu items on {string}")
    public void iVerifyTheGlobalNavigationHeaderSubNavComponentsAreDisplayed(String application) {
        globalNav.mouseHoverGlobalNavShopLink();
      //  globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();
        globalNav.mouseHoverGlobalNavtestOnDemandLink();
        globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();
        globalNav.mouseHoverGlobalNavNutritionLink();
        globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();
        globalNav.mouseHoverGlobalNavGroupsLink();
        globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();

        switch (application.toUpperCase()) {
            case "TBB":
                globalNav.mouseHoverGlobalNavJoinUsLink();
                globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();
                break;
            case "COO":
                globalNav.mouseOverCoachToolsLink();
                globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();
                break;
            default:
                throw new AutomationIssueException(application + " - Application name not supported");
        }
        globalNav.mouseHoverGlobalNavMoreLink();
        globalNav.verifyGlobalSubNavDropdownMenusAreDisplayed();
    }

    @When("I hover over Log-In link on global header")
    public void hoverOverSignInLink() {
        globalNav.mouseOverLogInLinkGlobal();
    }

    @When("I hover over Shop link on global header")
    public void hoverOverShopLink() {
        globalNav.mouseHoverGlobalNavShopLink();
    }

    @When("I click on Log-In button in the Top Navigation Header")
    public void iClickOnLogInButtonInTBBHomePage() {
        globalNav.mouseOverLogInLinkGlobal();
        globalNav.clickLogInLinkUnderLogInMenu();
    }

    @When("I click on country flag to change the country")
    public void iClickOnCountryFlagToChangeTheCountry() {
        globalNav.mouseOverGlobalFlagIcon();
        if (world.getLocale().equalsIgnoreCase("es_US")) {
            globalNav.clickUKEnglishFlagFromDropdown();
            world.setLocale("en_GB");
        } else {
            globalNav.clickUSASpanishFlagFromDropdown();
            world.setLocale("es_US");
        }
    }

    @When("I click on Global Flag link and change the country from global navigation")
    public void iClickOnGlobalFlagLinkAndChangeTheCountry() {
        tbbHome.setDefaultFlagValue();
        clickOnGlobalFlagLink();
        changeCountryLanguagePreference();
    }


    // Clicking on Global Flag to view country language preferences
    public void clickOnGlobalFlagLink() {
        globalNav.mouseOverGlobalFlagIcon();
    }

    // Selecting country language preference
    public void changeCountryLanguagePreference() {
        globalNav.clickCountryLanguageOption(tbbTestData.changeCountryLanguagePreferenceTo);
    }

    @Then("I verify that country is changed")
    public void iVerifyThatCountryIsChanged() {
        globalNav.verifyURLChangedToNewCountry();
    }

    @Then("I verify flag dropdown menu is displayed when user hovers over the flag icon")
    public void iVerifyFlagDropdownMenuIsDisplayedWhenUserHoversOverTheFlagIcon() {
        globalNav.mouseOverGlobalFlagIcon();
        globalNav.verifyFlagDropdownMenuIsDisplayed();
    }

    @Then("I verify the Top Navigation Header is displayed below the Message Bar")
    public void iVerifyTheTopNavigationHeaderIsDisplayedBelowTheMessageBar() {
        globalNav.verifyIfNavigationHeaderIsDisplayedBelowMessageBar();
    }


    @Then("I verify that the sub-navigation header is displayed below the top navigation header")
    public void iVerifyThatTheSubNavigationHeaderIsDisplayedBelowTheTopNavigationHeader() {
        globalNav.verifyIfSubNavigationIsDisplayedBelowNavigationHeader();
    }







}