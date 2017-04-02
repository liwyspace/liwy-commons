package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.liwy.commons.dbutils.DbUtils;

public class MapKeyedHandler<K> extends AbstractKeyedHandler<K, Map<String,Object>> {
	protected final int columnIndex;
	protected final String columnName;

	public MapKeyedHandler() {
		this(1, null);
	}

	public MapKeyedHandler(int columnIndex) {
		this(columnIndex, null);
	}

	public MapKeyedHandler(String columnName) {
		this(1, columnName);
	}

	private MapKeyedHandler(int columnIndex, String columnName) {
		super();
		this.columnIndex = columnIndex;
		this.columnName = columnName;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected K createKey(ResultSet rs) throws SQLException {
		return (columnName == null) ?
	               (K) rs.getObject(columnIndex) :
	               (K) rs.getObject(columnName);
	}

	@Override
	protected Map<String,Object> createRow(ResultSet rs) throws SQLException {
		return DbUtils.rs2Map(rs);
	}

}
