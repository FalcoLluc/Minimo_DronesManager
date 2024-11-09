package edu.upc.dsa.exceptions;

public class DronNotFoundException extends RuntimeException {
    public DronNotFoundException(String message) {
        super(message);
    }
}
