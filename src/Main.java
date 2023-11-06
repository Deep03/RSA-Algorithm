import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

    // constants for generatePrime function
    private static final int DESIRED_DIGITS = 300;
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


    // My algorithm to solve LDE:
    public static void myLDE() {
        int x;
        int d;
    }

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();

        // two distinct large prime, for now assume they are distinct
        BigInteger p = generatePrime(random);
        BigInteger q = generatePrime(random);

        // n = pq
        BigInteger n = p.multiply(q);

        // p - 1 and q - 1
        BigInteger p_one = p.subtract(BigInteger.ONE);
        BigInteger q_one = q.subtract(BigInteger.ONE);

        BigInteger e =  new BigInteger(String.valueOf(3));

        // 1 < e < uppBound
        BigInteger UppBound = p_one.multiply(q_one);

        // ed congruent 1 mod(uppBound).
        BigInteger d;
        // because p -1 and q - 1 might not be co prime, they have to be co prime
        // e has to be co prime with uppbound
        try {
            d = e.modInverse(UppBound);
        } catch (Exception e) {

        }
    }

}
