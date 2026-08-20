package servlet;

/**
 * Minimal stand-in for javax.servlet.Servlet so JCIP book examples compile
 * without a servlet-api dependency. The container (not modeled here) calls
 * service() concurrently from many threads — that is the whole point of the
 * book's servlet examples.
 */
public interface Servlet {
    void service(ServletRequest req, ServletResponse resp);
}
