package foodie.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("认证失败，请先登录");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
