package com.qentelli.automation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.influxdb.dto.Point;
import com.qentelli.automation.listeners.ResultSender;
import org.json.simple.JSONObject;

public class PackageE2ESendToInflux {
	static {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
		Configurator.initialize(null, "log4j2.xml");
	}
	static Logger logger = LogManager.getLogger(PackageE2ESendToInflux.class);
	public static String tmp = System.getProperty("java.io.tmpdir");

	static HashMap<String, Integer> results = new HashMap<String, Integer>();
	static Long duration = 0L;
	static Long end = System.currentTimeMillis();
	static long start;
	static String browser = System.getProperty("browser");
	static int total = 0;

	public static void main(String[] args) {
		if (System.getProperty("SID") == null && args[0] == null)
			throw new RuntimeException("no sid");

		String sid = String.valueOf(args[0]);

		results.put("PASSED", 0);
		results.put("FAILED", 0);
		results.put("SKIPPED", 0);
		results.put("UNDEFINED", 0);

		logger.info("sid ==" + args[0]);
		processEventFile(args[0]);
		logger.info("PASSED " + results.get("PASSED"));
		logger.info("FAILED " + results.get("FAILED"));
		logger.info(results.get("SKIPPED"));
		logger.info(results.get("UNDEFINED"));
		Point point = null;
		System.out.println("sending point to " + getPointName());
		logger.info("start " + start);
		logger.info("end " + end);
		duration = end - start;
		point = Point.measurement(getPointName()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("platform", System.getProperty("os.name"))
				.addField("user",
						System.getProperty("user.name") == "stick" ? System.getProperty("user.name")
								: "e2e[collection]")
				.addField("duration", duration).addField("lid", Long.valueOf(sid)).addField("start", start)
				.addField("end", end).addField("runid", sid).addField("suite", "E2E").addField("total", total)
				.addField("env", System.getProperty("environment")).addField("browser", System.getProperty("browser"))
				.addField("skipped", results.get("SKIPPED")).addField("failed", results.get("FAILED"))
				.addField("passed", results.get("PASSED")).addField("locale", "ALL").addField("application", "E2E")
				.addField("project", "Ops").addField("undefined", results.get("UNDEFINED"))
				.tag("env", System.getProperty("environment")).tag("browser", browser).tag("suite", "E2E")
				.tag("application", "E2E").tag("project", "Ops").tag("locale", "ALL").tag("suite", "E2E").build();

		if (total > 0) {
			ResultSender.send(point, ResultSender.TABLE.SET);

			//Construct PostgreSQL JSON
			JSONObject DataSentToPostgreSQL = new JSONObject();
			DataSentToPostgreSQL.put("time", System.currentTimeMillis());
			DataSentToPostgreSQL.put("platform", System.getProperty("os.name"));
			DataSentToPostgreSQL.put("user", System.getProperty("user.name") == "stick" ? System.getProperty("user.name")
					: "snelson[collection]");
			DataSentToPostgreSQL.put("suite", "E2E");
			DataSentToPostgreSQL.put("env", System.getProperty("environment"));
			DataSentToPostgreSQL.put("application", "E2E");
			DataSentToPostgreSQL.put("project", "Ops");
			DataSentToPostgreSQL.put("locale", "ALL");
			DataSentToPostgreSQL.put("bucket", System.getProperty("database"));

			DataSentToPostgreSQL.put("lid", args[0]);
			DataSentToPostgreSQL.put("start", start);
			DataSentToPostgreSQL.put("end", end);
			DataSentToPostgreSQL.put("duration", duration);
			DataSentToPostgreSQL.put("runId", args[0]);
			DataSentToPostgreSQL.put("total", total);
			DataSentToPostgreSQL.put("passed", results.get("PASSED"));
			DataSentToPostgreSQL.put("failed", results.get("FAILED"));
			DataSentToPostgreSQL.put("skipped", results.get("SKIPPED"));

			logger.info("After sending set data point to InfluxDB, JSON result = "
					+ DataSentToPostgreSQL);
			logger.info("Before sending results to Postgres");
			SendTestResultToPostgres.send("updateSet", DataSentToPostgreSQL.toString());
		}
	}

	public static void processEventFile(String s_id) {
		String line;
		File input = new File(tmp);

		for (String f : input.list()) {
			String[] split;

			System.out.println("file:: " + f);
			if (!f.contains(s_id))
				continue;
			try (BufferedReader reader = new BufferedReader(new FileReader(tmp + File.separator + f))) {
				System.out.println(" file @ |" + tmp + File.separator + f + "|");
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try (BufferedReader reader = new BufferedReader(new FileReader(tmp + File.separator + f))) {
				while ((line = reader.readLine()) != null) {
					System.out.println(line);

					if (line.contains("=")) {
						split = line.split("=");
						System.out.println("length @ |" + split.length + "|");
						if (split.length <= 1)
							continue;
						String res = split[1];
						if (line.contains("_DURATION")) {
							duration += Long.valueOf(res);
						}
						if (line.contains("START")) {
							start = Long.valueOf(res);
						}

						if (res.equals("PASSED") || res.equals("FAILED") || res.equals("SKIPED")
								|| res.equals("UNDEFINED")) {
							Integer value = results.get(res);
							value++;
							results.put(res, value);
							logger.info(res + "|" + value);
							total++;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static String getPointName() {
		return "Ops_E2E_E2E_" + System.getProperty("environment") + "_ALL_" + System.getProperty("database");
	}

}
