package codeexamples;

import servlet.Servlet;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;

public class UnsafeCountingFactorizer implements Servlet {
    private long count = 0;

    public long getCount (){
        return count;
    }

    public void service (ServletRequest request , ServletResponse response){
        BigInteger i = extractFromRequest(request);
        BigInteger [] factors = factor(i);
        ++count;
        encodeIntoResponse(response , factors);
    }

    private BigInteger extractFromRequest(ServletRequest request){
        // do some work here
        return new BigInteger(request.getParameter("value"));
    }

    private void encodeIntoResponse(ServletResponse response, BigInteger[] factors) {
        // do something
    }

    private BigInteger[] factor(BigInteger i) {
        // do some work here
        return new BigInteger[]{i};
    }

}
