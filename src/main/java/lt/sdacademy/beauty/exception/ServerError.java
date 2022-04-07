package lt.sdacademy.beauty.exception;

import lombok.Getter;
import java.util.Map;

@Getter
public class ServerError extends RuntimeException{

    private final String message;
    private Map<String, String> item;

    public ServerError(String message, Map<String, String> item) {
        this.message = message;
        this.item = item;
    }

    public ServerError(String message, String exceptionMessage) {
        super(exceptionMessage);
        this.message = message;
    }
}
