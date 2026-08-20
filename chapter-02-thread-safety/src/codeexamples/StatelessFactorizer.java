package codeexamples;

import servlet.Servlet;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;


public class StatelessFactorizer implements Servlet {

    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger [] factors = factor(i);
        encodeIntoResponse(resp , factors);
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        // do some work here
        return new BigInteger(req.getParameter("request"));
    }

    private BigInteger[] factor(BigInteger i) {
        // do some work here
        return new BigInteger[]{i};
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
       // do some work here
    }
}
