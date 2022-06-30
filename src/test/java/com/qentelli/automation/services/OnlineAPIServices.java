package com.qentelli.automation.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.json.simple.JSONObject;
import com.qentelli.automation.libraries.ConfigFileReader;
import com.qentelli.automation.utilities.api.HTTPClient;
import com.qentelli.services.util.OnlineAPI;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class OnlineAPIServices {

	private OnlineAPI onlineAPI;

	private static SOAPMessage request;
	private static SOAPPart soapPart;
	private static SOAPEnvelope envelope;
	private static SOAPHeader requestHeader;
	private static SOAPBody msgBody;

    public HTTPClient http = null;

	public OnlineAPIServices() {
		onlineAPI = new OnlineAPI();
	}

    public OnlineAPIServices(String s) {
      http = new HTTPClient(s);
    }

	/**
	 * @return Response
	 * @throws Exception Hard coded stuff(URI) has to move to config.properties
	 */
	private Response GetRepInfo() throws Exception {
		RestAssured.baseURI = "https://api.securefreedom.com";
		return RestAssured.given().headers(onlineAPI.getHeadersForGetRepInfo()).contentType("text/xml;charset=UTF-8")
				.and().body(onlineAPI.getBodyForGetRepInfo()).when()
				.post("/MillionDollarBodySandbox5/webservice/onlineapi.asmx").andReturn();
	}

	public boolean validateRepInfo() throws Exception {
		String response = GetRepInfo().asString();
		if (!response.contains("<Firstname>Andrea</Firstname>")) {
			throw new Exception("GetRepInfo - Failed while validating first Name");
		} else {
			return true;
		}
	}

	/**
	 * @Description: Getting Request body for OMI
	 * @param filterName
	 * @param filterValue
	 * @return
	 * @throws Exception
	 */
	// usage of response : String statusValue =
	// response.xmlPath().getString("Envelope.Body.searchOIMIdentityResponse.search_identity_response.status");
	public static String getBodyForOmi(String filterName, String filterValue) throws Exception {
		request = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
		soapPart = request.getSOAPPart();
		envelope = soapPart.getEnvelope();
		requestHeader = envelope.getHeader();
		msgBody = envelope.getBody();
		// Modifying default prefixes for name spaces
		envelope.removeNamespaceDeclaration(envelope.getPrefix());
		envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
		envelope.setAttribute(String.format("%s:%s", "xmlns", "prox"), "http://proxy.test.com/");
		envelope.setPrefix("soapenv");
		msgBody.setPrefix("soapenv");
		requestHeader.setPrefix("soapenv");
		SOAPElement searchOIMIdentityElement = msgBody.addChildElement("searchOIMIdentity", "prox");
		SOAPElement searchIdentityRequestElement = searchOIMIdentityElement.addChildElement("search_identity_request");
		searchIdentityRequestElement.addChildElement("searchFilterName").setValue(filterName);
		searchIdentityRequestElement.addChildElement("searchFilterValue").setValue(filterValue);
		return getSoapMessageAsXml(request);
	}

	/**
	 * @Description: Getting TBB order request body
	 * @param storeNumber
	 * @return
	 * @throws Exception
	 */
	public static String getTBBOrderReqBody(String storeNumber) throws Exception {
		request = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
		soapPart = request.getSOAPPart();
		envelope = soapPart.getEnvelope();
		requestHeader = envelope.getHeader();
		msgBody = envelope.getBody();
		// Modifying default prefixes for name spaces
		envelope.removeNamespaceDeclaration(envelope.getPrefix());
		envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
		envelope.setAttribute(String.format("%s:%s", "xmlns", "sec"), "http://www.securefreedom.com/");
		envelope.setPrefix("soap");
		requestHeader.setPrefix("soap");
		SOAPElement CSGetOrderInfoElement = msgBody.addChildElement("CSGetOrderInfo", "sec");
		CSGetOrderInfoElement.addChildElement("TBBOrderID", "sec").setValue(storeNumber);
		System.out.println("snelson -- TBB Order \n\n " + getSoapMessageAsXml(request));
		return getSoapMessageAsXml(request);
	}

	/**
	 * @Description: Getting REP info request body
	 * @param userName
	 * @param passWord
	 * @param repNumberOrURL
	 * @return
	 * @throws Exception
	 */
	public static String getRepInfoReqBody(String userName, String passWord, String repNumberOrURL) throws Exception {
		request = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
		soapPart = request.getSOAPPart();
		envelope = soapPart.getEnvelope();
		requestHeader = envelope.getHeader();
		msgBody = envelope.getBody();
		// Modifying default prefixes for name spaces
		envelope.removeNamespaceDeclaration(envelope.getPrefix());
		envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
		envelope.setAttribute(String.format("%s:%s", "xmlns", "sec"), "http://www.securefreedom.com/");
		envelope.setPrefix("soap");
		requestHeader.setPrefix("soap");
		SOAPElement CSGetOrderInfoElement = msgBody.addChildElement("GetRepInfo", "sec");
		CSGetOrderInfoElement.addChildElement("RepNumberOrURL", "sec").setValue(repNumberOrURL);// use : "319001"
		SOAPElement credentialsElement = CSGetOrderInfoElement.addChildElement("Credentials", "sec");
		credentialsElement.addChildElement("Username", "sec").setValue(userName);
		credentialsElement.addChildElement("Password", "sec").setValue(passWord);
		System.out.println(getSoapMessageAsXml(request));
		return getSoapMessageAsXml(request);
	}

	/**
	 * @Description: Getting Customer info request body
	 * @param userName
	 * @param passWord
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public static String getCustomerInfoReqBody(String userName, String passWord, String customerId) throws Exception {
		request = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
		soapPart = request.getSOAPPart();
		envelope = soapPart.getEnvelope();
		requestHeader = envelope.getHeader();
		msgBody = envelope.getBody();
		// Modifying default prefixes for name spaces
		envelope.removeNamespaceDeclaration(envelope.getPrefix());
		envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
		envelope.setAttribute(String.format("%s:%s", "xmlns", "sec"), "http://www.securefreedom.com/");
		envelope.setPrefix("soap");
		requestHeader.setPrefix("soap");
		SOAPElement CSGetOrderInfoElement = msgBody.addChildElement("GetCustomerInfo", "sec");
		CSGetOrderInfoElement.addChildElement("CustomerID", "sec").setValue(customerId);// use : "319001"
		SOAPElement credentialsElement = CSGetOrderInfoElement.addChildElement("Credentials", "sec");
		credentialsElement.addChildElement("Username", "sec").setValue(userName);
		credentialsElement.addChildElement("Password", "sec").setValue(passWord);
		System.out.println(getSoapMessageAsXml(request));
		return getSoapMessageAsXml(request);
	}

	/**
	 * @Description: Getting response
	 * @param url
	 * @param headermap
	 * @param requestbody
	 * @return
	 */
	public static Response getResponse(String url, HashMap headermap, String requestbody) {
		Response response = RestAssured.given().headers(headermap).body(requestbody).post(url);
		return response;
	}

	public static Response getResponse(String url, HashMap headermap) {
		Response response = RestAssured.given().headers(headermap).get(url);
		return response;
	}

	/**
	 * @Description: Getting soap message as XML
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws SOAPException
	 */
	public static String getSoapMessageAsXml(SOAPMessage request) throws IOException, SOAPException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		request.writeTo(b);
		return b.toString();
	}

	/**
	 * @Description: Getting response using authorization
	 * @param userName
	 * @param passWord
	 * @param url
	 * @param headermap
	 * @param requestbody
	 * @return
	 */
	public static Response getResponseByAuth(String userName, String passWord, String url, HashMap headermap,
			String requestbody) {
		Response response = RestAssured.given().auth().preemptive().basic(userName, passWord).headers(headermap)
				.body(requestbody).post(url);
		return response;
	}

	/**
	 * @Description: To get User Details from TDM Repository using Json Request body
	 * @param customerType
	 * @param locale
	 */

	public static List<HashMap> getUserDetailsFromTDM(String customerType, String locale, String env) throws Exception {
		List<HashMap> usersDetails = null;
		try {
			JSONObject requestBody = new JSONObject();
			requestBody.put("CustomerType", customerType);
			requestBody.put("Locale", locale);
			requestBody.put("Environment", env);
			requestBody.put("Limit", "1"); // limit we are setting to 1 record
			Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody.toJSONString())
					.post(ConfigFileReader.getConfigFileReader().getTDMEndPoints("UserDetails_EndPoint"));
			if (response.then().extract().asString().contains("No records found")) {
				throw new Exception("No records found in repository. Please create users using TDM API");
			} else {
				usersDetails = response.jsonPath().getList("users");
			}
		} catch (Exception e) {
			throw new Exception("Failed to get User Details from TDM", e);
		}
		return usersDetails;
	}

	/**
	 * @Description: To get Complete User Details from TDM Repository using Json
	 *               Request body
	 * @param customerType
	 * @param locale
	 */

	public static List<HashMap> getCompleteUserDetailsFromTDM(String customerType, String locale, String env)
			throws Exception {
		List<HashMap> usersDetails = null;
		try {
			JSONObject requestBody = new JSONObject();
			requestBody.put("CustomerType", customerType);
			requestBody.put("Locale", locale);
			requestBody.put("Environment", env);
			requestBody.put("Limit", "1"); // limit we are setting to 1 record
			Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody.toJSONString())
					.post(ConfigFileReader.getConfigFileReader().getTDMEndPoints("CompleteUserDetails_EndPoint"));
			if (response.then().extract().asString().contains("No records found")) {
				throw new Exception("No records found in repository. Please create users using TDM API");
			} else {
				usersDetails = response.jsonPath().getList("users");
			}
		} catch (Exception e) {
			throw new Exception("Failed to get User Details from TDM", e);
		}
		return usersDetails;
	}

	/**
	 * @Description: To get Static User Details from TDM Repository using Json
	 *               Request body
	 * @param customerType
	 * @param locale
	 */

	public static HashMap<String, String> getStaticUserDetailsFromTDM(String customerType, String locale, String env)
			throws Exception {
		String respBody = null;
		try {
			JSONObject requestBody = new JSONObject();
			requestBody.put("UserType", customerType);
			requestBody.put("Locale", locale);
			requestBody.put("Environment", env);
			requestBody.put("Limit", 1); // limit we are setting to 1 record
			respBody = RestAssured.given().contentType(ContentType.JSON).body(requestBody.toJSONString())
					.post(ConfigFileReader.getConfigFileReader().getTDMEndPoints("StaticUsers_EndPoint")).then()
					.extract().asString();
			if (respBody.contains("No records found")) {
				throw new Exception("No records found in repository with the given criteria");
			}
		} catch (Exception e) {
			throw new Exception("Failed to get User Details from TDM", e);
		}
		return convertToKeyValuePairs(respBody);
	}

	/**
	 * @Description: To store the json response to a Map
	 * @param jsonString
	 */
	public static HashMap<String, String> convertToKeyValuePairs(String jsonString) throws Exception {
		HashMap<String, String> map = new HashMap<>();
		try {
			String[] keyValuePairs = jsonString.replaceAll("[\\[\\{\\}\\]\"]", "").split(",");
			for (String keyValue : keyValuePairs) {
				String[] entry = keyValue.split(":");
				map.put(entry[0].trim(), entry[1].trim());
			}
		} catch (Exception e) {
			throw new Exception("Failed to convert Json response to Key ValuePairs", e);
		}
		return map;
	}

	/**
	 * @Description: To get User Details from TDM Repository using Json Request body
	 * @param env
	 * @param locale
	 */

	public static List<HashMap> getCoachWithoutSponsorFromTDM(String locale, String env) throws Exception {
		List<HashMap> usersDetails = null;
		try {
			JSONObject requestBody = new JSONObject();
			requestBody.put("Locale", locale);
			requestBody.put("Environment", env);
			Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody.toJSONString())
					.post(ConfigFileReader.getConfigFileReader().getTDMEndPoints("CoachWithoutSponsor_EndPoint"));
			if (response.then().extract().asString().contains("No records found")) {
				throw new Exception("No records found in repository. Please create users using TDM API");
			} else {
				usersDetails = response.jsonPath().getList("users");
			}
		} catch (Exception e) {
			throw new Exception("Failed to get User Details from TDM", e);
		}
		return usersDetails;
	}

}
