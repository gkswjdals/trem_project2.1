package main;

public class Drink {
    private String name;
    private String koreanName;
    private int price;
    private int stock;

    public Drink(String name, String koreanName, int price) {
        this(name, koreanName, price, 10);
    }

    public Drink(String name, String koreanName, int price, int stock) {
        this.name = name;
        this.koreanName = koreanName;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock() {
        if (stock > 0) {
            stock--;
        }
    }

    public void refillStock(int amount) {
        stock += amount;
    }

    public boolean isOutOfStock() {
        return stock <= 0;
    }
}
