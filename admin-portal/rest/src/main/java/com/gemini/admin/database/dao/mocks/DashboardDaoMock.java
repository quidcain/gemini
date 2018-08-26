package com.gemini.admin.database.dao.mocks;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.CriteriaForm;
import com.gemini.admin.database.dao.DashboardDao;
import com.gemini.admin.database.dao.beans.EnrollmentProgress;
import com.gemini.commons.database.beans.City;
import com.gemini.commons.database.beans.GradeLevel;
import com.gemini.commons.database.beans.Region;
import com.gemini.commons.database.beans.School;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 3:52 PM
 */
@Repository("dashboardDaoMock")
public class DashboardDaoMock implements DashboardDao {

    @Override
    public List<Region> getRegions(AdminUser user) {
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
    public List<City> getCities(Long regionId, AdminUser user) {
        final List<String> cities = Arrays.asList("Arecibo", "Bayamon", "Caguas", "Humacao", "Mayaguez", "Ponce");
        Function<String, City> strToCity = new Function<String, City>() {
            @Override
            public City apply(String cityDesc) {
                City city = new City();
                city.setCityCode(cityDesc.substring(0, 2).toUpperCase());
                city.setCity(cityDesc);
                return city;
            }
        };
        return Lists.transform(cities, strToCity);
    }

    @Override
    public List<School> getSchools(Long regionId, String cityCode, AdminUser user) {
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
    public int getPreEnrollmentBySIETotal(AdminUser user, CriteriaForm criteria) {
        return 999111;
    }

    @Override
    public int getConfirmedTotal(AdminUser user, CriteriaForm criteria) {
        return 324;
    }

    @Override
    public int getNewEntryTotal(AdminUser user, CriteriaForm criteria) {
        return 234;
    }

    @Override
    public int getDeniedTotal(AdminUser user, CriteriaForm criteria) {
        return 234;
    }

    @Override
    public List<EnrollmentProgress> getProgress(AdminUser user, CriteriaForm criteria) {
        int start = 20180401;
        List<EnrollmentProgress> progress = Lists.newArrayList();

        int n = 0;
        do {
            Random rand = new Random();
            EnrollmentProgress p = new EnrollmentProgress();
            p.setActionDate(Integer.toString(start++));
            p.setTotalConfirmed(rand.nextInt(50) + 1);
            p.setTotalDenied(rand.nextInt(50) + 1);
            p.setTotalNewEntryEnrollments(rand.nextInt(50) + 1);
            progress.add(p);
            n++;
        } while (n < 5);
        return progress;
    }

    @Override
    public List<GradeLevel> getGradeLevels(Long schoolId) {
        return null;
    }
}