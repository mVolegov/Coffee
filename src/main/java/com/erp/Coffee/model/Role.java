package com.erp.Coffee.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

public enum Role {
    BARISTA, ADMIN;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(name()));
    }
}
