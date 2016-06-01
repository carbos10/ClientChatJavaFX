package controller.model;

import java.io.Serializable;

/**
 * Created by luigi on 5/20/16.
 */
public class Message implements Serializable {

    public String name;
    public String text;

    public Message(String name , String text){
        this.name = name;
        this.text = text;
    }

    public String toString()
    {
        return this.name+" : "+this.text+"\r\n";
    }
}
