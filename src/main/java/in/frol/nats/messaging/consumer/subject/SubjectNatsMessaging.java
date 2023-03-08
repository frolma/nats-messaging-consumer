package in.frol.nats.messaging.consumer.subject;

import static in.frol.frutils.Objects.anyNull;

public record SubjectNatsMessaging(
        String queue,
        String subject,
        TypeSubjectNatsMessaging type
) {

    public SubjectNatsMessaging {
        if (anyNull(queue, subject, type)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isFireAndForgetType() {
        return TypeSubjectNatsMessaging.FIRE_AND_FORGET.equals(this.type);
    }
}
