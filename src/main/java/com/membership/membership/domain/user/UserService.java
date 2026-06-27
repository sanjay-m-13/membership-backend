package com.membership.membership.domain.user;

import java.util.List;
import java.util.Objects;

import com.membership.membership.domain.user.dto.UserLoginRequest;
import com.membership.membership.infrastructure.security.jwt.JWTService;
import com.membership.membership.infrastructure.security.jwt.dto.JWTUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.membership.membership.domain.tenant.Tenant;
import com.membership.membership.domain.tenant.TenantService;
import com.membership.membership.domain.user.dto.UpdateUserRequest;
import com.membership.membership.domain.user.dto.UserRequest;
import com.membership.membership.infrastructure.exception.DuplicateResourceException;
import com.membership.membership.infrastructure.exception.ResourceNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(12);

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public User getUserById(Long id){
        return userRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id:  "+ id)
        );
    }

    public User createUser(UserRequest userRequest){

        Tenant tenant = tenantService.getTenantById(userRequest.getTenantId());

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(bCryptEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        user.setStatus(userRequest.getStatus());
        user.setTenant(tenant);
        return userRepo.save(user);
    }

    public User updateUser(Long id, UpdateUserRequest userRequest){
        User user = getUserById(id);

        if (!Objects.equals(user.getEmail(), userRequest.getEmail())
                && userRepo.existsByEmailAndIdNot(userRequest.getEmail(), id)){
            throw new DuplicateResourceException("Email already exists: " + userRequest.getEmail());
        }

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());

        return userRepo.save(user);
    }

    public User updateUserStatus(Long id, String status){
        User user = getUserById(id);

        if (!status.equalsIgnoreCase("ACTIVE") && !status.equalsIgnoreCase("INACTIVE")){
            throw new IllegalArgumentException("Status must be ACTIVE or INACTIVE");
        }

        user.setStatus(status);
        return userRepo.save(user);
    }

    public void deleteUser(Long id){
        getUserById(id);
        userRepo.deleteById(id);
    }

    public String verifyLogin(UserLoginRequest user){
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getEmail());
        }

        return "Failed to login";
    }

}
