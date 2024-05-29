package main;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private List<Product> products;
    private List<Coin> coins;
    private int currentAmount;

    public VendingMachine() {
        products = new ArrayList<>();
        coins = new ArrayList<>();
        initializeProducts();
        initializeCoins();
    }

    private void initializeProducts() {
        products.add(new Product("물", 450, 10));
        products.add(new Product("커피", 500, 10));
        products.add(new Product("이온음료", 550, 10));
        products.add(new Product("고급커피", 700, 10));
        products.add(new Product("탄산음료", 750, 10));
        products.add(new Product("특화음료", 800, 10));
    }

    private void initializeCoins() {
        coins.add(new Coin(10, 10));
        coins.add(new Coin(50, 10));
        coins.add(new Coin(100, 10));
        coins.add(new Coin(500, 10));
        coins.add(new Coin(1000, 5));  // 지폐는 5장만 허용
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
                return true;
            }
        }
        return false;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void returnCoins() {
        currentAmount = 0;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}
