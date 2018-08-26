package com.gemini.commons.utils;

import com.gemini.commons.beans.forms.AddressBean;
import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.database.beans.School;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/12/18
 * Time: 2:48 PM
 */
public final class CopyUtils {

    public static <T, R> R convert(T object, Class<R> clazz) {
        try {
            R convertObject = clazz.newInstance();
            if (object != null)
                BeanUtils.copyProperties(object, convertObject, clazz);
            return convertObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T, R> List<R> convert(List<T> object, Class<R> clazz) {
        try {
            List<R> list = new ArrayList<>();
            for (T t : object) {
                R dest = convert(t, clazz);
                list.add(dest);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SchoolResponse createSchoolResponse(School school) {
        SchoolResponse response = CopyUtils.convert(school, SchoolResponse.class);
        response.setAddress(CopyUtils.createAddressBean(school));
        return response;
    }

    public static AddressBean createAddressBean(School school) {
        AddressBean addressBean = new AddressBean();
        addressBean.setLine1(school.getAddressLine_1());
        addressBean.setLine2(school.getAddressLine_2());
        addressBean.setCity(school.getCity());
        addressBean.setZipcode(school.getZipCode());
        return addressBean;
    }

}