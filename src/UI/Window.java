package UI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Window extends JFrame {

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    public JFrame create(String title, int x, int y, int width, int height) {
        JFrame wFrame = new JFrame(title);
        //设置窗体的位置及大小
        wFrame.setBounds(x, y, width, height);
        //设置按下右上角X号后关闭
        wFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 禁止窗口拉伸
        wFrame.setResizable(false);
        // 窗口居中显示
        wFrame.setLocationRelativeTo(null);

        InitGlobalFont(new Font("黑体", Font.PLAIN, 12));

        wFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource( "image/icon.png")));

        return wFrame;
    }
}
