package com.gemini.commons.beans.integration;

import com.gemini.commons.beans.forms.AddressBean;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/22/18
 * Time: 11:42 AM
 */
public class SchoolResponse {

  private Long schoolId;
  private Long extSchoolNumber;
  private String schoolName;
  private String schoolType;
  private String specializedCategory;
  private boolean isVocational;
  private boolean isMontessori;
  private AddressBean address = new AddressBean();
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

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public String getSchoolType() {
    return schoolType;
  }

  public void setSchoolType(String schoolType) {
    this.schoolType = schoolType;
  }

  public String getSpecializedCategory() {
    return specializedCategory;
  }

  public void setSpecializedCategory(String specializedCategory) {
    this.specializedCategory = specializedCategory;
  }

  public boolean isVocational() {
    return isVocational;
  }

  public void setVocational(boolean vocational) {
    isVocational = vocational;
  }

  public boolean isMontessori() {
    return isMontessori;
  }

  public void setMontessori(boolean montessori) {
    isMontessori = montessori;
  }

  public AddressBean getAddress() {
    return address;
  }

  public void setAddress(AddressBean address) {
    this.address = address;
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

  public String getDisplayName() {
    return String.format("%s-%s", extSchoolNumber, schoolName);
  }
}
