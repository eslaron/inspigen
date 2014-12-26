package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.School;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.ISchoolService;

@Service
@Transactional
public class SchoolServiceImpl implements ISchoolService {
	
	IGenericDao<School> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<School> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(School.class);
	   }

	@Override
	public void createSchool(School data) {
		dao.create(data);
	}

	@Override
	public List<School> findAllSchools() {
		return dao.findAll();
	}

	@Override
	public School findSchoolById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updateSchool(School data) {
		dao.update(data);
	}

	@Override
	public void deleteSchoolById(long id) {
		dao.deleteById(id);
	}	
}