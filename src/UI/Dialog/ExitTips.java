package UI.Dialog;

import javax.swing.*;

public class ExitTips {
    public void Exit() {
        int opt = JOptionPane.showConfirmDialog(null, "您确定要退出吗？", "提示", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
