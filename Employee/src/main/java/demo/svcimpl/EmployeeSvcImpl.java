package demo.svcimpl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.adapter.EmployeeAdapter;
import demo.svcint.EmployeeSvcInt;

@RestController
@RequestMapping("/employee")
public class EmployeeSvcImpl implements EmployeeSvcInt {
	@Autowired
	EmployeeAdapter adapter;

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> saveEmployee(@RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

			String employee = params.get("employee");
			
			return adapter.saveEmployee(employee);
	}
	
	@Override
	@RequestMapping(value = "/list", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getEmployeeList(@RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

			return adapter.getEmployeeList();
	}
	
	@Override
	@RequestMapping(value="/delete", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> deleteEmployee(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) {

		String empId = params.get("empId");
		return adapter.deleteEmployee( empId );
	}
	
	//@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> employeeLogin(@RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

			String email = params.get("empEmail");
			String password = params.get("empPassword");
			return adapter.employeeLogin(email,password);
	}

}