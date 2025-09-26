/*
Spring Security doesn’t know anything about your users (how you store them, what fields they have, what roles they belong to).
But it has its own way of representing a user — through an interface called UserDetails.

👉 That’s why we create a bridge class: CustomUserDetails.
It adapts your own UserInfo model (from the database) into something that Spring Security understands (UserDetails).

Without this, Spring Security cannot log in users using your user model.
 */

package com.security.auth.service;

import com.security.auth.entity.UserInfo;
import com.security.auth.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail extends UserInfo implements UserDetails {
    private  String username;
    private  String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(UserInfo userInfo){
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();
        List<GrantedAuthority> roles = new ArrayList<>();
        for(UserRole role: userInfo.getRoles()){
            roles.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
}
