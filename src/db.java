import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db extends Main {

    public static boolean establishConnection() {
        String url = "jdbc:postgresql://localhost:5432";
        String userName = "rootapollo";
        String password = "pass";
        boolean state = false;

        try {
            Connection db = DriverManager.getConnection(url, userName, password);
            db.close();
            return state;
        } catch (SQLException e) {
            return state;
        }
    }
}
