package com.luftraveler.mybatis.mapper.dao;


import com.luftraveler.mybatis.mapper.bean.Department;

public interface DepartmentMapper {
	
	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);

	public Department getDeptByIdStep(Integer id);
}
