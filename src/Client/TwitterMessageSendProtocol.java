package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TwitterMessageSendProtocol {
    static final String endMessage = ".";
    private MyStreamSocket mySocket;
    private InetAddress serverHost;
    private int serverPort;

    public TwitterMessageSendProtocol(String hostName,
                             String portNum) throws SocketException,
            UnknownHostException, IOException {

        this.serverHost = InetAddress.getByName(hostName);
        this.serverPort = Integer.parseInt(portNum);
        //Instantiates a stream-mode socket and wait for a connection.
        this.mySocket = new MyStreamSocket(this.serverHost,
                this.serverPort);
        System.out.println("Connection request made");
    } // end constructor

    //Starts the process of logging the user in by sending the code and the username and password which will
    //be checked by the server and a relevant code letting know of the result of the server
    public String Login(String username,String password) throws SocketException,
            IOException{
        String login = "Login"+username+password;
        mySocket.sendMessage(login);
        String msgReceived = mySocket.receiveMessage();
        return msgReceived;
    }
    //Method used to initiate the process of sending a message prefixed with the code to allow a user to saved
    // a message with the rest of their messages
    public String SendMessage(String message,String username) throws SocketException,
            IOException{
        String sendMsg = "SendM-"+username+"-"+message;
        mySocket.sendMessage(sendMsg);
        String msgReceived = mySocket.receiveMessage();
        return msgReceived;
    }
    // method used to send message to server to get the user's messages and recieve the code to known the result
    // plus the messages of the user to be displayed if allowed
    public String GetMessage(String username) throws SocketException,
            IOException{
        String getMsg = "GetMs-"+username;
        mySocket.sendMessage(getMsg);
        String msgReceived = mySocket.receiveMessage();
        return msgReceived;
    }
    // method used to send message to server to log the user off and recieve the code to let know the success
    // plus the messages of the user
    public String LogOff(String username) throws SocketException,
            IOException{
        String LogOf = "LogOf-"+username;
        mySocket.sendMessage(LogOf);
        String msgReceived = mySocket.receiveMessage();
        return msgReceived;
    }

    //Method to close the socket connection
    public void done( ) throws SocketException,
            IOException{
        mySocket.sendMessage(".");
        mySocket.close( );
    } // end done
} //end class



