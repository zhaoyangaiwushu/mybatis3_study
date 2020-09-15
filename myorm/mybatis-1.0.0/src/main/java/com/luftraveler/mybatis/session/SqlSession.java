package com.luftraveler.mybatis.session;
import com.luftraveler.mybatis.config.Configuration;
import com.luftraveler.mybatis.config.MappedStatement;
import com.luftraveler.mybatis.executor.Executor;
import com.luftraveler.mybatis.proxy.MapperProxy;
import java.lang.reflect.Proxy;


public class SqlSession {
    private Executor executor;

    private Configuration configuration;

    public SqlSession(Executor executor,Configuration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    /**
     * 获取mapper接口对象 使用jdk动态代理
     * 有个警告 就是代码黄色了
     * @param interfaces
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> interfaces) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(),
                new Class<?>[]{interfaces},
                mapperProxy);
    }


    /**
     * 查询一条数据
     * @param sqlId
     * @param param
     * @return
     */
    public Object selectOne(String sqlId, Object param) {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(sqlId);
        return executor.query(mappedStatement,param);
    }
}
