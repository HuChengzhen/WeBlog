package com.huchengzhen.weblog.service;

import com.huchengzhen.weblog.dao.RefreshToken;
import com.huchengzhen.weblog.mapper.RefreshTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenMapper.findByRefreshToken(refreshToken);
    }

    public int insert(RefreshToken refreshToken) {
        return refreshTokenMapper.insert(refreshToken);
    }
}
