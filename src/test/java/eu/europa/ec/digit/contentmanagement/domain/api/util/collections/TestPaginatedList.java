package eu.europa.ec.digit.contentmanagement.domain.api.util.collections;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class TestPaginatedList {

    @Test
    public void test() {
        // Empty list
        PaginatedList_i<Integer> lst0 = new PaginatedListImpl<>(0, 10, 20);
        assertEquals(0, lst0.getSkipItems());
        assertEquals(10, lst0.getMaxItems());
        assertEquals(20, lst0.getTotalItems());
        
        List<Integer> lst = new LinkedList<>();
        for (int i = 0; i < 30; i++) {
            lst.add(i);
        }
        
        // Paginated list with pre-paged list
        List<Integer> lstSub = lst.subList(0, 10);
        PaginatedList_i<Integer> lst1 = new PaginatedListImpl<>(lstSub, 0, 10, 200);
        assertEquals(0, lst1.getSkipItems());
        assertEquals(10, lst1.getMaxItems());
        assertEquals(200, lst1.getTotalItems());

        // Paginated list with full list
        PaginatedList_i<Integer> lst2 = new PaginatedListImpl<>(lst, 0, 10);
        assertEquals(0, lst2.getSkipItems());
        assertEquals(10, lst2.getMaxItems());
        assertEquals(30, lst2.getTotalItems());
        assertEquals(10, lst2.size());

        // Paginated list with small full list
        List<Integer> lstSub2 = lst.subList(0, 5);
        PaginatedList_i<Integer> lst3 = new PaginatedListImpl<>(lstSub2, 0, 10);
        assertEquals(0, lst3.getSkipItems());
        assertEquals(10, lst3.getMaxItems());
        assertEquals(5, lst3.getTotalItems());
        assertEquals(5, lst3.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(i, lst3.get(i), .1d);
        }

        // Paginated list with small full list
        List<Integer> lstSubSkip = lst.subList(0, 10);
        PaginatedList_i<Integer> lstSkip = new PaginatedListImpl<>(lstSubSkip, 3, 10);
        assertEquals(3, lstSkip.getSkipItems());
        assertEquals(10, lstSkip.getMaxItems());
        assertEquals(10, lstSkip.getTotalItems());
        assertEquals(7, lstSkip.size());
        for (int i = 0; i < 7; i++) {
            assertEquals(i + 3, lstSkip.get(i), .1d);
        }

        // Paginated list with small full list
        List<Integer> lstSub3 = lst.subList(0, 5);
        PaginatedList_i<Integer> lst4 = new PaginatedListImpl<>(lstSub3, 65, 50);
        assertEquals(65, lst4.getSkipItems());
        assertEquals(50, lst4.getMaxItems());
        assertEquals(5, lst4.getTotalItems());
        assertEquals(0, lst4.size());

        // Paginated list with null
        try {
            new PaginatedListImpl<>(null, 0, 10, 30);
            fail();
        } catch (RuntimeException e) {
            // expected
        }

        // Paginated list with null
        PaginatedList_i<Integer> lst5 = new PaginatedListImpl<>(null, 0, 10);
        assertEquals(0, lst5.getSkipItems());
        assertEquals(10, lst5.getMaxItems());
        assertEquals(0, lst5.getTotalItems());
        assertEquals(0, lst5.size());
    }
}
