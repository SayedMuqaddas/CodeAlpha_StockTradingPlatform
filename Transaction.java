public class Transaction {
    private int day;
    private String type; // BUY or SELL
    private String symbol;
    private int quantity;
    private double price;

    public Transaction(int day, String type, String symbol, int quantity, double price) {
        this.day = day;
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotal() {
        return quantity * price;
    }

    // One line of text for saving in a file
    public String toFileString() {
        return day + "," + type + "," + symbol + "," + quantity + "," + price;
    }

    // Creates a transaction back from a line of the file
    public static Transaction fromFileString(String line) {
        String[] p = line.split(",");
        return new Transaction(
                Integer.parseInt(p[0]),
                p[1],
                p[2],
                Integer.parseInt(p[3]),
                Double.parseDouble(p[4]));
    }

    public void print() {
        System.out.printf("Day %-3d %-5s %-6s x%-5d @ $%-9.2f Total: $%.2f%n",
                day, type, symbol, quantity, price, getTotal());
    }
}
