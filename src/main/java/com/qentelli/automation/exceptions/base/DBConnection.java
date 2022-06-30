package com.qentelli.automation.exceptions.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.resources.DataBaseResourceBundle;
import com.qentelli.automation.utilities.AbstractResourceBundle;
import com.qentelli.automation.utilities.COOTestDataObject;
import com.qentelli.automation.utilities.COOTestcaseResourceBundle;
import com.qentelli.automation.utilities.RuntimeProperties;
import com.qentelli.automation.utilities.TextUtils;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DBConnection {
	protected static Logger logger = LogManager.getLogger(DBConnection.class);
	public RuntimeProperties props = new RuntimeProperties();


	private Connection connection;
	private DataBaseResourceBundle database;

	public DBConnection(String c) {
		logger.info(" --------------------------------- ");
		logger.info(c);
		logger.info(" --------------------------------- ");

		database = new DataBaseResourceBundle(c);

		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:" + connStr(), database.user, database.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("connected@" + c);

	}

	public DBConnection() {
		// TODO Auto-generated constructor stub
	}

	private String connStr() {
		String str = String.format("@%s:%s/%s", database.host, database.port, database.service);
		logger.info("URL  :   " + str);

		return str;
	}

	public ResultSet query(String q) {
		ResultSet rs = null;
		logger.info("Query: " + q);
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	private ResultSet getMySQLviaSSH(String sql) {
		COOTestDataObject testdata = new COOTestDataObject();

		Connection con = null;
		Statement st = null;

		logger.info(testdata.mySqlPassword + "executing statement:\t|" + sql + "| on " + testdata.mySqlDatabase);
		ResultSet rs = null;
		int assinged_port = ssh(testdata.mySqlDatabase);
		if (assinged_port < 0)
			throw new RuntimeException("the MySQL tunnel ssh tunnel failed !!!! ");
		logger.info("SSH Connected port: " + assinged_port);

		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:" + assinged_port;
		String db = "";
		String dbUser = "reports";
		String dbPasswd = testdata.mySqlPassword;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, dbUser, dbPasswd);
			st = con.createStatement();
			rs = st.executeQuery(sql);
			logger.info("statement succeeds");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public void connectMemberMySQLviaSSH() {
		COOTestcaseResourceBundle testcase = new COOTestcaseResourceBundle() ;  
		RuntimeProperties r = new RuntimeProperties();
		String env = AbstractResourceBundle.getEnv() ; 
		logger.info(env + " sponsor_id = "+ testcase.sponsorID);
		String sql 										  = "SELECT coach_id, sponsor_id, guid, parent_coach_id, placement_from_parent, first_name, last_name, email1, lifetime_rank_id FROM coo.member WHERE sponsor_id = "+testcase.sponsorID+" AND lifetime_rank_id != 100 AND rank_id != 100 AND lifetime_rank_id != -1 AND rank_id != -1 AND nest_left IS NOT NULL AND nest_right IS NOT NULl AND member_restriction != 'Do Not Contact' LIMIT 0, 1";
		logger.info(env.equals("QA4") +" env:" + env) ; 
		if (env.equals("QA4")) sql = "SELECT coach_id, sponsor_id, guid, parent_coach_id, placement_from_parent, first_name, last_name, email1, lifetime_rank_id, member_restriction FROM coo.member WHERE sponsor_id = "+testcase.sponsorID+" AND lifetime_rank_id != 100 AND rank_id != 100 AND lifetime_rank_id != - 1 AND rank_id != - 1 AND nest_left IS NOT NULL AND nest_right IS NOT NULL AND (member_restriction IS NULL or member_restriction = 'Unrestricted') LIMIT 0 , 1" ; 

		writeMemberProps(getMySQLviaSSH(sql));
	}

	public void connectCustomerMySQLviaSSH() {
		COOTestcaseResourceBundle testcase = new COOTestcaseResourceBundle() ;  
		logger.info("coach_id = "+ testcase.coachID);

		

		RuntimeProperties rprops = new RuntimeProperties();
		final String sql = "Select customer_id, guid, coach_id, first_name, last_name, email1 FROM coo.customer WHERE coach_id = "+testcase.coachID +" AND cust_restriction != 'Do Not Contact' AND email1 NOT IN (SELECT email1 FROM coo.member WHERE sponsor_id = "+testcase.coachID +" AND lifetime_rank_id != 100 AND rank_id != 100 AND lifetime_rank_id != -1 AND rank_id != -1) LIMIT 0, 1";
		Boolean reTry = true;
		ResultSet rs = getMySQLviaSSH(sql);
		int i = 0;
		while (reTry && i++ < 10) {
			try {
				rs.next();
				rprops.writeProp("customer_id", rs.getString("customer_id"));
				rprops.writeProp("coach_id", rs.getString("coach_id"));
				rprops.writeProp("sponsor_id", rs.getString("coach_id"));

				rprops.writeProp("first_name", rs.getString("first_name"));
				rprops.writeProp("last_name", rs.getString("last_name"));
				rprops.writeProp("email1", rs.getString("email1"));

				logger.info("success");
				reTry = false;
			} catch (Exception e) {
				e.printStackTrace();
				BasePage.sleep(10);
				throw new EnvironmentException(" the data from mysql appears to have errors");

			}
			try {

				rprops.writeProp("guid", rs.getString("guid"));
			} catch (Exception e) {
				logger.info("guid is null; writting blank; this is OK");
				rprops.writeProp("guid", "");
			}
		}
		if (rprops.readProp("coach_id") == "" || rprops.readProp("coach_id") == null)
			throw new EnvironmentException(" the member data from mysql appears to have errors");
		if (rprops.readProp("sponsor_id") == "" || rprops.readProp("sponsor_id") == null)
			throw new EnvironmentException(" the member data from mysql appears to have errors");
		if (rprops.readProp("email1") == "" || rprops.readProp("email1") == null)
			throw new EnvironmentException(" the member data from mysql appears to have errors");
		if (rprops.readProp("customer_id") == "" || rprops.readProp("customer_id") == null)
			throw new EnvironmentException(" the member data from mysql appears to have errors");
	}

	public void connectPreferredCustomerMySQLviaSSH() {
		COOTestcaseResourceBundle testcase = new COOTestcaseResourceBundle() ;  
		logger.info("pc id = "+ testcase.pcsponsorID);
		String env = AbstractResourceBundle.getEnv() ; 
		logger.info(env.equals("QA4") +" env:" + env) ; 
		String sql = "SELECT rank_id,coach_id, sponsor_id, guid, parent_coach_id, placement_from_parent, first_name, last_name, email1, lifetime_rank_id  FROM coo.member WHERE sponsor_id = "+testcase.pcsponsorID+" AND lifetime_rank_id != 100 AND rank_id != 100 AND lifetime_rank_id = -1 AND rank_id = -1 AND nest_left IS NOT NULL AND nest_right IS NOT NULL AND member_restriction != 'Do Not Contact' AND email1 != '' LIMIT 0, 1";
		if (env.equals("QA4")) sql = "SELECT coach_id, sponsor_id, guid, parent_coach_id, placement_from_parent, first_name, last_name, email1, lifetime_rank_id, member_restriction FROM coo.member WHERE sponsor_id = "+testcase.pcsponsorID+" AND lifetime_rank_id != 100 AND rank_id != 100 AND lifetime_rank_id != - 1 AND rank_id != - 1 AND nest_left IS NOT NULL AND nest_right IS NOT NULL AND (member_restriction IS NULL or member_restriction = 'Unrestricted') LIMIT 0 , 1" ; 
		writeMemberProps(getMySQLviaSSH(sql)) ;
	}

	private void writeMemberProps(ResultSet rs) {
		RuntimeProperties rprops = new RuntimeProperties();
		try {
			rs.next();
			rprops.writeProp("coach_id", rs.getString("coach_id"));
			rprops.writeProp("sponsor_id", rs.getString("sponsor_id"));
			rprops.writeProp("parent_coach_id", rs.getString("parent_coach_id"));
			rprops.writeProp("guid", rs.getString("guid"));
			rprops.writeProp("placement_from_parent", rs.getString("placement_from_parent"));
			rprops.writeProp("first_name", rs.getString("first_name"));
			rprops.writeProp("last_name", rs.getString("last_name"));
			rprops.writeProp("email1", rs.getString("email1"));
			logger.info(rs.getString("email1").length());
			if (rs.getString("email1").length() == 0) {
				rprops.writeProp("email1",
						rs.getString("first_name") + rs.getString("last_name") + "@testtest.com");

			}
			rprops.writeProp("lifetime_rank_id", rs.getString("lifetime_rank_id"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EnvironmentException(" the member data from mysql appears to have errors");
		}

		if (rprops.readProp("coach_id") == "" || rprops.readProp("coach_id") == null)
			throw new EnvironmentException(" the member data from mysql appears to have errors");

	}

	int ssh(String db) {
		final String user = "ec2-user";
		final String host = "54.189.62.91";
		String key = null;
		java.util.Properties config = new java.util.Properties();
		JSch jsch = new JSch();
		Session session;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("id_rsa_mysql");
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		String key_path = null;

		try {
			for (String line; (line = reader.readLine()) != null;)
				key += line + "\n";

			key = key.substring(0, key.length() - 1);
			key_path = TextUtils.writeTmp("mysql_key", key);
			session = jsch.getSession(user, host, 22);
			jsch.addIdentity(key_path);
			config.put("StrictHostKeyChecking", "no");
			config.put("ConnectionAttempts", "1");
			session.setConfig(config);
			session.connect();
			return session.setPortForwardingL(0, db, 3306);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;

	}
}
