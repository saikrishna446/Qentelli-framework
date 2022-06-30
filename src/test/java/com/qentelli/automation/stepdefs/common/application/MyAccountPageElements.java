package com.qentelli.automation.stepdefs.common.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.qentelli.automation.common.BaseElements;
import com.qentelli.automation.exceptions.base.BaseByCssSelector;
import com.qentelli.automation.exceptions.base.BaseByXpath;
import com.qentelli.automation.utilities.PageObject;

public class MyAccountPageElements extends BaseElements {
    protected Logger logger = LogManager.getLogger(MyAccountPageElements.class);

    public ButtonAdd buttonAdd;
    public ButtonAddNew buttonAddNew;
    public ButtonCancel buttonCancel;
    public ButtonCloseAddressSuggestion buttonCloseAddressSuggestion;
    public ButtonClosePopup buttonClosePopup;
    public ButtonDeleteAddress buttonDeleteAddress;
    public ButtonMobAddNew buttonMobAddNew;
    public ButtonNewCreditCard buttonNewCreditCard;
    public ButtonNoPaymentSubscriptionOrder buttonNoPaymentSubscriptionOrder;
    public ButtonPaymentMethodsUpdate buttonPaymentMethodsUpdate;
    public ButtonPayPalAdd buttonPayPalAdd;
    public ButtonPayPalCheckout buttonPayPalCheckout;
    public ButtonPayPalConsent buttonPayPalConsent;
    public ButtonPayPalVerifyButton buttonPayPalVerifyButton;
    public ButtonPayPalLogin buttonPayPalLogin;
    public ButtonPayPalNext buttonPayPalNext;
    public ButtonPayPalSubmit buttonPayPalSubmit;
    public ButtonPopupCancel buttonPopupCancel;
    public ButtonSave buttonSave;
    public ButtonShippingUpdate buttonShippingUpdate;
    public ButtonSignIn buttonSignIn;
    public ButtonSignInAkamai buttonSignInAkamai;
    public ButtonUpdate buttonUpdate;
    public ButtonUpdateLanguage buttonUpdateLanguage;
    public ButtonUpdateProfileInformation buttonUpdateProfileInformation;
    public ButtonYesDeleteAddress buttonYesDeleteAddress;
    public CheckBoxPrimaryAddress checkBoxPrimaryAddress;
    public CheckboxRememberPayPalAccount checkboxRememberPayPalAccount;
    public EditLinkAccountAddress editLinkAccountAddress;
    public ErrorTextOrderHistory errorTextOrderHistory;
    public FrameCreditCardToken frameCreditCardToken;
    public HeaderLoginInfoPopup headerLoginInfoPopup;
    public IconImageSuccess iconImageSuccess;
    public ImagePayPalLogo imagePayPalLogo;
    public InputAccountSettingsFirstName inputAccountSettingsFirstName;
    public InputAccountSettingsLastName inputAccountSettingsLastName;
    public InputBillAddress inputBillAddress;
    public InputBillCity inputBillCity;
    public InputBillingAddress inputBillingAddress;
    public InputBillingCity inputBillingCity;
    public InputBillingPhone inputBillingPhone;
    public InputBillingZip inputBillingZip;
    public InputBillState inputBillState;
    public InputBillToCCNumber inputBillToCCNumber;
    public InputBillToLastname inputBillToLastname;
    public InputBillZipCode inputBillZipCode;
    public InputCCNumber inputCCNumber;
    public InputCCNumberMasked inputCCNumberMasked;
    public InputConfirmEmail inputConfirmEmail;
    public InputConfirmPassword inputConfirmPassword;
    public InputFirstName inputFirstName;
    public InputLastName inputLastName;
    public InputNewEmail inputNewEmail;
    public InputNewPassword inputNewPassword;
    public InputOldEmail inputOldEmail;
    public InputOldPassword inputOldPassword;
    public InputPayPalEmailID inputPayPalEmailID;
    public InputPayPalPassword inputPayPalPassword;
    public InputPhoneNumber inputPhoneNumber;
    public InputPrivacyPreferencesNo inputPrivacyPreferencesNo;
    public InputPrivacyPreferencesYes inputPrivacyPreferencesYes;
    public InputProfileSettingFirstName inputProfileSettingFirstName;
    public InputProfileSettingLastName inputProfileSettingLastName;
    public InputUserName inputUserName;
    public LabelUserNameChangeSuccessMessage labelUserNameChangeSuccessMessage;
    public LinkAccountSettings linkAccountSettings;
    public LinkCoachBusiness linkCoachBusiness;
    public LinkCoachBusinessAddressEdit linkCoachBusinessAddressEdit;
    public LinkEditAccountAddress linkEditAccountAddress;
    public LinkEditAccountInformation linkEditAccountInformation;
    public LinkEditBillToAddress linkEditBillToAddress;
    public LinkEditLanguage linkEditLanguage;
    public LinkEditPayPal linkEditPayPal;
    public LinkEditProfileInformation linkEditProfileInformation;
    public LinkEditShipAddress linkEditShipAddress;
    public LinkEditShippingAddress linkEditShippingAddress;
    public LinkEditUserName linkEditUserName;
    public LinkEditYourBusinessAddress linkEditYourBusinessAddress;
    public LinkEmailEdit linkEmailEdit;
    public LinkPasswordUpdate linkPasswordUpdate;
    public LinkPrivacyPreferences linkPrivacyPreferences;
    public LinkShippingAddress linkShippingAddress;
    public LinkSignOut linkSignOut;
    public LinkWelcome linkWelcome;
    public ModelAddressInformation modelAddressInformation;
    public PopUpDeleteAddress popUpDeleteAddress;
    public RadioButtonYesPrimaryAddress radioButtonYesPrimaryAddress;
    public RadioNo radioNo;
    public RadioNoShippingAddOnOpenSubOrders radioNoShippingAddOnOpenSubOrders;
    public RadioYesShippingAddOnOpenSubOrders radioYesShippingAddOnOpenSubOrders;
    public SelectShippingState selectShippingState;
    public SelectState selectState;
    public SelectCountry selectCountry;
    public ServiceFeesForCoachingCompanyPayPal textServiceFeesForCoachingCompanyPayPal;
    public TabAccountSettings tabAccountSettings;
    public TabCoachBusinessAddress tabCoachBusinessAddress;
    public TabCoachBusinessName tabCoachBusinessName;
    public TabCreditCard tabCreditCard;
    public TabDistributorInformation tabDistributorInformation;
    public TabGovernmentId tabGovernmentId;
    public TabMyCoach tabMyCoach;
    public TabMyOrders tabMyOrders;
    public TabPaymentMethods tabPaymentMethods;
    public TabPayPalPayment tabPayPalPayment;
    public TabShippingAddress tabShippingAddress;
    public TabSubscriptionAndMembership tabSubscriptionAndMembership;
    public TabSubscriptionMembershipPosition tabSubscriptionMembershipPosition;
    public TextShakeologytDisclaimersAccountPage textShakeologytDisclaimersAccountPage;
    public TextAccountAddresses textAccountAddresses;
    public TextAccountType textAccountType;
    public TextBillingAddress textBillingAddress;
    public TextBODPreLaunchPackPayPal textBODPreLaunchPackPayPal;
    public TextBusinessAddress textBusinessAddress;
    public TextCCDetails textCCDetails;
    public TextCoachName textCoachName;
    public TextCoachUserName textCoachUserName;
    public TextContactUsMessage textContactUsMessage;
    public TextContactMyCoach textContactMyCoach;
    public InputCreditCardNumber inputCreditCardNumber;
    public TextCreditCardNumberOnMyAccount textCreditCardNumberOnMyAccount;
    public TextDefaultLanguage textDefaultLanguage;
    public TextDisclaimerAccountCreationPage textDisclaimerAccountCreationPage;
    public TextDisclaimerBOD6MMembership textDisclaimerBOD6MMembership;
    public TextExistingEmail textExistingEmail;
    public TextExistingEmailAkamai textExistingEmailAkamai;
    public TextFirstName textFirstName;
    public TextFirstNameGreyedOut textFirstNameGreyedOut;
    public TextLastName textLastName;
    public TextLastNameGreyedOut textLastNameGreyedOut;
    public TextListOfOrders textListOfOrders;
    public TextMyAccountEmailAddress textMyAccountEmailAddress;
    public TextMyAccountTitle textMyAccountTitle;
    public TextMyOrderInfo textMyOrderInfo;
    public TextOrdersDetails textOrdersDetails;
    public TextPassword textPassword;
    public TextPasswordAkamai textPasswordAkamai;
    public TextPrimaryLanguage textPrimaryLanguage;
    public TextSecondAddressLine textSecondAddressLine;
    public TextShipAddress textShipAddress;
    public TextShippingAddress textShippingAddress;
    public TextShippingAddressAll textShippingAddressAll;
    public TextShippingCity textShippingCity;
    public TextShippingZip textShippingZip;
    public TextSubscriptionToPreLaunchPackPayPal textSubscriptionToPreLaunchPackPayPal;
    public TextUpdateSuccessMsg textUpdateSuccessMsg;

