package spring.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;

    public ErrorResult(ErrorConst errorConst) {
        this.code = errorConst.getCode();
        this.message = errorConst.getMessage();
    }
}
