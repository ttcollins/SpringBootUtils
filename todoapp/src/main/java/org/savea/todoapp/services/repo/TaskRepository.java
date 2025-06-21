package org.savea.todoapp.services.repo;

import org.savea.todoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface TaskRepository
        extends JpaRepository<Task, Long>,
        RevisionRepository<Task, Long, Integer> { }
