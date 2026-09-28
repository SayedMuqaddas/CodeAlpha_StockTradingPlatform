import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class DataManager {
    private static final String FILE_NAME = "portfolio.txt";

    public static void save(User user, Market market) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println("NAME," + user.getName());
            writer.println("CASH," + user.getCash());
            writer.println("DAY," + market.getDay());
            for (Stock s : market.getStocks()) {
                writer.println("STOCK," + s.getSymbol() + "," + s.getPrice()
                        + "," + s.getPreviousPrice());
            }
            for (Map.Entry<String, Integer> e : user.getHoldings().entrySet()) {
                writer.println("HOLDING," + e.getKey() + "," + e.getValue());
            }
            for (double v : user.getValueHistory()) {
                writer.println("HISTORY," + v);
            }
            for (Transaction t : user.getTransactions()) {
                writer.println("TRANS," + t.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Returns true if saved data was found and loaded
    public static boolean load(User user, Market market) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", 2);
                String key = parts[0];
                String value = parts.length > 1 ? parts[1] : "";

                switch (key) {
                    case "NAME":
                        user.setName(value);
                        break;
                    case "CASH":
                        user.setCash(Double.parseDouble(value));
                        break;
                    case "DAY":
                        market.setDay(Integer.parseInt(value));
                        break;
                    case "STOCK": {
                        String[] s = value.split(",");
                        Stock stock = market.findStock(s[0]);
                        if (stock != null) {
                            stock.setPrices(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
                        }
                        break;
                    }
                    case "HOLDING": {
                        String[] h = value.split(",");
                        user.getHoldings().put(h[0], Integer.parseInt(h[1]));
                        break;
                    }
                    case "HISTORY":
                        user.getValueHistory().add(Double.parseDouble(value));
                        break;
                    case "TRANS":
                        user.getTransactions().add(Transaction.fromFileString(value));
                        break;
                    default:
                        break;
                }
            }
            return true;
        } catch (IOException | RuntimeException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return false;
        }
    }
}
