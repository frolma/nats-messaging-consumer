package in.frol.nats.messaging.common.jackson.javatimeinstant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.time.Instant;

import static in.frol.frutils.Functions.unchecked;
import static in.frol.frutils.Objects.neNull;

public class InstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(final Instant value,
                          final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) {
        neNull(value, Instant::toEpochMilli, unchecked(val -> {
            jsonGenerator.writeNumber(val);
            return null;
        }));
    }
}
