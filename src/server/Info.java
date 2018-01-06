package server;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Info extends JFrame{
    static JLabel ipText;
    static JLabel portText;
    Info(){


        try {
           ipText = new JLabel("Server IP: "+getLocalIpAddress());
        }catch(UnknownHostException e){
            System.out.println("unknown localhost!");
        }

        portText=new JLabel("Server Port: 6789");
        setLayout(new FlowLayout());
        add(ipText);
        add(portText);
        setBounds(100,100,200,100);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static String getLocalIpAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();

        return address.getHostAddress();
    }



}

