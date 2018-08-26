package com.gemini.services;

import com.gemini.beans.internal.SchoolCapacityUpdateRequest;
import com.gemini.beans.internal.SchoolValidationRequest;
import com.gemini.commons.beans.integration.SchoolValidationResponse;
import com.gemini.commons.database.beans.SchoolGradeLimit;
import com.gemini.commons.database.dao.SchoolCapDao;
import com.gemini.commons.utils.CopyUtils;
import com.gemini.commons.database.jpa.entities.SchoolsCapsEntity;
import com.gemini.commons.database.jpa.respository.SchoolCapsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/12/18
 * Time: 12:47 PM
 */
@Service
public class SchoolAvailableSpaceService {

    @Autowired
    private SchoolCapsRepository schoolCapsRepository;
    @Autowired
    private SchoolCapDao schoolCapDao;


    public SchoolValidationResponse hasAvailableSpace(SchoolValidationRequest request) {
        SchoolGradeLimit cap = schoolCapDao.getLimitBySchoolAndGradeLevel(request.getSchoolId(), request.getGradeLevel());
        if(cap == null)
            return new SchoolValidationResponse();
        SchoolValidationResponse response = CopyUtils.convert(cap, SchoolValidationResponse.class);
        if(schoolCapDao.isOvercapacity(request.getSchoolId(), request.getGradeLevel()))
            response.setFull(true);
        return response;
    }

    //    todo: fran erase this later
    /*
            public SchoolValidationResponse hasAvailableSpace(SchoolValidationRequest request) {
                SchoolsCapsEntity cap = schoolCapsRepository.findBySchoolIdAndGradeLevel(request.getSchoolId(), request.getGradeLevel());
                if(cap == null)
                    return new SchoolValidationResponse();
                SchoolValidationResponse response = CopyUtils.convert(cap, SchoolValidationResponse.class);
                if(cap.isFull())
                    response.setFull(true);
                return response;
            }

            public void updateSchoolCap(SchoolCapacityUpdateRequest request){
                SchoolsCapsEntity cap = schoolCapsRepository.findBySchoolIdAndGradeLevel(request.getSchoolId(), request.getGradeLevel());
                if(cap == null)
                    return;
                if(request.getValue() > 0){
                    cap.setTotalAdded(request.getValue() + cap.getTotalAdded());
                }

                if(request.getValue() < 0){
                    cap.setTotalSubtracted(request.getValue() + cap.getTotalSubtracted());
                }
                Integer total = cap.getTotalEnrollmentsByNYE() + cap.getTotalAdded() - cap.getTotalSubtracted();
                cap.setFull(total >= cap.getCapacity());
                schoolCapsRepository.save(cap);
            }
    */
}