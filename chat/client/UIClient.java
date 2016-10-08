package ru.itacademy.chat.client;

import ru.itacademy.chat.server.Server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by асер.
 */
public class UIClient {
    private static final String HOST = "127.0.0.1";
    private static final String WELCOME = "Welcome, ";

    private String host;
    private int port;
    private Socket socket;
    private BufferedReader serverReader;
    private PrintWriter serverWriter;
    private String name;

    public UIClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Server.ENCODING));
        serverWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Server.ENCODING));
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception ignore) {
        }
        try {
            serverReader.close();
        } catch (Exception ignore) {
        }
        try {
            serverWriter.close();
        } catch (Exception ignore) {
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        UIClient uiClient = new UIClient(HOST, Server.PORT);

        JFrame frame = new JFrame("Чат");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        center(frame);

        JTextArea serverTextField = new JTextArea();
        serverTextField.setEditable(false);
        JTextField ourTextField = new JTextField();
        JButton sendButton = new JButton("Отправить");
        frame.getRootPane().setDefaultButton(sendButton);

        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.gridheight = 10;
        constraints.gridx = 0;
        constraints.gridy = 0;
        frame.add(serverTextField, constraints);

        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 10;
        frame.add(ourTextField, constraints);

        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 11;
        frame.add(sendButton, constraints);

        frame.setVisible(true);

        sendButton.addActionListener((event) -> {
            try {
                String text = ourTextField.getText();
                if (text.length() != 0) {
                    String senderPrefix = (uiClient.getName() != null) ? "[" + uiClient.getName() + "] " : "";
                    uiClient.sendToServer(text);
                    serverTextField.append(senderPrefix + text + "\n");
                    ourTextField.setText("");
                }
            } catch (IOException ex) {
                uiClient.close();
            }
        });

        new Thread(() -> {
            try {
                while (true) {
                    try {
                        uiClient.connect();
                        serverTextField.append("Connected to server\n");

                        if (uiClient.getName() != null) {
                            uiClient.readLine();
                            uiClient.sendToServer(uiClient.getName());
                            uiClient.setName(null);
                        }

                        while (true) {
                            while (uiClient.hasServerData()) {
                                String line = uiClient.readLine();

                                if (line.startsWith(WELCOME)) {
                                    uiClient.setName(line.substring(WELCOME.length()));
                                }

                                serverTextField.append(line + "\n");
                            }

                            Thread.sleep(100);
                        }
                    } catch (IOException e) {
                        serverTextField.append("Server connection lost\n");
                    }

                    Thread.sleep(5000);
                }
            } catch (InterruptedException ignore) {
            }
        }).start();
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean hasServerData() throws IOException {
        return serverReader.ready();
    }

    private String readLine() throws IOException {
        return serverReader.readLine();
    }

    public void sendToServer(String text) throws IOException {
        serverWriter.println(text);
        serverWriter.flush();

        if (serverWriter.checkError()) {
            throw new IOException("Connection lost");
        }
    }

    private static void center(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
