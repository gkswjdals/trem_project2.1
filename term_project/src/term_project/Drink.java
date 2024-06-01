package term_project;

public class Drink {
    private String name;
    private String koreanName;
    private int price;
    private int stock;

    // 기본 생성자: 기본 재고 수량을 10으로 설정
    public Drink(String name, String koreanName, int price) {
        this(name, koreanName, price, 10); // 기본 수량을 10으로 설정
    }

    // 명시적 생성자: 재고 수량을 명시적으로 설정
    public Drink(String name, String koreanName, int price, int stock) {
        this.name = name;
        this.koreanName = koreanName;
        this.price = price;
        this.stock = stock;
    }

    // Getter 메서드들
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

    // 재고 감소 메서드
    public void reduceStock() {
        if (stock > 0) {
            stock--;
        }
    }

    // 재고 보충 메서드
    public void refillStock(int amount) {
        stock += amount;
    }

    // 재고 확인 메서드
    public boolean isOutOfStock() {
        return stock <= 0;
    }
}
