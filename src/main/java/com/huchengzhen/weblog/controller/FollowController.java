package com.huchengzhen.weblog.controller;

import com.huchengzhen.weblog.dao.Follow;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/follow")
public class FollowController {

    @PostMapping
    public void follow(Follow follow) {

    }

}
