package com.aditya.SecurityApp.SecurityApplication.entities;


/*
* @author adityagupta
* @date 17/06/25
*/

import com.aditya.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.aditya.SecurityApp.SecurityApplication.entities.enums.Role;
import com.aditya.SecurityApp.SecurityApplication.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)// fetch roles as soon as user
    @Enumerated(EnumType.STRING) // by default ordinalal type is number, change to string
    private Set<Role> roles;

    // we have created utils mapping for role to permission so no need to add in db
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toSet());
//
//        permissions
//                .forEach(
//                        permission -> authorities.add(new SimpleGrantedAuthority(permission.name()))
//                );

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
