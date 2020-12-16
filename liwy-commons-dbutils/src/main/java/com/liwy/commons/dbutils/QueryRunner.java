package com.liwy.commons.dbutils;

import com.liwy.commons.dbutils.handlers.ResultSetHandler;

import java.sql.*;
import java.util.Arrays;

/**
 * CRUD相关方法
 * @author liwy-psbc
 *
 */
public class QueryRunner {

	/**
用指定的参数对象绑定到PreparedStatement中的占位符

@param stmt
           PreparedStatement to fill
@param params
           Query replacement parameters; <code>null</code> is a valid
           value to pass in.
@throws SQLException
            if a database access error occurs
	 */
	private void fillStatement(PreparedStatement stmt, Object... params)
			throws SQLException {

		// 检查参数与占位符的个数是否一致
		ParameterMetaData pmd = null;
		pmd = stmt.getParameterMetaData();
		int stmtCount = pmd.getParameterCount();
		int paramsCount = params == null ? 0 : params.length;
		if (stmtCount != paramsCount) {
			throw new SQLException("Wrong number of parameters: expected "
					+ stmtCount + ", was given " + paramsCount);
		}

		// 如果没有参数，不进行绑定
		if (params == null) {
			return;
		}

		// 开始绑定参数
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {// 如果有参数为null
				int sqlType = pmd.getParameterType(i + 1);
				stmt.setNull(i + 1, sqlType);
			}
		}
	}

	/**
     * 抛出自定义的异常信息
     *
     * @param cause
     *            The original exception that will be chained to the new
     *            exception when it's rethrown.
     *
     * @param sql
     *            The query that was executing when the exception happened.
     *
     * @param params
     *            The query replacement parameters; <code>null</code> is a valid
     *            value to pass in.
     *
     * @throws SQLException
     *             if a database access error occurs
     */
    private void rethrow(SQLException cause, String sql, Object... params)
            throws SQLException {

        String causeMessage = cause.getMessage();
        if (causeMessage == null) {
            causeMessage = "";
        }
        StringBuffer msg = new StringBuffer(causeMessage);

        msg.append(" Query: ");
        msg.append(sql);
        msg.append(" Parameters: ");

        if (params == null) {
            msg.append("[]");
        } else {
            msg.append(Arrays.deepToString(params));
        }

        SQLException e = new SQLException(msg.toString(), cause.getSQLState(),
                cause.getErrorCode());
        e.setNextException(cause);

        throw e;
    }

	/**
查询方法

@param conn
           数据库连接
@param sql
           查询SQL
@param rsh
           结果集处理器
@param params
           SQL参数
@return
@throws SQLException
	 */
	public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh,
			Object... params) throws SQLException {
		if (conn == null) {
			throw new SQLException("Null connection");
		}

		if (sql == null) {
			throw new SQLException("Null SQL statement");
		}

		if (rsh == null) {
			throw new SQLException("Null ResultSetHandler");
		}
		PreparedStatement stmt = null;
        ResultSet rs = null;
        T result = null;
		try {
			stmt = conn.prepareStatement(sql);
			this.fillStatement(stmt, params);
			rs = stmt.executeQuery();
			result = rsh.handle(rs);
		} catch (SQLException e) {
			this.rethrow(e, sql, params);
		} finally {
			try {
                DbUtils.close(rs);
            } finally {
            	DbUtils.close(stmt);
            }
		}
		return result;
	}

	/**
插入、修改、删除方法

@param conn
           The connection to use for the update call.
@param sql
           The SQL statement to execute.
@param params
           An array of update replacement parameters. Each row in this
           array is one set of update replacement values.
@return The number of rows updated.
@throws SQLException
            If there are database or parameter errors.
	 */
	public int update(Connection conn, String sql, Object... params)
			throws SQLException {
		if (conn == null) {
			throw new SQLException("Null connection");
		}

		if (sql == null) {
			throw new SQLException("Null SQL statement");
		}

		PreparedStatement stmt = null;
		int rows = 0;

		try {
			stmt = conn.prepareStatement(sql);
			this.fillStatement(stmt, params);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			this.rethrow(e, sql, params);
		} finally {
			DbUtils.close(stmt);
		}

		return rows;
	}

	/**
     * 批量执行插入、修改、删除语句
     * @param conn The connection to use for the batch call.
     * @param sql The SQL statement to execute.
     * @param params An array of query replacement parameters.  Each row in
     * this array is one set of batch replacement values.
     * @return The number of rows updated in the batch.
     * @throws SQLException If there are database or parameter errors.
     */
    public int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            throw new SQLException("Null SQL statement");
        }

        if (params == null) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty array.");
        }

        PreparedStatement stmt = null;
        int[] rows = null;
        try {
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                this.fillStatement(stmt, params[i]);
                stmt.addBatch();
            }
            rows = stmt.executeBatch();

        } catch (SQLException e) {
            this.rethrow(e, sql, (Object[])params);
        } finally {
            DbUtils.close(stmt);
        }
        return rows;
    }

    /**
     * 执行插入语句并返回生成的主键值
     * @param conn The connection to use for the query call.
     * @param sql The SQL statement to execute.
     * @param rsh The handler used to create the result object from
     * the <code>ResultSet</code> of auto-generated keys.
     * @param params The query replacement parameters.
     * @return An object generated by the handler.
     * @throws SQLException If there are database or parameter errors.
     * @since 1.6
     */
    public <T> T insert(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)
            throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            throw new SQLException("Null SQL statement");
        }

        if (rsh == null) {
            throw new SQLException("Null ResultSetHandler");
        }

        PreparedStatement stmt = null;
        T generatedKeys = null;

        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.fillStatement(stmt, params);
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            generatedKeys = rsh.handle(resultSet);
        } catch (SQLException e) {
            this.rethrow(e, sql, params);
        } finally {
            DbUtils.close(stmt);
        }

        return generatedKeys;
    }

    /**
     * 批量执行插入语句并返回生成的主键值
     * @param conn The connection to use for the query call.
     * @param sql The SQL statement to execute.
     * @param rsh The handler used to create the result object from
     * the <code>ResultSet</code> of auto-generated keys.
     * @param params The query replacement parameters.
     * @return The result generated by the handler.
     * @throws SQLException If there are database or parameter errors.
     * @since 1.6
     */
    public <T> T insertBatch(Connection conn, String sql, ResultSetHandler<T> rsh, Object[][] params)
            throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            throw new SQLException("Null SQL statement");
        }

        if (params == null) {
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty array.");
        }

        PreparedStatement stmt = null;
        T generatedKeys = null;
        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < params.length; i++) {
                this.fillStatement(stmt, params[i]);
                stmt.addBatch();
            }
            stmt.executeBatch();
            ResultSet rs = stmt.getGeneratedKeys();
            generatedKeys = rsh.handle(rs);

        } catch (SQLException e) {
            this.rethrow(e, sql, (Object[])params);
        } finally {
            DbUtils.close(stmt);
        }

        return generatedKeys;
    }

	// ---------------------------------

    /**
     * 调用函数或存储过程--待完善
     *
     * 函数sql：{?= call &lt;procedure-name&gt;[(&lt;arg1&gt;,&lt;arg2&gt;, ...)]}
     * 存储过程sql：{call &lt;procedure-name&gt;[(&lt;arg1&gt;,&lt;arg2&gt;, ...)]}
     *
     * 可以通过定义一个Map类型的参数，来表示Out参数
     * Map<String,Object> param1 = new HashMap<String,Object>();
     * param1.put("sqlType",OracleTypes.INTEGER); //sqlType必填项，且为int类型
     * param1.put("rsHandler",new MapListHandler()); //如果sqlType为Cursor等返回ResultSet的参数类型，必须设置RS处理器
     * param1.put("value",null);//非必填，即为Out类型又为In类型时填
     * @param conn
     * @param sql
     * @param params
     * @throws SQLException
     */
