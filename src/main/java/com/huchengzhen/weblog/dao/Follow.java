package com.huchengzhen.weblog.dao;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Follow {
    Long id;

    @NotNull
    @Min(1)
    Long follower;

    @NotNull
    @Min(1)
    Long followed;
}
