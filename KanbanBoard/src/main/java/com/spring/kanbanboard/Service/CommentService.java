package com.spring.kanbanboard.Service;

import com.spring.kanbanboard.modals.Comment;
import com.spring.kanbanboard.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    public List<Comment> findAll(){
        return this.commentRepository.findAll();
    }

    public void deleteById(int id){
        this.commentRepository.deleteById(id);
    }
    public void save(Comment comment){
        this.commentRepository.save(comment);
    }
}
