package org.apache.ibatis.test.dao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.test.bean.Employee;

import java.util.Map;

public interface EmployeeMapper {

	public Employee getEmpById(Integer id);

  public Employee getEmpByIdAndLastNameOne(Integer id,String lastName);

  public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

  public Employee getEmpByMap(Map<String, Object> map);

}
