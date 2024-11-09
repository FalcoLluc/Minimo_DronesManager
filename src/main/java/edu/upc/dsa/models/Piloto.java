package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Piloto {
    private String id;
    private String nombre;
    private String apellido;
    private List<FlightPlan> flights;

    public Piloto(String id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.flights= new LinkedList<>();
    }

    public Piloto(){
        this.flights=new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void addFlightPlan(FlightPlan fl){
        this.flights.add(fl);
    }

    public List<FlightPlan> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightPlan> flights) {
        this.flights = flights;
    }

    public int getFlightHours(){
        int timeTot=0;
        for (FlightPlan fl : this.flights){
            timeTot += fl.getFlightTime();
        }
        return timeTot;
    }

    @Override
    public String toString() {
        return "Piloto [id=" + this.id + ", name=" + this.nombre + ", flights=" + flights.size() + "]";
    }
}

