package com.qentelli.automation.stepdefs.common.application;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


import com.qentelli.automation.utilities.TBBTestDataObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.qentelli.automation.common.World;
import com.qentelli.automation.drivers.Waits;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;
import com.qentelli.automation.utilities.CommonUtilities;

public class HomePage extends BasePage {
	private World world;
	private MyAccountPage teamtestMyAccountPage;
	private ResourceBundle localeHomePageElements;
	private ResourceBundle homePageElements;
	private ResourceBundle commonTestData;
	private String defaultFlag;
	private String newFlag;
	private ResourceBundle addressLocale;
	private ResourceBundle environmentURLS;
	private TBBTestDataObject tbbTestDataObject;
	private Logger logger;
	private By linkMyOrders;
	private By linkSignIn;
	private By linkUser;
	private By linkSignOut;
	private By linkMyAccount;
	private By buttonBecomeCoach;
	private By linkSignInMobile;
	private By flagIcon;
	private By helpLink;
	private By searchBox;
	private By miniCartIcon;
	private By cartItemCount;
	private By shopLink;
	private By testOnDemandLink;
	private By coachLink;
	private By signInLinkInSignInMenu;
	private By buttonMiniCartInHeader;
	private By linkFooterBOD;
	private By linkFooterCoach;
	private By shoppingCartValue;
	private By removeProductsOnCart;
	private By linkGlobalFlag;
	private By countryLanguagePopupMessage;
	private By restrictedPopUpMessage;
	private By popUpMindset;
	private By textEmailResetPasswordPopup;
	private By textNewPassword;
	private By textReEnterPassword;
	private By buttonClickUpdate;
	private By buttonContinueUpdatePasswordPopup;
	private By checkboxKeepMeSignedIn;
	private By linkKeepMeSignedInPopup;
	private By buttonXClosePopUp;
	private By buttonShakeologyPackViewDetails;
	private By pageSectionTitle;
	private By linkBeachBar;
	private By linkShareACart;
	private By linkCoachFAQ;
	private By linkCoachOffice;
	private By buttonClosePopUp;
	private By buttonNonBODAAPack;
	private By textCoachInfoHeader;
	private By buttonSignUp;
	private By buttonSignIn;
	private By linkNutritionSupplementPerformanceStack;
	private By buttonRemoveProduct;
	private By dropdownGlobalFlagMobile;
	private String flagIconCountryLangPreference;
	private By textBusinessStarterKitPrice;
	private By textCoachPrice;
	private By buttonCloseCart;
	private By linkShakeologyChocolatePack;
	private By textMaxAllowedQtyPopupMessage;
	private By buttonAddToCartOffersPage;
	private By linkChocolateVeganShakeologyProduct;
	private By flagIconEnglishCanada;
	private By linkSignInUnderReturningCustomer;
	private By textPleaseSignIn;
	private By buttonBeachBarChocolateAlmondCrunch;
	private By buttonBeachBarVeganChocolateAlmond;
	private By retailPriceTTCText;
	private By yourPriceTTCText;
	private By itemBSKInMiniCart;
	private By mobileGrip;
	private By iconAlignArrowUp;
	private String buttonFitnessProgramType;
	private String homePageTab;
	private String countryLanguageUpdatedPopupMessage;
	private String buttonCPPlaceholder;
	private String completionPacks;
	private String challengePacksProductsPlaceholder;
	private String linkPlaceholder;
	private String countryLanguagePopupHeaderPlaceholder;
	private By buttonSearch;
	private String challengePackTypePlaceholder;
	private By linkFitnessSubMenu;
	private By linkChallengePacks;
	private String buttonShakeologyBuyNowPlaceholder;
	private String linkShopSubMenu;
	private String popUpButtonPlaceholder;
	private String buttonBuyNowCP;
	private By buttonBuyNowBODCPProduct;
	private By checkboxChinUpMaxFilter;
	private By linkFilterToSelect;
	private By buttonBuyNowChinUpMax;
	private By linkLoadMore;
	private By unsupportedModalPopUp;
	private By textUnsupportedModalPopUp;
	private By textHeaderUnsupportedPopUp;
	private By textYourCoachHeader;
	private By addPreferredMembershipToCartButton;
	private String textProductNameInMiniCart;
	private By removeLinkNonSKUOnMiniCart;
	private By removeLinkSKUOnMiniCart;
	private By productPrice;
	private By retailPriceInMiniCart;
	private By preferredMembershipTitle;
	private By coachTitleInCoachInfo;
	private By coachNameInCoachInfo;
	private By coachInfoDownArrow;
	private By emailInCoachInfoHeader;
	private By phoneInCoachInfoHeader;
	private By cdp2BMindsetSelectionButton;
	private By vanillaShakeologySelectButton;
	public HomePageElements body;


