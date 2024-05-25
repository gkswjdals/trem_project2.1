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

    public VendingMachineGUI() {
        vendingMachine = new VendingMachine();
        admin = new Admin("admin123!");
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // JLayeredPane 생성
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // 자판기 배경 이미지
        JLabel background = new JLabel(new ImageIcon("C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/main.png"));
        background.setBounds(0, 0, 800, 600);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // 음료수 버튼 추가
        addDrink(layeredPane, "water", 290, 55, 30, 75, "C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/water.png", 273, 129, 65, 20);
        addDrink(layeredPane, "coffee", 348, 55, 40, 78, "C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/coffee.png", 337, 129, 65, 20);
        addDrink(layeredPane, "sport", 418, 55, 40, 78, "C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/eon.png", 403, 129, 65, 20);
        addDrink(layeredPane, "high", 480, 52, 43, 80, "C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/high_coffee.png", 471, 129, 65, 20);
        addDrink(layeredPane, "Soda", 287, 164, 35, 76, "C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/sparkle.png", 274, 236, 65, 20);
        addDrink(layeredPane, "Special", 352, 164, 33, 73, "C:/대학교/2학년 1학기/자바 이론/Trem project/term/src/monster.png", 333, 236, 73, 20);

        
        frame.add(layeredPane);
        frame.pack();
        frame.setVisible(true);
    }

    private void addDrink(JLayeredPane pane, String name, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            // 이미지 라벨 생성
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight);
            imageLabel.setOpaque(false); // 배경 투명 설정
            pane.add(imageLabel, JLayeredPane.PALETTE_LAYER);

         // 버튼 생성
            JButton button = new JButton(name);
            button.setBounds(labelX, labelY, labelWidth, labelHeight);

         // 버튼 폰트 설정
            Font buttonFont = new Font("Arial", Font.BOLD, 10); // 폰트 이름, 스타일(보통은 폰트.BOLD 등), 크기
            button.setFont(buttonFont);
            
            // 버튼 배경색을 투명으로 설정
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            // 버튼 폰트 색상 설정
            button.setForeground(Color.BLACK); // 검정색

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                }
            });


            // 버튼 배경색 및 테두리 설정
            button.setBackground(null);

            // 버튼 텍스트 가운데 정렬
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);

            pane.add(button, JLayeredPane.PALETTE_LAYER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendingMachineGUI());
        
    }
}


