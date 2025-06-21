package org.savea.todoapp.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.savea.todoapp.models.Comment;
import org.savea.todoapp.models.Task;
import org.savea.todoapp.services.repo.CommentRepository;
import org.savea.todoapp.services.repo.TaskRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository tasks;
    private final CommentRepository comments;

    public Task create(Task t) { return tasks.save(t); }

    public Task update(Long id, Task incoming) {
        Task t = tasks.findById(id)
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));
        t.setTitle(incoming.getTitle());
        t.setDescription(incoming.getDescription());
        t.setStatus(incoming.getStatus());
        return t; // dirty-checking + Envers take care of the rest
    }

    public Comment addComment(Long taskId, Comment c) {
        Task t = tasks.getReferenceById(taskId);
        c.setTask(t);
        return comments.save(c);
    }
}

