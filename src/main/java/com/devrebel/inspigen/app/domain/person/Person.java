package com.devrebel.inspigen.app.domain.person;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.devrebel.inspigen.app.domain.common.BaseEntity;

@Entity  															//oznaczenie klasy jako encji
@Table(name="ig_persons")											//nazwa tabeli w bazie danych
public class Person extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8562484446335390991L;
 
    private String firstName;       //imię
    
    private String lastName;		//nazwisko
    
    private String pesel;			//PESEL
    
    private String phoneNumber;		//numer telefonu
    
    private int user_id;			//id użytkownika, do ktorego należą dane osobowe

	public Person() {}
    
	//Gettery i settery
	
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
   
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}