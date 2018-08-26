package com.gemini.admin.services;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.admin.database.dao.SmaxUserDao;
import com.gemini.admin.database.dao.beans.SieRole;
import com.gemini.admin.database.dao.beans.SieUser;
import com.gemini.admin.database.jpa.entities.AdminUserEntity;
import com.gemini.admin.database.jpa.repositories.AdminUserRepository;
import com.gemini.admin.security.Authorities;
import com.gemini.commons.database.beans.School;
import com.gemini.commons.utils.CopyUtils;
import com.gemini.commons.utils.ValidationUtils;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/17/18
 * Time: 2:41 PM
 */
@Service
public class UserService {
    final Long SIE_ROLE = 3641L;
    final Long TRANSPORTATION_ROLE = 7265L;
    final Long RH_ROLE = 5645L;
    final Long SAPDE_ROLE = 7967L;
    final Long REGIONAL_ROLE = 3642L;
    final Long PLANIFICACION_ROLE = 7967L;
    final Long SCHOOL_DIRECTOR_ROLE = 2620L;


    @Autowired
    @Qualifier("smaxUserDao")
    private SmaxUserDao userDao;

    @Autowired
    private AdminUserRepository userRepository;

    public AdminUser loadUserByUsername(String username) {
        SieUser sieUser = userDao.loadByUsername(username);
        if (sieUser == null)
            return null;
        List<SieRole> sieRoles = userDao.loadRoles(sieUser.getUserId());
        if (sieRoles == null) {
            return null;
        }
        int precedence = sieRoles.get(0).getPrecedence();
        List<Long> regionsAllowed = Collections.emptyList();
        List<Long> schoolsAllowed = Collections.emptyList();
        Authorities authority = null;
        boolean isSIEUser = false;
        boolean isTransportationUser = false;
        boolean isRHUser = false;
        boolean isSAPDE = false;
        boolean isRegionalUser = false;
        boolean isPlanificaUser = false;
        boolean directorUserRole = false;


        for (SieRole sieRole : sieRoles) {
            isSIEUser |= SIE_ROLE.equals(sieRole.getRoleId());
            isTransportationUser |= TRANSPORTATION_ROLE.equals(sieRole.getRoleId());
            isRHUser |= RH_ROLE.equals(sieRole.getRoleId());
            isSAPDE |= SAPDE_ROLE.equals(sieRole.getRoleId());
            isRegionalUser |= REGIONAL_ROLE.equals(sieRole.getRoleId());
            isPlanificaUser |= PLANIFICACION_ROLE.equals(sieRole.getRoleId());
            directorUserRole |= SCHOOL_DIRECTOR_ROLE.equals(sieRole.getRoleId());
        }

        switch (precedence) {
            case AdminAccessHelper.DE_CENTRAL_LEVEL:
                authority = Authorities.DE_CENTRAL;
                break;
            case AdminAccessHelper.REGION_LEVEL:
                regionsAllowed = FluentIterable
                        .from(sieRoles)
                        .filter(new Predicate<SieRole>() {
                            @Override
                            public boolean apply(SieRole role) {
                                return ValidationUtils.valid(role.getRegionId());
                            }
                        })
                        .transform(new Function<SieRole, Long>() {
                            @Override
                            public Long apply(SieRole role) {
                                return role.getRegionId();
                            }
                        })
                        .toList();
                authority = regionsAllowed.size() > 0
                        ? Authorities.ACCESS_ON_VARIOUS_REGIONS
                        : Authorities.REGION_DIRECTOR;
                break;
            case AdminAccessHelper.SCHOOL_LEVEL:
                School school = userDao.getSchoolById(sieRoles.get(0).getSchoolId());
                schoolsAllowed = FluentIterable
                        .from(sieRoles)
                        .filter(new Predicate<SieRole>() {
                            @Override
                            public boolean apply(SieRole role) {
                                return ValidationUtils.valid(role.getSchoolId());
                            }
                        })
                        .transform(new Function<SieRole, Long>() {
                            @Override
                            public Long apply(SieRole role) {
                                return role.getSchoolId();
                            }
                        })
                        .toList();
                authority = regionsAllowed.size() > 0
                        ? Authorities.ACCESS_ON_VARIOUS_SCHOOLS
                        : Authorities.SCHOOL_DIRECTOR;
                regionsAllowed = Lists.newArrayList(school.getRegionId());
                break;
        }

        if (authority == null)
            return null;


        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.name());
        AdminUser user = new AdminUser(
                sieUser.getUserId(),
                sieUser.getUsername(),
                sieUser.getPassword(),
                sieUser.getFirstName(),
                sieUser.getMiddleName(),
                sieUser.getLastName(),
                true,
                precedence,
                Sets.newHashSet(grantedAuthority),
                regionsAllowed,
                schoolsAllowed,
                isSIEUser,
                isTransportationUser,
                isRHUser,
                isSAPDE,
                isRegionalUser,
                isPlanificaUser,
                directorUserRole
        );


        AdminUserEntity entity = userRepository.findByUserId(user.getUserId());
        if (entity == null) {
            entity = CopyUtils.convert(user, AdminUserEntity.class);
        }
        entity.setLoggedAuthority(authority);
        userRepository.save(entity);

        return user;
    }

    @Transactional
    public void saveLastLogin(AdminUser user) {
        AdminUserEntity entity = userRepository.findByUserId(user.getUserId());
        entity.setLastLogin(new Date());
        entity.setRevisionDate(new Date());
        entity.setTries(0L);
        userRepository.save(entity);
    }

    @Transactional
    public void failureLogin(String username) {
        AdminUserEntity entity = userRepository.findByUsername(username);
        if (ValidationUtils.valid(entity) && ValidationUtils.valid(entity.getId())) {
            entity.setRevisionDate(new Date());
            entity.setTries(entity.getTries() + 1);
            userRepository.save(entity);
        }
    }


}