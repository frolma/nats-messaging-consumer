package in.frol.nats.messaging.consumer.handler;

import in.frol.nats.messaging.consumer.payload.MessagePayload;
import in.frol.nats.messaging.consumer.subject.SubjectNatsMessaging;
import io.nats.client.Message;

public interface MessageHandlerConsumerNats<T extends MessagePayload> {

    T handle(Message message);

    SubjectNatsMessaging pipeline();

}
