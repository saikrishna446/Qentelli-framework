package com.qentelli.automation.utilities;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import org.openqa.selenium.support.Color;
import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.base.AutomationIssueException;

public class CommonUtilities {

	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUM_LIST = "1234567890";

	private static final int RANDOM_STRING_LENGTH = 6;

	/**
	 * This method generates random string
	 * 
	 * @return
	 */
	public String generateRandomString() {

		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * randomstring with length
	 * 
	 * @param length
	 * @return
	 */
	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		String candidateChars = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}

	/**
	 * replace smart chars with simple chars
	 * 
	 * @param text
	 * @return
	 */
	public static String replaceSmartChars(String text) {
		return text.replaceAll("[\\u2013]", "-").replaceAll("[\\u2014]", "-").replaceAll("[\\u2015]", "-")
				.replaceAll("[\\u2017]", "_").replaceAll("[\\u2018]", "'").replaceAll("[\\u2019]", "'")
				.replaceAll("[\\u201a]", ",").replaceAll("[\\u201b]", "'").replaceAll("[\\u201c]", "\"")
				.replaceAll("[\\u201d]", "\"").replaceAll("[\\u201e]", "\"").replaceAll("[\\u2026]", "...")
				.replaceAll("[\\u2032]", "'").replaceAll("[\\u2033]", "\"").replaceAll("[\\u00A0]", " ")
				.replaceAll("®", "").replaceAll(" &", "").replaceAll("[\\u2122]","");
	}

	//this method is used to replace any smart char and avoids adding new char to exclusion list of chars
	public static String normalizeSpecialChars(String text) {
		String finalText = Normalizer.normalize(text, Normalizer.Form.NFD);
		return finalText = finalText.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	/**
	 * This method generates random numbers
	 * 
	 * @return int
	 */
	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(NUM_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;

		} else {
			return randomInt - 1;
		}
	}

	public static int getRandomNumber(int min, int max) {
		int x = (int) ((Math.random() * ((max - min) + 1)) + min);
		return x;
	}

	public static String getElapsedTime(long start, long end) {
		String elapsed = ((end - start) > 1000) ? (end - start) / 1000 + " secs." : (end - start) + " ms.";
		return elapsed;
	}

	/**
	 * This method generates random email
	 * 
	 * @return int
	 */
	public static String randomEmail() {
		long todayMillis1 = Instant.now().getEpochSecond();
        String s = randomString(10) + todayMillis1 + "@testtest.com";
        System.out.println("Random email: " + s);
        RuntimeProperties p = new RuntimeProperties();
        p.writeProp("EMAIL", s);
        return s;
	}

	// Generate random password
	public static String randomPassword() {
		return "T" + randomString(4) + "1234";
	}

	/**
	 * This method generates random phone number
	 * 
	 * @return String
	 */
	public static String randomPhone() {
		Random randomGenerator = new Random();
		// int randomCell= randomGenerator.nextInt(10000);
		int randomCell = ((1 + randomGenerator.nextInt(2)) * 10000 + randomGenerator.nextInt(10000));
		String cellnum = ("79393" + randomCell);
		return cellnum;
	}

	/**
	 * This method introduces some wait time
	 * 
	 * @return int
	 */
	public void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Description: To remove Trademark, Registered Symbols from the input string
	 *
	 * @param text
	 */
	public static String replaceSymbols(String text) {
		return text.replaceAll("[\\u00AE]", "").replaceAll("[\\u2122]", "");
	}

