package codeexamples;

import jdk.jfr.Event;

import java.util.EventListener;

public class SafeListener {


    private final EventListener eventListener;


    private SafeListener(){
        eventListener = new EventListener() {
            public void handleEvent(Event e) {
                doSomthing(e);
            }
        };
    }

    private void doSomthing(Event e) {
    }


    public static SafeListener newInstance(EventSource source){
        SafeListener listener = new SafeListener();
        source.registerEventLister(listener.eventListener);
        return listener;
    }
}
