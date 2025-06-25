import java.util.Scanner;

// Runnable Project class  
public class RunFile {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
       
        System.out.println("\nByte Me!\n");
        System.out.println("1. Enter the Application");
        System.out.println("2. Exit the Application");
        System.out.print("Input: ");
        int MainInput = Integer.parseInt(scan.nextLine());
        System.out.println();
        
        if (MainInput == 1) {
            System.out.println("1. CLI Mode");
            System.out.println("2. GUI Mode");
            System.out.print("Input: ");
            int interface_input=Integer.parseInt(scan.nextLine());
            if (interface_input==1) {
                // Start CLI functionalities
                AppFunctionalites functions = new AppFunctionalites();
                functions.start();
            }
            else if (interface_input==2) {
                // Start GUI functionalities
                FileOperations.loadMenuFromFile();
                FileOperations.loadOrdersFromFile();
                javafx.application.Application.launch(GUIMode_OpeningScreen.class);
            }
        } else if (MainInput == 2) {
            System.exit(0);
        }
    }
}