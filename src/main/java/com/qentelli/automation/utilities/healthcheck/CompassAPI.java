package com.qentelli.automation.utilities.healthcheck;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.qentelli.automation.testdatafactory.api.RestRequest;
import com.qentelli.automation.utilities.AbstractResourceBundle;
import com.qentelli.automation.utilities.HCE2EndpointObject;
import com.qentelli.automation.utilities.RuntimeProperties;

public class CompassAPI {
	protected static Logger logger = LogManager.getLogger(CompassAPI.class);
	protected String application;
	HCTestcaseResourceBundle testdata;
	RestRequest rest;
	String env = AbstractResourceBundle.getEnv();
	String compassapi;
	int step = 1;
	RuntimeProperties r;

	public CompassAPI(String a) {
		r = new RuntimeProperties();
		application = a;
		testdata = new HCTestcaseResourceBundle(a);
		rest = new RestRequest();
		compassapi = new HCE2EndpointObject().compassapi;
		sendCompassRequest();
	}

	public String getHealthCheckPayload() {
		Path contentBytes = Paths.get("src/main/resources/compass_request_template.json");
		String content = null;
		try {
			content = new String(Files.readAllBytes(contentBytes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//	    "application": "APP",
//	    "environment": "ENV",
//	    "request_type": "TYPE",
//	    "request_info": "INFO"
		String json = content.replace("ENV", env);
		json = json.replace("APP", testdata.application);
		json = json.replace("TYPE", testdata.type);
		json = json.replace("INFO", testdata.info);
		;

		if (json.contains("managed_server_health"))
			json = content.replace(env, "EBSPERF");
		logger.info("health check json payload: \n" + json);
		return json;
	}

	public String sendCompassRequest() {

		logger.debug("======================compass api=================================");
		logger.debug(compassapi);
		logger.debug("======================compass api=================================");
		String content = getHealthCheckPayload();
		logger.info(content);
		rest.doPostRequest(compassapi, content);
		String response = rest.getResponseAsString();
		logger.debug("This is the response: " + response);
		r.writeProp("json", response);
		return response;
	}

	public void verifyATGServerHealth() {

		int passed = 0;
		int total = 0;
		for (String s : r.readProp("json").split("\\n")) {
			String result;

			if (s.contains("instance_name")) {
				r.writeProp("step" + step, s.split(":")[1].replaceAll("\"", ""));
			}
			if (s.contains("status")) {
				result = s.contains("RUNNING") ? "PASSED" : "FAILED";
				String pipe = r.readProp("step" + step);
				total++;
				if (result.equals("PASSED"))
					passed++;
				r.writeProp("step" + step++, pipe + "|" + result);
			}

		}
		if (testdata.threshold != null) {
			float threshold = 0;
			float f = 0;
			if (passed == 0) {
				if (System.getProperty("database") != null && System.getProperty("database").equals("Production"))
					sendEmail("snelson@qentelli.com;dcaruana@qentelli.com;dbuckholz@qentelli.com",
							"[" + AbstractResourceBundle.getEnv() + "] Not enough atg servers",
							"At least " + threshold * 100 + "% of the atg cluster is required for "
									+ AbstractResourceBundle.getEnv() + " (" + passed + " of " + total + ")");
			} else {
				f = passed / total;
				threshold = Float.valueOf(testdata.threshold);
				logger.info(passed + " |||| " + total + "|||| " + passed / total + "<thrreshold" + threshold);
				if (f < threshold && System.getProperty("database") != null
						&& System.getProperty("database").equals("Production"))
					sendEmail("snelson@qentelli.com;dcaruana@qentelli.com;dbuckholz@qentelli.com",
							"[" + AbstractResourceBundle.getEnv() + "] Not enough atg servers",
							"At least " + threshold * 100 + "% of the atg cluster is required for "
									+ AbstractResourceBundle.getEnv() + " (" + passed + " of " + total + " passed ["
									+ passed / total * 100 + "%])");
			}
			Assert.assertTrue(f >= threshold,
					" Proves a threshold of atg servers are reached " + f + " >= " + threshold);
		}

	}

	public void verifyCOOServerHealth() {

		for (String s : r.readProp("json").split("\\n")) {
			String result;
			if (s.contains("instance_name")) {
				r.writeProp("step" + step, s.split(":")[1].replaceAll("\"", ""));
			}
			if (s.contains("status")) {
				result = s.contains("SUCCESS") ? "PASSED" : "FAILED";
				String pipe = r.readProp("step" + step);

				r.writeProp("step" + step++, pipe + "|" + result);
			}

		}

	}

	public void verifyEBSJobs() {
		for (String s : r.readProp("json").split("\\n")) {
			if (s.contains("description")) {
				r.writeProp("step" + step, s.split(":")[1].replaceAll("\"", ""));
				String pipe = r.readProp("step" + step);
				r.writeProp("step" + step++, pipe + "|FAILED");
				;
			}

		}

	}

	public void sendEmail(String toEmail, String sub, String msg) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String datePrintInEmail = formatter.format(date).toString();
		System.out.println("Subject to print date: " + datePrintInEmail);
		// Get properties object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "mail.qentelli.com");
		Session session = Session.getDefaultInstance(properties);

		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply-qaautomation@qentelli.com"));
			// String to = System.getProperty("sendTo.email");
			String to = toEmail;
			for (String to_1 : to.split(";")) {
				logger.debug(to_1);
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_1));
			}
			message.setSubject(sub + " - " + datePrintInEmail);
			message.setContent(msg, "text/html; charset=utf-8");
			// send message
			Transport.send(message);
			logger.debug("message sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
