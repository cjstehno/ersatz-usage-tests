package ersatz;

import io.github.cjstehno.ersatz.ErsatzServer;
import io.github.cjstehno.ersatz.junit.ErsatzServerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static io.github.cjstehno.ersatz.cfg.ContentType.TEXT_PLAIN;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ErsatzServerExtension.class)
class JavaUsageTest {

    private ErsatzServer server;

    @Test void usage() throws Exception {
        server.expectations(expect -> {
            expect.GET("/hello", req -> {
                req.called(1);
                req.query("name", "Java");
                req.responder(res -> {
                    res.body("Hi, Java", TEXT_PLAIN);
                });
            });
        });

        final var response = newHttpClient().send(
            newBuilder(new URI(server.httpUrl("/hello?name=Java"))).GET().build(),
            ofString()
        );

        assertEquals("Hi, Java", response.body());
        assertTrue(server.verify());
    }
}
