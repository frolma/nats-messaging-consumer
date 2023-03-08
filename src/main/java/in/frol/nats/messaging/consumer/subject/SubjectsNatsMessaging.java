package in.frol.nats.messaging.consumer.subject;

public class SubjectsNatsMessaging {

    public static final SubjectNatsMessaging TIME_UPDATE_OFFSET = new SubjectNatsMessaging(
            "outbox.service.time", "update.offset", TypeSubjectNatsMessaging.AT_LEAST_ONCE);

    public static final SubjectNatsMessaging NOTIFICATION_EMAIL_SUPPORT = new SubjectNatsMessaging(
            "inbox.service.notification", "email.support", TypeSubjectNatsMessaging.FIRE_AND_FORGET);

}
