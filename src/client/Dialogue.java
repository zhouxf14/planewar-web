package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogue extends JDialog implements ActionListener {

    private JLabel ip_label;
    private JLabel port_label;
    private JLabel message_label;
    private JTextField ip_text;
    private JTextField port_text;
    private JButton sure;
    private JButton close;

    //info
    private String ip = "127.0.0.1";
    private String port = "6789";
    private String message = "第二步：连接Client端";


    public Dialogue(JFrame parent){
        super(parent, "Client", true);

        ip_label = new JLabel("Server IP(不填则默认为127.0.0.1):");
        port_label = new JLabel("Port:");
        message_label = new JLabel();
        ip_text = new JTextField(15);
        port_text = new JTextField(port, 15);

        sure = new JButton("确定");
        sure.addActionListener(this);
        close = new JButton("关闭");
        close.addActionListener(this);
        message_label = new JLabel(message);
        message_label.setHorizontalAlignment(JLabel.CENTER);

        JPanel contentPanel = (JPanel)getContentPane();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new GridLayout(4, 1));

        JPanel ip_panel = new JPanel();
        ip_panel.setLayout(new FlowLayout());
        ip_panel.add(ip_label);
        ip_panel.add(ip_text);
        contentPanel.add(ip_panel);

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
        if(obj == sure){
            ip = ip_text.getText();
            port = port_text.getText();
        }
        dispose();
    }

    public String getIp(){
        ip = ip_text.getText();
//    	ip = "127.0.0.1";
        return ip;
    }

    public int getPort(){
        port = port_text.getText();
        return Integer.parseInt(port);
    }
}

