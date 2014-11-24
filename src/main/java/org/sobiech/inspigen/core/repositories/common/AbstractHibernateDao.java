package org.sobiech.inspigen.core.repositories.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
   
   @SuppressWarnings("unchecked")
   public List< T > findFirstPage(int size){
	   Query query = getCurrentSession().createQuery( "from " + clazz.getName());
	   query.setFirstResult(0);
	   query.setMaxResults(size);
	   
	   return query.list(); 
   }
   
   @SuppressWarnings("unchecked")
   public List< T > findPage(int page, int size){
	   int offset = (page-1) * size;

	   Query query = getCurrentSession().createQuery( "from " + clazz.getName());
	   query.setFirstResult(offset);
	   query.setMaxResults(size);
	   
	   return query.list(); 
   }
    
   @SuppressWarnings("unchecked")
   public List< T > findLastPage(int size) {  
	   Criteria criteriaCount = getCurrentSession().createCriteria(clazz.getName());
	   criteriaCount.setProjection(Projections.rowCount());
	   Long count = (Long) criteriaCount.uniqueResult();
	   
	   int lastPageNumber = (int)((count / size) + 1);
	   
	   Query query = getCurrentSession().createQuery("from " + clazz.getName());
	   query.setFirstResult((lastPageNumber - 1) * size);
	   query.setMaxResults(size);
	    
	   return query.list();   
   }
 
   @SuppressWarnings("unchecked")
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