package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liwy.commons.dbutils.DbUtils;

public class ArrayHandler implements ResultSetHandler<Object[]> {

	@Override
	public Object[] handle(ResultSet rs) throws SQLException {
		return rs.next()?DbUtils.rs2Array(rs):null;
	}

}
