package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimplePacManGame1 extends JPanel implements ActionListener, KeyListener {

    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,1},
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

    // 玩家初始位置
    private int playerRow = 1;
    private int playerCol = 1;
    private int dx = 0;
    private int dy = 0;
    private int score = 0;

    // 鬼魂初始位置(例如在地圖中央)
    private int ghostRow = 4;
    private int ghostCol = 6;

    private Timer timer;
    private Image playerImage;
    private Image ghostImage;
    private Random rand = new Random();

    public SimplePacManGame1() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // 載入玩家圖片
        try {
            playerImage = ImageIO.read(getClass().getResource("pacman1.png"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            playerImage = null;
        }

        // 載入鬼魂圖片 (假設 ghost.png存在)
        try {
            ghostImage = ImageIO.read(getClass().getResource("ghost.png"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            ghostImage = null;
        }

        timer = new Timer(150, this);
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
            Image scaledPlayer = playerImage.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            g.drawImage(scaledPlayer, px, py, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(px+2, py+2, tileSize-4, tileSize-4);
        }

        // 繪製鬼魂
        int gx = ghostCol * tileSize;
        int gy = ghostRow * tileSize;
        if (ghostImage != null) {
            Image scaledGhost = ghostImage.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            g.drawImage(scaledGhost, gx, gy, null);
        } else {
            // 若無鬼魂圖示，用紅色方塊代替
            g.setColor(Color.RED);
            g.fillRect(gx+2, gy+2, tileSize-4, tileSize-4);
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
        // 更新玩家
        movePlayer();

        // 更新鬼魂
        moveGhost();

        // 簡單檢查如果鬼魂和玩家位置相同 => 在真實遊戲中可能會減生命或Game Over
        if (ghostRow == playerRow && ghostCol == playerCol) {
            System.out.println("Ghost caught the player!");
            // 在真實遊戲中可做出玩家死亡邏輯，這裡先不加入
        }
    }

    private void movePlayer() {
        int newRow = playerRow + dy;
        int newCol = playerCol + dx;
        if (isWalkable(newRow, newCol)) {
            playerRow = newRow;
            playerCol = newCol;
            // 吃豆子
            if (map[playerRow][playerCol] == 2) {
                score += 10;
                map[playerRow][playerCol] = 0;
            } else if (map[playerRow][playerCol] == 3) {
                score += 50;
                map[playerRow][playerCol] = 0;
            }
        }
    }

    private void moveGhost() {
        // 計算鬼魂與玩家的曼哈頓距離
        int dist = Math.abs(ghostRow - playerRow) + Math.abs(ghostCol - playerCol);

        int gdx = 0;
        int gdy = 0;

        // 若距離較近(例如小於等於5)，嘗試朝玩家靠近
        if (dist <= 5) {
            // 嘗試縮小行列差距
            if (ghostRow < playerRow) {
                gdy = 1; // 向下
            } else if (ghostRow > playerRow) {
                gdy = -1; // 向上
            } else {
                gdy = 0;
            }

            if (ghostCol < playerCol) {
                gdx = 1; // 向右
            } else if (ghostCol > playerCol) {
                gdx = -1; // 向左
            } else {
                gdx = 0;
            }

            // 如果同時有行與列的差，將優先一個方向，以達成一格一格逼近
            // 這裡簡化先嘗試縱向移動(若可行)，不行再嘗試橫向
            boolean moved = tryMoveDirectionInOrder(new int[][] {
                {0,gdy}, {gdx,0}, {0,-gdy}, {-gdx,0}
            });
            if (!moved) {
                // 若還是沒移動成功，那就隨機走一格(保證不原地不動)
                tryRandomMove();
            }

        } else {
            // 若距離玩家較遠，嘗試隨機亂走，但不原地不動
            tryRandomMove();
        }
    }

    // 嘗試給定一組方向順序，逐一嘗試能否走通
    // 傳入方向陣列，如{{0,1},{1,0}}表示先嘗試向下，再向右。
    // 若找到可行方向則移動並回傳true，否則false。
    private boolean tryMoveDirectionInOrder(int[][] directions) {
        for (int[] dir : directions) {
            int nr = ghostRow + dir[1];
            int nc = ghostCol + dir[0];
            if (isWalkable(nr, nc)) {
                ghostRow = nr;
                ghostCol = nc;
                return true;
            }
        }
        return false;
    }

    private void tryRandomMove() {
        // 隨機嘗試上下左右4個方向直到有可行路徑
        List<int[]> dirs = new ArrayList<>();
        dirs.add(new int[]{0,1});   // down
        dirs.add(new int[]{0,-1});  // up
        dirs.add(new int[]{1,0});   // right
        dirs.add(new int[]{-1,0});  // left
        Collections.shuffle(dirs, rand);

        for (int[] d : dirs) {
            int nr = ghostRow + d[1];
            int nc = ghostCol + d[0];
            if (isWalkable(nr, nc)) {
                ghostRow = nr;
                ghostCol = nc;
                return;
            }
        }
        // 若全部方向被牆擋住，也只好不動了(理論上若地圖封閉會有此情況)
        // 不過題目要求鬼魂不能原地不動，如果有此狀況，可能需要改變地圖設計或邏輯。
        // 這裡假設一定有路可走。
    }

    private boolean isWalkable(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return false;
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
        JFrame frame = new JFrame("Pac-Man with Chasing Ghost");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SimplePacManGame1 gamePanel = new SimplePacManGame1();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
