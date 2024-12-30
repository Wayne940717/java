package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class PacSingle3 extends JPanel implements ActionListener, KeyListener {

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
        {1, 2, 2, 2, 2, 2, 1, 2, 1, 3, 3, 3, 1, 2, 1, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 2, 2, 2, 1, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1},
        {1, 1, 2, 2, 2, 2, 1, 2, 1, 3, 3, 3, 1, 2, 3, 3, 3, 3, 1, 2, 2, 2, 2, 2, 1, 1},
        {1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
        {2, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2},
        {2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
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
    private int ghostRow = rows - 2;
    private int ghostCol = cols - 2;
    private int dx = 0;
    private int dy = 0;
    private int score = 0;
    private int lives = 3;

    private Timer timer;
    private Image playerImage;
    private Image ghostImage;
    
    private int ghostMoveCounter = 0; // 控制鬼移動的速度
    private int ghostSpeed = 3; // 鬼每 3 次計時器觸發移動一次
    private int[] lastGhostDirection = {0, 0}; // 記錄鬼上次方向
    private Random random = new Random();

    private static final int MAX_GHOSTS = 4; // 最大鬼數量
    private int[][] ghostPositions = new int[MAX_GHOSTS][2]; // 鬼的座標
    private int[][] ghostDirections = new int[MAX_GHOSTS][2]; // 鬼的方向
    private int ghostCount = 1; // 當前鬼的數量
    private Timer ghostSpawnTimer; // 計時器用於生成新鬼

    private boolean[] ghostFrozen = new boolean[MAX_GHOSTS]; // 鬼是否靜止
    private int[] ghostFrozenTime = new int[MAX_GHOSTS]; // 靜止剩餘時間（毫秒）
    private static final int FROZEN_DURATION = 300; // 靜止時間 3 秒

    public PacSingle3() {
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

        timer = new Timer(150, this);
        timer.start();

        ghostPositions[0] = new int[]{ghostRow, ghostCol};
        ghostDirections[0] = new int[]{0, 0};

        // 定時生成新鬼
        ghostSpawnTimer = new Timer(20000, e -> {
            if (ghostCount < MAX_GHOSTS) {
                ghostPositions[ghostCount] = new int[]{rows - 2, cols - 2}; // 新鬼生成在右下角
                ghostDirections[ghostCount] = new int[]{0, 0};
                ghostCount++;
            }
        });
        ghostSpawnTimer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tile = map[row][col];
                int x = col * tileSize;
                int y = row * tileSize;

                switch (tile) {
                    case 0 -> g.setColor(Color.BLACK);
                    case 1 -> g.setColor(Color.BLUE);
                    case 2 -> {
                        g.setColor(Color.WHITE);
                        g.fillOval(x + 10, y + 10, 4, 4);
                    }
                    case 3 -> g.setColor(Color.BLACK);
                }
                if (tile != 2) {
                    g.fillRect(x, y, tileSize, tileSize);
                }
            }
        }

        int px = playerCol * tileSize;
        int py = playerRow * tileSize;
        if (playerImage != null) {
            Graphics2D g2d = (Graphics2D) g.create(); // 创建 Graphics2D 对象
            g2d.translate(px + tileSize / 2, py + tileSize / 2); // 将绘制原点移动到图片中心

            if (dx == -1) { // 向左
                g2d.scale(-1, 1); // 镜像翻转
            } else if (dy == -1) { // 向上
                g2d.rotate(-Math.PI / 2); // 向左旋转 90 度
            } else if (dy == 1) { // 向下
                g2d.rotate(Math.PI / 2); // 向右旋转 90 度
            }
            
            g2d.drawImage(playerImage, -tileSize / 2, -tileSize / 2, tileSize, tileSize, this); // 绘制图片
            g2d.dispose(); // 释放 Graphics2D 对象
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(px + 2, py + 2, tileSize - 4, tileSize - 4);
        }

        for (int i = 0; i < ghostCount; i++) {
            int gx = ghostPositions[i][1] * tileSize;
            int gy = ghostPositions[i][0] * tileSize;
            if (ghostImage != null) {
                g.drawImage(ghostImage, gx, gy, tileSize, tileSize, this);
            } else {
                g.setColor(Color.RED);
                g.fillRect(gx + 2, gy + 2, tileSize - 4, tileSize - 4);
            }
        }
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 15); // 顯示分數
        g.drawString("Lives: " + lives, 10, 30); // 顯示剩餘生命值

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePlayer();

        ghostMoveCounter++;
        if (ghostMoveCounter >= ghostSpeed) {
            moveGhost();
            ghostMoveCounter = 0;
        }

        checkCollision();
        repaint();
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
        }
    }

    private void moveGhost() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < ghostCount; i++) {
            // 如果鬼處於靜止狀態，減少靜止時間並跳過移動
            if (ghostFrozen[i]) {
                ghostFrozenTime[i]--;
                if (ghostFrozenTime[i] <= 0) {
                    ghostFrozen[i] = false; // 恢復活動
                }
                continue; // 跳過當前鬼的移動
            }
    
            int[] currentPos = ghostPositions[i];
            int[] lastDir = ghostDirections[i];
            int[] oppositeDirection = {-lastDir[0], -lastDir[1]};
            int[] bestDirection = null;
            int maxWeight = -1;
            int behavior = random.nextInt(100);
    
            if (behavior < 10) { // 隨機行動
                for (int[] dir : directions) {
                    int newRow = currentPos[0] + dir[0];
                    int newCol = currentPos[1] + dir[1];
                    if (isWalkable(newRow, newCol) && !isOppositeDirection(dir, oppositeDirection)) {
                        bestDirection = dir;
                        break;
                    }
                }
            } else { // 追蹤玩家
                for (int[] dir : directions) {
                    int newRow = currentPos[0] + dir[0];
                    int newCol = currentPos[1] + dir[1];
                    if (isWalkable(newRow, newCol) && !isOppositeDirection(dir, oppositeDirection)) {
                        int weight = 100 - (Math.abs(newRow - playerRow) + Math.abs(newCol - playerCol));
                        if (weight > maxWeight) {
                            maxWeight = weight;
                            bestDirection = dir;
                        }
                    }
                }
            }
    
            if (bestDirection != null) {
                currentPos[0] += bestDirection[0];
                currentPos[1] += bestDirection[1];
                ghostDirections[i] = bestDirection;
            }
        }
    }
    
    

    private boolean isOppositeDirection(int[] dir, int[] opposite) {
        return dir[0] == opposite[0] && dir[1] == opposite[1];
    }

    private void checkCollision() {
        for (int i = 0; i < ghostCount; i++) {
            if (playerRow == ghostPositions[i][0] && playerCol == ghostPositions[i][1]) {
                lives--;
                ghostFrozen[i] = true; // 讓該鬼靜止
                ghostFrozenTime[i] = FROZEN_DURATION / timer.getDelay(); // 計算靜止持續的次數
    
                if (lives <= 0) {
                    timer.stop();
                    ghostSpawnTimer.stop();
                    showCollisionEffect();
                } else {
                    showCollisionEffect2();
                    //resetPositions();
                }
                break;
            }
        }
    }
    
    

    private void showCollisionEffect() {
        Timer flashTimer = new Timer(100, null);
        flashTimer.addActionListener(new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 6) { // 閃爍效果 6 次
                    setBackground(count % 2 == 0 ? Color.RED : Color.BLACK);
                    repaint();
                    count++;
                } else {
                    flashTimer.stop();
                    JOptionPane.showMessageDialog(PacSingle3.this, "Game Over! Your score: " + score);
                    System.exit(0);
                }
            }
        });
        flashTimer.start();

        // 屏幕晃動效果
        Point originalLocation = getParent().getLocation();
        Timer shakeTimer = new Timer(50, null);
        shakeTimer.addActionListener(new ActionListener() {
            int count = 0;
            Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 10) { // 晃動 10 次
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

    private void showCollisionEffect2() {
        Timer flashTimer = new Timer(100, null);
        flashTimer.addActionListener(new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 6) { // 閃爍效果 6 次
                    setBackground(count % 2 == 0 ? Color.RED : Color.BLACK);
                    repaint();
                    count++;
                } else {
                    flashTimer.stop();
                    
                }
            }
        });
        flashTimer.start();

        // 屏幕晃動效果
        Point originalLocation = getParent().getLocation();
        Timer shakeTimer = new Timer(50, null);
        shakeTimer.addActionListener(new ActionListener() {
            int count = 0;
            Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 10) { // 晃動 10 次
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

    private boolean isWalkable(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols && map[r][c] != 1 && map[r][c] != 3;
    }
    private void resetPositions() {
        playerRow = 1;
        playerCol = 1;
        ghostRow = rows - 2;
        ghostCol = cols - 2;
        lastGhostDirection = new int[]{0, 0};
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tempDx = 0;
        int tempDy = 0;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> { tempDx = 0; tempDy = -1; }
            case KeyEvent.VK_DOWN -> { tempDx = 0; tempDy = 1; }
            case KeyEvent.VK_LEFT -> { tempDx = -1; tempDy = 0; }
            case KeyEvent.VK_RIGHT -> { tempDx = 1; tempDy = 0; }
        }

        int newRow = playerRow + tempDy;
        int newCol = playerCol + tempDx;
        if (isWalkable(newRow, newCol)) {
            dx = tempDx;
            dy = tempDy;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pac-Man Single Mode");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new PacSingle3());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
