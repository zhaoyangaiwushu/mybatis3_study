package com.luftraveler.mybatis.mapper.test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luftraveler.mybatis.mapper.bean.Department;
import com.luftraveler.mybatis.mapper.bean.Employee;
import com.luftraveler.mybatis.mapper.dao.DepartmentMapper;
import com.luftraveler.mybatis.mapper.dao.EmployeeMapper;
import com.luftraveler.mybatis.mapper.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

/**
 * 1、接口式编程
 * 原生：		Dao		====>  DaoImpl
 * mybatis：	Mapper	====>  xxMapper.xml
 * <p>
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * （将接口和xml进行绑定）
 * EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * sql映射文件：保存了每一个sql语句的映射信息：
 * 将sql抽取出来。
 *
 * @author lfy
 */
public class MyBatisTest {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 根据id查询信息
     *
     * @throws IOException
     */
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
            Employee employee = mapper.getEmpById(7);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     * Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * sqlSessionFactory.openSession();===》手动提交
     * sqlSessionFactory.openSession(true);===》自动提交
     *
     * @throws IOException
     */
    @Test
    public void test03() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1、获取到的SqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //测试添加
//			Employee employee = new Employee(null, "jerry4",null, "1", LocalDateTime.now());
//			mapper.addEmp(employee);
//			System.out.println(employee.getId());

            //测试修改
//			Employee saveEmployee = new Employee(8, null, "jerry@atguigu.com", "0",null);
//			boolean updateEmp = mapper.updateEmp(saveEmployee);
//			System.out.println(updateEmp);
            //测试删除
            mapper.deleteEmpById(8);
            //2、手动提交数据
            openSession.commit();
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

            //参数方式一:多个参数
            Employee args1 = mapper.getEmpByIdAndLastNameOne(1, "谢召阳");
            System.out.println("args1:" + args1);

            //参数方式二:多个参数
            Employee args2 = mapper.getEmpByIdAndLastName(1, "谢召阳");
            System.out.println("args2:" + args2);

            //参数方式三:map
			Map<String, Object> map = new HashMap<>();
			map.put("id", 2);
			map.put("lastName", "谢召阳");
			map.put("tableName", "tbl_employee");
			Employee args3 = mapper.getEmpByMap(map);
			System.out.println("args3:" + args2);

			List<Employee> like = mapper.getEmpsByLastNameLike("%召%");
			for (Employee employee : like) {
				System.out.println(employee);
			}
            System.out.println("===============================================================");
			Map<String, Object> map1 = mapper.getEmpByIdReturnMap(1);
			System.out.println(map1);

			System.out.println("===============================================================");

            Map<String, Employee> map2 = mapper.getEmpByLastNameLikeReturnMap("%召%");
			System.out.println(map2);

        } finally {
            openSession.close();
        }
    }

    @Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(empById);
            System.out.println("=====================================================");
            List<Employee> empAndDept = mapper.getEmpAndDept();
            for (Employee employee : empAndDept) {
                System.out.println(employee);
            }
            System.out.println("=====================================================");

            System.out.println("=====================================================");
            List<Employee> getEmpAndDeptAssociation = mapper.getEmpAndDeptAssociation();
            for (Employee employee1 : getEmpAndDeptAssociation) {
                System.out.println(employee1);
            }
            System.out.println("=====================================================");
            Employee employee = mapper.getEmpByIdStep(1);
            System.out.println(employee);
            System.out.println(employee.getDept());
        } finally {
            openSession.close();
        }


    }

    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
			/*Department department = mapper.getDeptByIdPlus(1);
			System.out.println(department);
			System.out.println(department.getEmps());*/
            Department deptByIdStep = mapper.getDeptByIdStep(1);
            System.out.println(deptByIdStep.getDepartmentName());
            System.out.println(deptByIdStep.getEmps());
        } finally {
            openSession.close();
        }
    }

}
