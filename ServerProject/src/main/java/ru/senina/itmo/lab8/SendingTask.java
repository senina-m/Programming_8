package ru.senina.itmo.lab8;

public class SendingTask implements Runnable{
    private String response;
    private final ServerNetConnector net;

    public SendingTask(ServerNetConnector net) {
        this.net = net;
    }

    public void setSendResponse(String response) {
        this.response = response;
    }

    @Override
    public void run() {
            net.sendResponse(response);
            net.stopConnection();
    }
}
