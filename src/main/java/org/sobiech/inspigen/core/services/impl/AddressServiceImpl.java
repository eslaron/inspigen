package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.Address;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IAddressService;

//Klasa implementujaca interfejs IAddressService
@Service
@Transactional
public class AddressServiceImpl implements IAddressService {
	
	IGenericDao<Address> dao;
	 
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Address> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Address.class);
	   }

	//Implementacja dodawania adresu do tabeli   
	@Override
	public void createAddress(Address data) {
		dao.create(data);
	}

	//Implementacja wyszukiwania wszystkich adresow w tabeli
	@Override
	public List<Address> findAllAddresses() {
		return dao.findAll();
	}

	//Implementacja wyszukiwania adresu po id
	@Override
	public Address findAddressById(long id) {
		return dao.findOneById(id);
	}

	//Implementacja aktualizacji adresu
	@Override
	public void updateAddress(Address data) {
		dao.update(data);
	}

	//Implementacja usuwania adresu po id
	@Override
	public void deleteAddressById(long id) {
		dao.deleteById(id);
	}	
}