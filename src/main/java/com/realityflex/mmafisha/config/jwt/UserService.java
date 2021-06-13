package com.realityflex.mmafisha.config.jwt;


import com.realityflex.mmafisha.entity.Role;
import com.realityflex.mmafisha.entity.Member;
import com.realityflex.mmafisha.repository.RoleRepository;
import com.realityflex.mmafisha.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private MemberRepository userEntityRepository;
    @Autowired
    private RoleRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member saveUser(Member member) {
        Role userRole = roleEntityRepository.findByName("ROLE_USER");
        member.setRole(userRole);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return userEntityRepository.save(member);
    }

    public Member findByLogin(String login) {
        return userEntityRepository.findByMemberName(login);
    }

    public Member findByLoginAndPassword(String login, String password) {
        Member memberEntity = findByLogin(login);
        if (memberEntity != null) {
            if (passwordEncoder.matches(password, memberEntity.getPassword())) {
                return memberEntity;
            }
        }
        return null;
    }
}