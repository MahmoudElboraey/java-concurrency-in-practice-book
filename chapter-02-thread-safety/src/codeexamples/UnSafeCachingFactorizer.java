package codeexamples;

import net.jcip.annotations.NotThreadSafe;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;


@NotThreadSafe
public class UnSafeCachingFactorizer {
    private final AtomicReference<BigInteger[]> factors = new AtomicReference<>(new BigInteger[]{});
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();


    public void service (ServletRequest request , ServletResponse response){
        BigInteger i = extractFromRequest(request);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(response, factors.get());
        }else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            this.factors.set(factors);
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
