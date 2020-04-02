package com.huchengzhen.weblog.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class User implements UserDetails {

    private static final long serialVersionUID = 1622211531448533147L;

    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Username max length is 50 characters")
    private String username;

    private String avatar;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
            message = "Invalid Email")
    private String email;

    private Boolean isEnabled;

    private Boolean emailConfirmed;

    private String roles;

    private Date createdDate;

    private Date lastLoginDate;

    private List<GrantedAuthority> authorities;

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
}