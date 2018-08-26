package com.gemini.admin.database.jpa.entities;

import com.gemini.admin.security.Authorities;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 8:01 PM
 */
@Entity
@Table(name = "school_grade_limit_request", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "schoolId", "gradeLevel"})})
public class SchoolGradeLimitEditRequestEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long schoolId;
    @Column(nullable = false)
    private String gradeLevel;
    @Column(nullable = false)
    private Integer maxCapacity;
    @Column(nullable = false)
    private Integer confirmedMaxCapacity;
    @Enumerated(EnumType.STRING)
    @Column
    private Authorities loggedAuthority;
    @CreatedDate
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date creationDate;
    @LastModifiedDate
    @Column()
    private Date revisionDate;

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

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getConfirmedMaxCapacity() {
        return confirmedMaxCapacity;
    }

    public void setConfirmedMaxCapacity(Integer confirmedMaxCapacity) {
        this.confirmedMaxCapacity = confirmedMaxCapacity;
    }

    public Authorities getLoggedAuthority() {
        return loggedAuthority;
    }

    public void setLoggedAuthority(Authorities loggedAuthority) {
        this.loggedAuthority = loggedAuthority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }
}