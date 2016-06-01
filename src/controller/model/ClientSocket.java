package controller.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by luigi on 5/20/16.
 */
class ClientSocket {
    public Socket socket ;
    public OutputStream os;
    public InputStream is;

    public ClientSocket(Socket socket){
        try {
            this.socket = socket;
            os = this.socket.getOutputStream();
            is = this.socket.getInputStream();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
