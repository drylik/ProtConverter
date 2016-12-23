package ru.novikov.http;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.novikov.jaxb.Envelope;
import ru.novikov.jaxb.parse.JaxbParser;
import ru.novikov.jaxb.parse.Parser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XmlParserServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(XmlParserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xml = request.getParameter("xml");
        Parser parser = new JaxbParser();
        Envelope env = (Envelope) parser.getObject(IOUtils.toInputStream(xml, StandardCharsets.UTF_8), Envelope.class);
        if (env == null) {
            response.sendError(0);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher index = request.getRequestDispatcher("/index.html");
        index.forward(request, response);
    }
}
