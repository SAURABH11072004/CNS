import java.net.*;
import java.io.*;

public class SimpleClient {
    public static void main(String args[]) throws IOException {
        Socket client = new Socket("localhost", 1254);
        System.out.println("Connected to server.");
        DataInputStream is = new DataInputStream(client.getInputStream());
        String line = is.readUTF();
        System.out.println("Server says: " + line);
        is.close();
        client.close();
        System.out.println("Client closed connection.");
    }
}
