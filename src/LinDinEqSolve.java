
import java.math.BigInteger;
import java.security.SecureRandom;

public class LinDinEqSolve {

    //    Algorithm to potentially use to solve LDE
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

    public static void solve(long a, long b)
    {
        long x = 0, y = 1, lastx = 1, lasty = 0, temp;
        while (b != 0)
        {
            long q = a / b;
            long r = a % b;

            a = b;
            b = r;

            temp = x;
            x = lastx - q * x;
            lastx = temp;

            temp = y;
            y = lasty - q * y;
            lasty = temp;
        }
        System.out.println("Roots  x : "+ lastx +" y :"+ lasty);
    }

    //----------------------------------------------------------------//
    // -----------------------NOT MY CODE-----------------------------------------

}