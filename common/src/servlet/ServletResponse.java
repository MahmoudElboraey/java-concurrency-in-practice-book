package servlet;

/** Minimal stand-in for javax.servlet.ServletResponse. */
public interface ServletResponse {
    void write(String body);
}
