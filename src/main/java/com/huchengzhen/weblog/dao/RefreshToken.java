package com.huchengzhen.weblog.dao;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Data
public class RefreshToken {

    Long id;

    Long userId;

    String refreshToken;

    Date expireDate;

    public RefreshToken(Long userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        this.refreshToken = RandomStringUtils.randomAscii(40);
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.DAY_OF_YEAR, 30);
        this.expireDate = expireDate.getTime();
    }
}
