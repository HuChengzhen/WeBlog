package com.huchengzhen.weblog.security;

import com.huchengzhen.weblog.service.UserService;
import com.huchengzhen.weblog.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    private AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    private AuthenticationFailureHandler jwtAuthenticationFailureHandler;

    private JwtTokenUtils jwtTokenUtils;

    @Value("${WeBlogRememberMeKey:key}")
    private String rememberMeKey;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtAuthenticationSuccessHandler(AuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
    }

    @Autowired
    public void setJwtAuthenticationFailureHandler(AuthenticationFailureHandler jwtAuthenticationFailureHandler) {
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
    }

    @Autowired
    public void setJwtTokenUtils(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    httpServletResponse.getWriter().write("{\"status\":\"error\",\"message\":\"unauthorized\"}");
                })
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                    httpServletResponse.getWriter().write("{\"status\":\"error\",\"message\":\"insufficient permissions\"}");
                })
                .and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/user").permitAll()
                .antMatchers("/v1/**").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .successHandler(jwtAuthenticationSuccessHandler)
                .failureHandler(jwtAuthenticationFailureHandler)
//                .and()
//                .rememberMe()
//                .userDetailsService(userService)
//                .key(rememberMeKey)
                .and()
                .logout().permitAll()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtTokenUtils, userService))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
