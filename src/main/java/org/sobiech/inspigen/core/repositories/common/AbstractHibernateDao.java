package org.sobiech.inspigen.core.repositories.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHibernateDao< T extends Serializable > {
 
   private Class< T > clazz;
 
   @Autowired
   SessionFactory sessionFactory;
   
   public final void setClazz( Class< T > clazzToSet ){
      this.clazz = clazzToSet;
   }

   @SuppressWarnings("unchecked")
   public T findOneById(long id){
      return (T) getCurrentSession().get( clazz, id );
   }

   @SuppressWarnings("unchecked")
   public List< T > findAll(){
	  return getCurrentSession().createQuery( "from " + clazz.getName() ).list();
   }
   
   public Long entitySize() {  
	   Criteria criteriaCount = getCurrentSession().createCriteria(clazz.getName());
	   criteriaCount.setProjection(Projections.rowCount());
	   Long count = (Long)criteriaCount.uniqueResult();
	   
	   return count;   
   }
   
   public void create( T entity ){
      getCurrentSession().persist( entity );
   }
 
   public void update( T entity ){
      getCurrentSession().update( entity );
   }
 
   public void delete( T entity ){
      getCurrentSession().delete( entity );
   }
   
   public void deleteById( long entityId ){
      T entity = findOneById( entityId );
      delete( entity );
   }
 
   protected final Session getCurrentSession(){
      return sessionFactory.getCurrentSession();
   }
}