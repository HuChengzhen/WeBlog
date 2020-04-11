package com.huchengzhen.weblog.controller;

import com.huchengzhen.weblog.dao.RefreshToken;
import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.service.RefreshTokenService;
import com.huchengzhen.weblog.service.UserService;
import com.huchengzhen.weblog.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("v1/token")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @GetMapping
    public Map<String, String> getToken(@RequestParam String refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByRefreshToken(refreshToken);
        if (optionalRefreshToken.isPresent()) {
            RefreshToken token = optionalRefreshToken.get();
            if (token.getExpireDate().after(new Date())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refreshToken expired");
            }
            Long userId = token.getUserId();

            User user = userService.findById(userId).orElseThrow();

            Map<String, String> map = new HashMap<>();

            String jwtToken = jwtTokenUtils.createToken(user);
            map.put("token", jwtToken);

            if ((token.getExpireDate().getTime() - new Date().getTime()) / 1000 < Duration.ofDays(7).getSeconds()) {
                RefreshToken newRefreshToken = new RefreshToken(userId);
                refreshTokenService.insert(newRefreshToken);
                map.put("refreshToken", newRefreshToken.getRefreshToken());
            }
            return map;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
