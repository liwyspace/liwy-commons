package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liwy.commons.dbutils.DbUtils;

public class BeanHandler<T> implements ResultSetHandler<T> {
	private final Class<T> type;
	

	public BeanHandler(Class<T> type) {
		super();
		this.type = type;
	}


	@Override
	public T handle(ResultSet rs) throws SQLException {
		
		return rs.next()?DbUtils.rs2Bean(rs, this.type):null;
	}

}
