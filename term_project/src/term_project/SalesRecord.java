package term_project;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SalesRecord {
    private Map<LocalDate, Map<String, Integer>> dailySales;
    private Map<String, Integer> monthlySales;

    public SalesRecord() {
        dailySales = new HashMap<>();
        monthlySales = new HashMap<>();
    }

    public void recordSale(String productName, int quantity) {
        LocalDate today = LocalDate.now();
        dailySales.putIfAbsent(today, new HashMap<>());
        dailySales.get(today).put(productName, dailySales.get(today).getOrDefault(productName, 0) + quantity);

        String month = today.getMonth().toString();
        monthlySales.put(month, monthlySales.getOrDefault(month, 0) + quantity);
    }

    public Map<LocalDate, Map<String, Integer>> getDailySales() {
        return dailySales;
    }

    public Map<String, Integer> getMonthlySales() {
        return monthlySales;
    }
}
