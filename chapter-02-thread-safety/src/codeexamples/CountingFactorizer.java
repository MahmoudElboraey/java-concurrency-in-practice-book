package codeexamples;

import servlet.Servlet;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CountingFactorizer implements Servlet {
    private final AtomicLong counter = new AtomicLong(0);

    public long getCount (){
        return counter.get();
    }

    public void service (ServletRequest request , ServletResponse response){
        BigInteger i = extractFromRequest(request);
        BigInteger [] factors = factor(i);
        counter.incrementAndGet();
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
