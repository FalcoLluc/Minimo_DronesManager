package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Dron {
    private String id;
    private String nombre;
    private String modelo;
    private boolean enMantenimiento;
    private List<FlightPlan> flights;

    public Dron(String id, String nombre, String modelo) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.enMantenimiento=false;
        this.flights= new LinkedList<>();
    }

    // Default constructor required by Jackson
    public Dron() {
        this.flights = new LinkedList<>();
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    public void addFlightPlan(FlightPlan fl){
        this.flights.add(fl);
    }

    public int getFlightHours(){
        int timeTot=0;
        for (FlightPlan fl : this.flights){
            timeTot += fl.getFlightTime();
        }
        return timeTot;
    }

    public List<FlightPlan> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightPlan> flights) {
        this.flights = flights;
    }


    @Override
    public String toString() {
        return "Dron [id="+this.id+", name=" + this.nombre + ", flights=" + flights.size() +"]";
    }
}
