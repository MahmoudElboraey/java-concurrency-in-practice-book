package codeexamples;

public class LoggingWidget extends Widget{
    public synchronized void doSomething(){
        System.out.println("doSomething child class  " + toString());
        super.doSomething();
    }
}
