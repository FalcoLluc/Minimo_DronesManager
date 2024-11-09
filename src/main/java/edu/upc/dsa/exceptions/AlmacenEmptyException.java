package edu.upc.dsa.exceptions;

public class AlmacenEmptyException extends RuntimeException {
    public AlmacenEmptyException(String message) {
        super(message);
    }
}
