package com.gemini.admin.services;

import com.gemini.admin.AdminUIHelper;
import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.Comment;
import com.gemini.admin.beans.forms.EditRequestResult;
import com.gemini.admin.beans.forms.RequestSearchCriteria;
import com.gemini.admin.beans.forms.RequestSearchResult;
import com.gemini.admin.beans.requests.ApprovalCentralRequest;
import com.gemini.admin.beans.requests.ApprovalInputRequest;
import com.gemini.admin.beans.requests.ReactivatePreEnrollmentRequest;
import com.gemini.admin.beans.requests.TransferAccountRequest;
import com.gemini.admin.beans.response.TransferAccountResponse;
import com.gemini.admin.beans.types.ReportType;
import com.gemini.admin.database.dao.ApprovalDao;
import com.gemini.admin.database.dao.PortalUserDao;
import com.gemini.admin.database.dao.RequestResultDao;
import com.gemini.admin.database.dao.SmaxUserDao;
import com.gemini.admin.database.dao.beans.RequestResult;
import com.gemini.admin.database.dao.beans.SieUser;
import com.gemini.admin.database.jpa.entities.CommentEntity;
import com.gemini.admin.database.jpa.repositories.CommentRepository;
import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.beans.types.EnrollmentType;
import com.gemini.commons.beans.types.EntryType;
import com.gemini.commons.beans.types.RequestStatus;
import com.gemini.commons.database.beans.EnrollmentInfo;
import com.gemini.commons.database.beans.School;
import com.gemini.commons.database.beans.Student;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import com.gemini.commons.database.jpa.entities.*;
import com.gemini.commons.database.jpa.respository.PreEnrollmentRepository;
import com.gemini.commons.database.jpa.respository.UserRepository;
import com.gemini.commons.utils.CopyUtils;
import com.gemini.commons.utils.GradeLevelUtils;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/23/18
 * Time: 5:36 AM
 */
@Service
public class RequestService {

    @Autowired
    private PreEnrollmentRepository preEnrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SchoolMaxDaoInterface smaxInterface;

    @Autowired
    private RequestResultDao requestResultDao;

    @Autowired
    private ApprovalDao approvalDao;

    @Autowired
    private PortalUserDao portalUserDao;

    //    todo: fran refractor this please!!!!
    @Autowired
    @Qualifier("smaxUserDao")
    private SmaxUserDao userDao;

