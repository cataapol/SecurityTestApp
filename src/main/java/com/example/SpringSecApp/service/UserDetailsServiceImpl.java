package com.example.SpringSecApp.service;

import com.example.SpringSecApp.persistence.entity.User;
import com.example.SpringSecApp.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findUserByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException("The user does not exist.." + username));

        Set<SimpleGrantedAuthority> grantList = new HashSet<SimpleGrantedAuthority>(); //Crea lista de roles/accesos que tiene el usuario


        appUser.getRoles().forEach(role -> grantList.add(new SimpleGrantedAuthority(role.getRoleNumber().name())));

        appUser.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> grantList.add(new SimpleGrantedAuthority(permission.getName())));



        return new org.springframework.security.core.userdetails.User(appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                appUser.isAccountNoExpired(),
                appUser.isCredentialNoExpired(),
                appUser.isAccountNoLocked(),
                grantList);

    }
}
