package com.gemini.commons.database.dao;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/21/18
 * Time: 5:34 PM
 */
public interface AddressHelperDao {

    boolean isPRZipcode(String zipcode);

    String getCountyCode(String zipcode);
}