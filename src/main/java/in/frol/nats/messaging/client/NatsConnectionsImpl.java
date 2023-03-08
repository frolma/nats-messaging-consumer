package in.frol.nats.messaging.client;

import in.frol.nats.messaging.common.NatsMessagingConsumerException;
import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.nats.client.ErrorListener;
import io.nats.client.Nats;
import io.nats.client.Options;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class NatsConnectionsImpl implements NatsConnections {

    private final NatsMessagingProperties natsMessagingProperties;
    private final ErrorListener errorListener;
    private final ConnectionListener connectionListener;
    private final Collection<Connection> connections = new LinkedBlockingQueue<>();

    private Options options;

    public NatsConnectionsImpl(final NatsMessagingProperties natsMessagingProperties,
                               final ErrorListener errorListener,
                               final ConnectionListener connectionListener) {
        this.natsMessagingProperties = natsMessagingProperties;
        this.errorListener = errorListener;
        this.connectionListener = connectionListener;
    }

    public void init() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Options.Builder builderOptions = new Options.Builder()
                .server(natsMessagingProperties.host())
                .errorListener(errorListener)
                .connectionListener(connectionListener)
                .maxReconnects(natsMessagingProperties.maxReconnects())
                .maxMessagesInOutgoingQueue(natsMessagingProperties.maxMessageOutQueue())
                .executor(executor);
        if (natsMessagingProperties.discardMessagesWhenQueueFull()) {
            builderOptions.discardMessagesWhenOutgoingQueueFull();
        }
        options = builderOptions.build();
    }

    @Override
    public Connection getConnection() {
        try {
            Connection connection = Nats.connect(options);
            connections.add(connection);
            return connection;
        } catch (IOException e) {
            throw new NatsMessagingConsumerException("IOException occurred while trying to get connection", e);
        } catch (InterruptedException e) {
            throw new NatsMessagingConsumerException("InterruptedException occurred while trying to get connection", e);
        }
    }

    public void teardown() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (InterruptedException e) {
                throw new NatsMessagingConsumerException("Failed to close NATS connection", e);
            }
        }
    }
}
