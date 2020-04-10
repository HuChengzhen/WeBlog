package com.huchengzhen.weblog.mapper;

import com.huchengzhen.weblog.dao.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ArticleMapper {
    Optional<Article> findById(Long id);

    int insert(Article article);

    List<Article> findAll();
}
