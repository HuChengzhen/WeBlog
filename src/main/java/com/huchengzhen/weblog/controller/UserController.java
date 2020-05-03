package com.huchengzhen.weblog.controller;

import com.huchengzhen.weblog.dao.User;
import com.huchengzhen.weblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
    }

    @GetMapping
    public User find(@RequestParam(required = false) Long id, @RequestParam(required = false) String username) {
        if (id != null) {
            return userService.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "id not exist"
            ));
        }

        if (username != null) {
            try {
                return userService.loadUserByUsername(username);
            } catch (UsernameNotFoundException exception) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        UsernameNotFoundException.class.getSimpleName(),
                        exception
                );
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userService.loadUserByUsername(authentication.getName());
    }

    @PostMapping()
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
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;

        String username = token.getName();

        return userService.loadUserByUsername(username);
    }

    @GetMapping("/followed")
    public List<User> findFollowedUsers(Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Long follower = (Long) token.getDetails();
        return userService.findFollowed(follower);
    }

    @GetMapping("/fans")
    public List<User> findFans(Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Long followed = (Long) token.getDetails();
        return userService.findFans(followed);
    }
}
