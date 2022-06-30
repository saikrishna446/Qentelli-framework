package com.qentelli.automation.utilities.api;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.ApplicationsEndpointObject;

public class HTTPClient {
  static Logger logger = LogManager.getLogger(HTTPClient.class);
  public String body;

  private final CloseableHttpClient httpClient = HttpClients.createDefault();
  public HTTPClient(String http_request) {
    HttpGet request = new HttpGet(http_request);

    request.addHeader("x-api-key", ApplicationsEndpointObject.e2e.identityXapi);
    logger.info(http_request);
    try (CloseableHttpResponse response = httpClient.execute(request)) {

      // Get HttpResponse Status
      logger.info(response.getStatusLine().toString());

      HttpEntity entity = response.getEntity();
      Header headers = entity.getContentType();
      logger.info(headers);

      if (entity != null) {
        body = EntityUtils.toString(entity);
        logger.info(body);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
