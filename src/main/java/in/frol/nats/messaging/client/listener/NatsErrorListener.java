package in.frol.nats.messaging.client.listener;

import io.nats.client.Connection;
import io.nats.client.Consumer;
import io.nats.client.ErrorListener;

public class NatsErrorListener implements ErrorListener {

    @Override
    public void errorOccurred(Connection conn, String error) {
        /* NATS client error occurred:'%s'; connection status: %s",
         error, conn.getStatus().toString() */
    }

    @Override
    public void exceptionOccurred(Connection conn, Exception exp) {
        /* "NATS client exception occurred:'%s'; connection status: %s; %s",
                exp.getMessage(), conn.getStatus().toString(), exp) */
    }

    @Override
    public void slowConsumerDetected(Connection conn, Consumer consumer) {
        /*
        boolean active = consumer.isActive();
        long deliveredCount = consumer.getDeliveredCount();
        long pendingMessageCount = consumer.getPendingMessageCount();
        long pendingMessageLimit = consumer.getPendingMessageLimit();
        long droppedCount = consumer.getDroppedCount();
        "NATS client slow consumer detected. connection status: %s;"
           + " deliveredCount: %d; pendingMessageCount: %d; pendingMessageLimit: %d;"
           + " droppedCount: %d; active: %s",
        conn.getStatus(), deliveredCount, pendingMessageCount, pendingMessageLimit, droppedCount, active); */
    }
}
