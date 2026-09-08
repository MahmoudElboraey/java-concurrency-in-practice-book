package codeexamples;

import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger [] lastFactors;
    public OneValueCache(BigInteger lastNumber, BigInteger [] lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
    }

    public BigInteger [] getFactors(BigInteger number) {
        if (lastNumber == null || !lastNumber.equals(number)) {
            return null;
        }
        return Arrays.copyOf(lastFactors, lastFactors.length);
    }

}
