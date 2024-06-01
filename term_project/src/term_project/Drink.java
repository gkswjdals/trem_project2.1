package term_project;

// Drink 클래스 정의
public class Drink {
    // 음료의 이름을 저장하는 변수
    private String name;
    // 음료의 한국어 이름을 저장하는 변수
    private String koreanName;
    // 음료의 가격을 저장하는 변수
    private int price;
    // 음료의 재고 수량을 저장하는 변수
    private int stock;

    // 기본 생성자: 음료의 이름, 한국어 이름, 가격을 설정하고 기본 재고 수량을 10으로 설정
    public Drink(String name, String koreanName, int price) {
        this(name, koreanName, price, 10); // 기본 수량을 10으로 설정
    }

    // 명시적 생성자: 음료의 이름, 한국어 이름, 가격, 재고 수량을 명시적으로 설정
    public Drink(String name, String koreanName, int price, int stock) {
        this.name = name; // 전달된 이름으로 name 변수 초기화
        this.koreanName = koreanName; // 전달된 한국어 이름으로 koreanName 변수 초기화
        this.price = price; // 전달된 가격으로 price 변수 초기화
        this.stock = stock; // 전달된 재고 수량으로 stock 변수 초기화
    }

    // Getter 메서드들

    // 음료의 이름을 반환하는 메소드
    public String getName() {
        return name;
    }

    // 음료의 한국어 이름을 반환하는 메소드
    public String getKoreanName() {
        return koreanName;
    }

    // 음료의 가격을 반환하는 메소드
    public int getPrice() {
        return price;
    }

    // 음료의 재고 수량을 반환하는 메소드
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

    // 재고 보충 메소드
    public void refillStock(int amount) {
        stock += amount; // 전달된 수량만큼 재고 보충
    }

    // 재고 확인 메소드
    public boolean isOutOfStock() {
        return stock <= 0; // 재고가 0 이하인지 확인
    }
}
