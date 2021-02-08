package eu.europa.ec.digit.contentmanagement.domain.api.util.collections;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author bentsth
 */
public class PaginatedListImpl<T> extends LinkedList<T> implements PaginatedList_i<T> {

    private int skipItems;
    private int maxItems;
    private long totalItems;

    public PaginatedListImpl(int skipItems, int maxItems, long totalItems) {
        this.skipItems = skipItems;
        this.maxItems = maxItems;
        this.totalItems = totalItems;
    }



    /**
     * Constructs a paginated list based on the collection<br>
     * The collection is expected to be the full collection and it will be limited
     * based on skipItems and maxItems
     * 
     * @param coll
     * @param skipItems
     * @param maxItems
     */
    public PaginatedListImpl(Collection<T> coll, int skipItems, int maxItems) {
        checkSkipAndMax(skipItems, maxItems);

        if (coll != null) {
            addAll(new LinkedList<>(coll).subList(Math.min(skipItems, coll.size()), Math.min(skipItems + maxItems, coll.size())));
            this.totalItems = coll.size();
        }
        this.skipItems = skipItems;
        this.maxItems = maxItems;
    }



    /**
     * Constructs a paginated list based on the collection<br>
     * The collection is expected to already be limited with skipItems and maxItems
     * 
     * @param coll
     * @param skipItems
     * @param maxItems
     * @param totalItems
     */
    public PaginatedListImpl(Collection<T> coll, int skipItems, int maxItems, long totalItems) {
        checkSkipAndMax(skipItems, maxItems);

        if (coll == null) {
            if (totalItems > 0)
                throw new RuntimeException("Can't construct a paginated list with size() = 0 and totalSize > 0");
        }
        else {
            addAll(coll);

            if (coll.size() > maxItems)
                throw new RuntimeException("Can't construct a paginated list with size() > maxItems with this constructor. "
                        + "Use PaginatedListImpl(Collection<T> coll, int skipItems, int maxItems) instead. Size: " + coll.size() + ", max items: " + maxItems);

            if (coll.size() > totalItems)
                throw new RuntimeException("Can't construct a paginated list with size() > totalItems. Size: " + coll.size() + ", total items: " + totalItems);
        }

        this.skipItems = skipItems;
        this.maxItems = maxItems;
        this.totalItems = totalItems;
    }



    private void checkSkipAndMax(int skipItems, int maxItems) {
        if (skipItems < 0)
            throw new RuntimeException("Can't construct a paginated list with skipItems < 0");

        if (maxItems <= 0)
            throw new RuntimeException("Can't construct a paginated list with maxItems <= 0");
    }



    public int getSkipItems() {
        return skipItems;
    }



    public int getMaxItems() {
        return maxItems;
    }



    public long getTotalItems() {
        return totalItems;
    }

}
