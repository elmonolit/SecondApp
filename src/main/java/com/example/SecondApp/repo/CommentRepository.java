package com.example.SecondApp.repo;

import com.example.SecondApp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByArticleId(Integer articleId);
}
