package eu.europa.ec.digit.contentmanagement.domain.api.entities;

/**
 * 
 * @author bentsth
 */
public interface TypeDefinition_i extends AbstractRepositoryObject_i {

    String getDisplayName();



    void setDisplayName(String displayName);



    String getDescription();



    void setDescription(String description);



    BaseType getBaseType();



    void setBaseType(BaseType baseType);



    TypeDefinition_i getParentTypeDefinition();



    void setParentTypeDefinition(TypeDefinition_i parentTypeDefinition);



    boolean isCreatable();



    void setCreatable(boolean creatable);



    boolean isFileable();



    void setFileable(boolean fileable);



    boolean isQueryable();



    void setQueryable(boolean queryable);



    boolean isFulltextIndexed();



    void setFulltextIndexed(boolean fulltextIndexed);



    boolean isIncludedInSupertypeQuery();



    void setIncludedInSupertypeQuery(boolean includedInSupertypeQuery);



    boolean isControllablePolicy();



    void setControllablePolicy(boolean controllablePolicy);



    boolean isControllableAcl();



    void setControllableAcl(boolean controllableAcl);



    boolean canCreateSubType();



    void setCanCreateSubType(boolean canCreateSubType);



    boolean canUpdateType();



    void setCanUpdateType(boolean canUpdateType);



    boolean canDeleteType();



    void setCanDeleteType(boolean canDeleteType);

}