package config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;


import java.io.*;

import java.util.Properties;

public class DatabaseConnector {
    private static String databaseName;
    private static MysqlDataSource dataSource =new MysqlDataSource();


    public static JdbcTemplate getMySQLDataSource() {
        Properties properties = new Properties();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        File propertiesFile = new File("src/main/resources/db.properties");

        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            properties.load(fileInputStream);

            dataSource.setServerName(properties.getProperty("SERVER.ADDRESS"));
            dataSource.setPortNumber(Integer.parseInt(properties.getProperty("PORT")));
            dataSource.setDatabaseName(properties.getProperty("DATABASE.NAME"));
            dataSource.setUser(properties.getProperty("DATASOURCE.USER"));
            dataSource.setPassword(properties.getProperty("DATASOURCE.PASSWORD"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        databaseName = dataSource.getDatabaseName();
        jdbcTemplate.setDataSource(dataSource);

        return jdbcTemplate;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static MysqlDataSource getDataSourceField() {
        return dataSource;
    }}