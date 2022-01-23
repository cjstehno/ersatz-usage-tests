package ersatz

import io.github.cjstehno.ersatz.GroovyErsatzServer
import io.github.cjstehno.ersatz.junit.ErsatzServerExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.github.cjstehno.ersatz.cfg.ContentType.TEXT_PLAIN
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

@ExtendWith(ErsatzServerExtension)
class GroovyUsageTest {

    private GroovyErsatzServer server

    @Test void usage() {
        server.expectations {
            GET('/hello') {
                called 1
                query 'name', 'Groovy'
                responder {
                    body 'Hi, Groovy', TEXT_PLAIN
                }
            }
        }

        assertEquals('Hi, Groovy', new URL(server.httpUrl('/hello?name=Groovy')).text)
        assertTrue server.verify()
    }
}