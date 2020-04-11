package com.huchengzhen.weblog.mapper;

import com.huchengzhen.weblog.dao.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    int insert(RefreshToken refreshToken);
}
