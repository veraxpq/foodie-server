package foodie.exceptions;

public class PermissionDenyException extends RuntimeException {
    public PermissionDenyException() {
        super("没有操作权限");
    }

    public PermissionDenyException(String message) {
        super(message);
    }
}
