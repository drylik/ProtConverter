package ru.novikov.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ru.novikov.Bootstrap;
import ru.novikov.jaxb.Envelope;
import ru.novikov.jaxb.parse.JaxbParser;
import ru.novikov.jaxb.parse.Parser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class XmlParserServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(XmlParserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xml = request.getParameter("xml");
        Parser parser = new JaxbParser();
        JSONObject jo = new JSONObject();
        Envelope env;
        try {
            env = (Envelope) parser.getObject(IOUtils.toInputStream(xml, StandardCharsets.UTF_8), Envelope.class);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(env);
            log.log(Level.INFO, jsonString);
            //sendViaTCP(jsonString, Bootstrap.getProps().getProperty("tcp.dest.addr"), Integer.parseInt(Bootstrap.getProps().getProperty("tcp.dest.port")));
            jo.put("redirect", "success");
        } catch (JAXBException e) {
            log.log(Level.ERROR, "Error during unmarshalling", e);
            jo.put("redirect", "error");
        } catch (JsonProcessingException e) {
            log.log(Level.ERROR, "Error during parsing to JSON", e);
            jo.put("redirect", "error");
        /*} catch (IOException e) {
            log.log(Level.ERROR, e);
            jo.put("redirect", "error");*/
        } finally {
            response.getWriter().println(jo);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher index = request.getRequestDispatcher("/index.html");
        index.forward(request, response);
    }

    private void sendViaTCP(String jsonString, String addr, int port) throws IOException {
        Socket s;
        s = new Socket(addr, port);
        s.getOutputStream().write(jsonString.getBytes());
    }
}
