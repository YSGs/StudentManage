package UI;

import Controllers.LoginControllers;
import UI.Dialog.Tips;
import UI.Dialog.ExitTips;
import Utils.Db;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class LoginUI {
    private static final Db db = new Db();

    private static JFrame lFrame;
    private static Container c;
    private static final JLabel a1 = new JLabel("用户名");
    private static final JTextField username = new JTextField();
    private static final JLabel a2 = new JLabel("密   码");
    private static final JPasswordField password = new JPasswordField();
    private static final JButton okbtn = new JButton("登入");
    private static final JButton cancelbtn = new JButton("退出");
    private static final JButton dbcbtn = new JButton("数据库配置");

    public static void create() {
        lFrame = new Window().create("登入", 600, 200, 300, 220);
        c = lFrame.getContentPane();
        //设置一层相当于桌布的东西
        c.setLayout(new BorderLayout());//布局管理器
        //设置按下右上角X号后关闭
        lFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //初始化--往窗体里放其他控件
        init();
        //设置窗体可见
        lFrame.setVisible(true);
        checksql();
    }

    public static void checksql() {
        boolean dbstatus = db.check();
        if (!dbstatus) {
            new Tips().create("无法连接至数据库！\n程序即将退出。", "错误", 0);
            System.exit(0);
        }
    }

    public static void init() {
        /*标题部分--North*/
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("学生管理系统"));
        c.add(titlePanel, "North");

        /*输入部分--Center*/
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        a1.setBounds(50, 20, 50, 20);
        a2.setBounds(50, 60, 50, 20);
        fieldPanel.add(a1);
        fieldPanel.add(a2);
        username.setBounds(110, 20, 120, 20);
        password.setBounds(110, 60, 120, 20);
        fieldPanel.add(username);
        password.setFont(new Font("宋体", Font.PLAIN, 18));
        fieldPanel.add(password);
        c.add(fieldPanel, "Center");

        /*按钮部分--South*/
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okbtn);
        buttonPanel.add(cancelbtn);
        buttonPanel.add(dbcbtn);
        c.add(buttonPanel, "South");

        // 绑定按钮监听器
        addActionListener(okbtn);
        addActionListener(cancelbtn);
        addActionListener(dbcbtn);

        // 绑定输入框监听器
        addKeyListener(username);
        addKeyListener(password);
    }

    // 登入触发
    private static void isLogin() {
        LoginControllers lc = new LoginControllers();
        Boolean status = lc.StartLogin(username.getText(), new String(password.getPassword()));

        if (status) {
            // 此处应销毁本窗口并创建Mange窗口
            MangeUI mFrame = new MangeUI();
            mFrame.create(); // 打开新界面
            lFrame.dispose();
        } else {
            new Tips().create("登入失败：用户名或密码错误！", "错误", 0);
        }
    }

    // 添加按钮监听器
    private static void addActionListener(JButton btn) {
        // 为按钮绑定监听器
        btn.addActionListener(e -> {
            // 对话框
            if (Objects.equals(e.getActionCommand(), "登入")) {
                if (username.getText().length() > 0 && new String(password.getPassword()).length() > 0) {
                    isLogin();
                } else {
                    new Tips().create("登入失败：用户名和密码不能为空！", "错误", 0);
                }
            }
            if (Objects.equals(e.getActionCommand(), "退出")) {
                new ExitTips().Exit();
            }
            if (Objects.equals(e.getActionCommand(), "数据库配置")) {
                lFrame.dispose();
                new DbConfigUI().create();
            }
        });
    }

    // 添加按键监听器
    private static void addKeyListener(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    isLogin();
                }
            }
        });
    }
}