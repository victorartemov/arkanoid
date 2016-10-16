package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;

/**
 * Created by Виктор on 10.10.2016.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../screen-layout.fxml"));
        Game game = new Game();
        game.runTheGame();
        Scene scene = new Scene(game.getRoot());
        scene.getStylesheets().add("dark-theme.css");
        primaryStage.resizableProperty().set(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jumping Sponge Bob and Friends");
        primaryStage.show();
    }

}
