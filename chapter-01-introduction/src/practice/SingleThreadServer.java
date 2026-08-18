package practice;


import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SingleThreadServer {

    private static final int PORT = 9090;

    public static void main(String[] args)  {

        try (ServerSocket server = new ServerSocket(PORT)){
            while (true) {
                Socket socket = server.accept();
                InputStream in = socket.getInputStream(); // bytes comes from the client
                OutputStream out = socket.getOutputStream(); // bytes to client
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in , StandardCharsets.UTF_8));
                String line = null;
                System.out.println("getting info from the client ");
                while ( (line = bufferedReader.readLine() )  != null) {
                    System.out.println(line);
                }

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }




    }
}
