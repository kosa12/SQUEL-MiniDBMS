package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JMenuItem;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;
        Client_GUI gui = new Client_GUI();
        Socket[] socketHolder = new Socket[1];

        JMenuItem executeButton = gui.getExecuteButton();
        executeButton.addActionListener(e -> {
            try {
                Socket socket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String message = gui.getjTextField();
                out.println(message);
                socketHolder[0] = socket;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        JMenuItem exitButton = gui.getExitButton();
        exitButton.addActionListener(e -> {
            Socket socket = socketHolder[0];
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                gui.dispose();
            }
        });
    }
}