    public List<RequestSearchResult> searchRequest(RequestSearchCriteria criteria) {
        List<PreEnrollmentRequestEntity> entities = preEnrollmentRepository.findByStudentNumberOrSsnOrEmail(criteria.getStudentNumber(), criteria.getSsn(), criteria.getEmail());
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        List<RequestSearchResult> results = FluentIterable
                .from(entities)
                .transform(new Function<PreEnrollmentRequestEntity, RequestSearchResult>() {
                    @Override
                    public RequestSearchResult apply(PreEnrollmentRequestEntity entity) {
                        StudentEntity student = entity.getStudent();
                        UserEntity userEntity = userRepository.findByPreEnrollmentId(entity.getId());
                        RequestSearchResult searchResult = new RequestSearchResult();
                        searchResult.setEmail(userEntity.getEmail());
                        searchResult.setSubmitDate(entity.getSubmitDate());
                        searchResult.setRequestId(entity.getId());
                        searchResult.setStudentName(Utils.toFullName(student.getFirstName(), student.getMiddleName(), student.getLastName()));
                        searchResult.setCreationDate(entity.getSubmitDate());
                        searchResult.setStatus(entity.getRequestStatus().getDescription());
                        searchResult.setCssStatusColor(AdminUIHelper.getColorCss(entity.getRequestStatus()));
                        searchResult.setGradeLevel(GradeLevelUtils.getDescriptionByGradeLevel(entity.getGradeLevel()));
                        searchResult.setSieStudentId(student.getSisStudentId() < 0L ? "Nuevo Ingreso" : student.getSisStudentId().toString());

                        //no se puede apobar solicitudes con status de aprobada o activa ni matriculas ocupacionales
                        RequestStatus status = entity.getRequestStatus();
                        searchResult.setCanApprove(!RequestStatus.APPROVED.equals(status)
                                && !RequestStatus.ACTIVE.equals(status)
                                && !EnrollmentType.OCCUPATIONAL.equals(entity.getType())
                                && EntryType.NEW.equals(student.getEntryType())
                        );


                        List<School> schools = new ArrayList<>();
                        if (ValidationUtils.valid(entity.getPreEnrollmentId()))
                            schools.add(userDao.getSchoolById(entity.getSchoolId()));
                        if (entity.getAlternateSchools() != null && entity.getAlternateSchools().size() > 0) {
                            List<PreEnrollmentAlternateSchoolEntity> alternateSchoolEntities = sort(entity);
                            schools.addAll(FluentIterable
                                    .from(alternateSchoolEntities)
                                    .transform(new Function<PreEnrollmentAlternateSchoolEntity, School>() {
                                        @Override
                                        public School apply(PreEnrollmentAlternateSchoolEntity altEntity) {
                                            Long schoolId = altEntity.getSchoolId();
                                            return userDao.getSchoolById(schoolId);
                                        }
                                    })
                                    .toList());
                        }

                        searchResult.setSchoolsSelected(CopyUtils.convert(schools, SchoolResponse.class));
                        return searchResult;
                    }
                })
                .toList();
        return results;
    }

