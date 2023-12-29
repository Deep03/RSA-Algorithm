import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CryptIt extends Main {


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

    static RSAKeyPair keyPair = new RSAKeyPair();

    public static void insert() throws SQLException {
        String query;
        ResultSet resultSet;
        query = "SELECT privateexp from keypair as privatexp";
        resultSet = db.executeSql(query);
        BigInteger privateexp = null;
        assert resultSet != null;
        if (resultSet.next()) {
            privateexp = resultSet.getObject(1, BigInteger.class);
        }
        query = "SELECT privatemod from keypair as privatemod";
        resultSet = db.executeSql(query);
        BigInteger privatemod = null;
        assert resultSet != null;
        if (resultSet.next()) {
            privatemod = resultSet.getObject(1, BigInteger.class);
        }
        if (privatemod == null || !privatemod.equals(keyPair.modulus) && keyPair.modulus != null) {
            query = String.format("UPDATE keypair SET privatemod = %s;", keyPair.modulus);
            db.executeSql(query);
        } else {
            keyPair.modulus = privatemod;
        }
        if (privateexp == null || !privateexp.equals(keyPair.privateKey) && keyPair.privateKey != null) {
            query = String.format("UPDATE keypair SET privateexp = %s;", keyPair.privateKey);
            db.executeSql(query);
        } else {
            keyPair.privateKey = privateexp;
        }
    }

    // method to check if data exists in database
    public static boolean exists() throws SQLException {
        // query to check if privateMod and privateexp exists
        String query = "SELECT EXISTS (SELECT privateexp FROM keypair WHERE ID = 1 AND privateexp IS NOT NULL) AS privateexp_exists, " +
                "EXISTS (SELECT privatemod FROM keypair WHERE ID = 1 AND privatemod IS NOT NULL) AS privatemod_exists;";
        ResultSet resultSet = db.executeSql(query);
        assert resultSet != null;
        if (resultSet.next()) {
            boolean privateexpExists = resultSet.getBoolean("privateexp_exists");
            boolean privatemodExists = resultSet.getBoolean("privatemod_exists");
            return privatemodExists && privateexpExists;
        }
        return false;
    }

    public static void generate() throws SQLException {
        boolean state = exists();
        if (state) {
            String query = "SELECT privatemod from keypair as privatemod";
            ResultSet resultSet = db.executeSql(query);
            BigInteger privatemod = null;
            assert resultSet != null;
            if (resultSet.next()) {
                privatemod = resultSet.getObject(1, BigInteger.class);
            }
            query = "SELECT privateexp from keypair as privatexp";
            resultSet = db.executeSql(query);
            BigInteger privateexp = null;
            assert resultSet != null;
            if (resultSet.next()) {
                privateexp = resultSet.getObject(1, BigInteger.class);
            }
            keyPair.privateKey = privateexp;
            keyPair.modulus = privatemod;
            keyPair.publicKey = new BigInteger(String.valueOf(3));
        } else {
            keyPair = rsaSetUp();
            insert();
        }
    }

    public static BigInteger[] enrypt(String msg) {
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        int len = msgBytes.length;
        BigInteger[] encryptedMsg = new BigInteger[len];
        BigInteger m = null;
        BigInteger c = null;

        for (int i = 0; i < len; i++) {
            m = new BigInteger(String.valueOf(msgBytes[i]));
            c = m.modPow(keyPair.publicKey, keyPair.modulus);
            encryptedMsg[i] = c;
        }
        return encryptedMsg;
    }

     public static String decrypt(BigInteger[] msgBytes) {
         int len = msgBytes.length;
         byte[] bytearr = new byte[len];
         for (int i = 0; i < len; i++) {
             msgBytes[i] = msgBytes[i].modPow(keyPair.privateKey, keyPair.modulus);
         }
         for (int i = 0; i < len; i++) {
             bytearr[i] = (byte) msgBytes[i].intValueExact();
         }
         String msg = new String(bytearr, StandardCharsets.UTF_8);
         return msg;
     }
}
