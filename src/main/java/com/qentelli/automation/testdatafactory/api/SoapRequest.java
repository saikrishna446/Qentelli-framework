package com.qentelli.automation.testdatafactory.api;

import com.qentelli.automation.common.World;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SoapRequest {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Response response;
	private World world;
	private String desc = "";

	public SoapRequest(World world) {
		this.world = world;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}

	public Response doSoapRequest(String endpoint, String body) {

		RestAssured.defaultParser = Parser.XML;
		String xAPIKey = world.factoryConfig.getOimAPIKey();
		LOGGER.debug("Endpoint: " + endpoint + " <x-api-key> " + xAPIKey);
		LOGGER.debug(this.desc + " Request Body: \n" + body);
		response = given().headers("Content-Type", "text/xml; charset=utf-8", "x-api-key", xAPIKey).body(body).when()
				.post(endpoint).then().extract().response();
//        LOGGER.debug(this.desc + " Response Body:\n" );
		response.xmlPath().prettyPrint();
		return response;
	}

	public Response doSoapRequestWithParser(String endpoint, String body) {

		LOGGER.debug("Endpoint: " + endpoint);
		LOGGER.debug(this.desc + " Request Body: \n" + body);
		response = given().header("Content-Type", "text/xml; charset=utf-8").body(body).when().post(endpoint).then()
				.extract().response();

		LOGGER.debug(this.desc + " Response Body:\n");
		response.xmlPath().prettyPrint();
		return response;
	}

	public Response doSoapRequestWithoutBody(String endpoint) {

		LOGGER.debug("Endpoint: " + endpoint);
		RestAssured.defaultParser = Parser.XML;

		response = given().post(endpoint).then().extract().response();

		LOGGER.debug(this.desc + " Response:\n");
		response.xmlPath().prettyPrint();
		return response;
	}

	public String getSoapResponseValue(String key) {
		LOGGER.info(response.asPrettyString());
		return response.xmlPath().getString(key);
	}

	public List<String> getSoapResponseList(String key) {
		return response.body().xmlPath().getList(key);
	}

	public Map<String, String> getSoapResponseMap(String key) {
		return response.body().xmlPath().getMap(key);
	}
}
