package codeexamples;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import servlet.Servlet;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;

@ThreadSafe
public class CachedFactorizer implements Servlet {


    @GuardedBy("this") private BigInteger[] factors ;
    @GuardedBy("this") private  BigInteger lastNumber;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cachedHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCachedHitRatio(){
        return (double)cachedHits / (double)hits;
    }

    public void service (ServletRequest request , ServletResponse response){
        BigInteger i = extractFromRequest(request);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cachedHits;
                factors = this.factors.clone(); /// note here
            }
        }

        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                this.lastNumber = i;
                this.factors = factors.clone();

            }

        }
        encodeIntoResponse(response, factors);

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
