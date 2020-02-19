package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;

@Service
public class PersonService {
	private final PersonDao personDao;

	@Autowired
	public PersonService(@Qualifier("postgres")PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public int addPerson(Person person) {
		return this.personDao.insertPerson(person);
	}

	public List<Person> getAllPeople(){
		return this.personDao.getAllPeople();
	}
	
	public Optional<Person> getPersonById(UUID id){
		return this.personDao.selectPersonById(id);
	}
	
	public int deletePersonByID(UUID id) {
		return this.personDao.deletePersonByID(id);
	}
	
	public int updatePersonById(UUID id, Person person) {
		return this.personDao.updatePersonById(id, person);
	}
}
