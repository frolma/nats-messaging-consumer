package in.frol.nats.messaging;

import in.frol.nats.messaging.common.NatsMessagingConsumerException;
import in.frol.nats.messaging.consumer.handler.MessageHandlerConsumerNats;
import in.frol.nats.messaging.consumer.launcher.HandlerLauncherConsumerNats;

import java.util.Collection;
import java.util.Objects;

import static in.frol.frutils.Collections.hasNoItems;
import static in.frol.frutils.Objects.isNull;

public class NatsMessagingConsumerLauncher {

    private final Collection<? extends MessageHandlerConsumerNats> messageHandlers;
    private final HandlerLauncherConsumerNats handlerLauncherConsumerNats;

    public NatsMessagingConsumerLauncher(final Collection<? extends MessageHandlerConsumerNats> messageHandlers,
                                         final HandlerLauncherConsumerNats handlerLauncherConsumerNats) {
        this.messageHandlers = messageHandlers;
        this.handlerLauncherConsumerNats = handlerLauncherConsumerNats;
    }

    public void launchAllMessageHandlers() {
        if (hasNoItems(messageHandlers)) {
            throw new NatsMessagingConsumerException("Failed to launch NATS message handlers. Empty messageHandlers");
        }
        try {
            for (MessageHandlerConsumerNats<?> messageHandler : messageHandlers) {
                if (isNull(messageHandler.pipeline())) {
                    throw new NatsMessagingConsumerException();
                }
                Objects.requireNonNull(messageHandler.pipeline());
                handlerLauncherConsumerNats.launch(messageHandler);
            }
        } catch (Exception e) {
            throw new NatsMessagingConsumerException("Failed to launch NATS message handlers", e);
        }
    }
}
