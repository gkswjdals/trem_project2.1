package main;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendingMachine {
    private List<Product> products; // 자판기에서 판매하는 제품 목록
    private List<Coin> coins; // 자판기에 있는 동전 목록
    private int currentAmount; // 현재 투입된 금액
    private Map<String, Integer> dailySales; // 일별 매출을 저장하는 맵

    // 기본 생성자: 초기 동전 개수를 10으로 설정
    public VendingMachine() {
        this(10);
    }

    // 생성자: 초기 동전 개수를 받아서 자판기 초기화
    public VendingMachine(int initialCoinCount) {
        products = new ArrayList<>();
        coins = new ArrayList<>();
        dailySales = new HashMap<>();
        initializeProducts(); // 제품 초기화
        initializeCoins(initialCoinCount); // 동전 초기화
        new Admin("admin123!");
    }

    // 동전을 초기화하는 메서드
    private void initializeCoins(int initialCoinCount) {
        coins.add(new Coin(10, initialCoinCount));
        coins.add(new Coin(50, initialCoinCount));
        coins.add(new Coin(100, initialCoinCount));
        coins.add(new Coin(500, initialCoinCount));
        coins.add(new Coin(1000, initialCoinCount));
    }

    // 제품을 초기화하는 메서드
    private void initializeProducts() {
        products.add(new Product("water", "물", 450, 10));
        products.add(new Product("coffee", "커피", 500, 10));
        products.add(new Product("sport", "이온음료", 550, 10));
        products.add(new Product("high", "고급커피", 700, 10));
        products.add(new Product("Soda", "탄산음료", 750, 10));
        products.add(new Product("Special", "특화음료", 800, 10));
    }

    // 동전을 투입하는 메서드
    public void insertCoin(int denomination) {
        for (Coin coin : coins) {
            if (coin.getDenomination() == denomination) {
                coin.addCount(1);
                currentAmount += denomination;
                break;
            }
        }
    }

    // 제품을 선택하는 메서드
    public boolean selectProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName) && !product.isOutOfStock() && currentAmount >= product.getPrice()) {
                product.reduceStock();
                currentAmount -= product.getPrice();
                recordSale(product.getPrice()); // 판매 기록
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "잔액이 부족하거나 재고가 없습니다.");
        return false;
    }

    // 판매를 기록하는 메서드
    private void recordSale(int amount) {
        String currentDate = getCurrentDate();
        int sales = dailySales.getOrDefault(currentDate, 0);
        sales += amount;
        dailySales.put(currentDate, sales);
    }

    // 현재 날짜를 반환하는 메서드
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    // 일별 매출을 반환하는 메서드
    public Map<String, Integer> getDailySales() {
        return dailySales;
    }

    // 현재 투입된 금액을 반환하는 메서드
    public int getCurrentAmount() {
        return currentAmount;
    }

    // 동전을 반환하는 메서드
    public int returnCoins() {
        int totalReturned = currentAmount;
        List<Coin> returnCoins = new ArrayList<>();
        int remainingAmount = currentAmount;

        // 동전을 큰 금액부터 정렬
        coins.sort((c1, c2) -> Integer.compare(c2.getDenomination(), c1.getDenomination()));

        // 반환할 동전을 계산
        for (Coin coin : coins) {
            int count = 0;
            while (remainingAmount >= coin.getDenomination() && coin.getCount() > 0) {
                coin.reduceCount(1);
                remainingAmount -= coin.getDenomination();
                count++;
            }
            if (count > 0) {
                returnCoins.add(new Coin(coin.getDenomination(), count));
            }
        }

        // 반환할 동전이 부족한 경우 처리
        if (remainingAmount > 0) {
            displayNoReturnCoins(remainingAmount);
        } else {
            currentAmount = remainingAmount;
            displayReturnCoins(returnCoins, totalReturned);
        }

        return totalReturned;
    }

    // 반환할 동전이 부족한 경우 메시지를 표시하는 메서드
    private void displayNoReturnCoins(int remainingAmount) {
        StringBuilder message = new StringBuilder("거스름돈 없음: 반환불가능한 금액은 " + remainingAmount + "원 입니다.\n현재 자판기에 남아 있는 화폐 현황:\n");
        for (Coin coin : coins) {
            message.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }

    // 반환된 동전을 표시하는 메서드
    private void displayReturnCoins(List<Coin> returnCoins, int totalReturned) {
        StringBuilder message = new StringBuilder("반환된 금액:\n");
        for (Coin coin : returnCoins) {
            message.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        message.append("총 반환 금액: ").append(totalReturned).append("원\n");
        JOptionPane.showMessageDialog(null, message.toString());
    }

    // 제품 목록을 반환하는 메서드
    public List<Product> getProducts() {
        return products;
    }

    // 동전 목록을 반환하는 메서드
    public List<Coin> getCoins() {
        return coins;
    }

    // 재고를 채우는 메서드
    public void refillStock(String productName, int amount) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.refillStock(amount);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "제품을 찾을 수 없습니다.");
    }

    // 동전을 채우는 메서드
    public void refillCoins(int denomination, int count) {
        for (Coin coin : coins) {
            if (coin.getDenomination() == denomination) {
                coin.addCount(count);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "해당 화폐를 찾을 수 없습니다.");
    }

    // 현재 자판기에 남아 있는 화폐 상태를 출력하는 메서드
    public void printCoinStatus() {
        StringBuilder status = new StringBuilder("현재 자판기에 남아 있는 화폐 현황:\n");
        for (Coin coin : coins) {
            status.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        JOptionPane.showMessageDialog(null, status.toString());
    }

    // 자판기에서 동전을 수거하는 메서드
    public void collectCoins() {
        StringBuilder collectedCoins = new StringBuilder("수금된 금액:\n");
        int totalCollected = 0;
        for (Coin coin : coins) {
            int toCollect = Math.max(coin.getCount() - 2, 0); // 최소 2개의 동전은 남김
            coin.reduceCount(toCollect);
            collectedCoins.append(coin.getDenomination()).append("원: ").append(toCollect).append("개\n");
            totalCollected += toCollect * coin.getDenomination();
        }
        collectedCoins.append("총 수금 금액: ").append(totalCollected).append("원\n");
        JOptionPane.showMessageDialog(null, collectedCoins.toString());
    }

    // 제품 정보를 변경하는 메서드
    public void changeProductDetails(String oldProductName, String newProductName, int newPrice) {
        for (Product product : products) {
            if (product.getName().equals(oldProductName)) {
                product.setName(newProductName);
                product.setPrice(newPrice);
                JOptionPane.showMessageDialog(null, "제품 정보가 성공적으로 업데이트되었습니다.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "제품을 찾을 수 없습니다.");
    }
}
