package eu.europa.ec.digit.contentmanagement.domain.api.entities;

import java.util.List;

/**
 * Top level interface for folders, files, documents, attachments
 * 
 * @author bentsth
 */
public interface Artifact_i extends AbstractRepositoryObject_i {

    BaseType getBaseType();



    void setBaseType(BaseType baseTypeDefinition);



    List<Artifact_i> getParents();



    void setParents(List<Artifact_i> parents);



    void addParent(Artifact_i parent);

}
