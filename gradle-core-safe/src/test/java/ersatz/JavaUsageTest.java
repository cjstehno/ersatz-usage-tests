package ersatz;

import io.github.cjstehno.ersatz.ErsatzServer;
import io.github.cjstehno.ersatz.junit.ErsatzServerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URI;

import static io.github.cjstehno.ersatz.cfg.ContentType.TEXT_PLAIN;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ErsatzServerExtension.class)
class JavaUsageTest {

    @SuppressWarnings("unused") private ErsatzServer server;

    @Test void usage() throws Exception {
        server.expectations(expect -> {
            expect.GET(startsWith("/hello"), req -> {
                req.called(1);
                req.query("name", "Java");
                req.responder(res -> {
                    res.body("Hi, Java Safe", TEXT_PLAIN);
                });
            });
        });

        final var response = newHttpClient().send(
            newBuilder(new URI(server.httpUrl("/hello?name=Java"))).GET().build(),
            ofString()
        );

        assertEquals("Hi, Java Safe", response.body());
        assertTrue(server.verify());
    }
}
