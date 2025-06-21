package org.savea.todoapp.config;

import org.hibernate.envers.RevisionListener;
import org.savea.todoapp.models.UserRevision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Component
public class UserRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object entity) {
        UserRevision rev = (UserRevision) entity;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        rev.setUsername(auth != null ? auth.getName() : "anonymous");
        Object clientIp = RequestContextHolder.currentRequestAttributes()
                .getAttribute("CLIENT_IP", SCOPE_REQUEST);
        rev.setIp(clientIp != null ? clientIp.toString() : "unknown");
    }
}