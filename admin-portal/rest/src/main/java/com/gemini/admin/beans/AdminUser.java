package com.gemini.admin.beans;

import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.commons.utils.Utils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/8/18
 * Time: 11:35 AM
 */
public class AdminUser implements UserDetails, CredentialsContainer {

    private final Set<GrantedAuthority> authorities;
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean enabled;
    private boolean sieUser;
    private boolean transportationUser;
    private boolean rhUser;
    private boolean sapdeUser;
    private boolean regionalUser;
    private boolean planificacionUser;
    private boolean directorUserRole;
    private int precedence;
    private List<Long> allowedRegions;
    private List<Long> allowedSchools;

    public AdminUser(Long userId,
                     String username,
                     String password,
                     String firstName,
                     String middleName,
                     String lastName,
                     boolean enabled,
                     int precedence,
                     Collection<? extends GrantedAuthority> authorities,
                     List<Long> allowedRegions,
                     List<Long> allowedSchools,
                     boolean sieUser,
                     boolean transportationUser,
                     boolean rhUser,
                     boolean sapdeUser,
                     boolean regionalUser,
                     boolean planificacionUser,
                     boolean directorUserRole) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.precedence = precedence;
        this.authorities = Collections.unmodifiableSet(new HashSet<>(authorities));
        this.allowedRegions = allowedRegions;
        this.allowedSchools = allowedSchools;
        this.sieUser = sieUser;
        this.transportationUser = transportationUser;
        this.rhUser = rhUser;
        this.sapdeUser = sapdeUser;
        this.regionalUser = regionalUser;
        this.planificacionUser = planificacionUser;
        this.directorUserRole = directorUserRole;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    public List<Long> getAllowedRegions() {
        return allowedRegions;
    }

    public List<Long> getAllowedSchools() {
        return allowedSchools;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isSieUser() {
        return sieUser;
    }

    public void setSieUser(boolean sieUser) {
        this.sieUser = sieUser;
    }

    public boolean isTransportationUser() {
        return transportationUser;
    }

    public void setTransportationUser(boolean transportationUser) {
        this.transportationUser = transportationUser;
    }

    public boolean isDirectorUser() {
        return this.precedence == AdminAccessHelper.SCHOOL_LEVEL;
    }

    public boolean isDirectorUserOverride() {
        return this.precedence == AdminAccessHelper.SCHOOL_LEVEL || this.directorUserRole;
    }


    public boolean isRhUser() {
        return rhUser;
    }

    public void setRhUser(boolean rhUser) {
        this.rhUser = rhUser;
    }

    public boolean isSapdeUser() {
        return sapdeUser;
    }

    public void setSapdeUser(boolean sapdeUser) {
        this.sapdeUser = sapdeUser;
    }

    public boolean isRegionalUser() {
        return regionalUser;
    }

    public void setRegionalUser(boolean regionalUser) {
        this.regionalUser = regionalUser;
    }

    public boolean isPlanificacionUser() {
        return planificacionUser;
    }

    public void setPlanificacionUser(boolean planificacionUser) {
        this.planificacionUser = planificacionUser;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    public String getFullName() {
        return Utils.toFullName(firstName, middleName, lastName);
    }
}