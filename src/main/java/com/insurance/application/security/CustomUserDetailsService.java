package com.insurance.application.security;

import com.insurance.application.models.UserInfo;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserInfoService userInfoService;

    @Autowired
    public CustomUserDetailsService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserInfo user = userInfoService.getByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("No user found with this email");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, getAuthorities(user.getUserRole().getAuthority()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String userRole) {
        return Arrays.asList(new SimpleGrantedAuthority(userRole));
    }
}
