import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Sample
        String msg = "Hello World";
        CryptIt.generate();
        BigInteger[] encryptArr = CryptIt.enrypt(msg);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your password to decrypt: ");
        String password = scanner.nextLine();
        scanner.close();
        String decryptMsg = CryptIt.decrypt(encryptArr, password);
        if (decryptMsg == null) {
            System.out.println("Incorrect Password");
        } else {
            // printing for a sample run
            System.out.println("Original Message:" + msg);
            System.out.println("Encrypted Message:" + Arrays.toString(encryptArr));
            System.out.println("Decrypted Message:" + decryptMsg);
        }
    }
}

// table:
// Privatexp Privatemod password
// no        no         hash