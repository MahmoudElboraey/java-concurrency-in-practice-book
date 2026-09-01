package codeexamples;

import java.util.HashSet;
import java.util.Set;

public class PublishingObject {
    public static Set<Secret> knownSecrets;

    public void initialize(){
        knownSecrets = new HashSet<>();
    }

}
