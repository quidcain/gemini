package com.gemini.admin.services;

import com.gemini.admin.AdminUIHelper;
import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.EditStudentRequest;
import com.gemini.admin.beans.forms.SchoolGradeLimitResult;
import com.gemini.admin.beans.forms.StudentSearchResult;
import com.gemini.admin.beans.requests.ApprovalInputRequest;
import com.gemini.admin.beans.requests.StudentMontessoriInfoRequest;
import com.gemini.admin.database.dao.ApprovalDao;
import com.gemini.admin.database.dao.beans.SRSStudent;
import com.gemini.admin.database.jpa.entities.ApprovalDirectorLogEntity;
import com.gemini.admin.database.jpa.entities.OccupationalApprovalEntity;
import com.gemini.admin.database.jpa.repositories.ApprovalDirectorLogRepository;
import com.gemini.admin.database.jpa.repositories.OccupationalApprovalRepository;
import com.gemini.commons.beans.forms.VocationalProgramSelection;
import com.gemini.commons.beans.types.EnrollmentType;
import com.gemini.commons.beans.types.RequestStatus;
import com.gemini.commons.database.beans.School;
import com.gemini.commons.database.beans.SchoolGradeLimit;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import com.gemini.commons.database.jpa.entities.PreEnrollmentRequestEntity;
import com.gemini.commons.database.jpa.respository.PreEnrollmentRepository;
import com.gemini.commons.utils.DateUtils;
import com.gemini.commons.utils.GradeLevelUtils;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 10:05 PM
 */
@Service
public class ApprovalService {

    @Autowired
    private ApprovalDao approvalDao;
    @Autowired
    private ApprovalDirectorLogRepository approvalDirectorLogRepository;
    @Autowired
    private PreEnrollmentRepository preEnrollmentRepository;
    @Autowired
    private SchoolMaxDaoInterface smaxInterface;
    @Autowired
    private OccupationalApprovalRepository occupationalApprovalRepository;

    public List<School> getApprovalSchools(AdminUser adminUser) {
        return approvalDao.getSchools(adminUser);
    }

    public List<StudentSearchResult> getStudentsBySchoolAndGradeLevel(Long schoolId, String gradeLevel) {
        if (!ValidationUtils.valid(schoolId)|| !ValidationUtils.valid(gradeLevel)) {
            return null;
        }

        List<SRSStudent> students = approvalDao.getStudentsBySchoolAndGradeLevel(schoolId, gradeLevel);
        List<StudentSearchResult> results = FluentIterable.from(students)
                .transform(new Function<SRSStudent, StudentSearchResult>() {
                    @Override
                    public StudentSearchResult apply(SRSStudent srsStudent) {
                        StudentSearchResult r = new StudentSearchResult();
                        r.setPreEnrollmentId(srsStudent.getPreEnrollmentId());
                        r.setStudentId(srsStudent.getSisStudentId());
                        r.setStudentName(Utils.toFullName(srsStudent.getFirstName(), srsStudent.getMiddleName(), srsStudent.getLastName()));
                        r.setOriginSchoolNumber(srsStudent.getOriginSchoolNumber());
                        r.setColorCss(AdminUIHelper.getColorCss(srsStudent.getRequestStatus()));
                        r.setHaveDisability(StringUtils.hasText(srsStudent.getImpedimento()) ? "Sí" : "No");
                        r.setAge(DateUtils.toYears(srsStudent.getDateOfBirth()));        
                        r.setStatus(srsStudent.getRequestStatus().getDescription());
                        r.setRequestedPrograms(srsStudent.getOccupationalEnrollment() > 0 ? "Sí" : "No");
                        r.setMontessori(srsStudent.isMontessori());
                        return r;
                    }
                })
                .toList();
        return results;
    }

