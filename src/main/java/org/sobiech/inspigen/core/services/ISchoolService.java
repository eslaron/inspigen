package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.School;


public interface ISchoolService {
	
	public void createSchool(School data);
	
	public List<School> findAllSchools();
	
	public School findSchoolById(long id);
		
	public void updateSchool(School data);
	
	public void deleteSchoolById(long id);	
}