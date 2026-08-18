package practice;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ThreadPerConnectionServer {

    private static int PORT = 9090;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){ // one per server

            while (true ){
                Socket socket = serverSocket.accept();
                Thread thread = new Thread (() -> {

                    try {
                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , StandardCharsets.UTF_8));
                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8 ) , true);

                        String line = bufferedReader.readLine();

                        printWriter.println("i received this line " + line);
                    }catch (IOException e){
                        System.out.println(e);
                    }

                });
                thread.start();
            }


        }catch (IOException e){

        }
    }
}
