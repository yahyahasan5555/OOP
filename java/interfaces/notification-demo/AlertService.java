public class AlertService {
    private final Notifier notifier;

    public AlertService(Notifier notifier) {
        this.notifier = notifier;
    }

    public void raiseAlert(String text) {
        if(!Notifier.isValidMessage(text))
            System.out.println("Invalid message, nothing sent");
        else notifier.sendUrgent(text);
    }
}
