import java.util.*;
public class PlayfairCipher {
    private static final int SIZE = 5; 
    private static char[][] keyMatrix = new char[SIZE][SIZE];
    private static String key = "ldrp";
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the plaintext (only alphabets): ");
            String plaintext = scanner.nextLine().toLowerCase().replaceAll("[^a-z]", "");
            generateKeyMatrix(key);
            String ciphertext = encrypt(plaintext);
            System.out.println("Ciphertext: " + ciphertext);
        } finally {
            scanner.close(); 
        }
    }
    private static void generateKeyMatrix(String key) {
        Set<Character> usedChars = new HashSet<>();
        StringBuilder keyWithDuplicatesRemoved = new StringBuilder();
        for (char ch : key.toLowerCase().toCharArray()) {
            if (!usedChars.contains(ch) && ch != 'j') {
                usedChars.add(ch);
                keyWithDuplicatesRemoved.append(ch);
            }
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (!usedChars.contains(ch) && ch != 'j') { // Exclude 'j'
                keyWithDuplicatesRemoved.append(ch);
            }
        }
        int k = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                keyMatrix[i][j] = keyWithDuplicatesRemoved.charAt(k++);
            }
        }
    }
    private static String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        plaintext = preparePlaintext(plaintext);
        for (int i = 0; i < plaintext.length(); i += 2) {
            char firstChar = plaintext.charAt(i);
            char secondChar = plaintext.charAt(i + 1);
            int[] firstPos = getPosition(firstChar);
            int[] secondPos = getPosition(secondChar);

            if (firstPos[0] == secondPos[0]) {
                ciphertext.append(keyMatrix[firstPos[0]][(firstPos[1] + 1) % SIZE]);
                ciphertext.append(keyMatrix[secondPos[0]][(secondPos[1] + 1) % SIZE]);
            } else if (firstPos[1] == secondPos[1]) { 
                ciphertext.append(keyMatrix[(firstPos[0] + 1) % SIZE][firstPos[1]]);
                ciphertext.append(keyMatrix[(secondPos[0] + 1) % SIZE][secondPos[1]]);
            } else { 
                ciphertext.append(keyMatrix[firstPos[0]][secondPos[1]]);
                ciphertext.append(keyMatrix[secondPos[0]][firstPos[1]]);
            }
        }
        return ciphertext.toString();
    }
    private static String preparePlaintext(String plaintext) {
        StringBuilder preparedText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);
            preparedText.append(ch);
            if (i + 1 < plaintext.length() && plaintext.charAt(i) == plaintext.charAt(i + 1)) {
                preparedText.append('x'); 
            }
        }
        if (preparedText.length() % 2 != 0) {
            preparedText.append('x');
        }
        return preparedText.toString();
    }
    private static int[] getPosition(char ch) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (keyMatrix[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character " + ch + " not found in key matrix.");
    }
}