    public EditRequestResult getRequest(final Long preEnrollmentId) {
        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(preEnrollmentId);
        if (entity == null) {
            return null;
        }

        EditRequestResult result = FluentIterable
                .from(Arrays.asList(entity))
                .transform(new Function<PreEnrollmentRequestEntity, EditRequestResult>() {
                    @Override
                    public EditRequestResult apply(PreEnrollmentRequestEntity entity) {
                        EditRequestResult result = new EditRequestResult();
                        StudentEntity student = entity.getStudent();
                        UserEntity userEntity = userRepository.findByPreEnrollmentId(entity.getId());
                        result.setEmail(userEntity.getEmail());
                        result.setDateOfBirth(student.getDateOfBirth());
                        result.setGradeLevel(entity.getGradeLevel());
                        result.setSieNumber(student.getSisStudentId());
                        result.setFatherName(Utils.toFullName(userEntity.getFirstName(), userEntity.getMiddleName(), userEntity.getLastName()));
                        result.setRequestId(entity.getId());
                        result.setStudentName(Utils.toFullName(student.getFirstName(), student.getMiddleName(), student.getLastName()));
                        result.setSubmitDate(entity.getSubmitDate());
                        result.setStatus(entity.getRequestStatus().getDescription());
                        result.setCssStatusColor(AdminUIHelper.getColorCss(entity.getRequestStatus()));

                        ReportType type = null;
                        if (EntryType.NEW.equals(student.getEntryType()))
                            type = ReportType.NEW_ENTRY_ENROLLMENT;
                        else if (EnrollmentType.REGULAR.equals(entity.getType()) && entity.getSubmitDate() != null)
                            type = ReportType.CONFIRMED;
                        else if (EnrollmentType.REGULAR.equals(entity.getType()) && entity.getSubmitDate() == null)
                            type = ReportType.PENDING_ENROLLMENT;
                        else if (EnrollmentType.REGULAR_ALTERNATE_SCHOOLS.equals(entity.getType()))
                            type = ReportType.ALTERNATE_ENROLLMENT;
                        else if (EnrollmentType.OCCUPATIONAL.equals(entity.getType()))
                            type = ReportType.VOCATIONAL_ENROLLMENT;

                        result.setType(entity.getType());
                        result.setSummaryCount(type);


                        List<School> schools = new ArrayList<>();
                        if (ValidationUtils.valid(entity.getResultSchoolId())) {
                            schools.add(userDao.getSchoolById(entity.getResultSchoolId()));
                        } else {

                            if (EnrollmentType.REGULAR.equals(entity.getType()))
                                schools.add(userDao.getSchoolById(entity.getSchoolId()));
                            else if (EnrollmentType.REGULAR_ALTERNATE_SCHOOLS.equals(entity.getType())) {
                                List<PreEnrollmentAlternateSchoolEntity> alternateSchoolEntities = sort(entity);

                                schools = FluentIterable
                                        .from(alternateSchoolEntities)
                                        .transform(new Function<PreEnrollmentAlternateSchoolEntity, School>() {
                                            @Override
                                            public School apply(PreEnrollmentAlternateSchoolEntity altEntity) {
                                                Long schoolId = altEntity.getSchoolId();
                                                return userDao.getSchoolById(schoolId);
                                            }
                                        })
                                        .toList();
                            } else if (EnrollmentType.OCCUPATIONAL.equals(entity.getType())) {
                                schools = FluentIterable
                                        .from(entity.getVocationalSchools())
                                        .transform(new Function<PreEnrollmentVocationalSchoolEntity, School>() {
                                            @Override
                                            public School apply(PreEnrollmentVocationalSchoolEntity vocEntity) {
                                                Long schoolId = vocEntity.getSchoolId();
                                                return userDao.getSchoolById(schoolId);
                                            }
                                        })
                                        .toList();
                            }
                        }
                        result.setSchoolLabel(schools != null && !schools.isEmpty() && schools.size() > 1 ? "Escuelas Seleccionadas:" : "Escuela :");
                        result.setSchoolsSelected(schools);
                        return result;
                    }
                })
                .first()
                .get();

        RequestResult requestResult = requestResultDao.getRequestResult(preEnrollmentId);
        if (requestResult != null) {

            String responseType = requestResult.getRequestResult().replaceAll("_", " ").toLowerCase();
            responseType = StringUtils.capitalize(responseType);
            String responseText = String.format("%s - %s (%s)", requestResult.getExtSchoolNumber(), requestResult.getSchoolName(), responseType);

            result.setEmail(requestResult.getEmail());
            result.setResultSchool(responseText);
            result.setEmailSentDate(requestResult.getSentDate());
        } else {
            result.setResultSchool("Sin evaluar");
        }
        return result;
    }

    public List<Comment> getRequestComments(Long preEnrollmentId) {
        List<CommentEntity> commentEntities = commentRepository.getAllByReferenceIdOrderByCreationDate(preEnrollmentId);
        if (commentEntities == null || commentEntities.isEmpty()) {
            return null;
        }

        List<Comment> comments = FluentIterable
                .from(commentEntities)
                .transform(new Function<CommentEntity, Comment>() {
                    @Override
                    public Comment apply(CommentEntity commentEntity) {
                        Comment comment = new Comment();
                        SieUser user = userDao.findByUserId(commentEntity.getUserId());
                        comment.setUser(user.getUsername());
                        comment.setCreationDate(commentEntity.getCreationDate());
                        comment.setComment(commentEntity.getCommentText());
                        return comment;
                    }
                })
                .toList();


        return comments;
    }

    @Transactional
    public boolean approveRequest(ApprovalCentralRequest request, AdminUser user) {
        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(request.getRequestId());

        if (entity != null && ValidationUtils.valid(request.getSchoolId())) {
            ApprovalInputRequest inputRequest = new ApprovalInputRequest();
            inputRequest.setApprove(true);
            inputRequest.setType(EnrollmentType.REGULAR);
            inputRequest.setRequestStatus(RequestStatus.APPROVED);
            inputRequest.setSchoolId(request.getSchoolId());
            inputRequest.setPreEnrollmentId(request.getRequestId());

            saveComment(String.format("APPROVED BY %s", user.getUserId()), entity, user);

            return approvalDao.updateRequest(inputRequest, user.getUserId());
        }

        return false;
    }

