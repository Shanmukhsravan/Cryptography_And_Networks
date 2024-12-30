import java.util.Scanner;
public class HillCipher {
    public static int[][] inverseMatrix(int[][] matrix) {
        int n = matrix.length;
        int det = determinant(matrix, n);
        int invDet = modInverse(det, 26);
        int[][] adj = adjoint(matrix);
        int[][] inv = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv[i][j] = (invDet * adj[i][j]) % 26;
                if (inv[i][j] < 0) inv[i][j] += 26;  
            }
        }
        return inv;
    }
    public static int determinant(int[][] matrix, int n) {
        if (n == 1) {
            return matrix[0][0];
        }
        int det = 0;
        int sign = 1;
        for (int f = 0; f < n; f++) {
            int[][] temp = new int[n - 1][n - 1];
            for (int i = 1; i < n; i++) {
                int colIndex = 0;
                for (int j = 0; j < n; j++) {
                    if (j == f) continue;
                    temp[i - 1][colIndex++] = matrix[i][j];
                }
            }
            det += sign * matrix[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }
        return det % 26;
    }
    public static int[][] adjoint(int[][] matrix) {
        int n = matrix.length;
        int[][] adj = new int[n][n];
        if (n == 1) {
            adj[0][0] = 1;
            return adj;
        }
        int sign = 1;
        int[][] temp = new int[n - 1][n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getCofactor(matrix, temp, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = sign * determinant(temp, n - 1) % 26;
                if (adj[j][i] < 0) adj[j][i] += 26;
            }
        }
        return adj;
    }
    public static void getCofactor(int[][] matrix, int[][] temp, int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = matrix[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }
    public static String encrypt(String text, int[][] keyMatrix) {
        int n = keyMatrix.length;
        StringBuilder ciphertext = new StringBuilder();
        while (text.length() % n != 0) {
            text += "X"; 
        }
        for (int i = 0; i < text.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = text.charAt(i + j) - 'A'; 
            }
            int[] encryptedBlock = new int[n];
            for (int j = 0; j < n; j++) {
                encryptedBlock[j] = 0;
                for (int k = 0; k < n; k++) {
                    encryptedBlock[j] += keyMatrix[j][k] * block[k];
                }
                encryptedBlock[j] %= 26;
                ciphertext.append((char) (encryptedBlock[j] + 'A'));
            }
        }
        return ciphertext.toString();
    }
    public static String decrypt(String ciphertext, int[][] keyInverseMatrix) {
        int n = keyInverseMatrix.length;
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = ciphertext.charAt(i + j) - 'A'; 
            }
            int[] decryptedBlock = new int[n];
            for (int j = 0; j < n; j++) {
                decryptedBlock[j] = 0;
                for (int k = 0; k < n; k++) {
                    decryptedBlock[j] += keyInverseMatrix[j][k] * block[k];
                }
                decryptedBlock[j] %= 26;
                if (decryptedBlock[j] < 0) decryptedBlock[j] += 26; 
                plaintext.append((char) (decryptedBlock[j] + 'A'));
            }
        }
        return plaintext.toString();
    }
    public static int[][] stringToMatrix(String key, int n) {
        int[][] matrix = new int[n][n];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = key.charAt(index++) - 'A';
            }
        }
        return matrix;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size of the key matrix (n x n): ");
        int n = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter the key string (length should be " + (n * n) + " characters): ");
        String key = sc.nextLine().toUpperCase().replaceAll("[^A-Z]", ""); 
        if (key.length() != n * n) {
            System.out.println("Key length should be exactly " + (n * n) + " characters!");
            return;
        }
        int[][] keyMatrix = stringToMatrix(key, n);
        int[][] inverseMatrix = inverseMatrix(keyMatrix);
        System.out.print("Enter plaintext (only letters A-Z): ");
        String plaintext = sc.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
        String ciphertext = encrypt(plaintext, keyMatrix);
        System.out.println("Encrypted Text: " + ciphertext);
        String decryptedText = decrypt(ciphertext, inverseMatrix);
        System.out.println("Decrypted Text: " + decryptedText);
        sc.close();
    }
}