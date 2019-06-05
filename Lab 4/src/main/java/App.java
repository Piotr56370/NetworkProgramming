import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) {
        String email = "";
        String password = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("=== Simple email client ===");
            System.out.println("To begin enter your gmail credentials : ");
            System.out.println("Email Address : ");
            email = br.readLine();
            System.out.println("Password : ");
            password = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User tempUser = new User(email, password);
        TLSEmail tlsEmail = new TLSEmail(tempUser);
        SSLEmail sslEmail = new SSLEmail(tempUser);
        ReadEmail readEmail = new ReadEmail(tempUser);
    }
}
