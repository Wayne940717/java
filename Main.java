package com.zetcode;

public class Main {
    public static void main(String[] args) {
        menu gameMenu = new menu();
        gameMenu.showMenu(new menuCallBack() {
            @Override
            public void onClassicPacmanSelected() {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JFrame frame = new javax.swing.JFrame("經典小精靈");
                    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                    frame.add(new PacManSingle()); // 使用您的 PacMan4 類別
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
                    frame.add(new PacManDual()); // 可根據需要創建雙人版本
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            }
        });
    }
}