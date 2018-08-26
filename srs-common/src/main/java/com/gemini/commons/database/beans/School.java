package com.gemini.commons.database.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/21/18
 * Time: 9:38 PM
 */
public class School {

    private Long schoolId;
    private Long extSchoolNumber;
    private Long districtId;
    private String districtName;
    private Long regionId;
    private String regionName;
    private String schoolName;
    private String schoolTypeCd;
    private String schoolType;
    private String specializedCategoryCode;
    private String specializedCategory;
    private Integer isVocational;
    private Integer isMontessori;
    private String addressLine_1;
    private String addressLine_2;
    private String cityCd;
    private String city;
    private String country;
    private String state;
    private String zipCode;
    private String email;
    private String phone;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getExtSchoolNumber() {
        return extSchoolNumber;
    }

    public void setExtSchoolNumber(Long extSchoolNumber) {
        this.extSchoolNumber = extSchoolNumber;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolTypeCd() {
        return schoolTypeCd;
    }

    public void setSchoolTypeCd(String schoolTypeCd) {
        this.schoolTypeCd = schoolTypeCd;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSpecializedCategoryCode() {
        return specializedCategoryCode;
    }

    public void setSpecializedCategoryCode(String specializedCategoryCode) {
        this.specializedCategoryCode = specializedCategoryCode;
    }

    public String getSpecializedCategory() {
        return specializedCategory;
    }

    public void setSpecializedCategory(String specializedCategory) {
        this.specializedCategory = specializedCategory;
    }

    public boolean IsVocational() {
        return isVocational != null && isVocational > 0;
    }

    public Integer getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Integer isVocational) {
        this.isVocational = isVocational;
    }

    public boolean isMontessori() {
        return isMontessori != null && isMontessori > 0;
    }

    public Integer getIsMontessori() {
        return isMontessori;
    }

    public void setIsMontessori(Integer isMontessori) {
        this.isMontessori = isMontessori;
    }

    public String getAddressLine_1() {
        return addressLine_1;
    }

    public void setAddressLine_1(String addressLine_1) {
        this.addressLine_1 = addressLine_1;
    }

    public String getAddressLine_2() {
        return addressLine_2;
    }

    public void setAddressLine_2(String addressLine_2) {
        this.addressLine_2 = addressLine_2;
    }

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
