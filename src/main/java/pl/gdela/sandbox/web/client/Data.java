package pl.gdela.sandbox.web.client;

import org.apache.commons.io.LineIterator;
import pl.gdela.sandbox.web.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.apache.commons.io.IOUtils.lineIterator;

public class Data {

    public static final int NUMBER_OF_LINES = 100000;

    /**
     * Simulates streaming write of large file to the output stream.
     */
    public static void writeRequestBodyTo(OutputStream outputStream) {
        Log.info("writing request");
        int linesToWrite = NUMBER_OF_LINES;
        PrintStream request = new PrintStream(outputStream);
        for (int i = 0; i < linesToWrite; i++) {
            request.println("Lorem ipsum dolor sit amet, consectetur adipiscing elit: " + i);
            Log.tick();
        }
        Log.info("written request of " + linesToWrite + " lines");
    }

    /**
     * Simulates streaming read of large response.
     */
    public static void readResponseBodyFrom(InputStream inputStream) throws IOException {
        Log.info("reading response");
        LineIterator response = lineIterator(inputStream, "UTF-8");
        int linesRead = 0;
        while (response.hasNext()) {
            response.nextLine(); // ignore actual contents
            linesRead++;
            Log.tick();
        }
        Log.info("read response of " + linesRead + " lines");
    }
}
