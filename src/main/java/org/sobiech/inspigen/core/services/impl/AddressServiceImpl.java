package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.Address;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IAddressService;


@Service
@Transactional
public class AddressServiceImpl implements IAddressService {
	
	IGenericDao<Address> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Address> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Address.class);
	   }

	@Override
	public void createAddress(Address data) {
		dao.create(data);
	}

	@Override
	public List<Address> findAllAddresses() {
		return dao.findAll();
	}

	@Override
	public Address findAddressById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updateAddress(Address data) {
		dao.update(data);
	}

	@Override
	public void deleteAddressById(long id) {
		dao.deleteById(id);
	}	
}