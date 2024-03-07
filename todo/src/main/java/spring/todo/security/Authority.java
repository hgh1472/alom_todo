package spring.todo.security;

public enum Authority {
    MEMBER("MEMBER");

    private String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
}
