package com.liwy.commons.dbutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库连接、结果集相关工具
 * @author liwy-psbc
 *
 */
public class DbUtils {
	private static String driver;
	private static String url;
	private static String usr;
	private static String pwd;
	/**
	 * 注册驱动
	 */
	static {
		Properties p = new Properties();
		try {
			p.load(DbUtils.class.getResourceAsStream("/config-jdbc.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		driver = p.getProperty("db_driver");
		url = p.getProperty("db_url");
		usr = p.getProperty("db_username");
		pwd = p.getProperty("db_password");

		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, usr, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}


	/**
	 * Close a <code>Connection</code>, avoid closing if null.
	 * 
	 * @param conn
	 *            Connection to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * Close a <code>Statement</code>, avoid closing if null.
	 * 
	 * @param stmt
	 *            Statement to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * Close a <code>ResultSet</code>, avoid closing if null.
	 * 
	 * @param rs
	 *            ResultSet to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param rs
	 * @param stmt
	 * @param con
	 */
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception ex) {
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}
		try {
			if (con != null)
				con.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * Commits a <code>Connection</code> then closes it, avoid closing if null.
	 * 
	 * @param conn
	 *            Connection to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void commitAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.commit();
			} finally {
				conn.close();
			}
		}
	}

	/**
	 * Rollback any changes made on the given connection.
	 * 
	 * @param conn
	 *            Connection to rollback. A null value is legal.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void rollback(Connection conn) throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * Performs a rollback on the <code>Connection</code> then closes it, avoid
	 * closing if null.
	 * 
	 * @param conn
	 *            Connection to rollback. A null value is legal.
	 * @throws SQLException
	 *             if a database access error occurs
	 * @since DbUtils 1.1
	 */
	public static void rollbackAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.rollback();
			} finally {
				conn.close();
			}
		}
	}

	// ----------ResultSet工具

	// 设置类型的默认值
	private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();
	static {
		primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
		primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
		primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
		primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
		primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
		primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
		primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
		primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
	}

	/**
	 * 根据类型获取结果集中某一列的值
	 * 
	 * @param rs
	 *            The <code>ResultSet</code> currently being processed. It is
	 *            positioned on a valid row before being passed into this
	 *            method.
	 * 
	 * @param index
	 *            The current column index being processed.
	 * 
	 * @param propType
	 *            The bean property type that this column needs to be converted
	 *            into.
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 * 
	 * @return The object from the <code>ResultSet</code> at the given column
	 *         index after optional type processing or <code>null</code> if the
	 *         column value was SQL NULL.
	 */
	private static Object processColumn(ResultSet rs, int index, Class<?> propType)
			throws SQLException {

		if (!propType.isPrimitive() && rs.getObject(index) == null) {
			return null;
		}

		if (propType.equals(String.class)) {
			return rs.getString(index);

		} else if (propType.equals(Integer.TYPE)
				|| propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(index));

		} else if (propType.equals(Boolean.TYPE)
				|| propType.equals(Boolean.class)) {
			return Boolean.valueOf(rs.getBoolean(index));

		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return Long.valueOf(rs.getLong(index));

		} else if (propType.equals(Double.TYPE)
				|| propType.equals(Double.class)) {
			return Double.valueOf(rs.getDouble(index));

		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return Float.valueOf(rs.getFloat(index));

		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return Short.valueOf(rs.getShort(index));

		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return Byte.valueOf(rs.getByte(index));

		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(index);

		} else if (propType.equals(SQLXML.class)) {
			return rs.getSQLXML(index);

		} else {
			return rs.getObject(index);
		}
	}

	/**
	 * 判断该对象是否属于该类型，兼容基本数据类型
	 * 
	 * @param value
	 *            The value to be passed into the setter method.
	 * @param type
	 *            The setter's parameter type (non-null)
	 * @return boolean True if the value is compatible (null => true)
	 */
	private static boolean isCompatibleType(Object value, Class<?> type) {
		// Do object check first, then primitives
		if (value == null || type.isInstance(value)) {
			return true;

		} else if (type.equals(Integer.TYPE) && value instanceof Integer) {
			return true;

		} else if (type.equals(Long.TYPE) && value instanceof Long) {
			return true;

		} else if (type.equals(Double.TYPE) && value instanceof Double) {
			return true;

		} else if (type.equals(Float.TYPE) && value instanceof Float) {
			return true;

		} else if (type.equals(Short.TYPE) && value instanceof Short) {
			return true;

		} else if (type.equals(Byte.TYPE) && value instanceof Byte) {
			return true;

		} else if (type.equals(Character.TYPE) && value instanceof Character) {
			return true;

		} else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
			return true;

		}
		return false;

	}

	/**
	 * 将结果转换为数组
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Object[] rs2Array(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int cols = meta.getColumnCount();
		Object[] result = new Object[cols];

		for (int i = 0; i < cols; i++) {
			result[i] = rs.getObject(i + 1);
		}
		return result;
	}

	/**
	 * 将结果转换为Map
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, Object> rs2Map(ResultSet rs) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();

		for (int i = 1; i <= cols; i++) {
			String columnName = rsmd.getColumnLabel(i);
			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(i);
			}
			result.put(columnName, rs.getObject(i));
		}

		return result;
	}

	/**
	 * 将结果转换为Bean
	 * 
	 * @param rs
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T rs2Bean(ResultSet rs, Class<T> type) throws SQLException {

		// 获取属性信息数组
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			throw new SQLException("Bean introspection failed: "
					+ e.getMessage());
		}
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors(); // 属性描述符

		// 将数据库列与Bean的属性相对应
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		int[] columnToProperty = new int[cols + 1];// 下标为列号，值为Bean的属性下标
		Arrays.fill(columnToProperty, -1);

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(col);
			}

			for (int i = 0; i < props.length; i++) {
				if (columnName.equalsIgnoreCase(props[i].getName())) {
					columnToProperty[col] = i;
					break;
				}
			}
		}

		// 创建Bean
		T bean;
		try {
			bean = type.newInstance();
		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + type.getName() + ": "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + type.getName() + ": "
					+ e.getMessage());
		}

		for (int i = 1; i < columnToProperty.length; i++) {
			// 该下标没有Bean的属性下标
			if (columnToProperty[i] == -1) {
				continue;
			}
			// 获取对应的属性信息
			PropertyDescriptor prop = props[columnToProperty[i]];
			Class<?> propType = prop.getPropertyType();// 属性类型

			Object value = null;
			if (propType != null) {
				// 获取该列的值
				value = processColumn(rs, i, propType);
				if (value == null && propType.isPrimitive()) {
					value = primitiveDefaults.get(propType);
				}
			}

			// 调用类的setter方法进行设值
			Method setter = prop.getWriteMethod();
			if (setter != null) {// 如果Setter方法不等于null
				Class<?>[] params = setter.getParameterTypes();
				try {
					// convert types for some popular ones
					if (value instanceof java.util.Date) {
						final String targetType = params[0].getName();
						if ("java.sql.Date".equals(targetType)) {
							value = new java.sql.Date(
									((java.util.Date) value).getTime());
						} else if ("java.sql.Time".equals(targetType)) {
							value = new java.sql.Time(
									((java.util.Date) value).getTime());
						} else if ("java.sql.Timestamp".equals(targetType)) {
							Timestamp tsValue = (Timestamp) value;
							int nanos = tsValue.getNanos();
							value = new java.sql.Timestamp(tsValue.getTime());
							((Timestamp) value).setNanos(nanos);
						}
					} else if (value instanceof String && params[0].isEnum()) {
						value = Enum.valueOf(params[0].asSubclass(Enum.class),
								(String) value);
					}

					// Don't call setter if the value object isn't the right
					// type
					if (isCompatibleType(value, params[0])) {
						setter.invoke(bean, new Object[] { value });
					} else {
						throw new SQLException("Cannot set " + prop.getName()
								+ ": incompatible types, cannot convert "
								+ value.getClass().getName() + " to "
								+ params[0].getName());
					}

				} catch (IllegalArgumentException e) {
					throw new SQLException("Cannot set " + prop.getName()
							+ ": " + e.getMessage());

				} catch (IllegalAccessException e) {
					throw new SQLException("Cannot set " + prop.getName()
							+ ": " + e.getMessage());

				} catch (InvocationTargetException e) {
					throw new SQLException("Cannot set " + prop.getName()
							+ ": " + e.getMessage());
				}
			}
		}

		return bean;
	}
}
