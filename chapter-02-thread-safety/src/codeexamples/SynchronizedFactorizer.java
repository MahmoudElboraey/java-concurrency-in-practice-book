package codeexamples;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;


@ThreadSafe
public class SynchronizedFactorizer {
    @GuardedBy("this") private BigInteger[] factors ;
    @GuardedBy("this") private  BigInteger lastNumber;


    public synchronized void service (ServletRequest request , ServletResponse response){
        BigInteger i = extractFromRequest(request);
        if (i.equals(lastNumber)) {
            encodeIntoResponse(response, factors);
        }else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            this.factors =factors;
            encodeIntoResponse(response, factors);
        }
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
