package com.gemini.commons.database.beans;

import com.gemini.commons.utils.GradeLevelUtils;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/22/18
 * Time: 11:18 AM
 */
public class GradeLevel {

  private String name;
  private String value;
  private String description;

  public GradeLevel(String name, String description, String value) {
    this.name = name;
    this.description = description;
    this.value = value;
  }

  public GradeLevel() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDisplayName() {
    return String.format("%s-%s", name, description);
  }

  public String getDisplay() {
    return GradeLevelUtils.getDescriptionByGradeLevel(value);
  }

}
