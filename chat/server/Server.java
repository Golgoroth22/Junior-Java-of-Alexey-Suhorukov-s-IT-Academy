package ru.itacademy.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by асер.
 */
public class Server {
    public static final String ENCODING = "UTF-8";
    public static final int PORT = 8888;

    private final ServerSocket server;
    private final List<ClientInfo> clients;

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public Server() throws IOException {
        server = new ServerSocket(PORT);
        clients = new ArrayList<>();
        dateFormat.setLenient(false);
    }

    public void start() throws IOException {
        while (true) {
            final Socket clientSocket = server.accept();
            final ClientInfo clientInfo = new ClientInfo();
            clientInfo.setSocket(clientSocket);
            clients.add(clientInfo);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), ENCODING));
                        clientInfo.setTimeZone(reader.readLine());
                        dateFormat.setTimeZone(TimeZone.getTimeZone(clientInfo.getTimeZone()));
                        askNickname(reader, clientInfo);

                        while (true) {
                            String line = reader.readLine();
                            Date date = new Date();
                            sendToClients(clientInfo, "[" + dateFormat.format(date) + "][" + clientInfo.getName() + "] " + line);
                        }
                    } catch (IOException ignore) {
                    }

                    clients.remove(clientInfo);
                }
            }).start();
        }
    }

    private void askNickname(BufferedReader reader, ClientInfo clientInfo) throws IOException {
        boolean uniqueNickname;

        do {
            sendToClient(clientInfo, "Enter nickname");
            String nickname = reader.readLine();
            uniqueNickname = true;

            for (ClientInfo otherClient : clients) {
                if (nickname.equals(otherClient.getName()) || nickname.equals("")) {
                    uniqueNickname = false;
                    sendToClient(clientInfo, "Nickname " + nickname + " is not avaliable, enter other nikname:");
                    break;
                }
            }

            if (uniqueNickname) {
                clientInfo.setName(nickname);
            }
        } while (!uniqueNickname);

        sendToClients(clientInfo, clientInfo.getName() + " entered");
        sendToClient(clientInfo, "Welcome, " + clientInfo.getName());
    }

    private void sendToClients(ClientInfo exceptClientInfo, String line) throws IOException {
        for (ClientInfo otherClientInfo : clients) {
            if (otherClientInfo != exceptClientInfo && otherClientInfo.getName() != null) {
                sendToClient(otherClientInfo, line);
            }
        }
    }

    private void sendToClient(ClientInfo clientInfo, String line) throws IOException {
        PrintWriter otherWriter = new PrintWriter(new OutputStreamWriter(clientInfo.getSocket().getOutputStream(), ENCODING));
        otherWriter.println(line);
        otherWriter.flush();
    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
