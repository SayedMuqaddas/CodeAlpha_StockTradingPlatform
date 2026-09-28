# CodeAlpha_StockTradingPlatform
Java console app to simulate stock trading with portfolio tracking and file storage
# Stock Trading Platform

A console-based Java application that simulates a basic stock trading environment. Users can view market data, buy and sell stocks, and track how their portfolio performs over time.

## Description
This project simulates a small stock market with five companies. Stock prices change randomly every trading day. A user starts with $10,000, buys and sells shares, and can see the portfolio value, profit or loss and the performance history. All data is saved in a file, so the account is still there the next time the program runs.

## Features
- Market data display with current price and daily change (%)
- Buy and sell stocks
- Portfolio view with cash balance, holdings, total value and profit/loss
- Transaction history of all buy and sell operations
- Portfolio performance tracking over time (value for each day)
- "Next day" feature that changes all stock prices randomly (-5% to +5%)
- Input validation (not enough cash, not enough shares, wrong input)
- Data is saved to and loaded from a file (File I/O)

## Technologies Used
- Java
- Object-Oriented Programming (classes, objects, encapsulation)
- ArrayList and HashMap (Collections)
- File I/O (BufferedReader, FileWriter)

## Project Structure
- `Stock.java` - represents a stock and updates its price
- `Market.java` - holds all stocks and manages trading days
- `Transaction.java` - represents one buy or sell record
- `User.java` - user account with cash, holdings, transactions and history
- `DataManager.java` - saves and loads data from a file
- `Main.java` - console menu and user interaction

## How to Run
1. Clone this repository:
   `git clone https://github.com/SayedMuqaddas/CodeAlpha_StockTradingPlatform.git`
2. Open the folder in any Java IDE (VS Code, IntelliJ, Eclipse)
3. Run `Main.java`

Or from the terminal:
`javac *.java`
`java Main`

## Menu
```
===== Stock Trading Platform =====
1. View market data
2. Buy stock
3. Sell stock
4. View my portfolio
5. View transaction history
6. View portfolio performance
7. Move to next day
8. Exit
```

## Note
Your account data is stored in `portfolio.txt`, which is created automatically. Delete this file to start again with a fresh account.

Made during the CodeAlpha Java Programming Internship.
