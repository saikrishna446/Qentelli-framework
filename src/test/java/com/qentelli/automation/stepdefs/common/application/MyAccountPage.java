package com.qentelli.automation.stepdefs.common.application;

import static com.qentelli.automation.utilities.CommonUtilities.extractPhoneNumber;
import static com.qentelli.automation.utilities.CommonUtilities.normalizeSpecialChars;
import static com.qentelli.automation.utilities.CommonUtilities.removeNonAlphaNumeric;
import static com.qentelli.automation.utilities.CommonUtilities.removeNonNumbers;
import static com.qentelli.automation.utilities.CommonUtilities.removeSpaces;
import static com.qentelli.automation.utilities.CommonUtilities.replaceSmartChars;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.qentelli.automation.common.World;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.pages.common.CommonPage;
import com.qentelli.automation.utilities.TBBTestDataObject;

//todo
public class MyAccountPage extends BasePage {
  private Logger logger = LogManager.getLogger(MyAccountPage.class);
	private World world;
	private CommonPage commonPage;
	private MyAccountPageElements body;
	private TBBTestDataObject tbbTestData;

	public MyAccountPage(World world) {
		super(world, world.driver);
		this.world = world;
		body = new MyAccountPageElements(world.driver);
		tbbTestData = new TBBTestDataObject();
		commonPage = new CommonPage(world);
	}

	//Akamai login is different than the regular login page
	//todo create a new page for Akamai Login and move the Akamai Login related controls and methods there
	//todo there are few other pages with redundant methods and controls for Akamai Login. Those can go off after the akamai login page is created

	//Clicks on the signin button on the Akamai page
	public void clickSignInButtonOnAkamai() {
		logger.info("Clicking on SignIn button on Akamai page");
		body.buttonSignInAkamai.click();
	}

	//Click on Edit Account Address link
	public void clickEditAccountAddressLink() {
		logger.info("Clicking on Edit Account Address link");
		click(body.linkEditAccountAddress.by());
	}

	//Click on close popup button
	public void clickClosePopUpButton() {
		logger.info("Clicking on Close popup");
		body.buttonClosePopup.click();
	}

	//Click Shipping Address link
	public void clickShippingAddressLink() {
		logger.info("Clicking on Shipping Address link");
		click(body.linkShippingAddress.by());
	}

	//Click edit shipping address link
	public void clickEditShippingAddressLink() {
		logger.info("Clicking on Edit Shipping Address link");
		click(body.linkEditShippingAddress.by());
	}


	//Clicks on sign-out link
	public void clickSignOutLink() {
		logger.info("Clicking on Sign-out Link");
		click(body.linkSignOut.by());
	}

	//Click close on the address suggestion popup
	public void clickCloseAddressSuggestionPopUp() {
		logger.info("Clicking on close address suggestion popup");
		click(body.buttonCloseAddressSuggestion.by());
	}

	//Click on no subscription order radio button
	public void clickNoSubscriptionOrderRadiobutton() {
		logger.info("Selecting No Subscription Order radio button");
		clickIfVisible(body.radioNo.by());
	}

	//Click on Shipping Update button
	public void clickShippingUpdateButton() {
		logger.info("Clicking on Shipping Update button");
		click(body.buttonShippingUpdate.by());
	}

	// TODO: review usage after 09/2021 release
	//Click on Account Settings link
	public void clickOnAccountSettingsLink() {
		logger.info("Clicking on Account Settings link");
		click(body.linkAccountSettings.by());
	}

	//Click update button if displayed
	public void clickUpdateIfDisplayed() {
		clickIfVisible(body.buttonUpdate.by());
	}

	// TODO: review usage after 09/2021 release
	//Click update payments methods button if displayed
	public void clickUpdatePaymentMethodsIfDisplayed() {
		clickIfVisible(body.buttonPaymentMethodsUpdate.by());
	}

	//Click Payment methods tab
	public void clickPaymentMethodsTab() {
		logger.info("Clicking on Payment methods tab");
		click(body.tabPaymentMethods.by());
	}

	//Click on My coach tab
	public void clickMyCoachTab() {
		logger.info("Clicking on My Coach Tab");
		click(body.tabMyCoach.by());
	}

	//Click on the Coach Business Address Tab
	public void clickCoachBusinessAddressTab() {
		logger.info("Clicking on Coach Business Address Tab");
		click(body.tabCoachBusinessAddress.by());
	}

	//Click on Coach Business link
	public void clickCoachBusinessLink() {
		logger.info("Clicking on Coach Business link");
		click(body.linkCoachBusiness.by());
	}

	//Click on Coach Business Address Edit link
	public void clickOnCoachBusinessAddressEditLink() {
		logger.info("Clicking on coach business address edit link in my account page");
		click(body.linkCoachBusinessAddressEdit.by());
	}

	//Click on Add New Shipping Address Button
	public void clickOnAddNewShippingAddressButton() {
		logger.info("Clicking on add new address button");
		click(body.buttonAddNew.by());
	}


	//Clicks the Primary Shipping Address Yes radio button
	public void clickPrimaryShippingAddressYesRadioButton() {
		logger.info("Clicking On Primary Address Yes Radio Button");
		click(body.radioButtonYesPrimaryAddress.by());
	}

	//Click on Add button on address information popup
	public void clickOnAddButtonOnAddressInformationPopUp() {
		logger.info("Clicking On Add Button On Address Information Pop-Up");
		click(body.buttonAdd.by());
	}

	// Clicking On Link Edit Account Address
	public void clickEditLinkAccountAddress() {
		logger.info("Clicking On Link Edit Account Address");
		click(body.linkEditAccountAddress.by());
	}

	// Clicking On Link Edit Shopping Address
	public void clickEditLinkShoppingAddress() {
		logger.info("Clicking On Link Edit Shopping Address");
		click(body.linkEditShipAddress.by());
	}

	// Clicking On Link Edit Bill To Address
	public void clickEditLinkBillToAddress() {
		logger.info("Clicking On Link Edit Bill To Address");
		click(body.linkEditBillToAddress.by());
	}

	// Clicking On Link Edit Business Address
	public void clickEditLinkYourBusinessAddress() {
		logger.info("Clicking On Link Edit Business Address");
		click(body.linkEditYourBusinessAddress.by());
	}

	//Click the Payment Method Update button
	public void clickPaymentMethodUpdateButton() {
		logger.info("Clicking on Payment method update button");
		mouseHoverAndClickElementByActions(body.buttonPaymentMethodsUpdate.by());
	}

	//Click delete address button
	public void clickDeleteAddressButton() {
		logger.info("Clicking on Delete Address button");
		click(body.buttonDeleteAddress.by());
	}

