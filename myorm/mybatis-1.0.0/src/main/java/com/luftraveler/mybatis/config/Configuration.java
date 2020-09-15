package com.luftraveler.mybatis.config;

import java.util.Map;

public class Configuration {
    //环境对象
    private Environment environment;

    private Map<String,MappedStatement> mappedStatementMap;

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
