package tutorial.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import tutorial.daoimpl.BookDao;
import tutorial.model.Book;
import tutorial.model.BookDtls;

@Component
public class BookAdapter {
	@Autowired
	BookDao dao;

	public Map<String, Object> saveEmployee(String employee) {
		Book model = new Book();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(employee, Book.class);
		} catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception Occured in reading Employee data");
		}
		
		return  model.getbookId()==0? dao.insertEmployee(model): dao.updateEmployee(model) ;

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
		BookDtls empDtls = (BookDtls)map.get("empDtls");
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
