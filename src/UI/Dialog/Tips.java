package UI.Dialog;

import javax.swing.*;

public class Tips {
    public void create(String msg, String title, int type) {
        JOptionPane.showMessageDialog(null, msg, title, type);
    }
}
