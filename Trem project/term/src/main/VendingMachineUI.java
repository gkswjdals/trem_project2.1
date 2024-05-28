package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VendingMachineUI extends JLayeredPane {
    public VendingMachineUI() {
        setPreferredSize(new Dimension(800, 600));
        addBackground();
    }

    private void addBackground() {
        JLabel background = new JLabel(new ImageIcon("src/main.png"));
        background.setBounds(0, 0, 800, 600);
        add(background, JLayeredPane.DEFAULT_LAYER);
    }

    public void addDrink(String name, int imgX, int imgY, int imgWidth, int imgHeight, String imagePath, int labelX, int labelY, int labelWidth, int labelHeight) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(imgX, imgY, imgWidth, imgHeight);
            imageLabel.setOpaque(false);
            add(imageLabel, JLayeredPane.PALETTE_LAYER);

            JButton button = new JButton(name);
            button.setBounds(labelX, labelY, labelWidth, labelHeight);
            Font buttonFont = new Font("Arial", Font.BOLD, 10);
            button.setFont(buttonFont);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(Color.BLACK);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            add(button, JLayeredPane.PALETTE_LAYER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