	public void initElements() {
		homePageElements = ResourceBundle.getBundle(
				"com.qentelli.automation." + world.getTestEnvironment() + ".elementlib.TBB.HomePage",
				Locale.getDefault());
		localeHomePageElements = ResourceBundle.getBundle(
				"com.qentelli.automation." + world.getTestEnvironment() + ".elementlib.TBB.HomePage",
				world.getFormattedLocale());
		//private ResourceBundle loginTestData;
		ResourceBundle packsTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.PacksTestData");
		commonTestData = ResourceBundle.getBundle("com.qentelli.automation.testdata.CommonTestData");
		tbbTestDataObject= new TBBTestDataObject();
		logger = LogManager.getLogger(HomePage.class);
		linkSignIn = By.xpath(localeHomePageElements.getString("link_signin"));
		linkUser = By.cssSelector(localeHomePageElements.getString("link_User"));
		linkSignOut = By.xpath(localeHomePageElements.getString("link_signout"));
		linkMyAccount = By.xpath(localeHomePageElements.getString("link_MyAccount"));
		buttonBecomeCoach = By.xpath(homePageElements.getString("button_CoachSignup"));
		linkSignInMobile = By.xpath(homePageElements.getString("link_signin_mobile"));
		flagIcon = By.cssSelector(homePageElements.getString("link_flagIcon"));
		helpLink = By.cssSelector(homePageElements.getString("link_help"));
		searchBox = By.cssSelector(homePageElements.getString("input_search"));
		miniCartIcon = By.cssSelector(homePageElements.getString("link_miniCart"));
		cartItemCount = By.cssSelector(homePageElements.getString("txt_cartItemCount"));
		shopLink = By.xpath(homePageElements.getString("link_Shop"));
		testOnDemandLink = By.cssSelector(homePageElements.getString("link_testOnDemand"));
		coachLink = By.xpath(homePageElements.getString("link_Coach"));
		signInLinkInSignInMenu = By.cssSelector(homePageElements.getString("signInLinkInSignInMenu"));
		buttonMiniCartInHeader = By.cssSelector(homePageElements.getString("button_MiniCartInHeader"));
		linkFooterBOD = By.xpath(homePageElements.getString("link_footertestOnDemand"));
		linkFooterCoach = By.xpath(homePageElements.getString("link_footerCoach"));
		shoppingCartValue = By.xpath(homePageElements.getString("shopping_cart_value"));
		removeProductsOnCart = By.xpath(homePageElements.getString("link_removeProduct_cart"));
		linkGlobalFlag = By.xpath(homePageElements.getString("link_global_flag"));
		countryLanguagePopupMessage = By.xpath(homePageElements.getString("country_language_popup_message"));
		restrictedPopUpMessage = By.xpath(homePageElements.getString("restricted_popup_message"));
		popUpMindset = By.xpath(homePageElements.getString("Mindset_Popup"));
		textEmailResetPasswordPopup = By.xpath(homePageElements.getString("input_EmailInResetPasswordPopUp"));
		textNewPassword = By.xpath(homePageElements.getString("new_Password"));
		textReEnterPassword = By.xpath(homePageElements.getString("reenter_Password"));
		buttonClickUpdate = By.xpath(homePageElements.getString("click_Update"));
		buttonContinueUpdatePasswordPopup = By.cssSelector(homePageElements.getString("button_toContinue"));
		checkboxKeepMeSignedIn = By.xpath(homePageElements.getString("checkbox_keep_me_signed_in"));
		linkKeepMeSignedInPopup = By.xpath(homePageElements.getString("link_keep_me_signed_in_popup"));
		buttonXClosePopUp = By.xpath(homePageElements.getString("close_popup"));
		buttonShakeologyPackViewDetails = By.xpath(homePageElements.getString("link_ShakeologyPack_Viewdetails"));
		pageSectionTitle = By.xpath(localeHomePageElements.getString("PageSectionTitle"));
		linkShareACart = By.xpath(homePageElements.getString("link_ShareACart"));
		linkCoachFAQ = By.xpath(homePageElements.getString("link_CoachFAQ"));
		linkCoachOffice = By.xpath(localeHomePageElements.getString("link_CoachOffice"));
		buttonClosePopUp = By.xpath(homePageElements.getString("close_button_in_popup"));
		buttonNonBODAAPack = By.xpath(homePageElements.getString("link_NonBodaa_Pack"));
		linkNutritionSupplementPerformanceStack = By
				.xpath(homePageElements.getString("link_nutritionSupple_PerformanceStack"));
		textCoachInfoHeader = By.xpath(homePageElements.getString("text_CoachInfo_Header"));
		linkMyOrders = By.xpath(localeHomePageElements.getString("link_myorders"));
		buttonSignUp = By.xpath(localeHomePageElements.getString("button_signup"));
		buttonSignIn = By.xpath(localeHomePageElements.getString("button_signin"));
		linkBeachBar = By.xpath(homePageElements.getString("link_BeachBar"));
		buttonRemoveProduct = By.xpath(homePageElements.getString("remove_Button"));
		dropdownGlobalFlagMobile = By.cssSelector(homePageElements.getString("continent_Drop_Down"));
		flagIconCountryLangPreference = homePageElements.getString("country_lang_preference_option");
		textBusinessStarterKitPrice = By.cssSelector(homePageElements.getString("businessStarterKitPrice"));
		textCoachPrice = By.xpath(homePageElements.getString("text_CoachPrice"));
		buttonCloseCart = By.xpath(homePageElements.getString("button_CloseCart"));
		linkShakeologyChocolatePack = By.xpath(homePageElements.getString("shakeology_ChocolatePack_SelectButton"));
		textMaxAllowedQtyPopupMessage = By.xpath(localeHomePageElements.getString("textMaxAllowedQtyPopupMessage"));
		buttonAddToCartOffersPage = By.xpath(homePageElements.getString("addToCartOnOffersPage"));
		linkChocolateVeganShakeologyProduct = By.xpath(homePageElements.getString("shakeology_ChocolateVeganPack"));
		flagIconEnglishCanada = By.xpath(homePageElements.getString("en_CAFlag"));
		linkSignInUnderReturningCustomer = By.xpath(homePageElements.getString("signInLinkInReturningCust"));
		textPleaseSignIn = By.xpath(homePageElements.getString("txtErrorLabel"));
		buttonBeachBarChocolateAlmondCrunch = By.xpath(localeHomePageElements.getString("bbarChocolateAlmondCrunch"));
		buttonBeachBarVeganChocolateAlmond = By.xpath(homePageElements.getString("beachBarVeganeChocolateAlmond"));
		retailPriceTTCText = By.xpath(homePageElements.getString("retailPriceTTCText"));
		yourPriceTTCText = By.xpath(homePageElements.getString("yourPriceTTCText"));
		itemBSKInMiniCart = By.xpath(localeHomePageElements.getString("itemBSKInMiniCart"));
		mobileGrip = By.xpath(homePageElements.getString("mobile_grip"));
		iconAlignArrowUp = By.xpath(homePageElements.getString("alignArrowUp"));
		buttonFitnessProgramType = localeHomePageElements.getString("placeholder_button_FitnessProgramType");
		homePageTab = homePageElements.getString("homePageTabs");
		countryLanguageUpdatedPopupMessage = commonTestData.getString("countryLanguageUpdatedPopupMessage");
		buttonCPPlaceholder = homePageElements.getString("placeholder_button_Pack");
		completionPacks = packsTestData.getString("CompletionPacks");
		challengePacksProductsPlaceholder = homePageElements.getString("placeholder_Challenge_pack_CDP");
		linkPlaceholder = homePageElements.getString("linkPlaceholder");
		countryLanguagePopupHeaderPlaceholder = homePageElements.getString("country_popup_header");
		buttonSearch = By.xpath(localeHomePageElements.getString("searchButton"));
		challengePackTypePlaceholder = homePageElements.getString("placeholder_Challenge_packType_CDP");
		linkFitnessSubMenu = By.xpath(homePageElements.getString("link_FitnessPrograms"));
		linkChallengePacks = By.xpath(localeHomePageElements.getString("link_ChallengePacks"));
		buttonShakeologyBuyNowPlaceholder = homePageElements.getString("button_Shakeology_BuyNow");
		linkShopSubMenu = homePageElements.getString("link_Shop_Menu");
		popUpButtonPlaceholder = homePageElements.getString("button_Text");
		buttonBuyNowCP = localeHomePageElements.getString("placeholder_button_PackType");
		buttonBuyNowBODCPProduct = By.xpath(localeHomePageElements.getString("link_challenge_Pack"));
		checkboxChinUpMaxFilter = By.xpath(localeHomePageElements.getString("checkbox_filter_chinupmax"));
		linkFilterToSelect = By.xpath(localeHomePageElements.getString("filter_link_toselect"));
		buttonBuyNowChinUpMax = By.xpath(localeHomePageElements.getString("link_chinup_max_buy"));
		linkLoadMore = By.xpath(localeHomePageElements.getString("link_load_more"));
		unsupportedModalPopUp = By.xpath(localeHomePageElements.getString("unsupportedModalPopUp"));
		textUnsupportedModalPopUp = By.xpath(localeHomePageElements.getString("textUnsupportedModalPopUp"));
		textHeaderUnsupportedPopUp = By.xpath(localeHomePageElements.getString("textHeaderUnsupportedPopUp"));
		textYourCoachHeader = By.xpath(localeHomePageElements.getString("textYourCoachHeader"));
		addPreferredMembershipToCartButton = By.xpath(localeHomePageElements.getString("addPreferredMembershipToCartButton"));
        textProductNameInMiniCart = localeHomePageElements.getString("textProductNameInMiniCart");
        removeLinkNonSKUOnMiniCart = By.xpath(localeHomePageElements.getString("removeLinkNonSKUOnMiniCart"));
        removeLinkSKUOnMiniCart = By.xpath(localeHomePageElements.getString("removeLinkSKUOnMiniCart"));
        productPrice = By.xpath(localeHomePageElements.getString("productPrice"));
        retailPriceInMiniCart = By.xpath(localeHomePageElements.getString("retailPriceInMiniCart"));
        preferredMembershipTitle = By.cssSelector(localeHomePageElements.getString("preferredMembershipTitle"));
        coachTitleInCoachInfo = By.cssSelector(localeHomePageElements.getString("coachTitleInCoachInfo"));
        coachNameInCoachInfo = By.cssSelector(localeHomePageElements.getString("coachNameInCoachInfo"));
        coachInfoDownArrow = By.cssSelector(localeHomePageElements.getString("coachInfoDownArrow"));
        emailInCoachInfoHeader = By.cssSelector(localeHomePageElements.getString("emailInCoachInfoHeader"));
        phoneInCoachInfoHeader = By.cssSelector(localeHomePageElements.getString("phoneInCoachInfoHeader"));
        cdp2BMindsetSelectionButton = By.xpath(localeHomePageElements.getString("cdp2BMindsetSelectionButton"));
		vanillaShakeologySelectButton = By.xpath(localeHomePageElements.getString("vanillaShakeologySelectButton"));
	}

