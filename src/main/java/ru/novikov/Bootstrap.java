package ru.novikov;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Bootstrap {

    private static final Logger log = LogManager.getLogger(Bootstrap.class);

    public static void main(String[] args) throws Exception {
        Properties props = readFromFile();
        assert props != null;
        Server server = new Server(Integer.parseInt(props.getProperty("http.port")));

        WebAppContext context = new WebAppContext();
        context.setDescriptor("webapp/WEB-INF/web.xml");
        context.setResourceBase("webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        server.start();
        server.join();
    }

    private static Properties readFromFile() {
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream("config.property");
            props.load(is);
            return props;
        } catch (FileNotFoundException e) {
            log.log(Level.ERROR, "No configuration file found", e);
        } catch (IOException e) {
            log.log(Level.ERROR, "Error during reading properties", e);
        }
        return null;
    }
}
