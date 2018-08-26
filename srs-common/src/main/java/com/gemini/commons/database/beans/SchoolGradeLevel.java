package com.gemini.commons.database.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/22/18
 * Time: 10:48 AM
 */
public class SchoolGradeLevel {

  //  NAME, DESCRIPTION, VALUE, SCHOOL_ID, SCHOOL_YEAR, NEXT_YEAR_GRADE
  private Long schoolId;
  private Long schoolYear;
  private String name;
  private String description;
  private String value;
  private String nextYearGrade;

  public Long getSchoolId() {
    return schoolId;
  }

  public void setSchoolId(Long schoolId) {
    this.schoolId = schoolId;
  }

  public Long getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(Long schoolYear) {
    this.schoolYear = schoolYear;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getNextYearGrade() {
    return nextYearGrade;
  }

  public void setNextYearGrade(String nextYearGrade) {
    this.nextYearGrade = nextYearGrade;
  }
}
