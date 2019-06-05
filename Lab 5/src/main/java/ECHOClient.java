import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ECHOClient {

    private static final String host = "localhost";
    private static final int port = 59898;

    public static void main(String[] args) throws IOException {
        try (var socket = new Socket(host, port)) {
            System.out.println("Client connected to " + host + " at port " + port);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String string = reader.readLine();
                if (string.equals("exit")) {
                    break;
                }
                out.writeUTF(string);
                System.out.println(in.readUTF());
            }

            System.out.println("Closing the connection on " + host + " at port " + port + "...");
        }
    }
}
