package com.huchengzhen.weblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huchengzhen.weblog.dao.Article;
import com.huchengzhen.weblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public Optional<Article> find(@RequestParam Long id) {
        return articleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody Article article, Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Long id = (Long) token.getDetails();
        article.setAuthorId(id);
        Date date = new Date();
        article.setCreatedDate(date);
        article.setEditedDate(date);

        int update = articleService.insert(article);

        if (update != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post article failed");
        }
    }

    @GetMapping("/all")
    public PageInfo<Article> list(@RequestParam Integer page, @RequestParam Integer size) {
        PageHelper.startPage(page, size);
        List<Article> list = articleService.findAll();
        return new PageInfo<>(list);
    }

}
