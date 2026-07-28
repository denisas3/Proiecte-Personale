package org.example.lab03.services;

public class LabException extends Exception {
    public LabException(){
    }
    public LabException(String message) {
        super(message);
    }

    public LabException(String message, Throwable cause) {
        super(message, cause);
    }
}
