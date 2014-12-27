package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Address;

public interface IAddressService {
	
	public void createAddress(Address data);
	
	public List<Address> findAllAddresses();
	
	public Address findAddressById(long id);
		
	public void updateAddress(Address data);
	
	public void deleteAddressById(long id);	
}