package com.edug.devfinder.models.entities;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private User user;

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = user.getRoles();
        var authorities = new HashSet<GrantedAuthority>();
        var permissionsAuthorities = roles.stream().flatMap((p) -> p.getPermissions().stream()).collect(Collectors.toSet());
        var rolesAuthorities = roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        authorities.addAll(permissionsAuthorities);
        authorities.addAll(rolesAuthorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getRemovedAt() == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
