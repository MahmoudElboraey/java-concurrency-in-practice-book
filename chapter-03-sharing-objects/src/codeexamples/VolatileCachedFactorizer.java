package codeexamples;

import net.jcip.annotations.ThreadSafe;
import servlet.Servlet;
import servlet.ServletRequest;
import servlet.ServletResponse;

import java.math.BigInteger;

@ThreadSafe
public class VolatileCachedFactorizer implements Servlet {
    private volatile OneValueCache cache = new OneValueCache(null , null);

    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger [] factors = cache.getFactors(i);
        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i , factors);
        }

        encodeIntoResponse(resp , factors);

    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger(req.getParameter("value"));
    }

    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }
}
