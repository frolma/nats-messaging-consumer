package in.frol.nats.messaging.consumer.launcher;

import in.frol.nats.messaging.consumer.handler.MessageHandlerConsumerNats;
import in.frol.nats.messaging.consumer.payload.MessagePayload;

public interface HandlerLauncherConsumerNats {

    <T extends MessagePayload> void launch(MessageHandlerConsumerNats<T> messageHandlerConsumerNats);

    void handleException(Exception exception);

}
