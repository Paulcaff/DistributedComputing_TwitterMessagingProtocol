import Client.TwitterMessageSendProtocol;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login {
    private JLabel lblUsername;
    private JPanel Login;
    private JLabel lblHeader;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JTextField txtpassword;
    private JButton btnSubmit;
    private JLabel lblServerHost;
    private JLabel lblPortNo;
    private JTextField txtServerHost;
    private JTextField txtPortNumber;
    private TwitterMessageSendProtocol clientHelper;

    public Login() {
        //GUI SETUP
        JFrame frame = new JFrame("Twitter Messaging Protocol");
        frame.setContentPane(Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                /*
                This button gets the username and password which is sent across to be checked by the server
                I have used localhost and port number 7 as the defaults all the time
                it creates an instance of the protocol class and calls the login method to send the message
                across to the server
                The message recieved is assigned to the variable valid and depending on the result a different outcome
                occurs
                200 allows access 201 incorrect username or password 202 already logged in
                 */
                String serverhost = txtServerHost.getText();
                String portNo = txtPortNumber.getText();

                if(portNo.equals("")){
                    portNo = "7";
                }
                String username = txtUsername.getText();
                String password = txtpassword.getText();

                if(!username.equals("") && !password.equals("")) {
                    try {
                        if(clientHelper == null) {
                            clientHelper = new TwitterMessageSendProtocol("localhost", "7");
                        }
                        String valid = clientHelper.Login(username,password);

                        if(valid.equals("200")){
                            frame.dispose();
                            SendMessage sendMessage = new SendMessage(username,clientHelper);
                        }
                        else if(valid.equals("201")){
                            JOptionPane.showMessageDialog(null,"Incorrect Username or password");
                        }
                        else if(valid.equals("202")){
                            JOptionPane.showMessageDialog(null,"Already Logged in");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Error Occurred with log in");
                        }

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null,"Incorrect Username or password");
                }

            }
        });

    }
}
