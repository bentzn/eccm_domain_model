package eu.europa.ec.digit.contentmanagement.domain.api.util.collections;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import eu.europa.ec.digit.contentmanagement.domain.api.util.ClasspathScanner;

public class TestClassPathScanner {

    @Test
    public void test() {
        List<Class<?>> lst = ClasspathScanner.getAllKnownClasses("java", "javax", "javassist", "com.google", "org.apache");
        assertTrue(lst.size() > 0);
    }
}
