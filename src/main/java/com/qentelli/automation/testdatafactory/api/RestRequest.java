package com.qentelli.automation.testdatafactory.api;

import static io.restassured.RestAssured.given;

import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.params.CoreConnectionPNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class RestRequest {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private Response response;

	private CookieFilter cookieFilter = new CookieFilter();

	public Response doGetRequest(String endpoint) {
		response = given().filter(cookieFilter).log().all().contentType(ContentType.JSON).when().get(endpoint).then()
				.log().all().contentType(ContentType.JSON).extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doGetRequest(String endpoint, Map<String, String> headers) {
		response = given().filter(cookieFilter).log().all().headers(headers).when().get(endpoint).then().log().all()
				.contentType(ContentType.JSON).extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doPostRequest(String endpoint, String body) {
		response = given().filter(cookieFilter).log().all().contentType(ContentType.JSON).body(body).when()
				.post(endpoint).then().log().all().contentType(ContentType.JSON).extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doPostRequestWithAuth(String endpoint, String body, String authorizationString) {
		response = given().header("Content-Type", "application/json").header("Authorization", authorizationString)
				.header("Accept", "application/json").body(body).relaxedHTTPSValidation().when().post(endpoint).then()
				.extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doHTMLGetRequest(String endpoint) {
		response = given().urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(false)
				.relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint).then().log().all().extract()
				.response();
//        LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response tryGetRequest(String endpoint) {
		try {
			RestAssuredConfig config = RestAssured.config().httpClient(
					HttpClientConfig.httpClientConfig().setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
							.setParam(CoreConnectionPNames.SO_TIMEOUT, 60000));

			response = given().config(config).urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(false)
					.relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint).then().log().all().extract()
					.response();
			LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public Response doHTMLGetRequestNoEncoding(String endpoint) {
		response = given().urlEncodingEnabled(false).redirects().follow(false).relaxedHTTPSValidation("TLSv1.2")
				.filter(cookieFilter).log().all().when().get(endpoint).then().log().all().extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doHTMLGetRequestFollowRedirect(String endpoint) {
		response = given().urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(true).redirects().max(20)
				.redirects().allowCircular(true).relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint)
				.then().log().all().extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doHTMLPostRequest(String endpoint, Map<String, String> headers, String body) {
		response = given().urlEncodingEnabled(false).redirects().follow(true).redirects().max(20).redirects()
				.allowCircular(true).relaxedHTTPSValidation("TLSv1.2").filter(cookieFilter).log().all().body(body)
				.headers(headers).when().post(endpoint).then().log().all().extract().response();
		LOGGER.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public String getRestResponseValue(String key) {
		return response.jsonPath().getString(key);
	}

	public String getHTMLResponseValue(String key) {
		return response.htmlPath().getString(key);
	}

	public String getResponseAsString() {
		return response.asString();
	}

	public JSONObject getResponseAsJSONObject() {
		return new JSONObject(response.asString());
	}

	public String getHeaderLocation() {
		return response.getHeader("Location");
	}

	public String getStringResponseRegex(String regex) {
		LOGGER.debug("Regex: " + regex);
		Pattern pattern = Pattern.compile(regex);
		// LOGGER.debug("Response: " + response.asString());
		Matcher matcher = pattern.matcher(response.asString());
		String answer = null;
		while (matcher.find()) {
			answer = matcher.group(1);
		}
		LOGGER.debug("Return is " + answer);
		return answer;
	}

	public String getStringResonseXpath(String string) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			return xPath.evaluate(string, new InputSource(new StringReader(response.asString())));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return "NO RESPONSE Matching " + string;
	}

	public Cookies getDetailedCookies() {
		LOGGER.debug("Get Detailed Cookies " + response.getDetailedCookies());
		return response.getDetailedCookies();
	}

	public Cookie getDetailedCookie(String key) {
		LOGGER.debug("Get Detailed Cookie " + response.getDetailedCookie(key));
		return response.getDetailedCookie(key);
	}

	public Map<String, String> getCookies() {
		LOGGER.debug("Get Cookies " + response.getCookies());
		return response.getCookies();
	}

	public String getCookie(String key) {
		LOGGER.debug("Get Detailed Cookie " + response.getCookie(key));
		return response.getCookie(key);
	}

}
