package com.huchengzhen.weblog.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    @JsonIgnore
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
            message = "Invalid Email")
    private String email;

    @JsonIgnore
    private Boolean isEnabled;

    @JsonIgnore
    private Boolean emailConfirmed;

    private String roles;

    private Date createdDate;

    private Date lastLoginDate;

    @JsonIgnore
    private List<GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
        }

        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
}