package com.liwy.commons.dbutils.wrappers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * 动态代理工厂
 * @author liwy-psbc
 *
 */
public class ProxyFactory {
	private static final ProxyFactory instance = new ProxyFactory();
	public static ProxyFactory instance() {
        return instance;
    }
	protected ProxyFactory() {
        super();
    }
	public <T> T newProxyInstance(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[] {type}, handler));
    }
	/**
     * Creates a new proxy <code>CallableStatement</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied CallableStatement
     */
    public CallableStatement createCallableStatement(InvocationHandler handler) {
        return newProxyInstance(CallableStatement.class, handler);
    }

    /**
     * Creates a new proxy <code>Connection</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied Connection
     */
    public Connection createConnection(InvocationHandler handler) {
        return newProxyInstance(Connection.class, handler);
    }

    /**
     * Creates a new proxy <code>Driver</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied Driver
     */
    public Driver createDriver(InvocationHandler handler) {
        return newProxyInstance(Driver.class, handler);
    }

    /**
     * Creates a new proxy <code>PreparedStatement</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied PreparedStatement
     */
    public PreparedStatement createPreparedStatement(InvocationHandler handler) {
        return newProxyInstance(PreparedStatement.class, handler);
    }

    /**
     * Creates a new proxy <code>ResultSet</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied ResultSet
     */
    public ResultSet createResultSet(InvocationHandler handler) {
        return newProxyInstance(ResultSet.class, handler);
    }

    /**
     * Creates a new proxy <code>ResultSetMetaData</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied ResultSetMetaData
     */
    public ResultSetMetaData createResultSetMetaData(InvocationHandler handler) {
        return newProxyInstance(ResultSetMetaData.class, handler);
    }

    /**
     * Creates a new proxy <code>Statement</code> object.
     * @param handler The handler that intercepts/overrides method calls.
     * @return proxied Statement
     */
    public Statement createStatement(InvocationHandler handler) {
        return newProxyInstance(Statement.class, handler);
    }
}
