package com.gemini.admin;

import com.gemini.commons.beans.types.RequestStatus;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/4/18
 * Time: 6:59 PM
 */
final public class AdminUIHelper {

    public static String getColorCss(RequestStatus requestStatus) {
        String colorCss = "text-info";
        switch (requestStatus) {

            case APPROVED:
                colorCss = "text-success";
                break;
            case DENIED:
            case DENIED_BY_PARENT:
            case DENIED_BY_DIRECTOR:
                colorCss = "text-danger";
                break;
            case PENDING_TO_REVIEW:
                colorCss = "text-warning";
                break;
        }
        return colorCss;
    }
}