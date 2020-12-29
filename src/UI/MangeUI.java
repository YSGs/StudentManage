package UI;

import Controllers.ManageControllers;
import UI.Dialog.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Objects;

public class MangeUI {
    private static final ManageControllers mac = new ManageControllers();

    private static JFrame mFrame;
    private static Container c;
    private static JPopupMenu m_popupMenu;
    private static final JMenuBar menuBar = new JMenuBar();
    private static final JMenu mainMenu = new JMenu("菜单");
    private static final JMenu aboutMenu = new JMenu("关于");
    private static final JMenuItem mainMenu_add = new JMenuItem("添加");
    private static final JMenuItem mainMenu_search = new JMenuItem("查找");
    private static final JMenuItem mainMenu_reload = new JMenuItem("刷新");
    private static final JMenuItem mainMenu_exit = new JMenuItem("退出");
    private static final JMenuItem aboutMenu_about = new JMenuItem("关于程序");


    private final JPanel centerLayout = new JPanel(new BorderLayout());

    private static final Object[] tablehead = {"学号", "姓名", "性别", "年龄", "班级", "电话"};
    private static Object[][] rowData = mac.query_table();
    private static final JTable table = new JTable(rowData, tablehead);

    public void create() {
        mFrame = new Window().create("管理器", 600, 200, 800, 600);
        c = mFrame.getContentPane();

        //设置一层相当于桌布的东西
        c.setLayout(new BorderLayout());//布局管理器
        //设置按下右上角X号后关闭
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //初始化--往窗体里放其他控件
        init();
        //设置窗体可见
        mFrame.setVisible(true);

        // 禁止窗口拉伸
        mFrame.setResizable(false);

        // 窗口居中显示
        mFrame.setLocationRelativeTo(null);

        // 设置table样式
        setColumnColor(table);
        table.getTableHeader().setBackground(Color.decode("#4d83b8"));
        table.getTableHeader().setForeground(Color.decode("#FFFFFF"));
        table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 26));
        table.getTableHeader().setFont(new Font("Table", Font.PLAIN, 12));
    }

    public static void setColumnColor(JTable table) {
        try {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                private static final long serialVersionUID = 1L;

                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (row % 2 == 0)
                        setBackground(Color.WHITE);//设置奇数行底色
                    else if (row % 2 == 1)
                        setBackground(new Color(220, 230, 241));//设置偶数行底色
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            };
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
            }
            tcr.setHorizontalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // 添加工具栏到顶部
        menuBar.add(mainMenu);
        menuBar.add(aboutMenu);
        // 子菜单添加到一级菜单
        mainMenu.add(mainMenu_add);
        mainMenu.add(mainMenu_search);
        mainMenu.add(mainMenu_reload);
        mainMenu.addSeparator();       // 添加一条分割线
        mainMenu.add(mainMenu_exit);
        aboutMenu.add(aboutMenu_about);
        c.add(menuBar, "North");

        // 中间面板
        centerLayout.add(table.getTableHeader(), "North");
        centerLayout.add(new JScrollPane(table), "Center");
        c.add(centerLayout, "Center");

        // 绑定按钮监听器
        addActionListener(mainMenu_add);
        addActionListener(mainMenu_search);
        addActionListener(mainMenu_reload);
        addActionListener(mainMenu_exit);
        addActionListener(aboutMenu_about);

        // 创建表格右键菜单
        createPopupMenu();
        // 绑定表格右键事件
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        table.setModel(DTM());
    }

    public static TableModel DTM() {
        return new DefaultTableModel(rowData, tablehead) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static void reloadTable(Object[][] data) {
        rowData = data;
        table.setModel(DTM());
        table.setEnabled(true);
        setColumnColor(table);
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        mouseRightButtonClick(evt);
    }

    private void createPopupMenu() {
        m_popupMenu = new JPopupMenu();

        JMenuItem addMenItem = new JMenuItem("添加");
        JMenuItem modifyMenItem = new JMenuItem("修改");
        JMenuItem delMenItem = new JMenuItem("删除");
        JMenuItem searchMenItem = new JMenuItem("查找");
        JMenuItem reloadMenItem = new JMenuItem("刷新");

        delMenItem.addActionListener(evt -> {
            //该操作需要做的事
            String sid = rowData[table.getSelectedRow()][0].toString();
            String sname = rowData[table.getSelectedRow()][1].toString();
            System.out.println();
            int opt = JOptionPane.showConfirmDialog(null, "是否要删除" + sid + " " + sname + "？", "提示", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                System.out.println("ok");
                if (mac.delete(sid)) {
                    new Tips().create("删除成功！", "提示", 1);
                    reloadTable(mac.query_table());
                } else {
                    new Tips().create("删除失败！", "错误", 0);
                }
            }
        });

        addMenItem.addActionListener(evt -> new AddStuden().create("Add", "0"));

        modifyMenItem.addActionListener(evt -> {
            String sid = rowData[table.getSelectedRow()][0].toString();
            new AddStuden().create("modify", sid);
        });

        searchMenItem.addActionListener(evt -> {
            new SearchUI().create();
        });

        reloadMenItem.addActionListener(evt -> reloadTable(mac.query_table()));

        m_popupMenu.add(addMenItem);
        m_popupMenu.addSeparator();
        m_popupMenu.add(modifyMenItem);
        m_popupMenu.add(delMenItem);
        m_popupMenu.add(searchMenItem);
        m_popupMenu.addSeparator();
        m_popupMenu.add(reloadMenItem);

    }

    //鼠标右键点击事件
    private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //通过点击位置找到点击为表格中的行
            int focusedRowIndex = table.rowAtPoint(evt.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            //将表格所选项设为当前右键点击的行
            table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            //弹出菜单
            m_popupMenu.show(table, evt.getX(), evt.getY());
        }

    }

    // 添加按钮监听器
    private void addActionListener(JMenuItem item) {
        item.addActionListener(e -> {
            // 对话框
            if (Objects.equals(e.getActionCommand(), "添加")) {
                new AddStuden().create("Add", "0");
            }
            if (Objects.equals(e.getActionCommand(), "查找")) {
                new SearchUI().create();
            }
            if (Objects.equals(e.getActionCommand(), "刷新")) {
                reloadTable(mac.query_table());
            }
            if (Objects.equals(e.getActionCommand(), "退出")) {
                new ExitTips().Exit();
            }
            if (Objects.equals(e.getActionCommand(), "关于程序")) {
                new About().create();
            }
        });
    }

}
