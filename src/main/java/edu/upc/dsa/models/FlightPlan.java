package edu.upc.dsa.models;

import java.util.Date;

public class FlightPlan {
    private String idDron;
    private Date inicio;
    private int flightTime;
    private Posicion posInicio;
    private Posicion posDest;
    private String idPiloto;

    public FlightPlan(String idDron, Date inicio, int flightTime, Posicion posInicio, Posicion posDest, String idPiloto) {
        this.idDron = idDron;
        this.inicio = inicio;
        this.flightTime = flightTime;
        this.posInicio = posInicio;
        this.posDest = posDest;
        this.idPiloto = idPiloto;
    }

    public FlightPlan(){

    }

    public String getIdDron() {
        return idDron;
    }

    public void setIdDron(String idDron) {
        this.idDron = idDron;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public Posicion getPosInicio() {
        return posInicio;
    }

    public void setPosInicio(Posicion posInicio) {
        this.posInicio = posInicio;
    }

    public Posicion getPosDest() {
        return posDest;
    }

    public void setPosDest(Posicion posDest) {
        this.posDest = posDest;
    }

    public String getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(String idPiloto) {
        this.idPiloto = idPiloto;
    }
}
