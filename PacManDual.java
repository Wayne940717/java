package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class PacManDual extends JPanel implements ActionListener, KeyListener {

    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,1,1,1,2,1,1,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,1,3,2,2,2,3,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,1,2,1,1,1,2,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
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

    private Timer timer;
    private Image playerImage;
    private Image ghostImage;

    public PacManDual() {
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
                    case 0: g.setColor(Color.BLACK); break;
                    case 1: g.setColor(Color.BLUE); break;
                    case 2: g.setColor(Color.WHITE); g.fillOval(x + 10, y + 10, 4, 4); continue;
                    case 3: g.setColor(Color.WHITE); g.fillOval(x + 8, y + 8, 8, 8); continue;
                }
                g.fillRect(x, y, tileSize, tileSize);
            }
        }

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

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 15);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePlayer();
        moveGhost();
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
            if (map[playerRow][playerCol] == 3) {
                score += 50;
                map[playerRow][playerCol] = 0;
            }
        }
    }

    private void moveGhost() {
        int newRow = ghostRow + ghostDy;
        int newCol = ghostCol + ghostDx;
        if (isWalkable(newRow, newCol)) {
            ghostRow = newRow;
            ghostCol = newCol;
        }
    }

    private void checkCollision() {
        if (playerRow == ghostRow && playerCol == ghostCol) {
            timer.stop();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Game Over! Ghost Wins!");
                System.exit(0);
            });
        }
    }

    private boolean isWalkable(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols && map[r][c] != 1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    dx = 0; dy = -1; break;
            case KeyEvent.VK_DOWN:  dx = 0; dy = 1; break;
            case KeyEvent.VK_LEFT:  dx = -1; dy = 0; break;
            case KeyEvent.VK_RIGHT: dx = 1; dy = 0; break;
            case KeyEvent.VK_W:     ghostDx = 0; ghostDy = -1; break;
            case KeyEvent.VK_S:     ghostDx = 0; ghostDy = 1; break;
            case KeyEvent.VK_A:     ghostDx = -1; ghostDy = 0; break;
            case KeyEvent.VK_D:     ghostDx = 1; ghostDy = 0; break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pac-Man Dual Mode");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new PacManDual());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
