package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Db {
    private static final Config config = new Config();
    private static Map<String, String> configData;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs;


    private void create() {
        configData = config.LoadConfig();
        try {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String url = "jdbc:sqlserver://" + configData.get("link") + ";DatabaseName=" + configData.get("dbname") + ";";
            //连接数据库
            conn = DriverManager.getConnection(url, configData.get("username"), configData.get("password"));
            //建立Statement对象
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void close() {
        try {
            conn.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public boolean check() {
        create();

        try {
            rs = stmt.executeQuery("SELECT * FROM Student");
            close();
            return rs != null;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Map<String, Object>> Query(String sql) {
        create();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try {
            rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数

            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }

            close();

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    public boolean Insert(String sql) {
        create();
        PreparedStatement ps;
        int row;
        if (conn != null) {
            try {
                ps = conn.prepareStatement(sql);
                row = ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            close();

            if (row > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean DeleteAndUpdate(String sql) {
        create();
        if (conn != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}