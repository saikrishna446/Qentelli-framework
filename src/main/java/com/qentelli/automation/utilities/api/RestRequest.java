package com.qentelli.automation.utilities.api;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import com.qentelli.automation.common.World;
import com.qentelli.automation.exceptions.custom.UnknownHostException;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class RestRequest {

	private Logger logger;
	private StringBuilder strResponse = new StringBuilder();
	private Response response;
	private CookieFilter cookieFilter = new CookieFilter();

	public RestRequest(World world) {
		// super(world, world.driver);
		InitElements();
	}

	public void InitElements() {
		logger = LogManager.getLogger(RestRequest.class);
	}

	public Response doGetRequest(String endpoint) {
		response = given().filter(cookieFilter).log().all().contentType(ContentType.JSON).when().get(endpoint).then()
				.log().all().contentType(ContentType.JSON).extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doGetRequest(String endpoint, Map<String, String> headers) {
		response = given().filter(cookieFilter).log().all().headers(headers).when().get(endpoint).then().log().all()
				.contentType(ContentType.JSON).extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doPostRequest(String endpoint, Map<String, String> headers, String body) {
		try {
			response = given().filter(cookieFilter).body(body).log().all().headers(headers).when().post(endpoint).then()
					.log().all().contentType(ContentType.JSON).extract().response();
		} catch (Exception e) {
			logger.info(e.toString());
			if (e.toString().contains("UnknownHostException"))
				throw new UnknownHostException(endpoint);
			e.printStackTrace();
			throw new RuntimeException(endpoint);
		}

		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doPostRequest(String endpoint, String body) {
		response = given().filter(cookieFilter).log().all().contentType(ContentType.JSON).body(body).when()
				.post(endpoint).then().log().all().contentType(ContentType.JSON).extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doPostRequestWithAuth(String endpoint, String body, String authorizationString) {
		logger.info("your endpoint <> " + endpoint);
		logger.info("your auth <> " + authorizationString);

		logger.info("your body <> ");
		logger.info(body);
		response = given().header("Content-Type", "application/json").header("Authorization", authorizationString)
				.header("Accept", "application/json").body(body).relaxedHTTPSValidation().when().post(endpoint).then()
				.extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doHTMLGetRequest(String endpoint) {
		response = given().urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(false)
				.relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint).then().log().all().extract()
				.response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

//	public Response tryGetRequest1(String endpoint) {
//
//		logger.info("Into the RestService call to get response1>>>>>>>>>>>>>>>>>>>>>>>> " + endpoint);
//		RestAssuredConfig config = RestAssured.config()
//				.httpClient(HttpClientConfig.httpClientConfig().setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
//						.setParam(CoreConnectionPNames.SO_TIMEOUT, 60000));
//
//		response = given().config(config).urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(false)
//				.relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint).then().log().all().extract()
//				.response();
//		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
//		return response;
//	}

	public void tryGetRequest(String endpoint) {
		/*
		 * fix for Exception in thread "main" javax.net.ssl.SSLHandshakeException:
		 * sun.security.validator.ValidatorException: PKIX path building failed:
		 * sun.security.provider.certpath.SunCertPathBuilderException: unable to find
		 * valid certification path to requested target
		 */
		TrustManager[] trustAllCerts = new TrustManager[] { new X509ExtendedTrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string, Socket socket)
					throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string, Socket socket)
					throws CertificateException {

			}

			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string, SSLEngine ssle)
					throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string, SSLEngine ssle)
					throws CertificateException {

			}

		} };

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {

			@Override
			public boolean verify(String hostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/*
		 * end of the fix
		 */
		URL url = null;
		HttpURLConnection con = null;
		try {
			url = new URL(endpoint);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				for (String line; (line = reader.readLine()) != null;) {
					logger.info(line);
					strResponse.append(line);
				}
			}
			con.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			endpoint = endpoint.replace("http://", "");
			String[] server = endpoint.split(":");
			String command = "nc -zvw10 " + server[0] + " " + server[1];
			System.out.println(command);
			logger.info("-------------------------------");
			try {
				Process proc = Runtime.getRuntime().exec(command);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String s = null;
				while ((s = stdInput.readLine()) != null) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>" + s);
					if (s.contains("cat: Connected to"))
						strResponse.append(s);
				}
				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				// Read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>" + s);
					if (s.contains("cat: Connected to"))
						strResponse.append(s);
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new RuntimeException("connection reset hit ");
			}

			logger.info("-------------------------------");
		}

//		try {
//			logger.info("Into the RestService call to get respons>>>>>>>>>>>>>>>>>>>>>>>> " + endpoint);
//			RestAssuredConfig config = RestAssured.config().httpClient(
//					HttpClientConfig.httpClientConfig().setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
//							.setParam(CoreConnectionPNames.SO_TIMEOUT, 60000));
//
////			response = given().config(config).urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(false)
////					.relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint).then().log().all().extract()
////					.response();
//			response = given().log().all().when().get(endpoint).then().log().all().extract()
//					.response();
//
//			logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail("Exception: " + endpoint + " - endpoint encounter exception when trying to get response i.e., "
//					+ e.getLocalizedMessage());
//		}
//		
		// return response;
	}

	public static String execCmd(String cmd) {
		String result = null;
		try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
				Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
			result = s.hasNext() ? s.next() : null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Response doHTMLGetRequestNoEncoding(String endpoint) {
		response = given().urlEncodingEnabled(false).redirects().follow(false).relaxedHTTPSValidation("TLSv1.2")
				.filter(cookieFilter).log().all().when().get(endpoint).then().log().all().extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doHTMLGetRequestFollowRedirect(String endpoint) {
		response = given().urlEncodingEnabled(false).filter(cookieFilter).redirects().follow(true).redirects().max(20)
				.redirects().allowCircular(true).relaxedHTTPSValidation("TLSv1.2").log().all().when().get(endpoint)
				.then().log().all().extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
		return response;
	}

	public Response doHTMLPostRequest(String endpoint, Map<String, String> headers, String body) {
//        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().setParam(COOKIE_POLICY, BROWSER_COMPATIBILITY));
		response = given().urlEncodingEnabled(false).redirects().follow(true).redirects().max(20).redirects()
				.allowCircular(true).relaxedHTTPSValidation("TLSv1.2").filter(cookieFilter).log().all().body(body)
				.headers(headers).when().post(endpoint).then().log().all().extract().response();
		logger.debug("Request to " + endpoint + " got status code of " + response.statusCode());
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

	public String getResponseAsStringNew() {
		return strResponse.toString();
	}

	public JSONObject getResponseAsJSONObject() {
		return new JSONObject(response.asString());
	}

	public String getHeaderLocation() {
		return response.getHeader("Location");
	}

	public String getStringResponseRegex(String regex) {
		logger.debug("Regex: " + regex);
		Pattern pattern = Pattern.compile(regex);
		// logger.debug("Response: " + response.asString());
		Matcher matcher = pattern.matcher(response.asString());
		String answer = null;
		while (matcher.find()) {
			answer = matcher.group(1);
		}
		logger.debug("Return is " + answer);
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
		logger.debug("Get Detailed Cookies " + response.getDetailedCookies());
		return response.getDetailedCookies();
	}

	public Cookie getDetailedCookie(String key) {
		logger.debug("Get Detailed Cookie " + response.getDetailedCookie(key));
		return response.getDetailedCookie(key);
	}

	public Map<String, String> getCookies() {
		logger.debug("Get Cookies " + response.getCookies());
		return response.getCookies();
	}

	public String getCookie(String key) {
		logger.debug("Get Detailed Cookie " + response.getCookie(key));
		return response.getCookie(key);
	}

}
