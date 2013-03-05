package com.gsr.myschool.server.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShutdownServlet extends HttpServlet {
    private static final String PARAM = "evil";
    private static final String SHUTDOWN = "kill";
    private static final String START = "bringToLife";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicationContext context = ApplicationContextProvider.get();

        if (request.getParameter(PARAM).equals(SHUTDOWN)) {
            ((XmlWebApplicationContext)context).close();
        } else if (request.getParameter(PARAM).equals(START)) {
            ((XmlWebApplicationContext)context).start();
            ((XmlWebApplicationContext)context).refresh();
        }
    }
}
