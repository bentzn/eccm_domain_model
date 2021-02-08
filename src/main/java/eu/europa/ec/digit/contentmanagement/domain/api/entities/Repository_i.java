package eu.europa.ec.digit.contentmanagement.domain.api.entities;

/**
 * 
 * @author bentsth
 */
public interface Repository_i extends AbstractEntity_i {

    String getDescription();



    void setDescription(String description);



    long getRootFolderId();



    void setRootFolderId(long rootFolderId);
}