    public MyAccountPageElements(WebDriver d) {
        super(d);
        // this happens in a base class
        // objects = new PageObject(driver, this.getClass().getSimpleName());;
        buttonAdd = new ButtonAdd(objects);
        buttonAddNew = new ButtonAddNew(objects);
        buttonCancel = new ButtonCancel(objects);
        buttonCloseAddressSuggestion = new ButtonCloseAddressSuggestion(objects);
        buttonClosePopup = new ButtonClosePopup(objects);
        buttonDeleteAddress = new ButtonDeleteAddress(objects);
        buttonMobAddNew = new ButtonMobAddNew(objects);
        buttonNewCreditCard = new ButtonNewCreditCard(objects);
        buttonNoPaymentSubscriptionOrder = new ButtonNoPaymentSubscriptionOrder(objects);
        buttonPaymentMethodsUpdate = new ButtonPaymentMethodsUpdate(objects);
        buttonPayPalAdd = new ButtonPayPalAdd(objects);
        buttonPayPalCheckout = new ButtonPayPalCheckout(objects);
        buttonPayPalConsent = new ButtonPayPalConsent(objects);
        buttonPayPalVerifyButton = new ButtonPayPalVerifyButton(objects);
        buttonPayPalLogin = new ButtonPayPalLogin(objects);
        buttonPayPalNext = new ButtonPayPalNext(objects);
        buttonPayPalSubmit = new ButtonPayPalSubmit(objects);
        buttonPopupCancel = new ButtonPopupCancel(objects);
        buttonSave = new ButtonSave(objects);
        buttonShippingUpdate = new ButtonShippingUpdate(objects);
        buttonSignIn = new ButtonSignIn(objects);
        buttonSignInAkamai = new ButtonSignInAkamai(objects);
        buttonUpdate = new ButtonUpdate(objects);
        buttonUpdateLanguage = new ButtonUpdateLanguage(objects);
        buttonUpdateProfileInformation = new ButtonUpdateProfileInformation(objects);
        buttonYesDeleteAddress = new ButtonYesDeleteAddress(objects);
        checkBoxPrimaryAddress = new CheckBoxPrimaryAddress(objects);
        checkboxRememberPayPalAccount = new CheckboxRememberPayPalAccount(objects);
        editLinkAccountAddress = new EditLinkAccountAddress(objects);
        errorTextOrderHistory = new ErrorTextOrderHistory(objects);
        frameCreditCardToken = new FrameCreditCardToken(objects);
        headerLoginInfoPopup = new HeaderLoginInfoPopup(objects);
        iconImageSuccess = new IconImageSuccess(objects);
        imagePayPalLogo = new ImagePayPalLogo(objects);
        inputAccountSettingsFirstName = new InputAccountSettingsFirstName(objects);
        inputAccountSettingsLastName = new InputAccountSettingsLastName(objects);
        inputBillAddress = new InputBillAddress(objects);
        inputBillCity = new InputBillCity(objects);
        inputBillingAddress = new InputBillingAddress(objects);
        inputBillingCity = new InputBillingCity(objects);
        inputBillingPhone = new InputBillingPhone(objects);
        inputBillingZip = new InputBillingZip(objects);
        inputBillState = new InputBillState(objects);
        inputBillToCCNumber = new InputBillToCCNumber(objects);
        inputBillToLastname = new InputBillToLastname(objects);
        inputBillZipCode = new InputBillZipCode(objects);
        inputCCNumber = new InputCCNumber(objects);
        inputCCNumberMasked = new InputCCNumberMasked(objects);
        inputConfirmEmail = new InputConfirmEmail(objects);
        inputConfirmPassword = new InputConfirmPassword(objects);
        inputFirstName = new InputFirstName(objects);
        inputLastName = new InputLastName(objects);
        inputNewEmail = new InputNewEmail(objects);
        inputNewPassword = new InputNewPassword(objects);
        inputOldEmail = new InputOldEmail(objects);
        inputOldPassword = new InputOldPassword(objects);
        inputPayPalEmailID = new InputPayPalEmailID(objects);
        inputPayPalPassword = new InputPayPalPassword(objects);
        inputPhoneNumber = new InputPhoneNumber(objects);
        inputPrivacyPreferencesNo = new InputPrivacyPreferencesNo(objects);
        inputPrivacyPreferencesYes = new InputPrivacyPreferencesYes(objects);
        inputProfileSettingFirstName = new InputProfileSettingFirstName(objects);
        inputProfileSettingLastName = new InputProfileSettingLastName(objects);
        inputUserName = new InputUserName(objects);
        labelUserNameChangeSuccessMessage = new LabelUserNameChangeSuccessMessage(objects);
        linkAccountSettings = new LinkAccountSettings(objects);
        linkCoachBusiness = new LinkCoachBusiness(objects);
        linkCoachBusinessAddressEdit = new LinkCoachBusinessAddressEdit(objects);
        linkEditAccountAddress = new LinkEditAccountAddress(objects);
        linkEditAccountInformation = new LinkEditAccountInformation(objects);
        linkEditBillToAddress = new LinkEditBillToAddress(objects);
        linkEditLanguage = new LinkEditLanguage(objects);
        linkEditPayPal = new LinkEditPayPal(objects);
        linkEditProfileInformation = new LinkEditProfileInformation(objects);
        linkEditShipAddress = new LinkEditShipAddress(objects);
        linkEditShippingAddress = new LinkEditShippingAddress(objects);
        linkEditUserName = new LinkEditUserName(objects);
        linkEditYourBusinessAddress = new LinkEditYourBusinessAddress(objects);
        linkEmailEdit = new LinkEmailEdit(objects);
        linkPasswordUpdate = new LinkPasswordUpdate(objects);
        linkPrivacyPreferences = new LinkPrivacyPreferences(objects);
        linkShippingAddress = new LinkShippingAddress(objects);
        linkSignOut = new LinkSignOut(objects);
        linkWelcome = new LinkWelcome(objects);
        modelAddressInformation = new ModelAddressInformation(objects);
        popUpDeleteAddress = new PopUpDeleteAddress(objects);
        radioButtonYesPrimaryAddress = new RadioButtonYesPrimaryAddress(objects);
        radioNo = new RadioNo(objects);
        radioNoShippingAddOnOpenSubOrders = new RadioNoShippingAddOnOpenSubOrders(objects);
        radioYesShippingAddOnOpenSubOrders = new RadioYesShippingAddOnOpenSubOrders(objects);
        selectShippingState = new SelectShippingState(objects);
        selectState = new SelectState(objects);
        selectCountry = new SelectCountry(objects);
        tabAccountSettings = new TabAccountSettings(objects);
        tabCoachBusinessAddress = new TabCoachBusinessAddress(objects);
        tabCoachBusinessName = new TabCoachBusinessName(objects);
        tabCreditCard = new TabCreditCard(objects);
        tabDistributorInformation = new TabDistributorInformation(objects);
        tabGovernmentId = new TabGovernmentId(objects);
        tabMyCoach = new TabMyCoach(objects);
        tabMyOrders = new TabMyOrders(objects);
        tabPaymentMethods = new TabPaymentMethods(objects);
        tabPayPalPayment = new TabPayPalPayment(objects);
        tabShippingAddress = new TabShippingAddress(objects);
        tabSubscriptionAndMembership = new TabSubscriptionAndMembership(objects);
        tabSubscriptionMembershipPosition = new TabSubscriptionMembershipPosition(objects);
        textShakeologytDisclaimersAccountPage = new TextShakeologytDisclaimersAccountPage(objects);
        textAccountAddresses = new TextAccountAddresses(objects);
        textAccountType = new TextAccountType(objects);
        textBillingAddress = new TextBillingAddress(objects);
        textBODPreLaunchPackPayPal = new TextBODPreLaunchPackPayPal(objects);
        textBusinessAddress = new TextBusinessAddress(objects);
        textCCDetails = new TextCCDetails(objects);
        textCoachName = new TextCoachName(objects);
        textCoachUserName = new TextCoachUserName(objects);
        textContactUsMessage = new TextContactUsMessage(objects);
        textContactMyCoach = new TextContactMyCoach(objects);
        inputCreditCardNumber = new InputCreditCardNumber(objects);
        textCreditCardNumberOnMyAccount = new TextCreditCardNumberOnMyAccount(objects);
        textDefaultLanguage = new TextDefaultLanguage(objects);
        textDisclaimerAccountCreationPage = new TextDisclaimerAccountCreationPage(objects);
        textDisclaimerBOD6MMembership = new TextDisclaimerBOD6MMembership(objects);
        textExistingEmail = new TextExistingEmail(objects);
        textExistingEmailAkamai = new TextExistingEmailAkamai(objects);
        textFirstName = new TextFirstName(objects);
        textFirstNameGreyedOut = new TextFirstNameGreyedOut(objects);
        textLastName = new TextLastName(objects);
        textLastNameGreyedOut = new TextLastNameGreyedOut(objects);
        textListOfOrders = new TextListOfOrders(objects);
        textMyAccountEmailAddress = new TextMyAccountEmailAddress(objects);
        textMyAccountTitle = new TextMyAccountTitle(objects);
        textMyOrderInfo = new TextMyOrderInfo(objects);
        textOrdersDetails = new TextOrdersDetails(objects);
        textPassword = new TextPassword(objects);
        textPasswordAkamai = new TextPasswordAkamai(objects);
        textPrimaryLanguage = new TextPrimaryLanguage(objects);
        textSecondAddressLine = new TextSecondAddressLine(objects);
        textServiceFeesForCoachingCompanyPayPal = new ServiceFeesForCoachingCompanyPayPal(objects);
        textShipAddress = new TextShipAddress(objects);
        textShippingAddress = new TextShippingAddress(objects);
        textShippingAddressAll = new TextShippingAddressAll(objects);
        textShippingCity = new TextShippingCity(objects);
        textShippingZip = new TextShippingZip(objects);
        textSubscriptionToPreLaunchPackPayPal = new TextSubscriptionToPreLaunchPackPayPal(objects);
        textUpdateSuccessMsg = new TextUpdateSuccessMsg(objects);
    }

