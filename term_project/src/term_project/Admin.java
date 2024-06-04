package term_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
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
        System.out.println(getDailySalesData());
    }

    public void printMonthlySales() {
        System.out.println(getMonthlySalesData());
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

    public void refillCoins(VendingMachine vendingMachine, int denomination, int count) {
        vendingMachine.refillCoins(denomination, count);
    }

    public void changeProductDetails(VendingMachine vendingMachine, String oldProductName, String newProductName, int newPrice) {
        vendingMachine.changeProductDetails(oldProductName, newProductName, newPrice);
    }

    public String getDailySalesData() {
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, Integer>> dailySales = readDailySales();
        for (Map.Entry<String, Map<String, Integer>> entry : dailySales.entrySet()) {
            sb.append("Date: ").append(entry.getKey()).append("\n");
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                sb.append("음료: ").append(productEntry.getKey()).append("\t매출: ").append(productEntry.getValue()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getMonthlySalesData() {
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, Integer>> monthlySales = readMonthlySales();
        for (Map.Entry<String, Map<String, Integer>> entry : monthlySales.entrySet()) {
            sb.append(entry.getKey()).append("\n");
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                sb.append("음료: ").append(productEntry.getKey()).append("\t매출: ").append(productEntry.getValue()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Map<String, Map<String, Integer>> readDailySales() {
        Map<String, Map<String, Integer>> dailySales = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/daily.txt"))) {
            String line;
            String currentDate = null;
            while ((line = br.readLine()) != null) {
                if (line.matches("\\d{8}")) { // 날짜 형식
                    currentDate = line;
                    dailySales.put(currentDate, new LinkedHashMap<>());
                } else if (currentDate != null) {
                    String[] parts = line.split(",");
                    String product = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    dailySales.get(currentDate).put(product, quantity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dailySales;
    }

    private Map<String, Map<String, Integer>> readMonthlySales() {
        Map<String, Map<String, Integer>> monthlySales = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/month.txt"))) {
            String line;
            String currentMonth = null;
            while ((line = br.readLine()) != null) {
                if (line.matches("\\w+")) { // 월 형식
                    currentMonth = line;
                    monthlySales.put(currentMonth, new LinkedHashMap<>());
                } else if (currentMonth != null) {
                    String[] parts = line.split(",");
                    String product = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    monthlySales.get(currentMonth).put(product, quantity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monthlySales;
    }
}
