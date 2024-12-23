package com.example.uni_dubna.security;

import com.example.uni_dubna.models.ScientificUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final ScientificUser scientificUser;

    public CustomUserDetails(ScientificUser scientificUser) {
        this.scientificUser = scientificUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(scientificUser.getRole().getName()));
    }

    @Override
    public String getPassword() { return this.scientificUser.getPassword(); }

    @Override
    public String getUsername() { return this.scientificUser.getUsername();  }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  return true;   }

    @Override
    public boolean isCredentialsNonExpired() {return true; }

    @Override
    public boolean isEnabled() {  return true;  }

    //ужно чтобы поулчать данные пользователя
    public ScientificUser getScientificUser(){  return  this.scientificUser;  }
}
