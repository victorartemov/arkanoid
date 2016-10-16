package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Game;

/**
 * Created by Viktor_Artemov on 10/13/2016.
 */
public class Controller {
    @FXML
    private AnchorPane gamePart;

    @FXML
    private AnchorPane bottomPart;

    @FXML
    private SplitPane mainWindow;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private TextField ballsCountField;

    @FXML
    private Label scoreField;

    public AnchorPane getGamePart() {
        return gamePart;
    }

    public SplitPane getMainWindow() {
        return mainWindow;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getPauseButtonButton() {
        return pauseButton;
    }

    public TextField getBallsCountField() {
        return ballsCountField;
    }

    public Label getScoreField() {
        return scoreField;
    }

    public AnchorPane getBottomPart() {
        return bottomPart;
    }

    @FXML
    protected void handleStartButtonClick(MouseEvent event){
        Game.running = true;
    }

    @FXML
    protected void handlePauseButtonClick(MouseEvent event){
        if(pauseButton.getText().equals("Pause")) {
            Game.running = false;
            pauseButton.setText("Resume");
        } else {
            Game.running = true;
            pauseButton.setText("Pause");
        }
    }

    @FXML
    protected void handleBallsCountFieldChange(InputMethodEvent event){

    }
}
