package codeexamples;

import net.jcip.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;

@Immutable
public class ThreeStooges {

    private final Set<String> stooges = new HashSet<>();

    public ThreeStooges() {
        stooges.add("mahmoud");
        stooges.add("will");
        stooges.add("get it done");
    }

    public boolean isStooge(String stooge) {
        return stooges.contains(stooge);
    }
}
