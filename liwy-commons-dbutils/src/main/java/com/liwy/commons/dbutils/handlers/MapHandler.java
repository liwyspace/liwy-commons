package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.liwy.commons.dbutils.DbUtils;

public class MapHandler implements ResultSetHandler<Map<String,Object>> {
	
	@Override
	public Map<String,Object> handle(ResultSet rs) throws SQLException {
		return rs.next() ? DbUtils.rs2Map(rs) : null;
	}

}
