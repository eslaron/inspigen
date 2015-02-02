package org.sobiech.inspigen.core.services;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.Person;

//Interfejs zawierajacy prototypy metod operujących na klasie Person
public interface IPersonService {

	public void createPerson(Person data);			//Dodawanie danych osobowych do tabeli
	
	public List<Person> findAllPersons();			//Wyszukiwanie wszystkich danych osobowych
	
	public Person findPersonById(long id);			//Wyszukiwanie danych osobowych po id
				
	public void updatePerson(Person data);			//Aktualizacja danych osobowych
	
	public void deletePersonById(long id);			//Usuwanie danych osobowych po id

}
