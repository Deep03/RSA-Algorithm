import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class db extends Main {

    public static boolean establishConnection() {
        String url = "jdbc:postgresql://localhost/rootapollo";
        Properties props = new Properties();
        props.setProperty("user", "rootapollo");
        props.setProperty("password", "pass");
        boolean state = false;
        try {
            Connection db = DriverManager.getConnection(url, props);
            state = true;
//            db.close();
            return state;
        } catch (SQLException e) {
            e.printStackTrace();
            return state;
        }
    }

}
