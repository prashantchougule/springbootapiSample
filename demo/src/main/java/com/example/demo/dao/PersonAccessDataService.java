package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("postgres")
public class PersonAccessDataService implements PersonDao{

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PersonAccessDataService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPerson(UUID id, Person person) {
		final String sql = "INSERT INTO person(id, name) VALUES (?, ?)";
		Object[] params = new Object[] { id, person.getName() };
		jdbcTemplate.update(sql,params);
		return 0;
	}

	@Override
	public List<Person> getAllPeople() {

		final String sql = "SELECT id, name FROM person";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Person(UUID.fromString(resultSet.getString("id")),
					resultSet.getString("name")
			);
		});

	}

	@Override
	public int deletePersonByID(UUID id) {
		final String sql = "DELETE FROM person WHERE id = ?";
		Object[] params = new Object[] { id };
		return jdbcTemplate.update(sql,params);

	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		final String sql = "UPDATE person SET name = ? WHERE id = ?";
		Object[] params = new Object[] { person.getName(), id };
		return jdbcTemplate.update(sql,params);
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		final String sql = "SELECT id, name FROM person WHERE id = ?";
		Person person = jdbcTemplate.queryForObject(sql,
				new Object[]{id},
				(resultSet, i) -> {
			return new Person(UUID.fromString(resultSet.getString("id")),
					resultSet.getString("name")
			);
		});
		return Optional.ofNullable(person);
	}

}
