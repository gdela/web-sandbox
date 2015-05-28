package pl.gdela.sandbox.web.server;

import org.apache.commons.io.LineIterator;
import org.apache.commons.io.output.DeferredFileOutputStream;
import pl.gdela.sandbox.web.Log;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.apache.commons.io.IOUtils.lineIterator;

/**
 * For each line read from request writes to response the length of this line.
 * This servlet stores response in intermediate store, until all data is read from response,
 */
public class LineLengthsServletFixed extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.info("starting servlet work");

        ServletInputStream input = request.getInputStream();
        ServletOutputStream output = response.getOutputStream();

        // prepare intermediate store
        int threshold = 100 * 1024; // 100 kB before switching to file store
        File file = File.createTempFile("intermediate", "");
        DeferredFileOutputStream intermediate = new DeferredFileOutputStream(threshold, file);

        // process request to intermediate store
        PrintStream intermediateFront = new PrintStream(new BufferedOutputStream(intermediate));
        LineIterator lineIt = lineIterator(input, "UTF-8");
        while (lineIt.hasNext()) {
            String line = lineIt.next();
            intermediateFront.println(line.length());
            Log.tick();
        }
        intermediateFront.close();

        // request fully processed, so now it's time to send response
        intermediate.writeTo(output);

        file.delete();
        Log.info("servlet work finished");
    }
}
