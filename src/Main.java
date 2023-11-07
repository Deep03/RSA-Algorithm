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



    private static BigInteger rsaSetUp(BigInteger p, BigInteger q, BigInteger n, BigInteger UppBound, BigInteger d, BigInteger e) {
        SecureRandom random = new SecureRandom();

        // two distinct large prime, for now assume they are distinct
        p = generatePrime(random);
        q = generatePrime(random);

        // n = pq
        n = p.multiply(q);

        // p - 1 and q - 1
        BigInteger p_one = p.subtract(BigInteger.ONE);
        BigInteger q_one = q.subtract(BigInteger.ONE);

        // public exponent
        e =  new BigInteger(String.valueOf(3));

        // 1 < e < uppBound
        UppBound = p_one.multiply(q_one);

        // private exponent
        d = myLDE(e, UppBound);
        return d;
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
        BigInteger d = rsaSetUp(null, null, null, null, null, null);

        // keep finding a D that exists such that gcd(e, UppBound) = 1
        while(true) {
            if (d == null == true) {
                d = rsaSetUp(null, null, null, null, null, null);
            }
            else {
                break;
            }
        }

    }

}
