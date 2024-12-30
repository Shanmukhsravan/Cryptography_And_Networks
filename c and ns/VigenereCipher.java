public class VigenereCipher {
    public static String generateKey(String text, String key) {
        int textLength = text.length();
        StringBuilder extendedKey = new StringBuilder(key);
        for (int i = 0; ; i++) {
            if (textLength == i)
                i = 0;
            if (extendedKey.length() == textLength)
                break;
            extendedKey.append(key.charAt(i));
        }
        return extendedKey.toString();
    }
    public static String encrypt(String text, String key) {
        StringBuilder cipherText = new StringBuilder();
        key = generateKey(text, key);

        for (int i = 0; i < text.length(); i++) {
            char x = (char) (((text.charAt(i) + key.charAt(i)) % 26) + 'A');
            cipherText.append(x);
        }
        return cipherText.toString();
    }
    public static String decrypt(String cipherText, String key) {
        StringBuilder plainText = new StringBuilder();
        key = generateKey(cipherText, key);

        for (int i = 0; i < cipherText.length(); i++) {
            char x = (char) (((cipherText.charAt(i) - key.charAt(i) + 26) % 26) + 'A');
            plainText.append(x);
        }
        return plainText.toString();
    }

    public static void main(String[] args) {
        String text = "HELLO";
        String key = "KEY";
        
        String encryptedText = encrypt(text, key);
        String decryptedText = decrypt(encryptedText, key);

        System.out.println("Original: " + text);
        System.out.println("Key: " + key);
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("Decrypted: " + decryptedText);
    }
}
