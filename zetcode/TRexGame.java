package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TRexGame extends JPanel implements ActionListener, KeyListener {

    // 遊戲狀態
    private boolean running = true;
    private boolean gameOver = false;

    // T-Rex 參數
    private int trexX = 50;
    private int trexY = 200; 
    private int trexWidth = 40;
    private int trexHeight = 40;
    private boolean jumping = false;
    private int jumpSpeed = 0;
    private int gravity = 1;
    private int jumpPower = 15;

    // 障礙物參數 (簡化為方塊)
    private class Obstacle {
        int x, y, width, height;
        public Obstacle(int x, int y, int width, int height){
            this.x = x; 
            this.y = y; 
            this.width = width; 
            this.height = height;
        }
    }

    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private int obstacleSpawnTimer = 0;
    private int obstacleSpawnInterval = 100; // 每隔100幀產生一個障礙物
    private int obstacleSpeed = 5;

    private Timer timer;

    public TRexGame() {
        setPreferredSize(new Dimension(800, 300));
        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(20, this); // 約50 FPS
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 繪製地面線
        g.setColor(Color.BLACK);
        g.drawLine(0, 240, getWidth(), 240);

        // 繪製 T-Rex
        g.setColor(Color.GREEN);
        g.fillRect(trexX, trexY, trexWidth, trexHeight);

        // 繪製障礙物
        g.setColor(Color.RED);
        for (Obstacle obs : obstacles) {
            g.fillRect(obs.x, obs.y, obs.width, obs.height);
        }

        // 若遊戲結束
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over! Press Space to Restart", 150, 150);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) return;

        // 更新 T-Rex 跳躍邏輯
        if (jumping) {
            trexY -= jumpSpeed; 
            jumpSpeed -= gravity;
            if (trexY >= 200) {
                trexY = 200;
                jumping = false;
                jumpSpeed = 0;
            }
        }

        // 產生障礙物
        obstacleSpawnTimer++;
        if (obstacleSpawnTimer >= obstacleSpawnInterval) {
            obstacleSpawnTimer = 0;
            obstacles.add(new Obstacle(getWidth(), 200, 20, 40));
        }

        // 移動障礙物 & 碰撞檢測
        Iterator<Obstacle> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacle obs = it.next();
            obs.x -= obstacleSpeed;

            // 超出左邊界就移除
            if (obs.x + obs.width < 0) {
                it.remove();
                continue;
            }

            // 簡單碰撞偵測(方塊對方塊)
            if (obs.x < trexX + trexWidth && 
                obs.x + obs.width > trexX && 
                obs.y < trexY + trexHeight && 
                obs.y + obs.height > trexY) {
                // 碰撞發生，結束遊戲
                running = false;
                gameOver = true;
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (!gameOver) {
            // 按下空白鍵跳躍
            if (code == KeyEvent.VK_SPACE && !jumping) {
                jumping = true;
                jumpSpeed = jumpPower;
            }
        } else {
            // 若遊戲結束後再按空白鍵重新開始
            if (code == KeyEvent.VK_SPACE) {
                resetGame();
            }
        }
    }

    private void resetGame() {
        running = true;
        gameOver = false;
        trexY = 200;
        jumping = false;
        jumpSpeed = 0;
        obstacles.clear();
        obstacleSpawnTimer = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 不需要實作
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 不需要實作
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("T-Rex Game Demo");
        TRexGame gamePanel = new TRexGame();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
