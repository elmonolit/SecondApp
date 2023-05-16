package com.example.SecondApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import javax.xml.crypto.Data;
import java.util.*;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    @Column(columnDefinition = "Text")
    private String articleBody;
    private Date created;

//    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="article_id")
//    @JoinTable(name="article_comments", joinColumns = @JoinColumn(name="article_id"))
    private List<Comment> commentList = new ArrayList<>();

    public List<Map<String,String>> getCommentList() {
        List<Map<String,String>> cl = new ArrayList<>();
        for (Comment c: commentList) {
            Map<String, String> pair = new HashMap<>();
            pair.put("author", c.getAuthor());
            pair.put("commentBody", c.getCommentBody());
            cl.add(pair);
        }
        return cl;
    }

    @NaturalId
    private String slug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
