package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class Dialogue  extends JDialog implements ActionListener{

    //ui
    private JLabel port_label;
    private JLabel message_label;
    private JTextField port_text;
    private JButton sure;
    private JButton close;

    //info
    private String port = "6789";
    private String message = "第一步：连接Server端";

    public Dialogue(JFrame parent){
        super(parent, "Server", true);

        port_label = new JLabel("Port:");
        message_label = new JLabel();
        port_text = new JTextField(port, 15);

        sure = new JButton("确定");
        sure.addActionListener(this);
        close = new JButton("关闭");
        close.addActionListener(this);
        message_label = new JLabel(message);
        message_label.setHorizontalAlignment(JLabel.CENTER);

        JPanel contentPanel = (JPanel)getContentPane();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new GridLayout(3, 1));


        JPanel port_panel = new JPanel();
        port_panel.setLayout(new FlowLayout());
        port_panel.add(port_label);
        port_panel.add(port_text);
        contentPanel.add(port_panel);

        contentPanel.add(message_label);

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new FlowLayout());
        button_panel.add(sure);
        button_panel.add(close);
        contentPanel.add(button_panel);
    }

    public void actionPerformed(ActionEvent e){
        Object obj = e.getSource();
        if(obj == (Object)sure){
            port = port_text.getText();
        }
        dispose();
    }


    public int getPort(){
        return Integer.parseInt(port);
    }
}

