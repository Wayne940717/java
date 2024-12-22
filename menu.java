import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu {
    public static void main(String[] args) {
        // 創建 JFrame 主窗口
        JFrame frame = new JFrame("Pac-man遊戲");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // 添加背景圖片
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("path/to/your/background.jpg"); // 替換為背景圖片路徑
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundPanel);

        // 添加標題
        JLabel titleLabel = new JLabel("Pac-man遊戲", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 24)); // 使用支援中文的字體
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setOpaque(false);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // 創建按鈕面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);

        // 按鈕通用樣式
        Font buttonFont = new Font("Microsoft YaHei", Font.BOLD, 16); // 使用支援中文的字體

        // 按鈕1: 經典小精靈
        JButton classicPacmanButton = new JButton("經典小精靈");
        classicPacmanButton.setFont(buttonFont);
        classicPacmanButton.setBackground(Color.GREEN);
        classicPacmanButton.setForeground(Color.WHITE);
        classicPacmanButton.setFocusPainted(false);
        classicPacmanButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true)); // 圓弧邊框
        classicPacmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "經典小精靈模式啟動中...", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 在這裡添加經典小精靈的邏輯
            }
        });
        buttonPanel.add(classicPacmanButton);

        // 按鈕2: 對戰模式
        JButton versusModeButton = new JButton("對戰模式");
        versusModeButton.setFont(buttonFont);
        versusModeButton.setBackground(Color.BLUE);
        versusModeButton.setForeground(Color.WHITE);
        versusModeButton.setFocusPainted(false);
        versusModeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true)); // 圓弧邊框
        versusModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "對戰模式啟動中...", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 在這裡添加對戰模式的邏輯
            }
        });
        buttonPanel.add(versusModeButton);

        // 按鈕3: 遊戲說明
        JButton instructionsButton = new JButton("遊戲說明");
        instructionsButton.setFont(buttonFont);
        instructionsButton.setBackground(Color.ORANGE);
        instructionsButton.setForeground(Color.WHITE);
        instructionsButton.setFocusPainted(false);
        instructionsButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true)); // 圓弧邊框
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "這是一個Pac-man遊戲示例。\n經典小精靈模式：體驗經典遊戲樂趣。\n對戰模式：與朋友對決！", "遊戲說明", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(instructionsButton);

        // 添加按鈕面板到主窗口
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // 顯示窗口
        frame.setVisible(true);
    }
}
