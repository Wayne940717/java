import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu {
    public static void main(String[] args) {
        // 創建 JFrame 主窗口
        JFrame frame = new JFrame("遊戲選單");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // 添加標題
        JLabel titleLabel = new JLabel("歡迎來到遊戲選單", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        // 創建按鈕面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // 按鈕1: 啟動遊戲
        JButton startGameButton = new JButton("開始遊戲");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "遊戲啟動中...", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 在這裡添加啟動遊戲的邏輯
            }
        });
        buttonPanel.add(startGameButton);

        // 按鈕2: 顯示說明
        JButton instructionsButton = new JButton("遊戲說明");
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "這是一個示例遊戲。按下開始遊戲來體驗！", "遊戲說明", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(instructionsButton);

        // 按鈕3: 退出遊戲
        JButton exitButton = new JButton("退出遊戲");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "確定要退出遊戲嗎？", "退出", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        buttonPanel.add(exitButton);

        // 添加按鈕面板到主窗口
        frame.add(buttonPanel, BorderLayout.CENTER);

        // 顯示窗口
        frame.setVisible(true);
    }
}

