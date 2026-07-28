import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.repository.EmployeeDbRepository;
import org.example.lab03.repository.ShowDbRepository;
import org.example.lab03.repository.TicketDbRepository;
import org.example.lab03.services.ILabServices;
import org.example.lab03.utils.EncryptUtils;
import org.example.lab03.utils.JdbcUtils;
import server.ServicesImpl;
import utils.AbstractServer;
import utils.JsonConcurrentServer;
import utils.ServerException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort=55555;
    private static Logger logger = LogManager.getLogger(StartJsonServer.class);

    public static void main(String[] args) {
        Properties serverProps=new Properties();

        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            logger.info("Server properties set. {} ", serverProps);
            //serverProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find server.properties "+e);
            logger.debug("Looking for file in "+(new File(".")).getAbsolutePath());
            return;
        }

        JdbcUtils jdbcUtils = new JdbcUtils(serverProps);

        EmployeeDbRepository employeeDbRepository = new EmployeeDbRepository(jdbcUtils);
        ShowDbRepository showDbRepository = new ShowDbRepository(jdbcUtils);
        TicketDbRepository ticketDbRepository = new TicketDbRepository(jdbcUtils);
        ILabServices service = new ServicesImpl(employeeDbRepository,showDbRepository,ticketDbRepository);

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            logger.error("Wrong  Port Number"+nef.getMessage());
            logger.debug("Using default port "+defaultPort);
        }
        logger.debug("Starting server on port: "+ serverPort);
        AbstractServer server = new JsonConcurrentServer(serverPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            logger.error("Error starting the server" + e.getMessage());
        }

    }
}
