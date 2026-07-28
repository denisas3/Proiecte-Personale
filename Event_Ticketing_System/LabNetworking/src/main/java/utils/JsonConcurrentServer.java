package utils;

import jsonprotocol.ClientJsonWorker;
import org.example.lab03.services.ILabServices;

import java.net.Socket;
import java.util.logging.Logger;

public class JsonConcurrentServer extends AbsConcurrentServer{

    private ILabServices labServer;
    private static Logger logger = Logger.getLogger(JsonConcurrentServer.class.getName());
    public JsonConcurrentServer(int port, ILabServices labServer){
        super(port);
        this.labServer = labServer;
        logger.info("LabJsonConcurrentServer created");
    }

    @Override
    protected Thread createWorker(Socket client){
        ClientJsonWorker worker = new ClientJsonWorker(labServer, client);

        Thread tw =new Thread(worker);
        return tw;
    }

}
