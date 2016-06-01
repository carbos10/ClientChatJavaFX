package controller.view;

import controller.MainApp;
import controller.model.Client;
import controller.model.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Created by luigi on 5/10/16.
 */
public class ChatOverviewController {

    private Client client;

    @FXML
    private TextArea textChat;

    @FXML
    private TextField textArea;

    @FXML
    private Button myButton;

    private MainApp mainApp;

    public ChatOverviewController(){

    }

    public void setClient(Client client){
        this.client = client;
    }

    @FXML
    private void initialize()
    {
        textChat.setWrapText(true);
        myButton.setOnAction((event) -> {
            client.send(textArea.getText());
            textArea.clear();
        });

        textArea.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                client.send(textArea.getText());
                textArea.clear();
            }
        });
    }

    public void addText(Message message)
    {
        textChat.appendText(message.toString());

    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

}
