package com.liwy.commons.dbutils;

import com.liwy.commons.dbutils.handlers.ArrayListHandler;
import com.liwy.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class QueryRunnerTest {
    @Test
    public void testDriver1() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();

        Properties pro = new Properties();
        pro.put("user", "root");
        pro.put("password", "l332301842");

        Connection conn = driver.connect("jdbc:mysql://127.0.0.1/liwy_jfinaltest", pro);
        System.out.println(conn);
    }

    @Test
    public void testDriver2() throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Properties pro = new Properties();
        pro.load(this.getClass().getClassLoader().getResourceAsStream("config-jdbc.properties"));
        String db_driver = pro.getProperty("db_driver");
        String db_url = pro.getProperty("db_url");
        String db_username = pro.getProperty("db_username");
        String db_password = pro.getProperty("db_password");

        Driver driver = (Driver) Class.forName(db_driver).newInstance();
        Properties proper = new Properties();
        proper.put("user", db_username);
        proper.put("password", db_password);
        Connection conn = driver.connect(db_url, proper);
        System.out.println(conn);
    }

    @Test
    public void testDriverManager() throws SQLException, IOException, ClassNotFoundException {
        Properties pro = new Properties();
        pro.load(this.getClass().getClassLoader().getResourceAsStream("config-jdbc.properties"));
        String db_driver = pro.getProperty("db_driver");
        String db_url = pro.getProperty("db_url");
        String db_username = pro.getProperty("db_username");
        String db_password = pro.getProperty("db_password");

        Class.forName(db_driver);

        Connection conn = DriverManager.getConnection(db_url, db_username, db_password);
        System.out.println(conn);

    }

    @Test
    public void testQuery() throws SQLException {
        Connection conn = DbUtils.getConnection();
        QueryRunner qu = new QueryRunner();
        String sql = "select dept_code,dept_name from framedepartment where para_seq=?";
        List<Object[]> list = qu.query(conn, sql, new ArrayListHandler(), "120001");
        for (Object[] objA : list) {
            System.out.println(Arrays.deepToString(objA));
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        Connection conn = DbUtils.getConnection();
        QueryRunner qu = new QueryRunner();
        String sql = "insert into liwy_article(id,title,author,content,time,liwy_text) values(?,?,?,?,?,?)";
        int num = qu.update(conn, sql, 15, "liwy121212", "liwy", "12121212212", new Date(), "12121212");
        System.out.println(num);
    }

    @Test
    public void testBatch() throws SQLException {
        Connection conn = DbUtils.getConnection();
        QueryRunner qu = new QueryRunner();
        String sql = "insert into liwy_article(title,author,content,time,liwy_text) values(?,?,?,?,?)";
        Object[][] par = new Object[][]{{"liwy12", "liwy", "121212", new Date(), "1111111"},
                {"liwy12", "liwy", "121212", new Date(), "1111111"},
                {"liwy12", "liwy", "121212", new Date(), "1111111"},
                {"liwy12", "liwy", "121212", new Date(), "1111111"}};
        int[] num = qu.batch(conn, sql, par);
        System.out.println(Arrays.toString(num));
    }

    @Test
    public void testInsert() throws SQLException {
        Connection conn = DbUtils.getConnection();
        QueryRunner qu = new QueryRunner();
        String sql = "insert into liwy_article(title,author,content,time,liwy_text) values(?,?,?,?,?)";
        Object[][] par = new Object[][]{{"liwy12", "liwy", "121212", new Date(), "1111111"},
                {"liwy12", "liwy", "121212", new Date(), "1111111"},
                {"liwy12", "liwy", "121212", new Date(), "1111111"},
                {"liwy12", "liwy", "121212", new Date(), "1111111"}};
        List<Map<String, Object>> num = qu.insertBatch(conn, sql, new MapListHandler(), par);
        System.out.println(num.toString());
    }

//    @Test
//	public void testFunction() throws SQLException {
//		Connection conn = DbUtils.getConnection();
//		CallableStatement stat = conn.prepareCall("{?= call liwy_add(?,?)}");
//		stat.setInt(2,100);
//		stat.setInt(3,100);
//		stat.registerOutParameter(1, OracleType.STYLE_INT);
//		stat.execute();
//		int result = stat.getInt(1);
//		System.out.println(result);
//		DbUtils.close(stat);
//
//		CallableStatement stat1 = conn.prepareCall("{call LIWY_PR_MICHAEL(?,?,?)}");
//		stat1.setString(1,"");
//		stat1.setInt(2,3000);
//		stat1.registerOutParameter(3,OracleType.STYLE_INT);
//		stat1.execute();
//		result = stat1.getInt(3);
//		System.out.println(result);
//		DbUtils.close(stat1);
//
//		stat1 = conn.prepareCall("{call LIWY_PR_OUTRS(?,?)}");
//		stat1.setInt(1,3000);
//		stat1.registerOutParameter(2, OracleTypes.CURSOR);
//		stat1.execute();
//		ResultSet rs = (ResultSet) stat1.getObject(2);
//		while (rs.next()) {
//			System.out.println(rs.getString(2));
//		}
//		DbUtils.close(stat1);
//	}

//    @Test
//	public void testProcedures() throws SQLException {
//		Connection conn = DbUtils.getConnection();
//		Map<String,Object> param1 = new HashMap<String,Object>();
//		param1.put("sqlType",OracleTypes.INTEGER);
//		param1.put("value",200);
//		QueryRunner.call(conn,"{call LIWY_PR_INOUT(?,?)}","李",param1);
//		System.out.println(param1.get("value"));
//	}
}
