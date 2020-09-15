package com.luftraveler.mybatis.mapper.dao;

import java.util.List;
import java.util.Map;

import com.luftraveler.mybatis.mapper.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;


public interface EmployeeMapper {
	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public Employee getEmpById(Integer id);

	public Employee getEmpByIdAndLastNameOne(Integer id,String lastName);

	public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

	public Employee getEmpByMap(Map<String, Object> map);



	//多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
	//@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("id")
	public Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);

	//返回一条记录的map；key就是列名，值就是对应的值
	public Map<String, Object> getEmpByIdReturnMap(Integer id);

	public List<Employee> getEmpsByLastNameLike(String lastName);

	public Long addEmp(Employee employee);

	public boolean updateEmp(Employee employee);

	public void deleteEmpById(Integer id);

}
