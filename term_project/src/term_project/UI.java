package term_project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// UI 클래스 정의, JLayeredPane을 상속받음
public class UI extends JLayeredPane {
    // 생성자: UI 클래스의 인스턴스를 생성할 때 기본 설정 수행
    public UI() {
        setPreferredSize(new Dimension(800, 600)); // 패널의 기본 크기 설정
        addBackground(); // 배경 이미지 추가 메소드 호출
    }

    // 배경 이미지를 추가하는 메소드
    private void addBackground() {
        JLabel background = new JLabel(new ImageIcon("src/main.png")); // 배경 이미지 로드
        background.setBounds(0, 0, 800, 600); // 배경 이미지 위치 및 크기 설정
        add(background, JLayeredPane.DEFAULT_LAYER); // 배경 이미지를 기본 레이어에 추가
    }

    // 음료 이미지를 추가하는 메소드
    public void addDrink(String name, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight, int price) {
        try {
            // 이미지 파일을 읽어와서 크기를 조정한 후 아이콘으로 설정
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            // 이미지 라벨 생성 및 설정
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight);
            imageLabel.setOpaque(false);
            add(imageLabel, JLayeredPane.PALETTE_LAYER); // 이미지 라벨을 PALETTE_LAYER에 추가

            // 음료 버튼 생성 및 설정
            JButton button = new JButton(name);
            button.setBounds(labelX, labelY, labelWidth, labelHeight);
            Font buttonFont = new Font("Arial", Font.BOLD, 10); // 버튼 글꼴 설정
            button.setFont(buttonFont);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(Color.BLACK); // 버튼 글꼴 색상 설정
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            add(button, JLayeredPane.PALETTE_LAYER); // 버튼을 PALETTE_LAYER에 추가

            // 가격 라벨 생성 및 설정
            JLabel priceLabel = new JLabel(price + " 원");
            priceLabel.setBounds(labelX, labelY + 10, labelWidth, labelHeight); // 위치 및 크기 설정
            priceLabel.setFont(buttonFont);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setVerticalAlignment(SwingConstants.CENTER);
            priceLabel.setForeground(Color.WHITE); // 가격 라벨 글꼴 색상 설정
            add(priceLabel, JLayeredPane.PALETTE_LAYER); // 가격 라벨을 PALETTE_LAYER에 추가
        } catch (IOException e) {
            e.printStackTrace(); // 이미지 파일 읽기 오류 처리
        }
    }
}
