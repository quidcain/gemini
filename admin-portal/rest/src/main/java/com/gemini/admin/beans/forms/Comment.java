package com.gemini.admin.beans.forms;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/24/18
 * Time: 5:49 AM
 */
public class Comment {
    private String user;
    private String comment;
    private Date creationDate;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}