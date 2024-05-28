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
    private int currentAmount = 0;
    private JFrame mainFrame;
    private JLabel amountLabel;

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
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("Vending Machine");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // 배경 이미지 추가
        JLabel background = new JLabel(new ImageIcon("src/main.png"));
        background.setBounds(0, 0, 800, 600);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // 음료수 버튼 추가
        addDrink(layeredPane, drinks[0], 290, 55, 30, 75, "src/water.png", 273, 129, 65, 20, 290, 150, 65, 20);
        addDrink(layeredPane, drinks[1], 348, 55, 40, 78, "src/coffee.png", 337, 129, 65, 20, 348, 150, 65, 20);
        addDrink(layeredPane, drinks[2], 418, 55, 40, 78, "src/eon.png", 403, 129, 65, 20, 418, 150, 65, 20);
        addDrink(layeredPane, drinks[3], 480, 52, 43, 80, "src/high_coffee.png", 471, 129, 65, 20, 480, 150, 65, 20);
        addDrink(layeredPane, drinks[4], 287, 164, 35, 76, "src/sparkle.png", 274, 236, 65, 20, 287, 260, 65, 20);
        addDrink(layeredPane, drinks[5], 352, 164, 33, 73, "src/monster.png", 333, 236, 73, 20, 352, 260, 73, 20);

        // 빨간색 동그라미 버튼 추가
        addRedCircleButton(layeredPane, 474, 308, 30, 30);

        // 투입 금액 표시 라벨 추가
        amountLabel = new JLabel("현재 투입된 금액 : " + currentAmount + " 원");
        amountLabel.setBounds(600, 20, 200, 30);
        layeredPane.add(amountLabel, JLayeredPane.PALETTE_LAYER);

        mainFrame.add(layeredPane);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void addDrink(JLayeredPane pane, Drink drink, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight, int buttonX, int buttonY, int buttonWidth, int buttonHeight) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            // 음료 이미지 라벨 추가
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight);
            imageLabel.setOpaque(false);
            pane.add(imageLabel, JLayeredPane.PALETTE_LAYER);

            // 음료 이름 라벨 추가
            JLabel nameLabel = new JLabel(drink.getName());
            nameLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 10));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            pane.add(nameLabel, JLayeredPane.PALETTE_LAYER);

            // 검정 부분에 버튼 추가
            JButton button = createDrinkButton(drink, buttonX, buttonY, buttonWidth, buttonHeight);
            pane.add(button, JLayeredPane.PALETTE_LAYER);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton createDrinkButton(Drink drink, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // 버튼 클릭 시 동작 추가
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDrinkSelection(drink);
            }
        });

        return button;
    }

    private void handleDrinkSelection(Drink drink) {
        if (currentAmount >= drink.getPrice()) {
            currentAmount -= drink.getPrice();
            amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
            JOptionPane.showMessageDialog(null, drink.getKoreanName() + "를 구입했습니다.");
        } else {
            JOptionPane.showMessageDialog(null, "금액이 부족합니다.");
        }
    }

    private void addRedCircleButton(JLayeredPane pane, int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // 동그라미 모양의 아이콘 설정
        try {
            BufferedImage buttonIcon = ImageIO.read(new File("src/red.png")); // 동그라미 아이콘 파일 경로
            Image scaledIcon = buttonIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 빨간색 동그라미 버튼 클릭 시 새 창 열기
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewWindow();
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void openNewWindow() {
        JFrame newFrame = new JFrame("Insert Money");
        newFrame.setSize(700, 250);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLayeredPane newPane = new JLayeredPane();
        newPane.setPreferredSize(new Dimension(700, 250));

        // 돈 버튼 추가
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
                newFrame.dispose();
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
                currentAmount += amount;
                amountLabel.setText("현재 투입된 금액 : " + currentAmount + " 원");
            }
        });

        pane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendingMachineGUI());
    }
}
