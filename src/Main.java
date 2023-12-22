import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        // file handler
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("encryptKeys.txt"))) {
//            writer.write("The value of private exponent d: " + keyPair.d + "\n");
//            writer.write("The value of public exponent d: " + keyPair.e + "\n");
//        } catch (IOException e) {
//            System.out.println("Error Occurred: " + e);
//        }
        String msg = "Hello World";
        Cryptit.enrypt(msg);
    }

}
