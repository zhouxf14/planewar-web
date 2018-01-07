package client;

import javax.swing.*;
import java.awt.*;

public class ClientStart extends JFrame {
    public ClientStart(){
//    	setFocusable(true);
        setTitle("巫师飞机大战-Client");
        Dialogue dialogue = new Dialogue(this);
        dialogue.pack();
        dialogue.setLocationRelativeTo(null);
        dialogue.setVisible(true);

        PlaneWar planeWar = new PlaneWar(dialogue.getIp(),dialogue.getPort());
        planeWar.setFocusable(true);
        add(planeWar);
        setAlwaysOnTop(true);
//        planeWar.frame.setSize(WIDTH, HEIGHT);
//        Container container = getContentPane();
//        container.add(planeWar);
        setLocationRelativeTo(null);
        pack();
    }

    public static void main(String[] args) {
    	ClientStart clientStart = new ClientStart();
    	clientStart.setSize(518,708);
    	clientStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	clientStart.setVisible(true);
    }
}