	public HomePage(World world) {
		super(world, world.driver);
		this.world = world;
		body = new HomePageElements(getDriver());
//		teamtestMyAccountPage = new MyAccountPage(world);
		addressLocale = ResourceBundle.getBundle("com.qentelli.automation.testdata.Address",
						world.getFormattedLocale());
		environmentURLS = ResourceBundle.getBundle(
					"com.qentelli.automation..testdata." + world.getTestEnvironment().toUpperCase() + ".EnvironmentURLS",
						world.getFormattedLocale());
		initElements();

	}

	// Click on signIn Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickSignInLink() {
		logger.info("Clicking on SignIn link");
		click(linkSignIn);
	}

	// Click Current locale flag
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickCurrentCountryLink() {
		logger.info("Clicking Current locale flag");
		click(By.xpath(homePageElements.getString("lnk_" + world.getLocale())));
	}

	//TODO: mobile is out of scope for now
	//Clicking on SignIn Icon Mobile
	public void clickSignInIconMobile() {
		logger.info("Clicking on SignIn icon in mobile");
		click(linkSignInMobile);
	}

	// click on user name link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickOnUserNameLink() {
		logger.info("Clicking on User name link");
		click(linkUser);
	}

	// click on My Account link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickOnMyAccount() {
		logger.info("Clicking on My Account link");
		click(linkMyAccount);
	}

	//TODO: there is no obvious way to implement parametrization with a new approach. Clicking on menu tab should be done separately for each tab
	public void clickOnMenu(String menu) {
		logger.info("Clicking on " + menu + " menu");
		click(By.xpath(homePageElements.getString("link_" + menu)));
	}

	//TODO: there is no obvious way to implement parametrization with a new approach. Clicking on menu tab should be done separately for each tab
	// Navigating to a menu tab on home page by clicking on it
	public void clickAMenuTabOnHomePage(String tabName) {
		logger.info("Clicking on " + tabName + " tab");
		click(By.xpath(homePageTab.replace("PLACEHOLDER", tabName)));
	}

	//TODO: there is no obvious way to implement parametrization with a new approach. Clicking on sub menu tab should be done separately for each tab
	public void navigateToSubMenu(String subMenu) {
		logger.info("Clicking on " + subMenu + " submenu");
		click(By.xpath(localeHomePageElements.getString("link_" + subMenu)));
	}

	// Clicks on mini cart on header
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickOnMiniCartInHeader() {
		logger.info("Clicking on Mini-Cart on header");
		click(buttonMiniCartInHeader);
	}

	//TODO: there is no obvious way to implement parametrization with a new approach. Clicking on menu tab should be done separately for each tab
	public void clickChallengePackSelectButton(String packName) {
		logger.info("Selecting challenge pack " + packName + " , by clicking on select button");
		click(By.xpath(buttonCPPlaceholder.replace("PLACEHOLDER", packName)));
	}



	// Click on SignIn link under returning customer
	//TODO: move to SignInPage
	// need clarification if this is still needed
	public void clickOnSignInUnderReturningCustomers() {
		logger.info("Clicking on SignIn Link Under Returning Customers");
		click(linkSignInUnderReturningCustomer);
	}


	// Click to select Beach Bar Chocolate Almond Crunch product
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickBBChocolateAlmondCrunchProduct() {
		logger.info("Selecting Beach Bar Chocolate Almond Crunch product by clicking");
		click(buttonBeachBarChocolateAlmondCrunch);
	}

	// clicking Beach Bar Vegan Chocolate Almond product button
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickBeachBarVeganChocolateAlmondProduct() {
		logger.info("Selecting Beach Bar Vegan Chocolate Almond product");
		click(buttonBeachBarVeganChocolateAlmond);
	}

	// Clicking on locale specific product link
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickLocaleSpecificProductLink(String productName) {
		logger.info("Clicking on locale specific product link - " + productName);
		click(By.xpath(localeHomePageElements.getString(productName)));
	}


	// Clicking on Beach Bar sub category link
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickBeachBarSubCategory() {
		logger.info("Clicking on Beach Bar sub category link");
		click(linkBeachBar);
	}

	// Clicking on Coach link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickCoachLink() {
		logger.info("Clicking on Coach link");
		click(coachLink);
	}



	// Clicking on Buy Now button for a product
	//TODO: there is no obvious way to implement parametrization with a new approach. Clicking on menu tab should be done separately for each tab
	public void clickProductBuyNowLink(String link) {
		logger.info("Clicking Buy Now link for a product");
		click(By.xpath(linkPlaceholder.replace("PLACEHOLDER", link)));
	}

	// Clicking Buy Now test on Demand Challenge Pack Product button
	//TODO: Don't use this method for unification. Should be replaced by clickFirstCPBuyNowType()
	public void clickBuyNowBODCPProduct() {
		logger.info("Clicking on Buy Now BOD CP Product button");
		click(buttonBuyNowBODCPProduct);
	}

	// Clicking Buy Now Chin Up Max button
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickBuyNowChinUpMax() {
		logger.info("Clicking on Chin Up Max Buy Now button");
		click(buttonBuyNowChinUpMax);
	}

	// clicking filter to select link
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickFilterToSelectLink() {
		logger.info("clicking Filter To Select link");
		click(linkFilterToSelect);
	}

	// clicking checkbox filter chin up max
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickFilterChinUpMaxCheckbox() {
		logger.info("clicking checkbox filter chin up max");
		click(checkboxChinUpMaxFilter);
	}

	// Clicking on link load more if visible
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickLoadMoreLink() {
		logger.info("Clicking on Load More Link if visible");
		clickIfVisible(linkLoadMore);
	}

	// Clicking search button
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickSearchButton() {
		logger.info("Clicking search button");
		click(buttonSearch);
	}

	// Clicking on remove button of last product
	//TODO: Don't use this method for unification. It was recreated in the ShoppingCartPage
	public void clickLastProductRemoveButton() {
		logger.info("Clicking remove button on last product");
		List<WebElement> removeButtonList = getPresentElements(buttonRemoveProduct);
		click(getFirstActiveElement(
				removeButtonList.subList(removeButtonList.size() - 2, removeButtonList.size() - 1)));
	}

	// Clicking on continue button on update password popup
	//TODO: Don't use this method for unification. It was recreated in the SignInPage
	public void clickUpdatePasswordPopupContinueButton() {
		logger.info("Clicking on continue button on update password popup");
		click(buttonContinueUpdatePasswordPopup);
	}

	// Clicking on update password button
	//TODO: Don't use this method for unification. It was recreated in the SignInPage
	public void clickUpdatePasswordButton() {
		logger.info("Clicking on update password button");
		click(buttonClickUpdate);
	}

	// Clicking on Buy Now button of Challenge Pack
	//TODO: there is no obvious way to implement parametrization with a new approach.
	// Clicking on menu tab should be done separately for each tab. Can be replaced by clickFirstCPBuyNowType()
	public void clickChallengePackBuyNowButton(String[] packType) {
		logger.info("Selecting challenge pack type - " + packType + " , by clicking on CP Buy Now button");
		click(By.xpath(buttonBuyNowCP.replaceAll("PLACEHOLDER1", packType[0]).replaceAll("PLACEHOLDER2", packType[1])));
	}

	// Clicking on User Account Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickOnUserAccount() {
		logger.info("Clicking on User Account Link");
		click(linkUser);
	}


	// Clicking on BOD footer link
	//TODO: Has been replaced by C11052274
	public void clickBODFooterLink() {
		logger.info("Clicking on BOD footer link");
		click(linkFooterBOD);
	}

	// Clicking on Coach footer link
	//TODO: Has been replaced by C11052274
	public void clickCoachFooterLink() {
		logger.info("Clicking on Coach footer link");
		click(linkFooterCoach);
	}

	// Selecting Completion Pack by clicking select button under Completion Pack
	//TODO: there is no obvious way to implement PLACEHOLDER with a new approach. Clicking on menu tab should be done separately for each tab.
	public void clickCompletionPacksCPButton() {
		logger.info("Selecting challenge pack " + completionPacks);
		click(By.xpath(buttonCPPlaceholder.replaceAll("PLACEHOLDER", completionPacks)));
	}

	// Clicking on Shakeology Pack view details button
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickShakeologyPackViewDetails() {
		logger.info("Clicking on ShakeologyPack ViewDetails button");
		click(buttonShakeologyPackViewDetails);
	}



	// clicking on global flag content dropdown on mobile
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickGlobalFlagDropdown() {
		logger.info("clicking on Global Flag dropdown on mobile");
		click(dropdownGlobalFlagMobile);
	}

	// clicking on global flag link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickGlobalFlagLink() {
		logger.info("clicking on Global Flag link");
		click(linkGlobalFlag);
	}

	// clicking on flag icon to select Country and Language preference options
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickCountryLanguageOption() {
		logger.info("Clicking on flag icon to select Country and Language preference options");
		click(getFirstActiveElement(getPresentElements(
				By.xpath(flagIconCountryLangPreference.replace("PLACEHOLDER", commonTestData.getString(newFlag))))));
	}

	// Click a popup button if visible.
	// Yes or No options are unavailable on mobile
	//TODO: there is no obvious way to implement PLACEHOLDER with a new approach. Click should be done separately for each element.
	public void clickOnPopUpButtonIfVisible(String buttonName) {
		logger.info("Clicking on " + buttonName + " button in PopUp if visible");
		clickIfVisible(By.xpath(popUpButtonPlaceholder.replace("PLACEHOLDER", buttonName)));
	}


	// Selecting nutrition program type by clicking on tab
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void clickNutritionProgramSubTab(String typeOfNutritionProgram) {
		logger.info("Clicking " + typeOfNutritionProgram + " Nutrition Program tab");
		click(By.xpath(homePageElements.getString(typeOfNutritionProgram)));
	}

	// Selecting Nutrition Pack by clicking buy now button
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element. Please use methods from NutritionPage, like clickTBBSubNavNutritionProgramsLink()
	public void clickNutritionPackBuyNowButton(String typeOfNutritionPack) {
		logger.info("Selecting Nutrition Pack by clicking Buy Now button");
		click(By.xpath(localeHomePageElements.getString(typeOfNutritionPack)));
	}



	// Selecting Fitness program pack type by clicking on buy now button
	//TODO: there is no obvious way to implement parametrization with a new approach.
	// Clicking on element should be done separately for each element. Can be replaced by clickFirstCPBuyNowType()
	public void selectFitnessProgramType(String[] packType) {
		logger.info("Selecting pack type - " + packType);
		click(By.xpath(buttonFitnessProgramType.replaceAll("PLACEHOLDER1", packType[0]).replaceAll("PLACEHOLDER2",
				packType[1])));
	}

	// Clicking on sign in link under sign in menu
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickSignInLinkUnderSignInMenu() {
		logger.info("Clicking on sign in link under sign in menu");
		click(signInLinkInSignInMenu);
	}

	// Clicking question mark link next to keep me signed in checkbox label
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in SignInPage
	public void clickKeepMeSignedQuestionMarkLink() {
		logger.info("Clicking on Question mark link next to Keep Me Signed text");
		click(linkKeepMeSignedInPopup);
	}

	// Clicking on keep me signed in checkbox
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in SignInPage
	public void clickKeepMeSignedCheckbox() {
		logger.info("Clicking on Keep Me Signed checkbox");
		click(checkboxKeepMeSignedIn);
	}

	// Clicking on Challenge Packs tab
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in TeamtestNavigationPage
	public void clickChallengePacksTab() {
		logger.info("Selecting on Challenge Packs tab by clicking");
		click(linkChallengePacks);
	}

	// Clicking on mobile menu
	//TODO: mobile is out of scope for Unification
	public void clickMobileMenu() {
		logger.info("Clicking on mobile menu");
		click(mobileGrip);
	}

	// clicking on shop link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickShopLink() {
		logger.info("Clicking on shop link");
		click(shopLink);
	}

	// Navigating to Fitness subMenu by clicking on the link
	// TODO:Don't use this method for unification. Needs to be removed
	//  as it was implemented in TeamtestNavigationPage - clickTBBSubNavFirstFitnessProgramsLink()
	public void navigateToFitnessSubMenu() {
		logger.info("Navigating to Fitness submenu by clicking");
		click(linkFitnessSubMenu);
	}

	// clicking on shop sub menu
	// TODO: GZ redo needed for Submenu in Global Nav page classes
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element. Please use methods from GlobalNavPage
	public void clickOnShopSubMenu(String menu) {
		logger.info("Clicking on Shop sub menu - " + menu);
		click(By.xpath(linkShopSubMenu.replace("PLACEHOLDER", menu)));
	}

	// Clicking on sub menu tab
	//TODO: there is no obvious way to implement parametrization with a new approach.
	// Clicking on menu tab should be done separately for each tab. Please use methods from TeamtestNavigationPage
	public void clickSubMenuTab(String subMenuTab) {
		logger.info("Clicking on Sub menu Tab");
		isElementStale(getPresentElement(By.xpath(localeHomePageElements.getString(subMenuTab))), Waits.MIN_WAIT);
		click(By.xpath(localeHomePageElements.getString(subMenuTab)));
	}

	// Selecting Pack by clicking on it's link
	//TODO: there is no obvious way to implement parametrization with a new approach.
	// Clicking on menu tab should be done separately for each link. This method is a duplication, please reuse existing ones
	public void clickPackType(String packType) {
		logger.info("Selecting Pack - " + packType + " by clicking on it's link");
		click(By.xpath(homePageElements.getString(packType)));
	}



	// Clicking pack type
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element. This method is a duplication, please reuse existing ones
	public void selectPackType(String packType) {
		logger.info("Selecting pack type - " + packType);
		click(By.xpath(localeHomePageElements.getString(packType)));
	}

	// Selecting Non-BODAA (Non-test On Demand Annual All Access) product by
	// clicking on Buy Now button
	//TODO: need clarification
	public void clickNonBODAAChallengePackButton() {
		logger.info("Selecting Non-BOD challenge pack by clicking on Buy Now button");
		click(buttonNonBODAAPack);
	}

	// Clicking on My Orders link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickOnMyOrders() {
		logger.info("Clicking on My Orders link");
		click(linkMyOrders);
	}

	// Clicking on Nutrition Supplement Performance pack link
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in TeamtestNutritionPage
	public void clickNutritionSupplementPerformanceStackLink() {
		logger.info("Clicking nutrition Supplement Performance Stack link");
		click(linkNutritionSupplementPerformanceStack);
	}



	// Clicking on a program from challenge packs page to select it
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element. This method is a duplication, please reuse the above one
	public void clickAChallengePackProgram(String pack) {
		logger.info("Selecting challenge pack type - " + pack + " by clicking it");
		click(By.xpath(challengePacksProductsPlaceholder.replaceAll("PLACEHOLDER", pack)));
	}

	// Selecting challenge pack type by clicking on buy now button
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element.
	public void clickChallengePackTypeProduct(String[] packType) {
		logger.info("Selecting challenge type product by clicking on Buy Now button");
		click(By.xpath(challengePackTypePlaceholder.replaceAll("PLACEHOLDER1", packType[0]).replaceAll("PLACEHOLDER2",
				packType[1])));
	}

	// Selecting Shakeology flavor by clicking on Buy Now button
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element.
	public void clickBuyNowShakeologyPack(String packName) {
		logger.info("Clicking Buy Now button to select Shakeology pack :" + packName);
		click(By.xpath(buttonShakeologyBuyNowPlaceholder.replaceAll("PLACEHOLDER", packName)));
	}

	// Clicking on close cart
	//TODO: Mini cart is no longer supported. Delete
	public void clickCloseCart() {
		logger.info("Clicking on close cart");
		clickIfVisible(buttonCloseCart);
	}


	//Selecting Vanilla Shakeology pack by clicking on it's link
	// TODO:Don't use this method for unification.
	//  Needs to be removed as it was implemented in TeamtestNutritionPage - clickVanillaWheyBuyNowLink()
	public void clickVanillaShakeologyPack() {
		logger.info("Clicking on Vanilla Shakeology Pack link");
		click(vanillaShakeologySelectButton);
	}

	// Clicking on 'Select' product button
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element.
	public void clickProductSelectButton(String productName) {
		logger.info("Clicking on 'Select' product button - " + productName);
		click(By.xpath(homePageElements.getString(productName)));
	}



	// Clicking Add To Cart button
	//TODO: need clarification what is OffersPage
	public void clickAddToCartOnOffersPage() {
		logger.info("Clicking on Add To Cart button on offers page.");
		click(buttonAddToCartOffersPage);
	}

	// Selecting Challenge Pack type by clicking on select button
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element. This method is a duplication, please reuse existing ones
	public void clickChallengePackButton(String packName) {
		logger.info("Clicking Challenge Pack select button");
		click(By.xpath(buttonCPPlaceholder.replaceAll("PLACEHOLDER", packName)));
	}

	// Selects the product by clicking on it's link
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element. This method is a duplication, please reuse existing ones
	public void clickOnProductToSelect(String productName) {
		logger.info("Clicking on link to select product - " + productName);
		click(By.xpath(localeHomePageElements.getString(productName)));
	}

	// Clicking on Chocolate Vegan Shakeology Pack link
	// TODO:Don't use this method for unification.
	//  Needs to be removed as it was implemented in TeamtestNutritionPage - clickChocolateVeganBuyNowLink()
	public void clickChocolateVeganShakeologyPack() {
		logger.info("Selecting Chocolate Vegan Shakeology Pack by clicking on it's link");
		click(linkChocolateVeganShakeologyProduct);
	}

	// Clicking en_CA flag to change country and language preference
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void clickChangeLocaleToEnglishCanada() {
		logger.info("Clicking en_CA flag to change country and language preference");
		click(flagIconEnglishCanada);
	}

	// Navigate to BOD Shakeology Challenge Pack Product Description page
	// todo please validate against test rail, step says performance pack but the
	//  product selected is challenge pack
	public void navigatingTOBODShakeologyCPProductDescriptionPage() {
		logger.info("Navigating to BOD Shakeology Challenge Pack PDP page");
		navigateToUrl(environmentURLS.getString("annualBODShakeologyCP"));
	}

	// Navigate to Ultimate Portion Fix Performance Pack product description page
	//TODO: there is no obvious way to implement parametrization with a new approach. Selection should be done
	// separately for each element.
	public void navigateToUltimatePortionFixPerformancePack() {
		logger.info("Navigating to Ultimate Portion Fix Performance Pack product description page");
		navigateToUrl(environmentURLS.getString("ultimatePortionFixPerformancePack").replace("PLACEHOLDER",
				world.getLocale().split("_")[1].toLowerCase()));
	}

	// Navigates to TBB home page, URL will be retrieved from config properties
	public void navigateToTBBHomePage() {
		logger.info("Navigating to TBB Home Page, for " + world.getLocale() + " locale:"
				+ ApplicationsEndpointObject.tbb.home);
		launchBrowserAndNavigate(ApplicationsEndpointObject.tbb.home);
	}

	// Navigating to TBB enrollment page without captcha
	public void navigateToTBBEnrollmentPage() {
		logger.info("Navigating to TBB Enrollment Page without captcha using URL");
		// navigateToUrl(environmentURLS.getString("bypassCaptchaTBBEnrolmentURL"));
		body.navigate(ApplicationsEndpointObject.tbb.home);
	}

	// Entering email in reset password popup
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in testSignInPage
	public void enterEmailInResetPasswordPopUp(String email) {
		logger.info("Entering email in reset password popup");
		enterText(textEmailResetPasswordPopup, email);
	}

	// Enter text in new password field
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in testSignInPage
	public void enterNewPassword(String password) {
		logger.info("Entering new password");
		enterText(textNewPassword, password);
	}

	// Enter text in new password field
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in testSignInPage
	public void enterConfirmNewPassword(String password) {
		logger.info("Entering new password");
		enterText(textReEnterPassword, password);
	}


	// Getting source(src) attribute value from Global Flag menu dropdown
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public String getSrcAttributeMobileGlobalFlagDropdown() {
		logger.info("Getting scr attribute value from Mobile Global Flag Dropdown");
		return getAttributeValueFromElement(dropdownGlobalFlagMobile, "src");
	}

	// Getting source(src) attribute value from Global Flag Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public String getSrcAttributeGlobalFlagLink() {
		logger.info("Getting scr attribute value from Global Flag Link");
		return getAttributeValueFromElement(linkGlobalFlag, "src");
	}



	// Get text from restricted popup message
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in ProductDetailsPage
	public String getRestrictedPopUpMessage() {
		logger.info("Getting Restricted PopUp Message text");
		return getTextFromElement(restrictedPopUpMessage);
	}





	// Check if shopping cart is empty, item count is zero
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public boolean isCartItemCountZero() {
		logger.info("Check if shopping cart is empty");
		return Integer.parseInt(getTextFromElement(shoppingCartValue).replaceAll("\\D", "")) == 0;
	}


	// Verifying icon align arrow up is displayed and enabled with a time out
	// parameter
	//TODO: Arrow on home page is removed for Unif proj. Comment for now.
	public boolean isAlignArrowUpIconDisplayed(int timeOut) {
		logger.info("Verifying Icon Arrow Up is displayed and enabled");
		return isElementDisplayedAndEnabled(iconAlignArrowUp, timeOut);
	}

	// Setting default flag value based on current country and language
	// preference(Locale)
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void setDefaultFlagValue() {
		logger.info("Setting variable defaultFlag to -" + world.getLocale());
		defaultFlag = world.getLocale();
	}

	// Setting newFlag variable value
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void setNewFlagValue(String newFlagLocale) {
		logger.info("Setting variable newFlag to -" + newFlagLocale);
		newFlag = newFlagLocale;
	}

	// Verifying sign in link under returning customer
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifySignInLinkUnderReturningCustomer() {
		logger.info("Verifying Sign In link under returning customer");
		getVisibleElement(linkSignInUnderReturningCustomer);
	}

	// Verifying 'Please Sign In' message under login panel
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in SignInPage
	public void verifyMessageDisplayedUnderLoginPanel() {
		logger.info("Verifying message displayed under Login Panel");
		verifyElementTextEquals(textPleaseSignIn, tbbTestDataObject.textPleaseSignInMessage);
	}

	// Validating Retail Price contains TTC text
	// todo revalidate this step is doing all validations as mentioned in test rail
	public void validateRetailPriceText() {
		logger.info("Validate Retail Price contains TTC text");
		verifyElementTextContainsExpectedText("TTC", retailPriceTTCText);
	}

	// Validate Your Price contains TTC EUR text
	// todo revalidate this step is doing all validations as mentioned in test rail
	public void validateYourPriceText() {
		logger.info("Validate Your Price contains TTC  EUR text");
		verifyElementTextContainsExpectedText("TTC  EUR", yourPriceTTCText);
	}


	// Verifying Ultimate Portion Kit Restricted PopUp Message
	//TODO: need clarification
	public void verifyUltimatePortionKitRestrictedPopUpMessage(String expectedPopUpMsg) {
		logger.info("Verifying Ultimate Portion Kit Restricted PopUp Message");
		verifyActualEqualsExpected(expectedPopUpMsg, CommonUtilities.replaceSmartChars(getRestrictedPopUpMessage()),
				"Ultimate Portion Kit Restricted PopUp Message");
	}


	// Verifying Share A Cart link is displayed
	//TODO: used in the step with no usages. Can be deleted
	public void verifyShareACartLink() {
		logger.info("Verifying Share A Cart link is displayed");
		getVisibleElement(linkShareACart);
	}

	// Verifying Coach FAQ link is displayed
	//TODO: used in the step with no usages. Can be deleted
	public void verifyCoachFAQLink() {
		logger.info("Verifying Coach FAQ link is displayed");
		getVisibleElement(linkCoachFAQ);
	}

	// Verifying Coach Office link is displayed
	//TODO: used in the step with no usages. Can be deleted
	public void verifyCoachOfficeLink() {
		logger.info("Verifying Coach Office link is displayed");
		getVisibleElement(linkCoachOffice);
	}

	// Verifying Flag Icon displayed and enabled
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyFlagIcon() {
		logger.info("Verifying Flag Icon is displayed and enabled");
		getElementDisplayedAndEnabled(flagIcon);
	}

	// Verifying Help link displayed and enabled
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyHelpLink() {
		logger.info("Verifying Help Link is displayed and enabled");
		getElementDisplayedAndEnabled(helpLink);
	}

	// Verifying Flag Icon is displayed on left of Help Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyFlagIconIsDisplayedOnLeftToHelpLink() {
		logger.info("Verifying Flag Icon is displayed on left of Help Link");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(flagIcon, helpLink),
				"Flag Icon is displayed on left of Help Link");
	}

	// Verifying Help Link is displayed to Left of Sign In Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyHelpLinkIsDisplayedOnLeftToLinkSignIn() {
		logger.info("Verifying Help Link is displayed to Left of Sign In Link");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(helpLink, linkSignIn),
				"Help Link is displayed to Left of Sign In Link");
	}

	// Verifying Help Link is displayed to Left of User Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyHelpLinkIsDisplayedOnLeftToUserLink() {
		logger.info("Verifying Help Link is displayed to Left of User Link");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(helpLink, linkUser),
				"Help Link is displayed to Left of User Link");
	}

	// Verifying Search Box is displayed to Left of Mini Cart Icon
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifySearchBoxIsDisplayedOnLeftToMiniCartIcon() {
		logger.info("Verifying Search Box is displayed to Left of Mini Cart Icon");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(searchBox, miniCartIcon),
				"Search Box is displayed to Left of Mini Cart Icon");
	}

	// Verifying Cart Item count is valid
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyCartExistWithValidItemCount() {
		int itemCount = Integer.parseInt(getTextFromElement(cartItemCount).replaceAll("[^\\d]", ""));
		logger.info("Verifying Cart Item count is valid, displayed cart count is- " + itemCount);
		verifyActualEqualsExpected(true, itemCount >= 0, "Cart Item Count");
	}

	// Verifying Shop Link is displayed and enabled
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyShopLink() {
		logger.info("Verifying Shop Link is displayed and enabled");
		getElementDisplayedAndEnabled(shopLink);
	}

	// Verifying BOD Link is display and enabled
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyBODLink() {
		logger.info("Verifying BOD Link is display and enabled");
		getElementDisplayedAndEnabled(testOnDemandLink);
	}

	// Verifying Coach Link is display and enabled
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyCoachLink() {
		logger.info("Verifying Coach Link is display and enabled");
		getElementDisplayedAndEnabled(coachLink);
	}

	// Verifying BOD Link is displayed to left of Coach Link
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyBODLinkIsDisplayedOnLeftToCoachLink() {
		logger.info("Verifying BOD Link is displayed to left of Coach Link");
		verifyActualEqualsExpected(true, isElementPositionedToLeftOf(testOnDemandLink, coachLink),
				"BOD Link is displayed to left of Coach Link");
	}

	// Verifying Sign Out link is displayed and enabled
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifySignOutLink() {
		logger.info("Verifying Sign Out link is displayed and enabled");
		getElementDisplayedAndEnabled(linkSignOut);
	}

	// To select Country Language Preference after clicking on global flag
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void selectCountryLanguagePreference() throws Exception {
		logger.info("Selecting Country Language preference");
		defaultFlag = world.getLocale();
		if (defaultFlag.equalsIgnoreCase("en_CA")) {
			newFlag = "en_US";
			world.getTestDataJson().put("Selecting Country Language", commonTestData.getString(newFlag));
			click(By.xpath(homePageElements
					.getString(
							world.isMobile() ? "country_Lang_Preference_Option_Mob" : "country_lang_preference_option")
					.replace("PLACEHOLDER", commonTestData.getString("en_US"))));
		} else if (defaultFlag.equalsIgnoreCase("fr_CA")) {
			newFlag = "en_US";
			world.getTestDataJson().put("Selecting Country Language", commonTestData.getString(newFlag));
			click(By.xpath(homePageElements
					.getString(
							world.isMobile() ? "country_Lang_Preference_Option_Mob" : "country_lang_preference_option")
					.replace("PLACEHOLDER", commonTestData.getString("en_US"))));
		} else if ((defaultFlag.equalsIgnoreCase("en_GB") || defaultFlag.equalsIgnoreCase("en_UK"))
				&& world.getIsE2E()) {
			newFlag = "en_US";
			world.getTestDataJson().put("Selecting Country Language", commonTestData.getString(newFlag));
			click(By.xpath(homePageElements
					.getString(
							world.isMobile() ? "country_Lang_Preference_Option_Mob" : "country_lang_preference_option")
					.replace("PLACEHOLDER", commonTestData.getString("en_US"))));
		} else {
			newFlag = "en_CA";
			world.getTestDataJson().put("Selecting Country Language", commonTestData.getString(newFlag));
			click(By.xpath(homePageElements
					.getString(
							world.isMobile() ? "country_Lang_Preference_Option_Mob" : "country_lang_preference_option")
					.replace("PLACEHOLDER", commonTestData.getString("en_CA"))));
		}
	}

	// TODO GZ move this to global nav? I don't see this popup
	// TODO GZ And this locator strategy doesn't work with the object class based approach in global nav page classes
	// Verifying "Country Language/Country Language updated" popup
	public void verifyCountryLanguagePopup(String popupHeader) {
		logger.info("Verifying " + popupHeader + " popup header");
		getElementDisplayedAndEnabled(
				By.xpath(countryLanguagePopupHeaderPlaceholder.replaceAll("PLACEHOLDER", popupHeader)));
	}

	// Verifying message in Country Language popup
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyMessageInCountryLanguagePopup() {
		logger.info("Verifying Message in Country Language Popup");
		String actualMessage = CommonUtilities.removeTabAndNewLine(getTextFromElement(countryLanguagePopupMessage));
		String expectedMessage = commonTestData.getString("countryLanguagePopupMessage")
				.replace("DefaultFlag", commonTestData.getString(defaultFlag))
				.replace("NewFlag", commonTestData.getString(newFlag));
		verifyTextContains(actualMessage, expectedMessage, "Country And Language text not displayed as expected");
	}



	// Verifying Shopping cart is empty
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyShoppingCartIsEmpty() {
		logger.info("Verifying Shopping Cart is Empty");
		verifyActualEqualsExpected("0", getTextFromElement(shoppingCartValue).replaceAll("\\D", ""),
				"Shopping cart is not empty");
	}

	// Verifying 2B Mindset PopUp message
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in ProductDetailsPage
	public void verify2BMindSetPopUpMessage(String expectedMessage) {
		logger.info("Verifying 2B Mindset message is updated");
		verifyActualEqualsExpected(expectedMessage, getTextFromElement(popUpMindset), "2B MindSet popup message");
	}

	// Validating maximum allowed quantity popup message text
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in CartPage
	public void validateMaxAllowedQuantityPopupMessage() {
		logger.info("Validating maximum allowed quantity popup message text");
		verifyActualEqualsExpected(addressLocale.getString("textMaxAllowedQuantityPopupMessage"),
				getTextFromElement(textMaxAllowedQtyPopupMessage), "Max Allowed Quantity Popup text");
	}

	// verifying Selected Country And Language Preference
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifySelectedCountryAndLanguagePreference(String actualFlag) {
		logger.info("Verifying Selected Country And Language Preference");
		verifyActualEqualsExpected(newFlag, actualFlag, "Selected Country And Language Preference");
	}


	// Verifying Restricted Message PopUp text
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in ProductDetailsPage
	public void verifyRestrictedPopUpText() {
		logger.info("Verifying Restricted PopUp Message text ");
		verifyElementTextEquals(restrictedPopUpMessage,
				CommonUtilities.replaceSymbols(world.getOrderDetails().get("ProductTitle")) + " "
						+ commonTestData.getString("restrictedMessage"));
	}

	// Verifying home page title based on selected tab
	public void verifyHomePageTitle(String expectedPageTitle) {
		logger.info("Validating " + expectedPageTitle + " Page Title");
		verifyActualEqualsExpected(
				CommonUtilities.replaceSmartChars(world.getLocaleResource().getString(expectedPageTitle)),
				CommonUtilities.replaceSmartChars(getPageTitle()), expectedPageTitle + " Page Title Validation");
	}

	// Verifying password is obfuscated in password field
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in SignInPage
	public void verifyPasswordIsObfuscated() {
		logger.info("Verifying Password is obfuscated");
		verifyActualEqualsExpected(true, teamtestMyAccountPage.verifyAkamaiPasswordFieldType(),
				"Failed validating Password field");
	}

	// To verify welcome user text after sign in
	// TODO: this method needs to be removed as it was implemented in TeamtestGlobalNavigationPage
	public void verifyWelcomeUserMessageInLoginSection(String expectedWelcomeTest) {
		logger.info("Verifying Welcome User text after sign in");
		verifyActualEqualsExpected(
				expectedWelcomeTest,
				CommonUtilities.replaceSmartChars(getTextFromElement(linkUser)), "Welcome User message");
	}

	// Verifying expected text displayed on menu tab
	//TODO: menu tabs are not there anymore. This method should be replaced by something else
	public void verifyTextDisplayedOnMenuTab(String tabName, String expectedTextOnMenuTab) {
		logger.info("Verifying expected text displayed on menu tab - " + expectedTextOnMenuTab);
		verifyActualEqualsExpected(world.getLocaleResource().getString(expectedTextOnMenuTab),
				getTextFromElement(By.xpath(localeHomePageElements.getString(tabName))), "Text Menu Tab");
	}

	// Verifying expected tab is displayed
	//TODO: It used in the feature which is out of scope for unification. To be deleted
	public void verifyExpectedTabIsDisplayed(String expectedTab) {
		logger.info("Verifying expected tab is displayed");
		verifyActualEqualsExpected(world.getLocaleResource().getString(expectedTab),
				getTextFromElement(pageSectionTitle), expectedTab + " Tab Title");
	}

	// Verifying Challenge Pack Restriction PopUp message
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in ProductDetailsPage
	public void verifyChallengePackRestrictionPopUpMessage() {
		logger.info("Verifying Challenge Pack Restriction PopUp message");
		verifyElementTextEquals(restrictedPopUpMessage,
				world.getLocaleResource().getString("challengePackRestrictedMsg"));
	}

	// Verifying Restriction Message PopUp close button is displayed
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in ProductDetailsPage
	public void verifyRestrictionPopUpCloseButtonIsDisplayed() {
		logger.info("Verifying Restriction PopUp close button is displayed");
		getVisibleElement(buttonClosePopUp);
	}

	// Verifying Restriction Message PopUp X(close) button is displayed
	// TODO:Don't use this method for unification. Needs to be removed as it was implemented in ProductDetailsPage
	public void verifyRestrictionPopUpXCloseButtonIsDisplayed() {
		logger.info("Verifying Restriction PopUp X close button is displayed");
		getVisibleElement(buttonXClosePopUp);
	}


}
