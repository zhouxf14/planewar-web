package server;

import javax.swing.*;
import java.awt.*;

public class ServerStart extends JFrame{
    public ServerStart(){
//    	setFocusable(true);
        setTitle("巫师飞机大战-Server");
        setLocationRelativeTo(null);
        Dialogue dialogue = new Dialogue(this);
        dialogue.pack();
        dialogue.setLocationRelativeTo(null);
        dialogue.setVisible(true);

        PlaneWar planeWar = new PlaneWar(dialogue.getPort());
        planeWar.setFocusable(true);
        add(planeWar);
        pack();
    }

    public static void main(String[] args) {
        ServerStart serverStart = new ServerStart();
        serverStart.setSize(518,708);
        serverStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverStart.setAlwaysOnTop(true);
        serverStart.setVisible(true);
    }
}