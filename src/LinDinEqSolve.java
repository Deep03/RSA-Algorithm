
import java.math.BigInteger;

public class LinDinEqSolve extends Main {

    // My algorithm to solve LDE:

    public static BigInteger myLDE(BigInteger e, BigInteger UppBound) {
        BigInteger d = null;
        // ed congruent 1 mod(uppBound).
        // because p -1 and q - 1 might not be co prime, they have to be co prime
        // e has to be co prime with uppBound
        if (e.gcd(UppBound).equals(BigInteger.ONE)) {
            // ed congruent 1 mod(uppBound).
            d = e.modInverse(UppBound);
        } else {
            return null;
        }
        return d;
    }

}