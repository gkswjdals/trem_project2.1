package term_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUI {
    private VendingMachine vendingMachine;
    private Admin admin;
    private Integer currentAmount = 0;
    private JFrame mainFrame;
    private JFrame changeProductDetailsFrame;
    private JFrame adminFrame;
    private JLabel amountLabel;
    private JLabel[] greenDots;
    private JLabel[] stockLabels;
    private JLabel[] blueDots;
    private JButton[] drinkButtons;

    private final int MAX_CASH_LIMIT = 5000;
    private final int MAX_TOTAL_LIMIT = 7000;

    private Drink[] drinks = {
        new Drink("water", "물", 450),
        new Drink("coffee", "커피", 500),
        new Drink("sport", "이온음료", 550),
        new Drink("high", "고급커피", 700),
        new Drink("Soda", "탄산음료", 750),
        new Drink("Special", "특화음료", 800)
    };

    public GUI() {
        vendingMachine = new VendingMachine();
        admin = new Admin("admin123!");
        greenDots = new JLabel[drinks.length];
        blueDots = new JLabel[drinks.length];
        drinkButtons = new JButton[drinks.length];
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("자판기");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        JLayeredPane layeredPane = new UI(this); // UI 인스턴스를 생성할 때 GUI 인스턴스를 전달

        addDrink(layeredPane, drinks[0], 290, 55, 30, 75, "src/water.png", 273, 129, 65, 20, 285, 150, 40, 10, 270, 150, 0);
        addDrink(layeredPane, drinks[1], 348, 55, 40, 78, "src/coffee.png", 337, 129, 65, 20, 348, 150, 40, 10, 335, 150, 1);
        addDrink(layeredPane, drinks[2], 418, 55, 40, 78, "src/sport.png", 405, 129, 65, 20, 418, 150, 40, 10, 402, 149, 2);
        addDrink(layeredPane, drinks[3], 480, 52, 43, 80, "src/high.png", 471, 129, 65, 20, 480, 150, 40, 10, 470, 150, 3);
        addDrink(layeredPane, drinks[4], 287, 164, 35, 76, "src/soda.png", 273, 235, 65, 20, 287, 255, 40, 10, 270, 258, 4);
        addDrink(layeredPane, drinks[5], 352, 164, 33, 73, "src/Special.png", 333, 235, 73, 20, 350, 255, 40, 10, 335, 257, 5);

        addRedCircleButton(layeredPane, 474, 308, 30, 30);

        amountLabel = new JLabel("현재 투입된 금액 : " + currentAmount + " 원");
        amountLabel.setBounds(600, 20, 200, 30);
        layeredPane.add(amountLabel, JLayeredPane.PALETTE_LAYER);

        // 반환 버튼 추가
        addReturnButton(layeredPane, 400, 382, 45, 22);

        mainFrame.add(layeredPane);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void addDrink(JLayeredPane pane, Drink drink, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight, int buttonX, int buttonY, int buttonWidth, int buttonHeight, int greenDotX, int greenDotY, int dotIndex) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight);
            imageLabel.setOpaque(false);
            pane.add(imageLabel, JLayeredPane.PALETTE_LAYER);

            JLabel nameLabel = new JLabel(drink.getKoreanName());
            nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
            nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 10));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            pane.add(nameLabel, JLayeredPane.PALETTE_LAYER);

            JLabel priceLabel = new JLabel(drink.getPrice() + "");
            priceLabel.setBounds(labelX + 2, labelY + 16, labelWidth, labelHeight);
            priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 10));
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setVerticalAlignment(SwingConstants.CENTER);
            priceLabel.setForeground(Color.WHITE);
            pane.add(priceLabel, JLayeredPane.PALETTE_LAYER);

            JButton button = createDrinkButton(drink, buttonX, buttonY, buttonWidth, buttonHeight, dotIndex);
            pane.add(button, JLayeredPane.PALETTE_LAYER);
            drinkButtons[dotIndex] = button;

            BufferedImage greenDotImg = ImageIO.read(new File("src/green.png"));
            Image scaledGreenDotImg = greenDotImg.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
            ImageIcon greenDotIcon = new ImageIcon(scaledGreenDotImg);

            JLabel greenDotLabel = new JLabel(greenDotIcon);
            greenDotLabel.setBounds(greenDotX + 15, greenDotY, 10, 10);
            greenDotLabel.setVisible(false);
            pane.add(greenDotLabel, JLayeredPane.PALETTE_LAYER);
            greenDots[dotIndex] = greenDotLabel;

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

    private JButton createDrinkButton(Drink drink, int x, int y, int width, int height, int dotIndex) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDrinkSelection(drink, dotIndex);
            }
        });

        return button;
    }

    private void handleDrinkSelection(Drink drink, int dotIndex) {
        if (drink.isOutOfStock()) {
            JOptionPane.showMessageDialog(null, "품절된 음료입니다.");
            return;
        }

        if (currentAmount >= drink.getPrice()) {
            currentAmount -= drink.getPrice();
            vendingMachine.selectProduct(drink.getKoreanName()); // 추가: vendingMachine의 상태도 업데이트
            drink.reduceStock();
            amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
            if (drink.getKoreanName().equals("물")) {
                JOptionPane.showMessageDialog(null, drink.getKoreanName() + "을 구입했습니다.");
            } else {
                JOptionPane.showMessageDialog(null, drink.getKoreanName() + "를 구입했습니다.");
            }
            updateGreenDots();
            updateDrinkAvailability(dotIndex);
        } else {
            JOptionPane.showMessageDialog(null, "금액이 부족합니다.");
        }
    }

    private void updateGreenDots() {
        for (int i = 0; i < drinks.length; i++) {
            if (currentAmount >= drinks[i].getPrice() && !drinks[i].isOutOfStock()) {
                greenDots[i].setVisible(true);
            } else {
                greenDots[i].setVisible(false);
            }
        }
    }

    private void updateDrinkAvailability(int dotIndex) {
        if (drinks[dotIndex].isOutOfStock()) {
            blueDots[dotIndex].setVisible(true);
            drinkButtons[dotIndex].setEnabled(false);
            greenDots[dotIndex].setVisible(false);
        } else {
            blueDots[dotIndex].setVisible(false);
            drinkButtons[dotIndex].setEnabled(true);
        }
    }

    private void addRedCircleButton(JLayeredPane pane, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        try {
            BufferedImage buttonIcon = ImageIO.read(new File("src/red.png"));
            Image scaledIcon = buttonIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewWindow();
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void addReturnButton(JLayeredPane pane, int x, int y, int width, int height) {
        JButton button = new JButton("r");
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnCoins();
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void returnCoins() {
        int returnedAmount = vendingMachine.returnCoins();
        currentAmount = vendingMachine.getCurrentAmount(); // 현재 투입된 금액을 정확히 반영
        amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
        updateGreenDots();
    }

    private void openNewWindow() {
        JFrame newFrame = new JFrame("현금 투입");
        newFrame.setSize(700, 250);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLayeredPane newPane = new JLayeredPane();
        newPane.setPreferredSize(new Dimension(700, 250));

        addMoneyButton(newPane, 10, 45, 30, 70, 70);
        addMoneyButton(newPane, 50, 155, 30, 70, 70);
        addMoneyButton(newPane, 100, 265, 30, 70, 70);
        addMoneyButton(newPane, 500, 375, 30, 70, 70);
        addMoneyButton(newPane, 1000, 485, 30, 70, 70);

        JButton completeButton = new JButton("투입완료");
        completeButton.setBounds(540, 140, 120, 50);
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame.dispose();
                updateGreenDots();
            }
        });
        newPane.add(completeButton, JLayeredPane.PALETTE_LAYER);

        newFrame.add(newPane);
        newFrame.pack();
        newFrame.setVisible(true);
    }

    private void addMoneyButton(JLayeredPane pane, int amount, int x, int y, int width, int height) {
        JButton button = new JButton(String.valueOf(amount));
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int newAmount = currentAmount + amount;
                if ((amount == 1000 && currentAmount + amount <= MAX_CASH_LIMIT) ||
                    (amount != 1000 && newAmount <= MAX_TOTAL_LIMIT)) {
                    currentAmount += amount;
                    vendingMachine.insertCoin(amount); 
                    amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
                } else {
                    JOptionPane.showMessageDialog(null, "입력할 수 있는 금액의 상한선을 초과했습니다.");
                }
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    // 관리자 모드로 전환하는 메소드
    public void switchToAdminMode() {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(
                mainFrame, passwordField, "비밀번호를 입력하세요:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String inputPassword = new String(passwordField.getPassword());
            if (admin.checkPassword(inputPassword)) {
                showAdminFrame(); // 관리자 프레임 표시
            } else {
                JOptionPane.showMessageDialog(mainFrame, "비밀번호가 틀렸습니다.");
            }
        }
    }

    private void showAdminFrame() {
        adminFrame = new JFrame("관리자 모드");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setSize(800, 600);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(0, 2));

        JButton dailySalesButton = new JButton("일별 매출 출력");
        dailySalesButton.addActionListener(e -> admin.printDailySales());
        adminPanel.add(dailySalesButton);

        JButton monthlySalesButton = new JButton("월별 매출 출력");
        monthlySalesButton.addActionListener(e -> admin.printMonthlySales());
        adminPanel.add(monthlySalesButton);

        JButton checkStockButton = new JButton("재고 현황");
        checkStockButton.addActionListener(e -> showStockStatus());
        adminPanel.add(checkStockButton);

        JButton refillStockButton = new JButton("재고 보충");
        refillStockButton.addActionListener(e -> showRefillStockFrame());
        adminPanel.add(refillStockButton);

        JButton coinStatusButton = new JButton("화폐 현황");
        coinStatusButton.addActionListener(e -> admin.checkCoinStatus(vendingMachine));
        adminPanel.add(coinStatusButton);

        JButton refillCoinsButton = new JButton("화폐 보충");
        refillCoinsButton.addActionListener(e -> showRefillCoinsFrame());
        adminPanel.add(refillCoinsButton);

        JButton changeProductDetailsButton = new JButton("제품 정보 변경");
        changeProductDetailsButton.addActionListener(e -> showChangeProductDetailsFrame());
        adminPanel.add(changeProductDetailsButton);

        JButton collectCoinsButton = new JButton("수금");
        collectCoinsButton.addActionListener(e -> admin.collectCoins(vendingMachine));
        adminPanel.add(collectCoinsButton);

        JButton backButton = new JButton("돌아가기");
        backButton.addActionListener(e -> adminFrame.dispose());
        adminPanel.add(backButton);

        adminFrame.add(adminPanel, BorderLayout.CENTER);
        adminFrame.setVisible(true);
    }

    private void showStockStatus() {
        StringBuilder stockStatus = new StringBuilder();
        for (Drink drink : drinks) {
            stockStatus.append(drink.getKoreanName()).append(": ").append(drink.getStock()).append("개\n");
        }
        JOptionPane.showMessageDialog(mainFrame, stockStatus.toString(), "재고 현황", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRefillStockFrame() {
        JFrame refillStockFrame = new JFrame("재고 보충");
        refillStockFrame.setSize(400, 300);
        refillStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel refillStockPanel = new JPanel();
        refillStockPanel.setLayout(new GridLayout(0, 2));

        for (int i = 0; i < drinks.length; i++) {
            int index = i;
            JButton button = new JButton(drinks[i].getKoreanName());
            button.addActionListener(e -> {
                admin.refillStock(vendingMachine, drinks[index].getKoreanName(), 10);
                drinks[index].refillStock(10); // Drink 객체의 재고도 업데이트
                JOptionPane.showMessageDialog(refillStockPanel, drinks[index].getKoreanName() + "의 재고가 10개 보충되었습니다.");
                showStockStatus(); // 재고 보충 후 전체 재고 현황을 표시
                updateBlueDots(); // 재고 상태 업데이트
            });
            refillStockPanel.add(button);
        }

        refillStockFrame.add(refillStockPanel, BorderLayout.CENTER);
        refillStockFrame.setVisible(true);
    }




    private void refillStock(String productName) {
        String amountStr = JOptionPane.showInputDialog("추가할 수량을 입력하세요:");
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
            admin.refillStock(vendingMachine, productName, amount);
            showStockStatus(); // 재고 보충 후 전체 재고 현황을 표시
            updateBlueDots(); // 재고 상태 업데이트
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "유효한 숫자를 입력하세요.");
        }
    }

    private void showRefillCoinsFrame() {
        JFrame refillCoinsFrame = new JFrame("화폐 보충");
        refillCoinsFrame.setSize(400, 300);
        refillCoinsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel refillCoinsPanel = new JPanel();
        refillCoinsPanel.setLayout(new GridLayout(0, 2));

        addRefillCoinButton(refillCoinsPanel, 10);
        addRefillCoinButton(refillCoinsPanel, 50);
        addRefillCoinButton(refillCoinsPanel, 100);
        addRefillCoinButton(refillCoinsPanel, 500);
        addRefillCoinButton(refillCoinsPanel, 1000);

        refillCoinsFrame.add(refillCoinsPanel, BorderLayout.CENTER);
        refillCoinsFrame.setVisible(true);
    }

    private void addRefillCoinButton(JPanel panel, int coinValue) {
        JButton button = new JButton(coinValue + "원");
        button.addActionListener(e -> {
            admin.refillCoins(vendingMachine, coinValue, 10);
            JOptionPane.showMessageDialog(panel, coinValue + "원 화폐가 10개 보충되었습니다.");
        });
        panel.add(button);
    }

    private void showChangeProductDetailsFrame() {
        changeProductDetailsFrame = new JFrame("제품 정보 변경");
        changeProductDetailsFrame.setSize(400, 300);
        changeProductDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel changeProductDetailsPanel = new JPanel();
        changeProductDetailsPanel.setLayout(new GridLayout(0, 2));

        for (int i = 0; i < drinks.length; i++) {
            int index = i;
            JButton button = new JButton(drinks[i].getKoreanName());
            button.addActionListener(e -> changeProductDetails(drinks[index].getKoreanName()));
            changeProductDetailsPanel.add(button);
        }

        changeProductDetailsFrame.add(changeProductDetailsPanel, BorderLayout.CENTER);
        changeProductDetailsFrame.setVisible(true);
    }

    private void changeProductDetails(String oldProductName) {
        String newProductName = JOptionPane.showInputDialog("새로운 제품 이름을 입력하세요:");
        if (newProductName != null && !newProductName.isEmpty()) {
            String newPriceStr = JOptionPane.showInputDialog("새로운 제품 가격을 입력하세요:");
            int newPrice;
            try {
                newPrice = Integer.parseInt(newPriceStr);
                admin.changeProductDetails(vendingMachine, oldProductName, newProductName, newPrice);
                updateProductLabels(); // 제품 정보를 업데이트하고
                changeProductDetailsFrame.dispose(); // 제품 정보 변경 창을 닫음
                if (adminFrame != null) {
                    adminFrame.dispose(); // 관리자 모드 창을 닫음
                }
                showAdminFrame(); // 관리자 모드 화면 다시 표시
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainFrame, "유효한 숫자를 입력하세요.");
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "유효한 새로운 제품 이름을 입력하세요.");
        }
    }



    private void updateProductLabels() {
        for (int i = 0; i < drinks.length; i++) {
            Product product = vendingMachine.getProducts().get(i);
            drinks[i] = new Drink(product); // Product 객체를 Drink 객체로 변환
        }
        // 제품 정보 업데이트 후 관리자 모드 화면 새로고침
        if (adminFrame != null) {
            adminFrame.dispose();
        }
        showAdminFrame();
    }



    private void updateBlueDots() {
        for (int i = 0; i < drinks.length; i++) {
            if (drinks[i].isOutOfStock()) {
                blueDots[i].setVisible(true);
                drinkButtons[i].setEnabled(false);
            } else {
                blueDots[i].setVisible(false);
                drinkButtons[i].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}
