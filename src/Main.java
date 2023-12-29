import java.math.BigInteger;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Sample
        String msg = "Hello World";
        CryptIt.generate();
        BigInteger[] encryptArr = CryptIt.enrypt(msg);
        String decryptMsg = CryptIt.decrypt(encryptArr);
        System.out.println("Original Message:" + msg);
        System.out.println("Encrypted Message:" + Arrays.toString(encryptArr));
        System.out.println("Decrypted Message:" + decryptMsg);
    }

}
