package org.kubernetesbigdataeg.tamer.resources.userRole;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.kubernetesbigdataeg.tamer.entities.userRole.UserRole;
import org.kubernetesbigdataeg.tamer.entities.userRole.UserRoleRepository;
import org.kubernetesbigdataeg.tamer.entities.userRole.UserRoleResponse;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/v1/userrole")
@Tag(name = "User Role Resource", description = "Gets user role information")
public class UserRoleResource {

    @Inject
    UserRoleRepository userrolerepository;

    @GET
    @RolesAllowed({ "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists or password is not valid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public UserRoleResponse listUserRoles() throws InterruptedException {
        List<UserRole> lr = userrolerepository.listAll();

        return new UserRoleResponse(lr);
    }
}

