package com.liwy.commons.dbutils;

import com.liwy.commons.dbutils.model.Article;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by liwy on 2017/4/2.
 */
public class DbUtilsTest {

    @Test
    public void testDriver() throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        Properties pro = new Properties();
        pro.setProperty("user", "root");
        pro.setProperty("password", "l332301842");

        // Driver driver = new com.mysql.jdbc.Driver();
        Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver")
                .newInstance();
        // Connection conn =
        // driver.connect("jdbc:mysql://127.0.0.1:3306/liwy_jfinaltest", pro);
        DriverManager.registerDriver(driver);// 可省略
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/liwy_jfinaltest", pro);
        System.out.println(conn);
    }

    @Test
    public void testGetConnect() {
        Connection conn = DbUtils.getConnection();
        System.out.println(conn);
    }
    @Test
    public void testIntrospector() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Article.class);
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors(); // 属性描述符
        for (Object obj : props) {
            System.out.println(obj);
            // java.beans.PropertyDescriptor[name=time; propertyType=class
            // java.sql.Date; readMethod=public java.sql.Date
            // com.liwy.commons.dbutils.Article.getTime(); writeMethod=public
            // void com.liwy.commons.dbutils.Article.setTime(java.sql.Date)]
            // java.beans.PropertyDescriptor[name=title; propertyType=class
            // java.lang.String; readMethod=public java.lang.String
            // com.liwy.commons.dbutils.Article.getTitle(); writeMethod=public
            // void com.liwy.commons.dbutils.Article.setTitle(java.lang.String)]
        }
    }
}
