package org.sobiech.inspigen.service;

import java.util.List;

import org.sobiech.inspigen.dao.DuplicateRoleException;
import org.sobiech.inspigen.dao.RoleNotFoundException;
import org.sobiech.inspigen.model.Role;

public interface RoleService {

    public void addRole(Role role) throws DuplicateRoleException;

    public Role getRole(int id) throws RoleNotFoundException;
    
    public Role getRole(String rolename) throws RoleNotFoundException;

    public void updateRole(Role role) throws RoleNotFoundException;

    public void deleteRole(int id) throws RoleNotFoundException;

    public List<Role> getRoles();

}
