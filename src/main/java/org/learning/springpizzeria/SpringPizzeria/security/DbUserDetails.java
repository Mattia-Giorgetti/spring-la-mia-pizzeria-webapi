package org.learning.springpizzeria.SpringPizzeria.security;

import org.learning.springpizzeria.SpringPizzeria.model.Role;
import org.learning.springpizzeria.SpringPizzeria.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DbUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    public DbUserDetails(User user) {
        this.username = user.getEmail();
        this.password= user.getPassword();
        this.authorities = new HashSet<>();
        for (Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
