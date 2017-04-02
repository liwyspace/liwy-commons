package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnListHandler<T> extends AbstractListHandler<T> {
    private final int columnIndex;
    private final String columnName;

    public ColumnListHandler() {
        this(1, null);
    }

    public ColumnListHandler(int columnIndex) {
        this(columnIndex, null);
    }

    public ColumnListHandler(String columnName) {
        this(1, columnName);
    }

    private ColumnListHandler(int columnIndex, String columnName) {
        super();
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

	@SuppressWarnings("unchecked")
	@Override
	public T handleRow(ResultSet rs) throws SQLException {
		if (this.columnName == null) {
            return (T) rs.getObject(this.columnIndex);
        }
        return (T) rs.getObject(this.columnName);
	}

}
