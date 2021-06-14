package com.realityflex.mmafisha.config;


import com.realityflex.mmafisha.config.jwt.UserService;
import com.realityflex.mmafisha.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = userService.findByLogin(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(memberEntity);
    }
}
