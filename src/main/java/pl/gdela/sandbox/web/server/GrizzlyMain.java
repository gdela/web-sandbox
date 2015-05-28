package pl.gdela.sandbox.web.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

/**
 * Starts line lengths servlet in Grizzly container.
 */
public class GrizzlyMain {

    public static void main(String[] args) throws Exception {
        WebappContext context = new WebappContext("main", "");

        ServletRegistration sr1 = context.addServlet("line-lengths", LineLengthsServlet.class);
        sr1.addMapping("/line-lengths");

        ServletRegistration sr2 = context.addServlet("line-lengths-fixed", LineLengthsServletFixed.class);
        sr2.addMapping("/line-lengths-fixed");

        HttpServer server = HttpServer.createSimpleServer();
        context.deploy(server);
        server.start();

        System.out.println("Hit ENTER to stop server");
        System.in.read();
        server.stop();
    }
}
