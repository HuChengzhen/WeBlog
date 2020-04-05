package com.huchengzhen.weblog.security;

import com.huchengzhen.weblog.service.UserService;
import com.huchengzhen.weblog.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtTokenUtils jwtTokenUtils;

    private UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils, UserService userService) {
        super(authenticationManager);
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));


        super.doFilterInternal(request, response, chain);
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("header"+ SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//        System.out.println("header"+ SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());


    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        if (tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            String token = tokenHeader.substring(JwtTokenUtils.TOKEN_PREFIX.length());

            Claims claims = jwtTokenUtils.getTokenBody(token);
            String username = jwtTokenUtils.getUsername(claims);

            if (username != null) {
                String roles = jwtTokenUtils.getUserRole(claims);
                Long id = jwtTokenUtils.getId(claims);
//                userService.updateLastLoginDate(id, new Date());
                return new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
            }
        }

        return null;
    }
}