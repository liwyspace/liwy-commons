package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liwy.commons.dbutils.DbUtils;

public class ArrayListHandler extends AbstractListHandler<Object[]> {

	@Override
	public Object[] handleRow(ResultSet rs) throws SQLException {
		return DbUtils.rs2Array(rs);
	}

}
