package dao;

        import org.junit.Test;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;
        import java.util.Properties;

public class DBConnection {

    private Connection dbConnection;

    @Test
    public void getDBConnection() throws ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        File propertiesFile = new File("src/main/resources/db.properties");

        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            properties.load(fileInputStream);
            Class.forName(properties.getProperty("DRIVER-CLASS-NAME"));
            String url =
                    "jdbc:mysql://localhost:"+properties.getProperty("PORT")+"/"+properties.getProperty("DATABASE.NAME");
            String user = properties.getProperty("DATASOURCE.USER");
            String password = properties.getProperty("DATASOURCE.PASSWORD");
            dbConnection = DriverManager.getConnection(url,user,password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public int executeQuery(String query) throws ClassNotFoundException, SQLException {
//        return dbConnection.createStatement().executeUpdate(query);
//    }

}
