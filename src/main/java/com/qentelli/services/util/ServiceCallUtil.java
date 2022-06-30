package com.qentelli.services.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

public class ServiceCallUtil {

    public static Response getResponse(String url, HashMap headermap, String requestbody) {
        Response response = RestAssured.given().headers(headermap).body(requestbody).post(url);
        return response;
    }

}
