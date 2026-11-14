public class Main {
    public static void main(String[] args) {
        // 1. Polymorphism --> every element is a Notifier, but each one is a diff class.
        Notifier[] notifiers = {
            new EmailNotifier(),
            new SmsNotifier(),
            new PushNotifier(),
            new ConsoleNotifier()
        };

        System.out.println("-------Polymorphism------------");
        for(Notifier notifier: notifiers) {
            notifier.send("Hello");

            // log() exists only in the Loggable,
            // so check the real type then case.
            if(notifier instanceof Loggable)
                ((Loggable) notifier).log("message sent");
        }

        // 2. default methods
        System.out.println("------Default Methods-----------");
        new EmailNotifier().sendTwice("Twice"); // default interface method
        new SmsNotifier().sendTwice("Twice"); // default overridden interface method


        // 3. Loose Coupling: the AlterService Only knows the notifier interface
        System.out.println("----------Alter Service---------------");
        AlertService emailAlerts = new AlertService(new EmailNotifier());
        AlertService smsAlerts = new AlertService(new SmsNotifier());
        AlertService consoleAlerts = new AlertService(new ConsoleNotifier());

        emailAlerts.raiseAlert("Server Down");
        smsAlerts.raiseAlert("Server Down");
        consoleAlerts.raiseAlert("Server Down");
        consoleAlerts.raiseAlert("           "); // blank string --> no messages is sent.
    }
}