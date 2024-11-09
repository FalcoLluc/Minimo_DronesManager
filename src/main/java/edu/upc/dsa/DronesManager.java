package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;

import java.util.Date;
import java.util.List;

public interface DronesManager {
    void addPiloto(String id, String nombre, String apellido);
    void addDron(String id, String nombre, String modelo);

    List<Dron> getDrones();
    List<Piloto> getPilotos();

    Dron addDronAlmacen(String id) throws DronNotFoundException;
    Dron hacerMantenimiento() throws AlmacenEmptyException;

    FlightPlan addFlightPlan(String idDron, Date d,int duracion,Posicion inicioPos, Posicion finalPos, String idPiloto) throws DronEnMantenimientoException, DronOcupadoException, PilotoOcupadoException,DronNotFoundException, PilotoNotFoundException;

    List<FlightPlan> getFlightsByPiloto(String id) throws PilotoNotFoundException;
    List<FlightPlan> getFlightsByDron(String id) throws DronNotFoundException;

    Piloto getPilotoById(String id) throws PilotoNotFoundException;
    Dron getDronById(String id) throws DronNotFoundException;

    int sizeDrones();

    int sizePilotos();

}
