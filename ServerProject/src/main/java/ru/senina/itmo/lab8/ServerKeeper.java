package ru.senina.itmo.lab8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ServerKeeper {
    private boolean isStopped = false;

    public ServerKeeper() {
    }

    public void start(int serverPort) {
        ExecutorService readThreads = Executors.newCachedThreadPool();
        ExecutorService processThreads = Executors.newCachedThreadPool();
        Controller controller = new Controller(new Model());
        while (!isStopped) {
            final ServerNetConnector net = new ServerNetConnector();
            SendingTask sendingTask = new SendingTask(net);
            ProcessingTask processingTask = new ProcessingTask(controller, sendingTask);
            ReadingTask readingTask = new ReadingTask(net, processThreads, processingTask);
            while (true) { //пока не появится готовый клиент  - проверяем
                if(net.startConnection(serverPort)){
                    break;
                }
            }
            readThreads.execute(readingTask);
            ServerLog.log(Level.INFO, "readThreads was executed for readingTask");
        }
        try {
            readThreads.shutdown();
            readThreads.awaitTermination(3, TimeUnit.SECONDS);
            processThreads.shutdown();
            processThreads.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ServerLog.log(Level.WARNING, "Termination error " + e);
        }
    }

    public void stop() {
        isStopped = true;
    }
}
