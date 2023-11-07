import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

public class Main {

    // constants for generatePrime function, change DESIRED_DIGITS to 3000 for build 1
    private static final int DESIRED_DIGITS = 20;
    private static final int CERTAINTY = 100;


    // uses miller-rabin primality test
    // generatePrime: Function to create two large distinct prime numbers
    private static BigInteger generatePrime(SecureRandom random) {
        BigInteger prime;
        do {
            prime = new BigInteger(DESIRED_DIGITS, CERTAINTY, random);
        } while (!prime.isProbablePrime(CERTAINTY));

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
        keyPair.d = myLDE(keyPair.e, keyPair.UppBound);

        return keyPair;
    }


    // My algorithm to solve LDE:
    public static BigInteger myLDE(BigInteger e, BigInteger UppBound) {
        BigInteger d = null;
        // ed congruent 1 mod(uppBound).
        // because p -1 and q - 1 might not be co prime, they have to be co prime
        // e has to be co prime with uppBound
        if (e.gcd(UppBound).equals(BigInteger.ONE)) {
            // ed congruent 1 mod(uppBound).
            d = e.modInverse(UppBound);
        }
        else {
            return null;
        }
        return d;
    }

    public static void main(String[] args) {
        RSAKeyPair keyPair = rsaSetUp();
        // keep finding a D that exists such that gcd(e, UppBound) = 1
        while(true) {
            if (keyPair.d == null == true) {
                keyPair = rsaSetUp();
            }
            else {
                break;
            }
        }

//        // file handler
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("encryptKeys.txt"))) {
//            writer.write("The value of private exponent d: " + keyPair.d + "\n");
//            writer.write("The value of public exponent d: " + keyPair.e + "\n");
//        } catch (IOException e) {
//            System.out.println("Error Occurred: " + e);
//        }
        String msg = "Hello World";
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        BigInteger m;
        BigInteger c;
        BigInteger r;
        System.out.println(Arrays.toString(msgBytes));
        for (int i = 0; i < msgBytes.length; i++) {
            m = new BigInteger(String.valueOf(msgBytes[i]));
            c = m.modPow(keyPair.e, keyPair.n);
            r  = c.modPow(keyPair.d, keyPair.n);
            System.out.println("The encrypted code is: " + c);
            System.out.println("The descrypted code is: " + r);
        }
    }

}
