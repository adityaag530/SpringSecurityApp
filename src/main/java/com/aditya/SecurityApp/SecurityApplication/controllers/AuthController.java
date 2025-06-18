package com.aditya.SecurityApp.SecurityApplication.controllers;


/*
 * @author adityagupta
 * @date 18/06/25
 */

import com.aditya.SecurityApp.SecurityApplication.dto.LoginDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.UserDTO;
import com.aditya.SecurityApp.SecurityApplication.services.AuthService;
import com.aditya.SecurityApp.SecurityApplication.services.JwtService;
import com.aditya.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse respose){
        String token = authService.login(loginDTO);
        Cookie cookie = new Cookie("token", token);
        // to make sure the cookie is not accessible using js and only through http method it can be used
        cookie.setHttpOnly(true);
        respose.addCookie(cookie);
        return ResponseEntity.ok(token);
    }
}
