package com.springmvc.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

public class CustomUserDetailService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if("Admin".equals(username)) {
      return new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
          return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        @Override
        public String getPassword() {
          return "Admin1234";
        }

        @Override
        public String getUsername() {
          return "Admin";
        }
      };
    }
    return null;
  }
}
