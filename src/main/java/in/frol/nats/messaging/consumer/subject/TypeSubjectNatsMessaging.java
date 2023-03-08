package in.frol.nats.messaging.consumer.subject;

/**
 * Behavior which implementation has to supports
 * for messages from the corresponding category.
 */
public enum TypeSubjectNatsMessaging {

    /**
     * Service is able to restore message on demand
     */
    RECOVERY,

    /**
     * Service is responsible to success message delivery
     * <p>
     * Service re-sends message by timeout
     * <p>
     * The only single subscriber and request-response pattern
     */
    AT_LEAST_ONCE,

    /**
     * Service fires event and the lost messages are not an issue
     */
    FIRE_AND_FORGET,

    /**
     * NO DATA
     */
    NONE

}
