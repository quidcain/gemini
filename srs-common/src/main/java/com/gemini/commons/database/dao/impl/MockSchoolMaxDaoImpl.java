package com.gemini.commons.database.dao.impl;

import com.gemini.commons.beans.requests.StudentSearchRequest;
import com.gemini.commons.beans.types.SpecializedSchoolCategory;

import com.gemini.commons.database.beans.*;
import com.gemini.commons.utils.DateUtils;
import com.gemini.commons.utils.ValidationUtils;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/27/18
 * Time: 9:05 AM
 */
public class MockSchoolMaxDaoImpl implements SchoolMaxDaoInterface {
    private final String currentGradeLevel = "01";
    private final String nextGradeLevel = "02";
    private Student studentFound = new Student();


    public MockSchoolMaxDaoImpl() {
    }

    @Override
    public Parent findHouseHead(String lastSsn, Date dob, String lastname) {
        return new Parent();
    }

    @Override
    public Student findStudent(StudentSearchRequest request) {
        studentFound.setFirstName("Pepito");
        studentFound.setLastName(request.getLastName());
        studentFound.setGenderValue("F");
        studentFound.setDateOfBirth(DateUtils.toDate(request.getDateOfBirth()));
        studentFound.setSsn("234-56-2712");
        Random rand = new Random();
        long value = Math.abs(rand.nextLong());
        studentFound.setExtStudentNumber(value);
        studentFound.setStudentId(value);
        if (ValidationUtils.valid(request.getSsn()))
            studentFound.setSsn(request.getSsn().toString());
        return studentFound;
    }

    @Override
    public Student findStudent(Long studentNumber) {
        return studentFound;
    }

    @Override
    public StudentAddress findAddress(Long studentNumber) {
        StudentAddress address = new StudentAddress();
        address.setPhysicalAddress_1("Ave. Tnte. César González,esq. Calle Juan Calaf");
        address.setPhysicalAddress_2("Urb. Industrial Tres Monjitas");
        address.setPhysicalCity("SNJN");
        address.setPhysicalCityDesc("San Juan");
        address.setPhysicalState("PR");
        address.setPhysicalZipcode("00917");
        address.setPostalAddress_1("P.O. Box 190759");
        address.setPostalCity("SNJN");
        address.setPostalCityDesc("San Juan");
        address.setPostalState("PR");
        address.setPostalZipcode("00919");
        return address;
    }

    @Override
    public EnrollmentInfo findRecentStudentEnrollment(Long studentId) {
        EnrollmentInfo enrollmentInfo = new EnrollmentInfo();
        enrollmentInfo.setSchoolYear(2019L);
        enrollmentInfo.setGradeLevel(currentGradeLevel);
        enrollmentInfo.setSchoolId(1L);
        enrollmentInfo.setEnrollmentId(1L);
        enrollmentInfo.setGradeLevel("02");
        return enrollmentInfo;
    }

    @Override
    public List<School> findSchoolsByRegionAndGradeLevel(Long regionId, Long schoolYear, String gradeLevel) {
        final List<String> schoolNames = Arrays.asList("Mejor Aprovechamiento", "Lealtad", "Honestidad", "Persistente", "Resiliente");

        Function<String, School> stringToSchool =
                new Function<String, School>() {
                    public School apply(String schoolName) {
                        School school = new School();
                        Integer schoolId = schoolNames.indexOf(schoolName) + 1;
                        school.setSchoolId(schoolId.longValue());
                        school.setExtSchoolNumber(schoolId.longValue());
                        school.setRegionId(1L);
                        school.setDistrictId(1L);
                        school.setAddressLine_1(String.format("Calle Felicidad #%s", schoolId));
                        school.setAddressLine_2("Urb Progreso");
                        school.setCityCd("");
                        school.setCity("San Juan");
                        school.setState("PR");
                        school.setZipCode("00918");
                        school.setSchoolName(schoolName);
                        return school;
                    }
                };

        return Lists.transform(schoolNames, stringToSchool);

    }

    @Override
    public School findSchoolById(final Long schoolId) {
        return FluentIterable
                .from(findSchoolsByRegionAndGradeLevel(null, null, null))
                .firstMatch(new Predicate<School>() {
                    @Override
                    public boolean apply(School school) {
                        return school.getSchoolId().equals(schoolId);
                    }
                }).orNull();
    }

    @Override
    public List<Region> getAllRegions() {
        final List<String> regionNames = Arrays.asList("Arecibo", "Bayamon", "Caguas", "Humacao", "Mayaguez", "Ponce");
        Function<String, Region> strToRegion = new Function<String, Region>() {
            @Override
            public Region apply(String regionName) {
                Region region = new Region();
                Integer regionId = regionNames.indexOf(regionName) + 1;
                region.setRegionId(regionId.longValue());
                region.setName(regionName);
                region.setDescription(regionName);
                return region;
            }
        };
        return Lists.transform(regionNames, strToRegion);
    }

    @Override
    public SchoolGradeLevel findGradeLevelInfo(Long schoolYear, Long schoolId, String gradeLevel) {
        SchoolGradeLevel schoolGradeLevel = new SchoolGradeLevel();
        schoolGradeLevel.setSchoolId(1L);
        schoolGradeLevel.setSchoolYear(2019L);
        schoolGradeLevel.setNextYearGrade(nextGradeLevel);
        return schoolGradeLevel;
    }

    @Override
    public List<School> findOccupationalSchoolsByRegionAndGradeLevel(Long regionId, Long schoolYear, String gradeLevel) {
        return findSchoolsByRegionAndGradeLevel(regionId, schoolYear, gradeLevel);
    }

    @Override
    public List<School> findSpecializedSchoolsByRegionAndGradeLevel(Long regionId, Long schoolYear, String gradeLevel, SpecializedSchoolCategory category) {
        return findSchoolsByRegionAndGradeLevel(regionId, schoolYear, gradeLevel);
    }

    @Override
    public List<School> findTechnicalSchools(Long schoolYear) {
        return findSchoolsByRegionAndGradeLevel(1L, schoolYear, "00");
    }

    @Override
    public List<School> findSharedSchools(Long schoolYear, String gradeLevel) {
        return findSchoolsByRegionAndGradeLevel(1L, schoolYear, "00");
    }

    @Override
    public School findSharedSchoolById(Long schoolId) {
        return null;
    }

    @Override
    public List<Region> getVocationalRegions() {
        return getAllRegions();
    }

    @Override
    public List<VocationalProgram> getVocationalPrograms(Long schoolId, Long schoolYear) {
        final Map<String, String> samplePrograms = ImmutableMap.of(
                "AARN", "Agricultura, Alimentos y Recursos Naturales",
                "GEAD", "Gerencia y Administración",
                "VNPR", "Ventas Profesionales",
                "FINA", "Finanzas");

        Function<String, VocationalProgram> toPrograms = new Function<String, VocationalProgram>() {
            @Override
            public VocationalProgram apply(String prog) {
                VocationalProgram program = new VocationalProgram();
                program.setCode(prog.substring(0, 4));
                program.setDescription(samplePrograms.get(prog));
                return program;
            }
        };
        return Lists.transform(Lists.newArrayList(samplePrograms.keySet()), toPrograms);
    }

    @Override
    public boolean foundStudentBySsn(String ssn) {
        return false;
    }

    @Override
    public boolean schoolInExclusionList(Long schoolId) {
        return false;
    }

    @Override
    public boolean schoolInMontesorri(Long schoolId) {
        return false;
    }
}
