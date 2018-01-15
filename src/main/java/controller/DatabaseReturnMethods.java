package controller;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.MysqlDataSource;
import config.DatabaseConnector;
import org.h2.jdbc.JdbcDatabaseMetaData;
import org.h2.jdbcx.JdbcDataSource;

import java.math.BigInteger;
import java.sql.*;

public class DatabaseReturnMethods {


    public static boolean checkIfBasketTableExists(Object dataSource) {
        if (dataSource.getClass().equals(MysqlDataSource.class)) {
            try {
                Connection connection = ((MysqlDataSource) dataSource).getConnection();
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, "basket_list", null);
                if (resultSet.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (dataSource.getClass().equals(JdbcDataSource.class)) {
            try {
                Connection connection = ((JdbcDataSource) dataSource).getConnection();
                JdbcDatabaseMetaData jdbcDatabaseMetaData = (JdbcDatabaseMetaData) connection.getMetaData();
                ResultSet resultSet = jdbcDatabaseMetaData.getTables(null, null, "basket_list", null);
                if (resultSet.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;

    }

    public static BigInteger sumAllDiscountedPrices(Object dataSource) {
        if (dataSource.getClass().equals(MysqlDataSource.class)) {

            ResultSet result = null;
            BigInteger sum = new BigInteger("0");
            try {
                Connection connection = DatabaseConnector.getDataSourceField().getConnection();
                Statement statement = connection.createStatement();
                result = statement.executeQuery("SELECT SUM(basket_price_with_discount) FROM basket_list;");
                while (result.next()) {
                    int sumFromDatabase = result.getInt(1);
                    sum = sum.add(BigInteger.valueOf(sumFromDatabase));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sum;

        } else if (dataSource.getClass().equals(JdbcDataSource.class)) {
            ResultSet result = null;
            BigInteger sum = new BigInteger("0");
            try {
                Connection connection = ((JdbcDataSource) dataSource).getConnection();
                Statement statement = connection.createStatement();
                result = statement.executeQuery("SELECT SUM(basket_price_with_discount) FROM basket_list;");
                while (result.next()) {
                    int sumFromDatabase = result.getInt(1);
                    sum = sum.add(BigInteger.valueOf(sumFromDatabase));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sum;
        }

        return null;
    }

    public static String getDatabaseName() {
        return DatabaseConnector.getDatabaseName();
    }

}
