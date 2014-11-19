package org.sobiech.inspigen.core.repositories.common;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class GenericHibernateDao< T extends Serializable > extends AbstractHibernateDao< T > implements IGenericDao< T >{

}