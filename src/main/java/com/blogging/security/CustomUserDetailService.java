package com.blogging.security;

import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.models.User;
import com.blogging.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User" , "UserName" , username));
        
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(user);

        return userDetails;
    }
}
