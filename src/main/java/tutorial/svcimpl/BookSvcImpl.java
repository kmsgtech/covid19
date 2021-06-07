package tutorial.svcimpl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tutorial.adapter.BookAdapter;
import tutorial.svcint.BookSvcInt;

@RestController
@RequestMapping("/book")
public class BookSvcImpl implements BookSvcInt {
	@Autowired
	BookAdapter adapter;

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> saveEmployee(@RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

			String book = params.get("book");
			
			return adapter.saveEmployee(book);
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

		String bookId = params.get("bookId");
		return adapter.deleteEmployee( bookId );
	}
	
	//@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> employeeLogin(@RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

			String email = params.get("email");
			String password = params.get("password");
			return adapter.employeeLogin(email,password);
	}

}