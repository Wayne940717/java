package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class PacManDual2 extends JPanel implements ActionListener, KeyListener {

    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1},
        {1, 2, 1, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 2, 2, 2, 1, 0, 0, 0, 2, 2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1},
        {1, 1, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 1},
        {1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private int tileSize = 24;
    private int rows = map.length;
    private int cols = map[0].length;
    private int panelWidth = cols * tileSize;
    private int panelHeight = rows * tileSize;

    private int playerRow = 1;
    private int playerCol = 1;
    private int ghostRow;
    private int ghostCol;
    private int dx = 0;
    private int dy = 0;
    private int ghostDx = 0;
    private int ghostDy = 0;
    private int score = 0;

    private boolean playerReversed = false;
    private boolean ghostReversed = false;

    private Timer timer;
    private Timer powerUpTimer;
    private Timer backgroundChangeTimer;
    private Image playerImage;
    private Image ghostImage;
    private boolean backgroundColorBlack = true;

    private Color currentBackgroundColor = Color.BLACK; // 当前背景色
    private Color targetBackgroundColor = Color.WHITE; // 目标背景色
    private float gradientProgress = 0.0f; // 渐变进度，范围 0.0 - 1.0
    private Timer gradientTimer; // 渐变计时器

    private Timer mapChangeTimer; // 地图动态变化计时器

    private Timer greenBeanTimer; // 绿色豆子生成计时器
    private Timer purpleBeanTimer; // 紫色豆子生成计时器


    


    public PacManDual2() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        try {
            playerImage = ImageIO.read(getClass().getResource("pacman1.png"));
        } catch (IOException | NullPointerException e) {
            playerImage = null;
        }

        try {
            ghostImage = ImageIO.read(getClass().getResource("ghost.png"));
        } catch (IOException | NullPointerException e) {
            ghostImage = null;
        }

        ghostRow = rows - 2;
        ghostCol = cols - 2;

        timer = new Timer(150, this);
        timer.start();

        powerUpTimer = new Timer(15000, e -> spawnPowerUp());
                powerUpTimer.start();
        
                backgroundChangeTimer = new Timer(30000, e -> toggleBackgroundColor());
                backgroundChangeTimer.start();
        
                mapChangeTimer = new Timer(10000, e -> modifyMap()); // 每10秒生成或破坏墙壁
                mapChangeTimer.start();
        
                greenBeanTimer = new Timer(20000, e -> spawnGreenBean());
                greenBeanTimer.start();
        
                // 每 10 秒生成一个紫色豆子
                purpleBeanTimer = new Timer(10000, e -> spawnPurpleBean());
                purpleBeanTimer.start();
            }
        
            private Object spawnPowerUp() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'spawnPowerUp'");
            }
        
            private void modifyMap() {
        Random rand = new Random();
        int generateWalls = 3; // 每次生成的墙壁数量
        int destroyWalls = generateWalls + 2; // 每次破坏的墙壁数量，比生成的多 2
    
        // 随机生成新墙壁
        for (int i = 0; i < generateWalls; i++) {
            int r, c;
            do {
                r = rand.nextInt(rows);
                c = rand.nextInt(cols);
            } while (map[r][c] != 0 || (r == playerRow && c == playerCol) || (r == ghostRow && c == ghostCol));
            map[r][c] = 1; // 设置为墙壁
        }
    
        // 随机破坏已有墙壁
        for (int i = 0; i < destroyWalls; i++) {
            int r, c;
            do {
                r = rand.nextInt(rows);
                c = rand.nextInt(cols);
            } while (map[r][c] != 1); // 仅破坏已有墙壁
            map[r][c] = 0; // 清除墙壁
        }
    
        repaint(); // 重绘地图
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 使用当前背景颜色绘制背景
        setBackground(currentBackgroundColor);
        g.setColor(currentBackgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 其他绘制逻辑保持不变
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tile = map[row][col];
                int x = col * tileSize;
                int y = row * tileSize;

                switch (tile) {
                    case 0: // 空地
                        g.setColor(currentBackgroundColor);
                        break;
                    case 1: // 墙壁
                        g.setColor(currentBackgroundColor.equals(Color.WHITE) ? Color.RED : Color.BLUE);
                        break;
                    case 2: // 食物
                        g.setColor(currentBackgroundColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
                        g.fillOval(x + 10, y + 10, 4, 4);
                        continue;
                    case 4: // 道具
                        g.setColor(Color.GREEN);
                        g.fillOval(x + 6, y + 6, 12, 12);
                        continue;
                }
                g.fillRect(x, y, tileSize, tileSize);
            }
        }

        // 绘制玩家和鬼
        int px = playerCol * tileSize;
        int py = playerRow * tileSize;
        if (playerImage != null) {
            g.drawImage(playerImage, px, py, tileSize, tileSize, this);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(px + 2, py + 2, tileSize - 4, tileSize - 4);
        }

        int gx = ghostCol * tileSize;
        int gy = ghostRow * tileSize;
        if (ghostImage != null) {
            g.drawImage(ghostImage, gx, gy, tileSize, tileSize, this);
        } else {
            g.setColor(Color.RED);
            g.fillRect(gx + 2, gy + 2, tileSize - 4, tileSize - 4);
        }

        // 绘制分数等信息
        g.setColor(currentBackgroundColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
        g.drawString("Score: " + score, 10, 15);
    }



    private void toggleBackgroundColor() {
        // 设置目标颜色
        targetBackgroundColor = backgroundColorBlack ? Color.WHITE : Color.BLACK;
        backgroundColorBlack = !backgroundColorBlack;
        gradientProgress = 0.0f; // 重置渐变进度
    
        if (gradientTimer != null) {
            gradientTimer.stop(); // 停止可能存在的渐变计时器
        }
    
        // 创建一个新计时器，用于逐步改变背景颜色
        gradientTimer = new Timer(30, e -> {
            gradientProgress += 0.05f; // 每次更新渐变进度
            if (gradientProgress >= 1.0f) {
                gradientProgress = 1.0f;
                gradientTimer.stop(); // 渐变完成后停止计时器
            }
    
            // 插值计算当前背景颜色
            int red = (int) (currentBackgroundColor.getRed() * (1 - gradientProgress)
                    + targetBackgroundColor.getRed() * gradientProgress);
            int green = (int) (currentBackgroundColor.getGreen() * (1 - gradientProgress)
                    + targetBackgroundColor.getGreen() * gradientProgress);
            int blue = (int) (currentBackgroundColor.getBlue() * (1 - gradientProgress)
                    + targetBackgroundColor.getBlue() * gradientProgress);
    
            currentBackgroundColor = new Color(red, green, blue);
            repaint(); // 重绘界面
        });
        gradientTimer.start(); // 启动渐变计时器
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // 检查背景状态
        updateBackgroundColor();
    
        // 玩家和鬼的移动逻辑
        movePlayer();
        moveGhost();
    
        // 碰撞检测和重绘
        checkCollision();
        repaint();
    }

    private void updateBackgroundColor() {
        // 如果 playerReversed 或 ghostReversed 至少有一个为 true，则背景为白色，否则为黑色
        if (playerReversed || ghostReversed) {
            currentBackgroundColor = Color.WHITE;
        } else {
            currentBackgroundColor = Color.BLACK;
        }
    }
    
    

    private void movePlayer() {
        int newRow = playerRow + dy;
        int newCol = playerCol + dx;
        if (isWalkable(newRow, newCol)) {
            playerRow = newRow;
            playerCol = newCol;
            if (map[playerRow][playerCol] == 2) {
                score += 10;
                map[playerRow][playerCol] = 0;
            }
            if (map[playerRow][playerCol] == 4) {
                if (playerReversed) {
                    // 恢复玩家到正常状态
                    playerReversed = false;
                } else {
                    // 让敌方操控方向与行进方向颠倒
                    ghostReversed = true;
                }
                map[playerRow][playerCol] = 0; // 移除道具
            }
        }
    }
    

    private void moveGhost() {
        int newRow = ghostRow + ghostDy;
        int newCol = ghostCol + ghostDx;
        if (isWalkable(newRow, newCol)) {
            ghostRow = newRow;
            ghostCol = newCol;
            if (map[ghostRow][ghostCol] == 4) {
                if (ghostReversed) {
                    // 恢复敌方到正常状态
                    ghostReversed = false;
                } else {
                    // 让玩家操控方向与行进方向颠倒
                    playerReversed = true;
                }
                map[ghostRow][ghostCol] = 0; // 移除道具
            }
        }
    }
    

    private void checkCollision() {
        if (playerRow == ghostRow && playerCol == ghostCol) {
            timer.stop();
            showCollisionEffect();
        }
    }

    private boolean isWalkable(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols && map[r][c] != 1;
    }

    private void spawnGreenBean() {
        Random rand = new Random();
        int r, c;
        do {
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
        } while (map[r][c] != 0); // 确保生成在空地上
    
        map[r][c] = 4; // 4 代表绿色豆子
        repaint();
    }
    
    private void spawnPurpleBean() {
        Random rand = new Random();
        int r, c;
        do {
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
        } while (map[r][c] != 0); // 确保生成在空地上
    
        map[r][c] = 5; // 5 代表紫色豆子
        repaint();
    }
    

    

    private void showCollisionEffect() {
        Timer flashTimer = new Timer(100, null);
        flashTimer.addActionListener(new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 6) { // 闪烁6次
                    setBackground(count % 2 == 0 ? Color.RED : (backgroundColorBlack ? Color.BLACK : Color.WHITE));
                    repaint();
                    count++;
                } else {
                    flashTimer.stop();
                    showGameOverDialog();
                }
            }
        });
        flashTimer.start();

        // 屏幕晃动效果
        Point originalLocation = getParent().getLocation();
        Timer shakeTimer = new Timer(50, null);
        shakeTimer.addActionListener(new ActionListener() {
            int count = 0;
            Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 10) { // 晃动10次
                    int offsetX = random.nextInt(10) - 5;
                    int offsetY = random.nextInt(10) - 5;
                    getParent().setLocation(originalLocation.x + offsetX, originalLocation.y + offsetY);
                    count++;
                } else {
                    shakeTimer.stop();
                    getParent().setLocation(originalLocation);
                }
            }
        });
        shakeTimer.start();
    }

    private void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Game Over! Ghost Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            returnToMenu();
        });
    }
    private void returnToMenu() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose(); // 關閉當前遊戲框架
            new menu().showMenu(new menuCallBack() {
                @Override
                public void onClassicPacmanSelected() {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        javax.swing.JFrame frame = new javax.swing.JFrame("經典小精靈");
                        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                        frame.add(new PacSingle4());
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
                        frame.add(new PacManDual2());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    });
                }
            });
        });
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        // 玩家控制逻辑
        if (!playerReversed) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:    dx = 0; dy = -1; break;
                case KeyEvent.VK_DOWN:  dx = 0; dy = 1; break;
                case KeyEvent.VK_LEFT:  dx = -1; dy = 0; break;
                case KeyEvent.VK_RIGHT: dx = 1; dy = 0; break;
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:    dx = 0; dy = 1; break; // 反向控制
                case KeyEvent.VK_DOWN:  dx = 0; dy = -1; break;
                case KeyEvent.VK_LEFT:  dx = 1; dy = 0; break;
                case KeyEvent.VK_RIGHT: dx = -1; dy = 0; break;
            }
        }

        // 敌方控制逻辑
        if (!ghostReversed) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:     ghostDx = 0; ghostDy = -1; break;
                case KeyEvent.VK_S:     ghostDx = 0; ghostDy = 1; break;
                case KeyEvent.VK_A:     ghostDx = -1; ghostDy = 0; break;
                case KeyEvent.VK_D:     ghostDx = 1; ghostDy = 0; break;
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:     ghostDx = 0; ghostDy = 1; break; // 反向控制
                case KeyEvent.VK_S:     ghostDx = 0; ghostDy = -1; break;
                case KeyEvent.VK_A:     ghostDx = 1; ghostDy = 0; break;
                case KeyEvent.VK_D:     ghostDx = -1; ghostDy = 0; break;
            }
        }
    }


    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pac-Man Dual Mode");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new PacManDual2());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}