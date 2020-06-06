
package databaseclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbClass {

    private static final String DB_NAME = "BMS.db";

   public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

   public static Connection con;

    public DbClass() {

    }

    public static Connection connect() {
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);

        } catch (SQLException e) {

        }
        return con;
    }
    
}
