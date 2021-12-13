package foodie.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Please login!");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
