package com.quipux.login.User;

//import java.util.Collection;
//import java.util.Collections;
import java.util.List;

import java.util.Set;
//import java.util.stream.Collector;
import java.util.stream.Collectors;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.quipux.login.User.Permission.ADMIN_READ;
import static com.quipux.login.User.Permission.ADMIN_UPDATE;
import static com.quipux.login.User.Permission.ADMIN_DELETE;
import static com.quipux.login.User.Permission.ADMIN_CREATE;

import static com.quipux.login.User.Permission.USER_READ;
import static com.quipux.login.User.Permission.USER_UPDATE;
import static com.quipux.login.User.Permission.USER_DELETE;
import static com.quipux.login.User.Permission.USER_CREATE;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
   // USER(Collections.emptySet()),
    ADMIN(
        Set.of(
        ADMIN_READ,
        ADMIN_UPDATE,
        ADMIN_DELETE,
        ADMIN_CREATE,
        USER_READ,
        USER_UPDATE,
        USER_DELETE,
        USER_CREATE
        )
    ),
    USER(
            Set.of(
        USER_READ,
        USER_UPDATE,
        USER_DELETE,
        USER_CREATE
        )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority>getAuthorities(){
       var authorities= getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    };
}
