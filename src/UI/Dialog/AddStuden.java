package UI.Dialog;

import Controllers.ManageControllers;
import UI.MangeUI;
import UI.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddStuden {
    private static final ManageControllers mac = new ManageControllers();

    private static JFrame AsFrame;
    private Container c;
    private static final JLabel id_lab = new JLabel("学号（必填）：");
    private static final JTextField id = new JTextField(12);
    private static final JLabel name_lab = new JLabel("姓名（必填）：");
    private static final JTextField name = new JTextField();
    private static final JLabel sex_lab = new JLabel("性别：");
    private final JComboBox<String> sex = new JComboBox<>();
    private static final JLabel age_lab = new JLabel("年龄（必填）：");
    private static final JTextField age = new JTextField(3);
    private static final JLabel class_lab = new JLabel("班级（必填）：");
    private static final JTextField class_text = new JTextField(10);
    private static final JLabel phone_lab = new JLabel("电话：");
    private static final JTextField phone = new JTextField(11);
    private String title;
    private String gtype;
    private String sids;

    public void create(String type, String sid) {
        gtype = type;
        sids = sid;
        if (Objects.equals(type, "Add")) {
            title = "学生信息录入";
        } else if (Objects.equals(type, "modify")) {
            title = "学生信息修改";
        }
        AsFrame = new Window().create(title, 600, 200, 300, 360);
        c = AsFrame.getContentPane();
        //设置一层相当于桌布的东西
        c.setLayout(new BorderLayout());//布局管理器
        //初始化--往窗体里放其他控件
        init();
        //设置窗体可见
        AsFrame.setVisible(true);
        // 禁止窗口拉伸
        AsFrame.setResizable(false);
        // 窗口居中显示
        AsFrame.setLocationRelativeTo(null);

        // 重写窗口关闭事件
        AsFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
        id_lab.setBounds(40, 20, 120, 20);
        name_lab.setBounds(40, 60, 120, 20);
        sex_lab.setBounds(40, 100, 120, 20);
        age_lab.setBounds(40, 140, 120, 20);
        class_lab.setBounds(40, 180, 120, 20);
        phone_lab.setBounds(40, 220, 120, 20);
        fieldPanel.add(id_lab);
        fieldPanel.add(name_lab);
        fieldPanel.add(sex_lab);
        fieldPanel.add(age_lab);
        fieldPanel.add(class_lab);
        fieldPanel.add(phone_lab);
        id.setBounds(130, 20, 120, 20);
        name.setBounds(130, 60, 120, 20);
        sex.setBounds(130, 100, 120, 20);
        sex.addItem("男");
        sex.addItem("女");
        age.setBounds(130, 140, 120, 20);
        class_text.setBounds(130, 180, 120, 20);
        phone.setBounds(130, 220, 120, 20);
        fieldPanel.add(id);
        fieldPanel.add(name);
        fieldPanel.add(sex);
        fieldPanel.add(age);
        fieldPanel.add(class_text);
        fieldPanel.add(phone);
        c.add(fieldPanel, "Center");

        /*按钮部分--South*/
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton okbtn = new JButton("保存");
        JButton cancelbtn = new JButton("取消");
        buttonPanel.add(okbtn);
        buttonPanel.add(cancelbtn);
        c.add(buttonPanel, "South");

        // 绑定按钮监听器
        addActionListener(okbtn);
        addActionListener(cancelbtn);

        id.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // None
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                String Id = id.getText();
                if (Id.length() > 3) {
                    String ClassId = Id.substring(0, Id.length() - 2);
                    class_text.setText(ClassId);
                }
            }
        });

        // 如果是修改则禁止修改sid,并将数据写入文本框
        if (Objects.equals(gtype, "modify")) {
            id.setEnabled(false);

            List<String> sexs = new ArrayList<>();
            sexs.add("男");
            sexs.add("女");

            Object[] info = mac.query_once("sid", sids)[0];
            id.setText(info[0].toString());
            name.setText(info[1].toString());
            sex.setSelectedIndex(sexs.indexOf(info[2].toString()));
            age.setText(info[3].toString());
            class_text.setText(info[4].toString());
            phone.setText(info[5].toString());
        } else {
            id.setEnabled(true);
        }
    }

    // 添加按钮监听器
    private void addActionListener(JButton btn) {
        // 为按钮绑定监听器
        btn.addActionListener(e -> {
            // 对话框
            if (Objects.equals(e.getActionCommand(), "保存")) {
                if (id.getText().length() > 0 && name.getText().length() > 0 && age.getText().length() > 0 && class_text.getText().length() > 0) {
                    if (Objects.equals(gtype, "Add")) {
                        boolean status = mac.inset(id.getText(), name.getText(), Objects.requireNonNull(sex.getSelectedItem()).toString(), age.getText(), class_text.getText(), phone.getText());
                        if (status) {
                            new Tips().create("添加成功！", "提示", 1);
                        } else {
                            new Tips().create("添加失败！\n请检查学生信息是否已存在。", "错误", 0);
                        }
                    } else if (Objects.equals(gtype, "modify")) {
                        boolean status = mac.update(id.getText(), name.getText(), Objects.requireNonNull(sex.getSelectedItem()).toString(), age.getText(), class_text.getText(), phone.getText());
                        if (status) {
                            new Tips().create("修改成功！", "提示", 1);
                        } else {
                            new Tips().create("修改失败！", "错误", 0);
                        }
                    }

                    MangeUI.reloadTable(mac.query_table());
                    AsFrame.dispose();
                } else {
                    new Tips().create("必填项不能为空！", "错误", 0);
                }
            }
            if (Objects.equals(e.getActionCommand(), "取消")) {
                int opt = JOptionPane.showConfirmDialog(null, "确定要取消吗？您所做的更改将不会保存。", "提示", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    AsFrame.dispose();
                }
            }
        });
    }
}
