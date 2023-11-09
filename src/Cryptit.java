import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;


public class Cryptit extends Main {


    // constants for generatePrime function, change DESIRED_DIGITS to 3000 for build 1
    private static final int DESIRED_BIT = 1024;
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
        public BigInteger n;
        public BigInteger UppBound;
        public BigInteger d;
        public BigInteger e;
    }

    private static RSAKeyPair rsaSetUp() {
        SecureRandom random = new SecureRandom();
        RSAKeyPair keyPair = new RSAKeyPair();

        // two distinct large primes, for now assume they are distinct
        keyPair.p = generatePrime(random);
        keyPair.q = generatePrime(random);

        // n = pq
        keyPair.n = keyPair.p.multiply(keyPair.q);

        // p - 1 and q - 1
        BigInteger p_one = keyPair.p.subtract(BigInteger.ONE);
        BigInteger q_one = keyPair.q.subtract(BigInteger.ONE);

        // public exponent
        keyPair.e = new BigInteger(String.valueOf(3));

        // 1 < e < UppBound
        keyPair.UppBound = p_one.multiply(q_one);

        // private exponent
        keyPair.d =LinDinEqSolve.myLDE(keyPair.e, keyPair.UppBound);

        return keyPair;
    }

    public static boolean enrypt(String msg) {
        RSAKeyPair keyPair = rsaSetUp();
        // keep finding a D that exists such that gcd(e, UppBound) = 1
        while (true) {
            if (keyPair.d == null == true) {
                keyPair = rsaSetUp();
            } else {
                break;
            }
        }

        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        BigInteger[] encryptedMsg;
        BigInteger m;
        BigInteger c;
        BigInteger r;
        for (byte msgByte : msgBytes) {
            m = new BigInteger(String.valueOf(msgByte));
            c = m.modPow(keyPair.e, keyPair.n);
            r = c.modPow(keyPair.d, keyPair.n);
            System.out.println("The encrypted code is: " + c);
            System.out.println("The decrypted code is: " + r);
        }
        return false;
    }

//    public static boolean decrypt(String msg) {
//        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
//        BigInteger[] encryptedMsg;
//        BigInteger m;
//        BigInteger c;
//        BigInteger r;
//        for (byte msgByte : msgBytes) {
//            m = new BigInteger(String.valueOf(msgByte));
//            c = m.modPow(keyPair.e, keyPair.n);
//            r = c.modPow(keyPair.d, keyPair.n);
//            System.out.println("The encrypted code is: " + c);
//            System.out.println("The decrypted code is: " + r);
//        }
//        return false;
//    }
}
