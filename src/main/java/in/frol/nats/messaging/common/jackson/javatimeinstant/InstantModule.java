package in.frol.nats.messaging.common.jackson.javatimeinstant;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.Instant;

public class InstantModule extends SimpleModule {

    public InstantModule() {
        super();
        addSerializer(Instant.class, new InstantSerializer());
        addDeserializer(Instant.class, new InstantDeserializer());
    }
}
