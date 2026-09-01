package codeexamples;

import jdk.jfr.Event;

import java.util.EventListener;

public class ThisEscape {

    public ThisEscape(EventSource source) {
        source.registerEventLister(
        new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
    }

    private void doSomething(Event e) {
    }


}
