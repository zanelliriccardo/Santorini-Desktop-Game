package it.polimi.ingsw.riccardoemelissa.exception;

public class SendException extends Exception {
    private String message_error;
    public SendException(String message)
    {
        message_error=message;
    }

    public String getMessage()
    {
        System.out.println(message_error);
        return null;
    }
}
