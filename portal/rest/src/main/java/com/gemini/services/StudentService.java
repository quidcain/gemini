package com.gemini.services;

import com.gemini.beans.forms.StudentScheduleForm;
import com.gemini.beans.requests.StudentAnswerAgreementRequest;
import com.gemini.commons.beans.forms.EthnicCodeBean;
import com.gemini.commons.beans.forms.StudentDemographicsBean;
import com.gemini.beans.requests.StudentAnswerRequest;
import com.gemini.beans.requests.StudentDemographicsRequest;
import com.gemini.commons.database.beans.StudentScheduleView;
import com.gemini.commons.database.jpa.entities.EthnicCodeEntity;
import com.gemini.commons.database.jpa.entities.PreEnrollmentRequestEntity;
import com.gemini.commons.database.jpa.entities.StudentEntity;
import com.gemini.commons.database.jpa.respository.PreEnrollmentRepository;
import com.gemini.commons.database.jpa.respository.StudentRepository;
import com.gemini.commons.utils.CopyUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/27/18
 * Time: 11:45 PM
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PreEnrollmentRepository preEnrollmentRepository;
    @Autowired
    private SchoolmaxService schoolmaxService;

    @Transactional
    public StudentDemographicsBean retrieveDemographicsInfo(Long studentId) {
        final StudentEntity entity = studentRepository.findOne(studentId);
        StudentDemographicsBean demographicsBean = CopyUtils.convert(entity, StudentDemographicsBean.class);
        List<EthnicCodeBean> ethnicCodeBeans = CopyUtils.convert(entity.getEthnicCodes(), EthnicCodeBean.class);
        demographicsBean.setEthnicCodes(ethnicCodeBeans);
        return demographicsBean;
    }

    @Transactional
    public boolean saveDemographicsInfo(StudentDemographicsRequest request) {
        StudentEntity entity = studentRepository.findOne(request.getStudentId());
        entity.setCitizenship(request.getCitizenship());
        entity.setLanguage(request.getLanguage());
        List<EthnicCodeEntity> ethnicCodeDB = entity.getEthnicCodes();
        List<EthnicCodeBean> ethnicCodeBeanList = CopyUtils.convert(ethnicCodeDB, EthnicCodeBean.class);
        Set<EthnicCodeBean> ethnicCodesFormInDb = new HashSet<>(ethnicCodeBeanList);
        Set<EthnicCodeBean> ethnicCodesToSave = Sets.newHashSet();


        boolean deleting = request.getEthnicCodesToDelete() != null && !request.getEthnicCodesToDelete().isEmpty();
        boolean adding = request.getEthnicCodes() != null && !request.getEthnicCodes().isEmpty();
        //deleting
        if (deleting) {
            ethnicCodesToSave = Sets.difference(ethnicCodesFormInDb, Sets.newHashSet(request.getEthnicCodesToDelete()));
            ethnicCodesFormInDb = ethnicCodesToSave;
        }
        //adding
        if (adding)
            ethnicCodesToSave = Sets.union(ethnicCodesFormInDb, new HashSet<>(request.getEthnicCodes()));

        if (!(adding || deleting)) {
            ethnicCodesToSave = ethnicCodesFormInDb;
        }

        List<EthnicCodeEntity> toSave = FluentIterable
                .from(Lists.newArrayList(ethnicCodesToSave))
                .transform(new Function<EthnicCodeBean, EthnicCodeEntity>() {
                    @Override
                    public EthnicCodeEntity apply(EthnicCodeBean bean) {
                        return CopyUtils.convert(bean, EthnicCodeEntity.class);
                    }
                })
                .toList();

        entity.getEthnicCodes().clear();
        entity.getEthnicCodes().addAll(toSave);

        entity = studentRepository.save(entity);
        return entity != null;
    }

    @Transactional
    public boolean saveIsBornPR(StudentAnswerRequest request) {
        StudentEntity entity = studentRepository.findOne(request.getStudentId());
        entity.setBornPR(request.getAnswer());
        entity = studentRepository.save(entity);
        return entity != null;
    }

    @Transactional
    public boolean saveIsHispanic(StudentAnswerRequest request) {
        StudentEntity entity = studentRepository.findOne(request.getStudentId());
        entity.setHispanic(request.getAnswer());
        entity = studentRepository.save(entity);
        return entity != null;
    }

    @Transactional
    public boolean saveRequestTransportation(StudentAnswerRequest request) {
        StudentEntity entity = studentRepository.findOne(request.getStudentId());
        entity.setTransportationRequested(request.getAnswer());
        entity = studentRepository.save(entity);
        return entity != null;
    }

    @Transactional
    public boolean saveRequestTransportation(Boolean answer, Long preEnrollmentId) {

        PreEnrollmentRequestEntity entity = preEnrollmentRepository.findOne(preEnrollmentId);
        StudentEntity studentEntity = entity.getStudent();
        studentEntity.setTransportationRequested(answer == null ? false : answer);
        studentEntity = studentRepository.save(studentEntity);

        return studentEntity != null;
    }

    @Transactional
    public boolean saveAgreementAnswer(StudentAnswerAgreementRequest request) {
        StudentEntity entity = studentRepository.findOne(request.getStudentId());
        entity.setAuthorization_365(request.getAnswer());
        entity = studentRepository.save(entity);
        return entity != null;
    }

    public List<StudentScheduleForm> getStudentScheduleView(Long studentId){
        List<StudentScheduleView> row = schoolmaxService.getStudentScheduleView(studentId);
        return CopyUtils.convert(row, StudentScheduleForm.class);
    }
}