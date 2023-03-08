package in.frol.nats.messaging.consumer.handler;


import in.frol.frutils.Functions;
import in.frol.nats.messaging.consumer.payload.EmptyMessagePayload;
import in.frol.nats.messaging.consumer.subject.SubjectNatsMessaging;
import in.frol.nats.messaging.consumer.subject.SubjectsNatsMessaging;
import io.nats.client.Message;


public class EmptyMessageHandlerConsumer
        implements MessageHandlerConsumerNats<EmptyMessagePayload> {

    @Override
    public EmptyMessagePayload handle(final Message message) {
        return Functions.<EmptyMessagePayload>emptySupplier()
                .get();
    }

    @Override
    public SubjectNatsMessaging pipeline() {
        return SubjectsNatsMessaging.NOTIFICATION_EMAIL_SUPPORT;
    }
}
