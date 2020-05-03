package com.huchengzhen.weblog.service;

import com.huchengzhen.weblog.dao.Follow;
import com.huchengzhen.weblog.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    private FollowMapper followMapper;

    public int insert(Follow follow) {
        return followMapper.insert(follow);
    }

    public int cancel(Follow follow) {
        return followMapper.cancel(follow);
    }
}
