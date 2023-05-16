package com.example.SecondApp.repo;

import com.example.SecondApp.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Article,Integer> {
    Optional<Article> findBySlug(String slug);

//    Optional<Article> findByIdWithComments(Integer id);
}
