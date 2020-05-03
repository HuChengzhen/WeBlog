package com.huchengzhen.weblog.dao;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class Follow {
    Long id;

    @NotNull
    @Min(1)
    final Long follower;

    @NotNull
    @Min(1)
    final Long followed;
}
