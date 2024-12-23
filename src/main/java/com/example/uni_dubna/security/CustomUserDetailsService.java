package com.example.uni_dubna.security;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ScientificUserRepository scientificUserRepository;

    @Autowired
    public CustomUserDetailsService(ScientificUserRepository scientificUserRepository) {
        this.scientificUserRepository = scientificUserRepository;
    }

    @Override
    @Transactional(readOnly = true) // Обеспечивает открытие сессии Hibernate
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Используем метод с JOIN FETCH для загрузки роли
        ScientificUser scientificUser = scientificUserRepository.findByUsernameWithRole(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                scientificUser.getUsername(),
                scientificUser.getPassword(),
                AuthorityUtils.createAuthorityList(scientificUser.getRole().getName())
        );
    }
}