package org.savea.todoapp.services;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.savea.todoapp.config.DiffUtil;
import org.savea.todoapp.config.Change;
import org.savea.todoapp.controllers.ActivityDto;
import org.savea.todoapp.models.Task;
import org.savea.todoapp.models.UserRevision;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final EntityManager em;

    public List<ActivityDto> getTaskActivity(Long taskId) {

        AuditReader reader = AuditReaderFactory.get(em);          // raw Envers
        List<Number> revNums = reader.getRevisions(Task.class, taskId); // all rev IDs

        return IntStream.range(0, revNums.size())
                .mapToObj(i -> {
                    Number rev = revNums.get(i);
                    Task snapshot = reader.find(Task.class, taskId, rev);
                    UserRevision meta = reader.findRevision(UserRevision.class, rev);

                    Map<String, Change<?>> diff;
                    try {
                        // If it's the first revision, there's no previous revision to compare against
                        diff = i == 0 ? Map.of()
                                : DiffUtil.diff(reader.find(Task.class, taskId, revNums.get(i - 1)), snapshot);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                    return new ActivityDto(rev.longValue(),
                            meta.getRevisionDate(),
                            meta.getUsername(),
                            diff);
                })
                .sorted(Comparator.comparing(ActivityDto::timestamp).reversed())
                .toList();
    }
}