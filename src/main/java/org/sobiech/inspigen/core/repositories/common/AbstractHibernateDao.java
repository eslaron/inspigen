package org.sobiech.inspigen.core.repositories.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

//Abstrakcyjna klasa zawierajaca metody generyczne wykonujące operacje na encjach bazy danych
public abstract class AbstractHibernateDao< T extends Serializable > {
 
   private Class< T > clazz;
 
   @Autowired
   SessionFactory sessionFactory;
   
   //Ustawienie klasy reprezentujacej encję
   public final void setClazz( Class< T > clazzToSet ){
      this.clazz = clazzToSet;
   }

   //Wyszukiwanie jednego rekordu po ID
   @SuppressWarnings("unchecked")
   public T findOneById(long id){
      return (T) getCurrentSession().get( clazz, id );
   }

   //Wyszukiwanie wszystkich rekordow z encji
   @SuppressWarnings("unchecked")
   public List< T > findAll(){
	  return getCurrentSession().createQuery( "from " + clazz.getName() ).list();
   }
   
   //Ilość rekordow w encji
   public Long entitySize() {  
	   Criteria criteriaCount = getCurrentSession().createCriteria(clazz.getName());
	   criteriaCount.setProjection(Projections.rowCount());
	   Long count = (Long)criteriaCount.uniqueResult();
	   
	   return count;   
   }
   
   //Tworzenie nowego rekordu w tabeli
   public void create( T entity ){
      getCurrentSession().persist( entity );
   }
 
   //Aktualizacja danego rekordu z tabeli
   public void update( T entity ){
      getCurrentSession().update( entity );
   }
 
   //Usuwanie danego rekordu z tabeli
   public void delete( T entity ){
      getCurrentSession().delete( entity );
   }
   
   //Usuwanie rekordu z tabeli po ID
   public void deleteById( long entityId ){
      T entity = findOneById( entityId );
      delete( entity );
   }
 
   //Getter dla sesji bazy danych
   protected final Session getCurrentSession(){
      return sessionFactory.getCurrentSession();
   }
}