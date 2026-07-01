package com.membership.membership.domain.role;

import com.membership.membership.domain.role.dto.CreateRoleRequest;
import com.membership.membership.domain.role.dto.UpdateRoleRequest;
import com.membership.membership.infrastructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public List<Role> getAllRole(){
        return roleRepo.findAll();
    }

    public Role getRoleById(Long id){
        return roleRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Role not found with id: " + id)
        );
    }

    public Role createRole(CreateRoleRequest roleRequest){

        Role role = new Role();
        role.setRole(roleRequest.getRole());
        role.setDescription(roleRequest.getDescription());
        return roleRepo.save(role);
    }

    public Role updateRole(Long id, UpdateRoleRequest role){
        Role role1 = getRoleById(id);
        role1.setRole(role.getRole());
        return roleRepo.save(role1);
    }

    public void deleteRoleById(Long id){
        getRoleById(id);
        roleRepo.deleteById(id);
    }

}
