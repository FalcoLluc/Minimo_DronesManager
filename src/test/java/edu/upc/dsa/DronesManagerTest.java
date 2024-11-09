package edu.upc.dsa;

import edu.upc.dsa.exceptions.DronEnMantenimientoException;
import edu.upc.dsa.exceptions.DronOcupadoException;
import edu.upc.dsa.exceptions.PilotoNotFoundException;
import edu.upc.dsa.models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DronesManagerTest {
    DronesManager dm;

    @Before
    public void setUp() {
        this.dm = DronesManagerImpl.getInstance();
        this.dm.addDron("1", "Gas", "Gasisme");
        this.dm.addDron("2", "Peta", "Petisme");
        this.dm.addDron("3", "Lluc", "Brr");
        this.dm.addPiloto("1","Lluc","Fernandez");
        this.dm.addPiloto("2","Joan","Falco");
        this.dm.addPiloto("3","Pere","Perez");

        Date time= new Date(2003,11,23,4,0);
        Posicion posInicio=new Posicion(10,10);
        Posicion posFinal=new Posicion(30,40);
        this.dm.addFlightPlan("2",time,2,posInicio,posFinal,"2");
    }
    @Test
    public void getDronesOrdered() {
        List<Dron> listOrdered=dm.getDrones();
        assertEquals(listOrdered.get(0).getId(),dm.getDronById("2").getId());
    }
    @Test
    public void getError() {
        Date time= new Date(2003,11,23,5,0);
        Posicion posInicio=new Posicion(10,10);
        Posicion posFinal=new Posicion(30,40);
        assertThrows(DronOcupadoException.class,()->dm.addFlightPlan("2",time,2,posInicio,posFinal,"3"));
    }
    @Test
    public void getFlightPlans() {
        List<FlightPlan> list=dm.getFlightsByPiloto("2");
        assertEquals(list.size(),1);
    }
    @Test
    public void getErrorNotFound() {
        Date time= new Date(2003,11,26,5,0);
        Posicion posInicio=new Posicion(10,10);
        Posicion posFinal=new Posicion(30,40);
        assertThrows(PilotoNotFoundException.class,()->dm.addFlightPlan("2",time,2,posInicio,posFinal,"6"));
    }

    @Test
    public void testAlmacen() {
        dm.addDronAlmacen("1");
        dm.addDronAlmacen("2");
        Date time= new Date(2003,11,26,5,0);
        Posicion posInicio=new Posicion(10,10);
        Posicion posFinal=new Posicion(30,40);
        assertThrows(DronEnMantenimientoException.class,()->dm.addFlightPlan("1",time,2,posInicio,posFinal,"1"));
        assertEquals(dm.getDronById("1").isEnMantenimiento(),true);
        dm.hacerMantenimiento();
        assertEquals(dm.getDronById("1").isEnMantenimiento(),false);
    }
}
