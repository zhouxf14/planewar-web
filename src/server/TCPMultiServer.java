package server;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMultiServer {

    static Socket[] list=new Socket[2];
    static int nClientNum = 0;
    public static void main(String[] args) throws Exception{
        new Info();
        ServerSocket ssocketWelcome = new ServerSocket(6789);
        int decision=(int)(Math.random()*2);
        decision=decision%2;

        while(nClientNum<2){


            Socket socketServer = ssocketWelcome.accept();
            list[nClientNum]=socketServer;

            nClientNum ++;
        }

        new DataOutputStream(TCPMultiServer.list[0].getOutputStream()).writeBytes(decision+"\n");
        System.out.println("decision1"+decision);
        decision=(decision+1)%2;
        new DataOutputStream(TCPMultiServer.list[1].getOutputStream()).writeBytes(decision+"\n");
        System.out.println("decision2"+decision);

        TCPServerThread thread1 =new TCPServerThread(list[0],list[1],1);

        TCPServerThread thread2 =new TCPServerThread(list[1],list[0],2);
        thread2.start();
        thread1.start();



        System.out.println(decision);





    }
}
