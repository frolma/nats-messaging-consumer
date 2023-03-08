package in.frol.nats.messaging.client.listener;

import io.nats.client.Connection;
import io.nats.client.ConnectionListener;

public class NatsConnectionListener implements ConnectionListener {

    @Override
    public void connectionEvent(Connection conn, Events events) {
        /* empty body */
    }
}
