package pl.KJJS.app;

public class Result<T> {
    public T token;
    public String rest;
    public Result(T token, String rest) {
        this.token = token;
        this.rest = rest;
    }

    public Boolean isOk() {
        return !(token == null);
    }
}