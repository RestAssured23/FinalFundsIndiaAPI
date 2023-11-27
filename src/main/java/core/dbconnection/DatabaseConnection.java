package core.dbconnection;

import core.basepath.AccessPropertyFile;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection extends AccessPropertyFile {

    private String userName;
    private String password;
    private String url;
    private String driverName;
    public DatabaseConnection() {
        this("Sathish", "Sathish&damodhar$%123", "jdbc:sqlserver://mumbai-test-priv.cpdliqr4wev2.ap-south-1.rds.amazonaws.com:1433", "WIFS_TEST", true, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
    public DatabaseConnection(String dbusr, String dbpwd, String dburl, String databasename, boolean encrypt, String dbdrivername) {
        this.userName = dbusr;
        this.password = dbpwd;
        this.url = dburl + databasename + (encrypt ? "encrypt=true;trustServerCertificate=true" : "");
        this.driverName = dbdrivername;
    }
@Test
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection Created");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
        }
        return con;
    }
}