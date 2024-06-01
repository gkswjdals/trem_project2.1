package term_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// GUI 클래스 정의
public class GUI {
    // 자판기와 관리자 객체 생성
    private VendingMachine vendingMachine; // 자판기 인스턴스
    private Admin admin; // 관리자 인스턴스
    private Integer currentAmount = 0; // 현재 투입된 금액
    private JFrame mainFrame; // 메인 프레임
    private JLabel amountLabel; // 현재 투입된 금액을 표시할 라벨
    private JLabel[] greenDots; // 구매 가능 여부를 나타내는 녹색 점 라벨 배열
    private JLabel[] stockLabels; // 재고 라벨 배열 (제공된 코드에서는 사용되지 않음)
    private JLabel[] blueDots; // 재고 없음 여부를 나타내는 파란 점 라벨 배열
    private JButton[] drinkButtons; // 음료 버튼 배열

    // 최대 투입 금액 제한 설정
    private final int MAX_CASH_LIMIT = 5000; // 1000원 지폐 최대 한도
    private final int MAX_TOTAL_LIMIT = 7000; // 모든 돈의 최대 한도

    // 제공되는 음료 배열
    private Drink[] drinks = {
        new Drink("water", "물", 450),
        new Drink("coffee", "커피", 500),
        new Drink("sport", "이온음료", 550),
        new Drink("high", "고급커피", 700),
        new Drink("Soda", "탄산음료", 750),
        new Drink("Special", "특화음료", 800)
    };

    // GUI 생성자
    public GUI() {
        vendingMachine = new VendingMachine(); // 자판기 인스턴스 초기화
        admin = new Admin("admin123!"); // 관리자 인스턴스 초기화
        greenDots = new JLabel[drinks.length]; // 녹색 점 라벨 배열 초기화
        blueDots = new JLabel[drinks.length]; // 파란 점 라벨 배열 초기화
        drinkButtons = new JButton[drinks.length]; // 음료 버튼 배열 초기화
        createAndShowGUI(); // GUI 생성 및 표시
    }

    // GUI를 생성하고 표시하는 메소드
    private void createAndShowGUI() {
        mainFrame = new JFrame("Vending Machine"); // 메인 프레임 생성
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 클릭 시 종료
        mainFrame.setSize(800, 600); // 프레임 크기 설정

        JLayeredPane layeredPane = new UI(); // 레이어드 패인 생성

        // 각 음료를 레이어드 패인에 추가
        addDrink(layeredPane, drinks[0], 290, 55, 30, 75, "src/water.png", 273, 129, 65, 20, 285, 150, 40, 10, 270, 150, 0);
        addDrink(layeredPane, drinks[1], 348, 55, 40, 78, "src/coffee.png", 337, 129, 65, 20, 348, 150, 40, 10, 335, 150, 1);
        addDrink(layeredPane, drinks[2], 418, 55, 40, 78, "src/sport.png", 403, 129, 65, 20, 418, 150, 40, 10, 402, 149, 2);
        addDrink(layeredPane, drinks[3], 480, 52, 43, 80, "src/high.png", 471, 129, 65, 20, 480, 150, 40, 10, 470, 150, 3);
        addDrink(layeredPane, drinks[4], 287, 164, 35, 76, "src/soda.png", 274, 236, 65, 20, 287, 255, 40, 10, 270, 258, 4);
        addDrink(layeredPane, drinks[5], 352, 164, 33, 73, "src/Special.png", 333, 236, 73, 20, 350, 255, 40, 10, 335, 257, 5);

        // 빨간 원 버튼 추가 (관리자 창 열기)
        addRedCircleButton(layeredPane, 474, 308, 30, 30);

        // 현재 금액 표시 라벨 추가
        amountLabel = new JLabel("현재 투입된 금액 : " + currentAmount + " 원");
        amountLabel.setBounds(600, 20, 200, 30);
        layeredPane.add(amountLabel, JLayeredPane.PALETTE_LAYER);

        // 반환 버튼 추가
        addReturnButton(layeredPane, 400, 382, 45, 22);

        mainFrame.add(layeredPane); // 메인 프레임에 레이어드 패인 추가
        mainFrame.pack(); // 프레임 크기 조정
        mainFrame.setVisible(true); // 프레임 표시
    }

