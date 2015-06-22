package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entity.Address;

//Interfejs zawierajacy prototypy metod operujących na klasie Address
public interface IAddressService {
	
	public void createAddress(Address data);		//Dodanie adresu do tabeli
	
	public List<Address> findAllAddresses();		//Wyszukanie wszystkich adresow w tabeli
	
	public Address findAddressById(long id);		//Wyszukanie adresu po id
		
	public void updateAddress(Address data);		//Aktualizacja adresu
	
	public void deleteAddressById(long id);			//Usuwanie adresu po id
}