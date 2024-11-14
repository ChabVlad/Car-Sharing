package project.carsharing.exception;

public class SendingNotificationException extends RuntimeException {
    public SendingNotificationException(String message) {
        super(message);
    }
}
