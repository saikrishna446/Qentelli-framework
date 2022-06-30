package com.qentelli.automation.testdatafactory.api;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qentelli.automation.common.World;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import com.qentelli.automation.testdatafactory.data.User;

public class TBBOrder {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private FactoryConfig factoryConfig;

	private RestRequest restRequest;

	private String shopEndPoint;
	private String fitnessCDPEndPoint;
	private String shakeologyCDPEndPoint;
	private String challengePackCPDEndPoint;
	private String gearCPDEndPoint;
	private String chocolateShakeologyPDPEndPoint;
	private String twentyOneDayFixBaseKitPDPEndPoint;
	private String eightyDayObsessionPDPEndPoint;
	private String bbStepPDPEndPoint;
	private String addToCart21DayFixEndPoint;
	private String addToCartChocShakeolgyEndPoint;
	private String addToCart80DayObessionEndPoint;
	private String addToCartBBStepEndPoint;
	private String sessionPattern;
	private String loginEndPoint;
	private Map<String, String> authOAMHeaders;
	private String authOAMEndPoint;
	private String authOAMBody;
	private String checkoutEndPoint;
	private Map<String, String> shippingHeaders;
	private String shippingEndPoint;
	private String shippingBodyStartWithoutSession;
	private String shippingBodyMiddleWithoutCoachID;
	private String shippingBodyEndWithoutPushSite;
	private Map<String, String> paymentSubmitOrderHeaders;
	private String paymentSubmitOrderEndPoint;
	private String paymentSubmitOrderBodyStartWithoutSession;
	private String paymentSubmitOrderBodyEndWithoutPushSite;
	private String confirmationEndPoint;
	private String confirmationPattern;
	private String logoutEndPoint;
	private String authRedirectURL;
	private String session;
	private String orderNumber;
	private World world;
	private WebDriver driver;

