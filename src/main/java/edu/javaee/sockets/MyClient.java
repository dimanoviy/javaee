package edu.javaee.sockets;

import java.io.*;
import java.net.Socket;

/**
 * This client based on sockets is for Server testing
 */
public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bufferedWriter.write("GET\n");
        bufferedWriter.flush();

        String curLine ;
        do {
            System.out.println(bufferedReader.readLine());
        } while (bufferedReader.ready());
        bufferedWriter.close();
        socket.close();


    }
}
