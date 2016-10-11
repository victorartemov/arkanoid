package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Sprite;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Виктор on 10.10.2016.
 */
public class MainController extends Application{

    private final int screenHeight = 650;
    private final int screenWidth = 900;
    private final double pi = 3.14159265358;
    GraphicsContext gc;
    Sprite platform;
    Sprite ball;
    Sprite leftWall;
    Sprite topWall;
    Sprite rightWall;
    Sprite bottomWall;
    Random random;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Arcanoid");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(screenWidth,screenHeight);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        random = new Random();

        initializePlatform();
        initializeBall();

        scene.setOnMouseDragged(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if((event.getX() > platform.getWidth()/2) && (event.getX() < screenWidth - platform.getWidth()/2)) {
                            platform.setPositionX(event.getX() - 40);
                        }
                    }
                }
        );


        new AnimationTimer()
        {
            public void handle(long currentNanoTime) {
                gc.clearRect(0,0,screenWidth,screenHeight);
                platform.render(gc);
                ball.update();
                if(ball.getPositionY()<0) ball.setSpeed(0);
                ball.render(gc);

            }
        }.start();

        primaryStage.show();
    }

    public void initializePlatform(){
        platform = new Sprite();
        platform.setImage(new Image("platform.png"));
        platform.setAlignment(screenWidth/2-25,screenHeight-30,15,80);
        platform.render(gc);
    }

    public void initializeBall(){
        ball = new Sprite();
        ball.setImage(new Image("ball.png"));
        ball.setAlignment(screenWidth/2-10,screenHeight/2-10,20,20);
        ball.setSpeed(random.nextInt(5));
        ball.setAngle(random.nextDouble()*(-pi));
        ball.render(gc);
    }

    public void initializeWalls(){
        leftWall.setAlignment(-1,0,screenHeight,1);
        topWall.setAlignment(0,-1,1,screenWidth);
        rightWall.setAlignment(screenWidth,0,screenHeight,1);
        bottomWall.setAlignment(0,screenHeight,1,screenWidth);
        //don't need to render. Walls are invisible
    }

}
