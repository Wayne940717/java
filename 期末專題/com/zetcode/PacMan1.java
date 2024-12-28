package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PacMan1 extends JPanel implements ActionListener, KeyListener {

    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,1,1,1,2,1,1,1,2,1,2,1},
        {1,2,1,3,2,2,2,3,1,2,1,2,1},
        {1,2,1,2,1,1,1,2,1,2,1,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    private int tileSize = 24;
    private int rows = map.length;
    private int cols = map[0].length;
    private int panelWidth = cols * tileSize;
    private int panelHeight = rows * tileSize;

    // 角色初始位置
    private int playerRow = 1;
    private int playerCol = 1;
    private int dx = 0;
    private int dy = 0;
    private int score = 0;
    private Timer timer;

    // 新增角色圖示
    private Image playerImage;

    public PacMan1() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.WHITE);
        addKeyListener(this);
        setFocusable(true);

        // 載入角色圖片 (假設 pacman.png 放在與此類同一資源路徑)
        try {
            playerImage = ImageIO.read(getClass().getResource("pacman1.png"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // 若載入失敗，可考慮使用預設圖案或中斷程式
            // 這裡暫時直接用一個方塊取代
            playerImage = null;
        }

        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 繪製地圖
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tile = map[row][col];
                int x = col * tileSize;
                int y = row * tileSize;

                switch (tile) {
                    case 0: 
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        break;
                    case 1:
                        g.setColor(Color.BLUE);
                        g.fillRect(x, y, tileSize, tileSize);
                        break;
                    case 2:
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        g.setColor(Color.WHITE);
                        g.fillOval(x + tileSize/2 - 2, y + tileSize/2 - 2, 4, 4);
                        break;
                    case 3:
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        g.setColor(Color.WHITE);
                        g.fillOval(x + tileSize/2 - 5, y + tileSize/2 - 5, 10, 10);
                        break;
                    default:
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, tileSize, tileSize);
                        break;
                }
            }
        }

        // 繪製玩家
        int px = playerCol * tileSize;
        int py = playerRow * tileSize;
        if (playerImage != null) {
            // 等比例縮放角色圖片以符合 tileSize
            Image scaledPlayer = playerImage.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            g.drawImage(scaledPlayer, px, py, null);
        } else {
            // 如果圖片載入失敗，就顯示一個備援圖形
            g.setColor(Color.YELLOW);
            g.fillOval(px + 2, py + 2, tileSize - 4, tileSize - 4);
        }

        // 繪製分數
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 15);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
    }

    private void updateGame() {
        int newRow = playerRow + dy;
        int newCol = playerCol + dx;

        if (isWalkable(newRow, newCol)) {
            playerRow = newRow;
            playerCol = newCol;
            if (map[playerRow][playerCol] == 2) {
                score += 10;
                map[playerRow][playerCol] = 0;
            } else if (map[playerRow][playerCol] == 3) {
                score += 50;
                map[playerRow][playerCol] = 0;
            }
        }
    }

    private boolean isWalkable(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return false;
        }
        return map[r][c] != 1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            dx = 0; dy = -1;
        } else if (code == KeyEvent.VK_DOWN) {
            dx = 0; dy = 1;
        } else if (code == KeyEvent.VK_LEFT) {
            dx = -1; dy = 0;
        } else if (code == KeyEvent.VK_RIGHT) {
            dx = 1; dy = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Pac-Man with Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PacMan1 gamePanel = new PacMan1();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
