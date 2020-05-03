package com.huchengzhen.weblog.mapper;

import com.huchengzhen.weblog.dao.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findById(Long id);

    Optional<User> loadUserByUsername(String username);

    void register(User user);

    int updateLastLoginDate(Long id, Date date);

    List<User> findFollowed(Long follower);

    List<User> findFans(Long followed);
}