import services.InformationProcessor;
import services.InventoryManager;
import services.BillingAndTransactionManager;
import services.ReportGenerator;
import services.TableViewer;

import java.util.Scanner;

public class MainMenu {
    static Scanner sc = new Scanner(System.in);

    public static void run() {
        boolean flag = true;

        while (flag) {
            System.out.println("\nSelect your Task:");
            System.out.println("1 : Information processing");
            System.out.println("2 : Maintaining inventory records");
            System.out.println("3 : Maintaining billing and transaction records");
            System.out.println("4 : Reports");
            System.out.println("5 : Display Data");

            System.out.print("Please enter the input (1-5): ");
            int option = sc.nextInt();

            switch (option) {
                case 1 -> informationMenu();
                case 2 -> InventoryManager.transferInventory();
                case 3 -> BillingAndTransactionManager.generateSupplierBillBySupplierId();
                case 4 -> reportMenu();
                case 5 -> TableViewer.displayTable();
                default -> System.out.println("Please enter a valid input!");
            }

            System.out.print("Do you want to update anything else? [y/n]: ");
            char c = sc.next().charAt(0);
            if (c == 'n' || c == 'N') flag = false;
        }
    }

    private static void informationMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Information Menu ====");
            System.out.println("1: Add Store");
            System.out.println("2: Add Customer");
            System.out.println("0: Exit");
            System.out.print("Enter your choice: ");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> InformationProcessor.addStore();
                case 2 -> InformationProcessor.addCustomer();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void reportMenu() {
        System.out.print("Enter the date (YYYY-MM-DD) for daily sales report: ");
        String date = sc.next();
        ReportGenerator.getSalesByDay(date);
    }
}
