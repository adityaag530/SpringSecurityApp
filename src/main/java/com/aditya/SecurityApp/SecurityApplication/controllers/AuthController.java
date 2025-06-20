package com.aditya.SecurityApp.SecurityApplication.controllers;


/*
 * @author adityagupta
 * @date 18/06/25
 */

import com.aditya.SecurityApp.SecurityApplication.dto.LoginDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.LoginResponseDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.aditya.SecurityApp.SecurityApplication.dto.UserDTO;
import com.aditya.SecurityApp.SecurityApplication.services.AuthService;
import com.aditya.SecurityApp.SecurityApplication.services.JwtService;
import com.aditya.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    @Value("${deploy.env}")
    private String deployEnv;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse respose){
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        // to make sure the cookie is not accessible using js and only through http method it can be used
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));// in production this need to be set
        respose.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow( () -> new AuthenticationServiceException("Refresh token not found inside the cookie"));

        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
        return  ResponseEntity.ok(loginResponseDTO);
    }
}
