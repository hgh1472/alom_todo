package spring.todo.exception;

public enum ErrorConst {
    DUPLICATE_EXCEPTION("DUPLICATE", "이미 존재하는 회원입니다."),
    EMPTY_EXCEPTION("EMPTY","이메일 또는 비밀번호는 필수입니다."),
    WRONG_EXCEPTION("WRONG","잘못된 이메일 또는 비밀번호입니다.");

    private final String code;
    private final String message;

    ErrorConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
