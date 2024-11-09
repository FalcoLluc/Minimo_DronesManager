package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.*;

public class DronesManagerImpl implements DronesManager{
    private static DronesManager instance;

    final static Logger logger = Logger.getLogger(DronesManagerImpl.class);

    public static DronesManager getInstance() {
        if (instance==null) instance = new DronesManagerImpl();
        return instance;
    }

    protected HashMap<String, Piloto> pilotoHashMap;
    protected HashMap<String, Dron> dronHashMap;
    protected Queue<Dron> almacen;

    private DronesManagerImpl() {
        this.pilotoHashMap=new HashMap<>();
        this.dronHashMap=new HashMap<>();
        this.almacen=new LinkedList<>();
    }

    @Override
    public void addDron(String id, String nombre, String modelo) {
        Dron d= new Dron(id,nombre,modelo);
        dronHashMap.put(id,d);
        logger.info("New dron: "+d.toString());
    }

    @Override
    public void addPiloto(String id, String nombre, String apellido) {
        Piloto p= new Piloto(id,nombre,apellido);
        pilotoHashMap.put(id,p);
        logger.info("New Piloto: "+p.toString());
    }

    @Override
    public Dron getDronById(String id) throws DronNotFoundException {
        Dron d = this.dronHashMap.get(id);
        if(d==null){
            logger.warn("Dron not found");
            throw new DronNotFoundException("Dron not found");
        }
        else{
            logger.info("Get Dron: "+d.toString());
            return d;
        }
    }

    @Override
    public Piloto getPilotoById(String id) throws PilotoNotFoundException {
        Piloto p = this.pilotoHashMap.get(id);
        if(p==null){
            logger.warn("Pilot not found");
            throw new PilotoNotFoundException("Piloto not found");
        }
        else{
            logger.info("Get Piloto: "+p.toString());
            return p;
        }
    }

    @Override
    public Dron addDronAlmacen(String id) throws DronNotFoundException{
        Dron d = this.getDronById(id);
        this.almacen.offer(d);
        d.setEnMantenimiento(true);
        logger.info("New Dron in Almacen: "+d.toString());
        return d;
    }

    @Override
    public Dron hacerMantenimiento() throws AlmacenEmptyException {
        if(this.almacen.isEmpty()) {
            logger.warn("Almacen Empty");
            throw new AlmacenEmptyException("No hay drones en el almacen");
        }
        Dron d =this.almacen.poll();
        d.setEnMantenimiento(false);
        logger.info("Dron Reparado: "+d.toString());
        return d;
    }

    @Override
    public List<Dron> getDrones() {
        List<Dron> valueList = new ArrayList<>(dronHashMap.values());
        Collections.sort(valueList,(new Comparator<Dron>() {
            @Override
            public int compare(Dron o1, Dron o2) {
                return Integer.compare(o2.getFlightHours(),o1.getFlightHours());
            }
        }));
        logger.info("drones Ordered");
        return valueList;
    }

    @Override
    public List<Piloto> getPilotos() {
        List<Piloto> valueList = new ArrayList<>(pilotoHashMap.values());
        Collections.sort(valueList,(new Comparator<Piloto>() {
            @Override
            public int compare(Piloto o1, Piloto o2) {
                return Integer.compare(o2.getFlightHours(),o1.getFlightHours());
            }
        }));
        logger.info("drones Ordered");
        return valueList;
    }

    @Override
    public FlightPlan addFlightPlan(String idDron, Date d, int duracion, Posicion inicioPos, Posicion finalPos, String idPiloto) throws DronEnMantenimientoException, DronOcupadoException, PilotoOcupadoException, DronNotFoundException,PilotoNotFoundException {
        Dron dr = getDronById(idDron);
        if(dr.isEnMantenimiento()){
            logger.warn("Dron En Mantenimiento");
            throw new DronEnMantenimientoException("Dron En Mantenimiento");
        }
        for(FlightPlan fl : dr.getFlights()){
            if (fl.getInicio().before(d)&&((fl.getInicio().getTime()+ (long) fl.getFlightTime() *60*60*1000)>d.getTime())){
                logger.warn("Dron Busy");
                throw new DronOcupadoException("Dron Busy");
            }
        }
        Piloto pil = getPilotoById(idPiloto);
        for(FlightPlan fl : pil.getFlights()){
            if (fl.getInicio().before(d)&&((fl.getInicio().getTime()+ (long) fl.getFlightTime() *60*60*1000)>d.getTime())){
                logger.warn("Piloto Busy");
                throw new PilotoOcupadoException("Piloto Busy");
            }
        }
        FlightPlan fl=new FlightPlan(idDron,d,duracion,inicioPos,finalPos,idPiloto);
        dr.addFlightPlan(fl);
        pil.addFlightPlan(fl);
        return fl;
    }

    @Override
    public List<FlightPlan> getFlightsByDron(String id) throws DronNotFoundException {
        return this.getDronById(id).getFlights();
    }

    @Override
    public List<FlightPlan> getFlightsByPiloto(String id) throws PilotoNotFoundException {
        return this.getPilotoById(id).getFlights();
    }

    public int sizeDrones(){
        return this.dronHashMap.size();
    }

    public int sizePilotos(){
        return this.pilotoHashMap.size();
    }
}
