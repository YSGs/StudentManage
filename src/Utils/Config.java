package Utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {

    // 检查配置文件是否存在
    public Boolean FindConfig() {
        File file = new File("./config.properties");
        return file.exists();
    }

    // 写入配置文件
    public Boolean WhiteConfig(String link, String dbname, String username, String password) {
        Properties prop = new Properties();
        try{
            FileOutputStream oFile = new FileOutputStream("config.properties", false);
            prop.setProperty("link", link);
            prop.setProperty("dbname", dbname);
            prop.setProperty("username", username);
            prop.setProperty("password", password);
            prop.store(oFile, "StudentManage Config");
            oFile.flush();
            oFile.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    // 读取配置文件
    public Map<String, String> LoadConfig() {
        Properties prop = new Properties();
        Map<String,String> configmap= new HashMap<>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
            prop.load(in);

            for (String key : prop.stringPropertyNames()) {
                configmap.put(key, prop.getProperty(key));
            }

            in.close();

            return configmap;
        } catch (Exception e) {
            return configmap;
        }
    }
}
