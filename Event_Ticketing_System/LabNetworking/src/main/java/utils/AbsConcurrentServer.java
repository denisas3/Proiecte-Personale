package utils;

import java.net.Socket;
import java.util.logging.Logger;

public abstract class AbsConcurrentServer extends AbstractServer {
    private static Logger logger = Logger.getLogger(AbsConcurrentServer.class.getName());

    public AbsConcurrentServer(int port) {
        super(port);
        logger.info("Concurrent AbstractServer");
    }

    protected void processRequest(Socket client) {
        Thread tw=createWorker(client);
        tw.start();
    }

    protected abstract Thread createWorker(Socket client) ;


}
