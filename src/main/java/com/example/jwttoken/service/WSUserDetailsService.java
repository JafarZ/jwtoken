package com.example.jwttoken.service;

import com.example.jwttoken.model.WSUser;
import com.example.jwttoken.principal.WSUserPrincipal;
import com.example.jwttoken.repository.WSUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WSUserDetailsService implements UserDetailsService {
    @Autowired
    private WSUserRepository wsUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        WSUser wsUser = wsUserRepository.findByUsername(username);
        if (wsUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new WSUserPrincipal(wsUser);
    }
}
