package com.liwy.commons.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractKeyedHandler<K,V> implements ResultSetHandler<Map<K,V>> {
	@Override
    public Map<K, V> handle(ResultSet rs) throws SQLException {
        Map<K, V> result = new HashMap<K, V>();
        while (rs.next()) {
            result.put(createKey(rs), createRow(rs));
        }
        return result;
    }
	
	protected abstract K createKey(ResultSet rs) throws SQLException;
    protected abstract V createRow(ResultSet rs) throws SQLException;
}
