package term_project;

// Product 클래스 정의
public class Product {
    private String name;
    private int price;
    private int stock;

    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
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

    public boolean isOutOfStock() {
        return stock <= 0;
    }
    
    public void refillStock(int amount) {
        this.stock += amount;
    }

    // 이름 변경 메소드
    public void setName(String name) {
        this.name = name;
    }

    // 가격 변경 메소드
    public void setPrice(int price) {
        this.price = price;
    }
}
