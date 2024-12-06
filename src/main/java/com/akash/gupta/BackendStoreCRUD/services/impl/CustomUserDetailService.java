package com.akash.gupta.BackendStoreCRUD.services.impl;

import com.akash.gupta.BackendStoreCRUD.entities.User;
import com.akash.gupta.BackendStoreCRUD.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load the user form the Database
        User user = userRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("User with given email id is  not found"));
        return user;


    }
}
//For any Query Contact : akashguptaworks@gmail.com or skyrisegupta@gmail.com