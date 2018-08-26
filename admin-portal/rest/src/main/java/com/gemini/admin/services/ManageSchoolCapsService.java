package com.gemini.admin.services;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.EditSchoolGradeLimit;
import com.gemini.admin.beans.forms.SchoolGradeLimitResult;
import com.gemini.admin.beans.requests.SchoolGradeLimitEditRequest;
import com.gemini.commons.database.dao.SchoolCapDao;
import com.gemini.commons.database.beans.SchoolGradeLimit;
import com.gemini.admin.database.jpa.entities.SchoolGradeLimitEditRequestEntity;
import com.gemini.admin.database.jpa.repositories.SchoolGradeLimitEditRequestRepository;
import com.gemini.admin.security.Authorities;
import com.gemini.commons.database.beans.School;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import com.gemini.commons.utils.GradeLevelUtils;
import com.gemini.commons.utils.ValidationUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 7:52 PM
 */
@Service
public class ManageSchoolCapsService {
    static Logger logger = LoggerFactory.getLogger(ManageSchoolCapsService.class.getName());

    @Autowired
    SchoolGradeLimitEditRequestRepository gradeLimitEditRequestRepository;
    @Autowired
    SchoolCapDao schoolCapDao;
    @Autowired
    SchoolMaxDaoInterface smaxInterface;

    public List<SchoolGradeLimitResult> getGradeLimitsBySchool(Long schoolId) {
        if (!ValidationUtils.valid(schoolId)) {
            return null;
        }

        List<SchoolGradeLimit> limits = schoolCapDao.getLimitBySchool(schoolId);
        List<SchoolGradeLimitResult> results = FluentIterable
                .from(limits)
                .transform(new Function<SchoolGradeLimit, SchoolGradeLimitResult>() {
                    @Override
                    public SchoolGradeLimitResult apply(SchoolGradeLimit limit) {
                        SchoolGradeLimitResult result = new SchoolGradeLimitResult();
                        result.setSchoolGradeLimitId(limit.getSchoolGradeLimitId());
                        result.setConfirmedMaxCapacity(limit.getConfirmedMaxCapacity());
                        result.setSchoolId(limit.getSchoolId());
                        result.setGradeLevel(limit.getGradeLevel());
                        result.setGradeLevelDescription(GradeLevelUtils.getDescriptionByGradeLevel(limit.getGradeLevel()));
                        result.setMaxCapacity(limit.getMaxCapacity());
                        return result;
                    }
                })
                .toList();
        return results;
    }

    public EditSchoolGradeLimit getGradeLimit(Long schoolGradeLimitId, Long userId) {
        EditSchoolGradeLimit editSchoolGradeLimit = new EditSchoolGradeLimit();
        SchoolGradeLimit gradeLimit = schoolCapDao.getLimitById(schoolGradeLimitId);
        editSchoolGradeLimit.setSchoolGradeLimitId(schoolGradeLimitId);
        editSchoolGradeLimit.setSchoolId(gradeLimit.getSchoolId());
        editSchoolGradeLimit.setGradeLevel(gradeLimit.getGradeLevel());
        editSchoolGradeLimit.setGradeLevelDescription(GradeLevelUtils.getDescriptionByGradeLevel(gradeLimit.getGradeLevel()));
        editSchoolGradeLimit.setMaxCapacity(gradeLimit.getMaxCapacity());
        SchoolGradeLimitEditRequestEntity entity = gradeLimitEditRequestRepository.findByUserIdAndSchoolIdAndGradeLevel(userId, gradeLimit.getSchoolId(), gradeLimit.getGradeLevel());
        if (entity != null)
            editSchoolGradeLimit.setConfirmedMaxCapacity(entity.getConfirmedMaxCapacity());

        School school = smaxInterface.findSchoolById(gradeLimit.getSchoolId());
        editSchoolGradeLimit.setSchoolName(school.getSchoolName());


        return editSchoolGradeLimit;
    }

    public boolean saveChangeRequestForGradeLimit(SchoolGradeLimitEditRequest request, AdminUser adminUser) {
        SchoolGradeLimit gradeLimit = schoolCapDao.getLimitBySchoolAndGradeLevel(request.getSchoolId(), request.getGradeLevel());

        if (gradeLimit == null)
            return false;

        SchoolGradeLimitEditRequestEntity entity = gradeLimitEditRequestRepository.findByUserIdAndSchoolIdAndGradeLevel(adminUser.getUserId(), request.getSchoolId(), request.getGradeLevel());
        if (entity == null) {
            entity = new SchoolGradeLimitEditRequestEntity();
            entity.setSchoolId(request.getSchoolId());
            entity.setGradeLevel(request.getGradeLevel());
            entity.setUserId(adminUser.getUserId());
        } else {
            entity.setRevisionDate(new Date());
        }


        String authority = Lists.newArrayList(adminUser.getAuthorities()).get(0).getAuthority();
        entity.setLoggedAuthority(Authorities.valueOf(authority));
        entity.setMaxCapacity(gradeLimit.getPlanificacionMaxCapacity());
        entity.setConfirmedMaxCapacity(request.getNewMaxCapacity());
        entity = gradeLimitEditRequestRepository.save(entity);

        Integer maxCapacity = request.getNewMaxCapacity() > gradeLimit.getMaxCapacity()
                ? request.getNewMaxCapacity()
                : gradeLimit.getPlanificacionMaxCapacity();

        schoolCapDao.updateLimitBySchoolAndGradeLevel(request.getSchoolId(), request.getGradeLevel(), adminUser.getUserId(), maxCapacity);

        return entity != null;
    }


}