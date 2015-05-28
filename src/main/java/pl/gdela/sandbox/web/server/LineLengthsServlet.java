package pl.gdela.sandbox.web.server;

import org.apache.commons.io.LineIterator;
import pl.gdela.sandbox.web.Log;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.io.IOUtils.lineIterator;

/**
 * For each line read from request writes to response the length of this line.
 * This servlet streams directly to the client.
 */
public class LineLengthsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.info("starting servlet work");

        ServletInputStream input = request.getInputStream();
        ServletOutputStream output = response.getOutputStream();

        LineIterator lineIt = lineIterator(input, "UTF-8");
        while (lineIt.hasNext()) {
            String line = lineIt.next();
            output.println(line.length());
            Log.tick();
        }
        output.flush();

        Log.info("servlet work finished");
    }
}
