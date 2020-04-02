package com.huchengzhen.weblog.controller;

import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(new Date());
        user.setIsEnabled(true);
        user.setEmailConfirmed(false);
        user.setRoles("ROLE_USER");
        userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
