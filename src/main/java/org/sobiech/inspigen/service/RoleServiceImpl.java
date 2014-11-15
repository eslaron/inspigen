package org.sobiech.inspigen.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.RoleDAO;
import org.sobiech.inspigen.dao.UserDAO;
import org.sobiech.inspigen.model.Role;
import org.sobiech.inspigen.model.User;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    
    @Autowired
    private RoleDAO roleDAO;
    
    @Autowired
    Role role;
    
    @Autowired
    private UserDAO userDAO;

	@Override
	public void addRole(User user) {
		role.setUser_role_id(userDAO.getUserByName(user.getUsername()).getId());
    	role.setUsername(user.getUsername());
    	role.setRole("ROLE_USER");
    	
    	roleDAO.addRole(role);
	}

	@Override
	public Role getRoleById(int userId) {
		return roleDAO.getRoleById(userId);
	}

	@Override
	public Role getRoleByName(String username) {
		return roleDAO.getRoleByName(username);
	}

	@Override
	public Role getRoleByEmail(String email) {
		return roleDAO.getRoleByEmail(email);
	}

	@Override
	public void updateRole(Role role) {
		roleDAO.updateRole(role);
	}

	@Override
	public List<Role> getRoles() {
		return roleDAO.getRoles();
	} 
}
