package com.luftraveler.mybatis.mapper.dao;

import com.luftraveler.mybatis.mapper.bean.Employee;

import java.util.List;


public interface EmployeeMapperPlus {

	public Employee getEmpById(Integer id);

	public  List<Employee> getEmpAndDept();

	public  List<Employee> getEmpAndDeptAssociation();

	public Employee getEmpByIdStep(Integer id);

	public List<Employee> getEmpsByDeptId(Integer deptId);

}
