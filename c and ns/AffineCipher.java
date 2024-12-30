public class AffineCipher {
    static final int a = 3; 
    static final int b = 12; 
    static final int m = 26; 

    public static String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (char character : plaintext.toUpperCase().toCharArray()) {
            if (Character.isLetter(character)) {
                int x = character - 'A';
                int encryptedChar = (a * x + b) % m;
                ciphertext.append((char) (encryptedChar + 'A'));
            } else {
                ciphertext.append(character);
            }
        }
        return ciphertext.toString();
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("Inverse does not exist.");
    }

    public static String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        int a_inv = modInverse(a, m); // Modular inverse of a under modulo m
        for (char character : ciphertext.toUpperCase().toCharArray()) {
            if (Character.isLetter(character)) {
                int y = character - 'A';
                int decryptedChar = (a_inv * (y - b + m)) % m;
                plaintext.append((char) (decryptedChar + 'A'));
            } else {
                plaintext.append(character);
            }
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        String plaintext = "HELLO WORLD";
        String encryptedText = encrypt(plaintext);
        String decryptedText = decrypt(encryptedText);

        System.out.println("Original: " + plaintext);
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("Decrypted: " + decryptedText);
    }
}
