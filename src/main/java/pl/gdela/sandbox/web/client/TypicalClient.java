package pl.gdela.sandbox.web.client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import pl.gdela.sandbox.web.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static pl.gdela.sandbox.web.client.Data.readResponseBodyFrom;
import static pl.gdela.sandbox.web.client.Data.writeRequestBodyTo;

/**
 * TypicalClient written using Apache HttpClient without special support for full duplex communication.
 */
public class TypicalClient {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/line-lengths");

        // request
        post.setEntity(new StreamingEntity() {
            public void writeTo(OutputStream out) throws IOException {
                writeRequestBodyTo(out);
            }
        });
        HttpResponse response = client.execute(post);

        // response
        logResponseHeaders(response);
        readResponseBodyFrom(response.getEntity().getContent());
    }

    private static void logResponseHeaders(HttpResponse response) {
        Log.info("response headers:");
        for (Header header : response.getAllHeaders()) {
            Log.info(header);
        }
    }

    private abstract static class StreamingEntity extends AbstractHttpEntity {

        public abstract void writeTo(OutputStream out) throws IOException;

        public boolean isStreaming() {
            return true;
        }

        public long getContentLength() {
            return -1;
        }

        public boolean isRepeatable() {
            return false;
        }

        public InputStream getContent() {
            throw new UnsupportedOperationException();
        }
    }
}
