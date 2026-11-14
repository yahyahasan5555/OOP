@FunctionalInterface // an interface with exactly one abstract method.
public interface Notifier {
    void send(String message);

    default void sendTwice(String message) {
        send(message);
        send(message);
    }

    default void sendUrgent(String message) {
        send(addPrefix(message));
    }

    private String addPrefix(String message) {
            return "[URGENT] " + message;
    }

    static boolean isValidMessage(String message) {
        return !message.isBlank();
    }
}