//	public static void call(Connection conn, String sql,
//			Object... params) throws SQLException {
//		if (conn == null) {
//			throw new SQLException("Null connection");
//		}
//
//		if (sql == null) {
//			throw new SQLException("Null SQL statement");
//		}
//
//		CallableStatement stmt = null;
//		try {
//			stmt = conn.prepareCall(sql);
//
//			//设置传入参数
//			for (int i = 0; i < params.length; i++) {
//			    if (params[i] instanceof Map) {
//			        Map parm = (Map) params[i];
//			        if(parm.get("sqlType")!=null) {
//                        //设置返回参数
//                        stmt.registerOutParameter(i+1, (Integer) parm.get("sqlType"));
//                        if(parm.get("value")!=null) {
//                            stmt.setObject(i+1, parm.get("value"));
//                        }
//                    }
//                } else {
//                    stmt.setObject(i + 1, params[i]);
//                }
//			}
//			stmt.execute();
//			//获取返回参数
//            for (int i = 0; i < params.length; i++) {
//                if (params[i] instanceof Map) {
//                    Map parm = (Map) params[i];
//                    if(parm.get("sqlType")!=null) {
//                        Object result = stmt.getObject(i+1);
//                        if((Integer)parm.get("sqlType")== OracleTypes.CURSOR) {
//                            ResultSetHandler handler = (ResultSetHandler) parm.get("rsHandler");
//                            ResultSet rs = (ResultSet) result;
//                            result = handler.handle(rs);
//                            DbUtils.close(rs);
//                        }
//                        parm.put("value",result);
//                    }
//                }
//            }
//
//		} finally {
//            DbUtils.close(stmt);
//		}
//	}
}
