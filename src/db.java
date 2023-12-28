import java.sql.*;
import java.util.Properties;

public class db extends Main {

    public static ResultSet executeSql(String query) {
        String url = "jdbc:postgresql://localhost/rootapollo";
        Properties props = new Properties();
        props.setProperty("user", "rootapollo");
        props.setProperty("password", "pass");
        try {
            Connection db = DriverManager.getConnection(url, props);
            Statement stmt = db.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            return null;
        }
    }

}
