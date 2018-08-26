package com.gemini.commons.database.jpa.entities;

import com.gemini.commons.beans.internal.UserAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/16/18
 * Time: 2:20 PM
 */

@Entity
@Table(name = "user_action_log")
public class UserActionsLogEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserAction action;

    @Column(nullable = false)
    private String keyString;

    @Column(nullable = false)
    private String remoteIp;

    @CreatedDate
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date actionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserAction getAction() {
        return action;
    }

    public void setAction(UserAction action) {
        this.action = action;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
}