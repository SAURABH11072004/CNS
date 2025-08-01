import java.net.*;
import java.io.*;

public class SimpleServer {
    public static void main(String args[]) throws IOException {
        ServerSocket s = new ServerSocket(1254);
        System.out.println("Server is running. Waiting for a client...");
        Socket s1 = s.accept();
        System.out.println("Client connected!");
        OutputStream s1out = s1.getOutputStream();
        DataOutputStream dos = new DataOutputStream(s1out);
        dos.writeUTF("Hi Saurabh\nWelcome to the Java Socket Program.\nHave a great day! ");
        dos.close();
        s1out.close();
        s1.close();
        System.out.println("Connection closed.");
    }
}
