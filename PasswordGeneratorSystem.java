import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.io.*;
import java.util.Base64;

public class PasswordGeneratorSystem {

    static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String lower = "abcdefghijklmnopqrstuvwxyz";
    static String numbers = "0123456789";
    static String symbols = "!@#$%^&*()_-+=<>?/";

    static String allChars = upper + lower + numbers + symbols;


    static String secretKey = "1234567890abcdef"; 

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter desired password length: ");
        int length = scanner.nextInt();

        String password = generatePassword(length);
        System.out.println("Generated Password: " + password);

        String strength = checkPasswordStrength(password);
        System.out.println("Password Strength: " + strength);

        displayUsefulInfo();

        String encrypted = encrypt(password, secretKey);
        System.out.println("Encrypted Password: " + encrypted);

        saveToFile("passwords.txt", encrypted);
        System.out.println("Encrypted password saved to passwords.txt");
    }

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(allChars.length());
            password.append(allChars.charAt(index));
        }
        return password.toString();
    }

    public static String checkPasswordStrength(String password) {
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSymbol = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else hasSymbol = true;
        }

        int strengthScore = 0;
        if (hasUpper) strengthScore++;
        if (hasLower) strengthScore++;
        if (hasDigit) strengthScore++;
        if (hasSymbol) strengthScore++;

        if (password.length() < 6) return "Weak";
        else if (strengthScore == 4) return "Strong";
        else return "Medium";
    }

    public static void displayUsefulInfo() {
        System.out.println("\nTips:");
        System.out.println("- Use 8+ characters");
        System.out.println("- Mix uppercase, lowercase, numbers, and symbols");
        System.out.println("- Don’t reuse old passwords");
        System.out.println("- Never share passwords with others");
    }

    public static String encrypt(String password, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static void saveToFile(String filename, String data) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e);
        }
    }
}
