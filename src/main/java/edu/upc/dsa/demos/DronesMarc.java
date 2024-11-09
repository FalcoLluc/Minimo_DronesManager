package edu.upc.dsa.demos;

@Api(value = "/drones", description = "Endpoint to Drones Service")
@Path("drones")
public class DronesMarc {
    private DronManager Dm;

    public DroneService() {
        this.Dm = DronManagerImpl.getInstance();
        if (Dm.sizeDrones()==0) {
            Dron dron1 = new Dron("Dron1","DSA","DronGrande");
            Dron dron2 = new Dron("Dron2","DSA","DronMediano");
            Dron dron3 = new Dron("Dron3","DSA","DronPequeño");
            dron1.setHorasVuelo(86);
            dron2.setHorasVuelo(76);
            dron3.setHorasVuelo(1000);
            this.Dm.AddDron(dron1);
            this.Dm.AddDron(dron2);
            this.Dm.AddDron(dron3);
            Piloto piloto1 = new Piloto("Pablo","Escobar");
            Piloto piloto2 = new Piloto("Marclo", "Polo");
            Piloto piloto3 = new Piloto("Gifre", "ElPilos");
            piloto1.setHorasVuelo(12);
            piloto2.setHorasVuelo(13);
            piloto3.setHorasVuelo(11);
            this.Dm.AddPiloto(piloto1);
            this.Dm.AddPiloto(piloto2);
            this.Dm.AddPiloto(piloto3);
            PlanDeVuelo plan1 = new PlanDeVuelo(piloto1.getId(), dron1.getId(),1,1,1,1,1,1);
            PlanDeVuelo plan2 = new PlanDeVuelo(piloto2.getId(), dron1.getId(),2,1,1,1,1,1);
            PlanDeVuelo plan3 = new PlanDeVuelo(piloto1.getId(), dron3.getId(),4,1,1,1,1,1);
            this.Dm.AddPlanDeVuelo(plan1);
            this.Dm.AddPlanDeVuelo(plan2);
            this.Dm.AddPlanDeVuelo(plan3);
        }
    }

    @POST
    @ApiOperation(value = "create a new Pilot", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Piloto.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/pilot")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPiloto(Piloto piloto) {

        if (piloto.getNombre() == null || piloto.getApellidos() == null)  return Response.status(500).entity(piloto).build();
        this.Dm.AddPiloto(piloto);
        return Response.status(201).entity(piloto).build();
    }

    @POST
    @ApiOperation(value = "create a new Drone", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Dron.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/dron")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newDron(Dron dron) {

        if (dron.getNombre() == null || dron.getFabricante() == null || dron.getModelo() == null)  return Response.status(500).entity(dron).build();
        this.Dm.AddDron(dron);
        return Response.status(201).entity(dron).build();
    }

    @GET
    @ApiOperation(value = "get all Drones", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Dron.class, responseContainer="List"),
    })
    @Path("/drones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrones() {

        List<Dron> drons = this.Dm.listaDrones();

        GenericEntity<List<Dron>> entity = new GenericEntity<List<Dron>>(drons) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get all Pilots", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Piloto.class, responseContainer="List"),
    })
    @Path("/piloto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPilotos() {

        List<Piloto> pilotos = this.Dm.listaPilotos();

        GenericEntity<List<Piloto>> entity = new GenericEntity<List<Piloto>>(pilotos) {};
        return Response.status(201).entity(entity).build()  ;

    }
}