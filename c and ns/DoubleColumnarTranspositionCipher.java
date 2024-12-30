import java.util.Arrays;
import java.util.Scanner;

public class DoubleColumnarTranspositionCipher {

    public static String singleTransposition(String text, String key) {
        int[] keyOrder = getKeyOrder(key);
        int rows = (int) Math.ceil((double) text.length() / key.length());
        char[][] grid = new char[rows][key.length()];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.length(); j++) {
                grid[i][j] = (index < text.length()) ? text.charAt(index++) : 'X';
            }
        }
        StringBuilder result = new StringBuilder();
        for (int col : keyOrder) {
            for (int i = 0; i < rows; i++) {
                result.append(grid[i][col]);
            }
        }

        return result.toString();
    }
    public static String singleDecryption(String text, String key) {
        int[] keyOrder = getKeyOrder(key);
        int rows = text.length() / key.length();
        char[][] grid = new char[rows][key.length()];
        int index = 0;
        for (int col : keyOrder) {
            for (int i = 0; i < rows; i++) {
                grid[i][col] = text.charAt(index++);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.length(); j++) {
                result.append(grid[i][j]);
            }
        }

        return result.toString();
    }
    private static int[] getKeyOrder(String key) {
        int[] order = new int[key.length()];
        Character[] chars = new Character[key.length()];
        for (int i = 0; i < key.length(); i++) chars[i] = key.charAt(i);
        Arrays.sort(chars, (a, b) -> a.compareTo(b));
        for (int i = 0; i < chars.length; i++) order[i] = key.indexOf(chars[i]);
        return order;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the plaintext: ");
        String plaintext = sc.nextLine();
        System.out.print("Enter the first key: ");
        String key1 = sc.nextLine();
        System.out.print("Enter the second key: ");
        String key2 = sc.nextLine();
        String intermediateCipher = singleTransposition(plaintext, key1);
        String finalCipher = singleTransposition(intermediateCipher, key2);
        System.out.println("Ciphertext: " + finalCipher);
        String intermediatePlain = singleDecryption(finalCipher, key2);
        String originalPlain = singleDecryption(intermediatePlain, key1);
        System.out.println("Decrypted Text: " + originalPlain);
        sc.close();
    }
}