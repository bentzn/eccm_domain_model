package eu.europa.ec.digit.contentmanagement.domain.api.entities.user;

import java.util.List;

import eu.europa.ec.digit.contentmanagement.domain.api.entities.AbstractEntity_i;

public interface PrivilegeHolder_i extends AbstractEntity_i {

    List<Privilege_i> getPrivileges(Object id);



    void addPrivilege(Privilege_i privilege, Object id);



    void removePrivilege(Privilege_i privilege, Object id);

}
