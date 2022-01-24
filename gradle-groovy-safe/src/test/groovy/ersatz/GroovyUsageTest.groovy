package ersatz

import io.github.cjstehno.ersatz.GroovyErsatzServer
import io.github.cjstehno.ersatz.junit.ErsatzServerExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.github.cjstehno.ersatz.cfg.ContentType.TEXT_PLAIN
import static org.hamcrest.CoreMatchers.startsWith
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

@ExtendWith(ErsatzServerExtension)
class GroovyUsageTest {

    private GroovyErsatzServer server

    @Test void usage() {
        server.expectations {
            GET(startsWith('/hello')) {
                called 1
                query 'name', 'Safe Groovy'
                responder {
                    body 'Hi, Safe Groovy', TEXT_PLAIN
                }
            }
        }

        assertEquals('Hi, Safe Groovy', new URL(server.httpUrl('/hello?name=Safe+Groovy')).text)
        assertTrue server.verify()
    }
}