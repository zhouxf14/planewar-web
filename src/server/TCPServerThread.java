package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPServerThread extends Thread {
    Socket socket;
    int nClientNum;
    Socket otherSocket;
    String temp;

    public TCPServerThread(Socket s, Socket s2,int i){
        socket = s;
        otherSocket=s2;
        nClientNum=i;
    }
    BufferedReader brInFromClient;
    DataOutputStream dosOutToClient;

    public void run() {
        try{
            brInFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dosOutToClient = new DataOutputStream(otherSocket.getOutputStream());

            do{
                temp = brInFromClient.readLine();
                System.out.println("Client"+nClientNum+": "+ temp);

                dosOutToClient.writeBytes(temp + '\n');
                System.out.println("FromClient"+nClientNum+": "+ temp);

            } while(!temp.equals("99"));



            socket.close();
            Info.ipText.setText("Server IP: CLOSED");
            Info.portText.setText("Server Port: CLOSED");
        }catch(Exception e){}
    }
}