	public static void storeUserSignupDetails(World world, String firstName, String lastName, String email,
			String password, String month, String date, String year, String phone, String coachNickName) {
		world.getTestDataJson().put("TBB_First_Name", firstName);
		world.getTestDataJson().put("TBB_Last_Name", lastName);
		world.setCustomerDetails("ShippingAddress_FName", firstName);
		world.setCustomerDetails("ShippingAddress_LName", lastName);
		world.setEmail(email);
		world.setPassword(password);
		world.setCustomerDetails("Email", email);
		world.setExistingCustomerdetails("Email", email);
		world.setCustomerDetails("FirstName", firstName);
		world.setCustomerDetails("LastName", lastName);
		world.setExistingCoachDetails("Password", password);
		world.setCustomerDetails("DOB", month + "/" + date + "/" + year);
		world.setCustomerDetails("CoachPhone", phone);
		world.setCustomerDetails("Phone", phone);
		world.setCustomerDetails("CreatedFrom", "Web");

		if (coachNickName != null) {
			world.setExistingCoachDetails("Email_Coach" + coachNickName, email);
			world.setCustomerDetails("fnameCoach" + coachNickName, firstName);
			world.setCustomerDetails("lnameCoach" + coachNickName, lastName);
		} else {
			world.setExistingCoachDetails("Email", email);
			world.setCustomerDetails("fname", firstName);
			world.setCustomerDetails("lname", lastName);
		}
	}

	public static void storeCoachUpgradeDetails(World world) {
		world.getTestDataJson().put("TBB_First_Name", world.getCustomerDetails().get("fname"));
		world.getTestDataJson().put("TBB_Last_Name", world.getCustomerDetails().get("lname"));
	}

	public static void storeAccountCreationDetails(World world, String password, String month, String day, String year,
			String email) {
		world.getTestDataJson().put("TBB_AccountPage_Password", password);
		world.getTestDataJson().put("TBB_AccountPage_Month", month);
		world.getTestDataJson().put("TBB_AccountPage_Day", day);
		world.getTestDataJson().put("TBB_AccountPage_Year", year);
		world.setCustomerDetails("Email", world.getEmail());
		world.setCustomerDetails("DOB", (month + "/" + day + "/" + year));
	}

	// Setting customer login details to world object
	public static void storeCustomerLoginDetails(World world, String email, String password) {
		world.getTestDataJson().put("Existing2B_Username", email);
		world.getTestDataJson().put("Existing2B_Password", password);
		world.setEmail(email);
		world.setPassword(password);
	}

	// Generate a random address by auto-generating the numeric part of the given
	// address
	public static String getRandomizedAddress(String address) {
		int randomNumber = CommonUtilities.getRandomNumber(0, 9);
		String[] streetAddress = address.split(" ");
		String newAddress = streetAddress[0] + randomNumber + " " + streetAddress[1] + " " + streetAddress[2];
		return newAddress;
	}

	// This method removes tab and new line characters from text
	public static String removeTabAndNewLine(String text) {
		return text.replaceAll("\n", "").replaceAll("\t", "");
	}

	// Tokenize credit card number
	public static String tokenizeNumber(String cardNumber) {
		return cardNumber.substring(0, 6) + cardNumber.substring(6, cardNumber.length() - 5).replaceAll("[0-9]", "*")
				+ cardNumber.substring(cardNumber.length() - 4);
	}

	// Tokenize credit card number
	public static String tokenizeNumberCyberSource(String cardNumber) {
		return cardNumber.substring(0, 6) + cardNumber.substring(6, cardNumber.length() - 4).replaceAll("[0-9]", "X")
				+ cardNumber.substring(cardNumber.length() - 4);
	}

	// Gets Date formatter based on locale and timezone
	public static String getLocalizedDate(World world) {
		SimpleDateFormat sdf = new SimpleDateFormat(world.getLocaleResource().getString("dateFormat"),
				world.getFormattedLocale());
		sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		return sdf.format(new Date());
	}

