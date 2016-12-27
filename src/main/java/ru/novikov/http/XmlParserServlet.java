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
import ru.novikov.jaxb.parse.impl.JaxbParser;
import ru.novikov.jaxb.parse.Parser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
            sendViaTCP(jsonString, Bootstrap.getProps().getProperty("tcp.dest.addr"), Integer.parseInt(Bootstrap.getProps().getProperty("tcp.dest.port")));
            jo.put("redirect", "success.html");
        } catch (JAXBException e) {
            log.log(Level.ERROR, "Error during unmarshalling", e);
            jo.put("redirect", "error");
            HttpSession session = request.getSession(true);
            session.setAttribute("Error", e);
        } catch (JsonProcessingException e) {
            log.log(Level.ERROR, "Error during parsing to JSON", e);
            jo.put("redirect", "error");
            HttpSession session = request.getSession(true);
            session.setAttribute("Error", e);
        } catch (IOException e) {
            log.log(Level.ERROR, e);
            jo.put("redirect", "error");
            HttpSession session = request.getSession(true);
            session.setAttribute("Error", e);
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
        byte[] header = ByteBuffer.allocate(4).putInt(0xFFBBCCDD).array();
        byte[] length = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(jsonString.length()).array();
        byte[] json = jsonString.getBytes(StandardCharsets.UTF_16LE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(header);
        out.write(length);
        out.write(json);
        log.log(Level.INFO, "header: " + DatatypeConverter.printHexBinary(header));
        log.log(Level.INFO, "length: " + DatatypeConverter.printHexBinary(length));
        log.log(Level.INFO, "json: " + DatatypeConverter.printHexBinary(json));
        s = new Socket(addr, port);
        s.getOutputStream().write(out.toByteArray());
    }
}
