package tutorial.daoimpl;

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

import tutorial.adapter.SvcStatus;
import tutorial.daoint.BookDaoInt;
import tutorial.model.Book;
import tutorial.model.BookDtls;

@Repository
public class BookDao implements BookDaoInt {

	@Autowired
	JdbcTemplate template;

	@Override
	public Map<String, Object> insertEmployee(Book model) {
		

			final String SQL =
					"INSERT INTO book("
							+ "name"
							+ ", no_of_pages"
							+ ") VALUES ("
							+ "?,?)";
			int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				count = template.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, model.getbookName());
						ps.setInt(2, model.getpageCount());
						return ps;
					}
				}, holder);
			} catch (Exception e) {
				e.printStackTrace();
				return SvcStatus.GET_FAILURE("Error Saving");
			}

			if (count > 0) {
				Map<String, Object> data = new HashMap<>();
				data.put("BookId", holder.getKey().intValue());
				data.put(SvcStatus.MSG, "Book Inserted");
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				return data;
			} else {
				return SvcStatus.GET_FAILURE("Error Saving Book");
			}

		}
	@Override
	public Map<String, Object> updateEmployee(Book model){
		
		final String SQL =
				"UPDATE book " + " SET"
						+ " name = ?"
						+ ", no_of_pages = ?"
						+ " WHERE bookid = ?";
		
		int count=0;
		try {
			count = template.update(SQL, new Object[] {
					model.getbookName(),
					model.getpageCount(),
					model.getbookId()
			});
		
	} catch(DuplicateKeyException e) {
		return SvcStatus.GET_FAILURE("Duplicate entry found");
	}catch (Exception e) {
		return SvcStatus.GET_FAILURE("Error while updating Book");
	}
			
	if(count>0) {
		Map<String, Object> data =new HashMap<>();
		data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		data.put("BookId",model.getbookId());
		data.put(SvcStatus.MSG, "Book Details updated");
		return data;
	}else {
		return SvcStatus.GET_FAILURE("Error while updating Book");
	}
	}

	@Override
	public Map<String, Object> selectEmployeeList() {
		Map<String, Object> data = new HashMap<>();

		String SQL = 
				" SELECT name as name"
						+ ", no_of_pages as pageCount"
				+" FROM book "
				+" ORDER BY name";

		try {
			List<Book> list = template.query(SQL,
					BeanPropertyRowMapper.newInstance(Book.class));

			if (list.size() == 0)
				return SvcStatus.GET_FAILURE("No List Found");

			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put("lstBook", list);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("No List Found");
		}
	}

	@Override
	public Map<String, Object> deleteEmployee(int employeeId) {
		final String SQL = "DELETE FROM book WHERE book_id = ?" ;

		int count = 0;
		try {
			count = template.update( SQL, new Object[] {employeeId});
		} catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error deleting Book");
		}

		if (count == 1) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG,"Book Deleted");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Error Deleting Book");
		}
	}

	public Map<String, Object> selectEmployeeDtls(String email) {
		Map<String, Object> data = new HashMap<>();

		String SQL = 
				" SELECT password as empPassword"
						+ ", blocked as empBlocked"
				+" FROM book "
				+ " WHERE email = ?";

		BookDtls obj=null;
		try {
			obj = template.queryForObject(SQL,new Object[] {email},
					BeanPropertyRowMapper.newInstance(BookDtls.class));
			
		}catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			return SvcStatus.GET_FAILURE("User Does not exist");
		}
		catch (Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Error getting Book Dtls");
		}
		data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		data.put("empDtls", obj);
		return data;
	}
	

	}

