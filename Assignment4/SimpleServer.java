import java.net.*;
import java.io.*;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1254);
        System.out.println("Server is running. Waiting for a client...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Thread to receive messages from client
        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while (!(message = in.readUTF()).equalsIgnoreCase("exit")) {
                    System.out.println("Client: " + message);  // Print messages FROM client
                }
                System.out.println("Client ended the chat.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });

        // Thread to send messages to client
        Thread sendThread = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String message;
                while (!(message = br.readLine()).equalsIgnoreCase("exit")) {
                    System.out.println("Server: " + message);  // Print your own sent message
                    out.writeUTF(message);
                }
                out.writeUTF("exit");
                System.out.println("Server ended the chat.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });

        receiveThread.start();
        sendThread.start();
    }
}
