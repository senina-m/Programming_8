package ru.senina.itmo.lab8.parser;

import ru.senina.itmo.lab8.exceptions.FileAccessException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public abstract class Parser<T> {

    /**@return instance with fields serialized from string*/
    public abstract T fromStringToObject(String str) throws ParsingException;

    @Deprecated
    public static String fromFileToString(String filename) throws FileAccessException {
        Path path = Paths.get(filename);
        try {
            StringBuilder resultString = new StringBuilder();
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                resultString.append(line);
            }
            sc.close();
            return resultString.toString();

        } catch (IOException e) {
            checkRights(path);
        }
        return null;
    }


    /**Method to write string to file
     * @param filename the path to which file value have be written
     * @param str      the string*/
    public static void writeStringToFile(String filename, String str) throws FileAccessException {
        Path path = Paths.get(filename);
        try {
            Files.write(path, ("").getBytes());
            Files.write(path, (str).getBytes());
        } catch (IOException e) {
            checkRights(path);
        }
    }


    /**
     * Method that parses Object to string
     */
    public abstract String fromObjectToString(T object) throws ParsingException;


    private static void checkRights(Path path) throws FileAccessException {
        if (Files.notExists(path)) {
            throw new FileAccessException("File " + path + " doesn't exist!", path.toString());
        } else if (!Files.isWritable(path)) {
            throw new FileAccessException("Invalid file rights. File " + path + " isn't writable!", path.toString());
        } else if (!Files.isReadable(path)) {
            throw new FileAccessException("Invalid file rights. File " + path + " isn't readable!", path.toString());
        }
    }
}