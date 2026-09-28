
public class Stock {
    private String symbol;
    private String name;
    private double price;
    private double previousPrice;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.previousPrice = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    // Used when loading saved data from a file
    public void setPrices(double price, double previousPrice) {
        this.price = price;
        this.previousPrice = previousPrice;
    }

    // Changes the price randomly between -5% and +5%
    public void updatePrice() {
        previousPrice = price;
        double changePercent = (Math.random() * 10) - 5;
        double newPrice = price + price * changePercent / 100;
        if (newPrice < 1) {
            newPrice = 1;
        }
        price = Math.round(newPrice * 100.0) / 100.0;
    }

    public double getChangePercent() {
        if (previousPrice == 0) {
            return 0;
        }
        return (price - previousPrice) / previousPrice * 100;
    }
}