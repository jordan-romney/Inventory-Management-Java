package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This is the Main class of the program.
 * Everything starts here.
 * Unique Part and Product ID variables are created to allow for access throughout the program.
 *
 * FUTURE ENHANCEMENT: One feature that would enhance this application would be the ability to output information to a new file.
 * This would allow a user to output the whole list of parts and products or just a single Part or Product with its attributes.
 *
 * The JavaDoc folder is located inside the jordan_romney_C482_PA zip file
 */
public class Main extends Application {
    public static int uniquePartId = 1;
    public static int uniqueProductId = 100;

    /**
     * The start method for the program.
     * MainForm.fxml is loaded, creates a stage and scene, then displays the scene on the stage.
     * @param primaryStage sets the main stage for the program
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }

    /**
     * The method that returns to the MainForm from another scene.
     * MainForm.fxml is loaded, calls a stage, creates a scene, and then displays the scene on the stage.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * @param actionEvent
     * @throws IOException
     */
    public static void returnToMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * The entry point of the application.
     * @param args
     */
     public static void main(String[] args){
       launch(args);
    }

}
