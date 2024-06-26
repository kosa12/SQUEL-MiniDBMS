package server;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class Server_GUI extends JFrame {
    private final JTextArea logTextArea;
    private Server server;

    public Server_GUI() {
        setTitle("Server GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(950, 350));

        JButton start = new JButton("START SERVER");
        start.setPreferredSize(new Dimension(460,50));
        start.setBackground(new Color(239, 240, 243));

        JButton stop = new JButton("STOP SERVER");
        stop.setPreferredSize(new Dimension(460,50));
        stop.setBackground(new Color(239, 240, 243));

        start.addActionListener(_ -> startServer());

        stop.addActionListener(_ -> stopServer());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(start);
        buttonPanel.add(stop);

        logTextArea = new JTextArea();
        logTextArea.setFont(new Font("Cfont", Font.PLAIN, 20));
        logTextArea.setEditable(false);
        logTextArea.setBackground(new Color(239, 240, 243));

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();


        buttonPanel.setBackground(new Color(75, 104, 178));

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/server.png");
        setIconImage(icon);

        this.setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startServer() {
        server = new Server();
        redirectOutput();
        server.start();
    }

    private void stopServer() {
        if(this.server!=null){
            logMessage("Server is shutting down...");
            System.exit(0);
        }
    }

    private void redirectOutput() {
        RedirectOutputStream redirectOutputStream = new RedirectOutputStream(logTextArea);
        System.setOut(new PrintStream(redirectOutputStream));
    }

    private void logMessage(String message) {
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    static class RedirectOutputStream extends OutputStream {
        private final JTextArea textArea;

        public RedirectOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Server_GUI());
    }
}