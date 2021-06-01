package tutorial.daoint;

import java.util.Map;

import tutorial.model.Book;

public interface BookDaoInt {

	Map<String, Object> insertEmployee(Book model);

	Map<String, Object> selectEmployeeList();

	Map<String, Object> deleteEmployee(int employeeId);

	Map<String, Object> updateEmployee(Book model);

}
