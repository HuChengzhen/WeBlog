package com.huchengzhen.weblog.service;

import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.loadUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFound"));
    }

    public void register(User user) {
        userMapper.register(user);
    }
}
