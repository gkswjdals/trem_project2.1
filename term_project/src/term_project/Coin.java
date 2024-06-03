package term_project;

// Coin 클래스 정의
public class Coin {
    // 코인의 가격을 저장하는 변수
    private int denomination;
    // 코인의 개수를 저장하는 변수
    private int count;

    // 생성자: Coin 클래스의 인스턴스를 생성할 때 액면가와 개수를 설정
    public Coin(int denomination, int count) {
        this.denomination = denomination; // 전달된 액면가로 denomination 변수 초기화
        this.count = count; // 전달된 개수로 count 변수 초기화
    }

    // 가격을 반환하는 메소드
    public int getDenomination() {
        return denomination;
    }

    // 코인의 개수를 반환하는 메소드
    public int getCount() {
        return count;
    }

    // 코인의 개수를 추가하는 메소드
    public void addCount(int count) {
        this.count += count; // 전달된 개수를 현재 개수에 더함
    }

    // 코인의 개수를 감소시키는 메소드
    public void reduceCount(int count) {
        // 현재 개수가 전달된 개수보다 크거나 같을 때만 감소시킴
        if (this.count >= count) {
            this.count -= count; // 현재 개수에서 전달된 개수를 뺌
        }
    }
}
