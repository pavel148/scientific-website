package com.example.uni_dubna.security;

import com.example.uni_dubna.models.Role;
import com.example.uni_dubna.models.ScientificUser;
import com.example.uni_dubna.repo.ScientificUserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final ScientificUserRepository scientificUserRepository;

    public CustomUserDetailsService(ScientificUserRepository scientificUserRepository) {
        this.scientificUserRepository = scientificUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получаем пользователя из базы данных по имени
        ScientificUser scientificUser = scientificUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                scientificUser.getUsername(),
                scientificUser.getPassword(),
                AuthorityUtils.createAuthorityList(scientificUser.getRole().getName()) // Теперь одна роль
        );
    }
}