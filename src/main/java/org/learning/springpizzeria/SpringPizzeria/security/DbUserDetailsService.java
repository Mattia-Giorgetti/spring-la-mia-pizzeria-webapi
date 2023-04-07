package org.learning.springpizzeria.SpringPizzeria.security;

import org.learning.springpizzeria.SpringPizzeria.model.User;
import org.learning.springpizzeria.SpringPizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()){
            return new DbUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
