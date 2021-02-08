package eu.europa.ec.digit.contentmanagement.domain.api.util.collections;

import java.util.List;

/**
 * 
 * @author bentsth
 */
public interface PaginatedList_i<T> extends List<T> {

    int getSkipItems();


    int getMaxItems();


    long getTotalItems();

}
