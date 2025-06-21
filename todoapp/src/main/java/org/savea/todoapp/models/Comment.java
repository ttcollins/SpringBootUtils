package org.savea.todoapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

@Entity
@Audited(withModifiedFlag = true)
@Data
@EqualsAndHashCode(exclude = "task")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)  // comment belongs to a task
    @JsonBackReference
    private Task task;
    private String body;
}
