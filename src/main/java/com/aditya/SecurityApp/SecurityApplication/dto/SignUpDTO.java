package com.aditya.SecurityApp.SecurityApplication.dto;


/*
 * @author adityagupta
 * @date 18/06/25
 */

import lombok.Data;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
}
