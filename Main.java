package com.zetcode;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {
    private static Clip backgroundMusicClip;

    public static void main(String[] args) {
        playBackgroundMusic(); // 播放背景音樂

        menu gameMenu = new menu();
        gameMenu.showMenu(new menuCallBack() {
            @Override
            public void onClassicPacmanSelected() {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JFrame frame = new javax.swing.JFrame("經典小精靈");
                    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                    frame.add(new PacSingle4()); // 使用您的 PacMan4 類別
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            }

            @Override
            public void onMultiplayerSelected() {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JFrame frame = new javax.swing.JFrame("雙人對戰");
                    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                    frame.add(new PacManDual2()); // 可根據需要創建雙人版本
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            }
        });
    }

    private static void playBackgroundMusic() {
        try {
            File audioFile = new File("C:\\Users\\許哲瑋\\Desktop\\java專題\\java2\\java-main\\期末專題\\com\\zetcode\\選單音樂.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}