import java.net.*;
import java.io.*;

public class SimpleClient23 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1254);
        System.out.println("Connected to server.");

        // Input and output streams
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Thread to receive messages
        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while (!(message = in.readUTF()).equalsIgnoreCase("exit")) {
                    System.out.println("Server: " + message);
                }
                System.out.println("Server ended the chat.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        });

        // Thread to send messages
        Thread sendThread = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String message;
                while (!(message = br.readLine()).equalsIgnoreCase("exit")) {
                    out.writeUTF( message);
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
