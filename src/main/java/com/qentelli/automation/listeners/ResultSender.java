package com.qentelli.automation.listeners;

import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ResponseFormat;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.Point;
import org.influxdb.impl.InfluxDBImpl;
import okhttp3.OkHttpClient;

public class ResultSender {
	public enum TABLE {
		SET, SCENARIOS, STEPS
	}

	static OkHttpClient.Builder builder = new OkHttpClient.Builder();
	static OkHttpClient.Builder offshore_builder = new OkHttpClient.Builder();
	private static final String DATABASE = "test_results";
	private static final String STEPS = "steps";
	private static final String SCENARIOS = "scenarios";
	private static final String SETV1 = "set";

	private static final String CONNECTION = "http://localhost:8086";
	private static final String USR = "root";
	private static final String PWD = "root12345";
	private static final InfluxDB INFLXUDB = connect(CONNECTION, USR, PWD, builder, ResponseFormat.JSON);
	private static final InfluxDB INFLXUDBSet = connect(CONNECTION, USR, PWD, builder, ResponseFormat.JSON);
	private static final InfluxDB INFLXUDBScenarios = connect(CONNECTION, USR, PWD, builder, ResponseFormat.JSON);
	private static final InfluxDB INFLXUDBSteps = connect(CONNECTION, USR, PWD, builder, ResponseFormat.JSON);

	static {
		INFLXUDB.setDatabase(DATABASE);
		INFLXUDB.setRetentionPolicy("liquid");
		INFLXUDBSet.setDatabase(SETV1);
		INFLXUDBSet.setRetentionPolicy("liquid");
		INFLXUDBScenarios.setDatabase(SCENARIOS);
		INFLXUDBScenarios.setRetentionPolicy("liquid");
		INFLXUDBSteps.setDatabase(STEPS);
		INFLXUDBSteps.setRetentionPolicy("liquid");

	}

	public synchronized static void send(final Point point) {
		try {

			INFLXUDB.write(point);

		} catch (InfluxDBIOException idbio) {
			System.out.println(idbio.getMessage());
			idbio.printStackTrace();
			System.out.println(idbio.getStackTrace().toString());
		}
	}

	public synchronized static InfluxDB connect(final String url, final String username, final String password,
			final OkHttpClient.Builder client, final ResponseFormat responseFormat) {
		client.connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES); // read
		client.build();
		return new InfluxDBImpl(url, username, password, client, responseFormat);
	}

	public synchronized static void send(final Point point, TABLE t) {
		try {

          // Per Ge; not sure if this framework is still using test_results
          // send(point);

//			if (t.equals(TABLE.SET)) {
//				INFLXUDBSet.write(point);
//			}
//			if (t.equals(TABLE.SCENARIOS)) {
//				INFLXUDBScenarios.write(point);
//			}
//			if (t.equals(TABLE.STEPS)) {
//				INFLXUDBSteps.write(point);
//			}
		} catch (InfluxDBIOException idbio) {
			System.out.println(idbio.getMessage());
			idbio.printStackTrace();
			System.out.println(idbio.getStackTrace().toString());
		}
	}
}