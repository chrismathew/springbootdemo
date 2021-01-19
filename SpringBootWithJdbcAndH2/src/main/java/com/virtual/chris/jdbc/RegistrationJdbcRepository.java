package com.virtual.chris.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationJdbcRepository  {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class RegistrationRowMapper implements RowMapper<Registration> {
		@Override
		public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Registration registration = new Registration();
			registration.setId(rs.getLong("id"));
			registration.setName(rs.getString("name"));
			registration.setAddress(rs.getString("address"));
		
			return registration;
		}
		
	}
	public List<Registration> findAll() {
		return jdbcTemplate.query("select * from registration", new RegistrationRowMapper());
	}

	public Registration findById(long id) {
		return jdbcTemplate.queryForObject("select * from registration where id=?", new Object[] { id },
				new BeanPropertyRowMapper<Registration>(Registration.class));
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from registration where id=?", new Object[] { id });
	}

	public int insert(Registration registration) {
		return jdbcTemplate.update("insert into registration (id, name, address) " + "values(?,  ?, ?)",
				new Object[] { registration.getId(), registration.getName(), registration.getAddress() });
	}

	public int update(Registration registration) {
		return jdbcTemplate.update("update registration " + " set name = ?, address = ? " + " where id = ?",
				new Object[] { registration.getName(), registration.getAddress(), registration.getId() });
	}

}