    public TBBOrder(World world) {
        this.world = world;
        factoryConfig = world.factoryConfig;
        restRequest = new RestRequest();
        shakeologyCDPEndPoint = factoryConfig.getServerUrl() + "/b/shakeology";
        chocolateShakeologyPDPEndPoint = factoryConfig.getServerUrl() + "/d/chocolate-shakeology-SHKChocolate";
        loginEndPoint = factoryConfig.getServerUrl() + "/checkout/login";
        authOAMEndPoint = factoryConfig.getAuthURL() + "/authentication";
        checkoutEndPoint = factoryConfig.getServerUrl() + "/checkout/?bso=true";
        shippingEndPoint = "https://www-tbb" + world.getTestEnvironment().toLowerCase() + ".qentelli.com"
                + "/rest/model/atg/commerce/order/purchase/ShippingGroupActor/shipToNewAddress?_DARGS=/shop/account/addresses/checkoutShippingAddressForm.jsp";
        shippingHeaders = new HashMap<String, String>() {
            {
                put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }
        };
        paymentSubmitOrderEndPoint = "https://www-tbb" + world.getTestEnvironment().toLowerCase() + ".qentelli.com"
                + "/rest/model/atg/commerce/order/purchase/PaymentGroupActor/applyPaymentAndCommitOrder"
                + "?_DARGS=/shop/checkout/2.0/includes/paymentMethodNotEmptyPayment.jsp";
        paymentSubmitOrderHeaders = new HashMap<String, String>() {
            {
                put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }
        };
        confirmationEndPoint = factoryConfig.getServerUrl() + "/checkout/confirmation";
        confirmationPattern = "el.innerHTML = '(.+?)';";
        logoutEndPoint = factoryConfig.getAuthURL() + "/logout?end_url=" + factoryConfig.getServerUrl()
                + "&DPSLogout=true";
        shippingBodyMiddleWithoutCoachID = "&checkoutAction=newUserShippingContinue&firstName=Test&_D%3AfirstName=+&lastName=Test"
                + "&_D%3AlastName=+&phoneNumber=3235551212&_D%3AphoneNumber=+&_D%3Acountry=+&country=US&address1=3301+Exposition+Bvd"
                + "&_D%3Aaddress1=+&_D%3Aaddress2=+&city=Santa+Monica&_D%3Acity=+&_D%3Astate=+&state=CA&postalCode=90404"
                + "&_D%3ApostalCode=+&_D%3A%2Fatg%2Fcommerce%2Forder%2Fpurchase%2FShippingGroupFormHandler.shippingMethod=+&"
                + "%2Fatg%2Fcommerce%2Forder%2Fpurchase%2FShippingGroupFormHandler.shippingMethod=1&_D%3A%2Fatg%2Fcommerce%2Forder%2Fpurchase"
                + "%2FShippingGroupFormHandler.shippingGroupId=+&gncCoachId=";
        shippingBodyEndWithoutPushSite = "&isCoach=true&isPersonalUse=on&%2Fatg%2Fcommerce%2Forder%2Fpurchase"
                + "%2FShippingGroupFormHandler.priceSuppression=false&_D%3A%2Fatg%2Fcommerce%2Forder%2Fpurchase"
                + "%2FShippingGroupFormHandler.priceSuppression=+&shipToAddressName=YTYMDTIIJGUEFKZ&_D%3AshipToAddressName=+&_DARGS=%2Fshop"
                + "%2Faccount%2Faddresses%2FcheckoutShippingAddressForm.jsp&pushSite=";
        shippingBodyStartWithoutSession = "_dyncharset=utf-8&_dynSessConf=";
        paymentSubmitOrderBodyStartWithoutSession = "_dyncharset=utf-8&_dynSessConf=";
        paymentSubmitOrderBodyEndWithoutPushSite = "&ccCVV=123&_D%3AccCVV=+&existingPayment=true&diffBillingAddrs=false"
                + "&_D%3AdiffBillingAddrs=&firstName=Test&_D%3AfirstName=+&lastName=Test&_D%3AlastName=+"
                + "&phoneNumber=&_D%3AphoneNumber=&address1=&_D%3Aaddress1=&address2="
                + "&_D%3Aaddress2=&postalCode=&_D%3ApostalCode=&city=&_D%3Acity=&state=&_D%3Astate="
                + "&country=&_D%3Acountry=&ccNumber=9418844961251111&_D%3AccNumber=+&ccMonth=04&_D%3AccMonth=+&ccYear=2022"
                + "&_D%3AccYear=&ccType=visa&_D%3AccType=+&purchaseTC=on&_DARGS=%2Fshop%2Fcheckout%2F2.0"
                + "%2Fincludes%2FpaymentMethodNotEmptyPayment.jsp&pushSite=";
        shopEndPoint = factoryConfig.getServerUrl();
        fitnessCDPEndPoint = factoryConfig.getServerUrl() + "/b/fitness";
        challengePackCPDEndPoint = factoryConfig.getServerUrl() + "/b/challenge-packs";
        gearCPDEndPoint = factoryConfig.getServerUrl() + "/b/gear";
        twentyOneDayFixBaseKitPDPEndPoint = factoryConfig.getServerUrl()
                + "/d/21-day-fix-21-day-fix-extreme-bod-shakeology-cp-21DChallengePackAA";
        eightyDayObsessionPDPEndPoint = factoryConfig.getServerUrl()
                + "/d/80-day-obsession-annual-qentelli-on-demand-performance-pack-80DPerformancePackAA";
        bbStepPDPEndPoint = factoryConfig.getServerUrl() + "/d/qentelli-step-BBSteps";
        addToCart21DayFixEndPoint = "https://www-tbb" + world.getTestEnvironment().toLowerCase() + ".qentelli.com"
                + "/rest/model/atg/commerce/order/"
                + "purchase/CartModifierActor/addItemToOrder?productId=productId=21DChallengePackAA&purchaseSource=PAGE_PDP&"
                + "subSkuId=MDSUSHPKT31112HD,MDBODDVD64&catalogRefIds=MDBCP21DAA&quantity=1&pushSite="
                + factoryConfig.getPushSite() + "&_dynSessConf=";
        addToCartChocShakeolgyEndPoint = "https://www-tbb" + world.getTestEnvironment().toLowerCase() + ".qentelli.com"
                + "/rest/model/atg/commerce/order/"
                + "purchase/CartModifierActor/addItemToOrder/?productId=SHKChocolate&quantity=1&purchaseSource=PAGE_PDP&"
                + "catalogRefIds=MDSHK0102N2E101&pushSite=" + factoryConfig.getPushSite();
		addToCart80DayObessionEndPoint = "https://www-tbb" + world.getTestEnvironment().toLowerCase() + ".qentelli.com"
				+ "/rest/model/atg/commerce/order/"
				+ "purchase/CartModifierActor/addItemToOrder/?productId=80DChallengePackAA&quantity=1&purchaseSource=PAGE_PDP&"
				+ "subCatalogRefIds=MDSUBBP3199&subSkuId=MDSUBBP3199:MDSUBBP3192~MDSUBBP3193,MDBODDVD64&subSubSkuIds=MDSUBBP3192~MDSUBBP3193"
				+ "&catalogRefIds=MDBPP80DAA&pushSite=" + factoryConfig.getPushSite() + "&_dynSessConf=";
		addToCartBBStepEndPoint = "https://www-tbb" + world.getTestEnvironment().toLowerCase() + ".qentelli.com"
				+ "/rest/model/atg/commerce/order/"
				+ "purchase/CartModifierActor/addItemToOrder/?productId=BBSteps&quantity=1&purchaseSource=PAGE_PDP&"
				+ "catalogRefIds=MDACCSTEP2106&pushSite=" + factoryConfig.getPushSite() + "&_dynSessConf=";
		sessionPattern = "<input type=\"hidden\" value=\"(.+?)\" id=\"_dynSessConf\" />";
		authOAMHeaders = new HashMap<String, String>() {
			{
				put("Accept", "*/*");
				put("Content-Type", "application/x-www-form-urlencoded");
			}
		};
	}

