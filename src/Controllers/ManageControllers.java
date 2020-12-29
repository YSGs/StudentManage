package Controllers;

import Utils.Db;

import java.util.List;
import java.util.Map;

public class ManageControllers {
    private static final Db db = new Db();

    public Object[][] query_table() {

        List<Map<String, Object>> rst = db.Query("SELECT * FROM Student");

        int listsize = rst.size();
        Object[][] result = new Object[listsize][6];

        int i = 0;
        for (Map map : rst) {
            //result[i][0] = map.get("id").toString();
            result[i][0] = map.get("sid").toString();
            result[i][1] = map.get("sname").toString();
            result[i][2] = map.get("ssex").toString();
            result[i][3] = map.get("sage").toString();
            result[i][4] = map.get("sclass").toString();
            result[i][5] = map.get("sphone") == null || map.get("sphone").toString().isEmpty() ? "未录入" : map.get("sphone").toString();
            i++;
        }

        return result;
    }

    public Object[][] query_once(String key, String value) {
        List<Map<String, Object>> rst = db.Query("SELECT * FROM Student WHERE " + key + " = '" + value + "'");

        int listsize = rst.size();
        Object[][] result = new Object[listsize][6];

        int i = 0;
        for (Map map : rst) {
            //result[i][0] = map.get("id").toString();
            result[i][0] = map.get("sid").toString();
            result[i][1] = map.get("sname").toString();
            result[i][2] = map.get("ssex").toString();
            result[i][3] = map.get("sage").toString();
            result[i][4] = map.get("sclass").toString();
            result[i][5] = map.get("sphone") == null ? "未录入" : map.get("sphone").toString();
            i++;
        }

        return result;
    }

    public boolean inset(String id, String name, String sex, String age, String class_id, String phone) {
        Object[][] result = query_once("sid", id);
        if (result.length > 0) {
            return false;
        } else {
            return db.Insert("INSERT INTO Student VALUES ('" + id + "', '" + name + "', '" + sex + "', '" + age + "', '" + class_id + "', '" + phone + "')");
        }
    }

    public boolean delete(String sid) {
        return db.DeleteAndUpdate("DELETE FROM Student WHERE sid = '" + sid + "'");
    }

    public boolean update(String id, String name, String sex, String age, String class_id, String phone) {
        return db.DeleteAndUpdate("UPDATE Student SET sid = '" + id + "', sname = '" + name + "', ssex = '" + sex + "', sage = '" + age + "', sclass = '" + class_id + "', sphone = '" + phone + "' WHERE sid = '" + id + "'");
    }

    public Object[][] all_search(String key, String value) {
        List<Map<String, Object>> rst = db.Query("select * from Student where " + key + " like '" + value + "'");

        int listsize = rst.size();
        Object[][] result = new Object[listsize][6];

        int i = 0;
        for (Map map : rst) {
            result[i][0] = map.get("sid").toString();
            result[i][1] = map.get("sname").toString();
            result[i][2] = map.get("ssex").toString();
            result[i][3] = map.get("sage").toString();
            result[i][4] = map.get("sclass").toString();
            result[i][5] = map.get("sphone") == null ? "未录入" : map.get("sphone").toString();
            i++;
        }

        return result;
    }
}
