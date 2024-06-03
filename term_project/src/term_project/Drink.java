package term_project;

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

    // Product 객체를 받아서 Drink 객체로 변환하는 생성자
    public Drink(Product product) {
        this.name = product.getName();
        this.koreanName = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
