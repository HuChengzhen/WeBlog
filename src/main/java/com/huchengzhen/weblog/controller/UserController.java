package com.huchengzhen.weblog.controller;

import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/name/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        try {
            userService.register(user);
        } catch (DuplicateKeyException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    DuplicateKeyException.class.getSimpleName(),
                    exception
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public User me(Principal principal) {
        System.out.println(principal.getClass());

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;

        String username = token.getName();

        return userService.loadUserByUsername(username);
    }
}
