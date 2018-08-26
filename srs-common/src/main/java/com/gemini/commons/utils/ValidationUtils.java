package com.gemini.commons.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 11:18 PM
 */
public final class ValidationUtils {

    public static boolean valid(Object... objects) {
        boolean valid = true;
        for (Object o : objects) {
            if (o == null)
                return false;
            if (o instanceof String)
                valid &= StringUtils.hasText((String) o);
            else if (o instanceof Long)
                valid &= ((Long) o) > 0L;
            else if (o instanceof Integer)
                valid &= ((Integer) o) > 0L;

        }
        return valid;
    }

    public static Date validDate(String dateParam) {
        Date date = null;
        try {
            if (StringUtils.hasLength(dateParam)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                date = sdf.parse(dateParam);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}