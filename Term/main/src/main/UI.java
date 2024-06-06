package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// UI 클래스 정의, JLayeredPane을 상속받음
public class UI extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	private GUI mainGUI; // 메인 GUI 인스턴스를 참조하기 위한 변수

    // 생성자: UI 클래스의 인스턴스를 생성할 때 기본 설정 수행
    public UI(GUI mainGUI) {
        this.mainGUI = mainGUI; // mainGUI 참조를 저장
        setPreferredSize(new Dimension(800, 600)); // 패널의 기본 크기 설정
        addBackground(); // 배경 이미지 추가 메소드 호출
        addAdminArea(); // 관리자 영역 추가 메소드 호출
    }

    // 배경 이미지를 추가하는 메소드
    private void addBackground() {
        JLabel background = new JLabel(new ImageIcon("src/main.png")); // 배경 이미지 로드
        background.setBounds(0, 0, 800, 600); // 배경 이미지 위치 및 크기 설정
        add(background, JLayeredPane.DEFAULT_LAYER); // 배경 이미지를 기본 레이어에 추가
    }

    // 관리자 영역을 추가하는 메소드
    private void addAdminArea() {
        try {
            BufferedImage img = ImageIO.read(new File("src/square.png")); // 관리자 영역 이미지 로드
            Image scaledImg = img.getScaledInstance(125, 125, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon icon = new ImageIcon(scaledImg); // 이미지 아이콘 생성

            JButton adminButton = new JButton(icon); // 관리자 버튼 생성
            adminButton.setBounds(265, 305, 125, 125); // 버튼 위치 및 크기 설정
            adminButton.setOpaque(false); // 버튼 불투명 설정
            adminButton.setContentAreaFilled(false); // 버튼 내용 영역 채우기 비활성화
            adminButton.setBorderPainted(false); // 버튼 테두리 그리기 비활성화

            // 관리자 버튼에 액션 리스너 추가
            adminButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainGUI.switchToAdminMode(); // 관리자 모드로 전환
                }
            });

            add(adminButton, JLayeredPane.PALETTE_LAYER); // 관리자 버튼을 PALETTE_LAYER에 추가
        } catch (IOException e) {
            e.printStackTrace(); // 이미지 파일 읽기 오류 처리
        }
    }
}
