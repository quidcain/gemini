package com.gemini.services;

import com.gemini.commons.beans.requests.StudentSearchRequest;
import com.gemini.commons.beans.types.SchoolCategory;
import com.gemini.commons.beans.types.SpecializedSchoolCategory;
import com.gemini.commons.database.beans.*;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import com.gemini.commons.utils.GradeLevelUtils;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/9/18
 * Time: 1:01 AM
 */
@Service
public class SchoolmaxService {

    final Logger logger = LoggerFactory.getLogger(SchoolmaxService.class.getName());

    final Integer OCCUPATIONAL_MIN_GRADE_LEVEL = 9;

    @Autowired
    private SchoolMaxDaoInterface smaxDao;
    @Autowired
    private CommonService commonService;

    @PostConstruct
    public void check() {
        logger.info(String.format("*****Smax Interface Use is %s*****", smaxDao.getClass().getSimpleName()));
    }

    public Parent retrieveHouseHeadInfo(String lastSsn, Date dob, String lastname) {
        return smaxDao.findHouseHead(lastSsn, dob, lastname);
    }

    public Student retrieveStudentInfo(StudentSearchRequest searchRequest) {
        return smaxDao.findStudent(searchRequest);
    }

    public Student retrieveStudentInfo(Long studentNumber) {
        return smaxDao.findStudent(studentNumber);
    }

    public StudentAddress retrieveStudentAddress(Long studentNumber) {
        return smaxDao.findAddress(studentNumber);
    }

    public EnrollmentInfo retrieveMostRecentEnrollment(Long studentId) {
        return smaxDao.findRecentStudentEnrollment(studentId);
    }

    public EnrollmentInfo findSIEStudentEnrollment(Long studentId) {
        return smaxDao.findSIEStudentEnrollment(studentId);
    }

    public School findSchoolById(Long schoolId) {
        return smaxDao.findSchoolById(schoolId);
    }

    @Cacheable
    public List<School> findSchoolByRegionAndGradeLevel(Long regionId, String gradeLevel) {
        Long schoolYear = commonService.getPreEnrollmentYear();
        return smaxDao.findSchoolsByRegionAndGradeLevel(regionId, schoolYear, gradeLevel);
    }

    public List<School> findOccupationalSchoolsByRegionAndGradeLevel(Long regionId, String gradeLevel) {
        Long schoolYear = commonService.getPreEnrollmentYear();
        return smaxDao.findOccupationalSchoolsByRegionAndGradeLevel(regionId, schoolYear, gradeLevel);
    }

    public List<School> findSpecializedSchoolsByRegionAndGradeLevel(Long regionId, String gradeLevel, SpecializedSchoolCategory category) {
        Long schoolYear = commonService.getPreEnrollmentYear();
        return smaxDao.findSpecializedSchoolsByRegionAndGradeLevel(regionId, schoolYear, gradeLevel, category);
    }

    public List<School> findTechnicalSchools() {
        Long schoolYear = commonService.getPreEnrollmentYear();
        return smaxDao.findTechnicalSchools(schoolYear);
    }

    public List<School> findSharedSchoolsByGradeLevel(String gradeLevel) {
        Long schoolYear = commonService.getPreEnrollmentYear();
        return smaxDao.findSharedSchools(schoolYear, gradeLevel);
    }

    public School findSharedSchoolsBySchoolId(Long schoolId) {
        return smaxDao.findSharedSchoolById(schoolId);
    }


    @Cacheable
    public SchoolGradeLevel findSchoolLevel(Long schoolYear, Long schoolId, String gradeLevel) {
        return smaxDao.findGradeLevelInfo(schoolYear, schoolId, gradeLevel);
    }

    @Cacheable
    public List<Region> getAllRegions() {
        return smaxDao.getAllRegions();
    }

    @Cacheable
    public List<Region> geVocationalRegions() {
        return smaxDao.getVocationalRegions();
    }

    @Cacheable
    public List<GradeLevel> getAllGradeLevels(final SchoolCategory category) {
        return FluentIterable
                .from(GradeLevelUtils.gradeLevels.entrySet())
                .filter(new Predicate<Map.Entry<String, String>>() {
                    @Override
                    public boolean apply(Map.Entry<String, String> entry) {

                        if (SchoolCategory.OCCUPATIONAL.equals(category)) {
                            try {
                                int gradeLevelInt = Integer.valueOf(entry.getKey());
                                return gradeLevelInt >= OCCUPATIONAL_MIN_GRADE_LEVEL;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        }

                        return true;
                    }
                })
                .transform(new Function<Map.Entry<String, String>, GradeLevel>() {
                    @Override
                    public GradeLevel apply(Map.Entry<String, String> entry) {
                        return new GradeLevel(entry.getKey(), entry.getValue(), entry.getKey());
                    }
                }).toList();
    }

    public GradeLevel getGradeLevelByCode(String code) {
        String value = GradeLevelUtils.gradeLevels.get(code);
        return value != null ? new GradeLevel(code, value, code) : null;
    }

    public List<VocationalProgram> getVocationalPrograms(Long schoolId) {
        Long schoolYear = commonService.getPreEnrollmentYear();
        return smaxDao.getVocationalPrograms(schoolId, schoolYear);
    }

    public boolean foundStudentBySsn(String ssn) {
        return smaxDao.foundStudentBySsn(ssn);
    }

    public boolean schoolInExclusionList(Long schoolId) {
        return smaxDao.schoolInExclusionList(schoolId);
    }

    public boolean schoolInMontesorri(Long schoolId) {
        return smaxDao.schoolInMontesorri(schoolId);
    }

    public List<StudentScheduleView> getStudentScheduleView(Long studentId){
        return smaxDao.getStudentScheduleView(studentId);
    }
}