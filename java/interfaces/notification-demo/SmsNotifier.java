public class SmsNotifier implements Notifier{
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }

    @Override
    public void sendTwice(String message) {
        send(message);
        System.out.println("SMS is charged per message, so it was sent once");
    }
}
