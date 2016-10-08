package ru.itacademy.chat.server;

import java.net.Socket;

/**
 * Created by асер.
 */
public class ClientInfo {
    private Socket socket;
    private String name;
    private String timeZone;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setName(String name) {
        this.name = name;
    }
}
