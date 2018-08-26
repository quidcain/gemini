package com.gemini.beans.forms;

import com.gemini.commons.beans.IdentityForm;
import com.gemini.commons.beans.types.Gender;
import com.gemini.commons.beans.types.RelationType;
import com.gemini.commons.utils.Utils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/12/18
 * Time: 6:39 PM
 */
public class User implements UserDetails, CredentialsContainer, IdentityForm {

    private Long id;
    private RelationType relationType;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;
    private Date lastLogin;
    private boolean enabled;
    private boolean profileCompleted;
    //    -1 means nothing, null means one or more requests submitted, a valid id means active pre-enrollment
    private Long workingPreEnrollmentId = -1L;
    private Integer totalPreEnrollments = 0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public Long getWorkingPreEnrollmentId() {
        return workingPreEnrollmentId;
    }

    public void setWorkingPreEnrollmentId(Long workingPreEnrollmentId) {
        this.workingPreEnrollmentId = workingPreEnrollmentId;
    }

    public Integer getTotalPreEnrollments() {
        return totalPreEnrollments;
    }

    public void setTotalPreEnrollments(Integer totalPreEnrollments) {
        this.totalPreEnrollments = totalPreEnrollments;
    }

    public boolean isCanGoHome() {
        return profileCompleted; //&& totalPreEnrollments > 0 && !(workingPreEnrollmentId != null && workingPreEnrollmentId > 0L);
    }

    public boolean isUserVocationalStudent(){
        return RelationType.SAME_STUDENT.equals(this.relationType);
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }


    public String getFullName() {
        return Utils.toFullName(firstName, middleName, lastName);
    }
}