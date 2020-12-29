package UI.Dialog;

import Controllers.ManageControllers;
import UI.MangeUI;
import UI.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class SearchUI {
    private static final ManageControllers mac = new ManageControllers();

    private static JFrame sFrame;
    private Container c;
    private JTextField input;

    String[] listData = new String[]{"姓名", "学号", "男", "女", "班级", "手机号码"};
    private final JComboBox<String> condition = new JComboBox<>(listData);

    public void create() {
        sFrame = new Window().create("查找",600, 200, 450, 80);
        c = sFrame.getContentPane();
        c.setLayout(new BorderLayout());
        init();
        sFrame.setVisible(true);
        sFrame.setResizable(false);
        sFrame.setLocationRelativeTo(null);
        // 重写窗口关闭事件
        sFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void init() {
        c.add(condition, "West");
        input = new JTextField();
        c.add(input, "Center");
        JButton ok = new JButton("查找");
        c.add(ok, "East");

        addActionListener(ok);
        addKeyListener(input);

        // 将焦点默认放在输入框上
        sFrame.addWindowListener(new WindowAdapter(){
            public void windowOpened(WindowEvent e) {
                input.requestFocus();
            }
        });

        condition.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if(Objects.equals(e.getItem().toString(), "男") || Objects.equals(e.getItem().toString(), "女")) {
                        input.setEnabled(false);
                    } else {
                        input.setEnabled(true);
                    }
                }
            }
        });
    }

    private void search() {
        if(condition.getSelectedIndex() == 0 && input.getText().length() > 0) {
            MangeUI.reloadTable(mac.all_search("sname", input.getText()));
        } else if(condition.getSelectedIndex() == 1 && input.getText().length() > 0) {
            MangeUI.reloadTable(mac.all_search("sid", input.getText()));
        } else if(condition.getSelectedIndex() == 2) {
            MangeUI.reloadTable(mac.all_search("ssex", "男"));
        } else if(condition.getSelectedIndex() == 3) {
            MangeUI.reloadTable(mac.all_search("ssex", "女"));
        } else if(condition.getSelectedIndex() == 4 && input.getText().length() > 0) {
            MangeUI.reloadTable(mac.all_search("sclass", input.getText()));
        } else if(condition.getSelectedIndex() == 5 && input.getText().length() > 0) {
            MangeUI.reloadTable(mac.all_search("sphone", input.getText()));
        } else {
            new Tips().create("搜索内容不能为空！", "错误", 0);
        }
    }

    // 添加按钮监听器
    private void addActionListener(JButton btn) {
        // 为按钮绑定监听器
        btn.addActionListener(e -> {
            // 对话框
            if (Objects.equals(e.getActionCommand(), "查找")) {
                search();
            }
        });
    }

    // 添加按键监听器
    private void addKeyListener(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    search();
                }
            }
        });
    }
}
