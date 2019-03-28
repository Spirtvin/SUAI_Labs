package Main;

import Binary.Binary;
import Encryption.SHA.SHA1;
import Encryption.SHA.SHA1_1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SHA1 sha1 = new SHA1();
        try {
            Scanner scanner = new Scanner(System.in);
            String inputStr = scanner.nextLine();
            String shaCustomStr = sha1.GetHash(inputStr).toStringHex();
            String shaOriginalStr = SHA1_1.encodeHex(inputStr);
            System.out.println("Результат:" + shaCustomStr.toLowerCase());
            System.out.println("Проверка: " + shaOriginalStr.toLowerCase());
            System.out.println("Проверка пройдена:" + shaCustomStr.toLowerCase().equals(shaOriginalStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
