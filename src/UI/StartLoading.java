package UI;

import javax.swing.*;
import java.awt.*;

public class StartLoading extends JFrame {
    private static JFrame LdFrame;

    public void create() {
        LdFrame = new Window().create("学生管理系统", 600, 200, 400, 200);
        LdFrame.setBackground(Color.decode("#333333"));
        Container c = LdFrame.getContentPane();
        c.setBackground(Color.decode("#333333"));
        JLabel info = new JLabel("程序启动中 正在检查数据库配置。", JLabel.CENTER);
        info.setBackground(Color.decode("#333333"));
        info.setForeground(Color.WHITE);
        c.add(info, "Center");

        LdFrame.setUndecorated(true);
        //设置窗体可见
        LdFrame.setVisible(true);
        // 禁止窗口拉伸
        LdFrame.setResizable(false);
        // 窗口居中显示
        LdFrame.setLocationRelativeTo(null);
    }

    public void remove() {
        LdFrame.dispose();
    }
}
