package ru.senina.itmo.lab8.exceptions;

/**
 * Exception to show that some arguments weren't valid
 */
public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException(String message) {
        super(message);
    }
}