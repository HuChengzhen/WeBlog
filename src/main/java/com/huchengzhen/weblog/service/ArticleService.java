package com.huchengzhen.weblog.service;

import com.huchengzhen.weblog.dao.Article;
import com.huchengzhen.weblog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private ArticleMapper articleMapper;

    @Autowired
    public void setUserMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public Optional<Article> findById(Long id) {
        return articleMapper.findById(id);
    }


    public int insert(Article article) {
        return articleMapper.insert(article);
    }

    public List<Article> findAll() {
        return articleMapper.findAll();
    }
}
