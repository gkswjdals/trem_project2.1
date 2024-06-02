package term_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Admin {
    private String password;
    private SalesRecord salesRecord;

    public Admin(String password) {
        this.password = password;
        this.salesRecord = new SalesRecord();
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean checkPassword(String inputPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/password.txt"))) {
            String filePassword = reader.readLine().trim();
            return inputPassword.equals(filePassword);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void recordSale(String productName, int quantity) {
        salesRecord.recordSale(productName, quantity);
    }

    public void printDailySales() {
        Map<LocalDate, Map<String, Integer>> dailySales = salesRecord.getDailySales();
        for (Map.Entry<LocalDate, Map<String, Integer>> entry : dailySales.entrySet()) {
            System.out.println("Date: " + entry.getKey());
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                System.out.println("Product: " + productEntry.getKey() + ", Quantity: " + productEntry.getValue());
            }
        }
    }

    public void printMonthlySales() {
        Map<String, Integer> monthlySales = salesRecord.getMonthlySales();
        for (Map.Entry<String, Integer> entry : monthlySales.entrySet()) {
            System.out.println("Month: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public void refillStock(VendingMachine vendingMachine, String productName, int amount) {
        vendingMachine.refillStock(productName, amount);
    }

    public void checkCoinStatus(VendingMachine vendingMachine) {
        vendingMachine.printCoinStatus();
    }

    public void collectCoins(VendingMachine vendingMachine) {
        vendingMachine.collectCoins();
    }
}
