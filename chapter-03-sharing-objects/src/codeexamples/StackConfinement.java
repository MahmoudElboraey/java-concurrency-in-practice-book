package codeexamples;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class StackConfinement {


    public int loadTheArk(Collection<Animal> candidates){
        SortedSet<Animal> sortedSet = new TreeSet<>(candidates);
        Animal candidate = null;

        int numPairs = 0;
        for (Animal animal : sortedSet){
            if (candidate == null || !candidate.isPoliteMate(animal) ){
                candidate = animal;
            }else{
                numPairs++;
                candidate = null;
            }

        }

        return numPairs;
    }
}
