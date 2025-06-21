package org.savea.todoapp.controllers;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.savea.todoapp.models.Comment;
import org.savea.todoapp.models.Task;
import org.savea.todoapp.services.ActivityService;
import org.savea.todoapp.controllers.ActivityDto;
import org.savea.todoapp.services.TaskService;
import org.savea.todoapp.services.repo.TaskRepository;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService svc;
    private final TaskRepository tasks;
    private final ActivityService activityService;

    @GetMapping("/{id}/activity")
    public List<ActivityDto> feed(@PathVariable Long id) {
        return activityService.getActivity(Task.class, id);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task t) {
        return new ResponseEntity<>(svc.create(t), CREATED);
    }

    @PutMapping("/{id}") public Task update(@PathVariable Long id,
                                            @RequestBody Task t) {
        return svc.update(id, t);
    }

    @GetMapping("/{id}/revisions")
    public Revisions<Integer, Task> history(@PathVariable Long id) {
        return tasks.findRevisions(id);
    }

    @PostMapping("/{id}/comments")
    public Comment comment(@PathVariable Long id, @RequestBody Comment c) {
        return svc.addComment(id, c);
    }
}
