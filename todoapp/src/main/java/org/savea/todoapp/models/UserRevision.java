package org.savea.todoapp.models;

import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.savea.todoapp.config.UserRevisionListener;

@Entity
@RevisionEntity(UserRevisionListener.class)
@Data
public class UserRevision extends DefaultRevisionEntity {

    private String username;            // extra column in REVINFO
    private String ip;
}

