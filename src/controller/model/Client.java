package controller.model;

import controller.view.ChatOverviewController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by luigi on 5/20/16.
 */
public class Client {

    private String name;
    private ObjectOutputStream oos;
    public ClientSocket socket;
    public ChatOverviewController controller;

    public Client(String host, int port, String name){
        try {
            this.name = name;
            Socket socket = new Socket(host, port);
            this.socket = new ClientSocket(socket);
            this.oos = new ObjectOutputStream(this.socket.os);
            ThreadClient tc = new ThreadClient();
            tc.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setControllerChat(ChatOverviewController controller)
    {
        this.controller = controller;
    }

    public void send(String message)
    {
        try {
            this.oos.writeObject(new Message(name, message));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public class ThreadClient extends Thread
    {

        public void run()
        {
            try {
                ObjectInputStream obj = new ObjectInputStream(socket.is);
                Message ob;
                while((ob =(Message) obj.readObject()) != null)
                {
                    controller.addText(ob);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
