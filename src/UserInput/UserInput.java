package UserInput;

import java.util.Scanner;

public class UserInput {
    private static Scanner scanner = new Scanner(System.in);

    public static int getUserInput(){
        return Integer.parseInt(scanner.nextLine());
    }
}
