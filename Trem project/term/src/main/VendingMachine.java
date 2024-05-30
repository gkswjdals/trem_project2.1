package main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class VendingMachine {
    private List<Product> products;
    private List<Coin> coins;
    private int currentAmount;

    // 기본 생성자
    public VendingMachine() {
        this(10); // 기본적으로 각 동전별로 10개씩 초기화
    }

    // 오버로드된 생성자
    public VendingMachine(int initialCoinCount) {
        products = new ArrayList<>();
        coins = new ArrayList<>();
        initializeProducts();
        initializeCoins(initialCoinCount);
    }

    // 동전 초기화 메소드
    private void initializeCoins(int initialCoinCount) {
        coins.add(new Coin(10, initialCoinCount));
        coins.add(new Coin(50, initialCoinCount));
        coins.add(new Coin(100, initialCoinCount));
        coins.add(new Coin(500, initialCoinCount));
        coins.add(new Coin(1000, initialCoinCount / 2));  // 지폐는 초기값의 절반으로 설정
    }

    private void initializeProducts() {
        products.add(new Product("물", 450, 10));
        products.add(new Product("커피", 500, 10));
        products.add(new Product("이온음료", 550, 10));
        products.add(new Product("고급커피", 700, 10));
        products.add(new Product("탄산음료", 750, 10));
        products.add(new Product("특화음료", 800, 10));
    }

    public void insertCoin(int denomination) {
        for (Coin coin : coins) {
            if (coin.getDenomination() == denomination) {
                coin.addCount(1);
                currentAmount += denomination;
                break;
            }
        }
    }

    public boolean selectProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName) && !product.isOutOfStock() && currentAmount >= product.getPrice()) {
                product.reduceStock();
                currentAmount -= product.getPrice();
                JOptionPane.showMessageDialog(null, productName + "를(을) 구입하였습니다.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "잔액이 부족하거나 재고가 없습니다.");
        return false;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int returnCoins() {
        int totalReturned = 0;
        List<Coin> returnCoins = new ArrayList<>();
        int remainingAmount = currentAmount;

        // 재고가 많은 동전부터 반환하기 위해 동전 리스트를 정렬
        coins.sort((c1, c2) -> {
            int countCompare = Integer.compare(c2.getCount(), c1.getCount());
            return countCompare != 0 ? countCompare : Integer.compare(c2.getDenomination(), c1.getDenomination());
        });

        for (Coin coin : coins) {
            int count = 0;
            while (remainingAmount >= coin.getDenomination() && coin.getCount() > 0) {
                coin.reduceCount(1);
                remainingAmount -= coin.getDenomination();
                totalReturned += coin.getDenomination();
                count++;
            }
            if (count > 0) {
                returnCoins.add(new Coin(coin.getDenomination(), count));
            }
        }

        currentAmount = remainingAmount;

        // 반환된 동전을 표시
        if (totalReturned > 0) {
            displayReturnCoins(returnCoins);
        } else {
            JOptionPane.showMessageDialog(null, "거스름돈 없음");
        }

        return totalReturned;
    }

    private void displayReturnCoins(List<Coin> returnCoins) {
        StringBuilder message = new StringBuilder("반환된 금액:\n");
        for (Coin coin : returnCoins) {
            message.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }

    private void addCoins(int denomination, int count) {
        for (Coin coin : coins) {
            if (coin.getDenomination() == denomination) {
                coin.addCount(count);
                break;
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}
