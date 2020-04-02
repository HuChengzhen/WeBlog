package com.huchengzhen.weblog.mapper;

import com.huchengzhen.weblog.dao.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    User findById(Long id);

    Optional<User> loadUserByUsername(String username);

    void register(User user);
}