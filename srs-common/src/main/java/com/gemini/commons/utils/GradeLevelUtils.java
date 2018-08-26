package com.gemini.commons.utils;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 9:10 PM
 */
public final class GradeLevelUtils {
    public static Map<String, String> gradeLevels = new TreeMap<>();

    static {
        gradeLevels.put("INF", "Infantes");
        gradeLevels.put("AND", "Andarín");
        gradeLevels.put("PKM", "Pre-Kinder Montessori");
        gradeLevels.put("PKE", "Educación Especial- Pre-Kinder");

        gradeLevels.put("PK", "Pre-Kinder");
        gradeLevels.put("KG", "Kindergarten");
        gradeLevels.put("01", "Primero");
        gradeLevels.put("02", "Segundo");
        gradeLevels.put("03", "Tercero");
        gradeLevels.put("04", "Cuarto");
        gradeLevels.put("05", "Quinto");
        gradeLevels.put("06", "Sexto");
        gradeLevels.put("07", "Septimo");
        gradeLevels.put("08", "Octavo");
        gradeLevels.put("09", "Noveno");
        gradeLevels.put("10", "Decimo");
        gradeLevels.put("11", "Undécimo");
        gradeLevels.put("12", "Duodécimo");

//        Educacion Especial
        gradeLevels.put("EE", "Educación Especial - Elemental");
        gradeLevels.put("EI", "Educación Especial - Intermedia");
        gradeLevels.put("ES", "Educación Especial - Superior");

    }

    public static String getDescriptionByGradeLevel(String gradeLevel) {
        if (StringUtils.hasText(gradeLevels.get(gradeLevel)))
            return String.format("%s - %s", gradeLevel, gradeLevels.get(gradeLevel));
        return null;
    }
}