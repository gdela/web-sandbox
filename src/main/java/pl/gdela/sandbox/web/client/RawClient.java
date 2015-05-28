package pl.gdela.sandbox.web.client;

import pl.gdela.sandbox.web.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static pl.gdela.sandbox.web.client.Data.readResponseBodyFrom;
import static pl.gdela.sandbox.web.client.Data.writeRequestBodyTo;

/**
 * Client written using low-level HttpURLConnection.
 */
public class RawClient {

    public static void main(String[] args) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/line-lengths").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setChunkedStreamingMode(1024); // enables streaming of request body

        // request
        writeRequestBodyTo(connection.getOutputStream());

        // response
        logResponseHeaders(connection);
        readResponseBodyFrom(connection.getInputStream());
    }

    private static void logResponseHeaders(HttpURLConnection connection) {
        Log.info("response headers:");
        for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
            Log.info(entry);
        }
    }
}
