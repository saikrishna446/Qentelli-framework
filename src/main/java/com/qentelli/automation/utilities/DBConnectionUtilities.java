package com.qentelli.automation.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.common.World;
import com.qentelli.automation.libraries.ConfigFileReader;
import com.qentelli.automation.resources.ATG;

public class DBConnectionUtilities {
	public enum DBNames {
		EBS, IDM, ATGDB
	}

	private boolean recordExists;
	private boolean orderExistsInATGDB;
	private Connection connection;
	private Statement statement;
	private World world;

	private ResultSet rs = null;
	private boolean gncDetailsFound = false;
	Logger logger = LogManager.getLogger(DBConnectionUtilities.class);

	public DBConnectionUtilities(World world) {
		this.world = world;
	}

	public DBConnectionUtilities(DBNames dbNames, World world) throws Exception {
		this.world = world;
		Class.forName("oracle.jdbc.driver.OracleDriver");
        logger.info(dbNames);
        logger.info(dbNames.equals(DBNames.ATGDB));

        connection = CreateConnection(DBNames.EBS, world.getTestEnvironment());
        if (connection != null)
		statement = connection.createStatement();
	}

	private Connection CreateConnection(DBNames dbNames, String env) throws SQLException {
		String url = null;
        logger.info(dbNames);
		ConfigDetails configFileReader = new ConfigDetails("db");
		String user = ConfigDetails.dbProperties.getProperty(dbNames + "_" + env + "_USER_ID");
		String password = ConfigDetails.dbProperties.getProperty(dbNames + "_" + env + "_PWD");
		logger.info("Db connection start create on " + (dbNames + "_" + env).toLowerCase());
		switch ((dbNames + "_" + env).toLowerCase()) {
		case "idm_uat":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("IDM_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("IDM_PORT"),
					ConfigDetails.dbProperties.getProperty("IDM_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_uat":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			logger.info("URL:" + url);

			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_uat":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_qa3":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_qa4":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			logger.info(url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_qa3":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_qa4":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "idm_dev3":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("IDM_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("IDM_DEV3_PORT"),
					ConfigDetails.dbProperties.getProperty("IDM_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_dev3":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_DEV3_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_dev3":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "idm_dev2":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("IDM_DEV2_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("IDM_DEV2_PORT"),
					ConfigDetails.dbProperties.getProperty("IDM_DEV2_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_dev2":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_dev2":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "idm_qa2":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("IDM_QA2_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("IDM_QA2_PORT"),
					ConfigDetails.dbProperties.getProperty("IDM_QA2_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
        case "ebs_qa2":
            url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env +
                            "_HOST_NAME"),
                    ConfigDetails.dbProperties.getProperty("EBS_" + env + "_PORT"),
                    ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
            connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
            break;
		case "atgdb_qa2":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "idm_dev4":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("IDM_DEV4_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("IDM_DEV4_PORT"),
					ConfigDetails.dbProperties.getProperty("IDM_DEV4_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_dev4":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "ebs_dev5":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("EBS_" + env + "_HOST_NAME"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_PORT"),
					ConfigDetails.dbProperties.getProperty("EBS_" + env + "_SERVICE_NAME"));
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_dev4":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		case "atgdb_dev5":
			url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_host"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_port"),
					ConfigDetails.dbProperties.getProperty("ATGDB_" + env + "_database"));
			logger.info("URL  :   " + url);
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		default:
          // url = String.format("@%s:%s/%s", ConfigDetails.dbProperties.getProperty("IDM_" + env +
          // "_HOST_NAME"),
          // ConfigDetails.dbProperties.getProperty("IDM_PORT"),
          // ConfigDetails.dbProperties.getProperty("IDM_" + env + "_SERVICE_NAME"));
          // connection = DriverManager.getConnection("jdbc:oracle:thin:" + url, user, password);
			break;
		}
		logger.info("Db connection got created on " + dbNames);
		logger.info("Db connection got created on " + url);

		return connection;
	}

	public ResultSet queryByDesignTable(String orderNumber) throws SQLException, InterruptedException {
		String query = "select header_id, error_message, order_number, gnc_process_flag, process_type, by_design_order_status, bonus_date, error_message, cust_createupgrade_flag, order_type, zero_out_Shakeology from XXPP.XXPP_BYDESIGN_ORDER_headers"
				+ " WHERE order_number = '" + orderNumber + "'";
		logger.info("Executing the query " + query);
		return waitForResult(query, "GNC_PROCESS_FLAG", "P");
	}

	public ResultSet queryICentrisTable(String orderNumber) throws SQLException, InterruptedException {
		world.setOrderNum(orderNumber);
		String query = "select *from apps.XXPP_ICENTRIS_EXTRACT_HEADERS where order_number = '" + orderNumber + "'";
		world.getDataBaseInputTestDataJson().put("queryICentrisTable_" + orderNumber,
				"select * from apps.XXPP_ICENTRIS_EXTRACT_HEADERS where order_number = '" + orderNumber + "'");
		logger.info("Executing the query " + query);
		waitForResult(query, "process_Flag", "S");
		return waitForResult(query, "ORDER_STATUS", "POSTED");
	}

	public ResultSet queryUsersAreProvisoned(String email) throws SQLException, InterruptedException {
		String schema = world.getTestEnvironment().equalsIgnoreCase("uat") ? "prodoim_oim" : "qa3oim_oim";
		if (world.getTestEnvironment().equalsIgnoreCase("DEV3") || world.getTestEnvironment().equalsIgnoreCase("DEV2")
				|| world.getTestEnvironment().equalsIgnoreCase("QA2")) {
			schema = "prodoim_oim";
		}
		executeQueryUsingScrollableResultSet("alter session set nls_date_format = 'dd-MON-yyyy hh24:mi:ss'");
		try {
			Thread.sleep(10000);
		} catch (Exception e1) {
			logger.info("Waiting exception >>>> " + e1.getLocalizedMessage());
		}
		String query = "select ost.ost_status USER_STATUS, obj.obj_name APPLICATION, obj.obj_key, oiu.oiu_create CREATION_DATE\n"
				+ "from " + schema + ".oiu\n" + "inner join " + schema + ".ost on oiu.ost_key = ost.ost_key\n"
				+ "inner join " + schema + ".obi on oiu.obi_key = obi.obi_key\n" + "inner join " + schema
				+ ".obj on obi.obj_key = obj.obj_key\n" + "where oiu.usr_key=(select usr_key from " + schema
				+ ".usr where USR_EMAIL ='" + email + "')";
		logger.info("Executing the query " + query);
		return waitForResult(query, "USER_STATUS", "provisioned");
	}

	public ResultSet ebsOrderUpdate(String email) throws SQLException {
		String query = "select hp.attribute4 byd_sponsor_id,hca.attribute14 Customer_Role,hp.attribute3 byd_cust_id, hp.attribute2 byd_coach_id "
				+ " from apps.hz_parties hp, apps.hz_cust_accounts hca "
				+ " where hp.party_id = hca.party_id and UPPER(hp.EMAIL_ADDRESS) ='" + email + "'";
		logger.info("Executing the query " + query);
		return statement.executeQuery(query);
	}

	public ResultSet executeQuery(String query) throws SQLException {
		logger.info("Executing the query " + query);
		return statement.executeQuery(query);
	}

	public ResultSet executeQueryUsingScrollableResultSet(String query) throws SQLException {
		Statement scrollableStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		logger.info("Executing the query " + query);
		return scrollableStatement.executeQuery(query);
	}

	public ResultSet waitForResult(String query) throws SQLException, InterruptedException {
		ResultSet resultSet = null;
		int count = 0;
		do {
			resultSet = executeQueryUsingScrollableResultSet(query);
			if (resultSet.next()) {
				resultSet.beforeFirst();
				break;
			} else {
				Thread.sleep(60 * 1000);
				count++;
			}
		} while (count < ConfigFileReader.getConfigFileReader().getdataSyncTime());
		return resultSet;
	}

	public ResultSet waitForResultWithWait(String query, int lCount) throws SQLException, InterruptedException {
		ResultSet resultSet = null;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();
		formatter.setTimeZone(TimeZone.getTimeZone("PST"));
		int count = 0;
		do {
			resultSet = executeQueryUsingScrollableResultSet(query);
			if (resultSet.next()) {
				date = new Date();
				if (query.contains("xxdaz_order_import_headers")) {
					if (!resultSet.getString("PROCESS_FLAG").toLowerCase().equals("s".toLowerCase())) {
						logger.info("STAGING Table << " + count + " >> " + formatter.format(date) + " waiting from "
								+ query + " Table, Process Flag Expecting S:  Actual: "
								+ resultSet.getString("PROCESS_FLAG"));
					}
					if (resultSet.getString("PROCESS_FLAG").toLowerCase().equals("s".toLowerCase())) {
						logger.info("STAGING Table << " + count + " >> " + formatter.format(date) + " waiting from "
								+ query + " Table, Process Flag Expecting S:  Actual: "
								+ resultSet.getString("PROCESS_FLAG"));
						resultSet.beforeFirst();
						break;
					}
				}
				if (query.contains("oe_order_headers_all")) {
					if (!resultSet.getString("FLOW_STATUS_CODE").toLowerCase().equals("CLOSED".toLowerCase())) {
						if (!resultSet.getString("FLOW_STATUS_CODE").toLowerCase().equals("BOOKED".toLowerCase())) {
							logger.info("Order Headers Table << " + count + " >> " + formatter.format(date)
									+ " waiting from " + query + " Table, FLOW_STATUS_CODE Expecting BOOKED:  Actual: "
									+ resultSet.getString("FLOW_STATUS_CODE"));
						}
					}
					if (resultSet.getString("FLOW_STATUS_CODE").toLowerCase().equals("BOOKED".toLowerCase())
							|| resultSet.getString("FLOW_STATUS_CODE").toLowerCase().equals("CLOSED".toLowerCase())) {
						logger.info("Order Headers Table << " + count + " >> " + formatter.format(date)
								+ " waiting from " + query + " Table, FLOW_STATUS_CODE Expecting BOOKED:  Actual: "
								+ resultSet.getString("FLOW_STATUS_CODE"));
						resultSet.beforeFirst();
						break;
					}
				}
				resultSet.beforeFirst();
			} else {
				date = new Date();
				logger.info(count + " >> " + formatter.format(date) + " waiting from " + query + " Table");
			}
			Thread.sleep(60 * 1000);
			count++;
		} while (count < lCount);
		return resultSet;
	}

	public ResultSet waitForResultWithWait(String query, String columnName, String columnValue, int iCount)
			throws SQLException, InterruptedException {
		ResultSet resultSet = null;
		recordExists = false;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();
		formatter.setTimeZone(TimeZone.getTimeZone("PST"));
		int count = 0;
		do {
			try {
				resultSet = executeQueryUsingScrollableResultSet(query);
				if (resultSet.next()) {
					date = new Date();
					if (resultSet.getString(columnName).toLowerCase().equalsIgnoreCase(columnValue.toLowerCase())) {
						resultSet.beforeFirst();
						recordExists = true;
						break;
					} else {
						resultSet.beforeFirst();
						logger.info(count + " >> " + formatter.format(date) + " waiting from " + query + " Table");
						Thread.sleep(60 * 1000);
						recordExists = true;
						count++;
					}
				} else {
					date = new Date();
					logger.info(count + " >> " + formatter.format(date) + " waiting from " + query + " Table");
					Thread.sleep(60 * 1000);
					recordExists = false;
					count++;
				}
			} catch (Exception db1) {
				logger.info(count + " >>>>> DB Exception: for Query: '" + query + "' execute exception: "
						+ db1.getLocalizedMessage());
				count++;
			}
		} while (count < iCount);
		if (!recordExists && !query.contains("OE_HOLD_DEFINITIONS")) {
			logger.error(count + " >> " + formatter.format(date) + " No Record found from " + query + " Table");
		}
		return resultSet;
	}

	public ResultSet waitForResult(String query, String columnName, String columnValue)
			throws SQLException, InterruptedException {
		ResultSet resultSet = null;
		recordExists = false;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();
		formatter.setTimeZone(TimeZone.getTimeZone("PST"));
		int count = 0;
		do {
			resultSet = executeQueryUsingScrollableResultSet(query);
			if (resultSet.next()) {
				if (resultSet.getString(columnName).toLowerCase().equalsIgnoreCase(columnValue.toLowerCase())) {
					resultSet.beforeFirst();
					recordExists = true;
					break;
				} else {
					resultSet.beforeFirst();
					Thread.sleep(60 * 1000);
					count++;
				}
				recordExists = true;
			} else {
				date = new Date();
				logger.info(count + " : " + formatter.format(date)
						+ " >>>> User Provisioning didn't fetch any records in query >>>>>");
				Thread.sleep(60 * 1000);
				count++;
			}
		} while (count < 10);// ConfigFileReader.getConfigFileReader().getdataSyncTime()
		if (!recordExists) {
			logger.error(count + " : " + formatter.format(date)
					+ " >>>> User Provisioning didn't fetch any records in query >>>>>");
		}
		return resultSet;
	}



}
