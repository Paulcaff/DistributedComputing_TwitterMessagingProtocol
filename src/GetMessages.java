import Client.TwitterMessageSendProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetMessages {

    private JTextArea textAreaMessages;
    private JButton btnAddMessages;
    private JLabel lblGetMessages;
    private JButton btnLogout;
    private JPanel GetMessagesPanel;
    private JButton btnDisplayMessages;
    private String username;
    private TwitterMessageSendProtocol clienthelper;


    public GetMessages(String username, TwitterMessageSendProtocol clienthelper) {
        //GUI SETUP
        JFrame frame = new JFrame("Twitter Messaging Protocol");
        frame.setContentPane(GetMessagesPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.username = username;
        this.clienthelper = clienthelper;

        //Add Message button action Listener which calls the relevant GUI page
        btnAddMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                SendMessage sendMessage = new SendMessage(username,clienthelper);
            }
        });

        //Display  button used to get messages of the server
        btnDisplayMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                        /* the reply string is the message recieved from the server in its entire form
                        the valid string is the first 3 characters of the string return which lets us
                        know the result of the reply from the server
                        the all messages string is the remainder of the string which is the messages
                        returned from the server.
                        they are divided by a hyphen(-) which denotes the end of a message and start of
                        a new one
                         */
                        String reply = clienthelper.GetMessage(username);
                        String valid = reply.substring(0,3);
                        String allMessages = reply.substring(4,reply.length());

                        if(valid.equals("400")){
                            String[] splitString = allMessages.split("-");
                            for (int i = 0; i < splitString.length; i++) {

                                String messageToBeAdded = splitString[i];
                                textAreaMessages.append(messageToBeAdded +"\n");
                            }
                        }

                        else if(valid.equals("401")){
                            JOptionPane.showMessageDialog(null,"No Messages Found");
                        }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //Button used for log off
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    String valid = clienthelper.LogOff(username);
                    if(valid.equals("500")) {
                        frame.dispose();
                        //clienthelper.done();
                        Login loginNew = new Login();
                    }
                    else if(valid.equals("501"))
                    {
                        JOptionPane.showMessageDialog(null, "Trouble Logging off");
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
