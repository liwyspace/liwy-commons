package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListHandler<T> implements ResultSetHandler<List<T>> {
	public List<T> handle(ResultSet rs) throws SQLException {
		List<T> rows = new ArrayList<T>(); 
		while(rs.next()) {
			rows.add(this.handleRow(rs));
		}
		return rows;
	}
	
	public abstract T handleRow(ResultSet rs) throws SQLException;
}
