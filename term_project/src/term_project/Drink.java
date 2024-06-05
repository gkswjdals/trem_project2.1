package term_project;

public class Drink {
    private String name; // 음료의 영어 이름
    private String koreanName; // 음료의 한국어 이름
    private int price; // 음료의 가격
    private int stock; // 음료의 재고 수량

    // 기본 생성자: 기본 재고는 10으로 설정
    public Drink(String name, String koreanName, int price) {
        this(name, koreanName, price, 10);
    }

    // 모든 필드를 초기화하는 생성자
    public Drink(String name, String koreanName, int price, int stock) {
        this.name = name;
        this.koreanName = koreanName;
        this.price = price;
        this.stock = stock;
    }

    // Product 객체를 받아서 Drink 객체로 변환하는 생성자
    public Drink(Product product) {
        this.name = product.getName(); // Product의 이름을 가져와 설정
        this.koreanName = product.getName(); // 한국어 이름도 동일하게 설정
        this.price = product.getPrice(); // Product의 가격을 가져와 설정
        this.stock = product.getStock(); // Product의 재고를 가져와 설정
    }

    // 이름을 반환하는 메서드
    public String getName() {
        return name;
    }

    // 한국어 이름을 반환하는 메서드
    public String getKoreanName() {
        return koreanName;
    }

    // 가격을 반환하는 메서드
    public int getPrice() {
        return price;
    }

    // 재고를 반환하는 메서드
    public int getStock() {
        return stock;
    }

    // 재고를 하나 줄이는 메서드
    public void reduceStock() {
        if (stock > 0) { // 재고가 있을 경우에만
            stock--; // 재고를 하나 줄임
        }
    }

    // 재고를 특정 양만큼 추가하는 메서드
    public void refillStock(int amount) {
        stock += amount; // 재고에 주어진 양을 더함
    }

    // 재고가 없는지 확인하는 메서드
    public boolean isOutOfStock() {
        return stock <= 0; // 재고가 0 이하이면 true 반환
    }

    // 이름을 설정하는 메서드
    public void setName(String name) {
        this.name = name; // 주어진 이름으로 설정
    }

    // 가격을 설정하는 메서드
    public void setPrice(int price) {
        this.price = price; // 주어진 가격으로 설정
    }
}
