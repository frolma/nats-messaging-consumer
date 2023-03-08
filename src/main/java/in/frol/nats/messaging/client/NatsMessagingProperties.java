package in.frol.nats.messaging.client;

public record NatsMessagingProperties(
        Long requestTimeoutMs,
        String host,
        Integer maxReconnects,
        Integer maxMessageOutQueue,
        Boolean discardMessagesWhenQueueFull
) {
    /* empty body */
}
