package demo.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface EmployeeSvcInt {

	Map<String, Object> saveEmployee(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getEmployeeList(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> deleteEmployee(Map<String, String> params, HttpSession session, HttpServletRequest request,
			HttpServletResponse response);

}
