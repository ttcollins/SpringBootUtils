package org.savea.todoapp.services.repo;

import org.savea.todoapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface CommentRepository
        extends JpaRepository<Comment, Long>,
        RevisionRepository<Comment, Long, Integer> { }