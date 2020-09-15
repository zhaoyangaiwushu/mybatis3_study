package com.luftraveler.mybatis.session;

import com.luftraveler.mybatis.builder.XMLConfigBuilder;
import com.luftraveler.mybatis.config.Configuration;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) {
        Configuration configuration = new XMLConfigBuilder(inputStream).parse();

        return new SqlSessionFactory(configuration);
    }
}
