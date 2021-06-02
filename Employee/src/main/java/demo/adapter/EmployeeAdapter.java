package demo.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.daoimpl.EmployeeDao;
import demo.daoint.EmployeeDaoInt;
import demo.model.Employee;
import demo.model.EmployeeDtls;

@Component
public class EmployeeAdapter {
	@Autowired
	EmployeeDao dao;

	public Map<String, Object> saveEmployee(String employee) {
		Employee model = new Employee();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(employee, Employee.class);
		} catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception Occured in reading Employee data");
		}
		
		return   model.getEmployee_id()==0 ? dao.insertEmployee(model) : dao.updateEmployee(model);

	}
    
	public Map<String, Object> getEmployeeList() {
		return dao.selectEmployeeList();
	}

	public Map<String, Object> deleteEmployee(String empId) {
		int employeeId = Integer.parseInt(empId);
		return dao.deleteEmployee( employeeId );
	}

	public Map<String, Object> employeeLogin(String email, String password) {
		Map<String, Object> map = new HashMap<>();
		map = dao.selectEmployeeDtls(email);
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE)) {
			return map;
		}
		EmployeeDtls empDtls = (EmployeeDtls)map.get("empDtls");
		if(!(empDtls.getEmpPassword().equals(password))) {
			return SvcStatus.GET_FAILURE("Password does not match");
		}
		if(empDtls.isEmpBlocked()) {
			return SvcStatus.GET_FAILURE("User is blocked");
		}
		map.clear();
		map.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		map.put(SvcStatus.MSG,"Login Successful");
		return map;
	}

}
