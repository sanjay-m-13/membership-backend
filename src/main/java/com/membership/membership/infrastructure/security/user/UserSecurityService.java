package com.membership.membership.infrastructure.security.user;

import com.membership.membership.domain.user.User;
import com.membership.membership.domain.user.UserRepo;
import com.membership.membership.infrastructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(()->
                new ResourceNotFoundException("User not found with :" + username));


        return new UserPrincipal(user);
    }
}
