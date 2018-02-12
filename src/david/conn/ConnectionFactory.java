package david.conn;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final static String URL = "jdbc:mysql://localhost:3306/smart_words?useUnicode=yes&characterEncoding=UTF-8";
    private final static String USER_NAME = "root";
    private final static String PASSWD = "";


    public static Connection getConnection(){
        try {

            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER_NAME, PASSWD);

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);

        }
    }
}
