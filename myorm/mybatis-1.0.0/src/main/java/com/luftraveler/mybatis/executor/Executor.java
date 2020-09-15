package com.luftraveler.mybatis.executor;

import com.luftraveler.mybatis.builder.SQLUtils;
import com.luftraveler.mybatis.config.Configuration;
import com.luftraveler.mybatis.config.MappedStatement;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Executor {
    private Configuration configuration;
    private ComboPooledDataSource dataSourcePool;

    public Executor(Configuration configuration) {
        //初始化数据源
        this.configuration = configuration;
        //获取c3p0数据库连接池
        try {
            dataSourcePool = new ComboPooledDataSource();
            dataSourcePool.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSourcePool.setJdbcUrl(configuration.getEnvironment().getUrl());
            dataSourcePool.setUser(configuration.getEnvironment().getUsername());
            dataSourcePool.setPassword(configuration.getEnvironment().getPassword());
            //通过设置相关的参数，对数据库连接池进行管理：
            //设置初始时数据库连接池中的连接数
            dataSourcePool.setInitialPoolSize(10);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public Object query(MappedStatement mappedStatement, Object param) {
        //jdbc
        Class resultType = null;
        try {
            resultType = Class.forName(mappedStatement.getResultType());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return getInstance(resultType,mappedStatement.getSql(),param);
    }

    /**
     *
     * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
     * @author shkstart
     * @date 上午11:42:23
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public <T> T getInstance(Class<T> clazz,String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSourcePool.getConnection();

            //select id,last_name lastName,email,gender from tbl_employee where id = #{id}
            //select id,last_name lastName,email,gender from tbl_employee where id = ?
            ArrayList<String> strings = new ArrayList<>();
            strings.add("id");
            String newSql = SQLUtils.parameQuestion(sql, strings);
            ps = conn.prepareStatement(newSql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[0]);
            }

            rs = ps.executeQuery();
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);

                    // 获取每个列的列名
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                DataSources.destroy( dataSourcePool );

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }
}
