package in.frol.nats.messaging.consumer.launcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.frol.nats.messaging.client.NatsConnections;
import in.frol.nats.messaging.common.NatsMessagingConsumerException;
import in.frol.nats.messaging.consumer.handler.MessageHandlerConsumerNats;
import in.frol.nats.messaging.consumer.payload.MessagePayload;
import in.frol.nats.messaging.consumer.subject.SubjectNatsMessaging;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static in.frol.frutils.Objects.isNull;

public class HandlerLauncherConsumerNatsImpl
        implements HandlerLauncherConsumerNats {

    private static final long QUEUE_POLL_TIMEOUT_MS = 1000L;

    private final NatsConnections natsConnections;
    private final ObjectMapper objectMapper;
    private final Executor executor = Executors.newCachedThreadPool();

    public HandlerLauncherConsumerNatsImpl(final NatsConnections natsConnections,
                                           final ObjectMapper objectMapper) {
        this.natsConnections = natsConnections;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T extends MessagePayload> void launch(MessageHandlerConsumerNats<T> handler) {
        LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        Connection connection = natsConnections.getConnection();
        Dispatcher dispatcher = connection.createDispatcher(queue::offer);
        SubjectNatsMessaging pipeline = handler.pipeline();
        dispatcher.subscribe(pipeline.subject(), pipeline.queue());
        CompletableFuture.runAsync(() -> handleMessages(connection, handler, queue), executor);
    }

    private <T extends MessagePayload> void handleMessages(final Connection connection,
                                                           final MessageHandlerConsumerNats<T> handler,
                                                           final LinkedBlockingQueue<Message> queue) {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Message message = queue.poll(QUEUE_POLL_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                if (message == null) {
                    /* no message yet, skip iteration */
                    continue;
                }
                T processed = handler.handle(message);
                SubjectNatsMessaging pipeline = handler.pipeline();
                if (pipeline.isFireAndForgetType()) {
                    /* no need to send a response */
                    continue;
                }
                if (isNull(processed)) {
                    String inputPayload = new String(message.getData(), StandardCharsets.UTF_8);
                    throw new NatsMessagingConsumerException("Handler processing result NULL, input message payload: " + inputPayload);
                }

                String processedJson = objectMapper.writeValueAsString(processed);
                byte[] processedData = processedJson.getBytes(StandardCharsets.UTF_8);
                connection.publish(message.getReplyTo(), processedData);
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    @Override
    public void handleException(Exception e) {
        /* do nothing */
    }
}
