package com.liwy.commons.dbutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * SQL控制台操作工具
 * 
 * 一个模拟sql客户端的程序
 * @author liwy
 *
 */
public class SqlCmdUtils {
	public static void main(String[] args) {
		Connection con = null;
		while ((con = getConnection()) == null) {
		}
		try {
			con.setAutoCommit(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		handleCommand(con);
		DbUtils.close(null, null, con);
		System.out.println("再见!");
	}

	private static void handleCommand(Connection con) {
		String command = "";
		boolean flag = true;
		while (flag) {
			command = getCommand();
			if ("quit".equals(command)) {
				flag = false;
			} else if ("commit".equals(command) || "rollback".equals(command)) {
				handleCommit(con, command);
			} else {
				handleSQL(con, command);
			}
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	private static Connection getConnection() {
		Connection con = null;
		String url = "";
		String usr = "";
		String pwd = "";
		url = prompt("请输入URL:");
		usr = prompt("请输入用户名:");
		pwd = prompt("请输入密码:");
		try {
			con = DriverManager.getConnection(url, usr, pwd);
		} catch (Exception e) {
			System.out.println("连接错误:" + e.getMessage());
		}
		return con;
	}

	/**
	 * 获取SQL操作
	 * 
	 * @return
	 */
	private static String getCommand() {
		StringBuffer sb = new StringBuffer();
		String command = "";
		String message = "SQL->";
		boolean flag = true;
		int c = 0;
		while (flag) {
			if (c++ != 0)
				message = c + "->";
			sb.append(prompt(message) + " ");
			command = sb.toString().trim();
			if (command.endsWith(";")) {
				flag = false;
			}
		}
		return command.substring(0, command.length() - 1).trim();
	}

	/**
	 * 执行命令
	 * 
	 * @param con
	 * @param command
	 */
	private static void handleCommit(Connection con, String command) {
		try {
			if ("commit".equals(command)) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			System.out.println("提交/回滚失败:" + e.getMessage());
		}
	}

	/**
	 * 执行sql
	 * 
	 * @param con
	 * @param command
	 */
	private static void handleSQL(Connection con, String command) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(command);
			if (ps.execute()) {
				rs = ps.getResultSet();
//				QueryUtils.printRs(rs);
				while(rs.next()) {
					System.out.println(DbUtils.rs2Map(rs));
				}
			} else {
				System.out.println("更新成功:" + ps.getUpdateCount() + " .");
			}
		} catch (Exception e) {
			System.out.println("数据操作失败:" + e.getMessage());
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 获取控制台输入信息
	 * 
	 * @param message
	 * @return
	 */
	private static String prompt(String message) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(message);
		String input = "";
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out.println("IO错误:" + e.getMessage());
		}
		return input;
	}
}
