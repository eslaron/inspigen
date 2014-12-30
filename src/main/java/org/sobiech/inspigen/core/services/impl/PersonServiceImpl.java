package org.sobiech.inspigen.core.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.Person;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IPersonService;

@Service
@Transactional
public class PersonServiceImpl implements IPersonService {
	
	IGenericDao<Person> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Person> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Person.class);
	   }
		   
	@Override
	public void createPerson(Person data) {
		dao.create(data);
	}
	
	@Override
	public List<Person> findAllPersons() {
		return dao.findAll();
	} 

	@Override
	public Person findPersonById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updatePerson(Person data) {
		dao.update(data);
	}

	@Override
	public void deletePersonById(long id) {
		dao.deleteById(id);
	}  
}