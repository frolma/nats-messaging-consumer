package in.frol.nats.messaging.common.jackson.javatimeinstant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

import static in.frol.frutils.Objects.neNull;

public class InstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(final JsonParser p,
                               final DeserializationContext deserializationContext)
            throws IOException {
        return neNull(p.getNumberValue(), Number::longValue, Instant::ofEpochMilli);
    }
}
