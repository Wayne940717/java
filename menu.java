package com.zetcode;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class menu {
    private menuCallBack callback;
    private Image backgroundImage;

    public void showMenu(menuCallBack callback) {
        this.callback = callback;

        // 載入背景圖片
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\許哲瑋\\Desktop\\java專題\\java2\\java-main\\期末專題\\com\\zetcode\\pacman圖.jpg")); // 替換為背景圖片的實際路徑
        } catch (IOException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // 創建主框架
            JFrame frame = new JFrame("遊戲選單");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            // 標題部分
            JLabel title = new JLabel("Pac-Man", JLabel.CENTER);
            title.setFont(new Font("Serif", Font.BOLD, 30));
            title.setForeground(new Color(0x2D6187)); // 標題顏色
            frame.add(title, BorderLayout.NORTH);

            // 創建自定義按鈕面板
            JPanel buttonPanel = new CustomPanel();
            buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
            buttonPanel.setOpaque(false); // 使背景透明
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

            // 經典小精靈按鈕
            JButton classicPacmanButton = new JButton("經典小精靈");
            classicPacmanButton.setFont(new Font("Serif", Font.BOLD, 18));
            classicPacmanButton.setContentAreaFilled(false); // 移除填充
            classicPacmanButton.setOpaque(false); // 背景透明
            classicPacmanButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // 白色邊框，厚度2
            classicPacmanButton.setForeground(Color.WHITE); // 按鈕文字顏色
            classicPacmanButton.setFocusPainted(false);
            classicPacmanButton.addActionListener(e -> {
                frame.dispose(); // 關閉選單框架
                if (callback != null) {
                    callback.onClassicPacmanSelected();
                }
            });
            buttonPanel.add(classicPacmanButton);

            // 雙人對戰按鈕
            JButton multiplayerButton = new JButton("雙人對戰");
            multiplayerButton.setFont(new Font("Serif", Font.BOLD, 18));
            multiplayerButton.setContentAreaFilled(false);
            multiplayerButton.setOpaque(false);
            multiplayerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            multiplayerButton.setForeground(Color.WHITE);
            multiplayerButton.setFocusPainted(false);
            multiplayerButton.addActionListener(e -> {
                frame.dispose(); // 關閉選單框架
                if (callback != null) {
                    callback.onMultiplayerSelected();
                }
            });
            buttonPanel.add(multiplayerButton);

            // 遊戲說明按鈕
            JButton instructionsButton = new JButton("遊戲說明");
            instructionsButton.setFont(new Font("Serif", Font.BOLD, 18));
            instructionsButton.setContentAreaFilled(false);
            instructionsButton.setOpaque(false);
            instructionsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            instructionsButton.setForeground(Color.WHITE);
            instructionsButton.setFocusPainted(false);
            instructionsButton.addActionListener(e -> showInstructions());
            buttonPanel.add(instructionsButton);

            // 退出按鈕
            JButton exitButton = new JButton("退出");
            exitButton.setFont(new Font("Serif", Font.BOLD, 18));
            exitButton.setContentAreaFilled(false);
            exitButton.setOpaque(false);
            exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            exitButton.setForeground(Color.WHITE);
            exitButton.setFocusPainted(false);
            exitButton.addActionListener(e -> {
                frame.dispose(); // 關閉選單框架
                System.exit(0);
            });
            buttonPanel.add(exitButton);

            // 添加面板到框架
            frame.add(buttonPanel, BorderLayout.CENTER);

            // 顯示框架
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void showInstructions() {
        String instructions = "遊戲說明:\n\n" +
                "1. 經典小精靈: 單人模式中，玩家需要控制小精靈避開幽靈並吃掉所有豆子。\n" +
                "2. 雙人對戰: 玩家可以與另一個玩家一起遊玩，其中一名玩家扮演幽靈，阻止小精靈吃掉所有豆子，當有一方吃到大豆子時，會使敵方的方向鍵相反，吃到大豆子才能復原。\n" +
                "3. 使用鍵盤方向鍵來控制移動，雙人模式則增加w、a、s、d來移動。\n\n" +
                "祝您遊戲愉快！";

        JOptionPane.showMessageDialog(null, instructions, "遊戲說明", JOptionPane.INFORMATION_MESSAGE);
    }

    class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
