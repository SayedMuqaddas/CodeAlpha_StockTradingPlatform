
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Market market = new Market();
        User user = new User("Trader", 10000.00);

        boolean loaded = DataManager.load(user, market);
        if (loaded) {
            System.out.println("Welcome back, " + user.getName() + "! Your saved data was loaded.");
        } else {
            System.out.print("Welcome! Enter your name: ");
            String name = sc.nextLine().replace(",", " ").trim();
            if (name.isEmpty()) {
                name = "Trader";
            }
            user.setName(name);
            user.recordValue(market);
            DataManager.save(user, market);
            System.out.printf("Account created with a starting balance of $%.2f%n", user.getCash());
        }

        int choice;
        do {
            System.out.println("\n===== Stock Trading Platform =====");
            System.out.println("1. View market data");
            System.out.println("2. Buy stock");
            System.out.println("3. Sell stock");
            System.out.println("4. View my portfolio");
            System.out.println("5. View transaction history");
            System.out.println("6. View portfolio performance");
            System.out.println("7. Move to next day");
            System.out.println("8. Exit");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    market.displayMarket();
                    break;
                case 2:
                    buyStock(market, user);
                    break;
                case 3:
                    sellStock(market, user);
                    break;
                case 4:
                    user.showPortfolio(market);
                    break;
                case 5:
                    user.showTransactions();
                    break;
                case 6:
                    user.showPerformance();
                    break;
                case 7:
                    market.nextDay();
                    user.recordValue(market);
                    DataManager.save(user, market);
                    System.out.println("A new trading day has started. Prices have changed.");
                    market.displayMarket();
                    break;
                case 8:
                    DataManager.save(user, market);
                    System.out.println("Your data has been saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);

        sc.close();
    }

    private static void buyStock(Market market, User user) {
        market.displayMarket();
        System.out.printf("Available cash: $%.2f%n", user.getCash());
        System.out.print("Enter stock symbol to buy: ");
        Stock stock = market.findStock(sc.nextLine().trim());
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        int quantity = readInt("Enter quantity to buy: ");
        if (user.buy(stock, quantity, market.getDay())) {
            System.out.printf("Bought %d shares of %s for $%.2f%n",
                    quantity, stock.getSymbol(), quantity * stock.getPrice());
            DataManager.save(user, market);
        } else {
            System.out.println("Purchase failed. Check the quantity and your cash balance.");
        }
    }

    private static void sellStock(Market market, User user) {
        user.showPortfolio(market);
        System.out.print("Enter stock symbol to sell: ");
        Stock stock = market.findStock(sc.nextLine().trim());
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        int quantity = readInt("Enter quantity to sell: ");
        if (user.sell(stock, quantity, market.getDay())) {
            System.out.printf("Sold %d shares of %s for $%.2f%n",
                    quantity, stock.getSymbol(), quantity * stock.getPrice());
            DataManager.save(user, market);
        } else {
            System.out.println("Sale failed. You do not own enough shares.");
        }
    }

    // Reads a number safely (no crash if user types text)
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}