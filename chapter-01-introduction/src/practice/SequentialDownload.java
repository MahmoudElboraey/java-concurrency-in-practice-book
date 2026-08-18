package practice;

import java.io.InputStream;
import java.net.URL;

public class SequentialDownload {

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

    public static void main(String[] args) {

        long timeBefore = System.currentTimeMillis();
        for (String url : URLS){
            fetch(url);
        }
        long timeAfter = System.currentTimeMillis();
        System.out.println(timeAfter - timeBefore);

    }
}
