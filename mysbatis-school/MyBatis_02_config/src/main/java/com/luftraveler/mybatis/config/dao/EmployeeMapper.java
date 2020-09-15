package com.luftraveler.mybatis.config.dao;


import com.luftraveler.mybatis.config.bean.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);

	public Long addEmp(Employee employee);

}