	// Build addresses for validation
	public static String getLocalizedAddress(String addressType, World world) {
		String address1, city, zip, state, country;
		String address, billingAddress, shippingAddress;
		address1 = world.getCustomerDetails().get("Address1");
		city = world.getCustomerDetails().get("City");
		state = world.getCustomerDetails().get("State");
		zip = world.getCustomerDetails().get("Zip");
		country = world.getCustomerDetails().get("Country");

		if (world.getLocale().equalsIgnoreCase("en_GB"))
			address = address1 + "\n" + city + " " + zip + "\n" + state;
		else if (world.getLocale().equalsIgnoreCase("fr_FR"))
			address = address1 + "\n" + city + " " + zip + "\n" + country;
		else
			address = address1 + "\n" + city + ", " + state + " " + zip + "\n" + country;
		if (addressType.equalsIgnoreCase("BILLING")) {
			return address;
		}
		if ("PointRelais".equalsIgnoreCase(world.getCustomerDetails().get("ShippingMethod"))) {
			address = world.getCustomerDetails().get("Shipping_Address1") + "\n"
					+ world.getCustomerDetails().get("Shipping_City") + " "
					+ world.getCustomerDetails().get("Shipping_Zip") + "\n" + world.getCustomerDetails().get("Country");

		}
		return address;
	}

	// Build Ship to Customer Name for validation
	public static String getShipToFirstAndLastName(World world) {
		String name;
		if (world.getCustomerDetails().get("CreatedFrom") != null) {
			if ("API".equalsIgnoreCase(world.getCustomerDetails().get("CreatedFrom"))) {
				name = world.factoryData.getUser().getFirstName() + " " + world.factoryData.getUser().getLastName()
						+ "\n";
			} else {
				name = world.getCustomerDetails().get("fname") + " " + world.getCustomerDetails().get("lname") + "\n";
			}
		} else {
			name = world.getTestDataJson().get("TBB_ShakeologyPackOrder_ShippingFname") + " "
					+ world.getTestDataJson().get("TBB_ShakeologyPackOrder_ShippingLname") + "\n";
		}
		if ("PointRelais".equalsIgnoreCase(world.getCustomerDetails().get("ShippingMethod"))) {
			name = world.getCustomerDetails().get("ShippingAddress_FName") + " "
					+ world.getCustomerDetails().get("ShippingAddress_LName") + "\n";
		} else if ("LivraisonStandard".equalsIgnoreCase(world.getCustomerDetails().get("ShippingMethod"))) {
			name = world.getCustomerDetails().get("ShippingAddress_FName") + " "
					+ world.getCustomerDetails().get("ShippingAddress_LName") + "\n";
			world.getCustomerDetails().put("fname", world.getCustomerDetails().get("ShippingAddress_FName"));
			world.getCustomerDetails().put("lname", world.getCustomerDetails().get("ShippingAddress_LName"));
		}
		return name;
	}

	// Build Bill to Customer Name for validation
	public static String getBillToFirstAndLastName(World world) {
		return world.getCustomerDetails().get("FirstName") + " " + world.getCustomerDetails().get("LastName") + "\n";
	}

	// Get Payment Mode Used
	public static String getPaymentModeUsed(World world) {
		if ("CREDITCARD".equalsIgnoreCase(world.getCustomerDetails().get("PaymentMode"))) {
			return "CCPayment";
		} else if ("PAYPAL".equalsIgnoreCase(world.getCustomerDetails().get("PaymentMode"))) {
			return "PayPalPayment";
		}
		return null;
	}

	// Gets the applicable product price based on discounts/coach price
	public static String getApplicableProductPrice(World world) {
		String productPrice = null;
		if (world.getOrderDetails().get("coachDiscountPrice") != null) {
			productPrice = world.getOrderDetails().get("coachPrice");
		} else if (world.getOrderDetails().get("FinalPrice") != null) {
			productPrice = world.getOrderDetails().get("FinalPrice");
		} else {
			productPrice = world.getOrderDetails().get("ProductPrice");
		}
		return productPrice;
	}

	// This method removes non numeric characters from text
	public static String removeNonNumbers(String text) {
		return text.replaceAll("[^0-9]", "");
	}

	// This method extracts decimal numbers and removes the currency symbol
	public static double getCurrencyValue(String text, String currencySymbol) {
		return Double.parseDouble(text.replaceAll("[^0-9.,]", "").replaceAll(",", ".").replaceAll(currencySymbol, ""));
	}

