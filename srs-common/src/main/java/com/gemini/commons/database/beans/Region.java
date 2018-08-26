package com.gemini.commons.database.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/21/18
 * Time: 4:35 PM
 */
public class Region {

    private Long regionId;
    private String extZoneNumber;
    private String name;
    private String description;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getExtZoneNumber() {
        return extZoneNumber;
    }

    public void setExtZoneNumber(String extZoneNumber) {
        this.extZoneNumber = extZoneNumber;
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
}