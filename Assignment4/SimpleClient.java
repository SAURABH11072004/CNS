import java.net.*;
import java.io.*;

public class SimpleClient{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1254);
        System.out.println("Connected to server.");

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Thread to receive messages from server
        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while (!(message = in.readUTF()).equalsIgnoreCase("exit")) {
                    System.out.println("Server: " + message);  // Print messages FROM server
                }
                System.out.println("Server ended the chat.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });

        // Thread to send messages to server
        Thread sendThread = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String message;
                while (!(message = br.readLine()).equalsIgnoreCase("exit")) {
                    System.out.println("Client: " + message);  // Print your own sent message
                    out.writeUTF(message);
                }
                out.writeUTF("exit");
                System.out.println("Client ended the chat.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });

        receiveThread.start();
        sendThread.start();
    }
}
