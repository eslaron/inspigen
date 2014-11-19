package org.sobiech.inspigen.core.repositories.common;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable> {
	 
	   T findOneById(final long id);
	   	   
	   List<T> findAll();
	 
	   void create(final T entity);
	 
	   void update(final T entity);
	 
	   void delete(final T entity);
	 
	   void deleteById(final long entityId);

	   void setClazz(Class<T> clazz);
	      
}