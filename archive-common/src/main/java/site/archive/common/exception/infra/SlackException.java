package site.archive.common.exception.infra;

public class SlackException extends RuntimeException {

    public SlackException(String message) {
        super(message);
    }

}
