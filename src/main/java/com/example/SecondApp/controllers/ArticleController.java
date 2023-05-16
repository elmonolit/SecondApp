package com.example.SecondApp.controllers;

import com.example.SecondApp.models.Article;
import com.example.SecondApp.models.Comment;
import com.example.SecondApp.repo.ArticlesRepository;
import com.example.SecondApp.repo.CommentRepository;
import com.example.SecondApp.utils.SlugGenerator;
import org.apache.coyote.Response;
import org.hibernate.Hibernate;
import org.json.JSONArray;
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
    @Autowired
    CommentRepository commentRepository;

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

//    @GetMapping("/{slug}")
//    public ResponseEntity showArticle(@PathVariable("slug") String slug){
//        Optional<Article> article = articlesRepository.findBySlug(slug);
////        articlesRepository.findBySlug(slug);
//        return ResponseEntity.ok(article);
//    }

    @GetMapping("/{id}")
    public ResponseEntity showArticle(@PathVariable("id") String id){
//        JSONObject article = new JSONObject(articlesRepository.findById(Integer.parseInt(id)));
//        JSONArray commentList = new JSONArray(commentRepository.findAllByArticleId(Integer.parseInt(id)));
//        article.put("comments", commentList);
////        System.out.println(article);
////        System.out.println(commentList);
//////        List<Comment> comments = article.get();
////        System.out.println(comments);
////        JSONObject result =
//        Optional<Article> article = articlesRepository.findById(Integer.parseInt(id));
        Optional<Article> article = articlesRepository.findById(Integer.parseInt(id));
        System.out.println(article.get().getCommentList());
        return ResponseEntity.ok(article.get());
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity addCommment(@PathVariable("id") String id, @RequestBody() String comment){
        JSONObject jsonComment = new JSONObject(comment);
        String author = jsonComment.getString("author");
        String commentBody = jsonComment.getString("comment");
        Article article = articlesRepository.findById(Integer.parseInt(id)).get();
        Comment commentEntity = new Comment();
        commentEntity.setArticle(article);
        commentEntity.setAuthor(author);
        commentEntity.setCommentBody(commentBody);
        commentRepository.save(commentEntity);
        return ResponseEntity.ok(commentEntity);
    }
}
