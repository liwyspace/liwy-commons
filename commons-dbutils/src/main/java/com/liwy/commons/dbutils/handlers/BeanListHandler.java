package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liwy.commons.dbutils.DbUtils;

public class BeanListHandler<T> extends AbstractListHandler<T> {
	private final Class<T> type;

	public BeanListHandler(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public T handleRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return DbUtils.rs2Bean(rs, type);
	}

}
