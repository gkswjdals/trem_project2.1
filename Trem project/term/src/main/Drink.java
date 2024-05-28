package main;

public class Drink {
    private String name;
    private String koreanName;
    private int price;

    public Drink(String name, String koreanName, int price) {
        this.name = name;
        this.koreanName = koreanName;
        this.price = price;
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
}
