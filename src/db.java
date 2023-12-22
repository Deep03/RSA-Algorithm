import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db extends Main {

    public void establishConnection() {
        String url = "jdbc:postgresql://localhost:5432";
        String userName = "rootapollo";
        String password = "pass";

        try {
            Connection db = DriverManager.getConnection(url, userName, password);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
