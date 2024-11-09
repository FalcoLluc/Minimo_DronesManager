package edu.upc.dsa.services;

import edu.upc.dsa.DronesManager;
import edu.upc.dsa.DronesManagerImpl;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Dron;
import edu.upc.dsa.models.FlightPlan;
import edu.upc.dsa.models.Piloto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/drones", description = "Endpoint to Drones")
@Path("/drones")
public class DronesService {
    private DronesManager dm;

    public DronesService() {
        this.dm = DronesManagerImpl.getInstance();
        if (dm.sizeDrones()==0) {
            this.dm.addDron("1","Gas","brr");
            this.dm.addDron("2","Dronator","brr");
        }
        if(dm.sizePilotos()==0){
            this.dm.addPiloto("1","Lluc","Fernandez");
            this.dm.addPiloto("2","Pere","Perez");
        }
    }

    @GET
    @ApiOperation(value = "get Drones", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class, responseContainer="List"),
    })
    @Path("/drones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrones() {
        List<Dron> drones = this.dm.getDrones();

        GenericEntity<List<Dron>> entity = new GenericEntity<List<Dron>>(drones) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get Pilotos", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Piloto.class, responseContainer="List"),
    })
    @Path("/pilotos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPilotos() {
        List<Piloto> pilotos = this.dm.getPilotos();

        GenericEntity<List<Piloto>> entity = new GenericEntity<List<Piloto>>(pilotos) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get dron", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class),
            @ApiResponse(code = 404, message = "Dron not found")
    })
    @Path("/drones/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDronesId(@PathParam("id") String id) {
        Dron dr;
        try{
            dr = this.dm.getDronById(id);
        }
        catch(DronNotFoundException e){
            return Response.status(404).build();
        }

        GenericEntity<Dron> entity = new GenericEntity<Dron>(dr) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @POST
    @ApiOperation(value = "create a new Dron", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Dron.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/dron")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newDron(Dron dron) {

        if (dron.getNombre()==null || dron.getModelo()==null || dron.getId()==null)  return Response.status(500).entity(dron).build();
        this.dm.addDron(dron.getId(),dron.getNombre(),dron.getModelo());
        return Response.status(201).entity(dron).build();
    }

    @POST
    @ApiOperation(value = "create a new Pilot", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Piloto.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/piloto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPiloto(Piloto pil) {
        if (pil.getId()==null|| pil.getNombre()==null || pil.getApellido()==null)  return Response.status(500).entity(pil).build();
        this.dm.addPiloto(pil.getId(),pil.getNombre(),pil.getApellido());
        return Response.status(201).entity(pil).build();
    }


    @GET
    @ApiOperation(value = "get FlightPlans Pilot", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = FlightPlan.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "Empty Flights"),
            @ApiResponse(code = 404, message = "Pilot not found")
    })
    @Path("/flightsPilot/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightPlansPilot(@PathParam("id") String id) {
        try{
            List<FlightPlan> flights = this.dm.getFlightsByPiloto(id);
            if(flights.isEmpty()){
                return Response.status(204).build();
            }
            GenericEntity<List<FlightPlan>> entity = new GenericEntity<List<FlightPlan>>(flights) {};
            return Response.status(201).entity(entity).build()  ;
        }
        catch(PilotoNotFoundException e){
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get FlightPlans Dron", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = FlightPlan.class, responseContainer="List"),
            @ApiResponse(code = 204, message = "Empty Flights"),
            @ApiResponse(code = 404, message = "Dron not found")
    })
    @Path("/flightsDron/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightPlansDron(@PathParam("id") String id) {
        try{
            List<FlightPlan> flights = this.dm.getFlightsByDron(id);
            if(flights.isEmpty()){
                return Response.status(204).build();
            }
            GenericEntity<List<FlightPlan>> entity = new GenericEntity<List<FlightPlan>>(flights) {};
            return Response.status(201).entity(entity).build()  ;
        }
        catch(DronNotFoundException e){
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "repair next", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class),
            @ApiResponse(code = 203, message = "Almacen Empty")
    })
    @Path("/repair")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reparar() {
        Dron o;
        try{
            o = this.dm.hacerMantenimiento();
        }
        catch(AlmacenEmptyException e){
            return Response.status(203).build();
        }
        GenericEntity<Dron> entity = new GenericEntity<Dron>(o) {
        };
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "add Almacen", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/repair/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAlmacen(@PathParam("id") String id) {
        Dron o;
        try{
            o=dm.addDronAlmacen(id);
        }
        catch(DronNotFoundException e){
            return Response.status(404).build();
        }
        GenericEntity<Dron> entity = new GenericEntity<Dron>(o) {
        };
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "create a new FlightPlan", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=FlightPlan.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 501, message = "Dron Mantenimiento"),
            @ApiResponse(code = 502, message = "Dron Busy"),
            @ApiResponse(code = 503, message = "Pilot Busy"),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/flight")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newFlightPlan(FlightPlan fl) {
        if (fl.getFlightTime()==0 || fl.getInicio()==null || fl.getIdDron()==null || fl.getIdPiloto()==null  )  return Response.status(500).entity(fl).build();
        try{
            FlightPlan flight=this.dm.addFlightPlan(fl.getIdDron(),fl.getInicio(),fl.getFlightTime(),fl.getPosInicio(),fl.getPosDest(),fl.getIdPiloto());
            return Response.status(201).entity(flight).build();
        }
        catch(PilotoNotFoundException e){
            return Response.status(404).build();
        }
        catch(DronNotFoundException e){
            return Response.status(404).build();
        }
        catch(PilotoOcupadoException e){
            return Response.status(503).build();
        }
        catch(DronOcupadoException e){
            return Response.status(502).build();
        }
        catch(DronEnMantenimientoException e){
            return Response.status(501).build();
        }
    }
}
