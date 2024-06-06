package main;

// Product 클래스 정의
public class Product {
    private String name; // 제품 이름
    private int price; // 제품 가격
    private int stock; // 제품 재고 수량

    // 생성자: 이름, 가격, 재고 수량을 초기화
    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 이름을 반환하는 메서드
    public String getName() {
        return name;
    }

    // 가격을 반환하는 메서드
    public int getPrice() {
        return price;
    }

    // 재고 수량을 반환하는 메서드
    public int getStock() {
        return stock;
    }

    // 재고를 하나 줄이는 메서드
    public void reduceStock() {
        if (stock > 0) { // 재고가 있을 경우에만
            stock--; // 재고를 하나 줄임
        }
    }

    // 재고가 없는지 확인하는 메서드
    public boolean isOutOfStock() {
        return stock <= 0; // 재고가 0 이하이면 true 반환
    }
    
    // 재고를 특정 양만큼 추가하는 메서드
    public void refillStock(int amount) {
        this.stock += amount; // 재고에 주어진 양을 더함
    }

    // 이름을 변경하는 메서드
    public void setName(String name) {
        this.name = name; // 주어진 이름으로 설정
    }

    // 가격을 변경하는 메서드
    public void setPrice(int price) {
        this.price = price; // 주어진 가격으로 설정
    }
}
