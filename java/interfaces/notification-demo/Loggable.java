@FunctionalInterface
public interface Loggable {
    String getLogName();

    default void log(String text) {
        System.out.println("[LOG: " + getLogName() + "] " + text);
    }
}
