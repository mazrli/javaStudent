package student.javastudent.user;

public class NoSuchUserError extends Throwable {
    public NoSuchUserError(String message ){
        super(message);
    }
}
