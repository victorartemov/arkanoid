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
    Sprite ball1 = new Sprite();
    Sprite ball2 = new Sprite();
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
        initializeBall(ball1);
        initializeBall(ball2);

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

                makeReflection(ball1);
                makeReflection(ball2);

                updateSpriteOnTheScreen(ball1);
                updateSpriteOnTheScreen(ball2);

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

    public void initializeBall(Sprite s){
        s.setImage(new Image("ball.png"));
        s.setAlignment(screenWidth/2-10,screenHeight/2-10,20,20);
        s.setSpeed(random.nextInt(7)+1);
        s.setAngle(random.nextDouble()*(-pi));
        s.render(gc);
    }

    public void initializeWalls(){
        leftWall.setAlignment(-1,0,screenHeight,1);
        topWall.setAlignment(0,-1,1,screenWidth);
        rightWall.setAlignment(screenWidth,0,screenHeight,1);
        bottomWall.setAlignment(0,screenHeight,1,screenWidth);
        //don't need to render. Walls are invisible
    }

    public void makeReflection(Sprite s){
        //wall reflection logic

        //top wall
        if(s.getPositionY() < 0 && s.getPositionX() > 0 && s.getPositionX() < screenWidth){
            s.setAngle(-s.getAngle());
        }
        //left wall
        if(s.getPositionX() < 0 && s.getPositionY() > 0 && s.getPositionY() < screenHeight){
            if(s.getAngle() > 0) s.setAngle(pi - s.getAngle()); //going down
            if(s.getAngle() < 0) s.setAngle(-pi - s.getAngle()); //going up
        }
        //right wall
        if(s.getPositionX() > screenWidth - 20 && s.getPositionY() > 0 && s.getPositionY() < screenHeight){
            if(s.getAngle() > 0) s.setAngle(pi - s.getAngle()); //going down
            if(s.getAngle() < 0) s.setAngle(-pi - s.getAngle()); //going up
        }

        //bottom wall

        //platform
        if(s.intersects(platform)){
            s.setAngle(-s.getAngle());
        }

    }

    public void updateSpriteOnTheScreen(Sprite s){
        s.update();
        s.render(gc);
    }

}
