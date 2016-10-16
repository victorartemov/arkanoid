package model;
import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Viktor_Artemov on 10/14/2016.
 */
public class Game {
    //constants
    private static final int SCREEN_HEIGHT = 595;
    private static final int SCREEN_WIDTH = 810;
    private static final int PLATFORM_HEIGHT = 35;
    private static final int PLATFORM_WIDTH = 100;
    private static final int BALL_HEIGHT = 60;
    private static final int BALL_WIDTH = 60;

    //game state flags
    public static boolean running = false;
    public static boolean started = false;

    //variables
    public static int ballsCount = 7;
    private ArrayList<Ball> balls = new ArrayList<>();
    private Platform platform = new Platform();
    private Random random = new Random();
    private Image background = new Image("pictures/background.png");
    private Image startBackground = new Image("pictures/startImage.png");
    ArrayList<Image> ballImages = new ArrayList<>();
    GraphicsContext gc;
    Parent root;
    FXMLLoader loader = new FXMLLoader();
    Canvas canvas;

    public void runTheGame(){
        loader.setLocation(getClass().getResource("../screen-layout.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = loader.getController();

        canvas = new Canvas(SCREEN_WIDTH,SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        controller.getGamePart().getChildren().add(canvas);

        initializePlatform();
        initializeBallImages();
        initializeBalls();

        gc.drawImage(startBackground, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime) {
                if(running) {
                    gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    gc.drawImage(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    platform.render(gc);

                    for (Ball s : balls) {
                        makeReflection(s);
                        updateBallOnTheScreen(s);
                    }
                }
            }
        }.start();

        canvas.setOnMouseDragged(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if((event.getX() > platform.getWidth()/2) && (event.getX() < SCREEN_WIDTH - platform.getWidth()/2)) {
                            platform.setPositionX(event.getX() - 40);
                        }
                    }
                }
        );
    }

    public Parent getRoot() {
        return root;
    }

    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        Game.started = started;
    }

    public void initializePlatform(){
        platform.setImage(new Image("pictures/platform.png"));
        platform.setAlignment(SCREEN_WIDTH / 2 - 25, SCREEN_HEIGHT - 30, PLATFORM_HEIGHT, PLATFORM_WIDTH);
        platform.render(gc);

    }

    public void initializeBall(Ball ball){
        ball.setImage(ballImages.get(random.nextInt(ballImages.size())));
        ball.setAlignment(SCREEN_WIDTH/2-BALL_WIDTH/2,SCREEN_HEIGHT/2-BALL_HEIGHT/2,BALL_HEIGHT,BALL_WIDTH);
        ball.setSpeed(random.nextInt(4)+1);
        ball.setAngle(random.nextDouble()*(-Math.PI));
    }

    public void initializeBalls(){
        for (int i = 0; i < ballsCount; i++){
            Ball ball = new Ball();
            initializeBall(ball);
            balls.add(ball);
        }
    }

    public void initializeBallImages(){
        ballImages.add(new Image("pictures/squidward.png"));
        ballImages.add(new Image("pictures/spanch.png"));
        ballImages.add(new Image("pictures/sandy.png"));
        ballImages.add(new Image("pictures/patrick.png"));
        ballImages.add(new Image("pictures/mrPlancton.png"));
        ballImages.add(new Image("pictures/mrCrabs.png"));
        ballImages.add(new Image("pictures/gary.png"));
    }

    public void makeReflection(Ball ball){
        //wall reflection logic

        //top wall
        if(ball.getPositionY() < 0 && ball.getPositionX() > 0 && ball.getPositionX() < SCREEN_WIDTH){
            ball.setAngle(-ball.getAngle());
        }
        //left wall
        if(ball.getPositionX() < 0 && ball.getPositionY() > 0 && ball.getPositionY() < SCREEN_HEIGHT){
            if(ball.getAngle() > 0) ball.setAngle(Math.PI - ball.getAngle()); //going down
            if(ball.getAngle() < 0) ball.setAngle(-Math.PI - ball.getAngle()); //going up
        }
        //right wall
        if(ball.getPositionX() > SCREEN_WIDTH - BALL_WIDTH && ball.getPositionY() > 0 && ball.getPositionY() < SCREEN_HEIGHT){
            if(ball.getAngle() > 0) ball.setAngle(Math.PI - ball.getAngle()); //going down
            if(ball.getAngle() < 0) ball.setAngle(-Math.PI - ball.getAngle()); //going up
        }

        //bottom wall

        //platform
        if(ball.intersects(platform)){
            ball.setAngle(-ball.getAngle());
        }

    }

    public void updateBallOnTheScreen(Ball ball){
        ball.update();
        ball.render(gc);
    }
}