	// This method gets the currency symbol
	public static String getCurrencySymbol(World world) {
		return Currency.getInstance(world.getFormattedLocale()).getSymbol(world.getFormattedLocale());
	}

	// This method removes specified characters from text
	public static String removeThese(String text, String chars) {
		return text.replaceAll("[" + chars + "]", "");
	}

	// This method removes non alpha-numeric characters from text
	public static String removeNonAlphaNumeric(String text) {
		return text.replaceAll(",", "").replaceAll("[^A-Za-z0-9]", "");
	}

	// This method extracts phone number from text
	public static String extractPhoneNumber(String text) {
		return text.replaceAll("\\(|\\)|-| +", "").replaceAll("[^0-9]", "");
	}

	// This method converts multi spaces to single space
	public static String multiSpacesToSingle(String text) {
		return text.replaceAll(" +", " ");
	}

	// This method converts commas to dots
	public static String convertCommaToDot(String text) {
		return text.replaceAll(",", ".");
	}

	// This method takes a string and returns a double
	public static double stringToDouble(String value) {
		return Double.parseDouble(value.replaceAll("[^0-9,.]", "").replaceAll(",", "."));
	}

	// This method removes all spaces from text
	public static String removeSpaces(String text) {
		return text.replaceAll("\\s", "");
	}

	// This method replaces all dots with commas from text
	public static String convertDotToComma(String text) {
		return text.replaceAll("\\.", ",");
	}

	// This method replaces new line with a space
	public static String replaceNewLineWithSpace(String text) {
		return text.replaceAll("\n", " ");
	}

	// This methods formats price from text
	public static String formatPriceText(String text) {
		return text.replaceAll("[^0-9.,]", "").replaceAll(",", ".").trim();
	}

