public class PushNotifier implements Loggable, Notifier{
    @Override
    public void send(String message) {
        System.out.println("Push: " + message);
    }

    @Override
    public String getLogName() {
        return "PushNotifier";
    }
}
