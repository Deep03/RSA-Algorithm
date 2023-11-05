import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

    // constants for generatePrime function
    private static final int DESIRED_DIGITS = 300;
    private static final int CERTAINTY = 100;
    // uses miller-rabin primality test
    private static BigInteger generatePrime(SecureRandom random) {
        BigInteger prime;
        do {
            prime = new BigInteger(DESIRED_DIGITS, CERTAINTY, random);
        } while (!prime.isProbablePrime(CERTAINTY));

        return prime;
    }
    // -----------------------NOT MY CODE-----------------------------------------
    //----------------------------------------------------------------//
    // algorithm to solve LDE, solve the congruency ed ≡ 1 mod (p - 1)(q - 1)
    // need to solve the equation below
    // (p - 1)(q - 1)x + ed = 1
    public static BigInteger findModularInverse(BigInteger e, BigInteger totient) {
        BigInteger[] x = extendedEuclidean(e, totient);
        if (x[0].compareTo(BigInteger.ZERO) < 0) {
            x[0] = x[0].add(totient);
        }
        return x[0];
    }

    public static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b) {
        BigInteger[] result = new BigInteger[3];
        if (b.equals(BigInteger.ZERO)) {
            result[0] = BigInteger.ONE;
            result[1] = BigInteger.ZERO;
            result[2] = a;
            return result;
        }
        BigInteger[] temp = extendedEuclidean(b, a.mod(b));
        result[0] = temp[1];
        result[1] = temp[0].subtract(a.divide(b).multiply(temp[1]));
        result[2] = temp[2];
        return result;
    }
    //----------------------------------------------------------------//
    // -----------------------NOT MY CODE-----------------------------------------


    // My algorithm to solve LDE:
    public static void myLDE() {
        int x;
        int d;
    }

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();

        BigInteger p = generatePrime(random);
        BigInteger q;
        do {
            q = generatePrime(random);
        } while (q.equals(p));

        BigInteger n = p.multiply(q);
        BigInteger p_one = p.subtract(BigInteger.ONE);
        BigInteger q_one = q.subtract(BigInteger.ONE);

        BigInteger e = BigInteger.valueOf(3);
        BigInteger totient = p_one.multiply(q_one);
        while (e.compareTo(totient) < 0 && !e.gcd(totient).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        BigInteger d = findModularInverse(e, totient);
    }
    


}
