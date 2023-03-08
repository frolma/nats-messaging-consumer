package in.frol.nats.messaging.common;

public class NatsMessagingConsumerException extends RuntimeException {

    public NatsMessagingConsumerException() {
        /* empty body */
    }

    public NatsMessagingConsumerException(final String message) {
        super(message);
    }

    public NatsMessagingConsumerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
