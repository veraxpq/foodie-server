package foodie.exceptions;

public class PermissionDenyException extends RuntimeException {
    public PermissionDenyException() {
        super("无权限");
    }

    public PermissionDenyException(String message) {
        super(message);
    }
}
