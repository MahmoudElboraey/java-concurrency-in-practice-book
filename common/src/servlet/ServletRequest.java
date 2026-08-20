package servlet;

/** Minimal stand-in for javax.servlet.ServletRequest. */
public interface ServletRequest {
    String getParameter(String name);
}
