package in.frol.nats.messaging.client;

import io.nats.client.Connection;

/**
 * Component distributing NATS connections.
 */
public interface NatsConnections {

    /**
     * @return NATS connection
     */
    Connection getConnection();

}
