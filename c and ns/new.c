#include <stdio.h>
#include <string.h>

void main() {
    char plaintext[] = "Hello World"; 
    char encrypted[50];              
    char decrypted[50];              
    int i, len;

    len = strlen(plaintext);
    char key = 127;

    printf("Plaintext: %s\n", plaintext);
    for (i = 0; i < len; i++) {
        encrypted[i] = plaintext[i] ^ key;
    }
    encrypted[len] = '\0';

    printf("Encrypted text: ");
    for (i = 0; i < len; i++) {
        printf("%c", encrypted[i]);
    }
    printf("\n");

    for (i = 0; i < len; i++) {
        decrypted[i] = encrypted[i] ^ key;
    }
    decrypted[len] = '\0'; 

    printf("Decrypted text: %s\n", decrypted);
}
