package com.aditya.SecurityApp.SecurityApplication.services;


import com.aditya.SecurityApp.SecurityApplication.dto.LoginDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.UserDTO;
import com.aditya.SecurityApp.SecurityApplication.entities.User;
import com.aditya.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.aditya.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @author adityagupta
 * @date 18/06/25
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow( () -> new BadCredentialsException("User with email "+ username + " no found"));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id "+ userId + " not found")
        );
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        // first we will check if the user is present in db or not
        Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists " + signUpDTO.getEmail());
        }

        User newUser = modelMapper.map(signUpDTO, User.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return modelMapper.map(newUser, UserDTO.class);
    }


    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