	private void initTBBStrings() {
		restRequest = new RestRequest();
	}

	public String purchaseDefaultProductWithCurrentUser() {
		LOGGER.debug("Running purchase default product with current user.");
		String orderNumber = purchaseDefaultProduct();
		return orderNumber;
	}

	public String puchaseDefaultProductWithGivenUser(String email) {
		User newUser = new User();
		newUser.setEmail(email);
		world.factoryData.setUser(newUser);
		LOGGER.debug("Running purchase default product with specific user.");
		String orderNumber = purchaseDefaultProduct();
		return orderNumber;
	}

	public String purchaseGivenProductWithCurrentUser(String product) {
		LOGGER.debug("Purchase given product with current user");
		// goToHomePage();
		selectGivenProduct(product);
		loginAfterSelectingProduct();
		orderNumber = goThroughCheckoutDefaultInfo();
		return orderNumber;
	}

	public String purchaseDefaultProduct() {
		LOGGER.debug("Purchase default product method.");
		goToHomePage();
		selectDefaultProduct();
		loginAfterSelectingProduct();
		String orderNumber = goThroughCheckoutDefaultInfo();
		return orderNumber;
	}

	public void selectGivenProduct(String product) {
		if (product.toLowerCase().equals("shakeology")) {
			selectChocolateShakeolgy();
		} else if (product.toLowerCase().equals("challenge-pack")) {
			selectChallengePack();
		} else if (product.toLowerCase().equals("bbstep")) {
			selectBBStep();
		}

	}

	private void goToHomePage() {
		initTBBStrings();
		LOGGER.debug("******************* SHOP *******************");
		restRequest.doHTMLGetRequest(shopEndPoint);
		restRequest.doHTMLGetRequest(shopEndPoint);
//        LOGGER.debug("******************* REDIRECTAUTH *******************");
//        authRedirectURL =  restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT: " + authRedirectURL);
////        restRequest.doHTMLGetRequest(authRedirectURL);
//        LOGGER.debug("******************* REDIRECTAUTH 2 *******************");
//        authRedirectURL =  restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT 2: " + authRedirectURL);
////        restRequest.doHTMLGetRequest(authRedirectURL);
//        LOGGER.debug("******************* REDIRECTAUTH 3 *******************");
//        authRedirectURL =  restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT 3: " + authRedirectURL);
////        restRequest.doHTMLGetRequest(authRedirectURL);
//        LOGGER.debug("******************* SHOP 2 *******************");
//        restRequest.doHTMLGetRequest(shopEndPoint);
		session = restRequest.getStringResponseRegex(sessionPattern);
		Assert.assertNotNull(session);
	}

	private void selectDefaultProduct() {
		LOGGER.debug("******************* FITNESS *******************");
		restRequest.doHTMLGetRequest(fitnessCDPEndPoint);
		session = restRequest.getStringResponseRegex(sessionPattern);
		LOGGER.debug("THIS IS THE SESSION: " + session);
		LOGGER.debug("******************* PDP *******************");
		restRequest.doHTMLGetRequest(twentyOneDayFixBaseKitPDPEndPoint);
		LOGGER.debug("******************* ADD TO CART *******************");
		restRequest.doHTMLGetRequest(addToCart21DayFixEndPoint + session);

	}

	public void selectChocolateShakeolgy() {
		LOGGER.debug("******************* FITNESS *******************");
		restRequest.doHTMLGetRequest(shakeologyCDPEndPoint);
//        session = restRequest.getStringResponseRegex(sessionPattern);
		session = restRequest.getStringResponseRegex(sessionPattern);
		LOGGER.debug("THIS IS THE SESSION: " + session);
		LOGGER.debug("******************* PDP *******************");
		restRequest.doHTMLGetRequest(chocolateShakeologyPDPEndPoint);
		LOGGER.debug("******************* ADD TO CART *******************");
		// String sessionId = ((RemoteWebDriver)
		// world.driver).getSessionId().toString();
		// sessionPattern = "<input type=\"hidden\" value=\"(.+?)\" id=\"_dynSessConf\"
		// />";
		// session = restRequest.getStringResponseRegex(sessionPattern);
		restRequest.doHTMLPostRequest(addToCartChocShakeolgyEndPoint + "&_dynSessConf=" + session, shippingHeaders,
				"&_dynSessConf=" + session);
	}

