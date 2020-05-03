package com.huchengzhen.weblog.mapper;

import com.huchengzhen.weblog.dao.Follow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {

    int insert(Follow follow);

}
