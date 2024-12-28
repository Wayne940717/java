package com.zetcode;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class menu {
    private menuCallBack callback;
    private Clip backgroundMusicClip;

    public void showMenu(menuCallBack callback) {
        this.callback = callback;

        // 播放背景音樂
        playBackgroundMusic();

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
            exitButton.addActionListener(e -> {
                stopBackgroundMusic(); // 停止背景音樂
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

    private void playBackgroundMusic() {
        try {
            File audioFile = new File("C:\\Users\\許哲瑋\\Desktop\\java專題\\java2\\java-main\\期末專題\\com\\zetcode\\videoplayback.wav"); // 替換為正確的音檔路徑
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // 循環播放
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
    }
}
