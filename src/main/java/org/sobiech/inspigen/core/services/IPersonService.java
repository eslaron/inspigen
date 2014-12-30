package org.sobiech.inspigen.core.services;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.Person;

public interface IPersonService {

	public void createPerson(Person data);
	
	public List<Person> findAllPersons();
	
	public Person findPersonById(long id);
				
	public void updatePerson(Person data);
	
	public void deletePersonById(long id);

}
