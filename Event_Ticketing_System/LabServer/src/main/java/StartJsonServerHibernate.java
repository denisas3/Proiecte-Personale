import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.repository.EmployeeDbHibernateRepository;
import org.example.lab03.repository.ShowDbHibernateRepository;
import org.example.lab03.repository.TicketDbHibernateRepository;
import org.example.lab03.services.ILabServices;
import server.ServicesImplHibernate;
import utils.AbstractServer;
import utils.JsonConcurrentServer;
import utils.ServerException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartJsonServerHibernate {

    private static int defaultPort = 55555;
    private static Logger logger = LogManager.getLogger(StartJsonServerHibernate.class);

    public static void main(String[] args) {
        Properties serverProps = new Properties();

        try {
            serverProps.load(StartJsonServerHibernate.class.getResourceAsStream("/server.properties"));
            logger.info("Server properties set. {} ", serverProps);
        } catch (IOException e) {
            logger.error("Cannot find server.properties " + e);
            logger.debug("Looking for file in " + (new File(".")).getAbsolutePath());
            return;
        }

        EmployeeDbHibernateRepository employeeDbRepository = new EmployeeDbHibernateRepository();
        ShowDbHibernateRepository showDbRepository = new ShowDbHibernateRepository();
        TicketDbHibernateRepository ticketDbRepository = new TicketDbHibernateRepository();

        ILabServices service = new ServicesImplHibernate(
                employeeDbRepository,
                showDbRepository,
                ticketDbRepository
        );

        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            logger.error("Wrong Port Number " + nef.getMessage());
            logger.debug("Using default port " + defaultPort);
        }

        logger.debug("Starting Hibernate server on port: " + serverPort);

        AbstractServer server = new JsonConcurrentServer(serverPort, service);

        try {
            server.start();
        } catch (ServerException e) {
            logger.error("Error starting the server " + e.getMessage());
        }
    }
}