package org.wazea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main( String[] args ) throws IOException {

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("\nEnter the type of the request(Or hit Enter to exit) : ");
            String request = reader.readLine();
            if (request.length() == 0) {
                break;
            }
            Dispatcher.makeRequest(request);
        }
    }
}
