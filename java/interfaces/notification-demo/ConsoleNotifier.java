public class ConsoleNotifier implements Notifier{
    @Override
    public void send(String name) {
        System.out.println("Console: " + name);
    }
}
