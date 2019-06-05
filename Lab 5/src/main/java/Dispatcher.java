import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

final class Dispatcher {

    static void processMessage(DataOutputStream out, String request) throws IOException {
        // Remove redundant spaces
        request = request.trim();

        String response = request + "\n";
        String command;
        String message;

        if (request.contains(" ")) {
            command = request.substring(0, request.indexOf(" "));
            message = request.substring(request.indexOf(" ") + 1);
        } else {
            command = request;
            message = "";
        }

        switch (command){
            case "help":
                response += "help - available commands :\n" +
                            "about - display some text about the system\n" +
                            "threads - display number of active threads\n" +
                            "time - display the current time on server\n" +
                            "add String - adds a message in the list\n" +
                            "rem String - removes a message from the list\n" +
                            "print-msg - prints all the messages\n";
                break;

            case "about":
                response += "Client Server App\n Author : Wazea\n";
                break;

            case "threads":
                response += "Total active threads on the server: " + ECHOServer.getInstance().getThreadCount();
                break;

            case "time":
                response += "Current server time: " + new Date().toString();
                break;

            case "add":
                ECHOServer.getInstance().addMessage(message);
                response += "Your message has been saved on the server!";
                break;

            case "print-msg":
                response += ECHOServer.getInstance().getMessages();
                break;
        }

        out.writeUTF(response);
    }
}