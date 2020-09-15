package com.luftraveler.test;

import com.luftraveler.mybatis.bean.Employee;
import com.luftraveler.mybatis.dao.EmployeeMapper;
import com.luftraveler.mybatis.io.Resources;
import com.luftraveler.mybatis.session.SqlSession;
import com.luftraveler.mybatis.session.SqlSessionFactory;
import com.luftraveler.mybatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * mybatis执行流程
 * 1.调用Resources.getResourceAsStream通过ClassLoaders.getResourceAsStream获取mybatis-config的配置信息
 * 2.把配置文件的流信息传递给SqlSessionFactoryBuilder.build返回一个配置类SqlSessionFactory
 * 2.1 build方法通过调用XMLConfigBuilder.parse方法
 * 2.2.XMLConfigBuilder通过构造器xml文档封装到xPath与document
 * 2.3.
 *
 *
 * 3.mybatis会把接口产生一个代理对象 使用动态代理
 */
public class MybatisTest {
    public static void main(String[] args) {
        //1.读取mybatis-config.xml 配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //2.构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.打开SqlSession
        SqlSession session = sqlSessionFactory.openSession();
        //4.获取Mapper接口对象
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        //5.调用Mapper接口对象的方法操作数据库
        Employee emp = mapper.getEmpById(1);
        System.out.println(emp);
    }
}
