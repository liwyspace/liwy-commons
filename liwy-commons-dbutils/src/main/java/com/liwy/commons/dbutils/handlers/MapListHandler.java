package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.liwy.commons.dbutils.DbUtils;

public class MapListHandler extends AbstractListHandler<Map<String,Object>> {

	@Override
	public Map<String,Object> handleRow(ResultSet rs) throws SQLException {
		return DbUtils.rs2Map(rs);
	}

}
