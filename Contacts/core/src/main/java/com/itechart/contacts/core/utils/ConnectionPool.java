package com.itechart.contacts.core.utils;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;

/**
 * Created by Admin on 12.09.2018
 */
public class ConnectionPool {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/users_db";

    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "root";

    public ConnectionPool() {
        super();
    }

    @SuppressWarnings("unused")
    public DataSource setUpPool() throws Exception {
        Class.forName(JDBC_DRIVER);
        GenericObjectPool genericObjectPool = new GenericObjectPool();
        genericObjectPool.setMaxActive(5);

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
                                connectionFactory, genericObjectPool, null, null, false, true);
        return new PoolingDataSource(genericObjectPool);
    }
}
