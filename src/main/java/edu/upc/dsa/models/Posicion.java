package edu.upc.dsa.models;

public class Posicion {
    private int Lat;
    private int Long;

    public Posicion(int lat, int aLong) {
        Lat = lat;
        Long = aLong;
    }

    public Posicion(){

    }

    public int getLat() {
        return Lat;
    }

    public void setLat(int lat) {
        Lat = lat;
    }

    public int getLong() {
        return Long;
    }

    public void setLong(int aLong) {
        Long = aLong;
    }
}
