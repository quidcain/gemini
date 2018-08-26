package com.gemini.utils;

import com.gemini.beans.internal.EmailSchool;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/16/18
 * Time: 8:01 AM
 */
public class EmailSchoolComparator implements Comparator<EmailSchool>{

    @Override
    public int compare(EmailSchool o1, EmailSchool o2) {
        if(o1.getIndex() > o2.getIndex())
            return 1;
        else if(o1.getIndex() < o2.getIndex())
            return -1;
        return 0;
    }
}