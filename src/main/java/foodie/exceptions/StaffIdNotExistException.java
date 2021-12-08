package foodie.exceptions;

public class StaffIdNotExistException extends RuntimeException{
    public StaffIdNotExistException() {
        super("工号不存在");
    }

    public StaffIdNotExistException(String message) {
        super(message);
    }
}
