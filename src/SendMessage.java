import Client.TwitterMessageSendProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SendMessage {
    private String username;
    private JTextArea txtAreaMessage;
    private JButton btnAddMessage;
    private JLabel lblAddMessage;
    private JPanel AddMessage;
    private JButton btnGetMessages;
    private JButton btnLogout;
    private TwitterMessageSendProtocol clienthelper;


    public SendMessage(String username,TwitterMessageSendProtocol clienthelper) {

        JFrame frame = new JFrame("Twitter Messaging Protocol");
        frame.setContentPane(AddMessage);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.username = username;
        this.clienthelper = clienthelper;


        btnAddMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    String message = txtAreaMessage.getText();

                    if(!message.equals("")){

                        String valid = clienthelper.SendMessage(message,username);

                        if(valid.equals("300")){
                            frame.dispose();
                            JOptionPane.showMessageDialog(null,"Message has been saved with other messages");
                            SendMessage sendMessage = new SendMessage(username,clienthelper);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"No Message to send");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        //Calls the Get Messages GUI
        btnGetMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                GetMessages getMessages = new GetMessages(username,clienthelper);

            }
        });

        //Logs the user out by sending a message across to the server and processing the message sent back
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
                    else
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
