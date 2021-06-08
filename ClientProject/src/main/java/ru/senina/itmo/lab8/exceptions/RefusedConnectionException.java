package ru.senina.itmo.lab8.exceptions;

public class RefusedConnectionException extends RuntimeException{
    public RefusedConnectionException(String message){
        super(message);
    }
}
