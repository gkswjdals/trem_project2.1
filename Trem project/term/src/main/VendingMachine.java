package main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private List<Product> products; // 자판기에 있는 제품 목록
    private List<Coin> coins; // 자판기에 있는 동전 목록
    private int currentAmount; // 현재 투입된 금액

    // 기본 생성자
    public VendingMachine() {
        this(10); // 기본적으로 각 동전별로 10개씩 초기화
    }

    // 오버로드된 생성자
    public VendingMachine(int initialCoinCount) {
        products = new ArrayList<>(); // 제품 목록 초기화
        coins = new ArrayList<>(); // 동전 목록 초기화
        initializeProducts(); // 제품 초기화
        initializeCoins(initialCoinCount); // 동전 초기화
    }

    // 동전 초기화 메소드
    private void initializeCoins(int initialCoinCount) {
        coins.add(new Coin(10, initialCoinCount)); // 10원 동전 초기화
        coins.add(new Coin(50, initialCoinCount)); // 50원 동전 초기화
        coins.add(new Coin(100, initialCoinCount)); // 100원 동전 초기화
        coins.add(new Coin(500, initialCoinCount)); // 500원 동전 초기화
        coins.add(new Coin(1000, initialCoinCount / 2)); // 1000원 지폐는 초기값의 절반으로 설정
    }

    // 제품 초기화 메소드
    private void initializeProducts() {
        products.add(new Product("물", 450, 10)); // 물 제품 추가
        products.add(new Product("커피", 500, 10)); // 커피 제품 추가
        products.add(new Product("이온음료", 550, 10)); // 이온음료 제품 추가
        products.add(new Product("고급커피", 700, 10)); // 고급커피 제품 추가
        products.add(new Product("탄산음료", 750, 10)); // 탄산음료 제품 추가
        products.add(new Product("특화음료", 800, 10)); // 특화음료 제품 추가
    }

    // 동전 투입 메소드
    public void insertCoin(int denomination) {
        for (Coin coin : coins) {
            if (coin.getDenomination() == denomination) {
                coin.addCount(1); // 해당 금액의 동전 개수 증가
                currentAmount += denomination; // 현재 투입 금액 증가
                break;
            }
        }
    }

    // 제품 선택 메소드
    public boolean selectProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName) && !product.isOutOfStock() && currentAmount >= product.getPrice()) {
                product.reduceStock(); // 제품 재고 감소
                currentAmount -= product.getPrice(); // 현재 투입 금액에서 제품 가격 차감
                JOptionPane.showMessageDialog(null, productName + "를(을) 구입하였습니다."); // 구매 메시지 표시
                return true; // 구매 성공
            }
        }
        JOptionPane.showMessageDialog(null, "잔액이 부족하거나 재고가 없습니다."); // 구매 실패 메시지 표시
        return false; // 구매 실패
    }

    // 현재 투입된 금액 반환 메소드
    public int getCurrentAmount() {
        return currentAmount;
    }

    // 동전 반환 메소드
    public int returnCoins() {
        int totalReturned = currentAmount; // 반환될 총 금액은 현재 남은 금액
        List<Coin> returnCoins = new ArrayList<>(); // 반환할 동전 목록
        int remainingAmount = currentAmount; // 반환 후 남은 금액

        // 액면가가 큰 순서대로 동전 리스트를 정렬
        coins.sort((c1, c2) -> Integer.compare(c2.getDenomination(), c1.getDenomination()));

        // 동전 반환 로직
        for (Coin coin : coins) {
            int count = 0;
            while (remainingAmount >= coin.getDenomination() && coin.getCount() > 0) {
                coin.reduceCount(1); // 동전 개수 감소
                remainingAmount -= coin.getDenomination(); // 남은 금액 감소
                count++;
            }
            if (count > 0) {
                returnCoins.add(new Coin(coin.getDenomination(), count)); // 반환할 동전 목록에 추가
            }
        }

        currentAmount = remainingAmount; // 현재 금액 업데이트

        // 반환된 동전을 표시
        if (totalReturned > 0) {
            displayReturnCoins(returnCoins, totalReturned); // 반환 동전 메시지 표시
        } else {
            JOptionPane.showMessageDialog(null, "거스름돈 없음"); // 반환할 동전 없음 메시지 표시
        }

        return totalReturned; // 반환된 총 금액 반환
    }

    // 반환된 동전을 표시하는 메소드
    private void displayReturnCoins(List<Coin> returnCoins, int totalReturned) {
        StringBuilder message = new StringBuilder("반환된 금액:\n");
        for (Coin coin : returnCoins) {
            message.append(coin.getDenomination()).append("원: ").append(coin.getCount()).append("개\n");
        }
        message.append("총 반환 금액: ").append(totalReturned).append("원\n");
        JOptionPane.showMessageDialog(null, message.toString());
    }

    // 제품 목록 반환 메소드
    public List<Product> getProducts() {
        return products;
    }

    // 동전 목록 반환 메소드
    public List<Coin> getCoins() {
        return coins;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VendingMachine vendingMachine = new VendingMachine();
            JFrame frame = new JFrame("자판기"); // 프레임 제목 설정
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);
            frame.setLayout(null);

            // 현재 투입된 금액을 표시하는 레이블
            JLabel amountLabel = new JLabel("현재 투입된 금액 : " + vendingMachine.getCurrentAmount() + "원");
            amountLabel.setBounds(150, 20, 200, 30);
            frame.add(amountLabel);

            // 동전 반환 버튼
            JButton returnButton = new JButton("반환");
            returnButton.setBounds(150, 400, 100, 50);
            returnButton.addActionListener(e -> {
                vendingMachine.returnCoins();
                amountLabel.setText("현재 투입된 금액 : " + vendingMachine.getCurrentAmount() + "원");
            });
            frame.add(returnButton);

            frame.setVisible(true);
        });
    }
}
