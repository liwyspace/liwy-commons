package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 获取结果集中第一行的某一列的值
 * @author liwy-psbc
 *
 * @param <T>
 */
public class ColumnHandler<T> implements ResultSetHandler<T> {
    private final int columnIndex;
    private final String columnName;

    public ColumnHandler() {
        this(1, null);
    }

    public ColumnHandler(int columnIndex) {
        this(columnIndex, null);
    }

    public ColumnHandler(String columnName) {
        this(1, columnName);
    }

    private ColumnHandler(int columnIndex, String columnName) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

	@SuppressWarnings("unchecked")
	@Override
	public T handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
            if (this.columnName == null) {
                return (T) rs.getObject(this.columnIndex);
            }
            return (T) rs.getObject(this.columnName);
        }
		return null;
	}

}
