package foodie.exceptions;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
        super("服务器内部错误，请重试或联系管理员");
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
