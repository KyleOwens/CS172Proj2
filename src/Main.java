import Characters.MainCharacter;
import GameLogic.GameManager;
import Saving.Loader;

import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static GameManager gameManager;

    public static void main(String[] args) {
        int menuSelection;
        scanner = new Scanner(System.in);
        Loader loader = new Loader();

        printMainMenu();

        menuSelection = getUserInput();

        if (menuSelection == 1) {
            gameManager = new GameManager();
            gameManager.startGame();
        } else if (menuSelection == 2) {
            MainCharacter mc = loader.loadFile();
            if(mc != null){
                gameManager = new GameManager(mc);
            } else {
                gameManager = new GameManager();
            }
            loader.close();
            gameManager.startGame();
        }
    }

    public static void printMainMenu() {
        System.out.println("1) New Game \n2) Load Game \n3) Exit");
    }

    public static int getUserInput() {
        return Integer.parseInt(scanner.nextLine());
    }
}
