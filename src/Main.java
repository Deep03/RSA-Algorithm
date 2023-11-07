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
        System.out.println(keyPair.e);

    }

}