	public void selectChallengePack() {
		LOGGER.debug("******************* FITNESS *******************");
		restRequest.doHTMLGetRequest(challengePackCPDEndPoint);
		session = restRequest.getStringResponseRegex(sessionPattern);
		LOGGER.debug("THIS IS THE SESSION: " + session);
		LOGGER.debug("******************* PDP *******************");
		restRequest.doHTMLGetRequest(eightyDayObsessionPDPEndPoint);
		LOGGER.debug("******************* ADD TO CART *******************");
		restRequest.doHTMLGetRequest(addToCart80DayObessionEndPoint + session);
	}

	public void selectBBStep() {
		LOGGER.debug("******************* FITNESS *******************");
		restRequest.doHTMLGetRequest(gearCPDEndPoint);
		session = restRequest.getStringResponseRegex(sessionPattern);
		LOGGER.debug("THIS IS THE SESSION: " + session);
		LOGGER.debug("******************* PDP *******************");
		restRequest.doHTMLGetRequest(bbStepPDPEndPoint);
		LOGGER.debug("******************* ADD TO CART *******************");
		restRequest.doHTMLGetRequest(addToCartBBStepEndPoint + session);
	}

    private void loginAfterSelectingProduct() {
        LOGGER.debug("******************* LOGIN *******************");
        String coachEmail = Genealogy.coachEmail;
//        restRequest.doHTMLGetRequest(loginEndPoint);
//        LOGGER.debug("******************* AUTH *******************");
//        try {
//            authOAMBody = "username=" + world.factoryData.getUser().getEmail()
//                    + "&password=" + factoryConfig.getPassword()
//                    + "&request_id=&successurl="
//                    + URLEncoder.encode(factoryConfig.getServerUrl(), "UTF-8") + "%2Fredirect.jsp"
//                    + "%3Fredirect_url%3D" + URLEncoder.encode(factoryConfig.getServerUrl(), "UTF-8")
//                    + "%2Fcheckout%253Flogin%253Dtrue&" + "failureurl="
//                    + URLEncoder.encode(factoryConfig.getServerUrl(), "UTF-8") + "%2Fcheckout%2Flogin";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        restRequest.doHTMLPostRequest(authOAMEndPoint, authOAMHeaders, authOAMBody);
//        LOGGER.debug("******************* REDIRECTAUTH 4*******************");
//        authRedirectURL = restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT: " + authRedirectURL);
//        restRequest.doHTMLGetRequest(authRedirectURL);
//        LOGGER.debug("******************* REDIRECTAUTH 5*******************");
//        authRedirectURL = restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT: " + authRedirectURL);
//        restRequest.doHTMLGetRequest(authRedirectURL);
//        LOGGER.debug("******************* REDIRECTAUTH 6*******************");
//        authRedirectURL = restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT: " + authRedirectURL);
//        restRequest.doHTMLGetRequest(authRedirectURL);
//        LOGGER.debug("******************* REDIRECTAUTH 7*******************");
//        authRedirectURL = restRequest.getHeaderLocation();
//        LOGGER.debug("AUTH URL REDIRECT: " + authRedirectURL);
//        restRequest.doHTMLGetRequest(authRedirectURL);
    }

	public String goThroughCheckoutDefaultInfo() {
		LOGGER.debug("******************* CHECKOUT *******************");
		restRequest.doHTMLGetRequest(checkoutEndPoint);
		LOGGER.debug("******************* SHIPPING *******************");
		restRequest.doHTMLPostRequest(shippingEndPoint, shippingHeaders,
				shippingBodyStartWithoutSession + session + shippingBodyMiddleWithoutCoachID
						+ world.factoryData.getUser().getGncID() + shippingBodyEndWithoutPushSite
						+ factoryConfig.getPushSite());
		LOGGER.debug("******************* PAYMENT *******************");
		restRequest.doHTMLPostRequest(paymentSubmitOrderEndPoint, paymentSubmitOrderHeaders,
				paymentSubmitOrderBodyStartWithoutSession + session + paymentSubmitOrderBodyEndWithoutPushSite
						+ factoryConfig.getPushSite());
		LOGGER.debug("******************* CONFIRMATION *******************");
		restRequest.doHTMLGetRequest(confirmationEndPoint);
		orderNumber = restRequest.getStringResponseRegex(confirmationPattern);
		LOGGER.debug("Order Number: " + orderNumber);
		LOGGER.debug("******************* LOGOUT *******************");
		// restRequest.doHTMLGetRequest(logoutEndPoint);
		return orderNumber;
	}
}