	// This method rounds double value
	public static double mathRound(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	// Setting Order Billing Details to world object
	public static void setOrderBillingDetails(World world, String orderType, String coachPrice, String productPrice,
			String subTotal, String total, String tax, String shippingAndHandling) {
		world.setOrderDetails("OrderType", orderType);
		world.setOrderDetails("coachPrice", formatPriceText(coachPrice));
		world.setOrderDetails("ProductPrice", formatPriceText(productPrice));
		world.setOrderDetails("SubTotal", formatPriceText(subTotal));
		world.setOrderDetails("Total", formatPriceText(total));
		world.setOrderDetails("Tax", formatPriceText(tax));
		world.setOrderDetails("SnH", formatPriceText(shippingAndHandling));
	}

	// Setting Price Details from Order Summary to world object
	public static void setPriceDetailsOrderSummary(World world, String productPrice, String subTotal, String total,
			String tax, String shippingAndHandling) {
		world.setOrderDetails("ProductPrice", formatPriceText(productPrice));
		world.setOrderDetails("SubTotal", formatPriceText(subTotal));
		world.setOrderDetails("Total", formatPriceText(total));
		world.setOrderDetails("Tax", formatPriceText(tax));
		world.setOrderDetails("SnH", formatPriceText(shippingAndHandling));
	}

	// Checks text contains a dollar amount and returns a boolean
	public static boolean isTextDollarAmount(String text) {
		return text.matches("^[0-9,]+$");
	}

	// Returns formatted Country Code
	public static String getFormattedCountryCode(String locale) {
		if (locale.equalsIgnoreCase("en_GB"))
			return locale = locale.split("_")[1].toUpperCase().trim().replaceAll("GB", "UK");
		else
			return locale = locale.split("_")[1].toUpperCase().trim();
	}

	// Returns Country Specific Date in SimpleDateFormat
	public static SimpleDateFormat getCountryFormattedDate(String country, String language) {
		SimpleDateFormat sdf;
		if (country.toLowerCase().equals("united kingdom") || (language.toLowerCase().equals("french"))) {
			sdf = new SimpleDateFormat("dd/MM/yy");
		} else if (country.toLowerCase().equals("united states") || (country.toLowerCase().equals("canada"))) {
			sdf = new SimpleDateFormat("MM/dd/yy");
		} else {
			sdf = new SimpleDateFormat("MM/dd/yy");
		}
		return sdf;
	}

	// Get locale from the string of <country> (<language>)
	public static Locale getLocaleFromText(String text) {
		String[] tokens = text.split(" \\(");
		String country = tokens[0].trim();
		String language = tokens[1].replaceAll("\\)", "").trim();
		switch (country) {
		case "United States":
		case "U.S.A.":
			country = "US";
			break;
		case "Canada":
			country = "CA";
			break;
		case "United Kingdom":
			country = "GB";
			break;
		default:
			throw new AutomationIssueException(
					"Invalid Country '" + country + "' passed in CommonUtilities.getLocaleFromText()");
		}
		switch (language) {
		case "English":
			language = "en";
			break;
		case "Espanol":
		case "Español":
			language = "es";
			break;
		case "François":
		case "Français":
			language = "fr";
			break;
		default:
			throw new AutomationIssueException(
					"Invalid Language '" + language + "' passed in CommonUtilities.getLocaleFromText()");
		}
		return new Locale(language, country);
	}

	// Returns County 2 Letter Code
	public static String getCountryTwoLetterCode(String countryName) throws Exception {
		String countryCode = null;
		switch (countryName.toLowerCase()) {
		case "united states":
		case "u.s.a.":
			countryCode = "us";
			break;
		case "united kingdom":
			countryCode = "gb";
			break;
		case "canada":
			countryCode = "ca";
			break;
		default:
			throw new AutomationIssueException("Not a valid Country - " + countryName);
		}
		return countryCode;
	}

	// Returns langiage 2 Letter Code
	public static String getLanguageTwoLetterCode(String language) throws Exception {
		String languageCode = null;
		switch (language.toLowerCase()) {
		case "english":
			languageCode = "en";
			break;
		case "français":
			languageCode = "fr";
			break;
		case "español":
			languageCode = "es";
			break;
		default:
			throw new AutomationIssueException("Not a valid Language - " + language);
		}
		return languageCode;
	}

	// This method removes numeric characters from text
	public static String removeNumbers(String text) {
		return text.replaceAll("[0-9]", "");
	}

	public static String currentDateInEstZone(String dateFormat) {
		Calendar now = Calendar.getInstance();
		TimeZone timeZone = now.getTimeZone();
		String currentZone = timeZone.getDisplayName();
		DateTimeFormatter etFormat = DateTimeFormatter.ofPattern(dateFormat);
		String value = null;
		ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
		ZoneId pstZoneId = ZoneId.of("America/Los_Angeles");
		ZoneId etZoneId = ZoneId.of("America/New_York");
		LocalDateTime currentDateTime = LocalDateTime.now();
		if (currentZone.equals("India Standard Time")) {
			ZonedDateTime currentISTime = currentDateTime.atZone(istZoneId); // India Time
			ZonedDateTime currentETime = currentISTime.withZoneSameInstant(etZoneId); // ET Time
			value = etFormat.format(currentETime);
		} else {
			ZonedDateTime currentPSTime = currentDateTime.atZone(pstZoneId); // PS Time
			ZonedDateTime currentETime = currentPSTime.withZoneSameInstant(etZoneId); // ET Time
			value = etFormat.format(currentETime);
		}
		return value;
	}

	// To convert RGB color value to Hexa value
	public static String convertRGBToHexaValue(String colorValue) {
		return Color.fromString(colorValue).asHex();
	}

	// Gets Date in EST Time zone in required date format
	public static String getDateInEST(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		return sdf.format(new Date());
	}

	// Gets Date in PST Time zone in required date format
	public static String getDateInPST(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		return sdf.format(new Date());
	}
}