    public EditStudentRequest getStudentRequestByPreEnrollmentId(final Long preEnrollmentId, final Long requestedSchoolId) {
        SRSStudent srsStudent = approvalDao.getStudentByPreEnrollmentId(preEnrollmentId);
        EditStudentRequest result = new Function<SRSStudent, EditStudentRequest>() {
            @Override
            public EditStudentRequest apply(SRSStudent srsStudent) {
                EditStudentRequest r = new EditStudentRequest();
                r.setPreEnrollmentId(srsStudent.getPreEnrollmentId());
                r.setStudentId(srsStudent.getSisStudentId());
                r.setDisability(srsStudent.getImpedimento());
                r.setReasonToDeny(srsStudent.getReasonToDenyEnrollment() != null ? srsStudent.getReasonToDenyEnrollment().getDescription() : "Ninguna");
                r.setColorCss(AdminUIHelper.getColorCss(srsStudent.getRequestStatus()));
                r.setOriginSchoolNumber(srsStudent.getOriginSchoolNumber());
                r.setOriginSchoolName(srsStudent.getOriginSchoolName());
                School school = null;
                if (requestedSchoolId.equals(srsStudent.getCoalesceSchoolId_1())) {
                    r.setSchoolId(srsStudent.getCoalesceSchoolId_1());
                    school = smaxInterface.findSchoolById(srsStudent.getCoalesceSchoolId_1());
                } else if (requestedSchoolId.equals(srsStudent.getCoalesceSchoolId_2())) {
                    r.setSchoolId(srsStudent.getCoalesceSchoolId_2());
                    school = smaxInterface.findSchoolById(srsStudent.getCoalesceSchoolId_2());
                }

                r.setDenied(RequestStatus.DENIED.equals(srsStudent.getRequestStatus())
                        || RequestStatus.DENIED_BY_DIRECTOR.equals(srsStudent.getRequestStatus())
                        || RequestStatus.DENIED_BY_PARENT.equals(srsStudent.getRequestStatus()));
                r.setSchoolName(school.getSchoolName());
                r.setGradeLevel(srsStudent.getGradeLevel());
                r.setGradeLevelDescription(GradeLevelUtils.getDescriptionByGradeLevel(srsStudent.getGradeLevel()));
                r.setStudentName(Utils.toFullName(srsStudent.getFirstName(), srsStudent.getMiddleName(), srsStudent.getLastName()));
                r.setAge(DateUtils.toYears(srsStudent.getDateOfBirth()));
                r.setStatus(srsStudent.getRequestStatus().getDescription());
                r.setMontessori(srsStudent.isMontessori());
                r.setOccupationalEnrollment(srsStudent.getOccupationalEnrollment() > 0);
                // occupational stuff
                if (r.isOccupationalEnrollment()) {
                    r.setPrograms(approvalDao.retrieveProgramsSelectedByPreEnrollmentAndSchool(preEnrollmentId, requestedSchoolId));
                    final OccupationalApprovalEntity occupationalApprovalEntity = occupationalApprovalRepository.findByPreEnrollmentIdAndSchoolId(preEnrollmentId, requestedSchoolId);
                    if (occupationalApprovalEntity != null) {
                        Function<OccupationalApprovalEntity, VocationalProgramSelection> toSelection = new Function<OccupationalApprovalEntity, VocationalProgramSelection>() {
                            @Override
                            public VocationalProgramSelection apply(OccupationalApprovalEntity occupationalApprovalEntity) {
                                VocationalProgramSelection selection = new VocationalProgramSelection();
                                selection.setSchoolId(requestedSchoolId);
                                selection.setProgramCode(occupationalApprovalEntity.getProgramCode());
                                return selection;
                            }
                        };
                        r.setSelectedProgram(toSelection.apply(occupationalApprovalEntity));
                    }
                }


                return r;
            }
        }.apply(srsStudent);
        return result;
    }

    public boolean saveDirectorInput(ApprovalInputRequest inputRequest, AdminUser adminUser) {

        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(inputRequest.getPreEnrollmentId());
        EnrollmentType type;
        RequestStatus status;

        if (inputRequest.isApprove()) {
            type = EnrollmentType.REGULAR_ALTERNATE_SCHOOLS.equals(entity.getType()) ? EnrollmentType.REGULAR : entity.getType();
            status = RequestStatus.APPROVED;

        } else {
            type = EnrollmentType.REGULAR.equals(entity.getType()) ? EnrollmentType.REGULAR_ALTERNATE_SCHOOLS : entity.getType();
            status = RequestStatus.DENIED_BY_DIRECTOR;
        }

        if (inputRequest.isOccupationEnrollment()) {
            type = EnrollmentType.OCCUPATIONAL;
            OccupationalApprovalEntity occupationalApprovalEntity =
                    occupationalApprovalRepository.findByPreEnrollmentId(inputRequest.getPreEnrollmentId());

            if (occupationalApprovalEntity == null) {
                occupationalApprovalEntity = new OccupationalApprovalEntity();
                occupationalApprovalEntity.setPreEnrollmentId(inputRequest.getPreEnrollmentId());
            } else {
                occupationalApprovalEntity.setRevisionDate(new Date());
            }

            occupationalApprovalEntity.setSchoolId(inputRequest.getSchoolId());
            occupationalApprovalEntity.setApproved(inputRequest.isApprove());
            occupationalApprovalEntity.setUserId(adminUser.getUserId());
            occupationalApprovalEntity.setGradeLevel(inputRequest.getGradeLevel());
            occupationalApprovalEntity.setProgramCode(inputRequest.getProgram().getProgramCode());

            occupationalApprovalRepository.save(occupationalApprovalEntity);
        }

        inputRequest.setType(type);
        inputRequest.setRequestStatus(status);
        boolean res = approvalDao.updateRequest(inputRequest, adminUser.getUserId());

        //approval log
        if (res) {
            ApprovalDirectorLogEntity log = new ApprovalDirectorLogEntity();
            log.setSchoolId(inputRequest.getSchoolId());
            log.setApproved(inputRequest.isApprove());
            log.setUserId(adminUser.getUserId());
            log.setGradeLevel(inputRequest.getGradeLevel());
            log.setPreEnrollmentId(inputRequest.getPreEnrollmentId());
            log.setReasonToDenyEnrollment(inputRequest.getReasonToDeny());
            log.setCommentText(inputRequest.getComment());
            approvalDirectorLogRepository.save(log);
        }

        return res;
    }

    public boolean saveStudentMontessoriInfo(StudentMontessoriInfoRequest request, AdminUser adminUser){
        return approvalDao.saveStudentMontessoriInfo(request);
    }


}