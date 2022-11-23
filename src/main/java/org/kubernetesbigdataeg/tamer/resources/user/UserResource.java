package org.kubernetesbigdataeg.tamer.resources.user;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.kubernetesbigdataeg.tamer.entities.user.User;
import org.kubernetesbigdataeg.tamer.entities.user.UsersRepository;
import org.kubernetesbigdataeg.tamer.utils.Tools;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Path("/v1/user")
@SecurityRequirement(name="SecurityScheme") // PROBLEMA1: problema con config auto-add-security-requirement
@Tag(name = "User Resource (Not Ready)", description = "Gets host connectivity information")
@RequestScoped
public class UserResource {

    @Inject
    UsersRepository repository;

    @GET
    @Operation(summary = "List users")
    @RolesAllowed({"admin", "adminusers"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some query properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public List<User> list(
        @QueryParam("sort") List<String> sortQuery, 
        @QueryParam("page") @DefaultValue("0") @Min(0) @Max((int)Integer.MAX_VALUE/100) int pageIndex, 
        @QueryParam("size") @DefaultValue("20") @Min(1) @Max(100) int pageSize) {
        List<String> fields_list = User.getAttributes();
        for (String sort_element : sortQuery) {
            if(!fields_list.contains(sort_element)){
                throw new WebApplicationException(400);
            }
        }
        return repository.list(pageIndex, pageSize, sortQuery);
    }

    @GET
    @Path("/id/{id}")
    @Operation(summary = "Get a user by id")
    @RolesAllowed({"admin", "adminusers"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some query properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public User get(@PathParam("id") @Min(1) Long id){
        User user = repository.get(id);
        if(user == null) {
            throw new WebApplicationException(404);
        }
        return user;
    }

    @GET
    @Path("/username/{username}")
    @Operation(summary = "Get a user by username")
    @RolesAllowed({"admin", "adminusers"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Ok"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some query properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public Optional<User> get(@PathParam("username") @Email @Size(min = 5,
            max = 45, message = "username/email should have size [{min},{max}]") String username){
        Optional<User> user = repository.getByUsername(username);
        if(!user.isPresent()) {
            throw new WebApplicationException(404);
        }
        return user;
    }

    @Transactional
    @POST
    @Operation(summary = "Create a user")
    @RolesAllowed({"admin", "adminusers"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "User created"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some user properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "409", description = "Conflict: User exists"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public Response add(@Valid User userToSave) {
        try {
            return Response.created(new URI("/user/"+repository.add(userToSave))).build();
        } catch(URISyntaxException e) {
            return Response.serverError().entity(Tools.ThrowableToJson(e)).build();
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

    @Transactional
    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a user")
    @RolesAllowed({"admin", "adminusers"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "User updated"),
            @APIResponse(responseCode = "304", description = "User not modified"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public Response update(@PathParam("id") @Min(1) Long id, 
                @QueryParam("name") @Size(min = 4, max = 25, message = "name should have size [{min},{max}]") String name,
                @QueryParam("password") @Size(min = 8, max = 72, message = "password should have size [{min},{max}]") String password,
                @QueryParam("roles") String roles,
                @QueryParam("enabled") Boolean enabled) {
        try {
            if (repository.get(id) == null) {
                return Response.status(404).build();
            } else if (repository.update(id, name, password, roles, enabled)) {
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

    @Transactional
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a user")
    @RolesAllowed({"admin", "adminusers"})
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "User removed"),
            @APIResponse(responseCode = "400", description = "Bad Request: Some properties fails"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "404", description = "Not Found: User not exists"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
        }
    )
    public Response delete(@PathParam("id") @Min(1) Long id) {
        if (repository.delete(id)) {
            return Response.noContent().status(204).build();
        } else {
            return Response.status(404).build();
        }
    }
}