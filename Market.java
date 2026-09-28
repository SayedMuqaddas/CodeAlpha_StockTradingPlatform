import java.util.ArrayList;

public class Market {
    private ArrayList<Stock> stocks = new ArrayList<>();
    private int day = 1;

    public Market() {
        stocks.add(new Stock("AAPL", "Apple Inc.", 180.00));
        stocks.add(new Stock("TSLA", "Tesla Inc.", 250.00));
        stocks.add(new Stock("GOOG", "Google (Alphabet)", 140.00));
        stocks.add(new Stock("AMZN", "Amazon.com", 130.00));
        stocks.add(new Stock("MSFT", "Microsoft Corp.", 330.00));
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Stock findStock(String symbol) {
        for (Stock s : stocks) {
            if (s.getSymbol().equalsIgnoreCase(symbol)) {
                return s;
            }
        }
        return null;
    }

    // Moves the market to the next day and changes all prices
    public void nextDay() {
        for (Stock s : stocks) {
            s.updatePrice();
        }
        day++;
    }

    public void displayMarket() {
        System.out.println("\n--- Market Data (Day " + day + ") ---");
        System.out.printf("%-8s %-20s %-12s %-10s%n", "Symbol", "Company", "Price", "Change");
        System.out.println("---------------------------------------------------");
        for (Stock s : stocks) {
            System.out.printf("%-8s %-20s $%-11.2f %+.2f%%%n",
                    s.getSymbol(), s.getName(), s.getPrice(), s.getChangePercent());
        }
    }
}
