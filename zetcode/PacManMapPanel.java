package com.zetcode;

import javax.swing.*;
import java.awt.*;

public class PacManMapPanel extends JPanel {
    private int[][] map;
    private int tileSize = 30; // 每格的大小(像素)
    
    public PacManMapPanel(int[][] map) {
        this.map = map;
        // 設定面板大小，根據地圖的行數、列數與每格大小來決定
        int width = map[0].length * tileSize;
        int height = map.length * tileSize;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 繪製地圖
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int tile = map[row][col];
                
                // 計算該格在畫面上的像素位置
                int x = col * tileSize;
                int y = row * tileSize;
                
                // 根據 tile 的值來決定要畫什麼
                switch (tile) {
                    case 0: 
                        // 空地：可選擇畫黑色背景
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        break;
                    case 1:
                        // 牆：畫藍色方塊
                        g.setColor(Color.BLUE);
                        g.fillRect(x, y, tileSize, tileSize);
                        break;
                    case 2:
                        // 小豆子：先畫黑底，再畫一個小白點
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        g.setColor(Color.WHITE);
                        g.fillOval(x + tileSize/2 - 2, y + tileSize/2 - 2, 4, 4);
                        break;
                    case 3:
                        // 大豆子：稍大一點的白點
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        g.setColor(Color.WHITE);
                        g.fillOval(x + tileSize/2 - 5, y + tileSize/2 - 5, 10, 10);
                        break;
                    default:
                        // 預設情況也可以畫成空地
                        g.setColor(Color.YELLOW);
                        g.fillRect(x, y, tileSize, tileSize);
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        // 測試用的地圖
        int[][] demoMap = {
            {1,1,1,1,1,1,1,1,1},
            {1,2,2,1,1,1,2,2,1},
            {1,2,2,1,1,1,2,2,1},
            {1,2,2,2,2,2,2,2,1},
            {1,2,2,2,2,2,2,2,1},
            {1,2,2,2,2,2,2,2,1},
            {1,2,2,2,2,2,2,2,1},
            {1,2,1,1,3,1,1,2,1},
            {1,2,2,2,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1}
        };

        JFrame frame = new JFrame("Pac-Man Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PacManMapPanel panel = new PacManMapPanel(demoMap);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
