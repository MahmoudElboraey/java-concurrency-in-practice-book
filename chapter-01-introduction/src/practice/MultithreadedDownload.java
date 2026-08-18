package practice;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MultithreadedDownload {

    private static final String[] URLS = {
            "https://www.google.com",
            "https://www.github.com",
            "https://www.wikipedia.org",
            "https://www.stackoverflow.com",
            "https://www.amazon.com"
    };

    private static void fetch(String url) {
        try (InputStream in = new URL(url).openStream()) {
            byte[] buffer = new byte[8192];
            long total = 0;
            int read;
            while ((read = in.read(buffer)) != -1) {
                total += read;
            }
            System.out.println(url + " -> " + total + " bytes");
        } catch (Exception e) {
            System.out.println(url + " failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add (new LocalThread(URLS[i]));
        }

        long timeBefore = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i < 5; i++) {
            threads.get(i).join();
        }
        long timeAfter = System.currentTimeMillis();
        System.out.println(timeAfter - timeBefore);

    }

    private static class LocalThread extends Thread {
        private final String url;

        public LocalThread(String url) {
            this.setName(this.getClass().getSimpleName() + " " +  url);
            this.setPriority(MAX_PRIORITY);
            this.url = url;
        }


        @Override
        public void start (){
            System.out.println("thread  " + this.getName() + " is starting");
            super.start();
        }

        @Override
        public void run(){
            fetch(url);
        }
    }
}
