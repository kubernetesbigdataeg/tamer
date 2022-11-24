package org.kubernetesbigdataeg.tamer.resources.access;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.kubernetesbigdataeg.tamer.entities.access.Login;
import org.kubernetesbigdataeg.tamer.entities.access.LoginInfoResponse;
import org.kubernetesbigdataeg.tamer.entities.access.LoginTokenResponse;
import org.kubernetesbigdataeg.tamer.entities.access.LogoutResponse;
import org.kubernetesbigdataeg.tamer.entities.user.User;
import org.kubernetesbigdataeg.tamer.entities.user.UsersRepository;
import org.kubernetesbigdataeg.tamer.utils.Tools;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Path("/v1/access")
@Tag(name = "Access Resource", description = "User authentication and token endpoints")
@RequestScoped
public class AccessResource {

    @Inject
    JsonWebToken jwt;
    @Inject
    UsersRepository userrepository;

    @POST
    @Path("/login")
    @Operation(summary = "User login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists or password is not valid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    // TODO: add -> (@Valid Login logindata)
    public LoginTokenResponse login(Login logindata) {
        // check if exist user
        Optional<User> user = userrepository.getByUsername(logindata.getUsername());
        if(!user.isPresent()) {
            throw new WebApplicationException(404);
        }

        // check if password is correct
        user = userrepository.getByUsernameAndPassword(logindata.getUsername(), logindata.getPassword());
        if(!user.isPresent()) {
            throw new WebApplicationException(404);
        }
        User user_returned = user.get();
        String[] user_roles = user_returned.getRoles().split(",");
        String jwt_issuer = ConfigProvider.getConfig().getValue("mp.jwt.verify.issuer", String.class);
        String token = Jwt.issuer(jwt_issuer)
             .upn(user_returned.getUsername()) 
             .groups(new HashSet<>(Arrays.asList(user_roles)))
             .innerSign().encrypt();

        return new LoginTokenResponse(token);
    }

    @POST
    @Path("/logout")
    @Operation(summary = "User logout")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists or password is not valid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public LogoutResponse logout(String username) {
        return new LogoutResponse(username);
    }

    @GET
    @Path("/info")
    @Operation(summary = "User information")
    @SecurityRequirement(name="SecurityScheme") // config auto-add-security-requirement
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public LoginInfoResponse info(@Context SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            throw new UnauthorizedException();
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = jwt.getName();
        }
        return new LoginInfoResponse(name, jwt.getGroups(), jwt.getExpirationTime());
    }

    @Transactional
    @PUT
    @Path("/chgpassword")
    @Operation(summary = "Update password")
    @SecurityRequirement(name="SecurityScheme") // config auto-add-security-requirement
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Password updated"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public Response chgpassword(@QueryParam("newpassword") @Size(min = 8, max = 72,
            message = "password should have size [{min},{max}]") String newpassword) {

        // get username
        String username = jwt.getName();

        // get user
        Optional<User> optional_user = userrepository.getByUsername(username);
        User user;
        if(!optional_user.isPresent()) {
            // if not exist returns 404
            throw new WebApplicationException(404);
        } else {
            user = optional_user.get();
        }

        try {
            if (userrepository.updatePassword(user.getId(), newpassword)) {
                return Response.noContent().status(200).build();
            } else {
                return Response.notModified().build();
            }
        } catch(PersistenceException e) {
            if (e.getCause() instanceof org.hibernate.PropertyValueException) {
                return Response.serverError().entity(Tools.ThrowableToJson(e.getCause())).status(400).build();
            } else if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                return Response.serverError().entity(Tools.ThrowableToJson(e.getCause().getCause())).status(409).build();
            } else {
                return Response.serverError().entity(Tools.ThrowableToJson(e)).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity(Tools.ThrowableToJson(e)).build();
        }
    }
}