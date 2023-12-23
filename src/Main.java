public class Main {

    public static void dbconnect() {
        boolean state = db.establishConnection();
        if (state == true) {
            System.out.println("YES");
        }
        else{
            System.out.println("NO");
        }
    }
    
    public static void main(String[] args) {
//        // file handler
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("encryptKeys.txt"))) {
//            writer.write("The value of private exponent d: " + keyPair.d + "\n");
//            writer.write("The value of public exponent d: " + keyPair.e + "\n");
//        } catch (IOException e) {
//            System.out.println("Error Occurred: " + e);
//        }
        dbconnect();
        // String msg = "Hello World";
        // // Cryptit.enrypt(msg);
    }

}
