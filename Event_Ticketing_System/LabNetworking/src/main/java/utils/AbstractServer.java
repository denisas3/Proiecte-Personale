package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server=null;
    private static Logger logger = Logger.getLogger(AbstractServer.class.getName());

    public AbstractServer(int port) {this.port=port;}

    public void start() throws ServerException {
        try{
            server=new ServerSocket(port);
            while(true){
                logger.info( "Waiting for clients...");
                Socket client = server.accept();
                logger.info( "Client connected...");
                processRequest(client);
            }
        }catch(IOException e){
            throw new ServerException("Starting server errror ",e);
        }finally {
            stop();
        }
    }

    protected abstract  void processRequest(Socket client);
    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
