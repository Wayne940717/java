package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class PacSingle4 extends JPanel implements ActionListener, KeyListener {

    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
        {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1},
        {2, 2, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2},
        {1, 2, 2, 2, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1},
        {2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2},
        {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 1, 2, 1, 3, 3, 3, 1, 2, 1, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 2, 2, 2, 1, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1},
        {1, 1, 2, 2, 2, 2, 1, 2, 1, 3, 3, 3, 1, 2, 3, 3, 3, 3, 1, 2, 2, 2, 2, 2, 1, 1},
        {2, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2},
        {1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1},
        {1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1},
        {1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
        {2, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 2},
        {2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1},
        {1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1}
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

    private boolean playerInvincible = false; // 玩家是否處於無敵狀態
    private static final int INVINCIBLE_DURATION = 5000; // 無敵狀態持續時間（毫秒）

    public PacSingle4() {
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
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.translate(px + tileSize / 2, py + tileSize / 2);

            if (dx == -1) {
                g2d.scale(-1, 1);
            } else if (dy == -1) {
                g2d.rotate(-Math.PI / 2);
            } else if (dy == 1) {
                g2d.rotate(Math.PI / 2);
            }

            // 無敵狀態下，玩家閃爍效果
            if (!playerInvincible || System.currentTimeMillis() % 500 < 250) {
                g2d.drawImage(playerImage, -tileSize / 2, -tileSize / 2, tileSize, tileSize, this);
            }
            g2d.dispose();
        } else {
            g.setColor(playerInvincible && System.currentTimeMillis() % 500 < 250 ? Color.GRAY : Color.YELLOW);
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
    
        // 处理循环地图逻辑
        if (newRow < 0) {
            newRow = rows - 1; // 从上边界进入下边界
        } else if (newRow >= rows) {
            newRow = 0; // 从下边界进入上边界
        }
        if (newCol < 0) {
            newCol = cols - 1; // 从左边界进入右边界
        } else if (newCol >= cols) {
            newCol = 0; // 从右边界进入左边界
        }
    
        // 检查新位置是否可行走
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
            if (ghostFrozen[i]) {
                ghostFrozenTime[i]--;
                if (ghostFrozenTime[i] <= 0) {
                    ghostFrozen[i] = false;
                }
                continue;
            }
    
            int[] currentPos = ghostPositions[i];
            int[] lastDir = ghostDirections[i];
            int[] oppositeDirection = {-lastDir[0], -lastDir[1]};
            int[] bestDirection = null;
            int maxWeight = -1;
            int behavior = random.nextInt(100);
    
            if (behavior < 10) { // 随机行动
                for (int[] dir : directions) {
                    int newRow = currentPos[0] + dir[0];
                    int newCol = currentPos[1] + dir[1];
    
                    // 处理循环地图逻辑
                    if (newRow < 0) {
                        newRow = rows - 1;
                    } else if (newRow >= rows) {
                        newRow = 0;
                    }
                    if (newCol < 0) {
                        newCol = cols - 1;
                    } else if (newCol >= cols) {
                        newCol = 0;
                    }
    
                    if (isWalkable(newRow, newCol) && !isOppositeDirection(dir, oppositeDirection)) {
                        bestDirection = dir;
                        break;
                    }
                }
            } else { // 追踪玩家
                for (int[] dir : directions) {
                    int newRow = currentPos[0] + dir[0];
                    int newCol = currentPos[1] + dir[1];
    
                    // 处理循环地图逻辑
                    if (newRow < 0) {
                        newRow = rows - 1;
                    } else if (newRow >= rows) {
                        newRow = 0;
                    }
                    if (newCol < 0) {
                        newCol = cols - 1;
                    } else if (newCol >= cols) {
                        newCol = 0;
                    }
    
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
    
                // 处理循环地图逻辑
                if (currentPos[0] < 0) {
                    currentPos[0] = rows - 1;
                } else if (currentPos[0] >= rows) {
                    currentPos[0] = 0;
                }
                if (currentPos[1] < 0) {
                    currentPos[1] = cols - 1;
                } else if (currentPos[1] >= cols) {
                    currentPos[1] = 0;
                }
    
                ghostDirections[i] = bestDirection;
            }
        }
    }
    
    private boolean isOppositeDirection(int[] dir, int[] opposite) {
        return dir[0] == opposite[0] && dir[1] == opposite[1];
    }

    private void checkCollision() {
        if (playerInvincible) {
            return; // 玩家處於無敵狀態，跳過碰撞檢測
        }
    
        for (int i = 0; i < ghostCount; i++) {
            if (playerRow == ghostPositions[i][0] && playerCol == ghostPositions[i][1]) {
                lives--; // 減少生命值
                ghostFrozen[i] = true; // 讓該鬼靜止
                ghostFrozenTime[i] = FROZEN_DURATION / timer.getDelay(); // 設置鬼的靜止時間
    
                if (lives <= 0) {
                    timer.stop(); // 停止玩家移動
                    ghostSpawnTimer.stop(); // 停止鬼生成
                    showCollisionEffect(); // 顯示遊戲結束效果
                } else {
                    showCollisionEffect2(); // 顯示碰撞效果
                    activateInvincibility(); // 啟動無敵狀態
                }
                break;
            }
        }
    }
    
    private void activateInvincibility() {
        playerInvincible = true; // 設置玩家為無敵狀態
    
        // 設置計時器，持續 5 秒後解除無敵狀態
        Timer invincibilityTimer = new Timer(INVINCIBLE_DURATION, e -> playerInvincible = false);
        invincibilityTimer.setRepeats(false); // 計時器只執行一次
        invincibilityTimer.start();
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
                    JOptionPane.showMessageDialog(PacSingle4.this, "Game Over! Your score: " + score);
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
            frame.add(new PacSingle4());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
