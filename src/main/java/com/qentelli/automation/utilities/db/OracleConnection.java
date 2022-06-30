package com.qentelli.automation.utilities.db;

//import cucumber.runtime.java.guice.ScenarioScoped;

import com.qentelli.automation.common.World;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class OracleConnection {
    private World world=null;
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static BasicDataSource dataSource;

    private Connection connection;

    public OracleConnection(World world) {
        //super(world, world.driver);
        this.world = world;
    }

    public void makeOracleConnection(String host, String port, String databaseName, String username, String password) {


        if(dataSource == null || !dataSource.getUrl().equalsIgnoreCase("jdbc:oracle:thin:@" + host + ":" + port + ":" + databaseName)){
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:oracle:thin:@" + host + ":" + port + ":" + databaseName);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setInitialSize(5);
            dataSource = ds;
            LOGGER.debug("DB connection created.");
        } else {
            LOGGER.debug("Using existing DB connection.");
        }

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void makeOracleConnectionUsingServiceName(String host, String port, String databaseName, String username, String password) {


        if(dataSource == null || !dataSource.getUrl().equalsIgnoreCase("jdbc:oracle:thin:@" + host + ":" + port + ":" + databaseName)){
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:oracle:thin:@" + host + ":" + port + "/" + databaseName);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setInitialSize(5);
            dataSource = ds;
            LOGGER.debug("DB connection created.");
        } else {
            LOGGER.debug("Using existing DB connection.");
        }

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ResultSet query(String queryString, Object... values){
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            LOGGER.debug(queryString + " " + Arrays.asList(values));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
            e.printStackTrace();
        }
        return resultSet;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
