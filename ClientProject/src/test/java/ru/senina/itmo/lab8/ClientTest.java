package ru.senina.itmo.lab8;

import org.junit.Test;
public class ClientTest {

    @Test
    public void main(){
        ClientNetConnector net = new ClientNetConnector();
        net.startConnection("localhost", 8181);
        net.sendMessage("abcdefghijkmnlop");
        net.receiveMessage();
        net.stopConnection();
    }
}
