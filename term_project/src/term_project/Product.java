package term_project;

// Product 클래스 정의
public class Product {
    // 제품의 이름을 저장하는 변수
    private String name;
    // 제품의 가격을 저장하는 변수
    private int price;
    // 제품의 재고 수량을 저장하는 변수
    private int stock;

    // 생성자: Product 클래스의 인스턴스를 생성할 때 이름, 가격, 재고 수량을 설정
    public Product(String name, int price, int stock) {
        this.name = name; // 전달된 이름으로 name 변수 초기화
        this.price = price; // 전달된 가격으로 price 변수 초기화
        this.stock = stock; // 전달된 재고 수량으로 stock 변수 초기화
    }

    // Getter 메서드들

    // 제품의 이름을 반환하는 메소드
    public String getName() {
        return name;
    }

    // 제품의 가격을 반환하는 메소드
    public int getPrice() {
        return price;
    }

    // 제품의 재고 수량을 반환하는 메소드
    public int getStock() {
        return stock;
    }

    // 재고 감소 메소드
    public void reduceStock() {
        // 재고가 0보다 클 때만 감소
        if (stock > 0) {
            stock--; // 재고 감소
        }
    }

    // 재고 확인 메소드
    public boolean isOutOfStock() {
        return stock <= 0; // 재고가 0 이하인지 확인
    }
}
