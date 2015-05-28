package pl.gdela.sandbox.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Starts line lengths servlet in Jetty container.
 */
public class JettyMain {

    public static void main(String[] args) throws Exception {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(LineLengthsServlet.class, "/line-lengths");
        handler.addServletWithMapping(LineLengthsServletFixed.class, "/line-lengths-fixed");

        Server server = new Server(8080);
        server.setHandler(handler);
        server.start();

        System.out.println("Hit ENTER to stop server");
        System.in.read();
        server.stop();
    }
}
