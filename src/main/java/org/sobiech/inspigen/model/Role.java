package org.sobiech.inspigen.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "ig_user_roles")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6874667425302308430L;
    static Logger logger = LoggerFactory.getLogger(Role.class);

    @Column(name = "user_role_id")
    private int user_role_id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "role")
    private String role;

    //@OneToMany(cascade = CascadeType.ALL)  
    /*@OneToMany  
    @JoinTable(name = "ig_user_roles",   
        joinColumns        = {@JoinColumn(name = "role_id", referencedColumnName = "id")},  
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}  
    )  */
    
    public int getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
