import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;


public class Cryptit extends Main {


    // constants for generatePrime function, change DESIRED_DIGITS to 3000 for build 1
    private static final int DESIRED_BIT = 10;
    private static final int CERTAINTY = 100;


    // uses miller-rabin primality test
    // generatePrime: Function to create two large distinct prime numbers
    private static BigInteger generatePrime(SecureRandom random) {
        BigInteger prime = null;
        prime = new BigInteger(DESIRED_BIT, CERTAINTY, random);
        while (!prime.isProbablePrime(CERTAINTY)) {
            prime = new BigInteger(DESIRED_BIT, CERTAINTY, random);
        }
        return prime;
    }

    public static class RSAKeyPair {
        public BigInteger p;
        public BigInteger q;
        public BigInteger modulus;
        public BigInteger UppBound;
        public BigInteger privateKey;
        public BigInteger publicKey;

    }

    // set up a way to save the data in a database, the private key pair, d and n has to be stored
    private static RSAKeyPair rsaSetUp() {
        SecureRandom random = new SecureRandom();
        RSAKeyPair keyPair = new RSAKeyPair();

        // two distinct large primes, for now assume they are distinct
        keyPair.p = generatePrime(random);
        keyPair.q = generatePrime(random);

        // n = pq
        keyPair.modulus = keyPair.p.multiply(keyPair.q);

        // p - 1 and q - 1
        BigInteger p_one = keyPair.p.subtract(BigInteger.ONE);
        BigInteger q_one = keyPair.q.subtract(BigInteger.ONE);

        // public exponent
        keyPair.publicKey = new BigInteger(String.valueOf(3));

        // 1 < e < UppBound
        keyPair.UppBound = p_one.multiply(q_one);

        // private exponent
        keyPair.privateKey =LinDinEqSolve.myLDE(keyPair.publicKey, keyPair.UppBound);

        return keyPair;
    }
    static RSAKeyPair keyPair = rsaSetUp();



    public static boolean enrypt(String msg) {
        // keep finding a D that exists such that gcd(e, UppBound) = 1
        while (true) {
            if (keyPair.privateKey == null == true) {
                keyPair = rsaSetUp();
            } else {
                break;
            }
        }
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        int len = msgBytes.length;
        BigInteger[] encryptedMsg = new BigInteger[len];
        BigInteger m = null;
        BigInteger c = null;
        for (int i = 0; i < len; i++) {
            m = new BigInteger(String.valueOf(msgBytes[i]));
            c = m.modPow(keyPair.publicKey, keyPair.modulus);
            encryptedMsg[i] = c.multiply(BigInteger.valueOf(msgBytes[i]));
        }
        return false;
    }

    public static void insert() {
        boolean state = db.establishConnection();
        if (state) {

        }
        else {
            System.out.println("NO");
        }
    }
    // private key = (d, n) = (private key exponent, public moduli)
    // public key = (e, n) = (public key exponent, public moduli)

    // The one who needs to decrpyt needs the private key so I need to store it somehow
     public static boolean decrypt() {
         BigInteger[] encryptedMsg;
         BigInteger m;
         for (byte msgByte : msgBytes) {
             m = new BigInteger(String.valueOf(msgByte));

             // both of them need to be saved
             // encryption code
             c = m.modPow(keyPair.publicKey, keyPair.modulus);

             // decryption code
             r = c.modPow(keyPair.privateKey, keyPair.modulus);
         }
         return false;
     }


}
