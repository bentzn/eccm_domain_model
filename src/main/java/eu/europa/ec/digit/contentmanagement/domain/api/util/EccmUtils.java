package eu.europa.ec.digit.contentmanagement.domain.api.util;

import static eu.europa.ec.digit.contentmanagement.domain.api.EccmDomainConstants.*;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.*;

import org.reflections.Reflections;

/**
 * 
 * @author bentsth
 */
public class EccmUtils {

    private static Properties eccmProps;

    public static Properties readEccmPropsFromClasspath() throws IOException {
        if (eccmProps == null) {
            synchronized (EccmUtils.class) {
                if (eccmProps == null) {
                    InputStream input = null;
                    try {
                        input = EccmUtils.class.getResourceAsStream("/" + PROPERTIES_FILENAME);

                        eccmProps = new Properties();
                        eccmProps.load(input);
                    }
                    finally {
                        if (input != null)
                            input.close();
                    }
                }
            }
        }

        return eccmProps;
    }



    public static void deleteAllFiles(String pathToDir) {
        deleteAllFiles(new File(pathToDir));
    }



    public static void deleteAllFiles(File dir) {
        if (dir == null || !dir.exists())
            return;

        for (File file : dir.listFiles()) {
            if (file.isDirectory())
                deleteAllFiles(file);
            else
                file.delete();
        }

        dir.delete();
    }



    public static <T> Class<? extends T> getImplementingClass(Class<T> clazz) throws Exception {
        Reflections reflections = new Reflections("eu", "com", "dk");
        Set<Class<? extends T>> set = reflections.getSubTypesOf(clazz);
        for (Class<? extends T> classSub : set) {
            if (!Modifier.isAbstract(classSub.getModifiers()))
                return classSub;
        }

        return null;
    }
}
