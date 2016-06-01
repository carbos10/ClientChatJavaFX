package controller;

import controller.model.Client;
import controller.view.ChatOverviewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;


/**
 * Created by luigi on 5/8/16.
 */
public class MainApp extends Application {

    private String name;
    private String server;
    private String port;

    private Stage primaryStage;
    private BorderPane rootLayout;

    private Client client;
    private ChatOverviewController controller;

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chat");

        initRootLayout();
        showDialogOverview();

        client = new Client(this.server, Integer.parseInt(this.port), this.name);

        showChatOverview();

    }

    public void initRootLayout(){
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void showDialogOverview(){
        // Set dialog
        Dialog<HashMap<String, String>> dialog = new Dialog();
        dialog.setTitle("Impostazioni");
        dialog.setHeaderText("Inserisci nome utente e indirizzo server");

        // Set the button
        ButtonType okButtonType = new ButtonType("Invia", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create labels
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        TextField serverUrl = new TextField();
        serverUrl.setPromptText("IP Server");

        TextField port = new TextField();
        port.setPromptText("Port");

        grid.add(new Label("Username: "), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("IP Server"), 0, 1);
        grid.add(serverUrl, 1, 1);
        grid.add(new Label("Port"), 0, 2);
        grid.add(port, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus() );

        dialog.setResultConverter(dialogButton -> {
           if(dialogButton == okButtonType) {
               HashMap<String, String> temp = new HashMap<String, String>();
               temp.put("username", username.getText());
               temp.put("server", serverUrl.getText());
               temp.put("port", port.getText());
               return temp;
           }
            return null;
        });

        Optional<HashMap<String, String>> result = dialog.showAndWait();

        result.ifPresent(data -> {
            this.name = data.get("username");
            this.server = data.get("server");
            this.port = data.get("port");
        });
    }

    public void showChatOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ChatOverview.fxml"));
            AnchorPane chatOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(chatOverview);

            controller = loader.getController();
            controller.setClient(this.client);
            controller.setMainApp(this);
            this.client.setControllerChat(controller);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
