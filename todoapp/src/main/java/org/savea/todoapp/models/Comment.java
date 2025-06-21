package org.savea.todoapp.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)  // comment belongs to a task
    private Task task;
    private String body;
}
