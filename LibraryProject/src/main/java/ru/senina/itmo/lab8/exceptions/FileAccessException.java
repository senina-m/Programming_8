package ru.senina.itmo.lab8.exceptions;

public class FileAccessException extends RuntimeException {
    private String filename;
    public FileAccessException(String message, String filename){
        super(message);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
