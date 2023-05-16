package com.example.SecondApp.controllers;

import com.example.SecondApp.models.Article;
import com.example.SecondApp.repo.ArticlesRepository;
import com.example.SecondApp.utils.SlugGenerator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticlesRepository articlesRepository;

    @GetMapping("")
    public ResponseEntity articles(){
        List<Article> articlesList = (List<Article>) articlesRepository.findAll();
        return ResponseEntity.ok(articlesList);
    }

    @PostMapping("")
    public ResponseEntity addArticle(@RequestBody() String stringRequest) throws ParseException {
        JSONObject requestJson = new JSONObject(stringRequest);
        String title = requestJson.getString("title");
        String articleBody = requestJson.getString("body");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date date = formatter.parse(requestJson.getString("date"));
        String slug = SlugGenerator.slugify(title);
        Article article = new Article();
        article.setTitle(title);
        article.setArticleBody(articleBody);
        article.setCreated(date);
        article.setSlug(slug);
        articlesRepository.save(article);
        return ResponseEntity.ok(article);
    }

    @GetMapping("/{slug}")
    public ResponseEntity showArticle(@PathVariable("slug") String slug){
        Optional<Article> article = articlesRepository.findBySlug(slug);
//        articlesRepository.findBySlug(slug);
        return ResponseEntity.ok(article);
    }
}
