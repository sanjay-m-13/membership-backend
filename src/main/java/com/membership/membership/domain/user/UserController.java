package com.membership.membership.domain.user;

import java.util.List;

import com.membership.membership.domain.user.dto.*;
import com.membership.membership.infrastructure.security.jwt.dto.JWTUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.membership.membership.domain.tenant.dto.TenantMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser().stream()
                .map(TenantMapper::toUserResponse)
                .collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(TenantMapper.toUserResponse(userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user){
        return new ResponseEntity<>(TenantMapper.toUserResponse(userService.createUser(user)),HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest){
        return new ResponseEntity<>(TenantMapper.toUserResponse(userService.updateUser(id, updateUserRequest)), HttpStatus.OK);
    }

    @PutMapping("/updateUserStatus/{id}")
    public ResponseEntity<String> updateUserStatus(@PathVariable Long id, @Valid @RequestBody UserStatusRequest statusRequest){
        userService.updateUserStatus(id,statusRequest.getStatus());
        return new ResponseEntity<>("Status updated", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequest user){
        return new ResponseEntity<>(userService.verifyLogin(user), HttpStatus.OK);
    }

}
