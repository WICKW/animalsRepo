package com.animals.app.controller.resource;

import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.domain.User;
import com.animals.app.repository.AnimalMedicalHistoryRepository;
import com.animals.app.repository.Impl.AnimalMedicalHistoryRepositoryImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */

@Path("doctor")
public class DoctorResource {
    private static Logger LOG = LogManager.getLogger(DoctorResource.class);

    //return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    /**
     * @param animalId instance used for lookup.
     * @return count of rows for pagination.
     * -------------------------------------------------------------------
     * animalId must be set and more than 0
     */
    @GET //http:localhost:8080/webapi/animals/paginator
    @RolesAllowed("лікар")
    @Path("medical_history/paginator/{animalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnimalsPaginator(@PathParam("animalId") @NotNull @DecimalMin(value = "1") long animalId) {
        //get count of row according to filter
        AnimalMedicalHistoryRepository animalMedicalHistory = new AnimalMedicalHistoryRepositoryImpl();
        long pages = animalMedicalHistory.getByAnimalIdCount(animalId);

        String json = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return ok(json);
    }

    /**
     * @param animalId instance used for lookup.
     * @return list of medical history items.
     * -------------------------------------------------------------------
     * animalId must be set and more than 0
     * AnimalsFilter.page must be set and more than 0
     * AnimalsFilter.limit must be set and more than 0
     */
    @POST //http:localhost:8080/webapi/doctor/medical_history/{animalId}
    @RolesAllowed("лікар")
    @Path("medical_history/{animalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalHistoryByAnimalId(@PathParam("animalId") @NotNull @DecimalMin(value = "1") long animalId,
                                                @Valid @NotNull AnimalsFilter animalsFilter) {

        AnimalMedicalHistoryRepository animalMedicalHistory = new AnimalMedicalHistoryRepositoryImpl();
        List<AnimalMedicalHistory> medicalHistory = animalMedicalHistory
                .getByAnimalId(animalId, animalsFilter.getOffset(), animalsFilter.getLimit());

        //cast list of animals medical history to generic list
        GenericEntity<List<AnimalMedicalHistory>> genericAnimals =
                new GenericEntity<List<AnimalMedicalHistory>>(medicalHistory) {};

        return ok(genericAnimals);
    }

    /**
     * Delete animal medical history item.
     * @param itemId instance used for lookup.
     * @return status 200 if delete is done.
     * -------------------------------------------------------------------
     * itemId must be set and more than 0
     */
    @DELETE //http:localhost:8080/webapi/doctor/medical_history/item/{itemId}
    @RolesAllowed("лікар")
    @Path("medical_history/item/{itemId}")
    public Response deleteMedicalHistoryItemById(@PathParam("itemId") @NotNull @DecimalMin(value = "1") long itemId) {
        AnimalMedicalHistoryRepository animalMedicalHistory = new AnimalMedicalHistoryRepositoryImpl();
        animalMedicalHistory.deleteById(itemId);

        return ok();
    }

    /**
     * Insert animal medical history item into data base.
     * @param req instance of HttpServletRequest fo get access to session
     * @param animalMedicalHistory instance of item to be inserted.
     * @return status 200 if delete is done.
     * -------------------------------------------------------------------
     * AnimalMedicalHistory.animalId must be set
     * AnimalMedicalHistory.date must be set
     * AnimalMedicalHistory.status.id must be set and more than 0
     * Session value userId must be set
     */
    @POST //http://localhost:8080/webapi/doctor/medical_history/item
    @RolesAllowed("лікар")
    @Path("medical_history/item")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalHistoryItemById(@Context HttpServletRequest req,
                                                 @Valid @NotNull AnimalMedicalHistory animalMedicalHistory) {
        if (animalMedicalHistory.getStatus().getId() == null) {
            return BAD_REQUEST;
        }

        HttpSession session = req.getSession(true);
        System.out.println("ss " + session.getId());
        if (session.getAttribute("userId") == null) {
            return BAD_REQUEST;
        }

        User user = new User();
        try {
            user.setId(Integer.parseInt((String) session.getAttribute("userId")));
        } catch (ClassCastException e) {
            return BAD_REQUEST;
        }

        animalMedicalHistory.setUser(user);

        AnimalMedicalHistoryRepository animalMedicalHistoryRepository = new AnimalMedicalHistoryRepositoryImpl();
        animalMedicalHistoryRepository.insert(animalMedicalHistory);

        return ok();
    }

    /**
     * Return response with code 200(OK) and build returned entity
     * @return HTTP code 200
     */
    private Response ok() {
        return Response.ok().build();
    }

    /**
     * Return response with code 200(OK) and build returned entity
     * @param entity Returned json instance from client
     * @return HTTP code 200
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }
}
