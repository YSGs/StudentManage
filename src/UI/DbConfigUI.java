package UI;

import UI.Dialog.Tips;
import Utils.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class DbConfigUI {

    private static final Config config = new Config();
    private static final Map<String, String> configData = config.LoadConfig();

    private static JFrame dbFrame;
    private Container c;
    private static final JLabel link_lab = new JLabel("连接地址：");
    private static final JTextField link = new JTextField();
    private static final JLabel username_lab = new JLabel("用户名：");
    private static final JTextField username = new JTextField();
    private static final JLabel dbname_lab = new JLabel("数据库名：");
    private static final JTextField dbname = new JTextField();
    private static final JLabel pass_lab = new JLabel("密码：");
    private static final JPasswordField password = new JPasswordField();
    private static final JButton okbtn = new JButton("保存");
    private static final JButton cancelbtn = new JButton("取消");

    public void create() {
        dbFrame = new Window().create("数据库配置", 600, 200, 300, 300);
        c = dbFrame.getContentPane();
        //设置一层相当于桌布的东西
        c.setLayout(new BorderLayout());//布局管理器
        //初始化--往窗体里放其他控件
        init();
        //设置窗体可见
        dbFrame.setVisible(true);
        // 禁止窗口拉伸
        dbFrame.setResizable(false);
        // 窗口居中显示
        dbFrame.setLocationRelativeTo(null);
    }

    public void init() {
        /*标题部分--North*/
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("学生管理系统"));
        c.add(titlePanel, "North");

        /*输入部分--Center*/
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        link_lab.setBounds(50, 20, 80, 20);
        username_lab.setBounds(50, 60, 80, 20);
        dbname_lab.setBounds(50, 100, 80, 20);
        pass_lab.setBounds(50, 140, 80, 20);
        fieldPanel.add(link_lab);
        fieldPanel.add(username_lab);
        fieldPanel.add(dbname_lab);
        fieldPanel.add(pass_lab);
        link.setBounds(110, 20, 120, 20);
        username.setBounds(110, 60, 120, 20);
        dbname.setBounds(110, 100, 120, 20);
        password.setBounds(110, 140, 120, 20);
        fieldPanel.add(link);
        fieldPanel.add(username);
        fieldPanel.add(dbname);
        password.setFont(new Font("宋体", Font.PLAIN, 18));
        fieldPanel.add(password);
        c.add(fieldPanel, "Center");

        /*按钮部分--South*/
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okbtn);
        buttonPanel.add(cancelbtn);
        c.add(buttonPanel, "South");

        // 绑定按钮监听器
        addActionListener(okbtn);
        addActionListener(cancelbtn);

        // 配置填入
        link.setText(configData.get("link"));
        dbname.setText(configData.get("dbname"));
        username.setText(configData.get("username"));
        password.setText(configData.get("password"));
    }

    // 添加按钮监听器
    private static void addActionListener(JButton btn) {
        // 为按钮绑定监听器
        btn.addActionListener(e -> {
            // 对话框
            if (Objects.equals(e.getActionCommand(), "保存")) {
                if (link.getText().length() > 0 && dbname.getText().length() > 0 && username.getText().length() > 0 && new String(password.getPassword()).length() > 0) {
                    Config config = new Config();
                    Boolean savestatus = config.WhiteConfig(link.getText(), dbname.getText(), username.getText(), new String(password.getPassword()));

                    if (savestatus) {
                        new Tips().create("保存成功！。", "提示", 1);
                        dbFrame.dispose();
                        LoginUI.create();
                    } else {
                        new Tips().create("配置文件保存失败！", "错误", 0);
                    }
                } else {
                    new Tips().create("请将配置填写完整！", "错误", 0);
                }
            }
            if (Objects.equals(e.getActionCommand(), "取消")) {
                /* 点击取消按钮时会检查配置文件是否存在，不存在则直接结束程序
                   如果存在则关闭配置窗口并打开登入窗口
                 */
                if (!config.FindConfig()) {
                    System.exit(0);
                } else {
                    dbFrame.dispose();
                    LoginUI.create();
                }
            }
        });
    }
}
