package demo.daoint;

import java.util.Map;

import demo.model.Employee;

public interface EmployeeDaoInt {

	Map<String, Object> insertEmployee(Employee model);

	Map<String, Object> selectEmployeeList();

	Map<String, Object> deleteEmployee(int employeeId);

	Map<String, Object> updateEmployee(Employee model);


}
