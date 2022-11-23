package org.kubernetesbigdataeg.tamer.entities.userRole;

import java.util.ArrayList;
import java.util.List;


public class UserRoleResponse {
    private final List<String> roles;

    public UserRoleResponse(List<UserRole> roleList) {
        this.roles= new ArrayList<String>();

        roleList.forEach((item) -> {
            setRoles(item.getName());
        });
    }

    public void setRoles(String name) {
        this.roles.add(name);
    }

    // The name of the method: getTHENAME it's the
    // key of the JSON, the return te values.
    public List<String> getRoles() {
        return roles;
    }
}
