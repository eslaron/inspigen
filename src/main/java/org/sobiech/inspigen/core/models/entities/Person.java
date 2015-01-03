package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity  
@Table(name="ig_persons")
public class Person extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8562484446335390991L;
	static Logger logger = LoggerFactory.getLogger(Person.class);
    
	@Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Column(name = "pesel")
    private String pesel;
    
    @Column(name = "phoneNumber")
    private String phoneNumber;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "attachment_id")
    private int attachmentId;
    
	public Person() {}
    
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

    public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
}