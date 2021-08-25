package com.codecool.snake;

import com.codecool.snake.resources.Resources;
import javafx.scene.image.Image;

public class Globals {
    private static Globals instance = null;

    public static final double WINDOW_WIDTH = 1000;
    public static final double WINDOW_HEIGHT = 700;

    public Display display;
    public Game game;
    private GameLoop gameLoop;
    private Resources resources;


    public static Globals getInstance() {
        if(instance == null) instance = new Globals();
        return instance;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void setupResources() {
        resources = new Resources();
        resources.addImage("SnakeHead", new Image("tourist1.png"));
        resources.addImage("SnakeBody", new Image("standingtourist.png"));
        resources.addImage("SimpleEnemy", new Image("simple_enemy.png"));
        resources.addImage("Beer", new Image("beer.png"));
        resources.addImage("EnemyBird", new Image("pigeon.png"));
        resources.addImage("EnemyGreenBug", new Image("greenbug.png"));
        resources.addImage("EnemyBrownBug", new Image("brownbug.png"));
        resources.addImage("EnemyBomb", new Image("bomb.png"));
        resources.addImage("EnemyScooter", new Image("scooter.png"));
        resources.addImage("BoostPower", new Image("starbucks.png"));
        resources.addImage("HotDog", new Image("hotdog.png"));
        resources.addImage("EnemyDog", new Image("dog2.png"));
        resources.addImage("EnemyPoop", new Image("poop1small.png"));

    }

    public Image getImage(String name) { return resources.getImage(name); }

    public void startGame() { gameLoop.start(); }

    public void stopGame() { gameLoop.stop(); }

    private Globals() {
        // singleton needs the class to have private constructor
    }
}
