package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liwy.commons.dbutils.DbUtils;

public class BeanKeyedHandler<K, V> extends AbstractKeyedHandler<K, V> {
	private final Class<V> type;
	private final int columnIndex;
	private final String columnName;

	public BeanKeyedHandler(Class<V> type) {
		this(type, 1, null);
	}

	public BeanKeyedHandler(Class<V> type, int columnIndex) {
		this(type, columnIndex, null);
	}

	public BeanKeyedHandler(Class<V> type, String columnName) {
		this(type, 1, columnName);
	}

	private BeanKeyedHandler(Class<V> type, int columnIndex, String columnName) {
		super();
		this.type = type;
		this.columnIndex = columnIndex;
		this.columnName = columnName;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected K createKey(ResultSet rs) throws SQLException {
		return (columnName == null) ? (K) rs.getObject(columnIndex) : (K) rs
				.getObject(columnName);
	}

	@Override
	protected V createRow(ResultSet rs) throws SQLException {
		return DbUtils.rs2Bean(rs, type);
	}
}
