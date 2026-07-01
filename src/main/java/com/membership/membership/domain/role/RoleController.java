package com.membership.membership.domain.role;


import com.membership.membership.domain.role.dto.CreateRoleRequest;
import com.membership.membership.domain.role.dto.UpdateRoleRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRole")
    public ResponseEntity<List<Role>> getAllRole(){
        return new ResponseEntity<>(roleService.getAllRole(), HttpStatus.OK);
    }

    @GetMapping("/getRoleById/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id){
        return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
    }

    @PostMapping("/createRole")
    public ResponseEntity<Role> createRole(@Valid @RequestBody CreateRoleRequest request){
        return new ResponseEntity<>(roleService.createRole(request), HttpStatus.CREATED);
    }

    @PostMapping("/updateRole/{id}")
    public ResponseEntity<Role> updateRole(@Valid @PathVariable Long id,  @RequestBody UpdateRoleRequest request){
        return new ResponseEntity<>(roleService.updateRole(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){
        roleService.deleteRoleById(id);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
