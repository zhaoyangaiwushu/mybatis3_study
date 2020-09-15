package com.luftraveler.mybatis.session;

import com.luftraveler.mybatis.config.Configuration;
import com.luftraveler.mybatis.executor.Executor;

public class SqlSessionFactory {
    private Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        //创建一个执行器executor对象
        Executor executor = new Executor(configuration);
        return new SqlSession(executor,configuration);
    }
}
