package org.sobiech.inspigen.core.repositories;

import org.sobiech.inspigen.core.models.entities.Person;

public interface IPersonDao {
	
	public Person findPersonByUserId(long id);
	
}