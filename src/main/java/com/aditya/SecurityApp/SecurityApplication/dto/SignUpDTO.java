package com.aditya.SecurityApp.SecurityApplication.dto;


/*
 * @author adityagupta
 * @date 18/06/25
 */

import com.aditya.SecurityApp.SecurityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
