package at.piwa.homemonitoring.network;

public class ModemConnectionException extends Exception {

    public ModemConnectionException(Exception e) {
        super(e);
    }

    public ModemConnectionException(String message) {
        super(message);
    }
}
