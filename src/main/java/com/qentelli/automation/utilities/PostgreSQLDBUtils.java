
package com.qentelli.automation.utilities;

import com.qentelli.automation.common.Constants;
import com.qentelli.automation.common.World;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.math.BigInteger;
import java.sql.*;


public class PostgreSQLDBUtils {

    static Logger logger = LogManager.getLogger(PostgreSQLDBUtils.class);

    /*
     * Store Results in Mongo DB Collection
     * */
    private World world;
    public PostgreSQLDBUtils(World world) {
        this.world = world;
    }

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    // private static final String url = "jdbc:postgresql://localhost:5432/DBForGrafanaDocker";
    private static final String user = "postgres";
    private static final String password = "superuser";

   /* public static void main(String[] args) throws SQLException, IOException {
        ProcessData app = new ProcessData();
        Connection conn = app.connect();
        processJSON(conn);
    }*/

    private static Connection connect() {
        World world = Constants.world;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            logger.info("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return conn;
    }

    public static void selectFromDB(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();

        String selectQuery = "select * from application";
        ResultSet resultSet = stmt.executeQuery(selectQuery);
        resultSet.getRow();
        while (resultSet.next()) {

            int appId = resultSet.getInt("application_id");
            String name = resultSet.getString("name");
        }
    }

    public static void sendRunStatsToPostgreSQL(JSONObject DataSentToPostgreSQL) throws SQLException {
        Connection conn = connect();
        conn.setAutoCommit(false);
        JsonParser jsonParser = new JsonParser();
        JsonObject resultObject = (JsonObject)jsonParser.parse(DataSentToPostgreSQL.toString());
        logger.info("resultObject passed in = "  + resultObject.toString());
        try {
            if (resultObject != null) {
                String insertQuery = createInsertSet(resultObject, conn);
                int setId = executeInsertAndGetPrimaryKey(insertQuery, conn);
                JsonArray scenarioArray = new Gson().fromJson(resultObject.get("scenario"), JsonArray.class);
                JsonArray stepArray = new Gson().fromJson(resultObject.get("step"), JsonArray.class);

                for (JsonElement scenario : scenarioArray) {
                    JsonObject scenarioObj = scenario.getAsJsonObject();
                    insertQuery = createInsertScenario(scenarioObj, setId);
                    String featureName = scenarioObj.get("featureName").getAsString();
                    // data from automation for scenario name in step section prepended with testRail
                    String scenarioName =
                            scenarioObj.get("testRail").getAsString()
                                    + "_"
                                    + scenarioObj.get("scenarioName").getAsString();
                    int scenarioId =  executeInsertAndGetPrimaryKey(insertQuery, conn);

                    String testRail = scenarioObj.get("testRail").getAsString();
                    BigInteger lid = scenarioObj.get("lid").getAsBigInteger();


                    // Use StreamSupport to do the filter - TODO
                    for (JsonElement step : stepArray) {
                        JsonObject stepObj = step.getAsJsonObject();
                        if (stepObj.get("lid").getAsBigInteger().equals(lid)
                                && stepObj.get("testRail").getAsString().equals(testRail)) {
                            insertQuery = createInsertStep(stepObj, scenarioId);
                            executeInsertAndGetPrimaryKey(insertQuery, conn);
                        }
                    }
                }
                // end transaction block, commit changes
                conn.commit();
            }
        } catch (SQLException e) {
            conn.rollback();
            logger.warn("Failed to insert results into postgres db, message is =  " + e.getMessage());
        } finally {
            //  set it back to default true
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public static String createInsertSet(JsonObject resultObject, Connection conn)
            throws SQLException {
        String insertQuery = "";
        if (resultObject != null) {
            int applicationId =
                    getFKForDimensionTable(
                            "application", resultObject.get("application").getAsString(), conn);
            int suiteId = getFKForDimensionTable("suite", resultObject.get("suite").getAsString(), conn);
            int bucketId =
                    getFKForDimensionTable("bucket", resultObject.get("bucket").getAsString(), conn);
            int projectId =
                    getFKForDimensionTable("project", resultObject.get("project").getAsString(), conn);
            int localeId =
                    getFKForDimensionTable("locale", resultObject.get("locale").getAsString(), conn);
            int envId = getFKForDimensionTable("env", resultObject.get("env").getAsString(), conn);

            String runId = resultObject.get("runId").getAsString();
            String testRail = resultObject.get("testRail").getAsString();
            String logLink = resultObject.get("logLink").getAsString().replaceAll("'", "''");
            BigInteger lid = resultObject.get("lid").getAsBigInteger();
            Boolean isMobile = Boolean.parseBoolean(resultObject.get("mobile").getAsString());
            String platform = resultObject.get("platform").getAsString();
            String browser = resultObject.get("browser").getAsString();
            int failed = Integer.valueOf(resultObject.get("failed").getAsString());
            int skipped = Integer.valueOf(resultObject.get("skipped").getAsString());
            int passed = Integer.valueOf(resultObject.get("passed").getAsString());
            int total = Integer.valueOf(resultObject.get("total").getAsString());
            int duration = Integer.valueOf(resultObject.get("duration").getAsString());
            BigInteger start_time = resultObject.get("start").getAsBigInteger();
            BigInteger end_time = resultObject.get("end").getAsBigInteger();
            BigInteger time = resultObject.get("time").getAsBigInteger();
            String by_user = resultObject.get("user").getAsString();
            insertQuery =
                    "INSERT INTO set(project_id, locale_id, application_id, bucket_id, suite_id, env_id, "
                            + "run_id, testrail, loglink, lid, mobile, platform, "
                            + "browser, failed, skipped, passed, total, duration, "
                            + "start_time, end_time, time, by_user) values ("
                            + projectId
                            + ","
                            + localeId
                            + ","
                            + applicationId
                            + ", "
                            + bucketId
                            + ", "
                            + suiteId
                            + ", "
                            + envId
                            + ","
                            + "'"
                            + runId
                            + "', "
                            + "'"
                            + testRail
                            + "', '"
                            + logLink
                            + "', "
                            + lid
                            + ", "
                            + isMobile
                            + ", '"
                            + platform
                            + "', '"
                            + browser
                            + "', "
                            + failed
                            + ", "
                            + skipped
                            + ", "
                            + passed
                            + ", "
                            + total
                            + ", "
                            + duration
                            + ", "
                            + start_time
                            + ", "
                            + end_time
                            + ", "
                            + time
                            + ", '"
                            + by_user
                            + "')";
        }
        return insertQuery;
    }

    public static String createInsertScenario(JsonObject scenario, int setId) {
        String insertQuery = "";
        if (scenario != null) {
            String testRail = scenario.get("testRail").getAsString();
            String featureName = scenario.get("featureName").getAsString().replaceAll("'", "''");
            String scenarioName = scenario.get("scenarioName").getAsString().replaceAll("'", "''");
            BigInteger lid = scenario.get("lid").getAsBigInteger();
            String errorType = scenario.get("errorType").getAsString().replaceAll("'", "''");
            String testRailLink = scenario.get("testRailLink").getAsString();
            String sauceLink = scenario.get("sauceLink").getAsString().replaceAll("'", "''");
            String sauceHtml = scenario.get("sauceHtml").getAsString().replaceAll("'", "''");
            String runId = scenario.get("runId").getAsString();
            String comment = scenario.get("comment").getAsString().replaceAll("'", "''");
            String serverInfo = scenario.get("serverInfo").getAsString().replaceAll("'", "''");
            String sauceVideo = scenario.get("sauceVideo").getAsString();
            String result = scenario.get("result").getAsString();
            int failed = Integer.parseInt(scenario.get("failed").getAsString());
            int skipped = Integer.parseInt(scenario.get("skipped").getAsString());
            int passed = Integer.parseInt(scenario.get("passed").getAsString());
            int totalSteps = Integer.parseInt(scenario.get("totalSteps").getAsString());
            int duration = Integer.parseInt(scenario.get("duration").getAsString());
            BigInteger start_time = scenario.get("start").getAsBigInteger();
            BigInteger end_time = scenario.get("end").getAsBigInteger();
            BigInteger time = scenario.get("time").getAsBigInteger();

            insertQuery =
                    "INSERT INTO scenario (set_id, run_id, testrail, feature_name, scenario_name, error_type, lid, testraillink, duration,"
                            + " start_time, end_time, total_steps, result, failed, skipped, "
                            + "passed, sauce_link, server_info, sauce_video, sauce_html, comment) values ("
                            + setId
                            + ", '"
                            + runId
                            + "', "
                            + "'"
                            + testRail
                            + "', '"
                            + featureName
                            + "', '"
                            + scenarioName
                            + "', '"
                            + errorType
                            + "', "
                            + lid
                            + ", '"
                            + testRailLink
                            + "', "
                            + duration
                            + ", "
                            + start_time
                            + ", "
                            + end_time
                            + ", "
                            + totalSteps
                            + ", '"
                            + result
                            + "', "
                            + failed
                            + ","
                            + skipped
                            + ", "
                            + passed
                            + ", '"
                            + sauceLink
                            + "', '"
                            + serverInfo
                            + "', '"
                            + sauceVideo
                            + "', '"
                            + sauceHtml
                            + "', '"
                            + comment
                            + "')";
        }

        return insertQuery;
    }

    public static String createInsertStep(JsonObject step, int scenarioId) {
        String insertQuery = "";
        if (step != null) {
            String stepName = step.get("step").getAsString().replaceAll("'", "''");
            String runId = step.get("runId").getAsString();
            BigInteger lid = step.get("lid").getAsBigInteger();
            String testRail = step.get("testRail").getAsString();
            String result = step.get("result").getAsString();
            int duration = Integer.parseInt(step.get("duration").getAsString());
            int line =  Integer.parseInt(step.get("line").getAsString());
            BigInteger start_time = step.get("start").getAsBigInteger();
            BigInteger end_time = step.get("end").getAsBigInteger();

            insertQuery =
                    "INSERT INTO step(scenario_id, run_id, step_name, testrail, lid, line, duration, start_time, end_time, result) values ("
                            + scenarioId
                            + ", '"
                            + runId
                            + "', "
                            + "'"
                            + stepName
                            + "', '"
                            + testRail
                            + "', "
                            + lid
                            + ", "
                            + line
                            + ", "
                            + duration
                            + ", "
                            + start_time
                            + ", "
                            + end_time
                            + ", '"
                            + result
                            + "')";
        }
        return insertQuery;
    }
    // For insert execution and returning the PK.
    public static int executeInsertAndGetPrimaryKey(String insertQuery, Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();

        PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        pstmt.addBatch();
        pstmt.executeBatch();

        ResultSet res = pstmt.getGeneratedKeys();
        int id = 0;
        logger.info(
                "Auto-incremented values of the column ID generated by the current PreparedStatement object: ");
        while (res.next()) {
            id = Integer.parseInt(res.getString(1));
            logger.info("Primary key id = " +   id );
        }
        return id;
    }

    public static int getFKForDimensionTable(String tableName, String value, Connection conn)
            throws SQLException {
        if (tableName.equals("bucket")) {
            value = ( value.equals("Test")) ? "Dev" : "Live";
        }
        Statement stmt = conn.createStatement();
        String selectQuery = "select * from " + tableName + " where name ILIKE '" + value + "'";
        ResultSet resultSet = stmt.executeQuery(selectQuery);
        int tableId = 0;
        if (resultSet.next()) {
            tableId = resultSet.getInt(tableName + "_id");
        } else {
            // INSERT INTO suite (name) VALUES('Smoke');
            String insertQuery =
                    "INSERT INTO " + tableName + " (name) values ('" + value + "')";
            tableId = executeInsertAndGetPrimaryKey(insertQuery, conn);
        }
        return tableId;
    }
}


