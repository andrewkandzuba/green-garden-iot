package le.arn;

import le.arn.GreetingProtos.Greeting;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static le.arn.GreetingProtos.Greeting.newBuilder;
import static org.junit.Assert.*;

public class GreetingProtoTest {

    private Greeting greeting;

    @Before
    public void setUp() throws Exception {
        greeting = newBuilder().setGreeting("Hello world!").build();
    }

    @Test
    public void serDes() throws IOException {
        byte[] serialized = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            greeting.writeTo(baos);
            serialized = baos.toByteArray();
        }

        assertNotNull(serialized);
        assertTrue(serialized.length > 0);

        Greeting parsed = Greeting.parseFrom(serialized);
        assertEquals(parsed, greeting);
    }
}
