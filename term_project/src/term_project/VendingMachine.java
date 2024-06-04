package term_project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private List<Product> products;
    private List<Coin> coins;
    private int currentAmount;
    private Admin admin;

    public VendingMachine() {
        this(10);
    }

    public VendingMachine(int initialCoinCount) {
        products = new ArrayList<>();
        coins = new ArrayList<>();
        initializeProducts();
        initializeCoins(initialCoinCount);
        admin = new Admin("admin123!"); // 예시로 관리자 추가
    }

    private void initializeCoins(int initialCoinCount) {
        coins.add(new Coin(10, initialCoinCount));
        coins.add(new Coin(50, initialCoinCount));
        coins.add(new Coin(100, initialCoinCount));
        coins.add(new Coin(500, initialCoinCount));
        coins.add(new Coin(1000, initialCoinCount));
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
                admin.recordSale(productName, 1); // 판매 기록
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
        int totalReturned = currentAmount;
        List<Coin> returnCoins = new ArrayList<>();
        int remainingAmount = currentAmount;

        coins.sort((c1, c2) -> Integer.compare(c2.getDenomination(), c1.getDenomination()));

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

        currentAmount = remainingAmount;

        if (totalReturned > 0) {
            displayReturnCoins(returnCoins, totalReturned);
        } else {
            JOptionPane.showMessageDialog(null, "거스름돈 없음");
        }

        return totalReturned;
    }

    private void displayReturnCoins(List<Coin> returnCoins, int totalReturned) {
        StringBuilder message = new StringBuilder("반환된 금액:\n");
        for (Coin coin : returnCoins) {
            message.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        message.append("총 반환 금액: ").append(totalReturned).append("원\n");
        JOptionPane.showMessageDialog(null, message.toString());
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void refillStock(String productName, int amount) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.refillStock(amount);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "제품을 찾을 수 없습니다.");
    }


    public void refillCoins(int denomination, int count) {
        for (Coin coin : coins) {
            if (coin.getDenomination() == denomination) {
                coin.addCount(count);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "해당 화폐를 찾을 수 없습니다.");
    }

    public void printCoinStatus() {
        StringBuilder status = new StringBuilder("현재 자판기에 남아 있는 화폐 현황:\n");
        for (Coin coin : coins) {
            status.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        JOptionPane.showMessageDialog(null, status.toString());
    }

    public void collectCoins() {
        StringBuilder collectedCoins = new StringBuilder("수금된 금액:\n");
        int totalCollected = 0;
        for (Coin coin : coins) {
            int toCollect = Math.max(coin.getCount() - 2, 0);
            coin.reduceCount(toCollect);
            collectedCoins.append(coin.getDenomination()).append("원: ").append(toCollect).append("개\n");
            totalCollected += toCollect * coin.getDenomination();
        }
        collectedCoins.append("총 수금 금액: ").append(totalCollected).append("원\n");
        JOptionPane.showMessageDialog(null, collectedCoins.toString());
    }

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
