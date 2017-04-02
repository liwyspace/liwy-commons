package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 结果集处理器接口
 * @author liwy-psbc
 *
 * @param <T>
 */
public interface ResultSetHandler<T> {
	T handle(ResultSet rs) throws SQLException;
}
