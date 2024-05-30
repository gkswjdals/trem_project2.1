package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VendingMachineGUI {
    private VendingMachine vendingMachine;
    private Admin admin;
    private Integer currentAmount = 0;
    private JFrame mainFrame;
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

    public VendingMachineGUI() {
        vendingMachine = new VendingMachine();
        admin = new Admin("admin123!");
        greenDots = new JLabel[drinks.length];
        blueDots = new JLabel[drinks.length];
        drinkButtons = new JButton[drinks.length];
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("Vending Machine");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        JLayeredPane layeredPane = new VendingMachineUI();

        addDrink(layeredPane, drinks[0], 290, 55, 30, 75, "src/water.png", 273, 129, 65, 20, 285, 150, 40, 10, 270, 150, 0);
        addDrink(layeredPane, drinks[1], 348, 55, 40, 78, "src/coffee.png", 337, 129, 65, 20, 348, 150, 40, 10, 335, 150, 1);
        addDrink(layeredPane, drinks[2], 418, 55, 40, 78, "src/sport.png", 403, 129, 65, 20, 418, 150, 40, 10, 402, 149, 2);
        addDrink(layeredPane, drinks[3], 480, 52, 43, 80, "src/high.png", 471, 129, 65, 20, 480, 150, 40, 10, 470, 150, 3);
        addDrink(layeredPane, drinks[4], 287, 164, 35, 76, "src/soda.png", 274, 236, 65, 20, 287, 255, 40, 10, 270, 258, 4);
        addDrink(layeredPane, drinks[5], 352, 164, 33, 73, "src/Special.png", 333, 236, 73, 20, 350, 255, 40, 10, 335, 257, 5);

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

            JLabel nameLabel = new JLabel(drink.getName());
            nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 10));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            pane.add(nameLabel, JLayeredPane.PALETTE_LAYER);

            JLabel priceLabel = new JLabel(drink.getPrice() + "");
            priceLabel.setBounds(labelX + 2, labelY + 16, labelWidth, labelHeight);
            priceLabel.setFont(new Font("Arial", Font.BOLD, 10));
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
        JFrame newFrame = new JFrame("Insert Money");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendingMachineGUI());
    }
}
