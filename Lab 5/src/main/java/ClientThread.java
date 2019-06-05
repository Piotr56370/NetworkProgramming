import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;

    ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("New Client Thread has been created!");

            ECHOServer.getInstance().addClientThread(this);

            while (true) {
                String line = in.readUTF();
                if (line.equals("exit")) {
                    break;
                }

                System.out.println(this.getName() + " wrote : " + line);
                Dispatcher.processMessage(out, line);
            }

            ECHOServer.getInstance().removeClientThread(this);
            socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}