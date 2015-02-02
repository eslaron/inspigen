package org.sobiech.inspigen.core.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.Person;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IPersonService;

//Klasa implementujaca interfejs IPersonService
@Service
@Transactional
public class PersonServiceImpl implements IPersonService {
	
	IGenericDao<Person> dao;
	
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Person> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Person.class);
	   }
	
	//Implementacja dodawania danych osobowych do tabeli   
	@Override
	public void createPerson(Person data) {
		dao.create(data);
	}
	
	//Implementacja wyszukiwania wszystkich danych osobowych
	@Override
	public List<Person> findAllPersons() {
		return dao.findAll();
	} 

	//Implementacja wyszkiwania danych osobowych po id
	@Override
	public Person findPersonById(long id) {
		return dao.findOneById(id);
	}

	//Implementacja aktualizacji danych osobowych
	@Override
	public void updatePerson(Person data) {
		dao.update(data);
	}

	//Implementacja usuwania danych osobowych po id
	@Override
	public void deletePersonById(long id) {
		dao.deleteById(id);
	}  
}