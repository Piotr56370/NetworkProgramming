# Course: *Network Programming*
------
# Topic: *Sockets API Client Server App*
------
## Objectives :
1. Get familiar with Sockets API of the chosen language;

2. Develop a transfer protocol of data(messages), using the TCP;

## Protocol description : 

1. Service - The service provided by the server part is to implement some basic commands which return text responses, also to store the messages sent by the user;

2. Transport/Execution path - We have the client which formulates the request and it is sent by the TCP to the server, next the server creates a thread which processes the request and returns the response;

3. Vocabulary(the Commands) :
  * help - available commands;
  * about - display some text about the system;
  * threads - display number of active threads;
  * time - display the current time on server;
  * add String - adds a message in the list;
  * rem String - removes a message from the list;
  * print-msg - prints all the messages;

4. Request format - Command [Message];
  
## Structure overview :

1. **ECHOClient** - The class which makes the connection to the port on which operates the server and reads the commands introduced by the user. The connection is made in the following way :

~~~
 private static final String host = "localhost";
 private static final int port = 59898;
 
 var socket = new Socket(host, port);
~~~

The requests are written and read using an input and an output stream :

~~~
String string = reader.readLine();
if (string.equals("exit")) {
    break;
}
out.writeUTF(string);
System.out.println(in.readUTF());
~~~


2. **ECHOServer** - The class which makes the connection for the server. It has the list of the running threads and a list for the stored messages :

~~~
private static final int port = 59898;
private static final ECHOServer instance = new ECHOServer();
private static List<ClientThread> threadList = new ArrayList<>();
private static List<String> messageList = new ArrayList<>();
~~~

The connection : 

~~~
try (var listener = new ServerSocket(port)) {
    System.out.println("The server is running on port " + port + "...");
    while (true) {
        var client = listener.accept();
        ClientThread thread = new ClientThread(client);
        thread.start();
    }
}
~~~

Below are declared the coresponding methods for the commands e.g. here's the method for adding a message : 

~~~
void addMessage(String message) {
    synchronized (this) {
        messageList.add(message);
    }
}
~~~

They are synchronized so that we could have concurrent processing.


3. **ClientThread** - A class which extends Thread class and it is the blueprint of the threads created by the server. In the run method we add in the list the thread, passes the request to the Dispatcher and then removes the thread : 

~~~
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
~~~


4. **Dispatcher** - a final class with a static method which converts our request into 2 components, the message and command and then by a switch statement it computes a response which is written in the output stream of the client : 

~~~
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
~~~

