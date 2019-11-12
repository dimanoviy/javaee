package edu.javaee.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyServer {
    public static void main(String[] args)  throws IOException {
            ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            Socket client = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String request = bufferedReader.readLine();
            if (request.contains("GET")) {
                browseDirectory(bufferedWriter);
            } else {
                bufferedWriter.write("404");
            }
            bufferedWriter.flush();

            bufferedWriter.close();
            bufferedReader.close();
            client.close();
        }
    }

    private static void browseDirectory(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("Listing of files and directories in current directory:\n");
        String dirPath = "./";
        DirectoryStream<Path> dirListing = Files.newDirectoryStream(Paths.get(dirPath));
        for (Path entry : dirListing) {
            bufferedWriter.write(entry.getFileName().toString() + "\n");
        }
    }
}
