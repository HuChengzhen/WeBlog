package com.huchengzhen.weblog.dao;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Long id;

    private Long authorId;

    private String title;

    private String body;

    private Date createdDate;

    private Date editedDate;
}
