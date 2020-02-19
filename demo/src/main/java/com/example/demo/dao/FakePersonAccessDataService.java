package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("fakedao")
public class FakePersonAccessDataService implements PersonDao{
	
	private static List<Person> DB = new ArrayList<>();

	@Override
	public int insertPerson(UUID id, Person person) {
		// TODO Auto-generated method stub
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> getAllPeople() {
		// TODO Auto-generated method stub
		return DB;
	}

	@Override
	public int deletePersonByID(UUID id) {
		// TODO Auto-generated method stub
		
		Optional<Person> personMaybe = selectPersonById(id);
		if(personMaybe.isPresent()) {
			DB.remove(personMaybe.get());
			return 1;
		}
		return 0;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		// TODO Auto-generated method stub
		return selectPersonById(id)
				.map(p->{
					int indexOfPersonToDelete = DB.indexOf(p);
					if(indexOfPersonToDelete >= 0) {
						DB.set(indexOfPersonToDelete, new Person(id, person.getName()));
						return 1;
					}
					return 0;
				})
				.orElse(0);
		
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		return  DB.stream()
				.filter(person -> person.getId().equals(id))
				.findFirst();
	}


}
