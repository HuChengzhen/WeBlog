package com.huchengzhen.weblog.controller;

import com.huchengzhen.weblog.dao.Follow;
import com.huchengzhen.weblog.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/v1/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@RequestBody Long followed, Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Long follower = (Long) token.getDetails();
        Follow follow = new Follow(follower, followed);

        int update = followService.insert(follow);

        if (update != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "insert follow failed");
        }
    }

    @DeleteMapping
    public void cancelFollow(@RequestBody Long followed, Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Long follower = (Long) token.getDetails();
        Follow follow = new Follow(follower, followed);

        int update = followService.cancel(follow);

        if (update != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cancel follow failed");
        }
    }
}
