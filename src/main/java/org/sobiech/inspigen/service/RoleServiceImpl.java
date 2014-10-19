package org.sobiech.inspigen.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.RoleDAO;
import org.sobiech.inspigen.model.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    
    @Autowired
    private RoleDAO roleDAO;

	@Override
	public void addUserRole(Role role) {
		roleDAO.addUserRole(role);	
	}

	@Override
	public Role getUserRoleById(int userId) {
		return roleDAO.getUserRoleById(userId);
	}

	@Override
	public Role getUserRoleByName(String username) {
		return roleDAO.getUserRoleByName(username);
	}

	@Override
	public Role getUserRoleByEmail(String email) {
		return roleDAO.getUserRoleByEmail(email);
	}

	@Override
	public void updateUserRole(Role role) {
		roleDAO.updateUserRole(role);
	}

	@Override
	public List<Role> getRoles() {
		return roleDAO.getRoles();
	} 
}
