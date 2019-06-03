package sk.fri.uniza.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import sk.fri.uniza.api.Paged;
import sk.fri.uniza.api.Senzor_data;
import sk.fri.uniza.auth.Role;
import sk.fri.uniza.core.User;
import sk.fri.uniza.db.SenzorDataDao;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Path("/data")
public class SenzorDataResource {
    private SenzorDataDao senzorDataDao;

    public SenzorDataResource(SenzorDataDao senzorDataDao) {this.senzorDataDao = senzorDataDao;}

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
    @ApiOperation(value = "Obtain all senzor data", response = Senzor_data.class, authorizations =
            {@Authorization(value = "oauth2", scopes = {@AuthorizationScope(scope = Role.ADMIN, description =
                    "Access to all resources"), @AuthorizationScope(scope = Role.USER_READ_ONLY, description = "Limited access")})})
    public Response getData(@QueryParam("limit") Integer limit, @QueryParam("page") Integer page){
        if (page == null) page = 1;
        if (limit != null) {
            Paged<List<Senzor_data>> listPaged = senzorDataDao.getAll(limit, page);
            return Response.ok()
                    .entity(listPaged)
                    .build();

        } else {
            List<Senzor_data> senzorDataList = senzorDataDao.getAll();
            return Response.ok()
                    .entity(senzorDataList)
                    .build();
        }
    }

    @GET
    @Path("/noauth")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtain all senzor data", response = Senzor_data.class )
    public Response getDataNoAuth(@QueryParam("limit") Integer limit, @QueryParam("page") Integer page){
        if (page == null) page = 1;
        if (limit != null) {
            Paged<List<Senzor_data>> listPaged = senzorDataDao.getAll(limit, page);
            return Response.ok()
                    .entity(listPaged)
                    .build();

        } else {
            List<Senzor_data> senzorDataList = senzorDataDao.getAll();
            return Response.ok()
                    .entity(senzorDataList)
                    .build();
        }

    }

    @POST
    @UnitOfWork()
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({Role.ADMIN})
    //@RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
    @ApiOperation(value = "Find data by ID", response = Senzor_data.class, authorizations =
            {@Authorization(value = "oauth2", scopes = {@AuthorizationScope(scope = Role.ADMIN, description =
                    "Access to all resources"), @AuthorizationScope(scope = Role.USER_READ_ONLY, description = "Limited access")})})
    public Response setNewData(@QueryParam("id") Long id, @QueryParam("kto") String kto, @QueryParam("co") int co) {
        Senzor_data data = new Senzor_data(id, kto, co, new DateTime());
        //senzorDataDao.save(data); //funkcne

        if (data.getId() == null)
            try {
                senzorDataDao.save(data);
            } catch (HibernateException e) {
                throw new WebApplicationException(e.getMessage(), 422);
            }
        else
            senzorDataDao.update(data, null);

        return Response.ok().build();
    }

    @POST
    @Path("/noauth")
    @UnitOfWork()
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Find data by ID", response = Senzor_data.class )
    public Response setNewDataNoAuth(@QueryParam("kto") String kto, @QueryParam("co") int co) {
        Senzor_data data = new Senzor_data(kto, co, new DateTime());
        senzorDataDao.save(data);

        return Response.ok().build();
    }

    @DELETE
    @UnitOfWork
    @RolesAllowed(Role.ADMIN)
    @ApiOperation(value = "Delete data", response = Senzor_data.class, authorizations = {@Authorization(value = "oauth2",
            scopes = {@AuthorizationScope(scope = Role.ADMIN, description = "Access to all resources"), @AuthorizationScope(scope = Role.USER_READ_ONLY, description = "Limited access")})})
    public Response deleteData(@ApiParam(hidden = true) @Auth User user, @QueryParam("id") Long id){
        if (user.getId() != id){
            Optional<Senzor_data> senzor_data1 = senzorDataDao.findById(id);

            return senzor_data1.map(senzor_data -> {
                senzorDataDao.delete(senzor_data);
                return Response.ok().build();
            }).get();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

//    @POST
//    @UnitOfWork
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({Role.ADMIN, Role.USER_READ_ONLY})
//    @ApiOperation(value = "Save or Update data", response = Senzor_data.class, authorizations = {@Authorization(value = "oauth2",
//            scopes = {@AuthorizationScope(scope = Role.ADMIN, description = "Access to all resources"), @AuthorizationScope(scope = Role.USER_READ_ONLY, description = "Limited access")})})
//    public Senzor_data setDataInfo(@ApiParam(hidden = true) @Auth User user, @Valid Senzor_data senzor_data) {
//        if (!user.getRoles().contains(Role.ADMIN)) {
//            if (user.getId() != senzor_data.getId()) {
//                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
//            }
//        }
//
//        if (senzor_data.getId() == null)
//            try {
//                senzorDataDao.save(senzor_data);
//            } catch (HibernateException e) {
//                throw new WebApplicationException(e.getMessage(), 422);
//            }
//        else
//            senzorDataDao.update(senzor_data, null);
//
//        return senzor_data;
//    }
}
