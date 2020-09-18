package org.apache.ibatis.test.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.test.bean.Employee;
import org.apache.ibatis.test.dao.EmployeeMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
public class MyBatisTest {
	public SqlSessionFactory getSqlSessionFactory() throws IOException {

		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	@Test
	public void test01() throws IOException {
    String resource = "mybatis-config.xml";
    //1.通过classLoad类获取配置文件获取流信息。
    InputStream inputStream = Resources.getResourceAsStream(resource);
    //2.根据流信息创建SQLSessionFactory。
    //2.1.mybatis底层通过JDK的xPath以及w3c的document来解析xml。
    //2.2.首先解析mybatis-config下的configuration节点。
    //2.3.获取全局设置settings，数据库连接池信息，缓存等等一大堆 解析之后存储到configuration对应属性中。
    //2.4.解析mappers节点。
    //2.5.通过mappers配置中的mapper节点或者package节点得值获取mapper.xml的具体位置。
    //2.6.我一般用批量注册package 的name
    //2.7.解析所有mapper.xml并分别获取把你namespace,自定义返回值,公共sql,什么一些公共配置等等一些。
    //2.8.然后在解析你每一个mapper.xml中的select|insert|update|delete标签。
    //2.9.并把标签的属性resultType,resultMap,id,paramType,sql语句啊等等一些全部解析出来。
    //3.0.把mapper.xml解析之后通过一个addMappedStatement封装成MappedStatement。
    //3.1.然后把你MappedStatement封装成一个map
    //3.2.key就是你mapper的namespace+每个标签的id用.拼接。
    //3.3.value就是你的MappedStatement 一个MappedStatement代表一个增删改查标签
    //3.4.最后返回DefaultSqlSessionFactory
    SqlSessionFactory sqlSessionFactory =  new SqlSessionFactoryBuilder().build(inputStream);
		// 4、获取sqlSession对象
    //4.1.根据你这个配置信息创建数据库连接池。
    // 4.2.然后创建Executor对象 (根据全局配置文件中的defaultExecutorType创建出对应的Executor) 然后使用拦截器给你包装一下。
    //4.2.1.Executor对象会创建一个StatementHandler对象（同时也会创建出ParameterHandler和ResultSetHandler）
    //4.2.2.调用StatementHandler预编译参数以及设置参数值
    //4.2.3.使用ParameterHandler来给sql设置参数
    //4.2.4.调用StatementHandler的增删改查方法
    //4.2.5.ResultSetHandler封装结果
    //4.2.6.上述四个对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
    //4.3.返回DefaultSqlSession 里面包含Executor。
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			// 5、mybatis 通过动态代理的方式帮你生成一个代理对象 拿到Mapper接口对应的MapperProxy
      // 5.1.Proxy.newProxyInstance()
      //5.2.1. 类加载器(Class Loader)
      //5.2.2. 需要实现的接口数组
      //5.2.3. 实现了InvocationHandler接口的方法。重写invoke()方法里面就能获取传递的.class的参数什么什么等等一大堆。
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//6.MapperProxy里面有（DefaultSqlSession）
			//6.1.根据类路径和方法名找到MappedStatement
      //6.2.取出里面的sql语句什么返回值之类的 解析你sql语句吧你#{}换成?具体咋写没研究太多了。
      //6.3.然后就是jdbc那一套了PreparedStatement往里封装参数呗。
      //6.4.通过反射机制给你返回值里面扔值。
			Employee employee = mapper.getEmpById(1);
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}

  @Test
  public void test02() throws IOException {

    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    //1、获取到的SqlSession不会自动提交数据
    SqlSession openSession = sqlSessionFactory.openSession();

    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

      //参数方式一:多个参数
      Employee args1 = mapper.getEmpByIdAndLastNameOne(1, "谢召阳");
      System.out.println("args1:" + args1);
    } finally {
      openSession.close();
    }
  }

  @Test
  public void test03() throws IOException {

    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    //1、获取到的SqlSession不会自动提交数据
    SqlSession openSession = sqlSessionFactory.openSession();

    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);


      //参数方式二:多个参数
      Employee args2 = mapper.getEmpByIdAndLastName(1, "谢召阳");
      System.out.println("args2:" + args2);

    } finally {
      openSession.close();
    }
  }

  @Test
  public void test04() throws IOException {

    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    //1、获取到的SqlSession不会自动提交数据
    SqlSession openSession = sqlSessionFactory.openSession();

    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      //参数方式三:map
      Map<String, Object> map = new HashMap<>();
      map.put("id", 2);
      map.put("lastName", "谢召阳");
      map.put("tableName", "tbl_employee");
      Employee args3 = mapper.getEmpByMap(map);
      System.out.println("args3:" + args3);
    } finally {
      openSession.close();
    }
  }

}
