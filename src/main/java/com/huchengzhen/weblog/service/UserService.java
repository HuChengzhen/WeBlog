package com.huchengzhen.weblog.service;

import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.loadUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFound"));
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(new Date());
        user.setIsEnabled(true);
        user.setEmailConfirmed(false);
        user.setRoles("ROLE_USER");

        userMapper.register(user);
    }

    public int updateLastLoginDate(Long id, Date date) {
        return userMapper.updateLastLoginDate(id, date);
    }
}
