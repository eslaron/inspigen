package org.sobiech.inspigen.core.repositories.common;

import java.io.Serializable;
import java.util.List;

//Interfejs zawierajacy prototypy metod operujących na bazie
public interface IGenericDao<T extends Serializable> {
	 
	   T findOneById(final long id);			//Wyszukiwanie jednego rekordu po ID
	   	   
	   List<T> findAll();						//Wyszukiwanie wszystkich rekordow z encji
	   	    
	   Long entitySize();						//Ilość rekordow w encji
	 
	   void create(final T entity);				//Tworzenie nowego rekordu w tabeli
	 
	   void update(final T entity);				//Aktualizacja danego rekordu z tabeli	
	 
	   void delete(final T entity);				//Usuwanie danego rekordu z tabeli
	 
	   void deleteById(final long entityId);	//Usuwanie rekordu z tabeli po ID

	   void setClazz(Class<T> clazz);			//Ustawienie klasy reprezentujacej encję	      
}