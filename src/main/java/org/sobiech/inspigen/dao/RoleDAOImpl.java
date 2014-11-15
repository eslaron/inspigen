package org.sobiech.inspigen.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
    static Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addRole(Role role) {
    	getCurrentSession().save(role);
    }

    @Override
    public Role getRoleById(int userId) {	
    	
    	 Role roleObject = (Role) getCurrentSession().get(Role.class, userId);
         
         if (roleObject == null) {
         	return null;
         } else {
             return roleObject;
         }
    }

	@Override
	public Role getRoleByName(String username) {
		
		Query query = getCurrentSession().createQuery("from Role where username = :userName ");
		query.setString("userName", username);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return (Role)query.list().get(0);
		}
	}

	@Override
	public Role getRoleByEmail(String email) {
		Query query = getCurrentSession().createQuery("from Role where email = :eMail ");
		query.setString("eMail", email);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
	        return (Role)query.list().get(0);
		}
	}

	@Override
	public void updateRole(Role role) {
		Role userRoleToUpdate = getRoleByName(role.getUsername());
		userRoleToUpdate.setUser_role_id(role.getUser_role_id());
		userRoleToUpdate.setUsername(role.getUsername());
		userRoleToUpdate.setRole(role.getRole());
        getCurrentSession().update(userRoleToUpdate);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		return getCurrentSession().createQuery("from Role").list();
	}
}
