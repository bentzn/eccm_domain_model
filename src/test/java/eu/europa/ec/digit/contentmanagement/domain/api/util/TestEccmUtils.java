package eu.europa.ec.digit.contentmanagement.domain.api.util;

import static org.junit.Assert.*;
import org.junit.Test;

import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedListImpl;
import eu.europa.ec.digit.contentmanagement.domain.api.util.collections.PaginatedList_i;

public class TestEccmUtils {

    
    @Test
    public void testScan() throws Exception {
        @SuppressWarnings("rawtypes")
        Class<? extends PaginatedList_i> clazz = EccmUtils.getImplementingClass(PaginatedList_i.class);
        assertNotNull(clazz);
        assertEquals(PaginatedListImpl.class, clazz);
    }
}
