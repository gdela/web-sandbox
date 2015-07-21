package pl.gdela.sandbox.web.client;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.InputStreamResponseListener;
import org.eclipse.jetty.client.util.OutputStreamContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import java.io.IOException;
import java.io.OutputStream;

import static pl.gdela.sandbox.web.client.Data.readResponseBodyFrom;
import static pl.gdela.sandbox.web.client.Data.writeRequestBodyTo;

/**
 * Client written using Jetty HttpClient.
 */
public class FullDuplexClient {
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.start();

        Request request = httpClient.newRequest("http://localhost:8080/line-lengths");
        final OutputStreamContentProvider contentProvider = new OutputStreamContentProvider();
        InputStreamResponseListener responseListener = new InputStreamResponseListener();

        request.content(contentProvider).method(HttpMethod.POST).send(responseListener); //async request
        httpClient.getExecutor().execute(new Runnable() {
            public void run() {
                try (OutputStream outputStream = contentProvider.getOutputStream()) {
                    writeRequestBodyTo(outputStream); //writing to stream in another thread
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        readResponseBodyFrom(responseListener.getInputStream()); //reading response
        httpClient.stop();
    }
}
