package com.huchengzhen.weblog.security;

import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.util.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            request.getSession().setAttribute("userDetail", user);

            String token = JwtTokenUtils.createToken(user.getUsername(), user.getRoles(), true);

            response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"ok\",\"message\":\"login success\"}");
            out.flush();
            out.close();
        }
    }
}