    public class TextExistingEmail extends BaseByXpath {
        TextExistingEmail(PageObject objects) {
            super(objects);
        }
    }

    public class TextExistingEmailAkamai extends BaseByXpath {
        TextExistingEmailAkamai(PageObject objects) {
            super(objects);
        }
    }

    public class TextPasswordAkamai extends BaseByXpath {
        TextPasswordAkamai(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSignInAkamai extends BaseByXpath {
        ButtonSignInAkamai(PageObject objects) {
            super(objects);
        }
    }

    public class TextPassword extends BaseByXpath {
        TextPassword(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSignIn extends BaseByXpath {
        ButtonSignIn(PageObject objects) {
            super(objects);
        }
    }

    public class TextMyAccountTitle extends BaseByXpath {
        TextMyAccountTitle(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditAccountAddress extends BaseByXpath {
        LinkEditAccountAddress(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillingPhone extends BaseByXpath {
        InputBillingPhone(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillingAddress extends BaseByXpath {
        InputBillingAddress(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillingCity extends BaseByXpath {
        InputBillingCity(PageObject objects) {
            super(objects);
        }
    }
    public class InputBillingZip extends BaseByXpath {
        InputBillingZip(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonUpdate extends BaseByXpath {
        ButtonUpdate(PageObject objects) {
            super(objects);
        }
    }

    public class TextUpdateSuccessMsg extends BaseByXpath {
        TextUpdateSuccessMsg(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonClosePopup extends BaseByXpath {
        ButtonClosePopup(PageObject objects) {
            super(objects);
        }
    }

    public class LinkShippingAddress extends BaseByXpath {
        LinkShippingAddress(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditShippingAddress extends BaseByXpath {
        LinkEditShippingAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TextShippingAddress extends BaseByXpath {
        TextShippingAddress(PageObject objects) {
            super(objects);
        }
    }

    public class SelectState extends BaseByXpath {
        SelectState(PageObject objects) {
            super(objects);
        }
    }

    public class SelectCountry extends BaseByCssSelector {
        SelectCountry(PageObject objects) {
            super(objects);
        }
    }

    public class TextShippingCity extends BaseByXpath {
        TextShippingCity(PageObject objects) {
            super(objects);
        }
    }

    public class TextShippingZip extends BaseByXpath {
        TextShippingZip(PageObject objects) {
            super(objects);
        }
    }

    public class LinkWelcome extends BaseByXpath {
        LinkWelcome(PageObject objects) {
            super(objects);
        }
    }

    public class LinkSignOut extends BaseByXpath {
        LinkSignOut(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonCloseAddressSuggestion extends BaseByXpath {
        ButtonCloseAddressSuggestion(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCoachBusiness extends BaseByXpath {
        LinkCoachBusiness(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCoachBusinessAddressEdit extends BaseByXpath {
        LinkCoachBusinessAddressEdit(PageObject objects) {
            super(objects);
        }
    }

    public class RadioNo extends BaseByXpath {
        RadioNo(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonShippingUpdate extends BaseByXpath {
        ButtonShippingUpdate(PageObject objects) {
            super(objects);
        }
    }

    public class LinkAccountSettings extends BaseByXpath {
        LinkAccountSettings(PageObject objects) {
            super(objects);
        }
    }

    public class TextFirstName extends BaseByXpath {
        TextFirstName(PageObject objects) {
            super(objects);
        }
    }

    public class TextLastName extends BaseByXpath {
        TextLastName(PageObject objects) {
            super(objects);
        }
    }

    public class TextAccountType extends BaseByXpath {
        TextAccountType(PageObject objects) {
            super(objects);
        }
    }

    public class TextDefaultLanguage extends BaseByXpath {
        TextDefaultLanguage(PageObject objects) {
            super(objects);
        }
    }

    public class TextAccountAddresses extends BaseByXpath {
        TextAccountAddresses(PageObject objects) {
            super(objects);
        }
    }

    public class TextShipAddress extends BaseByXpath {
        TextShipAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TextShippingAddressAll extends BaseByXpath {
        TextShippingAddressAll(PageObject objects) {
            super(objects);
        }
    }

    public class CheckBoxPrimaryAddress extends BaseByXpath {
        CheckBoxPrimaryAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TabShippingAddress extends BaseByXpath {
        TabShippingAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TabPaymentMethods extends BaseByXpath {
        TabPaymentMethods(PageObject objects) {
            super(objects);
        }
    }

    public class TextCCDetails extends BaseByXpath {
        TextCCDetails(PageObject objects) {
            super(objects);
        }
    }

    public class TextBillingAddress extends BaseByXpath {
        TextBillingAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TabMyCoach extends BaseByXpath {
        TabMyCoach(PageObject objects) {
            super(objects);
        }
    }

    public class TextCoachName extends BaseByXpath {
        TextCoachName(PageObject objects) {
            super(objects);
        }
    }

    public class TextMyOrderInfo extends BaseByXpath {
        TextMyOrderInfo(PageObject objects) {
            super(objects);
        }
    }

    public class TabMyOrders extends BaseByXpath {
        TabMyOrders(PageObject objects) {
            super(objects);
        }
    }

    public class TextCoachUserName extends BaseByXpath {
        TextCoachUserName(PageObject objects) {
            super(objects);
        }
    }

    public class TabCoachBusinessAddress extends BaseByXpath {
        TabCoachBusinessAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TextBusinessAddress extends BaseByXpath {
        TextBusinessAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TextListOfOrders extends BaseByXpath {
        TextListOfOrders(PageObject objects) {
            super(objects);
        }
    }

    public class TextOrdersDetails extends BaseByXpath {
        TextOrdersDetails(PageObject objects) {
            super(objects);
        }
    }

    public class RadioButtonYesPrimaryAddress extends BaseByXpath {
        RadioButtonYesPrimaryAddress(PageObject objects) {
            super(objects);
        }
    }

    public class SelectShippingState extends BaseByXpath {
        SelectShippingState(PageObject objects) {
            super(objects);
        }
    }

    public class ModelAddressInformation extends BaseByXpath {
        ModelAddressInformation(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonAddNew extends BaseByXpath {
        ButtonAddNew(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonMobAddNew extends BaseByXpath {
        ButtonMobAddNew(PageObject objects) {
            super(objects);
        }
    }

    public class InputFirstName extends BaseByXpath {
        InputFirstName(PageObject objects) {
            super(objects);
        }
    }

    public class InputLastName extends BaseByXpath {
        InputLastName(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonAdd extends BaseByXpath {
        ButtonAdd(PageObject objects) {
            super(objects);
        }
    }

    public class InputPhoneNumber extends BaseByXpath {
        InputPhoneNumber(PageObject objects) {
            super(objects);
        }
    }

    public class InputCCNumber extends BaseByXpath {
        InputCCNumber(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillAddress extends BaseByXpath {
        InputBillAddress(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillCity extends BaseByCssSelector {
        InputBillCity(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillState extends BaseByCssSelector {
        InputBillState(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillZipCode extends BaseByCssSelector {
        InputBillZipCode(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditBillToAddress extends BaseByXpath {
        LinkEditBillToAddress(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillToLastname extends BaseByXpath {
        InputBillToLastname(PageObject objects) {
            super(objects);
        }
    }

    public class InputBillToCCNumber extends BaseByXpath {
        InputBillToCCNumber(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPaymentMethodsUpdate extends BaseByXpath {
        ButtonPaymentMethodsUpdate(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditYourBusinessAddress extends BaseByXpath {
        LinkEditYourBusinessAddress(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditShipAddress extends BaseByXpath {
        LinkEditShipAddress(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonDeleteAddress extends BaseByXpath {
        ButtonDeleteAddress(PageObject objects) {
            super(objects);
        }
    }

    public class PopUpDeleteAddress extends BaseByXpath {
        PopUpDeleteAddress(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonYesDeleteAddress extends BaseByXpath {
        ButtonYesDeleteAddress(PageObject objects) {
            super(objects);
        }
    }

    public class TextShakeologytDisclaimersAccountPage extends BaseByXpath {
      TextShakeologytDisclaimersAccountPage(PageObject objects) {
        super(objects);
      }
    }

    public class ErrorTextOrderHistory extends BaseByXpath {
        ErrorTextOrderHistory(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEmailEdit extends BaseByXpath {
        LinkEmailEdit(PageObject objects) {
            super(objects);
        }
    }

    public class InputOldEmail extends BaseByXpath {
        InputOldEmail(PageObject objects) {
            super(objects);
        }
    }

    public class InputNewEmail extends BaseByXpath {
        InputNewEmail(PageObject objects) {
            super(objects);
        }
    }

    public class InputConfirmEmail extends BaseByXpath {
        InputConfirmEmail(PageObject objects) {
            super(objects);
        }
    }

    public class InputOldPassword extends BaseByXpath {
        InputOldPassword(PageObject objects) {
            super(objects);
        }
    }

    public class InputNewPassword extends BaseByXpath {
        InputNewPassword(PageObject objects) {
            super(objects);
        }
    }

    public class InputConfirmPassword extends BaseByXpath {
        InputConfirmPassword(PageObject objects) {
            super(objects);
        }
    }

    public class LinkPasswordUpdate extends BaseByXpath {
        LinkPasswordUpdate(PageObject objects) {
            super(objects);
        }
    }

    public class LinkPrivacyPreferences extends BaseByXpath {
        LinkPrivacyPreferences(PageObject objects) {
            super(objects);
        }
    }

    public class InputPrivacyPreferencesYes extends BaseByXpath {
        InputPrivacyPreferencesYes(PageObject objects) {
            super(objects);
        }
    }

    public class InputPrivacyPreferencesNo extends BaseByXpath {
        InputPrivacyPreferencesNo(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSave extends BaseByXpath {
        ButtonSave(PageObject objects) {
            super(objects);
        }
    }

    public class InputCCNumberMasked extends BaseByXpath {
        InputCCNumberMasked(PageObject objects) {
            super(objects);
        }
    }

    public class InputCreditCardNumber extends BaseByXpath {
        InputCreditCardNumber(PageObject objects) {
            super(objects);
        }
    }

    public class TextDisclaimerAccountCreationPage extends BaseByXpath {
        TextDisclaimerAccountCreationPage(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditProfileInformation extends BaseByXpath {
        LinkEditProfileInformation(PageObject objects) {
            super(objects);
        }
    }

    public class TextContactMyCoach extends BaseByXpath {
        TextContactMyCoach(PageObject objects) {
            super(objects);
        }
    }

    public class TextContactUsMessage extends BaseByXpath {
        TextContactUsMessage(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonUpdateProfileInformation extends BaseByXpath {
        ButtonUpdateProfileInformation(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditLanguage extends BaseByXpath {
        LinkEditLanguage(PageObject objects) {
            super(objects);
        }
    }

    public class TextPrimaryLanguage extends BaseByXpath {
        TextPrimaryLanguage(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonUpdateLanguage extends BaseByXpath {
        ButtonUpdateLanguage(PageObject objects) {
            super(objects);
        }
    }

    public class InputProfileSettingFirstName extends BaseByXpath {
        InputProfileSettingFirstName(PageObject objects) {
            super(objects);
        }
    }

    public class InputProfileSettingLastName extends BaseByXpath {
        InputProfileSettingLastName(PageObject objects) {
            super(objects);
        }
    }

    public class InputAccountSettingsFirstName extends BaseByXpath {
        InputAccountSettingsFirstName(PageObject objects) {
            super(objects);
        }
    }

    public class InputAccountSettingsLastName extends BaseByXpath {
        InputAccountSettingsLastName(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonCancel extends BaseByXpath {
        ButtonCancel(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditUserName extends BaseByXpath {
        LinkEditUserName(PageObject objects) {
            super(objects);
        }
    }

    public class InputUserName extends BaseByXpath {
        InputUserName(PageObject objects) {
            super(objects);
        }
    }

    public class LabelUserNameChangeSuccessMessage extends BaseByXpath {
        LabelUserNameChangeSuccessMessage(PageObject objects) {
            super(objects);
        }
    }

    public class TextCreditCardNumberOnMyAccount extends BaseByXpath {
        TextCreditCardNumberOnMyAccount(PageObject objects) {
            super(objects);
        }
    }

    public class TextSecondAddressLine extends BaseByXpath {
        TextSecondAddressLine(PageObject objects) {
            super(objects);
        }
    }

    public class HeaderLoginInfoPopup extends BaseByXpath {
        HeaderLoginInfoPopup(PageObject objects) {
            super(objects);
        }
    }

    public class IconImageSuccess extends BaseByXpath {
        IconImageSuccess(PageObject objects) {
            super(objects);
        }
    }

    public class TextMyAccountEmailAddress extends BaseByXpath {
        TextMyAccountEmailAddress(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPopupCancel extends BaseByXpath {
        ButtonPopupCancel(PageObject objects) {
            super(objects);
        }
    }

    public class TabAccountSettings extends BaseByXpath {
        TabAccountSettings(PageObject objects) {
            super(objects);
        }
    }

    public class TabPayPalPayment extends BaseByXpath {
        TabPayPalPayment(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalCheckout extends BaseByXpath {
        ButtonPayPalCheckout(PageObject objects) {
            super(objects);
        }
    }

    public class ImagePayPalLogo extends BaseByXpath {
        ImagePayPalLogo(PageObject objects) {
            super(objects);
        }
    }

    public class InputPayPalEmailID extends BaseByXpath {
        InputPayPalEmailID(PageObject objects) {
            super(objects);
        }
    }

    public class InputPayPalPassword extends BaseByXpath {
        InputPayPalPassword(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalNext extends BaseByXpath {
        ButtonPayPalNext(PageObject objects) {
            super(objects);
        }
    }

    public class CheckboxRememberPayPalAccount extends BaseByXpath {
        CheckboxRememberPayPalAccount(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalLogin extends BaseByXpath {
        ButtonPayPalLogin(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalSubmit extends BaseByXpath {
        ButtonPayPalSubmit(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalConsent extends BaseByXpath {
        ButtonPayPalConsent(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalVerifyButton extends BaseByXpath {
        ButtonPayPalVerifyButton(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditPayPal extends BaseByXpath {
        LinkEditPayPal(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPayPalAdd extends BaseByXpath {
        ButtonPayPalAdd(PageObject objects) {
            super(objects);
        }
    }

    public class TextDisclaimerBOD6MMembership extends BaseByXpath {
        TextDisclaimerBOD6MMembership(PageObject objects) {
            super(objects);
        }
    }

    public class ServiceFeesForCoachingCompanyPayPal extends BaseByXpath {
        ServiceFeesForCoachingCompanyPayPal(PageObject objects) {
            super(objects);
        }
    }

    public class TextBODPreLaunchPackPayPal extends BaseByXpath {
        TextBODPreLaunchPackPayPal(PageObject objects) {
            super(objects);
        }
    }

    public class TextSubscriptionToPreLaunchPackPayPal extends BaseByXpath {
        TextSubscriptionToPreLaunchPackPayPal(PageObject objects) {
            super(objects);
        }
    }

    public class TextFirstNameGreyedOut extends BaseByXpath {
        TextFirstNameGreyedOut(PageObject objects) {
            super(objects);
        }
    }

    public class TextLastNameGreyedOut extends BaseByXpath {
        TextLastNameGreyedOut(PageObject objects) {
            super(objects);
        }
    }

    public class TabCreditCard extends BaseByXpath {
        TabCreditCard(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonNewCreditCard extends BaseByXpath {
        ButtonNewCreditCard(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonNoPaymentSubscriptionOrder extends BaseByXpath {
        ButtonNoPaymentSubscriptionOrder(PageObject objects) {
            super(objects);
        }
    }

    public class RadioYesShippingAddOnOpenSubOrders extends BaseByXpath {
        RadioYesShippingAddOnOpenSubOrders(PageObject objects) {
            super(objects);
        }
    }

    public class RadioNoShippingAddOnOpenSubOrders extends BaseByXpath {
        RadioNoShippingAddOnOpenSubOrders(PageObject objects) {
            super(objects);
        }
    }

    public class FrameCreditCardToken extends BaseByXpath {
        FrameCreditCardToken(PageObject objects) {
            super(objects);
        }
    }

    public class LinkEditAccountInformation extends BaseByXpath {
        LinkEditAccountInformation(PageObject objects) {
            super(objects);
        }
    }

    public class TabSubscriptionAndMembership extends BaseByXpath {
        TabSubscriptionAndMembership(PageObject objects) {
            super(objects);
        }
    }

    public class TabSubscriptionMembershipPosition extends BaseByXpath {
        TabSubscriptionMembershipPosition(PageObject objects) {
            super(objects);
        }
    }

    public class TabCoachBusinessName extends BaseByXpath {
        TabCoachBusinessName(PageObject objects) {
            super(objects);
        }
    }

    public class TabGovernmentId extends BaseByXpath {
        TabGovernmentId(PageObject objects) {
            super(objects);
        }
    }

    public class TabDistributorInformation extends BaseByXpath {
        TabDistributorInformation(PageObject objects) {
            super(objects);
        }
    }

    public class EditLinkAccountAddress extends BaseByXpath {
        EditLinkAccountAddress(PageObject objects) {
            super(objects);
        }
    }

}
