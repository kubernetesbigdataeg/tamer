package org.kubernetesbigdataeg.tamer.resources.version;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

@Path("/v1/version")
@Tag(name = "Version Resource", description = "Gets Server Version information")
public class VersionResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String version() {
        return "Tamer Application v0.1.0";
    }

    @RequestScoped
    @Path("/extended")
    @GET
    @RolesAllowed({ "admin" })
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists or password is not valid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public String versionSecured() {
        String declared = System.getenv("MOCK");
        if (Objects.equals(declared, "true")) {
            return "Tamer Application v0.1.0 (restricted) (mocked)";
        } else {
            return "Tamer Application v0.1.0 (restricted)";
        }
    }
}