	//Click yes on delete address popup
	public void clickYesDeleteAddressPopup() {
		logger.info("Clicking Yes on Delete Address popup");
		click(body.buttonYesDeleteAddress.by());
	}

	//Click Edit Email link
	public void clickEditEmailLink() {
		logger.info("Clicking Edit email link");
		click(body.linkEmailEdit.by());
	}

	//Clicks the button - Update
	public void clickUpdateButton() {
		logger.info("Clicking on 'Update' button");
		click(body.buttonUpdate.by());
	}

	//Clicks the update password link
	public void clickUpdatePasswordLink() {
		logger.info("Clicking on 'Update Password' link");
		click(body.linkPasswordUpdate.by());
	}

	//Clicks close button on email update
	public void clickCloseEmailUpdateButton() {
		logger.info("Clicking on 'Close' email update button");
		body.buttonClosePopup.click();
	}

	//Click Privacy and Preferences link
	public void clickPrivacyAndPreferencesLink() {
		logger.info("Clicking Privacy and Preferences link");
		click(body.linkPrivacyPreferences.by());
	}

	// Click on edit for updating profile information
	public void clickEditProfileInformationLink() {
		logger.info("Click on edit profile information link");
		click(body.linkEditProfileInformation.by());
	}

	//Click username edit link
	public void clickEditUserNameLink() {
		logger.info("Clicking on Edit user name link");
		click(body.linkEditUserName.by());
	}

	//Clicks the field - Save
	public void clickSaveButton() {
		logger.info("Clicking on 'Save' button");
		click(body.buttonSave.by());
	}

	//Clicks the field - UpdateBtnForProfileInformation
	public void clickUpdateButtonForProfileInformation() {
		logger.info("Clicking on 'UpdateBtnForProfileInformation' button");
		body.buttonUpdateProfileInformation.click();
	}

	//Clicks the field - EditLinkForLanguage
	public void clickEditLinkForLanguage() {
		logger.info("Clicking on 'EditLinkForLanguage' link");
		click(body.linkEditLanguage.by());
	}

	//Clicks the field - UpdateLanguageBtn
	public void clickUpdateLanguageButton() {
		logger.info("Clicking on 'UpdateLanguageBtn' button");
		submitForm(body.textPrimaryLanguage.by());
	}

