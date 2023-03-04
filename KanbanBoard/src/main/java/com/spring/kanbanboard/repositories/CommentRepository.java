package com.spring.kanbanboard.repositories;

import com.spring.kanbanboard.modals.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findById(int id);
}
