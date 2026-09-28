import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private double cash;
    private double startingCash;
    private HashMap<String, Integer> holdings = new HashMap<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<Double> valueHistory = new ArrayList<>();

    public User(String name, double startingCash) {
        this.name = name;
        this.cash = startingCash;
        this.startingCash = startingCash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public HashMap<String, Integer> getHoldings() {
        return holdings;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Double> getValueHistory() {
        return valueHistory;
    }

    // Buys shares. Returns false if quantity is wrong or cash is not enough.
    public boolean buy(Stock stock, int quantity, int day) {
        if (quantity <= 0) {
            return false;
        }
        double cost = stock.getPrice() * quantity;
        if (cost > cash) {
            return false;
        }
        cash -= cost;
        String symbol = stock.getSymbol();
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        transactions.add(new Transaction(day, "BUY", symbol, quantity, stock.getPrice()));
        return true;
    }

    // Sells shares. Returns false if the user does not own enough shares.
    public boolean sell(Stock stock, int quantity, int day) {
        String symbol = stock.getSymbol();
        int owned = holdings.getOrDefault(symbol, 0);
        if (quantity <= 0 || quantity > owned) {
            return false;
        }
        cash += stock.getPrice() * quantity;
        if (owned - quantity == 0) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, owned - quantity);
        }
        transactions.add(new Transaction(day, "SELL", symbol, quantity, stock.getPrice()));
        return true;
    }

    // Cash + value of all shares at current market prices
    public double getPortfolioValue(Market market) {
        double total = cash;
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            Stock s = market.findStock(e.getKey());
            if (s != null) {
                total += s.getPrice() * e.getValue();
            }
        }
        return total;
    }

    // Saves today's portfolio value (used for tracking performance)
    public void recordValue(Market market) {
        valueHistory.add(getPortfolioValue(market));
    }

    public void showPortfolio(Market market) {
        System.out.println("\n--- Portfolio of " + name + " ---");
        System.out.printf("Cash balance: $%.2f%n", cash);
        if (holdings.isEmpty()) {
            System.out.println("You do not own any stocks yet.");
        } else {
            System.out.printf("%-8s %-10s %-12s %-12s%n", "Symbol", "Quantity", "Price", "Value");
            for (Map.Entry<String, Integer> e : holdings.entrySet()) {
                Stock s = market.findStock(e.getKey());
                if (s != null) {
                    System.out.printf("%-8s %-10d $%-11.2f $%-11.2f%n", e.getKey(),
                            e.getValue(), s.getPrice(), s.getPrice() * e.getValue());
                }
            }
        }
        double total = getPortfolioValue(market);
        double profitLoss = total - startingCash;
        System.out.printf("Total portfolio value: $%.2f%n", total);
        System.out.printf("Profit/Loss: %+.2f (%+.2f%%)%n", profitLoss,
                profitLoss / startingCash * 100);
    }

    public void showTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            t.print();
        }
    }

    public void showPerformance() {
        if (valueHistory.isEmpty()) {
            System.out.println("No performance data yet.");
            return;
        }
        System.out.println("\n--- Portfolio Performance Over Time ---");
        for (int i = 0; i < valueHistory.size(); i++) {
            double value = valueHistory.get(i);
            double change = value - startingCash;
            System.out.printf("Day %-3d Value: $%-12.2f Change: %+.2f%n", i + 1, value, change);
        }
    }
}
