package com.huchengzhen.weblog.security;

import com.huchengzhen.weblog.dao.RefreshToken;
import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.service.RefreshTokenService;
import com.huchengzhen.weblog.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
//            request.getSession().setAttribute("userDetail", user);

            String token = jwtTokenUtils.createToken(user);


            RefreshToken refreshToken = new RefreshToken(user.getId());


            refreshTokenService.insert(refreshToken);

            response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
            response.setHeader("refresh_token", URLEncoder.encode(refreshToken.getRefreshToken(), StandardCharsets.UTF_8));
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"ok\",\"message\":\"login success\"}");
            out.flush();
            out.close();
        }
    }
}