    // 음료 추가 메소드
    private void addDrink(JLayeredPane pane, Drink drink, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight, int buttonX, int buttonY, int buttonWidth, int buttonHeight, int greenDotX, int greenDotY, int dotIndex) {
        try {
            // 이미지 불러오기 및 설정
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            // 이미지 라벨 설정
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight);
            imageLabel.setOpaque(false);
            pane.add(imageLabel, JLayeredPane.PALETTE_LAYER);

            // 음료 이름 라벨 설정
            JLabel nameLabel = new JLabel(drink.getKoreanName());
            nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
            nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 10));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            pane.add(nameLabel, JLayeredPane.PALETTE_LAYER);

            // 음료 가격 라벨 설정
            JLabel priceLabel = new JLabel(drink.getPrice() + "");
            priceLabel.setBounds(labelX + 2, labelY + 16, labelWidth, labelHeight);
            priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 10));
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setVerticalAlignment(SwingConstants.CENTER);
            priceLabel.setForeground(Color.WHITE);
            pane.add(priceLabel, JLayeredPane.PALETTE_LAYER);

            // 음료 버튼 생성 및 추가
            JButton button = createDrinkButton(drink, buttonX, buttonY, buttonWidth, buttonHeight, dotIndex);
            pane.add(button, JLayeredPane.PALETTE_LAYER);
            drinkButtons[dotIndex] = button;

            // 녹색 점 이미지 불러오기 및 설정
            BufferedImage greenDotImg = ImageIO.read(new File("src/green.png"));
            Image scaledGreenDotImg = greenDotImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
            ImageIcon greenDotIcon = new ImageIcon(scaledGreenDotImg);

            JLabel greenDotLabel = new JLabel(greenDotIcon);
            greenDotLabel.setBounds(greenDotX + 15, greenDotY, 10, 10);
            greenDotLabel.setVisible(false);
            pane.add(greenDotLabel, JLayeredPane.PALETTE_LAYER);
            greenDots[dotIndex] = greenDotLabel;

            // 파란 점 이미지 불러오기 및 설정
            BufferedImage blueDotImg = ImageIO.read(new File("src/blue.png"));
            Image scaledBlueDotImg = blueDotImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
            ImageIcon blueDotIcon = new ImageIcon(scaledBlueDotImg);

            JLabel blueDotLabel = new JLabel(blueDotIcon);
            blueDotLabel.setBounds(greenDotX + 15, greenDotY, 10, 10);
            blueDotLabel.setVisible(false);
            pane.add(blueDotLabel, JLayeredPane.PALETTE_LAYER);
            blueDots[dotIndex] = blueDotLabel;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 음료 버튼 생성 메소드
    private JButton createDrinkButton(Drink drink, int x, int y, int width, int height, int dotIndex) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // 버튼 클릭 이벤트 처리
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDrinkSelection(drink, dotIndex); // 음료 선택 처리 메소드 호출
            }
        });

        return button;
    }

    // 음료 선택 처리 메소드
    private void handleDrinkSelection(Drink drink, int dotIndex) {
        if (drink.isOutOfStock()) {
            JOptionPane.showMessageDialog(null, "품절된 음료입니다."); // 음료 품절 시 메시지 표시
            return;
        }

        if (currentAmount >= drink.getPrice()) {
            currentAmount -= drink.getPrice(); // 금액 차감
            vendingMachine.selectProduct(drink.getKoreanName()); // 자판기 상태 업데이트
            drink.reduceStock(); // 재고 감소
            amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
            // 음료 구매 성공 메시지 표시
            if (drink.getKoreanName().equals("물")) {
                JOptionPane.showMessageDialog(null, drink.getKoreanName() + "을 구입했습니다.");
            } else {
                JOptionPane.showMessageDialog(null, drink.getKoreanName() + "를 구입했습니다.");
            }
            updateGreenDots(); // 녹색 점 업데이트
            updateDrinkAvailability(dotIndex); // 음료 가용성 업데이트
        } else {
            JOptionPane.showMessageDialog(null, "금액이 부족합니다."); // 금액 부족 시 메시지 표시
        }
    }

    // 녹색 점 업데이트 메소드
    private void updateGreenDots() {
        for (int i = 0; i < drinks.length; i++) {
            if (currentAmount >= drinks[i].getPrice() && !drinks[i].isOutOfStock()) {
                greenDots[i].setVisible(true); // 구매 가능 시 녹색 점 표시
            } else {
                greenDots[i].setVisible(false); // 구매 불가능 시 녹색 점 숨기기
            }
        }
    }

    // 음료 가용성 업데이트 메소드
    private void updateDrinkAvailability(int dotIndex) {
        if (drinks[dotIndex].isOutOfStock()) {
            blueDots[dotIndex].setVisible(true); // 품절 시 파란 점 표시
            drinkButtons[dotIndex].setEnabled(false); // 버튼 비활성화
            greenDots[dotIndex].setVisible(false); // 녹색 점 숨기기
        } else {
            blueDots[dotIndex].setVisible(false); // 재고 있을 시 파란 점 숨기기
            drinkButtons[dotIndex].setEnabled(true); // 버튼 활성화
        }
    }

    // 빨간 원 버튼 추가 메소드 (관리자 창 열기)
    private void addRedCircleButton(JLayeredPane pane, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        try {
            // 이미지 불러오기 및 설정
            BufferedImage buttonIcon = ImageIO.read(new File("src/red.png"));
            Image scaledIcon = buttonIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 버튼 클릭 이벤트 처리
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewWindow(); // 새 창 열기 메소드 호출
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    // 반환 버튼 추가 메소드
    private void addReturnButton(JLayeredPane pane, int x, int y, int width, int height) {
        JButton button = new JButton("r");
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);

        // 버튼 클릭 이벤트 처리
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnCoins(); // 반환 메소드 호출
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    // 반환 메소드
    private void returnCoins() {
        int returnedAmount = vendingMachine.returnCoins(); // 반환된 금액
        currentAmount = vendingMachine.getCurrentAmount(); // 현재 투입된 금액 업데이트
        amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
        updateGreenDots(); // 녹색 점 업데이트
    }

    // 새 창 열기 메소드
    private void openNewWindow() {
        JFrame newFrame = new JFrame("Insert Money"); // 새 프레임 생성
        newFrame.setSize(700, 250); // 프레임 크기 설정
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정

        JLayeredPane newPane = new JLayeredPane(); // 새로운 레이어드 패인 생성
        newPane.setPreferredSize(new Dimension(700, 250)); // 레이어드 패인 크기 설정

        // 각 금액 버튼 추가
        addMoneyButton(newPane, 10, 45, 30, 70, 70);
        addMoneyButton(newPane, 50, 155, 30, 70, 70);
        addMoneyButton(newPane, 100, 265, 30, 70, 70);
        addMoneyButton(newPane, 500, 375, 30, 70, 70);
        addMoneyButton(newPane, 1000, 485, 30, 70, 70);

        // 투입 완료 버튼 추가
        JButton completeButton = new JButton("투입완료");
        completeButton.setBounds(540, 140, 120, 50);
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame.dispose(); // 새 창 닫기
                updateGreenDots(); // 녹색 점 업데이트
            }
        });
        newPane.add(completeButton, JLayeredPane.PALETTE_LAYER);

        newFrame.add(newPane);
        newFrame.pack();
        newFrame.setVisible(true); // 새 프레임 표시
    }

    // 금액 버튼 추가 메소드
    private void addMoneyButton(JLayeredPane pane, int amount, int x, int y, int width, int height) {
        JButton button = new JButton(String.valueOf(amount));
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // 버튼 클릭 이벤트 처리
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int newAmount = currentAmount + amount;
                // 최대 금액 한도 체크 및 업데이트
                if ((amount == 1000 && currentAmount + amount <= MAX_CASH_LIMIT) ||
                    (amount != 1000 && newAmount <= MAX_TOTAL_LIMIT)) {
                    currentAmount += amount; // 현재 금액 업데이트
                    vendingMachine.insertCoin(amount); // 자판기에 금액 추가
                    amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
                } else {
                    JOptionPane.showMessageDialog(null, "입력할 수 있는 금액의 상한선을 초과했습니다."); // 한도 초과 시 메시지 표시
                }
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    // 메인 메소드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI()); // GUI 실행
    }
}
