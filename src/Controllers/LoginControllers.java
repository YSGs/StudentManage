package Controllers;

import Utils.*;

import java.util.List;
import java.util.Map;

public class LoginControllers {

    /**
     * 初始化文本加密模块
     * 此处使用sha256加密后与数据库内已加密密码进行比对
     * 数据库不直接存储明文密码，保证安全。
     */
    private static final Encrypt ec = new Encrypt();
    private static final Db db = new Db();

    public Boolean StartLogin(String Username, String Password) {
        List<Map<String, Object>> result = db.Query("SELECT * FROM Admin WHERE username = '" + Username + "' and password = '" + ec.SHA256(Password) + "'");

        return result.size() > 0;
    }

}
