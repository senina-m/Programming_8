package ru.senina.itmo.lab8;

public class RefusedConnectionException extends RuntimeException{
    RefusedConnectionException(String message){
        super(message);
    }
}