	//Clicks the field - CancelBtn
	public void clickCancelButton() {
		logger.info("Clicking on 'CancelBtn' button");
		click(body.buttonCancel.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - BtnPopupCancel
	public void clickPopupCancelButton() {
		logger.info("Clicking on 'BtnPopupCancel' button");
		click(body.buttonPopupCancel.by());
	}

	//Clicks the field - PayPalPaymentTab
	public void clickPayPalPaymentTab() {
		logger.info("Clicking on 'PayPalPaymentTab' webelement");
		click(body.tabPayPalPayment.by());
	}

	//Clicks the field - Shipping Address Tab
	public void clickShippingAddressTab() {
		logger.info("Clicking on 'Shipping Address Tab' webelement");
		click(body.tabShippingAddress.by());
	}

	//Clicks the field - PayPalCheckoutButton
	public void clickPayPalCheckoutButton() {
		logger.info("Clicking on 'PayPalCheckoutButton' button");
		click(body.buttonPayPalCheckout.by());
	}

	//Clicks the field - PayPalNext
	public void clickPayPalNextButton() {
		logger.info("Clicking on 'PayPalNext' button");
		click(body.buttonPayPalNext.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - CreditCardTab
	public void clickCreditCardTab() {
		logger.info("Clicking on 'CreditCardTab' link");
		click(body.tabCreditCard.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - NewCreditCardButton
	public void clickNewCreditCardButton() {
		logger.info("Clicking on 'NewCreditCardButton' button");
		click(body.buttonNewCreditCard.by());
	}

	//CLicks on the welcome link
	public void clickWelcomeLink() {
		logger.info("Clicking on the 'Welcome' link");
		click(body.linkWelcome.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - NoPaymentSubscriptionOrderButton
	public void clickNoPaymentSubscriptionOrderButton() {
		logger.info("Clicking on 'NoPaymentSubscriptionOrderButton' button");
		click(body.buttonNoPaymentSubscriptionOrder.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - RadioYesShippingAddOnOpenSubOrders
	public void clickRadioYesShippingAddOnOpenSubOrders() {
		logger.info("Clicking on 'RadioYesShippingAddOnOpenSubOrders' radiobutton");
		click(body.radioYesShippingAddOnOpenSubOrders.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - RadioNoShippingAddOnOpenSubOrders
	public void clickRadioNoShippingAddOnOpenSubOrders() {
		logger.info("Clicking on 'RadioNoShippingAddOnOpenSubOrders' radiobutton");
		click(body.radioNoShippingAddOnOpenSubOrders.by());
	}

	//Clicks the field - RememberPayPalAccount
	public void clickRememberPayPalAccountCheckbox() {
		logger.info("Clicking on 'RememberPayPalAccount' checkbox");
		click(body.checkboxRememberPayPalAccount.by());
	}

	//Clicks the field - PayPalLogin
	public void clickPayPalLoginButton() {
		logger.info("Clicking on 'PayPalLogin' button");
		click(body.buttonPayPalLogin.by());
	}

	//Clicks the field - PayPalSubmit
	public void clickPayPalSubmitButton() {
		logger.info("Clicking on 'PayPalSubmit' button");
		click(body.buttonPayPalSubmit.by());
	}

	//Clicks the field - PayPalConsent
	public void clickPayPalConsentButton() {
		logger.info("Clicking on 'PayPalConsent' button");
		clickByJS(body.buttonPayPalConsent.by());
	}

	// Clicking verifyPayPal Button
	public void clickVerifyPayPalPaymentButton() {
		logger.info("Clicking verifyPayPal Button");
		click(body.buttonPayPalVerifyButton.by());
	}

	// TODO: review usage after 09/2021 release
	//Clicks the field - EditLinkPayPal
	public void clickEditLinkPayPal() {
		logger.info("Clicking on 'EditLinkPayPal' link");
		click(body.linkEditPayPal.by());
	}

	//Clicks the field - PayPalAdd
	public void clickPayPalAddButton() {
		logger.info("Clicking on 'PayPalAdd' button");
		click(body.buttonPayPalAdd.by());
	}

	// Clicking on Sign In Button
	public void clickSignInButton() {
		logger.info("Clicking on Sign In Button");
		click(body.buttonSignIn.by());
	}

	//Select billing state
	public void selectBillingState(String state) {
		logger.info("Selecting billing state - " + state);
		selectByValueInDropdown(body.inputBillState.by(), state);
	}

	//Selects Yes option for "I want support from my Coach to help me reach my health and fitness goals"
	public void selectYesIWantCoachSupportOption() {
		logger.info("Selecting 'YES' for I want support from my Coach to help me reach my health and fitness goals");
		click(body.inputPrivacyPreferencesYes.by());
	}

	//Selects No option for "I want support from my Coach to help me reach my health and fitness goals"
	public void selectNoIDoNotWantCoachSupportOption() {
		logger.info("Selecting 'NO' for I want support from my Coach to help me reach my health and fitness goals");
		click(body.inputPrivacyPreferencesNo.by());
	}

	//Select Shipping State
	public void selectShippingState(String state) {
		logger.info("Selecting shipping state - " + state);
		selectByValueInDropdown(body.selectShippingState.by(), state);
	}

	//Switch to credit card details frame
	public void switchToCreditCardDetailsFrame() {
		logger.info("Switching to credit card details frame");
		switchToFrame(body.frameCreditCardToken.by());
	}

	//Select State
	public void selectState(String state) {
		logger.info("Selecting state from the dropdown" + state);
		selectByValueInDropdown(body.selectState.by(), state);
	}

	//Select language
	public void selectLanguage(String language) {
		logger.info("Selecting language - " + language);
		selectByValueInDropdown(body.textPrimaryLanguage.by(), language);
	}

	//toggle Remember PayPal account checkbox
	public void toggleRememberPayPalAccount(boolean checked) {
		if (isSelected(body.checkboxRememberPayPalAccount.by()) != checked) {
			logger.info("Clicking to set remember paypal account checkbox to " + checked);
			clickRememberPayPalAccountCheckbox();
		}
	}

	//Enter new first name in profile setting
	public void enterNewFirstName(String firstName) {
		logger.info("Entering new first name '" + firstName + "' in profile setting");
		enterText(body.inputProfileSettingFirstName.by(), firstName);
	}

	//Enter new last name in profile setting
	public void enterNewLastName(String lastName) {
		logger.info("Entering new last name '" + lastName + "' in profile setting");
		enterText(body.inputProfileSettingLastName.by(), lastName);
	}

	//Enters text into the field - OldEmail
	public void enterOldEmail(String text) {
		logger.info("Entering '" + text + "' into the OldEmail field");
		enterText(body.inputOldEmail.by(), text);
	}

	//Enters text into the field - New Email
	public void enterNewEmail(String text) {
		logger.info("Entering '" + text + "' into the NewEmail field");
		enterText(body.inputNewEmail.by(), text);
	}

	//Enters text into the field - Confirm Email
	public void enterConfirmEmail(String text) {
		logger.info("Entering '" + text + "' into the ConfirmEmail field");
		enterText(body.inputConfirmEmail.by(), text);
	}

	//Enter old password
	public void enterOldPassword(String oldPassword) {
		logger.info("Enter old password - " + oldPassword);
		enterText(body.inputOldPassword.by(), oldPassword);
	}

	//Enter new password
	public void enterNewPassword(String newPassword) {
		logger.info("Enter New password - " + newPassword);
		enterText(body.inputNewPassword.by(), newPassword);
	}

	//Enter confirm password
	public void enterConfirmPassword(String newPassword) {
		logger.info("Enter Confirm password - " + newPassword);
		enterText(body.inputConfirmPassword.by(), newPassword);
	}

	//Enter Credit Card Number
	public void enterCreditCardNumber(String creditCardNumber) {
		logger.info("Entering Credit Card Number - " + creditCardNumber);
		enterText(body.inputCreditCardNumber.by(), creditCardNumber, Keys.TAB);
	}

	// TODO: review usage after 09/2021 release
	//Enters text into the field - CCNumberMasked
	public void enterCCNumberMasked(String text) {
		logger.info("Entering '" + text + "' into the CCNumberMasked field");
		enterText(body.inputCCNumberMasked.by(), text);
	}

	// TODO: review usage after 09/2021 release
	//Enters text into the field - CCNumberInFrame
	public void enterCCNumberInFrame(String text) {
		logger.info("Entering '" + text + "' into the CCNumberInFrame field");
		enterText(body.inputCreditCardNumber.by(), text);
	}

	// TODO: review usage after 09/2021 release
	//Enters text into the field - ProfileSettingFirstName
	public void enterProfileSettingFirstName(String text) {
		logger.info("Entering '" + text + "' into the ProfileSettingFirstName field");
		enterText(body.inputProfileSettingFirstName.by(), text);
	}

	// TODO: review usage after 09/2021 release
	//Enters text into the field - ProfileSettingLastName
	public void enterProfileSettingLastName(String text) {
		logger.info("Entering '" + text + "' into the ProfileSettingLastName field");
		enterText(body.inputProfileSettingLastName.by(), text);
	}

	// TODO: review usage after 09/2021 release
	//Enters text into the field - AccountSettingsFirstName
	public void enterAccountSettingsFirstName(String text) {
		logger.info("Entering '" + text + "' into the AccountSettingsFirstName field");
		enterText(body.inputAccountSettingsFirstName.by(), text);
	}

	// TODO: review usage after 09/2021 release
	//Enters text into the field - AccountSettingsLastName
	public void enterAccountSettingsLastName(String text) {
		logger.info("Entering '" + text + "' into the AccountSettingsLastName field");
		enterText(body.inputAccountSettingsLastName.by(), text);
	}

	//Enters text into the field - UserName
	public void enterUserName(String text) {
		logger.info("Entering '" + text + "' into the UserName field");
		enterText(body.inputUserName.by(), text);
	}

	//Enters text into the field - PayPalEmailID
	public void enterPayPalEmailID(String text) {
		logger.info("Entering '" + text + "' into the PayPalEmailID field");
		enterText(body.inputPayPalEmailID.by(), text);
	}

	//Enters text into the field - PayPalPassword
	public void enterPayPalPassword(String text) {
		logger.info("Entering '" + text + "' into the PayPalPassword field");
		enterText(body.inputPayPalPassword.by(), text);
	}

	//Enters the password into the password field
	public void enterPassword(String password) {
		logger.info("Entering customer's password - " + password);
		enterText(body.textPassword.by(), password);
	}

	//Akamai login is different than the regular login page
	//todo create a new page for Akamai Login and move the Akamai Login related controls and methods there
	//todo there are few other pages with redundant methods and controls for Akamai Login. Those can go off after the akamai login page is created

	//Enter the email address into the akamai login page
	public void enterEmailOnAkamai(String email) {
		logger.info("Entering customer email address " + email + " into the Akamai login field");
		enterText(body.textExistingEmailAkamai.by(), email.trim());
	}

	//Akamai login is different than the regular login page
	//todo create a new page for Akamai Login and move the Akamai Login related controls and methods there
	//todo there are few other pages with redundant methods and controls for Akamai Login. Those can go off after the akamai login page is created

	//Enter password into the Akamai password field
	public void enterPasswordOnAkamai(String password) {
		logger.info("Entering customer's password " + password + " into the Akamai password field");
		enterText(body.textPasswordAkamai.by(), password);
	}

	//Enter the billing phone number
	public void enterBillingPhoneNumber(String phone) {
		logger.info("Entering billing phone number - " + phone);
		enterText(body.inputBillingPhone.by(), phone);
	}

	//Enter the billing address
	public void enterBillingAddress(String address) {
		logger.info("Entering billing address -" + address);
		enterText(body.inputBillingAddress.by(), address);
	}

	//Enter billing city
	public void enterBillingCity(String city) {
		logger.info("Entering Billing City-" + city);
		enterText(body.inputBillingCity.by(), city);
	}

	//Enter the Billing Zip code
	public void enterBillingZipCode(String zipCode) {
		logger.info("Entering Billing Zip Code-" + zipCode);
		enterText(body.inputBillingZip.by(), zipCode);
	}

	//Enter the shipping address
	public void enterShippingAddress(String address) {
		logger.info("Entering shipping address - " + address);
		enterText(body.textShippingAddress.by(), address);
	}

	//Enter Shipping city value
	public void enterShippingCity(String city) {
		logger.info("Entering Shipping City - " + city);
		enterText(body.textShippingCity.by(), city);
	}

	//Enter First Name value
	public void enterFirstName(String firstName) {
		logger.info("Entering first name - " + firstName);
		enterText(body.inputFirstName.by(), firstName);
	}

	//Enter Last Name value
	public void enterLastName(String lastName) {
		logger.info("Entering last name - " + lastName);
		enterText(body.inputLastName.by(), lastName);
	}

	//Enter Phone number value
	public void enterPhoneNumber(String phoneNumber) {
		logger.info("Entering phone number - " + phoneNumber);
		enterText(body.inputPhoneNumber.by(), phoneNumber);
	}

	//Enter the shipping zip code
	public void enterShippingZipCode(String zipCode) {
		logger.info("Entering Shipping Zip Code" + "-" + zipCode);
		enterText(body.textShippingZip.by(), zipCode);
	}

	//Get the cleansed first name value
	public String getFirstName() {
		logger.info("Reading First Name");
		return getTextFromElement(body.textFirstName.by()).split(":")[1].trim();
	}

	//Get the cleansed last name value
	public String getLastName() {
		logger.info("Reading Last Name");
		return getTextFromElement(body.textLastName.by()).split(":")[1].trim();
	}

	//Get the street address value
	public String getStreetAddress() {
		logger.info("Reading street address");
		return replaceSmartChars(getTextFromElement(getPresentElements(body.textAccountAddresses.by()).get(0)));
	}

	//Get the state value
	public String getStateFromAddress() {
		logger.info("Reading state from address");
		return replaceSmartChars(getTextFromElement(getPresentElements(body.textAccountAddresses.by()).get(1)));
	}

	//Get the country value
	public String getCountryFromAddress() {
		logger.info("Reading country from address");
		return replaceSmartChars(getTextFromElement(getPresentElements(body.textAccountAddresses.by()).get(2)));
	}

	//Get the phone number value
	public String getPhoneNumberFromAddress() {
		logger.info("Reading phone number from address");
		return getTextFromElement(getPresentElements(body.textAccountAddresses.by()).get(3)).replace("Phone: ", "");
	}

	//Gets the cleansed Language info
	public String getLanguageInfo() {
		logger.info("Reading Language Selected in My Account");
		return getTextFromElement(body.textDefaultLanguage.by());
	}

	//Gets the cleansed Account Type info
	public String getAccountType() {
		logger.info("Reading the Account Type info");
		return getTextFromElement(body.textAccountType.by()).split(":")[1];
	}

	//Get Shipping Address Street
	public String getShippingAddressStreet() {
		logger.info("Getting Shipping Address Street");
		return removeNonAlphaNumeric(getTextFromElement(getPresentElements(body.textShipAddress.by()).get(1)));
	}

	//Get Shipping Address State
	public String getShippingAddressState() {
		logger.info("Getting Shipping Address State");
		return removeNonAlphaNumeric(getTextFromElement(getPresentElements(body.textShipAddress.by()).get(2)));
	}

	//Get Shipping Address Country
	public String getShippingAddressCountry() {
		logger.info("Getting Shipping Address Country");
		return removeNonAlphaNumeric(getTextFromElement(getPresentElements(body.textShipAddress.by()).get(3)));
	}

	//Get Shipping Address Phone
	public String getShippingAddressPhone() {
		logger.info("Getting Shipping Address Phone");
		return extractPhoneNumber(getTextFromElement(getPresentElements(body.textShipAddress.by()).get(4)));
	}

	//Get Name on Credit Card
	public String getNameOnCreditCard() {
		logger.info("Getting Name on Credit Card");
		return getTextFromElement(getPresentElements(body.textCCDetails.by()).get(0)).replace("edit", "").trim();
	}

	//Get Expiry Date on Credit Card
	public String getExpiryDateOnCreditCard() {
		logger.info("Getting Expiry Date on Credit Card");
		return getTextFromElement(getPresentElements(body.textCCDetails.by()).get(2)).replace("Expires, ", "").trim();
	}

	//Get billing address street
	public String getBillingAddressStreet() {
		logger.info("Reading Billing Address Street");
		return getTextFromElement(getPresentElements(body.textBillingAddress.by()).get(0));
	}

	//Get billing address state
	public String getBillingAddressState() {
		logger.info("Reading Billing Address State");
		return getTextFromElement(getPresentElements(body.textBillingAddress.by()).get(1));
	}

	// TODO: review usage after 09/2021 release
	//Get billing address phone
	public String getBillingAddressPhone() {
		logger.info("Reading Billing Address Phone");
		return getTextFromElement(getPresentElements(body.textBillingAddress.by()).get(3)).replace("Phone : ", "").trim();
	}

	//Get billing address country
	public String getBillingAddressCountry() {
		logger.info("Reading Billing Address Country");
		return getTextFromElement(getPresentElements(body.textBillingAddress.by()).get(2));
	}

	//Get business address street
	public String getBusinessAddressStreet() {
		logger.info("Reading Business Address Street");
		return getTextFromElement(getPresentElements(body.textBusinessAddress.by()).get(0));
	}

	//Get business address state
	public String getBusinessAddressState() {
		logger.info("Reading Business Address State");
		return getTextFromElement(getPresentElements(body.textBusinessAddress.by()).get(1));
	}

	//Get business address phone
	public String getBusinessAddressPhone() {
		logger.info("Reading Business Address Phone");
		return getTextFromElement(getPresentElements(body.textBusinessAddress.by()).get(3)).replace("Phone : ", "").trim();
	}

	//Gets the coach name
	public String getCoachName() {
		logger.info("Reading coach name");
		return StringUtils.deleteWhitespace(getTextFromElement(body.textCoachName.by()));
	}

	//Get the coach user name
	public String getCoachUserName() {
		logger.info("Getting the coach user name");
		return ((getTextFromElement(body.textCoachUserName.by())).split(":")[1]).replace("edit", "").trim();
	}

	//Get the shakeology disclaimer text from account page
	public String getShakeologyDisclaimerText() {
		logger.info("Reading the shakeology disclaimer text");
		return getTextFromElement(body.textShakeologytDisclaimersAccountPage.by());
	}

	//Get updated first name in profile setting
	public String getUpdatedFirstName() {
		logger.info("Getting updated first name from profile setting");
		return getAttributeValueFromElement(body.inputProfileSettingFirstName.by(), "value");
	}

	//Get updated last name in profile setting
	public String getUpdatedLastName() {
		logger.info("Getting updated last name from profile setting");
		return getAttributeValueFromElement(body.inputProfileSettingLastName.by(), "value");
	}

	//Gets selected language
	public String getSelectedLanguage() {
		logger.info("Getting selected language");
		return getSelectedTextFromDropdown(body.textPrimaryLanguage.by());
	}

	// TODO: review usage after 09/2021 release
	// Gets the field - Service Fees For Coaching Company PayPal
	public String getServiceFeesForCoachingCompanyPayPal() {
		logger.info("Getting text 'Service Fees For Coaching Company PayPal' ");
		return getTextFromElement(body.textServiceFeesForCoachingCompanyPayPal.by());
	}

	// TODO: review usage after 09/2021 release
	// Gets the field - Label User Name Change Success Message
	public String getUserNameChangeSuccessMessage() {
		logger.info("Getting text 'Label User Name Change Success Message'");
		return getTextFromElement(body.labelUserNameChangeSuccessMessage.by());
	}

	// Gets the field - Credit Card Number On My Account
	public String getCreditCardNumberOnMyAccount() {
		logger.info("Getting text 'Credit Card Number On My Account'");
		return getTextFromElement(body.textCreditCardNumberOnMyAccount.by());
	}

	// Gets the field - Shipping Address All
	public String getShippingAddressAllText() {
		logger.info("Getting text 'Shipping Address All'");
		return getTextFromElement(body.textShippingAddressAll.by());
	}

	//Check if open subscription orders are available
	public boolean isOpenSubscriptionOrderAvailable() {
		logger.info("Checking if subscription orders are available");
		return isElementDisplayedWithLeastWait(body.radioYesShippingAddOnOpenSubOrders.by());
	}

	//Check if delete address button is displayed
	public boolean isDeleteAddressButtonDisplayed() {
		logger.info("Checking if Delete Address button is displayed");
		return isElementDisplayedAndEnabled(body.buttonDeleteAddress.by());
	}

	// TODO: review usage after 09/2021 release
	//verifies the field - Password
	public boolean verifyPasswordFieldType() {
		logger.info("Validating the password field based on the 'type' attribute of the password field");
		return getAttributeValueFromElement(body.textPassword.by(), "type").equalsIgnoreCase("password");
	}

	// TODO: review usage after 09/2021 release
	//verifies the field - BOD6MMembershipDisclaimerPaymentPage
	public void verifyBOD6MMembershipDisclaimerPaymentPage() {
		logger.info("Verifying 'BOD 6Months Membership Disclaimer Payment Page' text");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textDisclaimerBOD6MMembership.by()), "BOD 6Months Membership Disclaimer Payment Page Text");
	}

	//verifies the field - Akamai Password
	public boolean verifyAkamaiPasswordFieldType() {
		logger.info("Validating the akamai password field based on the 'type' attribute of the password field");
		return getAttributeValueFromElement(body.textPasswordAkamai.by(), "type").equalsIgnoreCase("password");
	}

	// TODO: review usage after 09/2021 release
	//verifies the field - DisclaimerAccountCreationPage
	public void verifyDisclaimerAccountCreationPage() {
		logger.info("Verifying the 'DisclaimerAccountCreationPage' text");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textDisclaimerAccountCreationPage.by()), "Disclaimer Account Creation Page Text");
	}

	//verify shakeology disclaimer on account page
	public void verifyShakeologyDisclaimer(String text) {
		verifyTextContains(replaceSmartChars(text),
				replaceSmartChars(getShakeologyDisclaimerText()), "Shakeology Disclaimer Text");
	}

	//Verify Name on Credit Card
	public void verifyNameOnCreditCard(String expected) {
		logger.info("Verifying Name on Credit Card Matches - " + expected);
		verifyActualEqualsExpected(expected, getNameOnCreditCard(), "Name on credit card");
	}

	//Verify Expiry Date on Credit Card
	public void verifyExpiryOnCreditCard(String expected) {
		logger.info("Verifying Expiry on Credit Card Matches - " + expected);
		verifyActualEqualsExpected(expected, getExpiryDateOnCreditCard(), "Expiry Date on credit card");
	}

	//verify first name
	public void verifyFirstName(String text) {
		logger.info("Verifying first name - " + text);
		verifyActualEqualsExpected(text, getFirstName(), "First Name");
	}

	//verify Last name
	public void verifyLastName(String text) {
		logger.info("Verifying last name - " + text);
		verifyActualEqualsExpected(text, getLastName(), "Last Name");
	}

	//verify Preferred language
	public void verifyPreferredLanguage(String text) {
		logger.info("Verifying preferred language - " + text);
		verifyActualEqualsExpected(text, getLanguageInfo(), "Preferred Language");
	}

	//verify Address details
	public void verifyAddressDetails(String text) {
		logger.info("Verifying Address Details - " + text);
		verifyActualEqualsExpected(text.replaceAll(" ", ""), (getStreetAddress() + ", " + getStateFromAddress()).replaceAll(" ", ""), "Address Details");
	}

	//verify Phone Number
	public void verifyPhoneNumber(String text) {
		logger.info("Verifying phone number - " + text);
		verifyActualEqualsExpected(text, getPhoneNumberFromAddress(), "Phone Number");
	}

	//Verify my account page is displayed
	public void verifyMyAccountPageIsDisplayed() {
		logger.info("Verifying My Account page is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textFirstName.by()), "My Account Page is displayed");
	}

	//Verify Sign out button is displayed
	public void verifySignOutButtonIsDisplayed() {
		logger.info("Verifying sign out button is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkSignOut.by()), "Sign Out button display check");
	}

	//Validate the successful message is displayed after update and that matches the expected value
	public void validateUpdateSuccessfulMessage(String message) {
		logger.info("Verifying update successful message '" + message + "' is displayed");
		verifyActualEqualsExpected(message, body.textUpdateSuccessMsg.getText(), "Account update msg");
	}

	//Checks is welcome link is displayed
	public void verifyWelcomeLink() {
		logger.info("Verifying the welcome link is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkWelcome.by()), "Welcome Link");
	}


	//Verify Shipping Address
	public void verifyShippingAddress(String expected) {
		logger.info("Verifying shipping address");
		expected = removeNonAlphaNumeric(expected);
		String actual = getShippingAddressStreet() + getShippingAddressState();
		verifyActualEqualsExpected(expected, actual, "Shipping Address");
	}

	//Verify Shipping Address Phone
	public void verifyShippingAddressPhone(String expected) {
		logger.info("Verifying shipping address phone");
		expected = extractPhoneNumber(expected);
		String actual = extractPhoneNumber(getShippingAddressPhone());
		verifyActualEqualsExpected(expected, actual, "Shipping Address Phone");
	}

	//verify Primary Address checkbox is checked
	public void verifyPrimaryAddressCheckboxIsChecked() {
		logger.info("Verify Primary Address checkbox is checked");
		verifyActualEqualsExpected(true, isSelected(body.checkBoxPrimaryAddress.by()), "Primary Address Checkbox");
	}

	//Verify orders in Orders page
	public void verifyOrdersDisplayed() {
		logger.info("Verifying orders are displayed in the orders tab");
		verifyActualEqualsExpected(false, getPresentElements(body.textListOfOrders.by()).isEmpty(), "Orders List in Orders tab");
	}

	//verify business address
	public void verifyBusinessAddress(String expected) {
		logger.info("Verifying business address");
		String actual = StringUtils.deleteWhitespace((getBusinessAddressStreet() + getBusinessAddressState()).replaceAll(",", ""));
		expected = StringUtils.deleteWhitespace(expected.replaceAll(",", ""));
		verifyActualEqualsExpected(expected, actual, "Business Address");
	}

	//verify business address phone
	public void verifyBusinessAddressPhone(String expected) {
		logger.info("Verifying business address phone");
		String actual = removeNonNumbers(getBusinessAddressPhone());
		expected = removeNonNumbers(expected);
		verifyActualEqualsExpected(expected, actual, "Business Address Phone");
	}

	// Verifies the email field is pre-populated
	public void verifyEmailIsPrePopulated() {
		logger.info("verifying if the email field has a value pre-populated");
		verifyActualEqualsExpected(world.getEmail(), getAttributeValueFromElement(body.textExistingEmail.by(), "value"), "Pre-populated email");
	}

	//Verify the Address Information modal is displayed
	public void verifyAddressInformationModelIsDisplayed() {
		logger.info("Verifying Address Information Model");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.modelAddressInformation.by()), "Address Information Modal display");
	}

	//Verify Primary Shipping Address - Yes is selected
	public void verifyPrimaryAddressYesRadioSelected() {
		logger.info("Verify if the Primary Shipping Yes Radio button is selected");
		verifyActualEqualsExpected(true, isSelected(body.radioButtonYesPrimaryAddress.by()), "Primary Ship Address Yes selected");
	}


	//Verifies button popup cancel button is displayed
	public void verifyCancelPopupButtonIsDisplayed() {
		logger.info("Verifying the 'Cancel' button in popup is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.buttonPopupCancel.by()), "Popup Cancel Button Display");
	}

	//Verify the coach name matches the expected value
	public void verifyCoachName(String expectedCoachName) {
		logger.info("Verifying coach name matches - " + expectedCoachName);
		verifyActualEqualsExpected(expectedCoachName, getCoachName(), "Coach Name");
	}

	//Verifies the Update button is displayed
	public void verifyUpdateButtonIsDisplayed() {
		logger.info("Verifying the 'Update' button is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.buttonUpdate.by()), "Update Button Display");
	}

	//Verifies the Success Icon is displayed
	public void verifySuccessIconIsDisplayed() {
		logger.info("Verifying the 'Success' icon is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.iconImageSuccess.by()), "Success Icon Display");
	}


	//Verify delete address popup is displayed
	public void verifyDeleteAddressPopupIsDisplayed() {
		logger.info("Checking if the Delete Address popup is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.popUpDeleteAddress.by()), "Delete Address Login Popup");
	}

	//Verify Login info popup is displayed
	public void verifyLoginInfoPopupIsDisplayed() {
		logger.info("Verifying login info popup is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.headerLoginInfoPopup.by()), "Login Popup");
	}


	//Verify Coach Business Service Fees Disclaimer
	public void verifyCoachBusinessServiceFeesDisclaimer() {
		logger.info("Verifying coach business service fees disclaimer");
		verifyElementTextContainsExpectedText(tbbTestData.coachBusinessServiceFeeText, body.textDisclaimerAccountCreationPage.by());
	}

	//Verify Disclaimer Text
	public void verifyDisclaimerByProductName(String productName) {
		logger.info("Verifying disclaimer text for " + productName);
		verifyActualEqualsExpected(true, replaceSmartChars(getTextFromElement(body.textDisclaimerAccountCreationPage.by())).contains(replaceSmartChars(world.getLocaleResource().getString(productName))), "Disclaimer Text");
	}

	//This is to verify the disclaimer by accpeting the disclaimer as a parameter
	public void verifyDisclaimer(String disclaimer) {
		logger.info("Verifying disclaimer text on account creation page");
		verifyTextContainsIgnoreCase(disclaimer, body.textDisclaimerAccountCreationPage.getText(), "Disclaimer Text");
	}

	//Verify contact us message in profile information pop-up
	public void verifyContactUsMessage() {
		logger.info("Verifying contact us message on profile information pop-up");
		verifyActualEqualsExpected(true, getTextFromElement(body.textContactUsMessage.by()).contains(world.getLocaleResource().getString("contactUsMessageOnProfileInformationPopUp")), "Contact Us message");
	}

	//verify default language is displayed
	public void verifyDefaultLanguageDisplayed() {
		logger.info("Verifying default language is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textDefaultLanguage.by()), "Default Language Displayed");
	}

	//verify primary language is displayed
	public void verifyPrimaryLanguageDisplayed() {
		logger.info("Verifying primary language is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textPrimaryLanguage.by()), "Primary Language");
	}


	//verify first name value of the field from account settings
	public void verifyAccountSettingsFirstName(String expectedValue) {
		logger.info("Verifying first name - " + expectedValue);
		verifyTextContains(expectedValue, body.inputAccountSettingsFirstName.getText(), "Verify FirstName");
	}

	//verify last name value of the field from account settings
	public void verifyAccountSettingsLastName(String expectedValue) {
		logger.info("Verifying last name - " + expectedValue);
		verifyTextContains(expectedValue, body.inputAccountSettingsLastName.getText(), "Verify LastName");
	}

	//verify Language text on the account settings page
	public void verifyAccountSettingsLanguage(String expectedValue) {
		logger.info("Verifying Language - " + expectedValue);
		verifyActualEqualsExpected(expectedValue, getTextFromElement(body.textDefaultLanguage.by()), "Default Language");
	}

	//Verify Success message was displayed after user name was updated
	public void verifyUpdateUserNameSuccessMessage() {
		logger.info("Verifying User Name success message is displayed");
		verifyElementTextEquals(body.labelUserNameChangeSuccessMessage.by(), tbbTestData.userNameChangeSuccessMessage);
	}

	//Verify Account Settings tab is selected by default
	public void verifyAccountSettingsTabIsSelected() {
		logger.info("Verifying Account Settings tab is selected");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.tabAccountSettings.by()), " Account Settings tab is selected");
	}

	//Verify Updated email address value
	public void verifyEmailAddressValue() {
		logger.info("Verifying email address value");
		verifyActualEqualsExpected(getTextFromElement(body.textMyAccountEmailAddress.by()), world.getTestDataJson().get("Existing2B_Username"), "My Account Email Address");
	}

	//Tabs out of Paypal payments tab to get to checkout button
	public void tabOutPayPalPaymentsTab() {
		logger.info("Tabbing out of paypal payments tab");
		getPresentElement(body.tabPayPalPayment.by()).sendKeys(Keys.TAB);
	}

	//Verify PayPal logo on Account creation page
	public void verifyPayPalLogoIsDisplayed() {
		logger.info("Verifying PayPal logo");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.imagePayPalLogo.by()), "PayPal Account Logo");
	}

	//verify edit PayPal account link is displayed
	public void verifyEditPayPalAccountLinkIsDisplayed() throws Exception {
		logger.info("Verifying edit PayPal Account link is displayed.");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.linkEditPayPal.by()), "PayPal Account Link");
	}

	/*
	@Description: To verify the Customer Credit card and Billing info in Payment Methods Tab
	 */
	public void verifyCustomersPaymentInfo() {
		int retry = 0;
		try {
			while (retry < 5) {
				logger.info("Verifying Credit Card information");
				//Adding retry as the customer information has to be updated in EBS, which is taking some time
				click(body.tabPaymentMethods.by());
				commonPage.validateLoadingSpinnerByApplicationName("TBB", 30);
				scrollElementIntoViewJS(body.textCCDetails.by());
				String name = getPresentElements(body.textCCDetails.by()).get(0).getText().split("\n")[0].trim();
				if (name.equalsIgnoreCase(world.getCreditCardDetails().get("CCName"))) {
					break;
				}
				retry++;
			}
			List<WebElement> creditCardElements = getPresentElements(body.textCCDetails.by());
			String creditCardNNum = creditCardElements.get(1).getText();
			logger.info("Credit Card Number is ::" + creditCardNNum);
			logger.info("Verifying Credit Card details are updated");
			String name = creditCardElements.get(0).getText().split("\n")[0].trim();
			String expiryDate = creditCardElements.get(2).getText().split(", ")[1].trim();
			String expectedExpiry = world.getCreditCardDetails().get("CCMonth") + "/" + world.getCreditCardDetails().get("CCYear").substring(2, 4);
			Assert.assertEquals(name, world.getCreditCardDetails().get("CCName"), "Failed to validate credit card name");
			Assert.assertEquals(expiryDate, expectedExpiry, "Failed to validate credit card expiry date");
			Assert.assertEquals(creditCardElements.get(1).getText().split("\n")[0].trim(), tbbTestData.maskedCCNumber, "Failed to validate credit card number");

			logger.info("Verifying Billing information");
			List<WebElement> addressElements = getPresentElements(body.textBillingAddress.by());
			if (world.getLocale().equalsIgnoreCase("en_gb")) {
				Assert.assertTrue(addressElements.get(0).getText().trim().contains(world.getUpdateAccountDetails().get("BillingAddress")));
				Assert.assertEquals(addressElements.get(1).getText(), world.getUpdateAccountDetails().get("BillingCity") + " " + world.getUpdateAccountDetails().get("BillingZipCode"), "Failed to verify city");
			} else {
				String address2 = world.getUpdateAccountDetails().get("BillingCity") + ", " + world.getUpdateAccountDetails().get("BillingState") + " " + world.getUpdateAccountDetails().get("BillingZipCode");
				Assert.assertTrue(addressElements.get(0).getText().trim().contains(world.getUpdateAccountDetails().get("BillingAddress")));
				Assert.assertEquals(addressElements.get(1).getText(), address2, "Failed to verify address details");
			}
		} catch (Exception e) {
			failScenarioAndReportInfo(this.getClass().getSimpleName() + " >> " + world.getMyMethodName(), e);
		}
	}

	// Verify Shakeology subscription disclaimer on account page
	public void verifyShakeologySubscriptionDisclaimer() {
		logger.info("Verifying hakeology subscription disclaimer on Account page");
		verifyElementTextContainsExpectedText(tbbTestData.textShakeologySubscriptionDisclaimer, body.textDisclaimerAccountCreationPage.by());
	}

	// Verify Performance Stack disclaimer on account page
	public void verifyPerformanceStackDisclaimer() {
		logger.info("Verifying Performance Stack disclaimer on Account page");
		verifyElementTextContainsExpectedText(tbbTestData.textPerformanceStackDisclaimer, body.textDisclaimerAccountCreationPage.by());
	}

	//Validate Mindset membership subscription disclaimer on account page
	public void verifyMindsetSubscriptionDisclaimerOnAccountPage() {
		logger.info("Verifying mindset membership subscription disclaimer");
		if (world.getTestEnvironment().equalsIgnoreCase("qa3")) {
			verifyElementTextContainsExpectedText(world.getLocaleResource().getString("mindsetMembershipSubscriptionDisclaimerQA"), body.textDisclaimerAccountCreationPage.by());
		} else {
			verifyElementTextContainsExpectedText(world.getLocaleResource().getString("mindsetMembershipSubscriptionDisclaimer"), body.textDisclaimerAccountCreationPage.by());
		}
	}

	//Verify FirstName field is grayed out
	public void verifyFirstNameIsGrayedOut() {
		logger.info("Verify First Name is grayed out");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textFirstNameGreyedOut.by()), "First Name Grayed Out");
	}

	//Verify Last Name field is grayed out
	public void verifyLastNameIsGrayedOut() {
		logger.info("Verify Last Name is grayed out");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.textLastNameGreyedOut.by()), "Last Name Grayed Out");
	}

	//Verify CreditCard tab is displayed
	public void verifyCreditCardTabIsDisplayed() {
		logger.info("Verifying Credit Card tab is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.tabCreditCard.by()), "Credit Card Tab Display");
	}

	//Verify PayPal tab is displayed
	public void verifyPayPalTabIsDisplayed() {
		logger.info("Verifying PayPal tab is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.tabPayPalPayment.by()), "PayPal Tab Display");
	}

	//Verify new credit card button is displayed
	public void verifyNewCreditCardButtonIsDisplayed() {
		logger.info("Verifying New Credit Card Button is displayed");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.buttonNewCreditCard.by()), "Credit Card Button");
	}


	//Verify account type
	public void verifyAccountType(String accountType) {
		logger.info("Verifying account type");
		verifyActualEqualsExpected(normalizeSpecialChars(removeSpaces(accountType)), normalizeSpecialChars(removeSpaces(getAccountType())), "Account Type");
	}

	//Verify edit account information link is not displayed
	public void verifyEditAccountInfoLinkIsNotDisplayed() {
		logger.info("Verifying edit account information link is not displayed");
		verifyActualEqualsExpected(false, isElementDisplayedWithLeastWait(body.linkEditAccountInformation.by()), "Edit account information link");
	}

	//This method wait till Add Button on address information to become invisible to avoid the response in JSON format on UI
	public void waitForAddButtonInvisibility(){
		logger.info("Waiting for Add Button on address information to become invisible");
		waitForElementInvisibility(body.buttonAdd.by());
	}

	//This method wait till Shipping Update Button on address information to become invisible to avoid the response in JSON format on UI
	public void waitForShippingUpdateButtonInvisibility(){
		logger.info("Waiting for Shipping Update Button on address information to become invisible");
		waitForElementInvisibility(body.buttonShippingUpdate.by());
	}

	//This method wait till Update Button on Profile Information information to become invisible to avoid the response in JSON format on UI
	public void waitForProfileInformationUpdateButtonInvisibility(){
		logger.info("Waiting for Update Button on Profile information to become invisible");
		waitForElementInvisibility(body.buttonUpdateProfileInformation.by());
	}

	// Verifying Subscription and Membership settings tab is displayed
	public void verifySubscriptionAndMembershipTabIsDisplayed() {
		logger.info("Verifying Subscription & Membership settings tab is displayed.");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.tabSubscriptionAndMembership.by()), "Subscription and Membership Tab Display");
	}

	//Verify subscription & membership tab is displayed below the payments tab
	public void verifySubscriptionAndMembershipTabDisplayPosition() {
		logger.info("Verifying Subscription & Membership tab is displayed below the payments tab.");
		String expected = world.getLocaleResource().getString("TextSubscriptionAndMembershipTab");
		String actual = getTextFromElement(body.tabSubscriptionMembershipPosition.by());
		verifyTextContainsIgnoreCase(expected, actual, "Subscription & Membership tab position check");
	}

	//Verify Account Settings tab is displayed in My Account Section
	public void verifyAccountSettingsTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Account Settings tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithMediumWait(body.linkAccountSettings.by()), "Account Settings Tab Display");
	}

	//Verify Shipping Address tab is displayed in My Account Section
	public void verifyShippingAddressTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Shipping Address tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.linkShippingAddress.by()), "Shipping Address Tab Display");
	}

	//Verify Payment Method tab is displayed in My Account Section
	public void verifyPaymentMethodTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Payment Method tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabPaymentMethods.by()), "Payment Method Tab Display");
	}

	//Verify My Orders tab is displayed in My Account Section
	public void verifyMyOrdersTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying My Orders tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabMyOrders.by()), "My Orders Tab Display");
	}

	//Verify My Coach tab is displayed in My Account Section
	public void verifyMyCoachTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying My Coach tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabMyCoach.by()), "My Coach Tab Display");
	}

	//Verify Coach Business Name tab is displayed in My Account Section
	public void verifyCoachBusinessNameTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Coach Business Name tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabCoachBusinessName.by()), "Coach Business Name Tab Display");
	}

	//Verify Coach Business Address tab is displayed in My Account Section
	public void verifyCoachBusinessAddressTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Coach Business Address tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabCoachBusinessAddress.by()), "Coach Business Address Tab Display");
	}

	//Verify Coach Business Address tab is displayed in My Account Section
	public void verifyGovernmentIdTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Government Id tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabGovernmentId.by()), "Government Id Tab Display");
	}

	//Verify Distributor Information tab is displayed in My Account Section
	public void verifyDistributorInformationTabDisplayed(boolean expectedStatus) {
		logger.info("Verifying Government Id tab is displayed in My Account Settings."+ expectedStatus);
		verifyActualEqualsExpected(expectedStatus, isElementDisplayedWithLeastWait(body.tabDistributorInformation.by()), "Distributor Information Tab Display");
	}

	//This method waits till PaymentUpdate Button on Payment information to become invisible to avoid the response in JSON format on UI
	public void waitForPaymentUpdateButtonInvisibility(){
		logger.info("Waiting for Shipping Update Button on address information to become invisible");
		waitForElementInvisibility(body.buttonPaymentMethodsUpdate.by());
	}

	// Verifying My Orders tab is selected
	public void verifyMyOrdersTabIsSelected() {
		logger.info("Verifying My Orders tab is selected");
		verifyActualEqualsExpected(true, isElementDisplayedAndEnabled(body.tabMyOrders.by()), "Active My Orders Tab");
	}

	// Verifying Country is not editable
	public void verifyCountryFieldIsNotEditable() {
		logger.info("Verifying My country is not editable");
		verifyActualEqualsExpected(false, body.selectCountry.exists(), "Country Dropdown is Disabled");
	}

	// Getting email text from email field
	public String getEmail() {
		logger.info("Getting email field value");
		return getTextFromElement(body.textMyAccountEmailAddress.by());
	}

	// Getting email text from Contact My coach email field
	public String getContactMyCoachEmail() {
		logger.info("Getting email text from Contact My coach email field");
		return getAttributeValueFromElement(body.textContactMyCoach.by(), "href").replace("mailto:", "");
	}
}