    @Transactional
    public TransferAccountResponse transferRequest(TransferAccountRequest request, AdminUser user) {
        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(request.getRequestId());
        UserEntity fromUser = userRepository.findByEmail(request.getEmail());
        UserEntity toUser = userRepository.findByEmail(request.getEmailNew());

        if (fromUser != null && toUser != null && entity != null) {
            saveComment(String.format("REQUEST %s TRANSFERRED FROM %s to %s", entity.getId(), fromUser.getId(), toUser.getId()), entity, user);
            portalUserDao.transferRequestToUser(toUser.getId(), entity.getId());
        } else {
            String errorMsg = "";
            if (fromUser == null)
                errorMsg = "Cuenta de usuario origen no existe ";

            if (toUser == null)
                errorMsg = "Cuenta de usuario destino no existe ";

            if (request == null)
                errorMsg = "Solicitud no existe ";

            return TransferAccountResponse.error(errorMsg);
        }

        return TransferAccountResponse.success();
    }

    @Transactional
    public boolean reactivatePreEnrollment(ReactivatePreEnrollmentRequest request, AdminUser user) {
        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(request.getRequestId());
        StudentEntity studentEntity = entity.getStudent();

        if (ValidationUtils.valid(studentEntity.getSisStudentId())) {
            EnrollmentInfo info = smaxInterface.findRecentStudentEnrollment(studentEntity.getSisStudentId());
            Student student = smaxInterface.findStudent(studentEntity.getSisStudentId());
            if (info != null) {
                Long schoolId = info.getSchoolId();
                School school = smaxInterface.findSchoolById(schoolId);
                entity.setPreEnrollmentId(info.getEnrollmentId());
                if (school != null) {
                    entity.setSchoolId(schoolId);
                    entity.setDistrictId(school.getDistrictId());
                    entity.setRegionId(school.getRegionId());
                    entity.setExtSchoolNumber(school.getExtSchoolNumber());
                    entity.setMunicipalityCode(school.getCityCd());
                }
                entity.setGradeLevel(info.getGradeLevel());
            }

            if (student != null) {
                studentEntity.setSsn(student.getSsn());
            }
        }

        entity.setSubmitDate(null);
        entity.setRequestStatus(RequestStatus.ACTIVE);
        entity.setRevisionDate(new Date());
        entity.setRevisionUserId(user.getUserId());
        entity.setResultSchoolId(null);
        if (request.getType() != null)
            entity.setType(request.getType());

        entity = preEnrollmentRepository.save(entity);

        saveComment(request.getComment(), entity, user);

        return entity != null;

    }

    @Transactional
    public boolean deactivatePreEnrollment(ReactivatePreEnrollmentRequest request, AdminUser user) {
        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(request.getRequestId());
        entity.setActiveInd(false);
        entity = preEnrollmentRepository.save(entity);

        saveComment(request.getComment(), entity, user);
        return entity != null;

    }

    private void saveComment(String comment, PreEnrollmentRequestEntity entity, AdminUser user) {
        if (StringUtils.hasText(comment)) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setReferenceId(entity.getId());
            commentEntity.setUserId(user.getUserId());
            commentEntity.setCommentText(comment);
            commentRepository.save(commentEntity);
        }
    }

    private List<PreEnrollmentAlternateSchoolEntity> sort(PreEnrollmentRequestEntity entity) {

        return FluentIterable
                .from(entity.getAlternateSchools())
                .toSortedList(new Comparator<PreEnrollmentAlternateSchoolEntity>() {
                    @Override
                    public int compare(PreEnrollmentAlternateSchoolEntity o1, PreEnrollmentAlternateSchoolEntity o2) {
                        if (o1.getPriority() > o2.getPriority())
                            return 1;
                        else if (o1.getPriority() < o2.getPriority())
                            return -1;
                        return 0;
                    }
                });
    }


}