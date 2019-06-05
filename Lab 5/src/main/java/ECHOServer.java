import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ECHOServer {

    private static final int port = 59898;
    private static final ECHOServer instance = new ECHOServer();
    private static List<ClientThread> threadList = new ArrayList<>();
    private static List<String> messageList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(port)) {
            System.out.println("The server is running on port " + port + "...");
            while (true) {
                var client = listener.accept();
                ClientThread thread = new ClientThread(client);
                thread.start();
            }
        }
    }

    static ECHOServer getInstance() {
        return instance;
    }

    void addClientThread(ClientThread thread) {
        synchronized (this) {
            threadList.add(thread);
        }
    }

    void removeClientThread(ClientThread thread) {
        synchronized (this) {
            threadList.remove(thread);
        }
    }

    void addMessage(String message) {
        synchronized (this) {
            messageList.add(message);
        }
    }

    void removeMessage(String message) {
        synchronized (this) {
            messageList.remove(message);
        }
    }

    int getThreadCount() {
        synchronized (this) {
            return threadList.size();
        }
    }

    String getMessages() {
        synchronized (this) {
            return String.join("\n", messageList);
        }
    }
}

