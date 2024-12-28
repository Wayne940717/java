package com.zetcode;

import javax.swing.*;
import java.awt.*;

// 回調介面定義

public class menu {
    private menuCallBack callback;

    public void showMenu(menuCallBack callback) {
        this.callback = callback;

        SwingUtilities.invokeLater(() -> {
            // 創建主框架
            JFrame frame = new JFrame("遊戲選單");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            // 標題部分
            JLabel title = new JLabel("小精靈", JLabel.CENTER);
            title.setFont(new Font("Serif", Font.BOLD, 30));
            title.setForeground(new Color(0x2D6187)); // 標題顏色
            frame.add(title, BorderLayout.NORTH);

            // 創建按鈕面板
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            buttonPanel.setBackground(new Color(0xEAEAEA)); // 背景顏色
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // 添加邊距

            // 經典小精靈按鈕
            JButton classicPacmanButton = new JButton("經典小精靈");
            classicPacmanButton.setFont(new Font("Serif", Font.BOLD, 18));
            classicPacmanButton.setBackground(new Color(0xFFD700));
            classicPacmanButton.setFocusPainted(false);
            classicPacmanButton.addActionListener(e -> {
                if (callback != null) {
                    callback.onClassicPacmanSelected();
                }
            });
            buttonPanel.add(classicPacmanButton);

            // 雙人對戰按鈕
            JButton multiplayerButton = new JButton("雙人對戰");
            multiplayerButton.setFont(new Font("Serif", Font.BOLD, 18));
            multiplayerButton.setBackground(new Color(0x4CAF50));
            multiplayerButton.setFocusPainted(false);
            multiplayerButton.addActionListener(e -> {
                if (callback != null) {
                    callback.onMultiplayerSelected();
                }
            });
            buttonPanel.add(multiplayerButton);

            // 退出按鈕
            JButton exitButton = new JButton("退出");
            exitButton.setFont(new Font("Serif", Font.BOLD, 18));
            exitButton.setBackground(new Color(0xFF5733));
            exitButton.setFocusPainted(false);
            exitButton.addActionListener(e -> System.exit(0));
            buttonPanel.add(exitButton);

            // 添加面板到框架
            frame.add(buttonPanel, BorderLayout.CENTER);

            // 顯示框架
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
