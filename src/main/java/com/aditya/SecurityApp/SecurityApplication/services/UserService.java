package com.aditya.SecurityApp.SecurityApplication.services;


import com.aditya.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.aditya.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * @author adityagupta
 * @date 18/06/25
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow( () -> new ResourceNotFoundException("User with email "+ username + " no found"));
    }
}
