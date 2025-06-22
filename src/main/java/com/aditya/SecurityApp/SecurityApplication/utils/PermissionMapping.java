package com.aditya.SecurityApp.SecurityApplication.utils;


/*
 * @author adityagupta
 * @date 22/06/25
 */

import com.aditya.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.aditya.SecurityApp.SecurityApplication.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aditya.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.aditya.SecurityApp.SecurityApplication.entities.enums.Role.*;

public class PermissionMapping {
    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(USER_UPDATE, POST_UPDATE, POST_CREATE),
            ADMIN, Set.of(USER_UPDATE, POST_UPDATE, POST_CREATE,USER_DELETE, POST_DELETE, USER_CREATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
