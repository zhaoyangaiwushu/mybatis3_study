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
		// 1、获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 2、获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
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
