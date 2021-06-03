package demo.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import demo.adapter.SvcStatus;
import demo.daoint.EmployeeDaoInt;
import demo.model.Employee;
import demo.model.EmployeeDtls;
import java.util.*
;@Repository
public class EmployeeDao implements EmployeeDaoInt {

	@Autowired
	JdbcTemplate template;

	@Override
	public Map<String, Object> insertEmployee(Employee model) {
		

			final String SQL =
					"INSERT INTO employee("
							+ "name"
							+ ", age"
							+ ", city"
							+ ") VALUES ("
							+ "?,?,?)";
			int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				count = template.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, model.getName());
						ps.setInt(2, model.getAge());
						ps.setString(3, model.getCity());
						return ps;
					}
				}, holder);
			} catch (Exception e) {
				e.printStackTrace();
				return SvcStatus.GET_FAILURE("Error Saving");
			}

			if (count > 0) {
				Map<String, Object> data = new HashMap<>();
				data.put("employeeId", holder.getKey().intValue());
				data.put(SvcStatus.MSG, "Employee Inserted");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				return data;
			} else {
				return SvcStatus.GET_FAILURE("Error Saving employee");
			}

		}

	@Override
	public Map<String, Object> selectEmployeeList() {
		Map<String, Object> data = new HashMap<>();

		String SQL = 
				" SELECT name as name"
						+ ", age as age"
						+ ", city as city"
				+" FROM employee "
				+" ORDER BY name";

		try {
			List<Employee> list = template.query(SQL,
					BeanPropertyRowMapper.newInstance(Employee.class));

			if (list.size() == 0)
				return SvcStatus.GET_FAILURE("No List Found");

			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put("lstEmployee", list);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("No List Found");
		}
	}

	@Override
	public Map<String, Object> deleteEmployee(int employeeId) {
		final String SQL = "DELETE FROM employee WHERE employee_id = ?" ;

		int count = 0;
		try {
			count = template.update( SQL, new Object[] {employeeId});
		} catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error deleting employee");
		}

		if (count == 1) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG,"Employee Deleted");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Error Deleting Employee");
		}
	}

	public Map<String, Object> selectEmployeeDtls(String email) {
		Map<String, Object> data = new HashMap<>();

		String SQL = 
				" SELECT password as empPassword"
						+ ", blocked as empBlocked"
				+" FROM employee "
				+ " WHERE email = ?";

		EmployeeDtls obj=null;
		try {
			obj = template.queryForObject(SQL,new Object[] {email},
					BeanPropertyRowMapper.newInstance(EmployeeDtls.class));
			
		}catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			return SvcStatus.GET_FAILURE("User Does not exist");
		}
		catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error getting employee Dtls");
		}
		data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		data.put("empDtls", obj);
		return data;
	}
	@Override
	public Map<String, Object> updateEmployee (Employee model) {
		
		    int count=0;
			final String SQL =
					"UPDATE employee "+" SET"
							+ " name = ?"
							+ ", age = ?"
							+ ", city = ?"
							+ " WHERE employee_id = ?";
			try {
				count= template.update(SQL,new Object[]{
						model.getName(),
						model.getAge(),
						model.getCity(),
						model.getEmployee_id()});
			}
			catch(DuplicateKeyException e) {
				return SvcStatus.GET_FAILURE("Duplicate entry erros");
			}
			catch(Exception e)
			{
				return SvcStatus.GET_FAILURE("Error while updating employee");
			}

			if (count > 0) {
				Map<String, Object> data = new HashMap<>();
				data.put(SvcStatus.STATUS,SvcStatus.SUCCESS);
				data.put("employeeId", model.getEmployee_id());
				data.put(SvcStatus.MSG, "Employee Updated");
				return data;
			} else {
				return SvcStatus.GET_FAILURE("Error while Updating employee");
			}

		}

	}

