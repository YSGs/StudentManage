package Main;

import UI.DbConfigUI;
import UI.LoginUI;
import UI.Dialog.Tips;
import UI.StartLoading;
import Utils.Config;

import java.util.Map;

import Utils.Db;

public class Main {

    public static void main(String[] args) {

        new StartLoading().create();

        Config configs = new Config();
        Db db = new Db();
        Boolean isconfig = configs.FindConfig();
        Map<String, String> configdata = configs.LoadConfig();

        // 检查配置文件是否存在不存在则打开配置窗口
        if (isconfig) {
            if (configdata.containsKey("username") && configdata.containsKey("dbname") && configdata.containsKey("link") && configdata.containsKey("password")) {
                if (db.check()) {
                    // 初始化登入窗口
                    LoginUI.create();
                } else {
                    new Tips().create("无法连接至数据库！\n程序即将退出。", "错误", 0);
                }
                new StartLoading().remove();
            } else {
                new StartLoading().remove();
                new Tips().create("数据库配置错误！\n程序将跳转至数据库配置。", "错误", 0);
                new DbConfigUI().create();
            }
        } else {
            new StartLoading().remove();
            new DbConfigUI().create();
        }
    }
}
