package com.example.uni_dubna.config;

import com.example.uni_dubna.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.example.uni_dubna.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import other necessary packages

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; // Note: Deprecated in newer versions
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Публичные маршруты
                .antMatchers("/", "/home", "/public/**", "/auth/**").permitAll()

                // Административные маршруты
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SYS_ADMIN")

                // Маршруты авторов
                .antMatchers("/author/**").hasRole("AUTHOR")

                // Маршруты рецензентов
                .antMatchers("/reviewer/**").hasRole("REVIEWER")

                // Маршруты SYS_ADMIN (если есть дополнительные)
                .antMatchers("/sysadmin/**").hasRole("SYS_ADMIN")

                // Доступ к домашней странице только для аутентифицированных пользователей
                .antMatchers("/home").authenticated()

                // Все остальные запросы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/process_login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/public/home")
                .permitAll()
                .and()
                .csrf()
                .disable(); // Включите CSRF защиту в реальном приложении
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}