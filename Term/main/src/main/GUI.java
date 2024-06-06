package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUI {
    private VendingMachine vendingMachine; // 자판기 인스턴스
    private Admin admin; // 관리자 인스턴스
    private Integer currentAmount = 0; // 현재 투입된 금액
    private JFrame mainFrame; // 메인 프레임
    private JFrame changeProductDetailsFrame; // 제품 정보 변경 프레임
    private JFrame adminFrame; // 관리자 모드 프레임
    private JFrame refillStockFrame; // 재고 보충 프레임
    private JFrame refillCoinsFrame; // 화폐 보충 프레임
    private JLabel amountLabel; // 투입된 금액을 표시하는 라벨
    private JLabel[] greenDots; // 제품 구매 가능 상태를 표시하는 초록색 점 라벨
    private JLabel[] blueDots; // 품절 상태를 표시하는 파란색 점 라벨
    private JButton[] drinkButtons; // 음료 선택 버튼
    private JLabel[] nameLabels; // 제품 이름 라벨
    private JLabel[] priceLabels; // 제품 가격 라벨
    private JButton insertCoinButton; // 동전 투입 버튼
    private JButton returnButton; // 반환 버튼

    private final int MAX_CASH_LIMIT = 5000; // 최대 현금 투입 한도
    private final int MAX_TOTAL_LIMIT = 7000; // 최대 총 투입 한도

    // 각 음료를 Drink 객체로 생성
    private Drink[] drinks = {
        new Drink("water", "물", 450),
        new Drink("coffee", "커피", 500),
        new Drink("sport", "이온음료", 550),
        new Drink("high", "고급커피", 700),
        new Drink("Soda", "탄산음료", 750),
        new Drink("Special", "특화음료", 800)
    };

    // 생성자: 자판기와 관리자 인스턴스를 초기화하고 UI를 생성
    public GUI() {
        vendingMachine = new VendingMachine(); // 자판기 인스턴스 초기화
        admin = new Admin("admin123!"); // 관리자 인스턴스 초기화
        greenDots = new JLabel[drinks.length]; // 초록색 점 라벨 배열 초기화
        blueDots = new JLabel[drinks.length]; // 파란색 점 라벨 배열 초기화
        drinkButtons = new JButton[drinks.length]; // 음료 버튼 배열 초기화
        nameLabels = new JLabel[drinks.length]; // 이름 라벨 배열 초기화
        priceLabels = new JLabel[drinks.length]; // 가격 라벨 배열 초기화
        createAndShowGUI(); // GUI 생성 및 표시
    }

    // GUI를 생성하고 표시하는 메서드
    private void createAndShowGUI() {
        mainFrame = new JFrame("자판기"); // 메인 프레임 생성
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 동작 설정
        mainFrame.setSize(800, 600); // 프레임 크기 설정

        JLayeredPane layeredPane = new UI(this); // UI 인스턴스를 생성할 때 GUI 인스턴스를 전달

        // 각 음료를 추가
        addDrink(layeredPane, drinks[0], 290, 55, 30, 75, "src/water.png", 273, 129, 65, 20, 285, 150, 40, 10, 270, 150, 0);
        addDrink(layeredPane, drinks[1], 348, 55, 40, 78, "src/coffee.png", 337, 129, 65, 20, 348, 150, 40, 10, 335, 150, 1);
        addDrink(layeredPane, drinks[2], 418, 55, 40, 78, "src/sport.png", 405, 129, 65, 20, 418, 150, 40, 10, 402, 149, 2);
        addDrink(layeredPane, drinks[3], 480, 52, 43, 80, "src/high.png", 471, 129, 65, 20, 480, 150, 40, 10, 470, 150, 3);
        addDrink(layeredPane, drinks[4], 287, 164, 35, 76, "src/soda.png", 273, 235, 65, 20, 287, 255, 40, 10, 270, 258, 4);
        addDrink(layeredPane, drinks[5], 352, 164, 33, 73, "src/Special.png", 333, 235, 73, 20, 350, 255, 40, 10, 335, 257, 5);

        insertCoinButton = addRedCircleButton(layeredPane, 499, 312, 40, 38); // 동전 투입 버튼 추가

        amountLabel = new JLabel("현재 투입된 금액 : " + currentAmount + " 원"); // 현재 투입된 금액 라벨 초기화
        amountLabel.setBounds(600, 20, 200, 30); // 라벨 위치 및 크기 설정
        layeredPane.add(amountLabel, JLayeredPane.PALETTE_LAYER); // 라벨을 레이어드 팬에 추가

        // 반환 버튼 추가
        returnButton = addReturnButton(layeredPane, 404, 378, 45, 35, 45, 35); // 버튼 크기: 50x30, 이미지 크기: 45x22

        mainFrame.add(layeredPane); // 메인 프레임에 레이어드 팬 추가
        mainFrame.pack(); // 프레임 크기 조정
        mainFrame.setVisible(true); // 프레임 표시
    }

    // 음료를 추가하는 메서드
    private void addDrink(JLayeredPane pane, Drink drink, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight, int buttonX, int buttonY, int buttonWidth, int buttonHeight, int greenDotX, int greenDotY, int dotIndex) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath)); // 이미지 파일 읽기
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon icon = new ImageIcon(scaledImg); // 이미지 아이콘 생성

            JLabel imageLabel = new JLabel(icon); // 이미지 라벨 생성
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight); // 이미지 라벨 위치 및 크기 설정
            imageLabel.setOpaque(false); // 불투명 설정
            pane.add(imageLabel, JLayeredPane.PALETTE_LAYER); // 이미지 라벨을 레이어드 팬에 추가

            JLabel nameLabel = new JLabel(drink.KoreanName()); // 이름 라벨 생성
            nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight); // 이름 라벨 위치 및 크기 설정
            nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 10)); // 폰트 설정
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
            nameLabel.setVerticalAlignment(SwingConstants.CENTER); // 수직 정렬 설정
            pane.add(nameLabel, JLayeredPane.PALETTE_LAYER); // 이름 라벨을 레이어드 팬에 추가
            nameLabels[dotIndex] = nameLabel; // 이름 라벨 배열에 추가

            JLabel priceLabel = new JLabel(drink.Price() + ""); // 가격 라벨 생성
            priceLabel.setBounds(labelX + 2, labelY + 16, labelWidth, labelHeight); // 가격 라벨 위치 및 크기 설정
            priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 10)); // 폰트 설정
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
            priceLabel.setVerticalAlignment(SwingConstants.CENTER); // 수직 정렬 설정
            priceLabel.setForeground(Color.WHITE); // 글자 색 설정
            pane.add(priceLabel, JLayeredPane.PALETTE_LAYER); // 가격 라벨을 레이어드 팬에 추가
            priceLabels[dotIndex] = priceLabel; // 가격 라벨 배열에 추가

            JButton button = createDrinkButton(drink, buttonX, buttonY, buttonWidth, buttonHeight, dotIndex); // 음료 버튼 생성
            pane.add(button, JLayeredPane.PALETTE_LAYER); // 음료 버튼을 레이어드 팬에 추가
            drinkButtons[dotIndex] = button; // 음료 버튼 배열에 추가

            BufferedImage greenDotImg = ImageIO.read(new File("src/green.png")); // 초록색 점 이미지 파일 읽기
            Image scaledGreenDotImg = greenDotImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon greenDotIcon = new ImageIcon(scaledGreenDotImg); // 이미지 아이콘 생성

            JLabel greenDotLabel = new JLabel(greenDotIcon); // 초록색 점 라벨 생성
            greenDotLabel.setBounds(greenDotX + 15, greenDotY, 10, 10); // 초록색 점 라벨 위치 및 크기 설정
            greenDotLabel.setVisible(false); // 초록색 점 라벨 초기 상태 설정
            pane.add(greenDotLabel, JLayeredPane.PALETTE_LAYER); // 초록색 점 라벨을 레이어드 팬에 추가
            greenDots[dotIndex] = greenDotLabel; // 초록색 점 라벨 배열에 추가

            BufferedImage blueDotImg = ImageIO.read(new File("src/blue.png")); // 파란색 점 이미지 파일 읽기
            Image scaledBlueDotImg = blueDotImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon blueDotIcon = new ImageIcon(scaledBlueDotImg); // 이미지 아이콘 생성

            JLabel blueDotLabel = new JLabel(blueDotIcon); // 파란색 점 라벨 생성
            blueDotLabel.setBounds(greenDotX + 15, greenDotY, 10, 10); // 파란색 점 라벨 위치 및 크기 설정
            blueDotLabel.setVisible(false); // 파란색 점 라벨 초기 상태 설정
            pane.add(blueDotLabel, JLayeredPane.PALETTE_LAYER); // 파란색 점 라벨을 레이어드 팬에 추가
            blueDots[dotIndex] = blueDotLabel; // 파란색 점 라벨 배열에 추가

        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 음료 선택 버튼을 생성하는 메서드
    private JButton createDrinkButton(Drink drink, int x, int y, int width, int height, int dotIndex) {
        JButton button = new JButton(); // 버튼 생성
        button.setBounds(x, y, width, height); // 버튼 위치 및 크기 설정
        button.setOpaque(false); // 불투명 설정
        button.setContentAreaFilled(false); // 내용 채우기 비활성화
        button.setBorderPainted(false); // 테두리 비활성화

        button.addActionListener(new ActionListener() { // 버튼에 액션 리스너 추가
            public void actionPerformed(ActionEvent e) {
                handleDrinkSelection(drink, dotIndex); // 음료 선택 처리 메서드 호출
            }
        });

        return button; // 버튼 반환
    }

    // 음료 선택 시 동작을 처리하는 메서드
    private void handleDrinkSelection(Drink drink, int dotIndex) {
        if (drink.is_Empty_Stock()) { // 음료가 품절되었는지 확인
            JOptionPane.showMessageDialog(null, "품절된 음료입니다."); // 품절 메시지 표시
            return;
        }

        if (currentAmount >= drink.Price()) { // 현재 투입된 금액이 음료 가격보다 많은지 확인
            currentAmount -= drink.Price(); // 현재 투입된 금액에서 음료 가격을 뺌
            vendingMachine.selectProduct(drink.KoreanName()); // 추가: 자판기의 상태도 업데이트
            drink.reduce_Stock(); // 음료 재고 감소
            amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원"); // 현재 투입된 금액 라벨 업데이트

            if (drink.KoreanName().equals("물")) { // 음료 이름이 "물"인지 확인
                JOptionPane.showMessageDialog(null, drink.KoreanName() + "을 구입했습니다."); // 구매 메시지 표시
            } else {
                JOptionPane.showMessageDialog(null, drink.KoreanName() + "를 구입했습니다."); // 구매 메시지 표시
            }

            // 매출 기록 파일에 저장
            admin.Sales_record(drink.KoreanName(), 1);

            updateGreenDots(); // 초록색 점 업데이트
            updateDrinkAvailability(dotIndex); // 음료의 품절 상태 업데이트
        } else {
            JOptionPane.showMessageDialog(null, "금액이 부족합니다."); // 금액 부족 메시지 표시
        }
    }

    // 초록색 점(구매 가능 상태)을 업데이트하는 메서드
    private void updateGreenDots() {
        for (int i = 0; i < drinks.length; i++) {
            if (currentAmount >= drinks[i].Price() && !drinks[i].is_Empty_Stock()) { // 현재 금액이 음료 가격 이상이고 음료가 품절되지 않은 경우
                greenDots[i].setVisible(true); // 초록색 점을 보이게 설정
            } else {
                greenDots[i].setVisible(false); // 초록색 점을 보이지 않게 설정
            }
        }
    }

    // 음료의 품절 상태를 업데이트하는 메서드
    private void updateDrinkAvailability(int dotIndex) {
        if (drinks[dotIndex].is_Empty_Stock()) { // 음료가 품절된 경우
            blueDots[dotIndex].setVisible(true); // 파란색 점을 보이게 설정
            drinkButtons[dotIndex].setEnabled(false); // 음료 버튼 비활성화
            greenDots[dotIndex].setVisible(false); // 초록색 점을 보이지 않게 설정
        } else {
            blueDots[dotIndex].setVisible(false); // 파란색 점을 보이지 않게 설정
            drinkButtons[dotIndex].setEnabled(true); // 음료 버튼 활성화
            updateGreenDots(); // 초록색 점 업데이트
        }
    }


    // 빨간 원 버튼을 추가하는 메서드 (동전 투입 버튼)
    private JButton addRedCircleButton(JLayeredPane pane, int x, int y, int width, int height) {
        JButton button = new JButton(); // 버튼 생성
        button.setBounds(x, y, width, height); // 버튼 위치 및 크기 설정
        button.setOpaque(false); // 불투명 설정
        button.setContentAreaFilled(false); // 내용 채우기 비활성화
        button.setBorderPainted(false); // 테두리 비활성화

        try {
            BufferedImage buttonIcon = ImageIO.read(new File("src/coin.png")); // 이미지 파일 읽기
            Image scaledIcon = buttonIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 이미지 크기 조정
            button.setIcon(new ImageIcon(scaledIcon)); // 버튼 아이콘 설정
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }

        button.addActionListener(new ActionListener() { // 버튼에 액션 리스너 추가
            public void actionPerformed(ActionEvent e) {
                openNewWindow(); // 새로운 창 열기
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER); // 버튼을 레이어드 팬에 추가
        return button; // 버튼 반환
    }

    // 반환 버튼을 추가하는 메서드
    private JButton addReturnButton(JLayeredPane pane, int x, int y, int buttonWidth, int buttonHeight, int imageWidth, int imageHeight) {
        JButton button = new JButton(); // 버튼 생성
        button.setBounds(x, y, buttonWidth, buttonHeight); // 버튼 위치 및 크기 설정
        button.setOpaque(false); // 불투명 설정
        button.setContentAreaFilled(false); // 내용 채우기 비활성화
        button.setBorderPainted(false); // 테두리 비활성화

        try {
            BufferedImage buttonIcon = ImageIO.read(new File("src/return.png")); // 이미지 파일 읽기
            Image scaledIcon = buttonIcon.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH); // 이미지 크기 조정
            button.setIcon(new ImageIcon(scaledIcon)); // 버튼 아이콘 설정
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }

        button.addActionListener(new ActionListener() { // 버튼에 액션 리스너 추가
            public void actionPerformed(ActionEvent e) {
                returnCoins(); // 동전 반환
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER); // 버튼을 레이어드 팬에 추가
        return button; // 버튼 반환
    }

    // 동전을 반환하는 메서드
    private void returnCoins() {
    	vendingMachine.returnCoins(); // 자판기에서 동전 반환
        currentAmount = vendingMachine.getCurrentAmount(); // 현재 투입된 금액을 정확히 반영
        amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원"); // 현재 투입된 금액 라벨 업데이트
        updateGreenDots(); // 초록색 점 업데이트
        if (currentAmount > 0) {
            openNewWindow(); // 화폐 입력 창을 열기
        }
    }

    // 새로운 창을 여는 메서드 (동전 투입 창)
    private void openNewWindow() {
        JFrame newFrame = new JFrame("현금 투입"); // 새 프레임 생성
        newFrame.setSize(700, 250); // 프레임 크기 설정
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정

        JLayeredPane newPane = new JLayeredPane(); // 새 레이어드 팬 생성
        newPane.setPreferredSize(new Dimension(700, 250)); // 레이어드 팬 크기 설정

        // 각 버튼에 맞는 이미지 경로 지정하여 화폐 투입 버튼 추가
        addMoneyButton(newPane, 10, 45, 30, 70, 70, "src/10.png", 70, 70);   
        addMoneyButton(newPane, 50, 130, 7, 120, 120, "src/50.png", 120, 120);  
        addMoneyButton(newPane, 100, 265, 30, 70, 70, "src/100.png", 70, 70); 
        addMoneyButton(newPane, 500, 375, 30, 70, 70, "src/500.png", 70, 70); 
        addMoneyButton(newPane, 1000, 485, 30, 150, 70, "src/1000.png", 150, 70); 

        // 투입 완료 버튼 추가
        JButton completeButton = new JButton("투입완료");
        completeButton.setBounds(540, 140, 120, 50); // 버튼 위치 및 크기 설정
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame.dispose(); // 새 프레임 닫기
                updateGreenDots(); // 초록색 점 업데이트
            }
        });
        newPane.add(completeButton, JLayeredPane.PALETTE_LAYER); // 투입 완료 버튼을 레이어드 팬에 추가

        newFrame.add(newPane); // 새 프레임에 레이어드 팬 추가
        newFrame.pack(); // 프레임 크기 조정
        newFrame.setVisible(true); // 프레임 표시
    }

    // 화폐 투입 버튼을 추가하는 메서드
    private void addMoneyButton(JLayeredPane pane, int amount, int x, int y, int buttonWidth, int buttonHeight, String imagePath, int imageWidth, int imageHeight) {
        JButton button = new JButton(); // 버튼 생성
        button.setBounds(x, y, buttonWidth, buttonHeight); // 버튼 위치 및 크기 설정
        button.setOpaque(false); // 불투명 설정
        button.setContentAreaFilled(false); // 내용 채우기 비활성화
        button.setBorderPainted(false); // 테두리 비활성화

        try {
            BufferedImage buttonIcon = ImageIO.read(new File(imagePath)); // 이미지 파일 읽기
            Image scaledIcon = buttonIcon.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH); // 이미지 크기 조정
            button.setIcon(new ImageIcon(scaledIcon)); // 버튼 아이콘 설정
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }

        button.addActionListener(new ActionListener() { // 버튼에 액션 리스너 추가
            public void actionPerformed(ActionEvent e) {
                int newAmount = currentAmount + amount; // 새로운 투입 금액 계산
                if ((amount == 1000 && currentAmount + amount <= MAX_CASH_LIMIT) || // 1000원일 경우 최대 현금 투입 한도 확인
                    (amount != 1000 && newAmount <= MAX_TOTAL_LIMIT)) { // 1000원이 아닐 경우 최대 총 투입 한도 확인
                    currentAmount += amount; // 현재 투입 금액 업데이트
                    vendingMachine.insertCoin(amount); // 자판기에 동전 삽입
                    amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원"); // 현재 투입 금액 라벨 업데이트
                } else {
                    JOptionPane.showMessageDialog(null, "입력할 수 있는 금액의 상한선을 초과했습니다."); // 한도 초과 메시지 표시
                }
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER); // 버튼을 레이어드 팬에 추가
    }

    // 자판기 버튼 비활성화 메서드
    private void disableVendingMachineButtons() {
        for (JButton button : drinkButtons) { // 모든 음료 버튼 비활성화
            button.setEnabled(false);
        }
        amountLabel.setEnabled(false); // 금액 라벨 비활성화
        insertCoinButton.setEnabled(false); // 동전 투입 버튼 비활성화
        returnButton.setEnabled(false); // 반환 버튼 비활성화
    }

    // 자판기 버튼 활성화 메서드
    private void enableVendingMachineButtons() {
        for (JButton button : drinkButtons) { // 모든 음료 버튼 활성화
            button.setEnabled(true);
        }
        amountLabel.setEnabled(true); // 금액 라벨 활성화
        insertCoinButton.setEnabled(true); // 동전 투입 버튼 활성화
        returnButton.setEnabled(true); // 반환 버튼 활성화
    }

    public void switchToAdminMode() {
        JPasswordField passwordField = new JPasswordField(); // 비밀번호 입력 필드 생성
        JLabel instructionLabel = new JLabel("특수문자, 숫자가 각각 하나 이상 포함된8자리 이상의 영문으로 입력하세요."); // 설명 문구 라벨 생성
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(instructionLabel, BorderLayout.NORTH);
        panel.add(passwordField, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(
                mainFrame, panel, "비밀번호를 입력하세요", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); // 비밀번호 입력 대화상자 표시

        if (option == JOptionPane.OK_OPTION) { // 확인 버튼을 누른 경우
            String inputPassword = new String(passwordField.getPassword()); // 입력된 비밀번호 가져오기
            if (admin.checkPassword(inputPassword)) { // 비밀번호 확인
                disableVendingMachineButtons(); // 자판기 버튼 비활성화
                showAdminFrame(); // 관리자 프레임 표시
            } else {
                JOptionPane.showMessageDialog(mainFrame, "비밀번호가 틀렸습니다."); // 비밀번호 오류 메시지 표시
            }
        }
    }

    // 관리자 모드 프레임을 표시하는 메서드
    private void showAdminFrame() {
    adminFrame = new JFrame("관리자 모드"); // 관리자 프레임 생성
    adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정
    adminFrame.setSize(800, 600); // 프레임 크기 설정

    adminFrame.addWindowListener(new java.awt.event.WindowAdapter() { // 프레임 창 닫기 이벤트 리스너 추가
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            enableVendingMachineButtons(); // 자판기 버튼 활성화
        }
    });

    JPanel adminPanel = new JPanel(); // 관리자 패널 생성
    adminPanel.setLayout(new GridLayout(0, 2)); // 그리드 레이아웃 설정

    // 매출 출력 버튼 생성
    JButton showSalesButton = new JButton("매출 출력");
    showSalesButton.addActionListener(e -> showSalesFrame()); // 클릭 시 새로운 창 열기
    adminPanel.add(showSalesButton); // 관리자 패널에 추가

    JButton checkStockButton = new JButton("재고 현황"); // 재고 현황 버튼 생성
    checkStockButton.addActionListener(e -> showStockStatus()); // 액션 리스너 추가
    adminPanel.add(checkStockButton); // 관리자 패널에 추가

    JButton refillStockButton = new JButton("재고 보충"); // 재고 보충 버튼 생성
    refillStockButton.addActionListener(e -> showRefillStockFrame()); // 액션 리스너 추가
    adminPanel.add(refillStockButton); // 관리자 패널에 추가

    JButton coinStatusButton = new JButton("화폐 현황"); // 화폐 현황 버튼 생성
    coinStatusButton.addActionListener(e -> admin.checkCoin(vendingMachine)); // 액션 리스너 추가
    adminPanel.add(coinStatusButton); // 관리자 패널에 추가

    JButton refillCoinsButton = new JButton("화폐 보충"); // 화폐 보충 버튼 생성
    refillCoinsButton.addActionListener(e -> showRefillCoinsFrame()); // 액션 리스너 추가
    adminPanel.add(refillCoinsButton); // 관리자 패널에 추가

    JButton changeProductDetailsButton = new JButton("제품 정보 변경"); // 제품 정보 변경 버튼 생성
    changeProductDetailsButton.addActionListener(e -> showChangeProductDetailsFrame()); // 액션 리스너 추가
    adminPanel.add(changeProductDetailsButton); // 관리자 패널에 추가

    JButton collectCoinsButton = new JButton("수금"); // 수금 버튼 생성
    collectCoinsButton.addActionListener(e -> admin.collectCoin(vendingMachine)); // 액션 리스너 추가
    adminPanel.add(collectCoinsButton); // 관리자 패널에 추가

    JButton backButton = new JButton("돌아가기"); // 돌아가기 버튼 생성
    backButton.addActionListener(e -> {
        adminFrame.dispose(); // 관리자 프레임 닫기
        enableVendingMachineButtons(); // 자판기 버튼 활성화
        updateProductLabels(); // 제품 라벨 업데이트
    });
    adminPanel.add(backButton); // 관리자 패널에 추가

    adminFrame.add(adminPanel, BorderLayout.CENTER); // 관리자 패널을 관리자 프레임에 추가
    adminFrame.setVisible(true); // 관리자 프레임 표시
}

    // 매출 관련 버튼들이 있는 새로운 창을 여는 메서드
	private void showSalesFrame() {
    JFrame salesFrame = new JFrame("매출 출력");
    salesFrame.setSize(400, 400);
    salesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel salesPanel = new JPanel();
    salesPanel.setLayout(new GridLayout(3, 2));

    JButton dailySalesButton = new JButton("일별 매출 수량 출력"); // 일별 매출 버튼 생성
    dailySalesButton.addActionListener(e -> showSalesData("일별 매출", admin.DailySalesVolume())); // 액션 리스너 추가
    salesPanel.add(dailySalesButton); // 매출 패널에 추가

    JButton monthlySalesButton = new JButton("월별 매출 수량 출력"); // 월별 매출 버튼 생성
    monthlySalesButton.addActionListener(e -> showSalesData("월별 매출", admin.MonthlySalesVolume())); // 액션 리스너 추가
    salesPanel.add(monthlySalesButton); // 매출 패널에 추가

    JButton dailySalesAmountButton = new JButton("일별 매출 금액 출력"); // 일별 매출 금액 버튼 생성
    dailySalesAmountButton.addActionListener(e -> showSalesData("일별 금액", admin.DailySalesAmount())); // 액션 리스너 추가
    salesPanel.add(dailySalesAmountButton); // 매출 패널에 추가

    JButton monthlySalesAmountButton = new JButton("월별 매출 금액 출력"); // 월별 매출 금액 버튼 생성
    monthlySalesAmountButton.addActionListener(e -> showSalesData("월별 금액", admin.MonthlySalesAmount())); // 액션 리스너 추가
    salesPanel.add(monthlySalesAmountButton); // 매출 패널에 추가

    JButton totalSalesButton = new JButton("총 매출 수량"); // 총 매출 수량 버튼 생성
    totalSalesButton.addActionListener(e -> showSalesData("총 매출 수량", admin.TotalSales_Volume())); // 액션 리스너 추가
    salesPanel.add(totalSalesButton); // 매출 패널에 추가

    JButton totalSalesAmountButton = new JButton("총 매출 금액"); // 총 매출 금액버튼 생성
    totalSalesAmountButton.addActionListener(e -> showSalesData("총 매출 금액", admin.TotalSales_Amount())); // 액션 리스너 추가
    salesPanel.add(totalSalesAmountButton); // 매출 패널에 추가

    salesFrame.add(salesPanel);
    salesFrame.setVisible(true);
}


    // 매출 데이터를 표시하는 메서드
    private void showSalesData(String title, String data) {
        JTextArea textArea = new JTextArea(data); // 텍스트 영역 생성
        textArea.setEditable(false); // 텍스트 영역 수정 불가 설정
        JScrollPane scrollPane = new JScrollPane(textArea); // 스크롤 패널 생성
        scrollPane.setPreferredSize(new Dimension(800, 600)); // 스크롤 패널 크기 설정

        JFrame frame = new JFrame(title); // 새 프레임 생성
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정
        frame.add(scrollPane); // 스크롤 패널을 새 프레임에 추가
        frame.pack(); // 프레임 크기 조정
        frame.setVisible(true); // 프레임 표시
    }

    // 재고 상태를 표시하는 메서드
    private void showStockStatus() {
        StringBuilder stockStatus = new StringBuilder(); // 문자열 빌더 생성
        for (Drink drink : drinks) { // 모든 음료에 대해
            stockStatus.append(drink.KoreanName()).append(": ").append(drink.Stock()).append("개\n"); // 재고 상태 추가
        }
        JOptionPane.showMessageDialog(mainFrame, stockStatus.toString(), "재고 현황", JOptionPane.INFORMATION_MESSAGE); // 재고 상태 메시지 표시
    }

    // 재고 보충 프레임을 표시하는 메서드
	private void showRefillStockFrame() {
	    if (adminFrame != null) {
	        adminFrame.dispose(); // 기존의 관리자 모드 창을 닫음
	    }
	
	    refillStockFrame = new JFrame("재고 보충"); // 새 프레임 생성
	    refillStockFrame.setSize(400, 300); // 프레임 크기 설정
	    refillStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정
	
	    refillStockFrame.addWindowListener(new java.awt.event.WindowAdapter() { // 프레임 창 닫기 이벤트 리스너 추가
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	            showAdminFrame(); // 창이 닫힐 때 관리자 모드 창을 다시 열기
	        }
	    });
	
	    JPanel refillStockPanel = new JPanel(); // 재고 보충 패널 생성
	    refillStockPanel.setLayout(new GridLayout(0, 2)); // 그리드 레이아웃 설정
	
	    for (int i = 0; i < drinks.length; i++) {
	        int index = i;
	        JButton button = new JButton(drinks[i].KoreanName()); // 음료 이름 버튼 생성
	        button.addActionListener(e -> {
	            admin.refillStock(vendingMachine, drinks[index].KoreanName(), 10); // 자판기 재고 보충
	            drinks[index].refill_Stock(10); // 음료 객체 재고 업데이트
	            JOptionPane.showMessageDialog(refillStockPanel, drinks[index].KoreanName() + "의 재고가 10개 보충되었습니다."); // 재고 보충 메시지 표시
	            refillStockFrame.dispose(); // 재고 보충 창을 닫기
	            updateDrinkAvailability(index); // 음료의 품절 상태 업데이트
	            showAdminFrame(); // 관리자 모드 창을 다시 열기
	        });
	        refillStockPanel.add(button); // 버튼을 패널에 추가
	    }
	
	    refillStockFrame.add(refillStockPanel, BorderLayout.CENTER); // 패널을 프레임에 추가
	    refillStockFrame.setVisible(true); // 프레임 표시
	}

    // 화폐 보충 프레임을 표시하는 메서드
    private void showRefillCoinsFrame() {
        if (adminFrame != null) {
            adminFrame.dispose(); // 기존의 관리자 모드 창을 닫음
        }

        refillCoinsFrame = new JFrame("화폐 보충"); // 새 프레임 생성
        refillCoinsFrame.setSize(400, 300); // 프레임 크기 설정
        refillCoinsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정

        refillCoinsFrame.addWindowListener(new java.awt.event.WindowAdapter() { // 프레임 창 닫기 이벤트 리스너 추가
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                showAdminFrame(); // 창이 닫힐 때 관리자 모드 창을 다시 열기
            }
        });

        JPanel refillCoinsPanel = new JPanel(); // 화폐 보충 패널 생성
        refillCoinsPanel.setLayout(new GridLayout(0, 2)); // 그리드 레이아웃 설정

        int[] coinValues = {10, 50, 100, 500, 1000}; // 화폐 단위 배열
        for (int coinValue : coinValues) {
            JButton button = new JButton(coinValue + "원"); // 화폐 단위 버튼 생성
            button.addActionListener(e -> {
                admin.refillCoin(vendingMachine, coinValue, 10); // 자판기 화폐 보충
                JOptionPane.showMessageDialog(refillCoinsPanel, coinValue + "원 화폐가 10개 보충되었습니다."); // 화폐 보충 메시지 표시
                refillCoinsFrame.dispose(); // 화폐 보충 창을 닫기
                showAdminFrame(); // 관리자 모드 창을 다시 열기
            });
            refillCoinsPanel.add(button); // 버튼을 패널에 추가
        }

        refillCoinsFrame.add(refillCoinsPanel, BorderLayout.CENTER); // 패널을 프레임에 추가
        refillCoinsFrame.setVisible(true); // 프레임 표시
    }

    // 제품 정보 변경 프레임을 표시하는 메서드
    private void showChangeProductDetailsFrame() {
        changeProductDetailsFrame = new JFrame("제품 정보 변경"); // 새 프레임 생성
        changeProductDetailsFrame.setSize(400, 300); // 프레임 크기 설정
        changeProductDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 동작 설정

        JPanel changeProductDetailsPanel = new JPanel(); // 제품 정보 변경 패널 생성
        changeProductDetailsPanel.setLayout(new GridLayout(0, 2)); // 그리드 레이아웃 설정

        for (int i = 0; i < drinks.length; i++) {
            int index = i;
            JButton button = new JButton(drinks[i].KoreanName()); // 음료 이름 버튼 생성
            button.addActionListener(e -> changeProductDetails(drinks[index].KoreanName())); // 액션 리스너 추가
            changeProductDetailsPanel.add(button); // 버튼을 패널에 추가
        }

        changeProductDetailsFrame.add(changeProductDetailsPanel, BorderLayout.CENTER); // 패널을 프레임에 추가
        changeProductDetailsFrame.setVisible(true); // 프레임 표시
    }

    // 제품 정보를 변경하는 메서드
    private void changeProductDetails(String oldProductName) {
        String newProductName = JOptionPane.showInputDialog("새로운 제품 이름을 입력하세요:"); // 새로운 제품 이름 입력 대화상자 표시
        if (newProductName != null && !newProductName.isEmpty()) { // 유효한 입력 확인
            String newPriceStr = JOptionPane.showInputDialog("새로운 제품 가격을 입력하세요:"); // 새로운 제품 가격 입력 대화상자 표시
            int newPrice;
            try {
                newPrice = Integer.parseInt(newPriceStr); // 가격 문자열을 정수로 변환
                admin.changeProduct(vendingMachine, oldProductName, newProductName, newPrice); // 제품 정보 변경
                updateProductLabels(); // 제품 라벨 업데이트
                changeProductDetailsFrame.dispose(); // 제품 정보 변경 창을 닫기
                if (adminFrame != null) {
                    adminFrame.dispose(); // 관리자 모드 창을 닫기
                }
                showAdminFrame(); // 관리자 모드 창을 다시 열기
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainFrame, "유효한 숫자를 입력하세요."); // 숫자 형식 오류 메시지 표시
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "유효한 새로운 제품 이름을 입력하세요."); // 이름 형식 오류 메시지 표시
        }
    }

    // 제품 정보를 업데이트하는 메서드
    private void updateProductLabels() {
        for (int i = 0; i < drinks.length; i++) {
            Product product = vendingMachine.getProducts().get(i); // 자판기에서 제품 가져오기
            drinks[i] = new Drink(product); // Product 객체를 Drink 객체로 변환

            // 제품 이름 및 가격 라벨 업데이트
            nameLabels[i].setText(drinks[i].KoreanName());
            priceLabels[i].setText(drinks[i].Price() + "");
        }
    }

    // 메인 메서드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI()); // GUI 생성
    }
}
