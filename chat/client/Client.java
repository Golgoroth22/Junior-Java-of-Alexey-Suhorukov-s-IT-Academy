package ru.itacademy.chat.client;

import ru.itacademy.chat.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.TimeZone;

/**
 * Created by асер.
 */
public class Client {
    private static final String HOST = "127.0.0.1";
    static TimeZone timeZone = TimeZone.getTimeZone("America/Chicago");

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket(HOST, Server.PORT);
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Server.ENCODING));
        PrintWriter serverWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Server.ENCODING));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Server.ENCODING));

        //serverWriter.println(timeZone.toString());

        while (true) {
            if (reader.ready()) {
                String line = reader.readLine();
                serverWriter.println(line);
                serverWriter.flush();
            }

            while (serverReader.ready()) {
                String line = serverReader.readLine();
                System.out.println(line);
            }

            Thread.sleep(100);
        }
    }
}
